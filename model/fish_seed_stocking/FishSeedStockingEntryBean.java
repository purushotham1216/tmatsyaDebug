package com.org.nic.ts.model.fish_seed_stocking;


public class FishSeedStockingEntryBean {

    private String
            no_of_fingerlings_per_kg, total_no_of_kgs, total_no_of_fingerlings,
            catla_count, rohu_count, common_carp_count,
            mrigala_count, vehicle_number;

    public FishSeedStockingEntryBean() {
    }

    public FishSeedStockingEntryBean(String no_of_fingerlings_per_kg, String total_no_of_kgs, String total_no_of_fingerlings,
                                     String catla_count, String rohu_count, String common_carp_count, String mrigala_count,
                                     String vehicle_number) {
        this.no_of_fingerlings_per_kg = no_of_fingerlings_per_kg;
        this.total_no_of_kgs = total_no_of_kgs;
        this.total_no_of_fingerlings = total_no_of_fingerlings;
        this.catla_count = catla_count;
        this.rohu_count = rohu_count;
        this.common_carp_count = common_carp_count;
        this.mrigala_count = mrigala_count;
        this.vehicle_number = vehicle_number;
    }

    public String getNo_of_fingerlings_per_kg() {
        return no_of_fingerlings_per_kg;
    }

    public void setNo_of_fingerlings_per_kg(String no_of_fingerlings_per_kg) {
        this.no_of_fingerlings_per_kg = no_of_fingerlings_per_kg;
    }

    public String getTotal_no_of_kgs() {
        return total_no_of_kgs;
    }

    public void setTotal_no_of_kgs(String total_no_of_kgs) {
        this.total_no_of_kgs = total_no_of_kgs;
    }

    public String getTotal_no_of_fingerlings() {
        return total_no_of_fingerlings;
    }

    public void setTotal_no_of_fingerlings(String total_no_of_fingerlings) {
        this.total_no_of_fingerlings = total_no_of_fingerlings;
    }

    public String getCatla_count() {
        return catla_count;
    }

    public void setCatla_count(String catla_count) {
        this.catla_count = catla_count;
    }

    public String getRohu_count() {
        return rohu_count;
    }

    public void setRohu_count(String rohu_count) {
        this.rohu_count = rohu_count;
    }

    public String getCommon_carp_count() {
        return common_carp_count;
    }

    public void setCommon_carp_count(String common_carp_count) {
        this.common_carp_count = common_carp_count;
    }

    public String getMrigala_count() {
        return mrigala_count;
    }

    public void setMrigala_count(String mrigala_count) {
        this.mrigala_count = mrigala_count;
    }

    public String getVehicle_number() {
        return vehicle_number;
    }

    public void setVehicle_number(String vehicle_number) {
        this.vehicle_number = vehicle_number;
    }
}
