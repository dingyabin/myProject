package net.dingyabin.com.controller;

import net.dingyabin.com.bean.Weight;
import net.dingyabin.com.result.Response;
import net.dingyabin.com.service.WeightService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

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
        weightService.saveWeight(weight);
        return Response.ok();
    }

}
