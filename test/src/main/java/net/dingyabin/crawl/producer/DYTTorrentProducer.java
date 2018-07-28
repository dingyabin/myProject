package net.dingyabin.crawl.producer;

import net.dingyabin.crawl.model.Torrent;
import org.apache.commons.collections4.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:23:57
 */
public class DYTTorrentProducer extends AbstractTorrentProducer {

    private String url = "http://www.dytt8.net/html/gndy/dyzz/list_23_%s.html";


    public DYTTorrentProducer(BlockingQueue<Torrent> queue, int pageNumber) {
        super(queue, pageNumber);
        this.url = String.format(this.url, pageNumber);
    }

    public DYTTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
        this.url = String.format(this.url, pageNumber);
    }


    @Override
    protected String getUrl() {
        return this.url;
    }


    @Override
    protected List<Torrent> makeTorrent(String resource) {
        Document doc = Jsoup.parse(resource);
        Elements links = doc.getElementsByClass("ulink");
        List<Torrent> list = new ArrayList<>();
        if (CollectionUtils.isEmpty(links)) {
            return list;
        }
        for (Element link : links) {
            String text = link.text();
            String href = "http://www.dytt8.net" + link.attr("href");
            list.add(new Torrent(text, href));
        }
        return list;
    }
}
