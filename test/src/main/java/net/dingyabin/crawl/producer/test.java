package net.dingyabin.crawl.producer;

import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author 丁亚宾
 * Date: 2022/1/29.
 * Time:11:48
 */
public class test {

    private static final String PATH = "E:\\test\\ss";

    private static final String MERGE_PATH = "E:\\test\\merge";


    public static void main(String[] args) {
        File file = new File(PATH);
        List<String> collect = Stream.of(file.listFiles()).map(File::getPath).filter(s -> s.endsWith(".ts")).sorted(Comparator.comparingInt(value -> Integer.parseInt(FilenameUtils.getBaseName(value)))).collect(Collectors.toList());

        List<String> mp4s = Lists.newArrayList();
        for (List<String> strings : Lists.partition(collect, 80)) {
            String name = UUID.randomUUID().toString();
            createScriptAndRun(file.getPath(), strings, name);
            mp4s.add(file.getPath() + File.separator + name + ".mp4");
        }

        createScriptAndRun(file.getPath(), mp4s,"final" );
    }





    private static void merge(List<String> collect){
        List<String> list = collect;
        do {
            List<String> mp4s = Lists.newArrayList();
            for (List<String> strings : Lists.partition(list, 80)) {
                String name = UUID.randomUUID().toString();
                createScriptAndRun(MERGE_PATH, strings, name);
                mp4s.add(MERGE_PATH + File.separator + name + ".mp4");
            }
            if (mp4s.size() > 80) {
                list = mp4s;
            } else {
                createScriptAndRun(MERGE_PATH, mp4s, "final");
                break;
            }
        } while (true);
    }




    private static List<String> createScriptAndRun(String moviePath, List<String> files, String movieName) {
        try {

            String basePath = moviePath + File.separator;

            String join = String.join("+", files);

            String scriptPath =  basePath + "run.bat";
            String scriptContent = String.format("copy/b  %s  %s%s.mp4", join, basePath, movieName);
            FileUtils.write(new File(scriptPath), scriptContent, "GBK");
            Process exec = Runtime.getRuntime().exec(scriptPath);
            InputStream inputStream = exec.getInputStream();
            List<String> lines = IOUtils.readLines(inputStream, "GBK");
            lines.forEach(System.out::println);
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

}
