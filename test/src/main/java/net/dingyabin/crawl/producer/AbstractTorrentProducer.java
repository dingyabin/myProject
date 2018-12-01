package net.dingyabin.crawl.producer;

import net.dingyabin.crawl.model.Torrent;
import net.dingyabin.crawl.request.AbstractRequest;
import net.dingyabin.crawl.utils.Utils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:22:25
 */
public abstract class AbstractTorrentProducer extends AbstractRequest implements Runnable {


    private BlockingQueue<Torrent> queue;

    private String encoding;

    private int pageNumber;

    public AbstractTorrentProducer(BlockingQueue<Torrent> queue, int pageNumber) {
        this.queue = queue;
        this.pageNumber = pageNumber;
    }

    public AbstractTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        this.queue = queue;
        this.encoding = encoding;
        this.pageNumber = pageNumber;
    }


    protected String getResource() {
        String stringResource = null;
        try {
            stringResource = getStringResource(getUrl(), encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringResource;
    }


    protected String getResource(String url) throws IOException {
        String stringResource = null;
        try {
            stringResource = getStringResource(url, encoding);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringResource;
    }



    public String getEncoding() {
        return encoding;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public String getEncoding() {
        return encoding;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    protected abstract String getUrl();


    protected abstract List<Torrent> makeTorrent(String resource);


    @Override
    public void run() {
        try {
            String resource = getResource();
            if (StringUtils.isBlank(resource)) {
                System.out.printf("xxxxxxxxxxparseHome,第%s页空白,跳过xxxxxxxxxxx\n", pageNumber);
                return;
            }
            List<Torrent> torrents = makeTorrent(resource);
            if (CollectionUtils.isEmpty(torrents)) {
                System.out.printf("第%s页,没有获取到任何torrent资源\n", pageNumber);
                return;
            }
            torrents.forEach(torrent -> {
                queue.offer(torrent);
                String content = (torrent.getContent() != null) ? new String(torrent.getContent()) : null;
                System.out.println("第" + pageNumber + "页-->" + torrent.getName() + "url=(" + torrent.getUrl() + ") content=" + content + "\n\n");
            });
            System.out.printf("第%s页，获取到%s个\n", pageNumber, torrents.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
