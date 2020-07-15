package net.dingyabin.game.snake;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import java.io.IOException;

/**
 * Created by MrDing
 * Date: 2020/7/12.
 * Time:16:42
 */
public class SoundManager {

    private static AudioStream click;

    private static AudioStream over;

    static {
        try {
            click = new AudioStream(SoundManager.class.getResourceAsStream("/sound/click.WAV"));
            over = new AudioStream(SoundManager.class.getResourceAsStream("/sound/over.WAV"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void playSound(SoundType soundType) {
        switch (soundType) {
            case CLICK:
                AudioPlayer.player.start(click);
                break;
            case OVER:
                AudioPlayer.player.start(over);
                break;
        }
    }


    public enum SoundType{
        CLICK,

        OVER
    }

}
