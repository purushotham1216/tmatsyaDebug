package com.org.nic.ts.model.harvesting;



public class WaterBodyBean {

    private String SNo,Seasionality;

    public WaterBodyBean() {
    }

    public WaterBodyBean(String SNo, String seasionality) {
        this.SNo = SNo;
        Seasionality = seasionality;
    }

    public String getSNo() {
        return SNo;
    }

    public void setSNo(String SNo) {
        this.SNo = SNo;
    }

    public String getSeasionality() {
        return Seasionality;
    }

    public void setSeasionality(String seasionality) {
        Seasionality = seasionality;
    }
}
