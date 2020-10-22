package net.dingyabin.crawl.consumer;

import net.dingyabin.crawl.enums.WebSiteEnum;
import net.dingyabin.crawl.model.Torrent;
import net.dingyabin.crawl.request.AbstractRequest;
import net.dingyabin.crawl.utils.TimeCounter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.DateTime;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:23:13
 */
public class SimpleTorrentConcumer extends AbstractRequest implements Runnable {

    private BlockingQueue<Torrent> queue;

    private WebSiteEnum webSiteEnum;

    private String[] delStr = {"\\*", "\\?", "\\|", "<", ">", "!", "\"", "/", " ", "：", ":"};

    public SimpleTorrentConcumer(WebSiteEnum webSiteEnum, BlockingQueue<Torrent> queue) {
        this.queue = queue;
        this.webSiteEnum = webSiteEnum;
    }

    protected Pair<File,Boolean> createFile(String path) throws IOException {
        File file;
        try {
            file = new File(path);
            if (file.exists()){
                return Pair.of(file,false);
            }
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            return Pair.of(file, file.createNewFile());
        } catch (IOException e) {
            System.out.println("xxxxxxx创建文件失败xxxxxxxx"+path);
            e.printStackTrace();
        }
        return Pair.of(null, false);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Torrent torrent = queue.poll(40000000, TimeUnit.SECONDS);
                if (torrent == null) {
                    if (queue.isEmpty()) {
                        return;
                    }
                    continue;
                }
                String name = torrent.getName();
                for (String str : delStr) {
                    name = name.replaceAll(str, "");
                }
                String fileName = webSiteEnum.getPath() + name + webSiteEnum.getFileType();
                Pair<File, Boolean> fileBooleanPair = createFile(fileName);
                File file = fileBooleanPair.getLeft();
                if (file == null) {
                    continue;
                }
                if (!fileBooleanPair.getRight() && file.getTotalSpace() > 100) {
                    System.out.println("发现已经存在的文件:"+ file.getAbsolutePath());
                    continue;
                }
                byte[] bytes = torrent.getContent();
                if (bytes == null) {
                    bytes = getFileResource(torrent.getUrl());
                }
                if (bytes == null) {
                    continue;
                }
                FileUtils.writeByteArrayToFile(file, bytes, false);
                System.out.printf(">>>>>>线程%s成功download一个文件,目前还剩%s个任务,大概需要%s<<<<<<<<\n", Thread.currentThread().getName(), queue.size(), TimeCounter.restTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
