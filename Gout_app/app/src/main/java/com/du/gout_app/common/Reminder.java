package com.du.gout_app.common;

/**
 * Created by 杜哲凯 on 2016/7/15.
 */
public class Reminder {
    Integer reminderid;
    Integer userid;
    Integer hour;
    Integer min;
    Integer effective;

    public Reminder(){}
    public Reminder(Integer b,Integer c,Integer d,Integer e){
        this.userid=b;
        this.hour=c;
        this.min =d;
        this.effective = e;
    }
    public Reminder(Integer b,Integer c,Integer d){
        this.userid=b;
        this.hour=c;
        this.min =d;
    }
    public Reminder(Integer a){
        this.effective = a;

    }
    public Reminder(Integer a,Integer b,Integer c,Integer d,Integer e){
        this.reminderid =a;
        this.userid=b;
        this.hour=c;
        this.min =d;
        this.effective = e;
    }
    public Integer getReminderid() {
        return reminderid;
    }

    public void setReminderid(Integer reminderid) {
        this.reminderid = reminderid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Integer getEffective() {
        return effective;
    }

    public void setEffective(Integer effective) {
        this.effective = effective;
    }
}
