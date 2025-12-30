package net.dingyabin.crawl.producer;

import com.alibaba.fastjson.JSONObject;
import net.dingyabin.crawl.model.Torrent;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by MrDing
 * Date: 2023/03/24.
 * Time:22:57
 */
public class HaoHaokuTorrentProducer extends AbstractTorrentProducer {

    private String baseUrl = "https://www.eefanhao.com";

    private String url = baseUrl + "/tags/";


    public HaoHaokuTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
    }


    @Override
    protected String getUrl() {
        return this.url;
    }


    /**
     * 设置请求头信息,子类继承的时候最好先super.getRequestHeader()一下
     *
     * @return header
     */
    protected Map<String, String> getRequestHeader() {
        Map<String, String> header = super.getRequestHeader();
        header.remove("Host");
        header.put("cookie", "__51vcke__Je3cl9DbHvVd7Yzy=501e42b8-fc3b-5a04-8f45-e33c93d4dfd3; __51vuft__Je3cl9DbHvVd7Yzy=1767013833357; sc_is_visitor_unique=rx13138842.1767093303.001C9DF1ED194D7CBFD89AC4E8B0F54E.2.2.2.2.2.2.1.1.1; __51uvsct__Je3cl9DbHvVd7Yzy=5; __vtins__Je3cl9DbHvVd7Yzy=%7B%22sid%22%3A%20%2220007421-7770-5344-86c9-968f09e2784c%22%2C%20%22vd%22%3A%2012%2C%20%22stt%22%3A%20519001%2C%20%22dr%22%3A%2011393%2C%20%22expires%22%3A%201767107564949%2C%20%22ct%22%3A%201767105764949%7D");
        return header;

    }

    @Override
    protected List<Torrent> makeTorrent(String resource) {
        List<Torrent> list = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(resource);
            Element mianTag = doc.getElementsByTag("main").get(0);
            Element tagCoulds = mianTag.getElementsByClass("tag_could").get(0);
            Elements links = tagCoulds.getElementsByTag("a");
            for (Element linkEle : links) {
                String href = linkEle.attr("href");
                String title = linkEle.attr("title");
                System.out.println(href + " --------------" + title);

                list.add(new Torrent(, ));
                if (href.startsWith("/")) {
                    href = StringUtils.substringAfter(href, "/");
                }
                sleep(500);
                List<String> content = findContent(baseUrl + "/" + URLEncoder.encode(href, "utf-8"));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(title, content);
                System.out.println(jsonObject.toJSONString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    private List<String> findContent(String url) {
        String html = getResource(url);
        if (StringUtils.isEmpty(html)) {
            System.out.println("xxxxxxxxxxx空值:" + url);
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements ul = doc.getElementsByTag("main").get(0).getElementsByClass("post-loop post-loop-default cols-4");
        Elements li = ul.get(0).getElementsByTag("li");
        for (Element element : li) {
            String text = element.getElementsByClass("item-meta-left").get(0).text();
            result.add(text);
        }
        return result;
    }


//    @Override
//    public void run() {
//        try {
//            String resource = getResource();
//            if (StringUtils.isBlank(resource)) {
//                System.out.println("xxxxxxxxxxparseHome,第1页空白,跳过xxxxxxxxxxx");
//                return;
//            }
//            makeTorrent(resource);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
