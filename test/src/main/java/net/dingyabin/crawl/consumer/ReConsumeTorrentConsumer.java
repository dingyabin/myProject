package net.dingyabin.crawl.consumer;

import com.alibaba.fastjson.JSONObject;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReConsumeTorrentConsumer extends SimpleTorrentConcumer {

    private String baseUrl = "https://www.eefanhao.com";


    @Override
    protected void doWrite(Torrent torrent, File file, byte[] bytes) throws IOException {
        String href = new String(torrent.getContent());
        String title = torrent.getName();

        List<String> content = findContent(baseUrl + "/" + URLEncoder.encode(href, "utf-8"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(title, content);

        System.out.println(jsonObject.toJSONString());
        synchronized (ReConsumeTorrentConsumer.class) {
            super.doWrite(torrent, file, jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8));
        }
    }


    private List<String> findContent(String url) throws IOException {
        List<String> result = new ArrayList<>();
        while (true) {
            sleep(500);
            String html = getResource(url);
            if (StringUtils.isEmpty(html)) {
                //计算下一页
                return result;
            }
            Document doc = Jsoup.parse(html);
            Element div = doc.getElementsByTag("main").get(0).getElementsByClass("default-loop-wrap").get(0);
            Elements ul = div.getElementsByClass("post-loop post-loop-default cols-4");
            Elements li = ul.get(0).getElementsByTag("li");
            for (Element element : li) {
                String text = element.getElementsByClass("item-meta-left").get(0).text();
                result.add(text);
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
        }
    }


}
