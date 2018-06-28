package net.dingyabin.com.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.dingyabin.com.bean.QueryConditon;
import net.dingyabin.com.bean.Weight;
import net.dingyabin.com.enums.BusinessEnum;
import net.dingyabin.com.result.GridDataResult;
import net.dingyabin.com.result.Response;
import net.dingyabin.com.service.WeightService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2017/3/18.
 * Time:20:36
 */
@RestController
@RequestMapping("/weight")
public class WeightController {

    @Resource(name = "weightService")
    private WeightService weightService;


    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public Response save(Weight weight) {
        weight.setCreateTime(new Date());
        List<Weight> weights = weightService.queryByDate(weight.getCreateTime());
        if (!CollectionUtils.isEmpty(weights)) {
            return Response.error().Message(BusinessEnum.ALREADYEXIST.getMessage());
        }
        weightService.saveWeight(weight);
        return Response.ok();
    }


    @RequestMapping(value = "/query", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public GridDataResult query(QueryConditon queryConditon) {
        PageHelper.startPage(queryConditon.getPage(), queryConditon.getRows());
        List<Weight> weights = weightService.queryByDateRange(queryConditon);
        PageInfo<Weight> pageInfo = new PageInfo<>(weights);
        return new GridDataResult(pageInfo.getTotal(), weights);
    }

}
