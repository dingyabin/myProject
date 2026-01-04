package net.dingyabin.crawl.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import net.dingyabin.crawl.model.Torrent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class ReConsumeTorrentConsumer extends SimpleTorrentConcumer {

    private static final String baseUrl = "https://www.eefanhao.com";

    private static final ReentrantLock lock = new ReentrantLock();

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(4);

    //private final static Set<String> set = Test666.findStorage("E:\\番号列表.txt");

    @Override
    protected void doWrite(Torrent torrent, File file, byte[] bytes) throws IOException {
        JSONObject json = JSONObject.parseObject(new String(torrent.getContent()));
        String href = json.getString("href");
        String title = json.getString("title");

//        if (set.contains(title)){
//            System.out.println("已经存在了-" + title);
//            return;
//        }

        Map<String, String> content = findFanHaoContent(baseUrl + "/" + URLEncoder.encode(href, "utf-8"));

        Map<String, String> jsonObject = new LinkedHashMap<>();
        jsonObject.put("title", title);
        jsonObject.putAll(content);

        String line = JSON.toJSONString(jsonObject);
        lock.lock();
        try {
            super.doWrite(torrent, file, (line + System.lineSeparator() ).getBytes(StandardCharsets.UTF_8));
        } finally {
            lock.unlock();
        }
    }


    private List<String> findContent(String url) throws IOException {
        List<String> result = new ArrayList<>();
        while (true) {
            sleep(100);
            String html = getResource(url);
            if (StringUtils.isEmpty(html)) {
                //计算下一页
                String pageNumStr = StringUtils.substringBetween(url, "_", ".html");
                if (StringUtils.isBlank(pageNumStr)) {
                    return result;
                }
                url = StringUtils.substringBefore(url, "_") + "_" + (Integer.parseInt(pageNumStr) + 1) + ".html";
                continue;
            }
            Document doc = Jsoup.parse(html);
            Element div = doc.getElementsByTag("main").get(0).getElementsByClass("default-loop-wrap").get(0);
            Elements ul = div.getElementsByClass("post-loop post-loop-default cols-4");
            Elements li = ul.get(0).getElementsByTag("li");
            for (Element element : li) {
                String text = element.getElementsByClass("item-meta-left").get(0).text();
                result.add(text);
            }
            if (CollectionUtils.size(result) == 0) {
                return result;
            }
            Elements navLinkEles = div.getElementsByClass("nav-links");
            if (CollectionUtils.isEmpty(navLinkEles)) {
                return result;
            }
            Elements page = navLinkEles.get(0).getElementsByTag("a");
            Optional<Element> nextPage = page.stream()
                    .filter(element -> element.className().equals("next page-numbers") && element.text().equals(">"))
                    .findFirst();
            if (!nextPage.isPresent()) {
                return result;
            }
            String href = nextPage.get().attr("href");
            if (href.startsWith("/")) {
                href = StringUtils.substringAfter(href, "/");
            }
            url = baseUrl + "/" + URLEncoder.encode(href, "utf-8");
            System.out.println("================"+ url);
        }
    }

    private Map<String, String> findFanHaoContent(String url) throws IOException {
        Map<String, String> jsonObject = new LinkedHashMap<>();
        String html = getResource(url);

        if (StringUtils.isBlank(html)) {
            return jsonObject;
        }

        Document doc = Jsoup.parse(html);
        Element div = doc.getElementsByClass("entry-preview entry-preview2").first();
        Element div2 = div.getElementsByClass("entry-info-item-wrap").first();
        Elements entrys = div2.getElementsByClass("entry-info-item");

        Element actor = entrys.get(2);
        if (actor != null) {
            Element a = actor.getElementsByTag("a").first();
            jsonObject.put("Actor", a != null ? a.text() : null);
        }

        Element year = entrys.get(3);
        if (year != null) {
            String text = year.text();
            jsonObject.put("Year",  text != null && text.contains("：") ? StringUtils.substringAfter(text,"：") : "");
        }

        Element cost = entrys.get(4);
        if (cost != null) {
            String text = cost.text();
            jsonObject.put("Cost",  text != null && text.contains("：") ? StringUtils.substringAfter(text,"：") : "");
        }

        List<String> cate = new ArrayList<>();
        Elements a = entrys.last().getElementsByTag("a");
        for (Element element : a) {
            cate.add(element.text());
        }
        jsonObject.put("Category", String.join(",", cate));
        System.out.println("================" + url);

        return jsonObject;
    }



    protected Map<String, String> getRequestHeader() {
        Map<String, String> header = super.getRequestHeader();
        header.remove("Host");
        header.put("Cookie", "__51vcke__Je3cl9DbHvVd7Yzy=501e42b8-fc3b-5a04-8f45-e33c93d4dfd3; __51vuft__Je3cl9DbHvVd7Yzy=1767013833357; sc_is_visitor_unique=rx13138842.1767453649.001C9DF1ED194D7CBFD89AC4E8B0F54E.3.3.3.3.3.3.2.2.1; __vtins__Je3cl9DbHvVd7Yzy=%7B%22sid%22%3A%20%22e816e4bc-4de4-5ef1-8ba1-c6fa1ebaed73%22%2C%20%22vd%22%3A%201%2C%20%22stt%22%3A%200%2C%20%22dr%22%3A%200%2C%20%22expires%22%3A%201767455454027%2C%20%22ct%22%3A%201767453654027%7D; __51uvsct__Je3cl9DbHvVd7Yzy=8");
        return header;

    }

    @Override
    protected RateLimiter getRateLimiter() {
        return RATE_LIMITER;
    }

    /**
     * 获取ConnTimeOut，单位是秒(s)
     * @return ConnTime
     */
    @Override
    protected int getConnTimeOut() {
        return 20;
    }

    /**
     * 获取ReadTimeOut，单位是秒(s)
     * @return ReadTimeOut
     */
    @Override
    protected int getReadTimeOut() {
        return 20;
    }
}
