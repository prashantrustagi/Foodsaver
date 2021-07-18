package com.cdac.foodsaver.model;

public class PendingOrder {

    String ngoName;
    String food;
    String logicticsName;
    Integer imageurl;
    String time;

    public PendingOrder(String name, String food,String time,String logicticsName, Integer imageurl) {
        this.ngoName = name;
        this.food = food;
        this.imageurl = imageurl;
        this.time = time;
        this.logicticsName = logicticsName;
    }
    public String getName() {
        return ngoName;
    }

    public void setName(String name) {
        this.ngoName = name;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String price) {
        this.food = food;
    }

    public Integer getImageurl() {
        return imageurl;
    }

    public void setImageurl(Integer imageurl) {
        this.imageurl = imageurl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String price) {
        this.time = time;
    }

    public String getLogicticsName() {
        return logicticsName ;
    }

    public void setLogicticsName(String logicticsName) {
        this.logicticsName = logicticsName;
    }

}


