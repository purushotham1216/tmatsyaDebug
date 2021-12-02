package com.org.nic.ts.model.harvesting;



public class District {

    private String StateCode,DistCode,DistName,DistName_Tel,DistCode_Lg;

    public String getDistCode() {
        return DistCode;
    }

    public void setDistCode(String distCode) {
        DistCode = distCode;
    }

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public String getDistName_Tel() {
        return DistName_Tel;
    }

    public void setDistName_Tel(String distName_Tel) {
        DistName_Tel = distName_Tel;
    }

    public String getDistCode_Lg() {
        return DistCode_Lg;
    }

    public void setDistCode_Lg(String DistCode_Lg) {
        DistCode_Lg = DistCode_Lg;
    }

    public District() {
    }

    public District(String StateCode,String distCode, String distName, String distName_Tel, String DistCode_Lg) {
        this.StateCode = StateCode;
        DistCode = distCode;
        DistName = distName;
        DistName_Tel = distName_Tel;
        this.DistCode_Lg = DistCode_Lg;
    }
}
