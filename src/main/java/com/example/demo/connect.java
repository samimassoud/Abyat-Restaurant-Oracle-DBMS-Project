package com.example.demo;

import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import oracle.jdbc.pool.OracleDataSource;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class connect implements Initializable {

    @FXML
    private Button connectbtn;

    @FXML
    private Button disconnectbtn;

    @FXML
    private ComboBox<String> sup_cmbox;

    @FXML
    private ComboBox<String> wh_cmbox;
    String URL1="jdbc:oracle:thin:@localhost:1521:XE";
    String User="projusersami";
    String Password="654321";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(URL1);
            ods.setUser(User);
            ods.setPassword(Password);
            Connection con = ods.getConnection();
            Statement stmnt=con.createStatement();
            String SQLSTMNT="SELECT SPID FROM SUPPLIERS";
            ResultSet rs=stmnt.executeQuery(SQLSTMNT);
            if(sup_cmbox.getItems().size()==0) {
                while (rs.next()) {
                    String spid = rs.getString(1);
                    sup_cmbox.getItems().add(spid);
                }
            }
            SQLSTMNT="SELECT WID FROM WAREHOUSE";
            rs=stmnt.executeQuery(SQLSTMNT);
            if(wh_cmbox.getItems().size()==0) {
                while (rs.next()) {
                    String wid = rs.getString(1);
                    wh_cmbox.getItems().add(wid);
                }
            }
            con.close();
        }
        catch (Exception exp){

        }
    }

    @FXML
    void connect_bclicked(ActionEvent event) throws IOException {
        try {
            boolean x= (sup_cmbox.getValue()==null || wh_cmbox.getValue()==null);
            if(!x) {
                if (event.getSource() == connectbtn) {
                    OracleDataSource ods = new OracleDataSource();
                    ods.setURL(URL1);
                    ods.setUser(User);
                    ods.setPassword(Password);
                    Connection con = ods.getConnection();
                    Statement stmnt = con.createStatement();
                    String SQLSTMNT = "INSERT INTO SUPPLIES (SP_ID,W_ID) VALUES(";
                    SQLSTMNT += wh_cmbox.getValue() + "," + sup_cmbox.getValue() + ")";
                    stmnt.executeQuery(SQLSTMNT);
                    con.close();
                    Alert alrt=new Alert(Alert.AlertType.INFORMATION,"The Connection was operated Successfully");
                    alrt.showAndWait();

                } else if (event.getSource() == disconnectbtn) {
                    OracleDataSource ods = new OracleDataSource();
                    ods.setURL(URL1);
                    ods.setUser(User);
                    ods.setPassword(Password);
                    Connection con = ods.getConnection();
                    Statement stmnt = con.createStatement();
                    String SQLSTMNT = "DELETE FROM SUPPLIES WHERE SP_ID=";
                    SQLSTMNT += sup_cmbox.getValue() + " AND W_ID=" + wh_cmbox.getValue();
                    System.out.println(SQLSTMNT);
                    stmnt.executeQuery(SQLSTMNT);
                    con.close();
                    Alert alrt=new Alert(Alert.AlertType.INFORMATION,"The Connection of Suppliment was Deleted Successfully");
                    alrt.showAndWait();

                }
            }
            else if(x){
                Alert alrt=new Alert(Alert.AlertType.WARNING,"Please fill in both values to perform an operation");
                alrt.showAndWait();
            }
        }
        catch (Exception exp){


        }
    }

}

