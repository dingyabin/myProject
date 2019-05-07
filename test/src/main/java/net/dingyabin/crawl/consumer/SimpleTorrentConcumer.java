package net.dingyabin.crawl.consumer;

import net.dingyabin.crawl.model.Torrent;
import net.dingyabin.crawl.request.AbstractRequest;
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

    private String basepath;

    private String fileType = ".torrent";

    public SimpleTorrentConcumer(BlockingQueue<Torrent> queue, String basepath) {
        this.queue = queue;
        this.basepath = basepath;
    }

    public SimpleTorrentConcumer(BlockingQueue<Torrent> queue, String basepath, String fileType) {
        this.queue = queue;
        this.basepath = basepath;
        this.fileType = fileType;
    }


    public SimpleTorrentConcumer fileType(String fileType) {
        if (StringUtils.isNotBlank(fileType)) {
            this.fileType = fileType;
        }
        return this;
    }


    protected Pair<File,Boolean> createFile(String path) throws IOException {
        File file = null;
        try {
            String[] delStr = {"\\*", "\\?", "\\|", "<", ">", "!", "\"", "/"," "};
            for (String str : delStr) {
                path = path.replaceAll(str, "");
            }
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
                Torrent torrent = queue.poll(100, TimeUnit.SECONDS);
                if (torrent == null) {
                    if (queue.isEmpty()) {
                        return;
                    }
                    continue;
                }
                String fileName = basepath + DateTime.now().toString("yyyyMMdd") + "\\男生\\" + torrent.getName() + fileType;
                Pair<File, Boolean> fileBooleanPair = createFile(fileName);
                File file = fileBooleanPair.getLeft();
                if (file == null) {
                    continue;
                }
                if (!fileBooleanPair.getRight()) {
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
                System.out.printf(">>>>>>线程%s成功download一个文件,目前还剩%s个任务<<<<<<<<\n", Thread.currentThread().getName(), queue.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
