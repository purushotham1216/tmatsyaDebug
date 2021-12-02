package com.org.nic.ts.model.harvesting;



public class FinYearBean {

        private String Year_Code,Year_Desc;

    public FinYearBean() {
    }

    public FinYearBean(String year_Code, String year_Desc) {
        Year_Code = year_Code;
        Year_Desc = year_Desc;
    }

    public String getYear_Code() {
        return Year_Code;
    }

    public void setYear_Code(String year_Code) {
        Year_Code = year_Code;
    }

    public String getYear_Desc() {
        return Year_Desc;
    }

    public void setYear_Desc(String year_Desc) {
        Year_Desc = year_Desc;
    }
}
