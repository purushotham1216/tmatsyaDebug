package com.org.nic.ts.model.harvesting;



public class SpeciesBean {

    private String LandTypecode,LandTypedesc,Type,SpeciesQty;

    public SpeciesBean() {
    }

    public SpeciesBean(String landTypecode, String landTypedesc, String type) {
        LandTypecode = landTypecode;
        LandTypedesc = landTypedesc;
        Type = type;
    }

    public String getLandTypecode() {
        return LandTypecode;
    }

    public void setLandTypecode(String LandTypecode) {
        this.LandTypecode = LandTypecode;
    }

    public String getLandTypedesc() {
        return LandTypedesc;
    }

    public void setLandTypedesc(String LandTypedesc) {
        this.LandTypedesc = LandTypedesc;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getSpeciesQty() {
        return SpeciesQty;
    }

    public void setSpeciesQty(String speciesQty) {
        SpeciesQty = speciesQty;
    }
}
