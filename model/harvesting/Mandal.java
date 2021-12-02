package com.org.nic.ts.model.harvesting;


public class Mandal {

    private String DistCode, MandCode, MandName, MandName_Tel,  MandCode_LG;

    public Mandal(String distCode, String mandCode, String mandName, String mandName_Tel, String MandCode_LG) {
        DistCode = distCode;
        MandCode = mandCode;
        MandName = mandName;
        MandName_Tel = mandName_Tel;
        this.MandCode_LG = MandCode_LG;
//     String m_LgCode,    M_LgCode = m_LgCode;

    }

    public Mandal() {
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

    public String getMandName() {
        return MandName;
    }

    public void setMandName(String mandName) {
        MandName = mandName;
    }

    public String getMandName_Tel() {
        return MandName_Tel;
    }

    public void setMandName_Tel(String mandName_Tel) {
        MandName_Tel = mandName_Tel;
    }

    public String getMandCode_LG() {
        return MandCode_LG;
    }

    public void setMandCode_LG(String MandCode_LG) {
        MandCode_LG = MandCode_LG;
    }
}
