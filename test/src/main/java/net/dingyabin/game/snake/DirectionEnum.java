package net.dingyabin.game.snake;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created by MrDing
 * Date: 2020/7/12.
 * Time:3:35
 */
public enum DirectionEnum {
    UP(KeyEvent.VK_UP) {
        @Override
        public void moveHead(SnakeElement head, int distasnce) {
            head.setY(head.getY() - distasnce);
        }
        @Override
        public ImageIcon getHeadImageIcon() {
            return ImageIcons.UP;
        }
    },
    DOWN(KeyEvent.VK_DOWN) {
        @Override
        public void moveHead(SnakeElement head, int distasnce) {
            head.setY(head.getY() + distasnce);
        }
        @Override
        public ImageIcon getHeadImageIcon() {
            return ImageIcons.DOWN;
        }
    },
    LEFT(KeyEvent.VK_LEFT) {
        @Override
        public void moveHead(SnakeElement head, int distasnce) {
            head.setX(head.getX() - distasnce);
        }
        @Override
        public ImageIcon getHeadImageIcon() {
            return ImageIcons.LEFT;
        }
    },
    RIGHT(KeyEvent.VK_RIGHT) {
        @Override
        public void moveHead(SnakeElement head, int distasnce) {
            head.setX(head.getX() + distasnce);
        }
        @Override
        public ImageIcon getHeadImageIcon() {
            return ImageIcons.RIGHT;
        }
    };

    private int keyCode;

    DirectionEnum(int keyCode) {
        this.keyCode = keyCode;
    }


    public int getKeyCode() {
        return keyCode;
    }

    public abstract void moveHead(SnakeElement head, int distasnce);

    public abstract ImageIcon getHeadImageIcon();


    public static DirectionEnum getDirection(int keyCode) {
        for (DirectionEnum directionEnum : values()) {
            if (directionEnum.getKeyCode() == keyCode) {
                return directionEnum;
            }
        }
        return null;
    }

}
