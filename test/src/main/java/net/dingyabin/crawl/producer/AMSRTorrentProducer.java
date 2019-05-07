package net.dingyabin.crawl.producer;

import net.dingyabin.crawl.model.Torrent;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:22:57
 */
public class AMSRTorrentProducer extends AbstractTorrentProducer {

    private String url = "http://www.semorn.com/article/20844.html";


    public AMSRTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
        this.url = String.format(this.url, pageNumber);
    }


    @Override
    protected String getUrl() {
        return this.url;
    }


    @Override
    protected List<Torrent> makeTorrent(String resource) {
        List<Torrent> list = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(resource);
            Element main = doc.getElementById("content").getElementsByClass("main").get(0);
            Element iframe = main.getElementsByTag("iframe").get(0);
            Document frameSrc = Jsoup.parse(getResource(iframe.attr("src")));
            Element playlist = frameSrc.getElementById("playlist");
            Element soundlist = playlist.getElementsByClass("soundlist").get(0);
            Elements li = soundlist.getElementsByTag("li");

            for (Element lie : li) {
                String title = lie.attr("title");
                String url = lie.attr("data-soundurl64");
                String href = "http://static.missevan.com/" + url;
                list.add(new Torrent(title, href));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
