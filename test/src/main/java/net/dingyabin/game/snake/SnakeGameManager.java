package net.dingyabin.game.snake;

import javax.swing.*;
import java.awt.*;

/**
 * Created by MrDing
 * Date: 2020/7/12.
 * Time:13:42
 */
public class SnakeGameManager {

    public static final int WIDTH = 900;

    public static final int HEIGHT = 900;

    public static final int STEP = ImageIcons.BODY.getIconHeight();

    public static final int TITLE = ImageIcons.HEADER.getIconHeight();


    public static void main(String[] args) {
        JFrame jFrame = new JFrame("贪吃蛇");
        jFrame.setResizable(false);
        //窗口居中
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setBounds((int)(screenSize.getWidth() - WIDTH) / 2, (int)(screenSize.getHeight() - HEIGHT) / 2, WIDTH, HEIGHT);
        //关闭动作
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //面板
        SnakeGamePanel snakeGamePanel = new SnakeGamePanel();
        jFrame.add(snakeGamePanel);
        //显示
        jFrame.setVisible(true);

    }





}
