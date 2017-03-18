package net.dingyabin.com.dao;

import net.dingyabin.com.bean.QueryConditon;
import net.dingyabin.com.bean.Weight;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2017/3/18.
 * Time:19:23
 */
public interface WeightDao {

    int insertWeight(Weight weight);

    List<Weight> selectByDateRange(QueryConditon queryConditon);

    List<Weight> selectByDate(@Param("date") Date date);

}
