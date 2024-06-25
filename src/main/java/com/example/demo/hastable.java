package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class hastable {
    private StringProperty his_tid=new SimpleStringProperty();
    private StringProperty his_tot_cost=new SimpleStringProperty();
    private StringProperty his_tot_cal=new SimpleStringProperty();
    private String his_hid;
    private String his_wid;
   hastable(){

   }

    public String getHis_hid() {
        return his_hid;
    }

    public String getHis_wid() {
        return his_wid;
    }

    public void setHis_hid(String his_hid) {
        this.his_hid = his_hid;
    }

    public void setHis_wid(String his_wid) {
        this.his_wid = his_wid;
    }

    public String getHis_tid() {
        return his_tidProperty().get();
    }

    public StringProperty his_tidProperty() {
        return his_tid;
    }

    public void setHis_tid(String his_tid) {
        this.his_tidProperty().set(his_tid);
    }

    public String getHis_tot_cost() {
        return his_tot_costProperty().get();
    }

    public StringProperty his_tot_costProperty() {
        return his_tot_cost;
    }

    public void setHis_tot_cost(String his_tot_cost) {
        this.his_tot_costProperty().set(his_tot_cost);
    }

    public String getHis_tot_cal() {
        return his_tot_calProperty().get();
    }

    public StringProperty his_tot_calProperty() {
        return his_tot_cal;
    }

    public void setHis_tot_cal(String hi_tot_cal) {
        this.his_tot_cal.set(hi_tot_cal);
    }
}
