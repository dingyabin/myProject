package net.dingyabin.game.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2020/7/12.
 * Time:4:02
 */
public class SnakeGamePanel extends JPanel {

    private Snake snake;

    private Food food;

    private int startLength = 3;

    private Timer timer;

    private boolean start;

    private boolean over;

    private int score;


    private void initSnake(){
        List<SnakeElement> snakeElements = new ArrayList<>();
        //蛇头
        snakeElements.add(new SnakeElement(0, SnakeGameManager.TITLE));
        //身子
        for (int i = 1; i <= startLength; i++) {
            snakeElements.add(new SnakeElement(0 - SnakeGameManager.STEP, SnakeGameManager.TITLE));
        }
        snake = new Snake(DirectionEnum.RIGHT, snakeElements);
        food = new Food().refresh();
        //默认暂停
        start = false;
        //默认不结束
        over = false;
        //默认0分
        score = 0;
    }


    public SnakeGamePanel() {
        super();
        initSnake();
        this.setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (over) {
                        initSnake();
                        over = false;
                    } else {
                        start = !start;
                    }
                    repaint();
                    return;
                }
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
                DirectionEnum direction = DirectionEnum.getDirection(e.getKeyCode());
                if (direction == null) {
                    return;
                }
                snake.setDirection(direction);
            }
        });

        timer = new Timer(150, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!start || over) {
                    return;
                }
                snake.move(SnakeGameManager.STEP);
                //判断吃到食物没
                SnakeElement head = snake.getHead();
                if (head.getX() == food.getX() && head.getY() == food.getY()) {
                    //长一节身体
                    snake.grow();
                    //食物更新
                    food.refresh();
                    //更新得分
                    score += 10;
                }
                //判断超出边界
                snake.beyondWindowHandle(SnakeGameManager.TITLE, SnakeGameManager.HEIGHT, 0, SnakeGameManager.WIDTH);
                //是否碰到自己
                over = snake.touchSelf();
                //重画
                repaint();
            }
        });
        //启动刷新
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        //背景色
        this.setBackground(new Color(149, 191, 147));
        //标题图
        ImageIcons.HEADER.paintIcon(this, graphics, 0, 0);

        //画头
        SnakeElement head = snake.getHead();
        ImageIcon headImageIcon = snake.getDirection().getHeadImageIcon();
        headImageIcon.paintIcon(this, graphics, head.getX(), head.getY());

        //身子
        List<SnakeElement> snakeBody = this.snake.getSnake();
        for (int i = 1; i < snakeBody.size(); i++) {
            SnakeElement snakeElement = snakeBody.get(i);
            ImageIcons.BODY.paintIcon(this, graphics, snakeElement.getX(), snakeElement.getY());
        }

        //食物
        ImageIcons.FOOD.paintIcon(this, graphics, food.getX(), food.getY());


        //暂停提示
        if (!start){
            graphics.setColor(new Color(201, 45, 55));
            graphics.setFont(new Font("微软雅黑", Font.BOLD, 50));

            graphics.drawString(
                    "点击空格键开始/暂停游戏",
                    (SnakeGameManager.WIDTH - graphics.getFont().getSize() * 12) / 2,
                    SnakeGameManager.HEIGHT / 2
            );
        }
        //画积分
        graphics.setColor(new Color(51, 201, 18));
        graphics.setFont(new Font("华文行楷", Font.BOLD, 35));
        graphics.drawString("得分:" + score, 480, 13 + SnakeGameManager.TITLE / 2);

        //游戏结束
        if (over){
            graphics.setColor(new Color(201, 45, 55));
            graphics.setFont(new Font("微软雅黑", Font.BOLD, 50));
            graphics.drawString(
                    "游戏失败 Game Over!",
                    (SnakeGameManager.WIDTH - graphics.getFont().getSize() * 12) / 2,
                    SnakeGameManager.HEIGHT / 2
            );
        }
    }





}
