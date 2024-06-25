package com.example.demo;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Parameter;
import java.net.URL;
import java.sql.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.AmbientLight;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.swing.JRViewer;
import net.sf.jasperreports.view.JasperViewer;
import oracle.jdbc.pool.OracleDataSource;

import javax.swing.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    ObservableList<hasitem> itemslist;
    ObservableList<hastable> tableslist;
    String tblnumber;
    String tf1;
    String hall1;
    String URL1="jdbc:oracle:thin:@localhost:1521:XE";
    String User="projusersami";
    String Password="654321";
    String loginfxml="hello-view.fxml";
    String historyfxml="history.fxml";

    String iname,icost,cal;
    @FXML
    private Button lessbtn;
    @FXML
    private Button plusbtn;
    @FXML
    private Label itemname1;
    @FXML
    private Label itemname2;
    @FXML
    private Label itemname3;
    @FXML
    private Label itemname4;
    @FXML
    private Label itemname5;

    @FXML
    private Label itemcost1;
    @FXML
    private Label itemcost2;
    @FXML
    private Label itemcost3;
    @FXML
    private Label itemcost4;
    @FXML
    private Label itemcost5;


    @FXML
    private Label itemcalorie1;
    @FXML
    private Label itemcalorie2;
    @FXML
    private Label itemcalorie3;
    @FXML
    private Label itemcalorie4;
    @FXML
    private Label itemcalorie5;

    @FXML
    private AnchorPane item1;
    @FXML
    private AnchorPane item2;
    @FXML
    private AnchorPane item3;
    @FXML
    private AnchorPane item4;
    @FXML
    private AnchorPane item5;


    @FXML
    private TableColumn <hasitem,String> tidc;
    @FXML
    private TableColumn <hasitem,String> costc;
    @FXML
    private TableColumn <hasitem,String> caloryc;
    @FXML
    private TableColumn <hasitem,String> quantitiyc ;
    @FXML
    private TableView <hasitem> itemstable;
    @FXML
    private AnchorPane logout;
    @FXML
    private AnchorPane historybtn;


    // home based default:
    @FXML
    private Label Hallnm;
    @FXML
    private Label CIDnm;
    @FXML
    private ComboBox <String>tablescbox;
    // home based default:

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    public void logoutbtn(MouseEvent event) throws IOException {
        try {
            if (event.getClickCount() > 0) GTO(loginfxml, event);
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt=con.createStatement();
                Statement stmnt2=con.createStatement();
                ResultSet rs,rs2;
                for(int i=0;i<tableslist.size();i++) {
                    String SQLST="SELECT COST,CALORIES,QUANTITIY FROM HAS_A WHERE T_ID=";
                    SQLST+=tableslist.get(i).getHis_tid();
                    rs=stmnt.executeQuery(SQLST);
                    int totcalinctr = 0;
                    int totcostctr = 0;
                    while (rs.next()) {
                        String cost=rs.getString(1);
                        String cal=rs.getString(2);
                        String quan=rs.getString(3);
                        int x,y,z;
                        x=Integer.parseInt(cost); y=Integer.parseInt(cal); z=Integer.parseInt(quan);
                        // System.out.println(x+","+y+","+z);
                        totcalinctr+= (y*z);
                        totcostctr+=(x*z);
                    }
                    // System.out.println(totcalinctr+","+totcostctr);
                    tableslist.get(i).setHis_tot_cal(Integer.toString(totcalinctr));
                    tableslist.get(i).setHis_tot_cost(Integer.toString(totcostctr));
                    // if(      !(tableslist.get(i).getHis_tot_cal().equals("0")) || !(tableslist.get(i).getHis_tot_cost().equals("0"))        ) {
                    String updatetbls = "UPDATE T_TABLE SET TOTALCOST=";
                    updatetbls += totcostctr + ",TOTALCALORIEINTAKE=" + totcalinctr + " WHERE TID=";
                    updatetbls += tableslist.get(i).getHis_tid();
                    // System.out.println(updatetbls);
                    rs2=stmnt2.executeQuery(updatetbls);
                    // }
                } // for loop
                // starts with each table in the hall, and take it's whole items from the HAS_A then calculate and updates in the T_Table.
                con.close();
        } catch (Exception exp) {

        }
    }
    @FXML
    public void gohistory(MouseEvent event) throws IOException{
        try{
            if (event.getClickCount() > 0) GTONT(historyfxml, event);
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(URL1);
            ods.setUser(User);
            ods.setPassword(Password);
            Connection con = ods.getConnection();
            Statement stmnt=con.createStatement();
            Statement stmnt2=con.createStatement();
            ResultSet rs,rs2;
            for(int i=0;i<tableslist.size();i++) {
                String SQLST="SELECT COST,CALORIES,QUANTITIY FROM HAS_A WHERE T_ID=";
                SQLST+=tableslist.get(i).getHis_tid();
                rs=stmnt.executeQuery(SQLST);
                int totcalinctr = 0;
                int totcostctr = 0;
                while (rs.next()) {
                    String cost=rs.getString(1);
                    String cal=rs.getString(2);
                    String quan=rs.getString(3);
                    int x,y,z;
                    x=Integer.parseInt(cost); y=Integer.parseInt(cal); z=Integer.parseInt(quan);
                    // System.out.println(x+","+y+","+z);
                    totcalinctr+= (y*z);
                    totcostctr+=(x*z);
                }
               // System.out.println(totcalinctr+","+totcostctr);
                tableslist.get(i).setHis_tot_cal(Integer.toString(totcalinctr));
                tableslist.get(i).setHis_tot_cost(Integer.toString(totcostctr));
               // if(      !(tableslist.get(i).getHis_tot_cal().equals("0")) || !(tableslist.get(i).getHis_tot_cost().equals("0"))        ) {
                    String updatetbls = "UPDATE T_TABLE SET TOTALCOST=";
                    updatetbls += totcostctr + ",TOTALCALORIEINTAKE=" + totcalinctr + " WHERE TID=";
                    updatetbls += tableslist.get(i).getHis_tid();
                   // System.out.println(updatetbls);
                     rs2=stmnt2.executeQuery(updatetbls);
               // }
            } // for loop
            // starts with each table in the hall, and take it's whole items from the HAS_A then calculate and updates in the T_Table.
            con.close();
        }
        catch (Exception exp){
            exp.printStackTrace();
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle rb){
        try{
            //System.out.println("hi");
            itemslist =FXCollections.observableArrayList();
            tableslist=FXCollections.observableArrayList();
            tidc.setCellValueFactory(data -> data.getValue().hi_tidProperty());
            costc.setCellValueFactory(data -> data.getValue().hi_costProperty());
            caloryc.setCellValueFactory(data -> data.getValue().hi_calProperty());
            quantitiyc.setCellValueFactory(data -> data.getValue().hi_quanProperty());
            tf1=HelloController.TF1;
            CIDnm.setText("CID:"+tf1);
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(URL1);
            ods.setUser(User);
            ods.setPassword(Password);
            Connection con = ods.getConnection();
            String SQLSTMNT = "SELECT H_ID FROM CASHIER WHERE CID=";
            SQLSTMNT+=tf1;
            // System.out.println(SQLSTMNT);
            Statement stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery(SQLSTMNT);
            if(rs.next()) hall1=rs.getString(1);
            SQLSTMNT="SELECT TID FROM T_TABLE WHERE H_ID=";
            SQLSTMNT+=hall1;
            rs=stmnt.executeQuery(SQLSTMNT);
             if(tablescbox.getItems().size()==0) {
                while (rs.next()) {
                    String tid = rs.getString(1);
                    tablescbox.getItems().add(tid);
                    hastable x=new hastable();
                    x.setHis_tid(tid);
                    tableslist.add(x);
                }
            }
            //Default Items:
            int counter=1;
            SQLSTMNT= "SELECT INAME,ICOST,CALORIEINTAKE FROM ITEM WHERE ITYPE='Sandwich'";
           rs=stmnt.executeQuery(SQLSTMNT);
            fillin(counter,rs);
            // Default Items:
            con.close();
            Hallnm.setText("Hall:"+hall1);


        }
        catch (Exception exp){

        }
    }
        @FXML
        public void stypebtn(MouseEvent event) throws IOException {
            try {
                if (event.getClickCount() > 0) {
                    int counter=1;
                    OracleDataSource ods = new OracleDataSource();
                    ods.setURL(URL1);
                    ods.setUser(User);
                    ods.setPassword(Password);
                    Connection con = ods.getConnection();
                    String SQLSTMNT = "SELECT INAME,ICOST,CALORIEINTAKE FROM ITEM WHERE ITYPE='Sandwich'";
                    Statement stmnt = con.createStatement();
                    ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                        fillin(counter,rs);
                    //while rs.next();
                    con.close();
                } // if
            } // try
            catch(Exception exp){

            }
    } //stype
    @FXML
    public void dtypebtn(MouseEvent event) throws IOException {
        try {
            if (event.getClickCount() > 0) {

                int counter=1;
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                String SQLSTMNT = "SELECT INAME,ICOST,CALORIEINTAKE FROM ITEM WHERE ITYPE='Drink'";
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                fillin(counter,rs);
                //while rs.next();
                con.close();
            } // if
        } // try
        catch(Exception exp){

        }
    }
    public void mtypebtn(MouseEvent event) throws IOException {
        try {
            if (event.getClickCount() > 0) {

                int counter=1;
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                String SQLSTMNT = "SELECT INAME,ICOST,CALORIEINTAKE FROM ITEM WHERE ITYPE='Meal'";
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                fillin(counter,rs);
                //while rs.next();
                con.close();
            } // if
        } // try
        catch(Exception exp){

        }
    }

    public void gritypebtn(MouseEvent event) throws IOException {
        try {
            if (event.getClickCount() > 0) {

                int counter=1;
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                String SQLSTMNT = "SELECT INAME,ICOST,CALORIEINTAKE FROM ITEM WHERE ITYPE='Grills'";
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                fillin(counter,rs);
                //while rs.next();
                con.close();
            } // if
        } // try
        catch(Exception exp){

        }
    }


    public void swetypebtn(MouseEvent event) throws IOException {
        try {
            if (event.getClickCount() > 0) {

                int counter=1;
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                String SQLSTMNT = "SELECT INAME,ICOST,CALORIEINTAKE FROM ITEM WHERE ITYPE='Desert'";
                Statement stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                fillin(counter,rs);
                //while rs.next();
                con.close();
            } // if
        } // try
        catch(Exception exp){

        }
    }
    @FXML
    public void cmboxsel(ActionEvent event) throws IOException{
        try {
            itemstable.getItems().clear();
            ComboBox<String> cb = (ComboBox<String>) event.getSource();
            if (!(cb.getValue().equals(null))) {
                tblnumber = cb.getValue();
                itemslist.removeAll();
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLSTMNT = "SELECT COST,QUANTITIY,CALORIES,I_ID FROM HAS_A WHERE T_ID=";
                SQLSTMNT += tblnumber;
                ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                String cost, quan, cal,iid;
                ObservableList<hasitem> list= itemstable.getItems();
                while (rs.next()) {
                    cost = rs.getString(1);
                    quan = rs.getString(2);
                    cal = rs.getString(3);
                    iid= rs.getString(4);
                    hasitem x = new hasitem();
                    x.setHi_cost(cost+"$");
                    x.setHi_quan(quan);
                    x.setHi_tid(tblnumber);
                    x.setHi_cal(cal);
                    x.setHi_iid(iid);
                    list.add(x);
                }
                itemstable.setItems(list);

                itemstable.refresh();
                con.close();
            }
        }
        catch(Exception e){

        }
    }
    @FXML
    public void itemClicked(MouseEvent event)throws IOException{
        try{
            if(event.getSource()==item1){
                String iid;
                boolean flag=false;
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLSTMNT2="SELECT IID FROM ITEM WHERE INAME=";
                SQLSTMNT2+="'"+itemname1.getText()+"'";
                ResultSet rs2=stmnt.executeQuery(SQLSTMNT2);
                rs2.next(); iid = rs2.getString(1);
                String boolSQL="SELECT T_ID,I_ID FROM HAS_A WHERE T_ID=";
                boolSQL+=tblnumber+" AND I_ID="+iid;
                ResultSet boolrs=stmnt.executeQuery(boolSQL);
                if(boolrs.next()){
                    String SQLSTMNT = "SELECT QUANTITIY FROM HAS_A WHERE T_ID=";
                    SQLSTMNT+=tblnumber+" AND I_ID="+iid;
                    ResultSet rsrs=stmnt.executeQuery(SQLSTMNT);
                    rsrs.next(); String q=rsrs.getString(1);
                    //System.out.println(SQLSTMNT);
                    int x=Integer.parseInt(q); x++; q=String.valueOf(x);

                    SQLSTMNT="UPDATE HAS_A SET QUANTITIY=";
                    SQLSTMNT+=q+" WHERE T_ID=";
                    SQLSTMNT+=tblnumber+" AND I_ID="+iid;
                    rsrs=stmnt.executeQuery(SQLSTMNT);
                }
                else {
                    String SQLSTMNT = "INSERT INTO HAS_A (T_ID,COST,I_ID,QUANTITIY,CALORIES) " +
                            "VALUES(";
                    SQLSTMNT += tblnumber;
                    SQLSTMNT += ",";
                    SQLSTMNT += itemcost1.getText().replace("$","");
                    SQLSTMNT += ",";
                    SQLSTMNT += iid;
                    SQLSTMNT += ",1,";
                    SQLSTMNT += itemcalorie1.getText() + ")";
                    ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                }
                itemstable.getItems().clear();
                String SQLSTMNT = "SELECT COST,QUANTITIY,CALORIES FROM HAS_A WHERE T_ID=";
                SQLSTMNT += tblnumber;
                ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                String cost, quan, cal;
                ObservableList<hasitem> list= itemstable.getItems();
                while (rs.next()) {
                    cost = rs.getString(1);
                    quan = rs.getString(2);
                    cal = rs.getString(3);
                    hasitem x = new hasitem();
                    x.setHi_cost(cost+"$");
                    x.setHi_quan(quan);
                    x.setHi_tid(tblnumber);
                    x.setHi_cal(cal);
                    x.setHi_iid(iid);
                    list.add(x);
                }
                itemstable.setItems(list);

                itemstable.refresh();
                // now to update the table content.

                // now to update etc..

                con.close();

            } // ITEM1





            if(event.getSource()==item2){
                String iid;
                boolean flag=false;
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLSTMNT2="SELECT IID FROM ITEM WHERE INAME=";
                SQLSTMNT2+="'"+itemname2.getText()+"'";
                ResultSet rs2=stmnt.executeQuery(SQLSTMNT2);
                rs2.next(); iid = rs2.getString(1);
                String boolSQL="SELECT T_ID,I_ID FROM HAS_A WHERE T_ID=";
                boolSQL+=tblnumber+" AND I_ID="+iid;
                ResultSet boolrs=stmnt.executeQuery(boolSQL);
                if(boolrs.next()){
                    String SQLSTMNT = "SELECT QUANTITIY FROM HAS_A WHERE T_ID=";
                    SQLSTMNT+=tblnumber+" AND I_ID="+iid;
                    ResultSet rsrs=stmnt.executeQuery(SQLSTMNT);
                    rsrs.next(); String q=rsrs.getString(1);
                   // System.out.println(SQLSTMNT);
                    int x=Integer.parseInt(q); x++; q=String.valueOf(x);

                    SQLSTMNT="UPDATE HAS_A SET QUANTITIY=";
                    SQLSTMNT+=q+" WHERE T_ID=";
                    SQLSTMNT+=tblnumber+" AND I_ID="+iid;
                    rsrs=stmnt.executeQuery(SQLSTMNT);
                }
                else {
                    String SQLSTMNT = "INSERT INTO HAS_A (T_ID,COST,I_ID,QUANTITIY,CALORIES) " +
                            "VALUES(";
                    SQLSTMNT += tblnumber;
                    SQLSTMNT += ",";
                    SQLSTMNT += itemcost2.getText().replace("$","");
                    SQLSTMNT += ",";
                    SQLSTMNT += iid;
                    SQLSTMNT += ",1,";
                    SQLSTMNT += itemcalorie2.getText() + ")";
                    ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                }
                itemstable.getItems().clear();
                String SQLSTMNT = "SELECT COST,QUANTITIY,CALORIES FROM HAS_A WHERE T_ID=";
                SQLSTMNT += tblnumber;
                ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                String cost, quan, cal;
                ObservableList<hasitem> list= itemstable.getItems();
                while (rs.next()) {
                    cost = rs.getString(1);
                    quan = rs.getString(2);
                    cal = rs.getString(3);
                    hasitem x = new hasitem();
                    x.setHi_cost(cost+"$");
                    x.setHi_quan(quan);
                    x.setHi_tid(tblnumber);
                    x.setHi_cal(cal);
                    x.setHi_iid(iid);
                    list.add(x);
                }
                itemstable.setItems(list);

                itemstable.refresh();



                con.close();

            } //ITEM2










            if(event.getSource()==item3){
                String iid;
                boolean flag=false;
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLSTMNT2="SELECT IID FROM ITEM WHERE INAME=";
                SQLSTMNT2+="'"+itemname3.getText()+"'";
                ResultSet rs2=stmnt.executeQuery(SQLSTMNT2);
                rs2.next(); iid = rs2.getString(1);
                String boolSQL="SELECT T_ID,I_ID FROM HAS_A WHERE T_ID=";
                boolSQL+=tblnumber+" AND I_ID="+iid;
                ResultSet boolrs=stmnt.executeQuery(boolSQL);
                if(boolrs.next()){
                    String SQLSTMNT = "SELECT QUANTITIY FROM HAS_A WHERE T_ID=";
                    SQLSTMNT+=tblnumber+" AND I_ID="+iid;
                    ResultSet rsrs=stmnt.executeQuery(SQLSTMNT);
                    rsrs.next(); String q=rsrs.getString(1);
                   // System.out.println(SQLSTMNT);
                    int x=Integer.parseInt(q); x++; q=String.valueOf(x);

                    SQLSTMNT="UPDATE HAS_A SET QUANTITIY=";
                    SQLSTMNT+=q+" WHERE T_ID=";
                    SQLSTMNT+=tblnumber+" AND I_ID="+iid;
                    rsrs=stmnt.executeQuery(SQLSTMNT);
                }
                else {
                    String SQLSTMNT = "INSERT INTO HAS_A (T_ID,COST,I_ID,QUANTITIY,CALORIES) " +
                            "VALUES(";
                    SQLSTMNT += tblnumber;
                    SQLSTMNT += ",";
                    SQLSTMNT += itemcost3.getText().replace("$","");
                    SQLSTMNT += ",";
                    SQLSTMNT += iid;
                    SQLSTMNT += ",1,";
                    SQLSTMNT += itemcalorie3.getText() + ")";
                    ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                }



                itemstable.getItems().clear();
                String SQLSTMNT = "SELECT COST,QUANTITIY,CALORIES FROM HAS_A WHERE T_ID=";
                SQLSTMNT += tblnumber;
                ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                String cost, quan, cal;
                ObservableList<hasitem> list= itemstable.getItems();
                while (rs.next()) {
                    cost = rs.getString(1);
                    quan = rs.getString(2);
                    cal = rs.getString(3);
                    hasitem x = new hasitem();
                    x.setHi_cost(cost+"$");
                    x.setHi_quan(quan);
                    x.setHi_tid(tblnumber);
                    x.setHi_cal(cal);
                    x.setHi_iid(iid);
                    list.add(x);
                }
                itemstable.setItems(list);

                itemstable.refresh();
                con.close();

            }
            // ITEM 3












            if(event.getSource()==item4){
                String iid;
                boolean flag=false;
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLSTMNT2="SELECT IID FROM ITEM WHERE INAME=";
                SQLSTMNT2+="'"+itemname4.getText()+"'";
                ResultSet rs2=stmnt.executeQuery(SQLSTMNT2);
                rs2.next(); iid = rs2.getString(1);
                String boolSQL="SELECT T_ID,I_ID FROM HAS_A WHERE T_ID=";
                boolSQL+=tblnumber+" AND I_ID="+iid;
                ResultSet boolrs=stmnt.executeQuery(boolSQL);
                if(boolrs.next()){
                    String SQLSTMNT = "SELECT QUANTITIY FROM HAS_A WHERE T_ID=";
                    SQLSTMNT+=tblnumber+" AND I_ID="+iid;
                    ResultSet rsrs=stmnt.executeQuery(SQLSTMNT);
                    rsrs.next(); String q=rsrs.getString(1);
                   // System.out.println(SQLSTMNT);
                    int x=Integer.parseInt(q); x++; q=String.valueOf(x);

                    SQLSTMNT="UPDATE HAS_A SET QUANTITIY=";
                    SQLSTMNT+=q+" WHERE T_ID=";
                    SQLSTMNT+=tblnumber+" AND I_ID="+iid;
                    rsrs=stmnt.executeQuery(SQLSTMNT);
                }
                else {
                    String SQLSTMNT = "INSERT INTO HAS_A (T_ID,COST,I_ID,QUANTITIY,CALORIES) " +
                            "VALUES(";
                    SQLSTMNT += tblnumber;
                    SQLSTMNT += ",";
                    SQLSTMNT += itemcost4.getText().replace("$","");
                    SQLSTMNT += ",";
                    SQLSTMNT += iid;
                    SQLSTMNT += ",1,";
                    SQLSTMNT += itemcalorie4.getText() + ")";
                    ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                }


                itemstable.getItems().clear();
                String SQLSTMNT = "SELECT COST,QUANTITIY,CALORIES FROM HAS_A WHERE T_ID=";
                SQLSTMNT += tblnumber;
                ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                String cost, quan, cal;
                ObservableList<hasitem> list= itemstable.getItems();
                while (rs.next()) {
                    cost = rs.getString(1);
                    quan = rs.getString(2);
                    cal = rs.getString(3);
                    hasitem x = new hasitem();
                    x.setHi_cost(cost+"$");
                    x.setHi_quan(quan);
                    x.setHi_tid(tblnumber);
                    x.setHi_cal(cal);
                    x.setHi_iid(iid);
                    list.add(x);
                }
                itemstable.setItems(list);

                itemstable.refresh();

                con.close();

            }
            // ITEM 4













            if(event.getSource()==item5){
                String iid;
                boolean flag=false;
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLSTMNT2="SELECT IID FROM ITEM WHERE INAME=";
                SQLSTMNT2+="'"+itemname5.getText()+"'";
                ResultSet rs2=stmnt.executeQuery(SQLSTMNT2);
                rs2.next(); iid = rs2.getString(1);
                String boolSQL="SELECT T_ID,I_ID FROM HAS_A WHERE T_ID=";
                boolSQL+=tblnumber+" AND I_ID="+iid;
                ResultSet boolrs=stmnt.executeQuery(boolSQL);
                if(boolrs.next()){
                    String SQLSTMNT = "SELECT QUANTITIY FROM HAS_A WHERE T_ID=";
                    SQLSTMNT+=tblnumber+" AND I_ID="+iid;
                    ResultSet rsrs=stmnt.executeQuery(SQLSTMNT);
                    rsrs.next(); String q=rsrs.getString(1);
                   // System.out.println(SQLSTMNT);
                    int x=Integer.parseInt(q); x++; q=String.valueOf(x);

                    SQLSTMNT="UPDATE HAS_A SET QUANTITIY=";
                    SQLSTMNT+=q+" WHERE T_ID=";
                    SQLSTMNT+=tblnumber+" AND I_ID="+iid;
                    rsrs=stmnt.executeQuery(SQLSTMNT);
                }
                else {
                    String SQLSTMNT = "INSERT INTO HAS_A (T_ID,COST,I_ID,QUANTITIY,CALORIES) " +
                            "VALUES(";
                    SQLSTMNT += tblnumber;
                    SQLSTMNT += ",";
                    SQLSTMNT += itemcost5.getText().replace("$","");
                    SQLSTMNT += ",";
                    SQLSTMNT += iid;
                    SQLSTMNT += ",1,";
                    SQLSTMNT += itemcalorie5.getText() + ")";
                    ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                }

                itemstable.getItems().clear();
                String SQLSTMNT = "SELECT COST,QUANTITIY,CALORIES FROM HAS_A WHERE T_ID=";
                SQLSTMNT += tblnumber;
                ResultSet rs = stmnt.executeQuery(SQLSTMNT);
                String cost, quan, cal;
                ObservableList<hasitem> list= itemstable.getItems();
                while (rs.next()) {
                    cost = rs.getString(1);
                    quan = rs.getString(2);
                    cal = rs.getString(3);
                    hasitem x = new hasitem();
                    x.setHi_cost(cost+"$");
                    x.setHi_quan(quan);
                    x.setHi_tid(tblnumber);
                    x.setHi_cal(cal);
                    x.setHi_iid(iid);
                    list.add(x);
                }
                itemstable.setItems(list);

                itemstable.refresh();


                con.close();

            }
            // ITEM 5
















        }
        catch(Exception e){

        }
    }
    // History::
    @FXML
    private Button his_search;

    @FXML
    private TableView<hastable> his_table;

    @FXML
    private TableColumn<hastable, String> his_tidc;

    @FXML
    private TextField his_tnm;

    @FXML
    private TableColumn<hastable, String> his_tot_calc;

    @FXML
    private TableColumn<hastable, String> his_tot_costc;

    @FXML
    void gosearch(ActionEvent event) {
        try {
            his_table.getItems().clear();
            his_tidc.setCellValueFactory(data -> data.getValue().his_tidProperty());
            his_tot_costc.setCellValueFactory(data -> data.getValue().his_tot_costProperty());
            his_tot_calc.setCellValueFactory(data -> data.getValue().his_tot_calProperty());
            OracleDataSource his_ods = new OracleDataSource();
            his_ods.setURL(URL1);
            his_ods.setUser(User);
            his_ods.setPassword(Password);
            Connection his_con = his_ods.getConnection();
            String SQLSTMNT = "SELECT TID,TOTALCOST,TOTALCALORIEINTAKE FROM T_TABLE WHERE TID=";
            SQLSTMNT+=his_tnm.getText();
            Statement stmnt = his_con.createStatement();
            ResultSet rs = stmnt.executeQuery(SQLSTMNT);
            rs.next(); String tid,tcost,tcalin;
            tid=rs.getString(1); tcost=rs.getString(2); tcalin=rs.getString(3);
            hastable x=new hastable(); x.setHis_tid(tid); x.setHis_tot_cost(tcost); x.setHis_tot_cal(tcalin);
            ObservableList<hastable>list=his_table.getItems();
            list.add(x);
            his_table.setItems(list);
            his_tnm.clear();
            his_con.close();
        }
        catch(Exception exp){

        }


    }
    @FXML
    void plbtn(ActionEvent event) {
        try {
            hasitem x = itemstable.getSelectionModel().getSelectedItem();
            if (event.getSource() == plusbtn && !(x.equals(null))) {
                int quan = Integer.parseInt(x.getHi_quan());
                quan++;
                x.setHi_quan(Integer.toString(quan));
                itemstable.getItems().add(itemstable.getSelectionModel().getSelectedIndex(), x);
                itemstable.getItems().remove(itemstable.getSelectionModel().getSelectedIndex() - 1);
                itemstable.refresh();
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLST="UPDATE HAS_A SET QUANTITIY=";
                SQLST+=x.getHi_quan()+"WHERE T_ID="+x.getHi_tid();
                SQLST+="AND I_ID="+x.getHi_iid();
                stmnt.executeQuery(SQLST);
                con.close();

            }
            else if (event.getSource() == lessbtn && !(x.equals(null))) {
                int quan = Integer.parseInt(x.getHi_quan());
                quan--;
                x.setHi_quan(Integer.toString(quan));
                itemstable.getItems().add(itemstable.getSelectionModel().getSelectedIndex(), x);
                itemstable.getItems().remove(itemstable.getSelectionModel().getSelectedIndex() - 1);
                itemstable.refresh();
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLST="UPDATE HAS_A SET QUANTITIY=";
                SQLST+=x.getHi_quan()+"WHERE T_ID="+x.getHi_tid();
                SQLST+="AND I_ID="+x.getHi_iid();
                stmnt.executeQuery(SQLST);
                con.close();
            }
        } //try
        catch(Exception exp){

        }
    }
    @FXML Button deletebtn;
    @FXML
    public void delbtn(ActionEvent event){
        try {
            hasitem x = itemstable.getSelectionModel().getSelectedItem();
            if (event.getSource() == deletebtn && !(x.equals(null))) {
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLST="DELETE FROM HAS_A WHERE T_ID=";
                SQLST+=tblnumber+" AND I_ID="+x.getHi_iid();
                stmnt.executeQuery(SQLST);
                itemstable.getItems().remove(itemstable.getSelectionModel().getSelectedIndex());
                con.close();
            }
        }
        catch (Exception exp){

        }
    }




    // History::
    // Reciept
    @FXML
    private Button reciept_button;
    @FXML
    public void get_reciept(ActionEvent event)throws IOException{

        try {
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt=con.createStatement();
                Statement stmnt2=con.createStatement();
                ResultSet rs,rs2;
                for(int i=0;i<tableslist.size();i++) {
                    String SQLST="SELECT COST,CALORIES,QUANTITIY FROM HAS_A WHERE T_ID=";
                    SQLST+=tableslist.get(i).getHis_tid();
                    rs=stmnt.executeQuery(SQLST);
                    int totcalinctr = 0;
                    int totcostctr = 0;
                    while (rs.next()) {
                        String cost=rs.getString(1);
                        String cal=rs.getString(2);
                        String quan=rs.getString(3);
                        int x,y,z;
                        x=Integer.parseInt(cost); y=Integer.parseInt(cal); z=Integer.parseInt(quan);
                        // System.out.println(x+","+y+","+z);
                        totcalinctr+= (y*z);
                        totcostctr+=(x*z);
                    }
                    // System.out.println(totcalinctr+","+totcostctr);
                    tableslist.get(i).setHis_tot_cal(Integer.toString(totcalinctr));
                    tableslist.get(i).setHis_tot_cost(Integer.toString(totcostctr));
                    // if(      !(tableslist.get(i).getHis_tot_cal().equals("0")) || !(tableslist.get(i).getHis_tot_cost().equals("0"))        ) {
                    String updatetbls = "UPDATE T_TABLE SET TOTALCOST=";
                    updatetbls += totcostctr + ",TOTALCALORIEINTAKE=" + totcalinctr + " WHERE TID=";
                    updatetbls += tableslist.get(i).getHis_tid();
                    // System.out.println(updatetbls);
                    rs2=stmnt2.executeQuery(updatetbls);
                    // }
                } // for loop
                // starts with each table in the hall, and take it's whole items from the HAS_A then calculate and updates in the T_Table.
            String sq="SELECT TOTALCOST FROM T_TABLE WHERE TID="+tablescbox.getValue();
                ResultSet rsrsrs=stmnt2.executeQuery(sq);
                rsrsrs.next(); sq=rsrsrs.getString(1);
            InputStream input = new FileInputStream(new File("C:\\Users\\hp\\Downloads\\democashierfinale\\demo\\demo\\src\\main\\resources\\com\\example\\demo\\rec1.jrxml"));
          //  JasperDesign jd = JRXmlLoader.load();
            String y=tablescbox.getValue();
            JasperDesign jd = JRXmlLoader.load(input);
            String x="SELECT `QUANTITIY`,`INAME`,`ICOST` FROM `HAS_A`,`ITEM` ";
            x+="WHERE HAS_A.I_ID=ITEM.IID AND HAS_A.T_ID="+y;
            x="SELECT QUANTITIY,INAME,ICOST FROM HAS_A,ITEM WHERE HAS_A.I_ID=ITEM.IID AND HAS_A.T_ID="+y;
            JRDesignQuery jdq=new JRDesignQuery();
            jdq.setText(x);
            jd.setQuery(jdq);
            HashMap<String, Object> parameters=new HashMap<String,Object>();
            parameters.put("tblnmb",y);
            parameters.put("totalcost",sq+"$");

            JasperReport jr = JasperCompileManager.compileReport(jd);
            JasperPrint jpp = JasperFillManager.fillReport(jr, parameters, con);
         //   JasperExportManager.exportReportToPdfFile(jasperPrint, "C:\\Users\\   hp\\Downloads\\democashierfinale\\demo\\demo\\src\\main\\resources\\com\\example\\demo\\RECIEPT1.jrxml");
            JasperViewer xy=new JasperViewer(jpp,false);
            xy.viewReport(jpp,false);
            x="DELETE FROM HAS_A WHERE T_ID="+y;
            stmnt.executeQuery(x);
            itemstable.getItems().clear();



        }catch (Exception e){
            throw new RuntimeException(e);
        }


    }

    // Reciept


public void fillin(int counter,ResultSet rs){
        try {
            while (rs.next()) {
                iname = rs.getString(1);
                icost = rs.getString(2);
                cal = rs.getString(3);
                if (counter == 1) {
                    itemname1.setText(iname);
                    itemcost1.setText(icost);
                    itemcalorie1.setText(cal);
                    counter++;
                } else if (counter == 2) {
                    itemname2.setText(iname);
                    itemcost2.setText(icost);
                    itemcalorie2.setText(cal);
                    counter++;
                } else if (counter == 3) {
                    itemname3.setText(iname);
                    itemcost3.setText(icost);
                    itemcalorie3.setText(cal);
                    counter++;
                } else if (counter == 4) {
                    itemname4.setText(iname);
                    itemcost4.setText(icost);
                    itemcalorie4.setText(cal);
                    counter++;
                } else if (counter == 5) {
                    itemname5.setText(iname);
                    itemcost5.setText(icost);
                    itemcalorie5.setText(cal);
                }
            }
        }
        catch(Exception exp){

        }
}
    public void GTO(String fxml,MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        Image x=new Image("C:\\Users\\hp\\Downloads\\democashierfinale\\demo\\demo\\src\\main\\resources\\com\\example\\demo\\316741304_1850431091984088_2968029368833723444_n.png");
        stage.getIcons().add(x);
        stage.show();
    }
    public void GTONT(String fxml,MouseEvent event) throws IOException{
        Parent root2 = FXMLLoader.load(getClass().getResource(fxml));
        Stage newstage=new Stage();
        newstage.setScene(new Scene(root2));
        Image x=new Image("C:\\Users\\hp\\Downloads\\democashierfinale\\demo\\demo\\src\\main\\resources\\com\\example\\demo\\316741304_1850431091984088_2968029368833723444_n.png");
        newstage.getIcons().add(x);
        newstage.show();
    }
    public void GTOA(String fxml,ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        Image x=new Image("C:\\Users\\hp\\Downloads\\democashierfinale\\demo\\demo\\src\\main\\resources\\com\\example\\demo\\316741304_1850431091984088_2968029368833723444_n.png");
        stage.getIcons().add(x);
        stage.show();
    }
    public void GTONTA(String fxml,ActionEvent event) throws IOException{
        Parent root2 = FXMLLoader.load(getClass().getResource(fxml));
        Stage newstage=new Stage();
        newstage.setScene(new Scene(root2));
        Image x=new Image("C:\\Users\\hp\\Downloads\\democashierfinale\\demo\\demo\\src\\main\\resources\\com\\example\\demo\\316741304_1850431091984088_2968029368833723444_n.png");
        newstage.getIcons().add(x);
        newstage.show();
    }
}

