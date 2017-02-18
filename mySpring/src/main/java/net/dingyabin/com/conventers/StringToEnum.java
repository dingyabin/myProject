package net.dingyabin.com.conventers;

import net.dingyabin.com.enums.Sex;
import org.springframework.core.convert.converter.Converter;

/**
 * Created by MrDing
 * Date: 2017/2/18.
 * Time:12:04
 */
public class StringToEnum implements Converter<String ,Sex>{

    @Override
    public Sex convert(String source) {
        if (source!=null){
           return Sex.getByValue(Integer.valueOf(source));
        }
        return null;
    }

}
