package net.dingyabin;

import org.apache.commons.lang3.StringUtils;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Administrator
 * Date: 2023/7/29.
 * Time:22:21
 */
public class Test3 {


    public static void main(String[] args) throws Exception {
        Set<String> songs = findSongs();

        Robot robot = new Robot();
        robot.delay(2000);

        for (String song : songs) {
            //聚焦搜索框
            pressKey(robot, false, KeyEvent.VK_F1);
            //复制文本
            copy2System(song);
            //选中输入框中的文本
            pressKey(robot, true, KeyEvent.VK_CONTROL, KeyEvent.VK_A);
            //粘贴文本
            pressKey(robot, true, KeyEvent.VK_CONTROL, KeyEvent.VK_V);
            //回车
            pressKey(robot, true, KeyEvent.VK_ENTER);

            robot.delay(2000);

            pressLeftMouse(robot,1475,220);


            robot.delay(3000);
        }
    }


    private static void pressKey(Robot robot, boolean concurrent, int... keys) {
        for (int key : keys) {
            robot.keyPress(key);
            robot.delay(50);
            if (!concurrent) {
                robot.keyRelease(key);
            }
        }

        robot.delay(200);

        if (concurrent) {
            for (int key : keys) {
                robot.keyRelease(key);
            }
        }
    }


    private static void pressLeftMouse(Robot robot, int x ,int y) {
       // robot.mouseMove(x, y);
        robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        robot.delay(30);
        robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }


    private static void copy2System(String content) {
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(new StringSelection(content), null);
    }


    public static Set<String> findSongs() throws Exception {
        Stream<Path> walk = Files.walk(Paths.get("F:\\歌曲\\歌曲下载"));
        Set<String> download = walk.filter(path -> Files.isRegularFile(path)).map(path -> {
            String fileName = path.getFileName().toString();
            fileName = StringUtils.substringBefore(fileName, ".");
            if (fileName.contains("-")) {
                fileName = StringUtils.substringBefore(fileName, "-").trim();
            }
            fileName = fileName.replaceAll("，", "");
            return fileName;
        }).collect(Collectors.toSet());


        Stream<Path> walk2 = Files.walk(Paths.get("F:\\歌曲\\音乐1"));
        Set<String> old = walk2.filter(path -> Files.isRegularFile(path)).map(path -> {
            String fileName = path.getFileName().toString();
            fileName = StringUtils.substringBefore(fileName, ".");
            return fileName;
        }).collect(Collectors.toSet());
        old.removeAll(download);
        return old;
    }


}
