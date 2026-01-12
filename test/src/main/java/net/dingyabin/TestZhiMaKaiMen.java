package net.dingyabin;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import net.dingyabin.crawl.utils.Utils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.Objects.isNull;

/**
 * @author Administrator
 * Date: 2026/1/1.
 * Time:11:58
 */
public class TestZhiMaKaiMen {

    private static final String URL = "https://test1xyz.zmkmapi.com/api/page/getvideolist";

//    private static final Pair<Integer, String> CONFIG = Pair.of(22, "日韩");
    private static final Pair<Integer, String> CONFIG = Pair.of(21, "国产");

    private static final File FILE = new File("E:\\芝麻开门.txt");

    private static final ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) throws IOException {
        Integer totalPage = null;
        for (int i = 1; i <= (isNull(totalPage) ? 1 : totalPage); i++) {
            String resource = getResource(i, 144, CONFIG.getLeft());
            if (StringUtils.isBlank(resource)) {
                System.out.printf("---------第%s页，获取失败....\r\n", i);
                continue;

            }
            JSONObject jsonObject = JSONObject.parseObject(resource);
            JSONObject data = jsonObject.getJSONObject("data");
            if (isNull(data)) {
                continue;
            }
            if (isNull(totalPage)) {
                totalPage = data.getInteger("totalPage");
            }
            JSONArray jsonArray = data.getJSONArray("list");
            for (int j = 0; j < jsonArray.size(); j++) {
                JSONObject dataObject = jsonArray.getJSONObject(j);
                String title = dataObject.getString("title");
                String pic = dataObject.getString("pic");
                long playtime = TimeUnit.SECONDS.toMinutes(dataObject.getIntValue("playtime"));
                System.out.println(title);

                Map<String, Object> content = new LinkedHashMap<>();
                content.put("title", title);
                content.put("pic", pic);
                content.put("playtime", playtime);
                content.put("Category", CONFIG.getRight());
                doWrite(FILE, JSONObject.toJSONString(content) + System.lineSeparator());
            }
            sleep(400);
        }

    }


    protected static String getResource(int pageNum, int pageSize, int channel) {
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("page", pageNum);
        paramMap.put("pageSize", pageSize);
        paramMap.put("channel", channel);
        HttpRequest httpRequest = HttpRequest.post(URL).body(JSONObject.toJSONString(paramMap)).timeout(20000);
        getRequestHeader().forEach(httpRequest::header);
        try (HttpResponse execute = httpRequest.execute()) {
            return execute.body();
        } catch (HttpException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected static Map<String, String> getRequestHeader() {
        Map<String, String> header = new HashMap<>();
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0");
        header.put("x-forwarded-for", Utils.getRandomIp());
        header.put("origin", "https://www.zmkm1.com");
        header.put("referer", "https://www.zmkm1.com/");
        return header;
    }


    protected static void doWrite(File file, String content) throws IOException {
        FileUtils.writeByteArrayToFile(file, content.getBytes(StandardCharsets.UTF_8), true);
    }


    protected static void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
