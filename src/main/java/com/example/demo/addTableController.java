package com.example.demo;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import oracle.jdbc.pool.OracleDataSource;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class addTableController implements Initializable {
    String URL1="jdbc:oracle:thin:@localhost:1521:XE";
    String User="projusersami";
    String Password="654321";

        @FXML
        private TextField add_hidtf;

        @FXML
        private TextField add_tidtf;

        @FXML
        private TextField add_widtf;

        @FXML
        private Button addtablebtnbtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
        }
        catch(Exception exp){

        }
    }

    @FXML
        void doaddtable(ActionEvent event) {
        try {
            String a, b, c;
            a = add_tidtf.getText();
            b = add_hidtf.getText();
            c = add_widtf.getText();
            if (!(a.equals("") && b.equals("") && c.equals(""))) {
                hastable x=new hastable();
                x.setHis_tid(a);
                x.setHis_hid(b);
                x.setHis_wid(c);
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                    String SQLSTMNT = "INSERT INTO T_TABLE (TID,H_ID,W_ID) VALUES ";
                    SQLSTMNT += "(" + x.getHis_tid() + "," + "'" + x.getHis_hid() + "'" + "," + x.getHis_wid() + ")";
                    System.out.println(SQLSTMNT);
                    stmnt.executeQuery(SQLSTMNT);
                    con.close();
                    Alert alrt = new Alert(Alert.AlertType.INFORMATION);
                    alrt.setContentText("A Cell Hall was Added Successfully");
                    alrt.showAndWait();
                    add_tidtf.clear();
                    add_hidtf.clear();
                    add_widtf.clear();
                }
                else {
                Alert alrt = new Alert(Alert.AlertType.ERROR);
                alrt.setContentText("You Should fill in all paths in order to add a HALL");
                alrt.showAndWait();
            }

        }
        catch (SQLException exp){
            Alert alrt=new Alert(Alert.AlertType.ERROR);
            alrt.setContentText(exp.getMessage());
            alrt.showAndWait();
        }
    }

    }

