package com.dingyabin.mongo.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by MrDing
 * Date: 2017/5/6.
 * Time:3:18
 */
public class Movie implements Serializable{

    private static final long serialVersionUID = 7195491819231653130L;

    private String name;

    private String xxxxx;

    private String directior;

    private int time;

    private List<String> people;

    private Date createTime;

    public Movie() {
    }

    public Movie(String name, String directior, int time, Date createTime) {
        this.name = name;
        this.directior = directior;
        this.time = time;
        this.createTime = createTime;
        this.people= Arrays.asList("1","2","3","4");
    }

    public String getName() {
        return name;
    }

    public List<String> getPeople() {
        return people;
    }

    public void setPeople(List<String> people) {
        this.people = people;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirectior() {
        return directior;
    }

    public void setDirectior(String directior) {
        this.directior = directior;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getXxxxx() {
        return xxxxx;
    }

    public void setXxxxx(String xxxxx) {
        this.xxxxx = xxxxx;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
