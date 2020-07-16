package net.dingyabin.game.cute;

import com.google.common.collect.HashMultimap;
import net.dingyabin.game.cute.abstracts.BaseCute;
import net.dingyabin.game.cute.constants.ImageIcons;
import net.dingyabin.game.snake.SnakeGameManager;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.*;

/**
 * Created by MrDing
 * Date: 2020/7/14.
 * Time:22:37
 */
public class CutePanel extends JPanel {


    private Timer timer;

    private BaseCute current;

    private BaseCute next;

    private int score;

    private boolean over;

    private boolean start;

    private HashMultimap<Integer, CuteUnit> cuteContainer = HashMultimap.create();


    private void init() {
        score = 0;
        over = false;
        //默认暂停
        start = false;
        cuteContainer.clear();
        current = Utils.randomCute();
        next = Utils.randomCute();
        this.setFocusable(true);
    }


    /**
     * 更新下一个下落的方块
     */
    private void startNext(){
        setCurrent(getNext());
        setNext(Utils.randomCute());
    }


    public CutePanel() {
        super();
        init();
        this.setBackground(new Color(137, 149, 121));
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    if (over) {
                        init();
                        over = false;
                    } else {
                        start = !start;
                    }
                    repaint();
                }
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    current.rotate();
                }
                if (e.getKeyCode() == KeyEvent.VK_LEFT && canLeft()) {
                    current.left();
                }
                if (e.getKeyCode() == KeyEvent.VK_RIGHT && canRight()) {
                    current.right();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (canDown()) {
                        current.down();
                    }
                }
                repaint();
            }
        });


        timer = new Timer(500, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!start ||over){
                    return;
                }
                if (!canDown()) {
                    //处理消除行
                    handleDisAppare();
                    //刷新下一个
                    startNext();
                } else {
                    current.down();
                }
                //检测是否结束游戏
                isOver();
                //重画
                repaint();
            }
        });
        timer.start();

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //侧边栏
        g.setColor(new Color(149, 191, 147));
        g.fillRect(0, 0, CuteGameManager.WIDTH, CuteGameManager.HEIGHT);

        //画当前方块
        for (CuteUnit cuteUnit : getCurrent().getCuteUnits()) {
            ImageIcons.CUTE.paintIcon(this, g, cuteUnit.getX(), cuteUnit.getY());
        }
        //画下面的堆
        for (CuteUnit unit : cuteContainer.values()) {
            ImageIcons.CUTE.paintIcon(this, g, unit.getX(), unit.getY());
        }

        //画积分
        g.setColor(new Color(201, 50, 33));
        g.setFont(new Font("微软雅黑", Font.BOLD, 35));
        g.drawString("得分:" + score, CuteGameManager.WIDTH + CuteGameManager.BAR / 4, CuteGameManager.BAR / 3);

        //画侧边栏下一个
        for (CuteUnit cuteUnit : getNext().getCuteUnits()) {
            ImageIcons.CUTE.paintIcon(this, g, cuteUnit.getX() + 800, cuteUnit.getY()+400);
        }

        //暂停提示
        if (!start){
            g.setColor(new Color(201, 45, 55));
            g.setFont(new Font("微软雅黑", Font.BOLD, 50));

            g.drawString(
                    "点击空格键开始/暂停游戏",
                    (SnakeGameManager.WIDTH - g.getFont().getSize() * 12) / 2,
                    SnakeGameManager.HEIGHT / 2
            );
        }
        //游戏结束
        if (over){
            g.setColor(new Color(201, 45, 55));
            g.setFont(new Font("微软雅黑", Font.BOLD, 50));
            g.drawString(
                    "游戏失败 Game Over!",
                    (SnakeGameManager.WIDTH - g.getFont().getSize() * 12) / 2,
                    SnakeGameManager.HEIGHT / 2
            );
        }

    }


    /**
     * 是否还能继续下落
     * @return true 能
     */
    public boolean canDown(){
        for (CuteUnit cuteUnit : getCurrent().getCuteUnits()) {
            Set<CuteUnit> baseCutes = cuteContainer.get(cuteUnit.getY() + CuteGameManager.STEP);
            if (baseCutes.contains(new CuteUnit(cuteUnit.getX(),cuteUnit.getY() + CuteGameManager.STEP))){
                return false;
            }
            if (cuteUnit.getY() + CuteGameManager.STEP >= CuteGameManager.WIDTH){
                return false;
            }
        }
        return true;
    }


    /**
     * 是否可以向左
     * @return true 是
     */
    public boolean canLeft(){
        for (CuteUnit cuteUnit : current.getCuteUnits()) {
            Set<CuteUnit> cuteUnits = cuteContainer.get(cuteUnit.getY());
            CuteUnit toBeChecked = new CuteUnit(cuteUnit.getX() - CuteGameManager.STEP, cuteUnit.getY());
            if (cuteUnit.getX() <= 0 || cuteUnits.contains(toBeChecked)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 是否可以向右
     * @return true 是
     */
    public boolean canRight(){
        for (CuteUnit cuteUnit : current.getCuteUnits()) {
            Set<CuteUnit> cuteUnits = cuteContainer.get(cuteUnit.getY());
            CuteUnit toBeChecked = new CuteUnit(cuteUnit.getX() + CuteGameManager.STEP, cuteUnit.getY());
            if (cuteUnit.getX() + CuteGameManager.STEP >= CuteGameManager.WIDTH || cuteUnits.contains(toBeChecked)) {
                return false;
            }
        }
        return true;
    }



    public void isOver(){
        Set<Integer> integers = cuteContainer.keySet();
        if (integers.size() >= CuteGameManager.HEIGHT / CuteGameManager.STEP) {
            over = true;
        }
    }



    /**
     * 当不能继续下落时，处理已经满了，能够消除的行
     */
    public void handleDisAppare(){
        for (CuteUnit cuteUnit : current.getCuteUnits()) {
            cuteContainer.put(cuteUnit.getY(), cuteUnit);
        }
        SortedSet<Integer> disAppareRows = new TreeSet<>(Comparator.naturalOrder());
        for (CuteUnit cuteUnit : current.getCuteUnits()) {
            Set<CuteUnit> baseCutes = cuteContainer.get(cuteUnit.getY());
            //cuteUnit.getY()行满了，消除他
            if (baseCutes.size() >= CuteGameManager.WIDTH / CuteGameManager.STEP) {
                disAppareRows.add(cuteUnit.getY());
                cuteContainer.removeAll(cuteUnit.getY());
                score += 10;
            }
        }
        //没有消除的行
        if (disAppareRows.isEmpty()){
            return;
        }
        //消除之后，消除行上面的行要下移
        for (Integer integer : disAppareRows) {
            Optional<Integer> min = cuteContainer.keySet().stream().min(Integer::compareTo);
            if (!min.isPresent()){
                return;
            }
            for (int i = integer - CuteGameManager.STEP; i >= min.get(); i -= CuteGameManager.STEP) {
                Set<CuteUnit> cuteUnits = cuteContainer.get(i);
                if (cuteUnits.isEmpty()) {
                    continue;
                }
                cuteUnits.forEach(unit -> unit.setY(unit.getY() + CuteGameManager.STEP));
                cuteContainer.putAll(i + CuteGameManager.STEP, cuteUnits);
                cuteContainer.removeAll(i);
            }
        }
    }




    public BaseCute getCurrent() {
        return current;
    }

    public void setCurrent(BaseCute current) {
        this.current = current;
    }

    public BaseCute getNext() {
        return next;
    }

    public void setNext(BaseCute next) {
        this.next = next;
    }
}
