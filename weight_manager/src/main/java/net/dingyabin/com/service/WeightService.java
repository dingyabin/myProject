package net.dingyabin.com.service;

import net.dingyabin.com.bean.QueryConditon;
import net.dingyabin.com.bean.Weight;

import java.util.List;

/**
 * Created by MrDing
 * Date: 2017/3/18.
 * Time:20:30
 */
public interface WeightService {

     int saveWeight(Weight weight);

     List<Weight> queryByDate(QueryConditon queryConditon);

}
