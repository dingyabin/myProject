package net.dingyabin.crawl.producer;

import net.dingyabin.crawl.model.Torrent;
import net.wecash.utils.HTTPClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by MrDing
 * Date: 2023/03/24.
 * Time:22:57
 */
public class SoftWareTorrentProducer extends AbstractTorrentProducer {

    private String url = "https://mp.weixin.qq.com/s/RH0oCJWD00QFXpuCYwB8oA";

    private Pattern baiduPompile = Pattern.compile("(https://pan.baidu.com/[a-zA-Z0-9]+/[a-zA-Z0-9_\\-]+)");

    private Pattern codePompile = Pattern.compile("\\[提取码\\]：</span><span [\\s\\S]*?>([a-zA-Z0-9_]+)</span>");


    public SoftWareTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
    }


    @Override
    protected String getUrl() {
        return this.url;
    }




    @Override
    protected List<Torrent> makeTorrent(String resource) {
        List<Torrent> list = new ArrayList<>();
        StringBuilder stringBuilder = new StringBuilder();
        try {
            Document doc = Jsoup.parse(resource);
            Elements tbodys = doc.getElementsByTag("tbody");

            for (int i = 1; i < tbodys.size(); i++) {
                Element tbody = tbodys.get(i);
                Elements tds = tbody.getElementsByTag("td");
                for (Element td : tds) {
                    if (td.html().length() < 15) {
                        continue;
                    }
                    String colspan = td.attr("colspan");
                    if ("3".equals(colspan) && "center".equals(td.attr("align"))) {
                        String html = td.getElementsByAttributeValueContaining("style", "rgb(0, 0, 0)").html();
                        System.out.println("大标题: " + html.replaceAll("&nbsp;", ""));
                        stringBuilder.append(html.replaceAll("&nbsp;", "")).append("\n");
                        continue;
                    }
                    Elements aTags = td.getElementsByTag("a");
                    if (aTags == null || aTags.isEmpty()) {
                        String html = td.getElementsByTag("strong").get(0).getElementsByTag("span").html();
                        System.out.println("小标题: " + html.replaceAll("&nbsp;", ""));
                        stringBuilder.append(html.replaceAll("&nbsp;", "")).append("\n");
                        continue;
                    }
                    Element atag = aTags.get(0);
                    String href = atag.attr("href");
                    String textvalue = atag.attr("textvalue");
                    System.out.println(textvalue + " : " + href);
                    stringBuilder.append(textvalue).append("\t");

                    Document _doc = Jsoup.parse(HTTPClient.get(href));
                    String html = _doc.html();

                    Matcher matcher = baiduPompile.matcher(html);
                    while (matcher.find()){
                        String group = matcher.group(1);
                        System.out.println("地址:    "+group);
                        stringBuilder.append(group).append("\t");
                    }


                    Matcher matcherCode = codePompile.matcher(html);
                    while (matcherCode.find()){
                        String group = matcherCode.group(1);
                        System.out.println("提取码:    "+group);
                        stringBuilder.append(group).append("\t");
                    }
                    stringBuilder.append("\n");
                    Thread.sleep(2000);
                }
                if (i==2){
                    break;
                }
            }
            list.add(new Torrent("soft",stringBuilder.toString().getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
