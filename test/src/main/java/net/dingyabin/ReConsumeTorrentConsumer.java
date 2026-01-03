package net.dingyabin.crawl.consumer;

import com.alibaba.fastjson.JSONObject;
import javafx.scene.effect.SepiaTone;
import net.dingyabin.Test666;
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

public class ReConsumeTorrentConsumer extends SimpleTorrentConcumer {

    private String baseUrl = "https://www.eefanhao.com";

    private Set<String> set = Test666.findStorage("E:\\测试文件-姓名.txt");

    @Override
    protected void doWrite(Torrent torrent, File file, byte[] bytes) throws IOException {
        JSONObject json = JSONObject.parseObject(new String(torrent.getContent()));
        String href = json.getString("href");
        String title = json.getString("title");

        if (set.contains(title)){
            System.out.println("已经存在了-" + title);
            return;
        }

        List<String> content = findContent(baseUrl + "/" + URLEncoder.encode(href, "utf-8"));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(title, content);

        System.out.println(jsonObject.toJSONString());
        synchronized (ReConsumeTorrentConsumer.class) {
            super.doWrite(torrent, file, (jsonObject.toJSONString() + System.lineSeparator() ).getBytes(StandardCharsets.UTF_8));
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



    protected Map<String, String> getRequestHeader() {
        Map<String, String> header = super.getRequestHeader();
        header.remove("Host");
        header.put("Cookie", "sc_is_visitor_unique=rx13138842.1767238772.DE6B3E08987D43AB892B061B89111218.1.1.1.1.1.1.1.1.1; __51vcke__Je3cl9DbHvVd7Yzy=55849c1b-3fdd-5fa5-a010-e92b235d9cd7; __51vuft__Je3cl9DbHvVd7Yzy=1767238777532; __51uvsct__Je3cl9DbHvVd7Yzy=2; __vtins__Je3cl9DbHvVd7Yzy=%7B%22sid%22%3A%20%22b88fbca6-82b6-5dcd-bda3-a6c202bb1175%22%2C%20%22vd%22%3A%2017%2C%20%22stt%22%3A%204271115%2C%20%22dr%22%3A%2050984%2C%20%22expires%22%3A%201767253768993%2C%20%22ct%22%3A%201767251968993%7D");
        return header;

    }
}
