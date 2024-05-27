package net.dingyabin;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Scanner;

/**
 * @author 丁亚宾
 * Date: 2024/5/27.
 * Time:21:20
 */
public class TestNew {

    private static final String HEADER = "HEADER";


    public static void main(String[] args) throws IOException {

        LineIterator lineIterator = IOUtils.lineIterator(new FileInputStream("C:\\Users\\丁亚宾\\Desktop\\测试文档.txt"), "UTF-8");
        Map<String, String> objMap = Maps.newHashMap();
        String content = StringUtils.EMPTY;
        while (lineIterator.hasNext()) {
            String curline = lineIterator.nextLine();
            if (StringUtils.isBlank(curline)) {
                //上一行也是空行,已经处理过了
                if (StringUtils.isBlank(content)) {
                    continue;
                }
                objMap.put(getName(content), content);
                content = StringUtils.EMPTY;
            } else {
                content = content + curline + "\n";
                //如果是最后一行，写入map
                if (!lineIterator.hasNext() && StringUtils.isNotBlank(content)) {
                    objMap.put(getName(content), content);
                }
            }
        }

        try (PrintWriter printWriter = new PrintWriter(new FileOutputStream("C:\\Users\\丁亚宾\\Desktop\\处理后的文档.txt"))) {
            String header = objMap.get(HEADER);
            printWriter.println(header);
            for (Map.Entry<String, String> entry : objMap.entrySet()) {
                if (!HEADER.equals(entry.getKey())) {
                    printWriter.println(entry.getValue());
                    System.out.println("写入游戏 ： " + entry.getKey());
                }
            }
            printWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static String getName(String line) {
        if (line.startsWith("game:")) {
            String[] split = line.split("game:|file:");
            return split.length > 1 ? split[1].replaceAll("\n", "") : StringUtils.EMPTY;
        }
        if (line.startsWith("collection:")) {
            return HEADER;
        }
        return StringUtils.EMPTY;
    }


}
