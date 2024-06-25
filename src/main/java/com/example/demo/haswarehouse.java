package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class haswarehouse {
    private StringProperty his_WID=new SimpleStringProperty();
    private StringProperty his_WNAME=new SimpleStringProperty();
    private String his_MID;
    private String his_KID;
    haswarehouse(){

    }

    public String getHis_WID() {
        return his_WIDProperty().get();
    }

    public StringProperty his_WIDProperty() {
        return his_WID;
    }

    public void setHis_WID(String his_WID) {
        this.his_WIDProperty().set(his_WID);
    }

    public String getHis_WNAME() {
        return his_WNAMEProperty().get();
    }

    public StringProperty his_WNAMEProperty() {
        return his_WNAME;
    }

    public void setHis_WNAME(String his_WNAME) {
        this.his_WNAMEProperty().set(his_WNAME);
    }

    public String getHis_MID() {
        return his_MID;
    }

    public void setHis_MID(String his_MID) {
        this.his_MID = his_MID;
    }

    public String getHis_KID() {
        return his_KID;
    }

    public void setHis_KID(String his_KID) {
        this.his_KID = his_KID;
    }
}
