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
public class E048TorrentProducer extends AbstractTorrentProducer {

    private String url = "http://tscze.biz/2048/thread.php?fid-43-page-%s.html";

    private String keyWords[] = {"【影片片名】",
                                 "[影片名稱]",
                                 "【影片大小】",
                                 "【影片格式】",
                                 "[是否有碼]",
                                 "【有码无码】",
                                 "【影片预览】",
                                 "[影片截图]",
                                 "【下载链接】"
                              };


    public E048TorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
        this.url = String.format(this.url, pageNumber);
    }


    @Override
    protected String getUrl() {
        return this.url;
    }

    @Override
    protected int getConnTimeOut() {
        return 30;
    }

    @Override
    protected List<Torrent> makeTorrent(String resource) {
        List<Torrent> list = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(resource);
            Element table = doc.getElementById("ajaxtable");
            Element tbody = table.getElementsByTag("tbody").get(1);
            Elements trs = tbody.getElementsByClass("tr3 t_one");
            for (Element tr : trs) {
                boolean onmouseover = tr.hasAttr("onmouseover");
                boolean onmouseout =  tr.hasAttr("onmouseout");
                if (onmouseover && onmouseout) {
                    continue;
                }
                Elements tal = tr.getElementsByAttributeValueStarting("id", "td_");
                if (tal == null) {
                    continue;
                }
                Element a = tal.get(0).getElementsByTag("a").get(0);
                if (a == null) {
                    continue;
                }
                String html = getResource("http://tscze.biz/2048/" + a.attr("href"));
                if (StringUtils.isBlank(html)) {
                    continue;
                }
                Document doc2 = Jsoup.parse(html);
                Element subject = doc2.getElementById("subject_tpc");
                String text = "默认名字";
                if (subject != null) {
                    text = subject.text();
                }
                Element read_tpc = doc2.getElementById("read_tpc");
                if (read_tpc == null) {
                    continue;
                }
                String content = formatContent(read_tpc.text());
                list.add(new Torrent(text, null, content.getBytes()));
                System.out.println("拿到一个.......");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }


    private String formatContent(String source) {
        if (StringUtils.isBlank(source)) {
            return "";
        }
        for (String keyWord : keyWords) {
            if (source.contains(keyWord)) {
                source = source.replace(keyWord, "\n\r" + keyWord );
            }
        }
        return source;
    }

}
