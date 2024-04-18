package net.dingyabin.crawl.producer;

import cn.hutool.core.text.UnicodeUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import lombok.Getter;
import lombok.Setter;
import net.dingyabin.crawl.model.Torrent;
import netscape.javascript.JSObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:22:57
 */
public class Tai9TorrentProducer extends AbstractTorrentProducer {

    private static String baseUrl = "https://t90931.xyz:9388/";

    private String url = baseUrl + "category?category_id=135&category_child_id=&page=%s&limit=30";

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(3);

    public Tai9TorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
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
            List<String> videoDetailUrl = getVideoDetailUrl(doc);
            if (CollectionUtils.isEmpty(videoDetailUrl)) {
                return list;
            }
            for (String url : videoDetailUrl) {
                System.out.println("开始------------------" + getPageNumber() + "----------------- "  +  url);
                String videoDetailPage = getResource(baseUrl + url);
                ResourceMsg video = getVideoMeate(videoDetailPage);
                if (video != null) {
                    byte[] content = String.format("%s \t %s \n", video.getTitle(), video.getUrl()).getBytes();
                    pushTorrent(new Torrent("测试", content, true));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }







    private List<String> getVideoDetailUrl(Document doc) {
        Elements ranBoxs = doc.getElementsByClass("ran-box");
        Optional<Element> ranBoxOp = ranBoxs.stream().findFirst();
        if (!ranBoxOp.isPresent()) {
            return Collections.emptyList();
        }
        Elements divs = ranBoxOp.get().getElementsByAttributeValueStarting("class", "vs_");
        List<String> list = Lists.newArrayList();
        for (Element div : divs) {
            Elements atag = div.getElementsByTag("a");
            if (Objects.isNull(atag)) {
                continue;
            }
            list.add(atag.attr("href"));
        }
        return list;
    }



    private ResourceMsg getVideoMeate(String doc) {
        try {
            if (StringUtils.isBlank(doc)) {
                return null;
            }
            String base64 = StringUtils.substringBetween(doc, "JSON.parse(atob(\"", "\"));");
            String videObject = new String(Base64.getDecoder().decode(base64));
            if (StringUtils.isBlank(videObject)){
                return null;
            }

            JSONObject jsonObject = JSONObject.parseObject(videObject);
            String title = jsonObject.getString("title");
            if (StringUtils.isNotBlank(title)){
                title = UnicodeUtil.toString(title);
            }
            String sl = jsonObject.getString("sl");
            if (StringUtils.isBlank(sl)){
                return null;
            }


            String realVideo = getResource(sl);
            if (StringUtils.isBlank(realVideo)){
                return null;
            }
            String videoUrl = StringUtils.substringAfter(realVideo, "/");
            videoUrl = getBaseUrl(sl) + "/"  + videoUrl;
            return new ResourceMsg(videoUrl, title);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    @Override
    protected boolean needRetry() {
        return false;
    }



    @Override
    protected RateLimiter getRateLimiter() {
        return RATE_LIMITER;
    }



    /**
     * 获取baseUrl
     * @param longUrl longUrl
     * @return baseUrl
     */
    private String getBaseUrl(String longUrl) {
        try {
            URL url = new URL(longUrl);
            return url.getProtocol() + "://" + url.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "https://al1.zacuin.com";
    }



    @Getter
    @Setter
    private static class ResourceMsg{

        private String url;

        private String title;


        public ResourceMsg(String url, String title) {
            this.url = url;
            this.title = title;
        }
    }



//    public static void main(String[] args) {
//        String html = "\n" +
//                "<style>\n" +
//                "*{ margin:0px; padding:0px; list-style:none; color: #FFF; text-decoration:none}\n" +
//                ".wgm{ color:#FFF; text-align:center; margin-top:6x; }\n" +
//                ".wgm1{ font-size:18px; margin-bottom:6px; line-height:24px;}\n" +
//                ".wgm1 span{ display:block; font-size:14px}\n" +
//                ".wgm2{ font-size:16px; margin-bottom:6px; line-height:24px;}\n" +
//                ".wgm3{ font-size:14px; margin-bottom:6px; line-height:24px; color:#F00}\n" +
//                ".wgm4{ font-size:14px;  width:100px; margin:0 auto;  height:34px;line-height:34px; color:#fff; background:#F90; border-radius:10px;}\n" +
//                ".wgm4 a{color:#fff;}\n" +
//                "</style>\n" +
//                "<script type=\"text/javascript\" src=\"ckplayer/ckplayer.js\" charset=\"utf-8\"></script>\n" +
//                "<div id=\"a1\" style=\" position:relative; z-index:2222222; width:380px; height:220px;\"></div>\n" +
//                "    <script type=\"text/javascript\">\n" +
//                "        var videoObject = {\n" +
//                "            container: '#a1',//“#”代表容器的ID，“.”或“”代表容器的class\n" +
//                "            variable: 'player',//该属性必需设置，值等于下面的new chplayer()的对象\n" +
//                "            autoplay: false,\n" +
//                "            html5m3u8: true,\n" +
//                "            video: 'https://88.xn--bw2a68j.com/20240108/D18GBE5/5199kb/hls/index.m3u8?segments=5&time=12'        \n" +
//                "        };\n" +
//                "        var player = new ckplayer(videoObject);\n" +
//                "    </script>\n";
//
//        String videoUrl = StringUtils.substringAfter(html, "video:");
//        videoUrl = StringUtils.substring(videoUrl, videoUrl.indexOf("'")+1, videoUrl.lastIndexOf("'"));
//        System.out.println(videoUrl);;
//    }
}
