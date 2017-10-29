package net.dingyabin.bean;

import java.util.Date;

public class Weight {
    private Long id;

    private Double weight;

    private Double waist;

    private Date createtime;

    public Weight(Long id, Double weight, Double waist, Date createtime) {
        this.id = id;
        this.weight = weight;
        this.waist = waist;
        this.createtime = createtime;
    }

    public Weight() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getWaist() {
        return waist;
    }

    public void setWaist(Double waist) {
        this.waist = waist;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "Weight{" +
                "id=" + id +
                ", weight=" + weight +
                ", waist=" + waist +
                ", createtime=" + createtime +
                '}';
    }
}