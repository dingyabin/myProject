package net.dingyabin.crawl.producer;

import net.dingyabin.crawl.model.Torrent;
import org.apache.commons.collections4.CollectionUtils;
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
 * Time:23:57
 */
public class LpxxsTorrentProducer extends AbstractTorrentProducer {

    private String url = "http://www.lpxxs.com/bbs/book_list.aspx?action=class&siteid=1000&classid=333&getTotal=158&page=%s";


    public LpxxsTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
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
            Elements biaotis = doc.getElementsByClass("biaoti");
            if (CollectionUtils.isEmpty(biaotis)) {
                return list;
            }
            for (Element baoti : biaotis) {
                Element atag = baoti.getElementsByTag("a").get(0);
                if (CollectionUtils.isNotEmpty(atag.getElementsByTag("font "))) {
                    continue;
                }
                String href = atag.attr("href");
                Document _doc = Jsoup.parse(getResource(href));

                Elements content2s = _doc.getElementsByClass("content2");
                if (CollectionUtils.isEmpty(content2s)) {
                    continue;
                }
                Element content2 = content2s.get(0);
                Element h2 = content2.getElementsByTag("h2").get(0);
                String name = h2.html();
                String src;

                Element bbscontent = content2.getElementsByClass("bbscontent").get(0);
                Elements audios = bbscontent.getElementsByTag("audio");
                if (CollectionUtils.isEmpty(audios)) {
                    src = getWithNoAudio(bbscontent);
                } else if (audios.get(0).hasAttr("src")) {
                    src = getWithAudio(audios.get(0));
                } else {
                    src = getWithAudioSource(audios.get(0));
                }
                if (src == null) {
                    System.out.printf("发现需要购买的, page=%s, name=%s \n\r", getPageNumber(), name);
                    continue;
                }
                if (!src.startsWith("http")){
                    src = "http://www.lpxxs.com" + src;
                }
                list.add(new Torrent(name, src));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    private String getWithAudio(Element audio) {
        return audio.attr("src");
    }

    private String getWithAudioSource(Element audio) {
        return audio.getElementsByTag("source").get(0).attr("src");
    }

    private String getWithNoAudio(Element bbscontent) {
        Element atag = bbscontent.getElementsByTag("a").get(0);
        String html = atag.html();
        if (html != null && html.contains("购买")) {
            return null;
        }
        return atag.attr("href");
    }

}
