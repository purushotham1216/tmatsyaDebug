package com.org.nic.ts.model;



public class GetSubComponentMstBean {

    private String SubComponentCode,SubComponentName;
    private boolean isSelected;

    public boolean getSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public GetSubComponentMstBean() {
    }

    public GetSubComponentMstBean(String subComponentCode, String subComponentName) {
        SubComponentCode = subComponentCode;
        SubComponentName = subComponentName;
    }

    public String getSubComponentCode() {
        return SubComponentCode;
    }

    public void setSubComponentCode(String subComponentCode) {
        SubComponentCode = subComponentCode;
    }

    public String getSubComponentName() {
        return SubComponentName;
    }

    public void setSubComponentName(String subComponentName) {
        SubComponentName = subComponentName;
    }
}
