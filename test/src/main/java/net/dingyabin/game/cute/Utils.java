package net.dingyabin.game.cute;

import net.dingyabin.game.cute.abstracts.BaseCute;
import net.dingyabin.game.cute.certain.*;

import java.util.Random;

/**
 * Created by MrDing
 * Date: 2020/7/15.
 * Time:22:48
 */
public class Utils {

    private static final Class[] BASE_CUTES = {ACute.class, BCute.class, DCute.class, ECute.class, FCute.class};


    public static BaseCute randomCute() {
        try {
            return ((BaseCute) BASE_CUTES[new Random().nextInt(BASE_CUTES.length)].newInstance()).init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ACute().init();
    }


}
