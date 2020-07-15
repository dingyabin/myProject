package net.dingyabin.game.cute.abstracts;

import lombok.Getter;
import lombok.Setter;
import net.dingyabin.game.cute.CuteGameManager;
import net.dingyabin.game.cute.CuteUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2020/7/14.
 * Time:22:08
 */
@Getter
@Setter
public abstract class BaseCute {

    protected List<CuteUnit> cuteUnits;

    protected CuteUnit centerCute;

    public BaseCute() {
        cuteUnits = new ArrayList<>();
    }

    public abstract BaseCute init();


    public synchronized void down() {
        for (CuteUnit cuteUnit : cuteUnits) {
            cuteUnit.setY(cuteUnit.getY() + CuteGameManager.STEP);
        }
    }

    public synchronized void left() {
        for (CuteUnit cuteUnit : cuteUnits) {
            cuteUnit.setX(cuteUnit.getX() - CuteGameManager.STEP);
        }
    }

    public synchronized  void right() {
        for (CuteUnit cuteUnit : cuteUnits) {
            cuteUnit.setX(cuteUnit.getX() + CuteGameManager.STEP);
        }
    }

    public synchronized void rotate() {
        int m = getCenterCute().getX();
        int n = getCenterCute().getY();
        for (CuteUnit cuteUnit : cuteUnits) {
            int a = cuteUnit.getX();
            int b = cuteUnit.getY();
            cuteUnit.setX(n + m - b);
            cuteUnit.setY(n - m + a);
        }
    }


}
