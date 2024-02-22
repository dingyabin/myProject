package net.dingyabin;

import com.google.common.base.Splitter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Administrator
 * Date: 2023/7/29.
 * Time:22:21
 */
public class Test2 {


//    public static void main(String[] args) throws Exception {
//        Stream<Path> walk = Files.walk(Paths.get("D:\\JJDown\\Download\\3"));
//        walk.filter(path -> Files.isRegularFile(path))
//                .forEach(path -> {
//                    //1.苏星婕-听悲伤的情歌(Av787126370,P1).mp3
//                    String fileName = path.getFileName().toString();
//                    fileName = fileName.replace("《","").replace("》","");
//
//                    String formatName = StringUtils.substringAfter(fileName, ".");
//                    formatName = StringUtils.substringBefore(formatName, "(");
//
//                    if (formatName.contains("-")){
//                        String[] split = formatName.split("-");
//                        ArrayUtils.reverse(split);
//                        formatName = String.join("-",split) ;
//                    }
//                    formatName += ".mp3";
//
//                    try {
//                        Path newPath = Paths.get("D:\\JJDown\\Download\\new_1", formatName);
//                        System.out.println(newPath);
//                        Files.move(path, newPath, StandardCopyOption.REPLACE_EXISTING);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//    }


//    public static void main(String[] args) throws Exception {
//        Map<String, Path> yibyue1Map = new HashMap<>();
//        Stream<Path> yibyue1 = Files.walk(Paths.get("F:\\音乐\\音乐1"));
//        yibyue1.filter(path -> Files.isRegularFile(path))
//                .forEach(path -> {
//                    String name = path.getFileName().toString();
//                    if (name.contains("-")) {
//                        yibyue1Map.put(StringUtils.substringBefore(name, "-").trim(), path);
//                    } else {
//                        yibyue1Map.put(StringUtils.substringBefore(name, ".").trim(), path);
//                    }
//                });
//
//
//
//        Map<String, Path> yibyue2Map = new HashMap<>();
//        Stream<Path> yibyue2 = Files.walk(Paths.get("F:\\音乐\\音乐2"));
//        yibyue2.filter(path -> Files.isRegularFile(path))
//                .forEach(path -> {
//                    String name = path.getFileName().toString();
//                    yibyue2Map.put(StringUtils.substringBefore(name, ".").trim(), path);
//                });
//
//
//        for (Map.Entry<String, Path> stringPathEntry1 : yibyue1Map.entrySet()) {
//            if (yibyue2Map.containsKey(stringPathEntry1.getKey())) {
//                Path path1 = stringPathEntry1.getValue();
//                Path path2 = yibyue2Map.get(stringPathEntry1.getKey());
//                Path small = path1.toFile().length() < path2.toFile().length() ? path1 : path2;
//                System.out.println( path1 +"----" + path2+"---" +  small);
//            }
//        }
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
//            }Files
//            if(++index == size){
//                return;
//            }
//        }
//
//    }


//    public static void main(String[] args) throws Exception {
//        Stream<Path> walk = Files.walk(Paths.get("F:\\歌曲\\歌曲下载"));
//        Set<String> download = walk.filter(path -> Files.isRegularFile(path)).map(path -> {
//            String fileName = path.getFileName().toString();
//            fileName = StringUtils.substringBefore(fileName, ".");
//            if (fileName.contains("-")){
//                fileName = StringUtils.substringBefore(fileName, "-").trim();
//            }
//            fileName = fileName.replaceAll("，","");
//            return fileName;
//        }).collect(Collectors.toSet());
//
//
//        Stream<Path> walk2 = Files.walk(Paths.get("F:\\歌曲\\音乐1"));
//        Set<String> old = walk2.filter(path -> Files.isRegularFile(path)).map(path -> {
//            String fileName = path.getFileName().toString();
//            fileName = StringUtils.substringBefore(fileName, ".");
//            return fileName;
//        }).collect(Collectors.toSet());
//
//
//        old.removeAll(download);
//
//        for (String s : old) {
//            System.out.println(s);
//        }
//    }


    public static void main(String[] args) throws IOException {
        Stream<Path> walk = Files.walk(Paths.get("F:\\IE下载\\下载测试\\漫漫 女神战靴"));
        List<Path> collect = walk.filter(path -> Files.isRegularFile(path)).sorted(Comparator.comparingInt(o -> Integer.parseInt(FilenameUtils.getBaseName(o.getFileName().toString())))).collect(Collectors.toList());

        RandomAccessFile raf = new RandomAccessFile( "F:\\IE下载\\下载测试\\join.mp4","rw");


        long count  = 0L;

        byte[] bytes;
        for (Path path : collect) {

            raf.seek(count);


            bytes = Files.readAllBytes(path);
            count += bytes.length;

            raf.write(bytes);

        }
        raf.close();
    }

}
