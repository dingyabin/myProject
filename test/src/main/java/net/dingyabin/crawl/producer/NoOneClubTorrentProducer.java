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
public class NoOneClubTorrentProducer extends AbstractTorrentProducer {

    private String url = "http://68.168.16.149/forum/forum-25-%s.html";


    public NoOneClubTorrentProducer(BlockingQueue<Torrent> queue, int pageNumber) {
        super(queue, pageNumber);
        this.url = String.format(this.url, pageNumber);
    }

    public NoOneClubTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
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
            Elements tbodys = doc.getElementsByTag("tbody");
            for (Element tbody : tbodys) {
                if (StringUtils.isBlank(tbody.id()) || !tbody.id().startsWith("normalthread")) {
                    continue;
                }
                Element a = tbody.getElementsByTag("tr").get(0)
                        .getElementsByTag("th").get(0)
                        .getElementsByTag("span").get(0)
                        .getElementsByTag("a").get(0);
                if (a == null) {
                    continue;
                }
                String html = getResource("http://68.168.16.149/forum/" + a.attr("href"));
                if (StringUtils.isBlank(html)) {
                    continue;
                }
                Elements tAttachlists = Jsoup.parse(html).getElementsByClass("t_attachlist");
                if (tAttachlists.size() == 0) {
                    continue;
                }
                Element ta = tAttachlists.get(0).getElementsByTag("a").get(1);
                String text = a.text();
                String href = "http://68.168.16.149/forum/" + ta.attr("href");
                list.add(new Torrent(text, href));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
