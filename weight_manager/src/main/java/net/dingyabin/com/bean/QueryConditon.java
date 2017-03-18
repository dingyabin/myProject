package net.dingyabin.com.bean;

import java.util.Date;

/**
 * Created by MrDing
 * Date: 2017/3/18.
 * Time:19:26
 */
public class QueryConditon {

    private Date fromDate;

    private Date endDate;


    public static QueryConditon create() {
        return new QueryConditon();
    }

    public QueryConditon fromDate(Date fromDate) {
        setFromDate(fromDate);
        return this;
    }

    public QueryConditon endDate(Date endDate) {
        setEndDate(endDate);
        return this;
    }


    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "QueryConditon{" +
                "fromDate=" + fromDate +
                ", endDate=" + endDate +
                '}';
    }

}
