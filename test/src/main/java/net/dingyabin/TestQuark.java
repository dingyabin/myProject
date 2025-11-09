package net.dingyabin;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class TestQuark {


    public static void main(String[] args) throws IOException, InterruptedException {
        String string = IOUtils.toString(TestQuark.class.getResourceAsStream("/file.json"), StandardCharsets.UTF_8);
        JSONObject jsonObject = JSONObject.parseObject(string);
        JSONArray jsonArray = jsonObject.getJSONObject("data").getJSONArray("list");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject data = jsonArray.getJSONObject(i);
            String fid = data.getString("fid");
            String name = data.getString("file_name");

            String prefix = StringUtils.substringBeforeLast(name, ".");
            prefix = prefix.replaceAll("骚","")
                    .replaceAll("操","")
                    .replaceAll("穴","")
                    .replaceAll("射","")
                    .replaceAll("精","")
                    .replaceAll("淫","")
                    .replaceAll("肉","")
                    .replaceAll("棒","")
                    .replaceAll("白浆","")
                    .replaceAll("逼","")
                    .replaceAll("液","");

            name = prefix + ".exe";

            JSONObject param = new JSONObject();
            param.put("fid", fid);
            param.put("file_name", name);

            String body = HttpRequest.post("https://drive-pc.quark.cn/1/clouddrive/file/rename?pr=ucpro&fr=pc&uc_param_str=")
                    .body(param.toJSONString())
                    .header("cookie", IOUtils.toString(TestQuark.class.getResourceAsStream("/cookie.txt"), StandardCharsets.UTF_8))
                    .execute()
                    .body();
            System.out.println(body);

            Thread.sleep(500 + RandomUtils.nextInt(80, 200));
        }

    }


}
