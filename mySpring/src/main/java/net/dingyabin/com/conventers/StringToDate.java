package net.dingyabin.com.conventers;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MrDing
 * Date: 2017/2/18.
 * Time:11:54
 */
public class StringToDate implements Converter<String,Date> {

    private  SimpleDateFormat sdf;

    public StringToDate(String  pattern) {
        this.sdf = new SimpleDateFormat(pattern);
    }

    @Override
    public Date convert(String source) {
        if (!StringUtils.isEmpty(source)) {
            try {
                return sdf.parse(source);
            } catch (ParseException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return null;
    }
}
