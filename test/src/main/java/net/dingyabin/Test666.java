package net.dingyabin;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Administrator
 * Date: 2026/1/1.
 * Time:11:58
 */
public class Test666 {


    public static void main(String[] args) throws IOException {
        List<Map<String, String>> list = new ArrayList<>();
        LineIterator lineIterator = IOUtils.lineIterator(Files.newInputStream(Paths.get("E:\\测试文件-姓名.txt")), StandardCharsets.UTF_8);
        while (lineIterator.hasNext()) {
            String next = lineIterator.next();
            JSONObject jsonObject = JSONObject.parseObject(next);
            Optional<String> firstOp = jsonObject.keySet().stream().findFirst();
            if (!firstOp.isPresent()) {
                continue;
            }
            String firstKey = firstOp.get();
            JSONArray jsonArray = jsonObject.getJSONArray(firstKey);
            if (jsonArray.isEmpty()) {
                continue;
            }
            for (int i = 0; i < jsonArray.size(); i++) {
                if (i < list.size()) {
                    list.get(i).put(firstKey, jsonArray.getString(i));
                    continue;
                }
                Map<String, String> map = new HashMap<>();
                map.put(firstKey, jsonArray.getString(i));
                list.add(map);

            }
        }
        EasyExcel.write("E:\\姓名.xlsx").withTemplate("E:\\姓名模板.xlsx").sheet().doFill(list);
    }


    public static Set<String> findStorage(String filePath) {
        Set<String> set = new HashSet<>();
        try {
            LineIterator lineIterator = IOUtils.lineIterator(Files.newInputStream(Paths.get(filePath)), StandardCharsets.UTF_8);
            while (lineIterator.hasNext()) {
                String next = lineIterator.next();
                JSONObject jsonObject = JSONObject.parseObject(next);
                set.add(jsonObject.getString("title"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return set;
    }
}
