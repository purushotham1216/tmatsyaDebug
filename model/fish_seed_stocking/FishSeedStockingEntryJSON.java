package com.org.nic.ts.model.fish_seed_stocking;


public class FishSeedStockingEntryJSON {

    private String
            StackingDt, stackingNo, Vehicle_no,
            CPerKG,CTotalkgs, CTotalFLG,catla,
            RPerKG,RTotalkgs, RTotalFLG,rohu,
            CCPerKG,CCTotalkgs, CCTotalFLG,common_carp,
            MPerKG,MTotalkgs, MTotalFLG,mrigala;
    ;

    public FishSeedStockingEntryJSON() {
    }


    public FishSeedStockingEntryJSON(String stackingDt, String stackingNo, String vehicle_no, String CPerKG, String CTotalkgs,
                                     String CTotalFLG, String catla, String RPerKG, String RTotalkgs, String RTotalFLG,
                                     String rohu, String CCPerKG, String CCTotalkgs, String CCTotalFLG, String common_carp,
                                     String MPerKG, String MTotalkgs, String MTotalFLG, String mrigala) {
        StackingDt = stackingDt;
        this.stackingNo = stackingNo;
        Vehicle_no = vehicle_no;
        this.CPerKG = CPerKG;
        this.CTotalkgs = CTotalkgs;
        this.CTotalFLG = CTotalFLG;
        this.catla = catla;
        this.RPerKG = RPerKG;
        this.RTotalkgs = RTotalkgs;
        this.RTotalFLG = RTotalFLG;
        this.rohu = rohu;
        this.CCPerKG = CCPerKG;
        this.CCTotalkgs = CCTotalkgs;
        this.CCTotalFLG = CCTotalFLG;
        this.common_carp = common_carp;
        this.MPerKG = MPerKG;
        this.MTotalkgs = MTotalkgs;
        this.MTotalFLG = MTotalFLG;
        this.mrigala = mrigala;
    }

    public String getStackingDt() {
        return StackingDt;
    }

    public void setStackingDt(String stackingDt) {
        StackingDt = stackingDt;
    }

    public String getStackingNo() {
        return stackingNo;
    }

    public void setStackingNo(String stackingNo) {
        this.stackingNo = stackingNo;
    }

    public String getVehicle_no() {
        return Vehicle_no;
    }

    public void setVehicle_no(String vehicle_no) {
        Vehicle_no = vehicle_no;
    }

    public String getCPerKG() {
        return CPerKG;
    }

    public void setCPerKG(String CPerKG) {
        this.CPerKG = CPerKG;
    }

    public String getCTotalkgs() {
        return CTotalkgs;
    }

    public void setCTotalkgs(String CTotalkgs) {
        this.CTotalkgs = CTotalkgs;
    }

    public String getCTotalFLG() {
        return CTotalFLG;
    }

    public void setCTotalFLG(String CTotalFLG) {
        this.CTotalFLG = CTotalFLG;
    }

    public String getCatla() {
        return catla;
    }

    public void setCatla(String catla) {
        this.catla = catla;
    }

    public String getRPerKG() {
        return RPerKG;
    }

    public void setRPerKG(String RPerKG) {
        this.RPerKG = RPerKG;
    }

    public String getRTotalkgs() {
        return RTotalkgs;
    }

    public void setRTotalkgs(String RTotalkgs) {
        this.RTotalkgs = RTotalkgs;
    }

    public String getRTotalFLG() {
        return RTotalFLG;
    }

    public void setRTotalFLG(String RTotalFLG) {
        this.RTotalFLG = RTotalFLG;
    }

    public String getRohu() {
        return rohu;
    }

    public void setRohu(String rohu) {
        this.rohu = rohu;
    }

    public String getCCPerKG() {
        return CCPerKG;
    }

    public void setCCPerKG(String CCPerKG) {
        this.CCPerKG = CCPerKG;
    }

    public String getCCTotalkgs() {
        return CCTotalkgs;
    }

    public void setCCTotalkgs(String CCTotalkgs) {
        this.CCTotalkgs = CCTotalkgs;
    }

    public String getCCTotalFLG() {
        return CCTotalFLG;
    }

    public void setCCTotalFLG(String CCTotalFLG) {
        this.CCTotalFLG = CCTotalFLG;
    }

    public String getCommon_carp() {
        return common_carp;
    }

    public void setCommon_carp(String common_carp) {
        this.common_carp = common_carp;
    }

    public String getMPerKG() {
        return MPerKG;
    }

    public void setMPerKG(String MPerKG) {
        this.MPerKG = MPerKG;
    }

    public String getMTotalkgs() {
        return MTotalkgs;
    }

    public void setMTotalkgs(String MTotalkgs) {
        this.MTotalkgs = MTotalkgs;
    }

    public String getMTotalFLG() {
        return MTotalFLG;
    }

    public void setMTotalFLG(String MTotalFLG) {
        this.MTotalFLG = MTotalFLG;
    }

    public String getMrigala() {
        return mrigala;
    }

    public void setMrigala(String mrigala) {
        this.mrigala = mrigala;
    }
}
