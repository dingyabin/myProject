package net.dingyabin.game.snake;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MrDing
 * Date: 2020/7/12.
 * Time:3:31
 */
@Getter
@Setter
public class SnakeElement {

    private int X;

    private int Y;

    public SnakeElement() {
    }

    public SnakeElement(int x, int y) {
        X = x;
        Y = y;
    }
}
