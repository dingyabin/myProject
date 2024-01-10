package net.dingyabin.crawl.producer;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.RateLimiter;
import lombok.Getter;
import lombok.Setter;
import net.dingyabin.crawl.model.Torrent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:22:57
 */
public class CaiTaDiYiTorrentProducer extends AbstractTorrentProducer {

    private static String baseUrl = "https://www.caita3456.com/";

    private String url = baseUrl + "video/index%s.html";

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(3);

    public CaiTaDiYiTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
        this.url = String.format(this.url, pageNumber > 1 ? ("_" + pageNumber) : "");
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
            List<ResourceMsg> homeSimpleMsg = getHomeSimpleMsg(doc);
            if (CollectionUtils.isEmpty(homeSimpleMsg)) {
                return null;
            }
            for (ResourceMsg resourceMsg : homeSimpleMsg) {
                String detailMsgSrc = getDetailIframMsgSrc(resourceMsg);
                String videoUrl = getVideoUrl(detailMsgSrc);
                if (StringUtils.isNotBlank(videoUrl)) {
                    byte[] content = String.format("%s \t %s \n", resourceMsg.getTitle(), videoUrl).getBytes();
                    list.add(new Torrent("测试", content, true));
                }
            }
            list.add(new Torrent("测试", ("------------------" + getPageNumber() + "----------------- \n").getBytes(), true));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    private List<ResourceMsg> getHomeSimpleMsg(Document doc) {
        Element ul = doc.getElementById("showajaxnews");
        if (ul == null) {
            return null;
        }
        Elements lis = ul.getElementsByTag("li");
        if (CollectionUtils.isEmpty(lis)) {
            return null;
        }
        List<ResourceMsg> objects = Lists.newArrayList();
        for (Element li : lis) {
            Elements as = li.getElementsByTag("a");
            if (CollectionUtils.isEmpty(as)) {
                continue;
            }
            Element aEle = as.get(0);
            objects.add(new ResourceMsg(baseUrl + aEle.attr("href"), aEle.attr("title")));
        }
        return objects;
    }




    private String getDetailIframMsgSrc(ResourceMsg resourceMsg) {
        //限流
        RATE_LIMITER.acquire();
        String html = getResource(resourceMsg.getUrl());
        if (StringUtils.isBlank(html)) {
            return null;
        }
        Document doc = Jsoup.parse(html);
        Element player = doc.getElementById("player");
        if (player == null) {
            return null;
        }
        Elements iframes = player.getElementsByTag("iframe");
        if (CollectionUtils.isEmpty(iframes)) {
            return null;
        }
        Element element = iframes.get(0);
        String src = element.attr("src");
        return baseUrl + StringUtils.substringBefore(src, "pathid") + "pathid=2";
    }




    private String getVideoUrl(String src) {
        //限流
        RATE_LIMITER.acquire();
        String html = getResource(src);
        if (StringUtils.isBlank(html) || !html.contains("video:")) {
            return null;
        }
        String videoUrl = StringUtils.substringAfter(html, "video:");
        videoUrl = StringUtils.substring(videoUrl, videoUrl.indexOf("'") + 1, videoUrl.lastIndexOf("'"));
        videoUrl = StringUtils.substringBefore(videoUrl, "?");
        return videoUrl;
    }



    @Override
    protected RateLimiter getRateLimiter() {
        return RATE_LIMITER;
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
