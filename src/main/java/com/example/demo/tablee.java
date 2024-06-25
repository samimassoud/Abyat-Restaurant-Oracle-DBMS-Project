package com.example.demo;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.net.URL;
import java.util.ResourceBundle;

public class tablee implements Initializable {

    @FXML
    private TableColumn<hasitem, String> c1;

    @FXML
    private TableColumn<hasitem, String> c2;
    @FXML
    private TableView<hasitem> t1;
    @FXML
    private Button btn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        c1.setCellValueFactory(data -> data.getValue().hi_tidProperty());
        c2.setCellValueFactory(data -> data.getValue().hi_calProperty());


    }
    @FXML
    void pressed(ActionEvent event) {
        hasitem x=new hasitem();
        x.setHi_tid("1");
        x.setHi_cal("69");
        ObservableList<hasitem> list= t1.getItems();
        list.add(x);
        t1.setItems(list);
    }
}

