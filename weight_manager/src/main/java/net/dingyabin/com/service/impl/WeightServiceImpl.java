package net.dingyabin.com.service.impl;

import net.dingyabin.com.ExceptionAdvice.BusinessException;
import net.dingyabin.com.bean.QueryConditon;
import net.dingyabin.com.bean.Weight;
import net.dingyabin.com.dao.WeightDao;
import net.dingyabin.com.enums.BusinessEnum;
import net.dingyabin.com.service.WeightService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2017/3/18.
 * Time:20:31
 */
@Service("weightService")
public class WeightServiceImpl implements WeightService {

    @Resource(name="weightDao")
    private WeightDao weightDao;


    @Override
    public int saveWeight(Weight weight) {
        int result;
        try {
            result= weightDao.insertWeight(weight);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(BusinessEnum.SYSTEM_BUSY);
        }
        return result;
    }

    @Override
    public List<Weight> queryByDate(QueryConditon queryConditon) {
        return weightDao.selectByDate(queryConditon);
    }

}
