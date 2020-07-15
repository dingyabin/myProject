package net.dingyabin.game.snake;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2020/7/12.
 * Time:3:32
 */
@Getter
@Setter
public class Snake {

    private List<SnakeElement> snake;

    private DirectionEnum direction;

    public Snake() {
        this.snake = new ArrayList<>();
        this.direction = DirectionEnum.RIGHT;
    }

    public Snake(DirectionEnum direction, List<SnakeElement> snakeElements) {
        this.direction = direction;
        this.snake = new ArrayList<>(snakeElements);
    }


    public SnakeElement getHead(){
        return snake.get(0);
    }


    public void move(int distasnce) {
        for (int i = snake.size()-1; i > 0; i--) {
            SnakeElement snakeElement = snake.get(i);
            snakeElement.setX(snake.get(i-1).getX());
            snakeElement.setY(snake.get(i-1).getY());
        }
        direction.moveHead(snake.get(0), distasnce);
    }


    public void grow(){
        SnakeElement snakeElement = new SnakeElement();
        SnakeElement tail = snake.get(snake.size() -1);
        SnakeElement tpre = snake.get(snake.size() -2);
        if (tail.getX() < tpre.getX()) {
            snakeElement.setX(tail.getX() - 1);
            snakeElement.setY(tail.getY());
        }
        if (tail.getX() > tpre.getX()) {
            snakeElement.setX(tail.getX() + 1);
            snakeElement.setY(tail.getY());
        }
        if (tail.getY() < tpre.getY()) {
            snakeElement.setX(tpre.getX());
            snakeElement.setY(tpre.getY() - 1);
        }
        if (tail.getY() > tpre.getY()) {
            snakeElement.setX(tpre.getX());
            snakeElement.setY(tpre.getY() + 1);
        }
        this.snake.add(snakeElement);
    }


    public void beyondWindowHandle(int up, int down, int left, int right) {
        SnakeElement head = getHead();
        if (head.getY() < up) {
            head.setY(SnakeGameManager.HEIGHT - SnakeGameManager.STEP);
        }
        if (head.getY() > down) {
            head.setY(SnakeGameManager.TITLE);
        }
        if (head.getX() < left) {
            head.setX(SnakeGameManager.WIDTH - SnakeGameManager.STEP);
        }
        if (head.getX() >= right) {
            head.setX(0);
        }
    }


    public boolean touchSelf() {
        SnakeElement head = getHead();
        for (int i = 1; i < snake.size(); i++) {
            if (snake.get(i).getX() == head.getX() && snake.get(i).getY() == head.getY()) {
                return true;
            }
        }
        return false;
    }


}
