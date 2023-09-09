package net.dingyabin;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Administrator
 * Date: 2023/7/29.
 * Time:22:21
 */
public class Test2 {


//    public static void main(String[] args) throws Exception {
//        Stream<Path> walk = Files.walk(Paths.get("D:\\JJDown\\Download\\111111111"));
//        walk.filter(path -> Files.isRegularFile(path))
//                .forEach(path -> {
//                    String fileName = path.getFileName().toString();
//
//                    String formatName = StringUtils.substringAfterLast(fileName, " ");
//                    formatName = StringUtils.substringBefore(formatName, "(") + ".mp3";
//                    try {
//                        Path newPath = Paths.get("D:\\JJDown\\Download\\new2", formatName);
//                        System.out.println(newPath);
//                        Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//
//
//    }



//    public static void main(String[] args) throws Exception {
//        Stream<Path> walk = Files.walk(Paths.get("G:\\Music"));
//        Set<Path> pathSet = walk.filter(path -> Files.isRegularFile(path)).collect(Collectors.toSet());
//        int size = (int) (pathSet.size() * 0.6);
//
//        int index = 0;
//        for (Path path : pathSet) {
//            String fileName = path.getFileName().toString();
//            try {
//                Path newPath = Paths.get("G:\\Music02\\", fileName);
//                System.out.println(index + "---" + newPath);
//                Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if(++index == size){
//                return;
//            }
//        }
//
//    }



    public static void main(String[] args) throws Exception {
        Stream<Path> walk = Files.walk(Paths.get("F:\\歌曲\\歌曲下载"));
        Set<String> download = walk.filter(path -> Files.isRegularFile(path)).map(path -> {
            String fileName = path.getFileName().toString();
            fileName = StringUtils.substringBefore(fileName, ".");
            if (fileName.contains("-")){
                fileName = StringUtils.substringBefore(fileName, "-").trim();
            }
            fileName = fileName.replaceAll("，","");
            return fileName;
        }).collect(Collectors.toSet());


        Stream<Path> walk2 = Files.walk(Paths.get("F:\\歌曲\\音乐1"));
        Set<String> old = walk2.filter(path -> Files.isRegularFile(path)).map(path -> {
            String fileName = path.getFileName().toString();
            fileName = StringUtils.substringBefore(fileName, ".");
            return fileName;
        }).collect(Collectors.toSet());


        old.removeAll(download);

        for (String s : old) {
            System.out.println(s);
        }
    }




}
