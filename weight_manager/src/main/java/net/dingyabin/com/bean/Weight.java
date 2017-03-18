package net.dingyabin.com.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MrDing
 * Date: 2017/3/18.
 * Time:19:18
 */
public class Weight implements Serializable {

    private static final long serialVersionUID = 7138382515512564740L;

    private Long id;

    private double weight;

    private double waist;

    @JSONField(format = "yyyy-MM-dd")
    private Date createTime;


    public Weight() {
    }

    public Weight(double weight, double waist, Date createTime) {
        this.weight = weight;
        this.waist = waist;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getWaist() {
        return waist;
    }

    public void setWaist(double waist) {
        this.waist = waist;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
