package net.dingyabin.crawl.producer;

import cn.hutool.http.HttpGlobalConfig;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import net.dingyabin.crawl.model.Torrent;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Collectors;

/**
 * Created by MrDing
 * Date: 2023/03/24.
 * Time:22:57
 */
public class CiLiCaoTorrentProducer extends AbstractTorrentProducer {

    private String url = "http://124.223.172.237:3535/list.php/?name=%E5%AE%87%E4%BD%90%E7%BE%8E&page=";

    private String magnetUrl = "http://124.222.194.190:3535/ajax.php";

    private static final RateLimiter RATE_LIMITER = RateLimiter.create(2);

    public CiLiCaoTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
        this.url = this.url + pageNumber;
    }


    @Override
    protected String getUrl() {
        return this.url;
    }


    @Override
    protected List<Torrent> makeTorrent(String resource) {
        try {
            Document doc = Jsoup.parse(resource);
            Element ul = doc.getElementById("wdc_index");
            if (ul == null) {
                return null;
            }
            Elements lis = ul.getElementsByTag("li");
            if (CollectionUtils.isEmpty(lis)) {
                return null;
            }
            for (Element li : lis) {
                Elements wdcDis = li.getElementsByClass("wdc_dis");
                Elements spans = wdcDis.get(0).getElementsByTag("span");
                if (CollectionUtils.isEmpty(spans)) {
                    continue;
                }
                String name = spans.get(0).html();

                Elements wdcP = li.getElementsByClass("wdc_p");
                if (CollectionUtils.isEmpty(wdcP)) {
                    continue;
                }
                Elements links = wdcP.get(0).getElementsByTag("a");
                if (CollectionUtils.isEmpty(links)) {
                    continue;
                }
                String onclick = links.get(2).attr("onclick");
                Pair<String, String> params = parseParams(onclick);

                String magnetLink = getMagnetLink(params.getLeft(), params.getRight());
                if (StringUtils.isBlank(magnetLink)) {
                    return null;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", name);
                jsonObject.put("magnet", "magnet:?xt=urn:btih:" + magnetLink);

                //载入下载队列
                pushTorrent(new Torrent("soft", jsonObject.toJSONString().getBytes(), true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    //fuzhi('2','ae5eb00b75793b64762c9dbbae50fb8d70baf50eb1326fb77c0905335ea40248')
    private Pair<String, String> parseParams(String onclick) {
        onclick = onclick.replaceAll("'", "");
        onclick = StringUtils.substringBetween(onclick, "(", ")");
        String[] split = onclick.split(",");
        if (ArrayUtils.getLength(split) < 2) {
            return Pair.of(null, null);
        }
        return Pair.of(split[0], split[1]);
    }



    private String getMagnetLink(String sjk, String hash) {
        try {
            if (StringUtils.isAnyBlank(sjk, hash)) {
                return null;
            }
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("typenum", "4");
            paramMap.put("sjk", sjk);
            paramMap.put("md5hash", hash);
            //限流
            RATE_LIMITER.acquire();
            HttpRequest httpRequest = HttpRequest.post(magnetUrl).form(paramMap).timeout(HttpGlobalConfig.getTimeout());
            getRequestHeader().forEach(httpRequest::header);
            String result = httpRequest.execute().body();
            if (StringUtils.isNotBlank(result) && result.startsWith("{")) {
                JSONObject jsonObject = JSONObject.parseObject(result);
                if ("0".equals(jsonObject.getString("code"))) {
                    System.out.println("获取MagnetLink失败：" + result);
                    return null;
                }
                return jsonObject.getString("info_hash");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    @Override
    public void run() {
        try {
            //限流
            RATE_LIMITER.acquire();
            String resource = getResource();
            if (StringUtils.isBlank(resource)) {
                System.out.println("xxxxxxxxxxparseHome,第" + getPageNumber() + "页空白,跳过xxxxxxxxxxx");
                return;
            }
            makeTorrent(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
