package com.org.nic.ts.model.harvesting;



public class CultureTypeBean {

    private String Fishculture_cd,Fishculture_Name;

    public CultureTypeBean() {
    }

    public CultureTypeBean(String Fishculture_cd, String Fishculture_Name) {
        this.Fishculture_cd = Fishculture_cd;
        this.Fishculture_Name = Fishculture_Name;
    }

    public String getFishculture_cd() {
        return Fishculture_cd;
    }

    public void setFishculture_cd(String fishculture_cd) {
        Fishculture_cd = fishculture_cd;
    }

    public String getFishculture_Name() {
        return Fishculture_Name;
    }

    public void setFishculture_Name(String fishculture_Name) {
        Fishculture_Name = fishculture_Name;
    }
}
