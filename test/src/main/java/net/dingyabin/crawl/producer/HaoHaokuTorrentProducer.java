package net.dingyabin.crawl.producer;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import net.dingyabin.crawl.model.Torrent;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by MrDing
 * Date: 2023/03/24.
 * Time:22:57
 */
public class HaoHaokuTorrentProducer extends AbstractTorrentProducer {

    private String baseUrl = "https://www.eefanhao.com";

    private String url = baseUrl + "/fanhao/";

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(2);


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
        header.put("Cookie", "__51vcke__Je3cl9DbHvVd7Yzy=501e42b8-fc3b-5a04-8f45-e33c93d4dfd3; __51vuft__Je3cl9DbHvVd7Yzy=1767013833357; sc_is_visitor_unique=rx13138842.1767453649.001C9DF1ED194D7CBFD89AC4E8B0F54E.3.3.3.3.3.3.2.2.1; __vtins__Je3cl9DbHvVd7Yzy=%7B%22sid%22%3A%20%22e816e4bc-4de4-5ef1-8ba1-c6fa1ebaed73%22%2C%20%22vd%22%3A%201%2C%20%22stt%22%3A%200%2C%20%22dr%22%3A%200%2C%20%22expires%22%3A%201767455454027%2C%20%22ct%22%3A%201767453654027%7D; __51uvsct__Je3cl9DbHvVd7Yzy=8");
        header.put("Origin", "https://www.eefanhao.com");
        return header;

    }

//    @Override
//    protected List<Torrent> makeTorrent(String resource) {
//        List<Torrent> list = new ArrayList<>();
//        try {
//            Document doc = Jsoup.parse(resource);
//            Element mianTag = doc.getElementsByTag("main").get(0);
//            Element tagCoulds = mianTag.getElementsByClass("tag_could").get(0);
//            Elements links = tagCoulds.getElementsByTag("a");
//            for (Element linkEle : links) {
//                String href = linkEle.attr("href");
//                String title = linkEle.attr("title");
//                System.out.println(href + " --------------" + title);
//                if (href.startsWith("/")) {
//                    href = StringUtils.substringAfter(href, "/");
//                }
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("title", title);
//                jsonObject.put("href", href);
//                list.add(new Torrent("测试文件22", jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8), true));
//                sleep(500);
//                return list;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }


//
//    @Override
//    protected List<Torrent> makeTorrent(String resource) {
//        List<Torrent> list = new ArrayList<>();
//        try {
//            Document doc = Jsoup.parse(resource);
//            Element ulList = doc.getElementsByClass("post-loop post-loop-default cols-4").first();
//            Elements li = ulList.getElementsByTag("li");
//            for (Element liItem : li) {
//                Element img = liItem.getElementsByTag("img").first();
//                Element h3 = liItem.getElementsByClass("item-title my-item-title").first();
//                if (Objects.isNull(h3)){
//                    continue;
//                }
//                Element linkEle = h3.getElementsByTag("a").first();
//                String title = linkEle.text();
//                JSONObject jsonObject = new JSONObject();
//                jsonObject.put("title", title);
//                jsonObject.put("href", "/fanhao/nvyou_"+ title + "_1.html");
//                if (Objects.nonNull(img)) {
//                    jsonObject.put("picture", baseUrl + img.attr("src"));
//                }
//                list.add(new Torrent("测试文件-图片", jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8), true));
//                System.out.println("====================="+jsonObject.toJSONString());
//                sleep(100);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return list;
//    }
//
//    protected String getResource() {
//        try {
//            //140页
//            Map<String, Object> paramMap = Maps.newHashMap();
//            paramMap.put("op", "getlist");
//            paramMap.put("page", getPageNumber());
//            paramMap.put("t1", "作品最多");
//            paramMap.put("t2", "");
//            HttpRequest httpRequest = HttpRequest.post("https://www.eefanhao.com/fhindex.php?tp=nylist").form(paramMap).timeout(HttpGlobalConfig.getTimeout());
//            getRequestHeader().forEach(httpRequest::header);
//            return httpRequest.execute().body();
//        } catch (HttpException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }




    @Override
    protected List<Torrent> makeTorrent(String resource) {
        List<Torrent> list = new ArrayList<>();
        try {
            Document doc = Jsoup.parse(resource);
            Element ulList = doc.getElementsByClass("post-loop post-loop-default cols-4").first();
            Elements li = ulList.getElementsByTag("li");
            for (Element liItem : li) {
                Element alink = liItem.getElementsByTag("a").first();

                if (Objects.isNull(alink)){
                    continue;
                }

                String title = alink.attr("title");
                String href = alink.attr("href");

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("title", title);
                jsonObject.put("href",  href);

                list.add(new Torrent("番号列表", jsonObject.toJSONString().getBytes(StandardCharsets.UTF_8), true));
                System.out.println("====================="+jsonObject.toJSONString());
                sleep(80);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    protected String getResource() {
        //140页
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("op", "getlist");
        paramMap.put("page", getPageNumber());
        paramMap.put("t1", "全部");
        paramMap.put("t2", "无MA");
        paramMap.put("t3", "默认");
        paramMap.put("t4", "");
        HttpRequest httpRequest = HttpRequest.post("https://www.eefanhao.com/fhindex.php?tp=fhlist").form(paramMap).timeout(HttpGlobalConfig.getTimeout());
        httpRequest.timeout(20000);
        getRequestHeader().forEach(httpRequest::header);
        try (HttpResponse execute = httpRequest.execute()) {
            return execute.body();
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected RateLimiter getRateLimiter() {
        return RATE_LIMITER;
    }
}
