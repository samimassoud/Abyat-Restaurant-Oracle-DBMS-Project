package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class hashall {
    private StringProperty hall_HID=new SimpleStringProperty();
    private StringProperty hall_HName=new SimpleStringProperty();
    private StringProperty hall_KID=new SimpleStringProperty();
    private StringProperty hall_MID=new SimpleStringProperty();

    hashall(){

    }
    public String getHall_HID() {
        return hall_HIDProperty().get();
    }

    public StringProperty hall_HIDProperty() {
        return hall_HID;
    }

    public void setHall_HID(String hall_HID) {
        this.hall_HIDProperty().set(hall_HID);
    }

    public String getHall_HName() {
        return hall_HNameProperty().get();
    }

    public StringProperty hall_HNameProperty() {
        return hall_HName;
    }

    public void setHall_HName(String hall_HName) {
        this.hall_HNameProperty().set(hall_HName);
    }

    public String getHall_KID() {
        return hall_KIDProperty().get();
    }

    public StringProperty hall_KIDProperty() {
        return hall_KID;
    }

    public void setHall_KID(String hall_KID) {
        this.hall_KIDProperty().set(hall_KID);
    }

    public String getHall_MID() {
        return hall_MIDProperty().get();
    }

    public StringProperty hall_MIDProperty() {
        return hall_MID;
    }

    public void setHall_MID(String hall_MID) {
        this.hall_MIDProperty().set(hall_MID);
    }
}
