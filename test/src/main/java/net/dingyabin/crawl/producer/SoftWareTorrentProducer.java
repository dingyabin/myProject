package net.dingyabin.crawl.producer;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import net.dingyabin.crawl.model.Torrent;
import net.wecash.utils.HTTPClient;
import org.apache.commons.lang3.StringUtils;
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

    private Pattern subTitlePompile = Pattern.compile(">([a-zA-Z0-9_\\u4e00-\\u9fa5]+?[&nbsp;]*?)</");


    public SoftWareTorrentProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
    }


    @Override
    protected String getUrl() {
        return this.url;
    }




    @Override
    protected List<Torrent> makeTorrent(String resource) {
        try {
            Document doc = Jsoup.parse(resource);
            Elements tbodys = doc.getElementsByTag("tbody");

            int fff=0;
            for (int i = 1; i < tbodys.size(); i++) {
                Element tbody = tbodys.get(i);
                Elements tds = tbody.getElementsByTag("td");
                for (Element td : tds) {
                    if (td.html().length() < 15) {
                        continue;
                    }
                    String colspan = td.attr("colspan");
                    if ("3".equals(colspan) && "center".equals(td.attr("align"))) {
                        String html = td.getElementsByAttributeValueContaining("style", "rgb(0, 0, 0)").html().replaceAll("&nbsp;", "");
                        System.out.println("大标题: " + html);
                        pushTorrent(new Torrent("soft", JSONObject.toJSONString(ImmutableMap.of("name", html)).getBytes(),true));
                        continue;
                    }
                    Elements aTags = td.getElementsByTag("a");
                    if (aTags == null || aTags.isEmpty()) {
                        Matcher matcher = subTitlePompile.matcher(td.html());
                        String subTitle = StringUtils.EMPTY;
                        if (matcher.find()) {
                            subTitle = matcher.group(1).replaceAll("&nbsp;", "");
                            System.out.println("小标题: " + subTitle);
                        }
                        pushTorrent(new Torrent("soft", JSONObject.toJSONString(ImmutableMap.of("name", subTitle)).getBytes(),true));
                        continue;
                    }

                    JSONObject jsonObject = new JSONObject();

                    Element atag = aTags.get(0);
                    String href = atag.attr("href");
                    String textvalue = atag.attr("textvalue");
                    System.out.println(textvalue + " : " + href);
                    jsonObject.put("name", textvalue);

                    Document _doc = Jsoup.parse(HTTPClient.get(href));
                    String html = _doc.html();
                    //提取下载url
                    findUrl(jsonObject, html);
                    //提取验证码
                    findCode(jsonObject, html);
                    //获取安装方法
                    findOperation(jsonObject, html);
                    //载入下载队列
                    pushTorrent(new Torrent("soft", jsonObject.toJSONString().getBytes(),true));
                    //休眠2s防止太频繁
                    Thread.sleep(2000);
                    fff++;
                    if (fff ==50) {
                        return null;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    private void findUrl(JSONObject jsonObject, String html) {
        List<String> urls = new ArrayList<>();
        Matcher matcher = baiduPompile.matcher(html);
        while (matcher.find()){
            String group = matcher.group(1);
            urls.add(group);
            System.out.println("地址:    " + group);
        }
        jsonObject.put("url", String.join("\n", urls));
    }





    private void findCode(JSONObject jsonObject, String html) {
        List<String> codes = new ArrayList<>();
        Matcher matcherCode = codePompile.matcher(html);
        while (matcherCode.find()){
            String group = matcherCode.group(1);
            System.out.println("提取码:    "+group);
            codes.add(group);
        }
        jsonObject.put("code", String.join("\n", codes));
    }



    private void findOperation(JSONObject jsonObject, String html){

    }




    @Override
    public void run() {
        try {
            String resource = getResource();
            if (StringUtils.isBlank(resource)) {
                System.out.println("xxxxxxxxxxparseHome,第1页空白,跳过xxxxxxxxxxx");
                return;
            }
            makeTorrent(resource);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
