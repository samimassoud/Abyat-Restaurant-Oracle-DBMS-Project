package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class hasitem{
    private StringProperty hi_tid=new SimpleStringProperty();
    private StringProperty hi_cost=new SimpleStringProperty();
    private StringProperty hi_quan=new SimpleStringProperty();
    private StringProperty hi_cal=new SimpleStringProperty();
    String hi_iid;

    public String getHi_iid() {
        return hi_iid;
    }

    public void setHi_iid(String hi_iid) {
        this.hi_iid = hi_iid;
    }

    public String getHi_tid() {
        return hi_tidProperty().get();
    }

    public StringProperty hi_tidProperty() {
        return hi_tid;
    }

    public void setHi_tid(String hi_tid) {
        this.hi_tidProperty().set(hi_tid);
    }

    public String getHi_cost() {
        return hi_costProperty().get();
    }

    public StringProperty hi_costProperty() {
        return hi_cost;
    }

    public String getHi_quan() {
        return hi_quanProperty().get();
    }

    public StringProperty hi_quanProperty() {
        return hi_quan;
    }

    public String getHi_cal() {
        return hi_calProperty().get();
    }

    public StringProperty hi_calProperty() {
        return hi_cal;
    }

    public void setHi_cal(String hi_cal) {
        this.hi_calProperty().set(hi_cal);
    }

    public void setHi_cost(String hi_cost) {
        this.hi_costProperty().set(hi_cost);
    }

    public void setHi_quan(String hi_quan) {
        this.hi_quanProperty().set(hi_quan);
    }

    public hasitem(){

    }
}
