package com.example.demo;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import oracle.jdbc.pool.OracleDataSource;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class WarehouseController implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;

    String URL1="jdbc:oracle:thin:@localhost:1521:XE";
    String User="projusersami";
    String Password="654321";
    @FXML
    private Label MID_Label;

    @FXML
    private TextField edit_kid;

    @FXML
    private TextField edit_mid;

    @FXML
    private TextField edit_wid;

    @FXML
    private TextField edit_wname;

    @FXML
    private ImageView goTables;

    @FXML
    private Button go_add;

    @FXML
    private Button go_connect;

    @FXML
    private Button go_delete;

    @FXML
    private Button go_edit;

    @FXML
    private Button gosearch;

    @FXML
    private TableView<haswarehouse> w_tableview;

    @FXML
    private TextField wid_search;

    @FXML
    private TableColumn<haswarehouse,String> widc;

    @FXML
    private TextField wname_search;

    @FXML
    private TableColumn<haswarehouse,String> wnamec;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            widc.setCellValueFactory(data -> data.getValue().his_WIDProperty());
            wnamec.setCellValueFactory(data -> data.getValue().his_WNAMEProperty());
            MID_Label.setText(HelloController.TF1);
            edit_wid.setEditable(false);
        }
        catch(Exception exp){

        }

    }

    @FXML
    void buttonclicked(ActionEvent event) throws IOException {
        if(event.getSource()==go_add){
            GTONTA("addwatehouse.fxml",event);
        }
        ///////////////////////////////////////////////////////////////////
        else if(event.getSource()==go_connect){
            GTONTA("cooectpage.fxml",event);
        }
        ///////////////////////////////////////////////////////////////////
        else if(event.getSource()==go_delete){

            try {
                if(   !(w_tableview.getSelectionModel().isEmpty())  ) {
                    OracleDataSource ods = new OracleDataSource();
                    ods.setURL(URL1);
                    ods.setUser(User);
                    ods.setPassword(Password);
                    Connection con = ods.getConnection();
                    Statement stmnt = con.createStatement();
                    String SQLSTMNT = "DELETE FROM WAREHOUSE WHERE WID=";
                    SQLSTMNT += w_tableview.getSelectionModel().getSelectedItem().getHis_WID();
                    stmnt.executeQuery(SQLSTMNT);
                    SQLSTMNT="DELETE FROM SUPPLIES WHERE W_ID=";
                    SQLSTMNT+=w_tableview.getSelectionModel().getSelectedItem().getHis_WID();
                    stmnt.executeQuery(SQLSTMNT);
                    con.close();
                    Alert alrrt = new Alert(Alert.AlertType.INFORMATION);
                    alrrt.setContentText("The Selected WAREHOUSE::Cell and it's connections were Deleted Successfully!!");
                    alrrt.showAndWait();
                    w_tableview.getItems().clear();
                }
                else{
                    Alert alrrt = new Alert(Alert.AlertType.WARNING);
                    alrrt.setContentText("Please Select a WAREHOUSE you'd like to delete!.");
                    alrrt.showAndWait();
                }
            }
            catch(SQLException exp){
                Alert alrrt = new Alert(Alert.AlertType.WARNING);
                alrrt.setContentText(exp.getMessage());
                alrrt.showAndWait();
            }

        }
        ///////////////////////////////////////////////////////////////////
        else if(event.getSource()==go_edit){

            try {
                String widd,wnamee,kidd,midd;
                widd=edit_wid.getText();
                wnamee=edit_wname.getText();
                kidd=edit_kid.getText();
                midd=edit_mid.getText();
                if(!((widd.equals(""))&&(wnamee.equals(""))&&(kidd.equals(""))&&(midd.equals(""))) ) {
                    haswarehouse x = w_tableview.getSelectionModel().getSelectedItem();
                    x.setHis_MID(midd);
                    x.setHis_KID(kidd);
                    x.setHis_WNAME(wnamee);
                    x.setHis_WID(widd);
                    OracleDataSource ods = new OracleDataSource();
                    ods.setURL(URL1);
                    ods.setUser(User);
                    ods.setPassword(Password);
                    Connection con = ods.getConnection();
                    Statement stmnt = con.createStatement();
                    String SQLSTMNT = "UPDATE WAREHOUSE";
                    SQLSTMNT += " SET WNAME=" + "'" + x.getHis_WNAME() + "'" + ", K_ID=" + x.getHis_KID() + ", M_ID=" + x.getHis_MID() + " WHERE WID=" + x.getHis_WID();
                    System.out.println(SQLSTMNT);
                    stmnt.executeQuery(SQLSTMNT);
                    con.close();
                    Alert alrrt = new Alert(Alert.AlertType.INFORMATION);
                    alrrt.setContentText("WAREHOUSE-Cell was Edited Successfully:");
                    alrrt.showAndWait();
                    w_tableview.getItems().clear();
                }
                else{
                    Alert alrrt = new Alert(Alert.AlertType.CONFIRMATION);
                    alrrt.setContentText("Please Select a WAREHOUSE to be able to edit it!.");
                    alrrt.showAndWait();
                }
            }
            catch(SQLException exp){
                Alert alrt=new Alert(Alert.AlertType.ERROR);
                alrt.setContentText(exp.getMessage());
                alrt.showAndWait();
            }


        }
        ///////////////////////////////////////////////////////////////////

    }

    @FXML
    void tableviewclicked_warehouse(MouseEvent event) {
        try {
            if (event.getClickCount() >1) {
                haswarehouse x=w_tableview.getSelectionModel().getSelectedItem();
                edit_wid.setText(x.getHis_WID());
                edit_wname.setText(x.getHis_WNAME());
                edit_mid.setText(x.getHis_MID());
                edit_kid.setText(x.getHis_KID());
            }
        }
        catch (Exception exp){

        }
    }

    @FXML
    void do_search(ActionEvent event) {

        try {
            edit_wid.clear();
            edit_wname.clear();
            edit_kid.clear();
            edit_mid.clear();
            w_tableview.getItems().clear();
            String wid, wname;
            //
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(URL1);
            ods.setUser(User);
            ods.setPassword(Password);
            Connection con = ods.getConnection();
            Statement stmnt = con.createStatement();
            //
            wid = wid_search.getText(); wname =wname_search.getText();
             if (!(wid.equals(""))) {
            String SQLSTMNT="SELECT WID,WNAME,M_ID,K_ID FROM WAREHOUSE WHERE WID=";
            SQLSTMNT+=wid_search.getText();
            // System.out.println(SQLSTMNT);
            Statement stmmnt=con.createStatement();
            ResultSet rs=stmmnt.executeQuery(SQLSTMNT);
            rs.next(); String widd,wnamee,midd,kidd;
            widd=rs.getString(1); wnamee=rs.getString(2);
            midd=rs.getString(3); kidd=rs.getString(4);
            haswarehouse x=new haswarehouse(); x.setHis_WID(widd); x.setHis_WNAME(wnamee);
            x.setHis_KID(kidd); x.setHis_MID(midd);
            ObservableList<haswarehouse>list=w_tableview.getItems();
            list.add(x);
            w_tableview.setItems(list);
            wname_search.clear(); wid_search.clear();
            con.close();
             }
             else if ((wid.equals("")) && !(wname.equals(""))) {
                 String SQLSTMNT="SELECT WID,WNAME,M_ID,K_ID FROM WAREHOUSE WHERE WNAME=";
                 SQLSTMNT+="'"+wname_search.getText()+"'";
                 // System.out.println(SQLSTMNT);
                 Statement stmmnt=con.createStatement();
                 ResultSet rs=stmmnt.executeQuery(SQLSTMNT);
                 rs.next(); String widd,wnamee,midd,kidd;
                 widd=rs.getString(1); wnamee=rs.getString(2);
                 midd=rs.getString(3); kidd=rs.getString(4);
                 haswarehouse x=new haswarehouse(); x.setHis_WID(widd); x.setHis_WNAME(wnamee);
                 x.setHis_KID(kidd); x.setHis_MID(midd);
                 ObservableList<haswarehouse>list=w_tableview.getItems();
                 list.add(x);
                 w_tableview.setItems(list);
                 wname_search.clear(); wid_search.clear();
                 con.close();
             }
             else{
                 Alert alrrt=new Alert(Alert.AlertType.INFORMATION);
                 alrrt.setContentText("Fill in at least one path to be able to search");
                 alrrt.showAndWait();
             }

        }

        catch(Exception exp){

        }

    }

    @FXML
    void go_logout(ActionEvent event) {
        try {
            GTOA("hello-view.fxml",event);
        }
        catch (Exception exp){

        }
    }

    @FXML
    void siginbuttononaction(ActionEvent event) {

    }


























    public void GTO(String fxml, MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void GTONT(String fxml,MouseEvent event) throws IOException{
        Parent root2 = FXMLLoader.load(getClass().getResource(fxml));
        Stage newstage=new Stage();
        newstage.setScene(new Scene(root2));
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

