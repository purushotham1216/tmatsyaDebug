package com.org.nic.ts.model.harvesting;


public class UpdateSpeciesBean {

    private String Huid, DistCode, MandCode, VillCode, DistName, MandName, VillName, Seasonality_code,
            Culture_type, Wb_Code, Harvesting_dt, Tot_Harvested, Catla,
            Rohu, Mrigala, CC, Murrel, Prawn, Catfish_jelly_wallage,
            Others, Catla_PerKg, Rohu_PerKg, Mrigala_PerKg, CC_PerKg,
            Murrel_PerKg, Prawn_PerKg, Catfish_PerKg, Others_PerKg, wb_name, Seasionality,
            pangasiusFish,    pangasiusFish_PerKg,            tilapiaFish,    tilapiaFish_PerKg;

    public UpdateSpeciesBean() {
    }

    public UpdateSpeciesBean(String huid, String distCode, String mandCode, String villCode, String distName,
                             String mandName, String villName, String seasonality_code, String culture_type,
                             String wb_Code, String harvesting_dt, String tot_Harvested, String catla,
                             String rohu, String mrigala, String CC, String murrel, String prawn,
                             String catfish_jelly_wallage, String others,String pangasiusFish,
                             String tilapiaFish,String catla_PerKg, String rohu_PerKg,
                             String mrigala_PerKg, String CC_PerKg, String murrel_PerKg, String prawn_PerKg,
                             String catfish_PerKg, String others_PerKg,String pangasiusFish_PerKg,
                             String tilapiaFish_PerKg,
                             String wb_name, String seasionality) {
        Huid = huid;
        DistCode = distCode;
        MandCode = mandCode;
        VillCode = villCode;
        DistName = distName;
        MandName = mandName;
        VillName = villName;
        Seasonality_code = seasonality_code;
        Culture_type = culture_type;
        Wb_Code = wb_Code;
        Harvesting_dt = harvesting_dt;
        Tot_Harvested = tot_Harvested;
        Catla = catla;
        Rohu = rohu;
        Mrigala = mrigala;
        this.CC = CC;
        Murrel = murrel;
        Prawn = prawn;
        Catfish_jelly_wallage = catfish_jelly_wallage;
        Others = others;
        Catla_PerKg = catla_PerKg;
        Rohu_PerKg = rohu_PerKg;
        Mrigala_PerKg = mrigala_PerKg;
        this.CC_PerKg = CC_PerKg;
        Murrel_PerKg = murrel_PerKg;
        Prawn_PerKg = prawn_PerKg;
        Catfish_PerKg = catfish_PerKg;
        Others_PerKg = others_PerKg;
        this.wb_name = wb_name;
        Seasionality = seasionality;
        this.pangasiusFish = pangasiusFish;
        this.pangasiusFish_PerKg = pangasiusFish_PerKg;
        this.tilapiaFish = tilapiaFish;
        this.tilapiaFish_PerKg = tilapiaFish_PerKg;
    }

    public String getHuid() {
        return Huid;
    }

    public void setHuid(String huid) {
        Huid = huid;
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

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public String getMandName() {
        return MandName;
    }

    public void setMandName(String mandName) {
        MandName = mandName;
    }

    public String getVillName() {
        return VillName;
    }

    public void setVillName(String villName) {
        VillName = villName;
    }

    public String getSeasonality_code() {
        return Seasonality_code;
    }

    public void setSeasonality_code(String seasonality_code) {
        Seasonality_code = seasonality_code;
    }

    public String getCulture_type() {
        return Culture_type;
    }

    public void setCulture_type(String culture_type) {
        Culture_type = culture_type;
    }

    public String getWb_Code() {
        return Wb_Code;
    }

    public void setWb_Code(String wb_Code) {
        Wb_Code = wb_Code;
    }

    public String getHarvesting_dt() {
        return Harvesting_dt;
    }

    public void setHarvesting_dt(String harvesting_dt) {
        Harvesting_dt = harvesting_dt;
    }

    public String getTot_Harvested() {
        return Tot_Harvested;
    }

    public void setTot_Harvested(String tot_Harvested) {
        Tot_Harvested = tot_Harvested;
    }

    public String getCatla() {
        return Catla;
    }

    public void setCatla(String catla) {
        Catla = catla;
    }

    public String getRohu() {
        return Rohu;
    }

    public void setRohu(String rohu) {
        Rohu = rohu;
    }

    public String getMrigala() {
        return Mrigala;
    }

    public void setMrigala(String mrigala) {
        Mrigala = mrigala;
    }

    public String getCC() {
        return CC;
    }

    public void setCC(String CC) {
        this.CC = CC;
    }

    public String getMurrel() {
        return Murrel;
    }

    public void setMurrel(String murrel) {
        Murrel = murrel;
    }

    public String getPrawn() {
        return Prawn;
    }

    public void setPrawn(String prawn) {
        Prawn = prawn;
    }

    public String getCatfish_jelly_wallage() {
        return Catfish_jelly_wallage;
    }

    public void setCatfish_jelly_wallage(String catfish_jelly_wallage) {
        Catfish_jelly_wallage = catfish_jelly_wallage;
    }

    public String getOthers() {
        return Others;
    }

    public void setOthers(String others) {
        Others = others;
    }

    public String getCatla_PerKg() {
        return Catla_PerKg;
    }

    public void setCatla_PerKg(String catla_PerKg) {
        Catla_PerKg = catla_PerKg;
    }

    public String getRohu_PerKg() {
        return Rohu_PerKg;
    }

    public void setRohu_PerKg(String rohu_PerKg) {
        Rohu_PerKg = rohu_PerKg;
    }

    public String getMrigala_PerKg() {
        return Mrigala_PerKg;
    }

    public void setMrigala_PerKg(String mrigala_PerKg) {
        Mrigala_PerKg = mrigala_PerKg;
    }

    public String getCC_PerKg() {
        return CC_PerKg;
    }

    public void setCC_PerKg(String CC_PerKg) {
        this.CC_PerKg = CC_PerKg;
    }

    public String getMurrel_PerKg() {
        return Murrel_PerKg;
    }

    public void setMurrel_PerKg(String murrel_PerKg) {
        Murrel_PerKg = murrel_PerKg;
    }

    public String getPrawn_PerKg() {
        return Prawn_PerKg;
    }

    public void setPrawn_PerKg(String prawn_PerKg) {
        Prawn_PerKg = prawn_PerKg;
    }

    public String getCatfish_PerKg() {
        return Catfish_PerKg;
    }

    public void setCatfish_PerKg(String catfish_PerKg) {
        Catfish_PerKg = catfish_PerKg;
    }

    public String getOthers_PerKg() {
        return Others_PerKg;
    }

    public void setOthers_PerKg(String others_PerKg) {
        Others_PerKg = others_PerKg;
    }

    public String getWb_name() {
        return wb_name;
    }

    public void setWb_name(String wb_name) {
        this.wb_name = wb_name;
    }

    public String getSeasionality() {
        return Seasionality;
    }

    public void setSeasionality(String seasionality) {
        Seasionality = seasionality;
    }

    public String getPangasiusFish() {
        return pangasiusFish;
    }

    public void setPangasiusFish(String pangasiusFish) {
        this.pangasiusFish = pangasiusFish;
    }

    public String getPangasiusFish_PerKg() {
        return pangasiusFish_PerKg;
    }

    public void setPangasiusFish_PerKg(String pangasiusFish_PerKg) {
        this.pangasiusFish_PerKg = pangasiusFish_PerKg;
    }

    public String getTilapiaFish() {
        return tilapiaFish;
    }

    public void setTilapiaFish(String tilapiaFish) {
        this.tilapiaFish = tilapiaFish;
    }

    public String getTilapiaFish_PerKg() {
        return tilapiaFish_PerKg;
    }

    public void setTilapiaFish_PerKg(String tilapiaFish_PerKg) {
        this.tilapiaFish_PerKg = tilapiaFish_PerKg;
    }
}
