package net.dingyabin.crawl.consumer;

import net.dingyabin.crawl.model.Torrent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:23:13
 */
public class SimpleTorrentConcumer implements Runnable {

    private BlockingQueue<Torrent> queue;

    private String basepath;

    public SimpleTorrentConcumer(BlockingQueue<Torrent> queue, String basepath) {
        this.queue = queue;
        this.basepath = basepath;
    }

    protected byte[] getFile(String url) throws IOException {
        HttpURLConnection e = (HttpURLConnection) (new URL(url)).openConnection();
        InputStream inputStream = e.getInputStream();
        byte[] bytes = IOUtils.toByteArray(inputStream);
        IOUtils.closeQuietly(inputStream);
        return bytes;
    }

    protected File createFile(String path) throws IOException {
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

    @Override
    public void run() {
        try {
            while (true) {
               Torrent torrent = queue.poll(30, TimeUnit.SECONDS);
                if (torrent == null) {
                    if (queue.isEmpty()) {
                        return;
                    }
                    continue;
                }
                byte[] bytes = getFile(torrent.getUrl());
                String fileName = basepath + DateTime.now().toString("yyyyMMdd") + "\\" + torrent.getName() + ".torrent";
                FileUtils.writeByteArrayToFile(createFile(fileName), bytes, false);
                System.out.printf(">>>>>>线程%s成功download一个文件,目前还剩%s个任务<<<<<<<<\n", Thread.currentThread().getName(), queue.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
