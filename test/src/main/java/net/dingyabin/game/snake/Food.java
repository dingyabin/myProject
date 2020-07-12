package net.dingyabin.game.snake;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Random;

/**
 * Created by MrDing
 * Date: 2020/7/12.
 * Time:5:05
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Food {

    private int X;

    private int Y;

    /**
     * X：0-900
     * Y：100-900
     *
     */
    public Food refresh(){
        Random random = new Random();
        X = SnakeGameManager.STEP * (random.nextInt(SnakeGameManager.WIDTH / SnakeGameManager.STEP));
        Y = SnakeGameManager.TITLE + SnakeGameManager.STEP * random.nextInt((SnakeGameManager.HEIGHT - SnakeGameManager.TITLE) / SnakeGameManager.STEP);
        return this;
    }

}
