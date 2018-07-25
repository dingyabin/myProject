package net.dingyabin.test;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;

/**
 * Created by MrDing
 * Date: 2017/9/13.
 * Time:21:05
 */
public class Test {

    public static void main(String[] args) throws InterruptedException {
        //File file = new File("C:\\Users\\MrDing\\Desktop\\新建文本文档.txt");
        // FileUtils.write(file, text + "(" + href + ")\n", "gbk", true);
        LinkedBlockingQueue<String> queue = new LinkedBlockingQueue<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 1; i <= 10; i++) {
            executorService.submit(new parseHome(i, "http://68.168.16.149/forum/forum-25-" + i + ".html", queue));
        }
        executorService.shutdown();
        executorService.awaitTermination(10, TimeUnit.MINUTES);
    }


    private static class parseHome implements Runnable {
        private int page;
        private String url;
        private BlockingQueue<String> queue;

        public parseHome(int page, String url, BlockingQueue<String> queue) {
            this.page = page;
            this.url = url;
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                HttpURLConnection e = (HttpURLConnection) (new URL(url)).openConnection();
                InputStream inputStream = e.getInputStream();
                String html = IOUtils.toString(inputStream, "gbk");

                Document doc = Jsoup.parse(html);
                Elements tbodys = doc.getElementsByTag("tbody");
                int size = 0;
                for (Element tbody : tbodys) {
                    if (StringUtils.isBlank(tbody.id())) {
                        continue;
                    }
                    Element a = tbody.getElementsByTag("tr").get(0)
                            .getElementsByTag("th").get(0)
                            .getElementsByTag("span").get(0)
                            .getElementsByTag("a").get(0);
                    String text = a.text();
                    String href = "http://68.168.16.149/forum/" + a.attr("href");
                    queue.offer(href);
                    size++;
                    System.out.println(text + "(" + href + ")");
                }
                System.out.printf("第%s页，%s个\n", page, size);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }


}
