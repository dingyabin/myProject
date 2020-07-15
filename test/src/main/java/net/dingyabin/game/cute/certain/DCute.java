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
public class DCute extends BaseCute {

    public DCute() {
        super();
    }


    @Override
    public BaseCute init() {
        cuteUnits.add(new CuteUnit(4 * CuteGameManager.STEP, 0));
        cuteUnits.add(new CuteUnit(4 * CuteGameManager.STEP + CuteGameManager.STEP, 0));
        cuteUnits.add(new CuteUnit(4 * CuteGameManager.STEP, CuteGameManager.STEP));
        cuteUnits.add(new CuteUnit(4 * CuteGameManager.STEP + CuteGameManager.STEP, CuteGameManager.STEP));
        centerCute = cuteUnits.get(3);
        return this;
    }


}
