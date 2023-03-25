package net.dingyabin.crawl.producer;

import com.google.common.collect.Maps;
import net.dingyabin.crawl.model.Torrent;
import net.dingyabin.crawl.utils.Utils;
import net.wecash.utils.HTTPClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
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


    public SoftWareTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
    }


    @Override
    protected String getUrl() {
        return this.url;
    }



    protected Map<String, String> getQueryRequestHeader() {
        Map<String, String> requestHeader = Maps.newHashMap();
        requestHeader.put("Accept","*/*");
        requestHeader.put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
//        requestHeader.put("Cache-Control","max-age=0");
//          requestHeader.put("Cookie","rewardsn=; wxtokenkey=777");
          requestHeader.put("Host","mp.weixin.qq.com");
//        //requestHeader.put("If-Modified-Since","Sat, 25 Mar 2023 16:15:43 +0800");
//        requestHeader.put("sec-ch-ua","\"Chromium\";v=\"110\", \"Not A(Brand\";v=\"24\", \"Microsoft Edge\";v=\"110\"");
//        requestHeader.put("sec-ch-ua-mobile","?0");
//        requestHeader.put("sec-ch-ua-platform","\"Windows\"");
//        requestHeader.put("Sec-Fetch-Dest","document");
//        requestHeader.put("Sec-Fetch-Mode","navigate");
//        requestHeader.put("Sec-Fetch-Site","same-origin");
//        requestHeader.put("Sec-Fetch-User","?1");
//        //requestHeader.put("Upgrade-Insecure-Requests","1");
        requestHeader.put("x-forwarded-for", Utils.getRandomIp());
        requestHeader.put("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.0.0 Safari/537.36 Edg/110.0.1587.50");
        return requestHeader;
    }

    @Override
    protected List<Torrent> makeTorrent(String resource) {
        List<Torrent> list = new ArrayList<>();
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
                        continue;
                    }
                    Elements aTags = td.getElementsByTag("a");
                    if (aTags == null || aTags.isEmpty()) {
                        String html = td.getElementsByTag("strong").get(0).getElementsByTag("span").html();
                        System.out.println("小标题: " + html.replaceAll("&nbsp;", ""));
                        continue;
                    }
                    Element atag = aTags.get(0);
                    String href = atag.attr("href");
                    String textvalue = atag.attr("textvalue");
                    System.out.println(textvalue + " : " + href);

                   // href="http://mp.weixin.qq.com/s?__biz=MzA4MjU4MTg2Ng==&mid=2247494166&idx=1&sn=dc8529ba9f127eeb4c18bbfcd7214673";
                   // Document _doc = Jsoup.parse(getResource(href, getQueryRequestHeader()));
                    Document _doc = Jsoup.parse(HTTPClient.get(href));
                    String html = _doc.html();

                    Matcher matcher = baiduPompile.matcher(html);
                    while (matcher.find()){
                        String group = matcher.group(1);
                        System.out.println("地址:    "+group);
                    }

                    Thread.sleep(2000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
