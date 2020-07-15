package net.dingyabin.game.cute;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Created by MrDing
 * Date: 2020/7/12.
 * Time:3:31
 */
@Getter
@Setter
public class CuteUnit {

    private int X;

    private int Y;

    public CuteUnit() {
    }

    public CuteUnit(int x, int y) {
        X = x;
        Y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CuteUnit cuteUnit = (CuteUnit) o;
        return X == cuteUnit.X && Y == cuteUnit.Y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(X, Y);
    }
}
