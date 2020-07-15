package net.dingyabin.game.cute;

import com.google.common.collect.HashMultimap;
import net.dingyabin.game.cute.abstracts.BaseCute;
import net.dingyabin.game.cute.certain.ACute;
import net.dingyabin.game.cute.constants.ImageIcons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by MrDing
 * Date: 2020/7/14.
 * Time:22:37
 */
public class CutePanel extends JPanel {


    private Timer timer;

    private BaseCute current;

    private BaseCute next;

    private HashMultimap<Integer, CuteUnit> cuteContainer = HashMultimap.create();


    private void init() {
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
        this.setBackground(new Color(149, 191, 147));
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
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
                    if (!canDown()) {
                        handleDisAppare();
                        startNext();
                        return;
                    }
                    current.down();
                }
                repaint();
            }
        });


        timer = new Timer(1000, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!canDown()) {
                    handleDisAppare();
                    startNext();
                    return;
                }
                current.down();
                //重画
                repaint();
            }
        });
        timer.start();

    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //画当前方块
        for (CuteUnit cuteUnit : getCurrent().getCuteUnits()) {
            ImageIcons.CUTE.paintIcon(this, g, cuteUnit.getX(), cuteUnit.getY());
        }
        //画下面的堆
        for (CuteUnit unit : cuteContainer.values()) {
            ImageIcons.CUTE.paintIcon(this, g, unit.getX(), unit.getY());
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



    /**
     * 当不能继续下落时，处理已经满了，能够消除的行
     */
    public void handleDisAppare(){
        for (CuteUnit cuteUnit : current.getCuteUnits()) {
            cuteContainer.put(cuteUnit.getY(), cuteUnit);
        }
        for (CuteUnit cuteUnit : current.getCuteUnits()) {
            Set<CuteUnit> baseCutes = cuteContainer.get(cuteUnit.getY());
            //cuteUnit.getY()行满了，消除他
            if (baseCutes.size() >= CuteGameManager.WIDTH / CuteGameManager.STEP) {
                cuteContainer.removeAll(cuteUnit.getY());
            }
        }
        //消除之后，消除行上面的行要下移
        for (Integer integer : new HashSet<>(cuteContainer.keySet())) {
            if (integer >= cuteUnit.getY()) {
                continue;
            }
            Set<CuteUnit> cuteUnits = cuteContainer.get(integer);
            if (!cuteUnits.isEmpty()) {
                cuteUnits.forEach(unit -> unit.setY(unit.getY() + CuteGameManager.STEP));
                cuteContainer.removeAll(integer);
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
