package net.dingyabin.mapstruct;

import org.mapstruct.Named;

import java.util.Objects;

public class Conventer {


    @Named("objToString")
    public static String objToString(Object obj) {
        return Objects.toString(obj, null);
    }


    @Named("objToInt")
    public static int objToInt(Object obj) {
        return Integer.parseInt(obj.toString());
    }
}
