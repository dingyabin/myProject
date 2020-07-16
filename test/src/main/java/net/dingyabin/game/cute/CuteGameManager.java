package net.dingyabin.game.cute;



import net.dingyabin.game.cute.constants.ImageIcons;

import javax.swing.*;
import java.awt.*;

/**
 * Created by MrDing
 * Date: 2020/7/12.
 * Time:13:42
 */
public class CuteGameManager {

    public static final int WIDTH = 900;

    public static final int HEIGHT = 930;

    public static final int BAR = 300;

    public static final int STEP = ImageIcons.CUTE.getIconHeight();


    public static void main(String[] args) {
        JFrame jFrame = new JFrame("俄罗斯方块");
        jFrame.setResizable(false);
        //窗口居中
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setBounds((int) (screenSize.getWidth() - WIDTH) / 2, (int) (screenSize.getHeight() - HEIGHT) / 2, WIDTH + BAR, HEIGHT);
        //关闭动作
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //面板
        CutePanel cutePanel = new CutePanel();
        jFrame.add(cutePanel);
        //显示
        jFrame.setVisible(true);

    }





}
