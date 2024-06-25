package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import oracle.jdbc.pool.OracleDataSource;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class addd implements Initializable {
    String URL1="jdbc:oracle:thin:@localhost:1521:XE";
    String User="projusersami";
    String Password="654321";

    @FXML
    private Button add_button;

    @FXML
    private TextField add_kid;

    @FXML
    private TextField add_mid;

    @FXML
    private TextField add_wid;

    @FXML
    private TextField add_wname;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_mid.setText("901");
        add_mid.setEditable(false);
    }

    @FXML
    void add_add(ActionEvent event) {
        try {
            String a, b, c,d;
            a = add_wid.getText();
            b = add_wname.getText();
            c = add_kid.getText();
            d= add_mid.getText();
            if (!(a.equals("") && b.equals("") && c.equals("") &&d.equals(""))) {
                haswarehouse x=new haswarehouse();
                x.setHis_WID(a); x.setHis_WNAME(b);
                x.setHis_MID(d); x.setHis_KID(c);
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLSTMNT = "INSERT INTO WAREHOUSE (WID,WNAME,M_ID,K_ID) VALUES ";
                String SQLSTMNT2="SELECT KID FROM KITCHEN WHERE KID=";
                SQLSTMNT2+=x.getHis_KID();
                SQLSTMNT += "(" + x.getHis_WID()+ "," + "'" + x.getHis_WNAME() + "'" + "," + x.getHis_KID() +","+ x.getHis_MID()+")";
                // System.out.println(SQLSTMNT);
                ResultSet rs=stmnt.executeQuery(SQLSTMNT2);
                if(rs.next()) {
                    stmnt.executeQuery(SQLSTMNT);
                    con.close();
                    Alert alrt = new Alert(Alert.AlertType.INFORMATION);
                    alrt.setContentText("A Cell Hall was Added Successfully");
                    alrt.showAndWait();
                    add_mid.clear();
                    add_wname.clear();
                    add_wid.clear();
                    add_kid.clear();
                }
                else{
                    Alert alrt = new Alert(Alert.AlertType.INFORMATION);
                    alrt.setContentText("The Kitchen ID you're trying to connect the new warehouse to seems to be Non-Existed!!");
                    alrt.showAndWait();
                    con.close();
                }
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
