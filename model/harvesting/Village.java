package com.org.nic.ts.model.harvesting;

public class Village {

    private String DistCode,MandCode,VillCode,VillName,VillName_Tel,VillCode_LG;

    public Village(){

    }

    public Village(String distCode, String mandCode, String villCode, String villName, String villName_Tel,
                   String VillCode_LG) {
        DistCode = distCode;
        MandCode = mandCode;
        VillCode = villCode;
        VillName = villName;
        VillName_Tel = villName_Tel;
        VillCode_LG = VillCode_LG;
    }

    public String getVillCode_LG() {
        return VillCode_LG;
    }

    public void setVillCode_LG(String VillCode_LG) {
        VillCode_LG = VillCode_LG;
    }

    public String getDistCode() {
        return DistCode;
    }

    public void setDistCode(String distCode) {
        DistCode = distCode;
    }

    public String getMandCode() {
        return MandCode;
    }

    public void setMandCode(String mandCode) {
        MandCode = mandCode;
    }

    public String getVillCode() {
        return VillCode;
    }

    public void setVillCode(String villCode) {
        VillCode = villCode;
    }

    public String getVillName() {
        return VillName;
    }

    public void setVillName(String villName) {
        VillName = villName;
    }

    public String getVillName_Tel() {
        return VillName_Tel;
    }

    public void setVillName_Tel(String villName_Tel) {
        VillName_Tel = villName_Tel;
    }
}
