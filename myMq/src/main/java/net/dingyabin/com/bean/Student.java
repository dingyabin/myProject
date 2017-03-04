package net.dingyabin.com.bean;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by MrDing
 * Date: 2017/2/18.
 * Time:11:35
 */
public class Student implements Serializable {

    private static final long serialVersionUID = 7933934468423664841L;

    private Integer id;

    private String name;

    @JSONField (format="yyyy/MM/dd")
    private Date birth;


    public Student() {
    }

    public Student(Integer id, String name, Date birth) {
        this.id = id;
        this.name = name;
        this.birth = birth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }



    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                '}';
    }
}
