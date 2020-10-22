package net.dingyabin.crawl.producer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import javafx.util.Pair;
import net.dingyabin.crawl.context.Holder;
import net.dingyabin.crawl.model.Torrent;
import net.wecash.utils.HTTPClient;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.*;
import java.util.concurrent.BlockingQueue;

import static net.dingyabin.crawl.producer.CaiTaZhiJiaProducer.coiunt;

/**
 * Created by MrDing
 * Date: 2018/7/28.
 * Time:23:57
 */
public class YueLiangProducer extends AbstractTorrentProducer {

    private String url = "https://yl668s.com/api/content/findNewMediaList";


    public YueLiangProducer(BlockingQueue<Torrent> queue, String encoding, int pageNumber) {
        super(queue, encoding, pageNumber);
    }


    @Override
    protected String getUrl() {
        return this.url;
    }


    @Override
    protected int getConnTimeOut() {
        return 60000;
    }


    @Override
    protected Map<String, String> getRequestHeader() {
        Map<String, String> requestHeader = new HashMap<>();
        requestHeader.put("X-Channel", "h5");
        requestHeader.put("Host", "yl668s.com");
        requestHeader.put("Content-Type", "application/json");
        return requestHeader;
    }

    @Override
    public void run() {
        try {
            List<String> contentIds = getContentIds();
            List<Pair<String, String>> contentDetails = getContentDetails(contentIds);

            for (Pair<String, String> contentDetail : contentDetails) {
                String contentTitle = contentDetail.getKey();
                String contentVideoUrl = contentDetail.getValue();
                System.out.println(contentVideoUrl);


                String resource = getResource(contentVideoUrl);
                String prefix = contentVideoUrl.substring(0, contentVideoUrl.lastIndexOf("/"));

                byte[] bytes = (prefix + "\n\r" + resource).getBytes();

                pushTorrent(new Torrent(contentTitle, bytes));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private List<String> getContentIds() {
        Map<String, String> requestHeader = getRequestHeader();
        Header[] headers = new BasicHeader[requestHeader.size()];
        int index = 0;
        for (Map.Entry<String, String> stringStringEntry : requestHeader.entrySet()) {
            String key = stringStringEntry.getKey();
            String value = stringStringEntry.getValue();
            Header header = new BasicHeader(key, value);
            headers[index++] = header;
        }

        Holder.lock();
        String lastId = Holder.lastId;


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pageNum", getPageNumber());
        jsonObject.put("pageSize", 10);
        jsonObject.put("subCategoryId", 2);
        jsonObject.put("lastId", lastId);
        jsonObject.put("userId", null);
        String params = jsonObject.toJSONString();
        String string = HTTPClient.postJson(getUrl(), params, headers);
        JSONObject parseObject = JSONObject.parseObject(string);
        if (parseObject == null) {
            return Collections.emptyList();
        }
        JSONArray jsonArray = parseObject.getJSONObject("data").getJSONArray("rows");
        List<String> list = Lists.newArrayList();
        String tempLastId = "";
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject row = jsonArray.getJSONObject(i);
            tempLastId = row.getString("id");
            String contentId = row.getString("contentId");
            list.add(contentId);
        }
        Holder.lastId = tempLastId;
        Holder.unlock();

        return list;
    }


    private List<Pair<String, String>> getContentDetails(List<String> contentIds) {
        List<Pair<String, String>> pairs = new ArrayList<>();
        for (String contentId : contentIds) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("contentId", contentId);
            String string = HTTPClient.postJson("https://yl668s.com/api/content/contentDetail", jsonObject.toJSONString());
            JSONObject parseObject = JSONObject.parseObject(string);
            if (parseObject == null) {
                continue;
            }
            JSONObject data = parseObject.getJSONObject("data");
            String contentTitle = data.getString("contentTitle");
            String contentVideoUrl = data.getString("contentVideoUrl");
            pairs.add(new Pair<>(contentTitle, contentVideoUrl));
        }
        return pairs;
    }


    @Override
    protected List<Torrent> makeTorrent(String resource) {
        return null;
    }
}
