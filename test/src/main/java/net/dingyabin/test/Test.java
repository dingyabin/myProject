package net.dingyabin.test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by MrDing
 * Date: 2017/9/13.
 * Time:21:05
 */
public class Test {

    private static final LinkedBlockingQueue<Torrent> QUEUE = new LinkedBlockingQueue<>();
    private static final String BATHPATH = "C:\\Users\\MrDing\\Desktop\\1111\\";

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 1; i <= 10; i++) {
            executorService.submit(new parseHome(i, "http://68.168.16.149/forum/forum-25-" + i + ".html"));
            executorService.submit(new DownTorrent());
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.HOURS);
    }


    private static class parseHome implements Runnable {
        private int page;
        private String url;

        public parseHome(int page, String url) {
            this.page = page;
            this.url = url;
        }

        @Override
        public void run() {
            try {
                Document doc = Jsoup.parse(getHtml(url));
                Elements tbodys = doc.getElementsByTag("tbody");
                int size = 0;
                for (Element tbody : tbodys) {
                    if (StringUtils.isBlank(tbody.id()) || !tbody.id().startsWith("normalthread")) {
                        continue;
                    }
                    Element a = tbody.getElementsByTag("tr").get(0)
                            .getElementsByTag("th").get(0)
                            .getElementsByTag("span").get(0)
                            .getElementsByTag("a").get(0);
                    String text = a.text();
                    String href = "http://68.168.16.149/forum/" + a.attr("href");
                    QUEUE.offer(new Torrent(text, href));
                    size++;
                    System.out.println(text + "(" + href + ")");
                }
                System.out.printf("第%s页，%s个\n", page, size);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


    private static class DownTorrent implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Torrent torrent = QUEUE.poll(5, TimeUnit.MINUTES);
                    if (torrent == null) {
                        if (QUEUE.isEmpty()) {
                            return;
                        }
                        continue;
                    }
                    String html = getHtml(torrent.getUrl());
                    if (StringUtils.isBlank(html)){
                        continue;
                    }
                    Elements tAttachlists = Jsoup.parse(html).getElementsByClass("t_attachlist");
                    if (tAttachlists.size()==0){
                        continue;
                    }
                    Element a = tAttachlists.get(0).getElementsByTag("a").get(1);
                    String href = "http://68.168.16.149/forum/" + a.attr("href");
                    byte[] bytes = getFile(href);
                    String fileName = BATHPATH + DateTime.now().toString("yyyyMMdd") + "\\" + torrent.getName() + ".torrent";
                    FileUtils.writeByteArrayToFile(createFile(fileName), bytes, false);
                    System.out.printf(">>>>>>线程%s成功download一个文件,目前还剩%s个任务<<<<<<<<\n", Thread.currentThread().getName(), QUEUE.size());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static String getHtml(String url) throws IOException {
        HttpURLConnection e = (HttpURLConnection) (new URL(url)).openConnection();
        InputStream inputStream = e.getInputStream();
        String gbk = IOUtils.toString(inputStream, "gbk");
        IOUtils.closeQuietly(inputStream);
        return gbk;
    }

    private static byte[] getFile(String url) throws IOException {
        HttpURLConnection e = (HttpURLConnection) (new URL(url)).openConnection();
        InputStream inputStream = e.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        IOUtils.closeQuietly(inputStream);
        return bytes;
    }


    private static File createFile(String path) throws IOException {
        String[] delStr = {"\\*", "\\?", "|", "<", ">", "!", "\""};
        for (String str : delStr) {
            path = path.replaceAll(str, "");
        }
        File file = new File(path);
        if (!file.exists() && !file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();
        return file;
    }


    private static class Torrent {
        private String name;
        private String url;

        public Torrent(String name, String url) {
            this.name = name;
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }

}
