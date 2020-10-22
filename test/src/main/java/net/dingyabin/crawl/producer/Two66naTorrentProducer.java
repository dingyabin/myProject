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
public class Two66naTorrentProducer extends AbstractTorrentProducer {

    private String url = "https://www.266na.com/vod/html16/index%s.html";


    public Two66naTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
        this.url = String.format(this.url, pageNumber == 1 ? "" : "_" + pageNumber);
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
            Elements box = doc.getElementsByClass("box-video-list");
            if (CollectionUtils.isEmpty(box)) {
                return list;
            }
            Elements links = box.get(0).getElementsByClass("item").get(0).getElementsByTag("ul").get(0).getElementsByTag("li");
            for (Element link : links) {
                Element a = link.getElementsByTag("a").get(0);
                String title = a.attr("title");
                String href = "https://www.266na.com" + a.attr("href");
                Document _doc = Jsoup.parse(getResource(href));
                Elements playul = _doc.getElementsByClass("playul");
                String preview = handlePreview(playul.get(0));
                String downLoad = handleDownLoad(playul.get(1));
                list.add(new Torrent(title, null, (preview + "\n\r" + downLoad).getBytes()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }



    private String  handlePreview(Element preview) {
        StringBuilder content = new StringBuilder();
        try {
            Elements previewList = preview.getElementsByTag("a");
            for (int i = 0; i < previewList.size(); i++) {
                content.append("预览地址")
                        .append(i + 1)
                        .append(" https://www.266na.com")
                        .append(previewList.get(i).attr("href"))
                        .append("\n\r");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    private String  handleDownLoad(Element download) {
        String attr = null;
        try {
            Elements downloadList = download.getElementsByTag("a");
            String href = "https://www.266na.com" + downloadList.get(0).attr("href");
            Document _doc = Jsoup.parse(getResource(href));
            attr = _doc.getElementsByClass("download").get(0).getElementsByTag("a").get(0).attr("href");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "下载地址："+ attr;
    }
}
