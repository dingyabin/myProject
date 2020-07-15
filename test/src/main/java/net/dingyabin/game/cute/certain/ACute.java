package net.dingyabin.game.cute.certain;

import lombok.Getter;
import lombok.Setter;
import net.dingyabin.game.cute.CuteGameManager;
import net.dingyabin.game.cute.CuteUnit;
import net.dingyabin.game.cute.abstracts.BaseCute;


/**
 * Created by MrDing
 * Date: 2020/7/14.
 * Time:22:05
 *      |
     -------
 */
@Getter
@Setter
public class ACute extends BaseCute {

    public ACute() {
        super();
    }


    @Override
    public BaseCute init() {
        cuteUnits.add(new CuteUnit(4 * CuteGameManager.STEP, 0));
        cuteUnits.add(new CuteUnit(cuteUnits.get(0).getX() + CuteGameManager.STEP, 0));
        cuteUnits.add(new CuteUnit(cuteUnits.get(1).getX() + CuteGameManager.STEP, 0));
        cuteUnits.add(new CuteUnit(cuteUnits.get(0).getX() + CuteGameManager.STEP, CuteGameManager.STEP));
        centerCute = cuteUnits.get(1);
        return this;
    }


}
