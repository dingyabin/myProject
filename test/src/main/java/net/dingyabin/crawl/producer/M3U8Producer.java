package net.dingyabin.crawl.producer;

import net.dingyabin.crawl.model.Torrent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:23:57
 */
public class M3U8Producer extends AbstractTorrentProducer {

    private String url = "https://m3u8.38cdn.com/movie-hls/160730/bdyjy168/index.m3u8";

    private String parentPath;


    public M3U8Producer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
        this.url = String.format(this.url, pageNumber);
        this.parentPath = url.substring(0, url.lastIndexOf("/"));
    }


    @Override
    protected String getUrl() {
        return this.url;
    }


    @Override
    protected List<Torrent> makeTorrent(String resource) {
        List<Torrent> list = new ArrayList<>();
        try {

            List<String> strings = IOUtils.readLines(new ByteArrayInputStream(resource.getBytes(getEncoding())), getEncoding());

            for (String info : strings) {
                if (info.startsWith("#")) {
                    continue;
                }
                list.add(new Torrent(info, parentPath + "/" + info));
            }
            System.out.println("总共有"+list.size()+"个文件");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
