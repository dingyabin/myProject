package net.dingyabin.com.controller;

import net.dingyabin.com.bean.QueryConditon;
import net.dingyabin.com.bean.Weight;
import net.dingyabin.com.enums.BusinessEnum;
import net.dingyabin.com.result.Response;
import net.dingyabin.com.service.WeightService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2017/3/18.
 * Time:20:36
 */
@Controller
@RequestMapping("/weight")
public class WeightController {

    @Resource(name="weightService")
    private WeightService weightService;


    @RequestMapping(value = "/save", method = RequestMethod.POST , produces = "application/json;charset=utf-8")
    @ResponseBody
    public Response save(@RequestBody Weight weight) {
        List<Weight> weights = weightService.queryByDate(weight.getCreateTime());
        if (!CollectionUtils.isEmpty(weights)){
            return Response.error().Message(BusinessEnum.ALREADYEXIST.getMessage());
        }
        weightService.saveWeight(weight);
        return Response.ok();
    }



    @RequestMapping(value = "/query", method = RequestMethod.POST , produces = "application/json;charset=utf-8")
    @ResponseBody
    public Response query(@RequestBody QueryConditon queryConditon) {
        List<Weight> weights = weightService.queryByDateRange(queryConditon);
        return Response.ok().Data(weights);
    }

}
