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
import java.util.Optional;
import java.util.ResourceBundle;

public class TablesController implements Initializable {
    private Stage stage;
    private Parent root;
    private Scene scene;


    @FXML
    private Label MID_Label;

    @FXML
    private Button deletetable;

    @FXML
    private Button edittable_edit_btn;

    @FXML
    private TextField edittable_hid;

    @FXML
    private TextField edittable_tid;

    @FXML
    private TextField edittable_wid;

    @FXML
    private ImageView goHalls;

    @FXML
    private Button go_table_add;

    @FXML
    private Button gosearchbutton_table;

    @FXML
    private TableView<hastable> his_table;

    @FXML
    private TableColumn<hastable, String> his_tidc;

    @FXML
    private TableColumn<hastable, String> his_tot_calc;

    @FXML
    private TableColumn<hastable, String> his_tot_costc;

    @FXML
    private TextField tnm_tf;
    String URL1="jdbc:oracle:thin:@localhost:1521:XE";
    String User="projusersami";
    String Password="654321";
    String loginfxml="hello-view.fxml";
    String slchal="Selectionhall.fxml";
    String addhal="addhall.fxml";
    String edithal="edithall.fxml";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        his_tidc.setCellValueFactory(data -> data.getValue().his_tidProperty());
        his_tot_calc.setCellValueFactory(data -> data.getValue().his_tot_calProperty());
        his_tot_costc.setCellValueFactory(data -> data.getValue().his_tot_costProperty());
        MID_Label.setText(HelloController.TF1);
        edittable_tid.setEditable(false);
    }

    @FXML
    void goadd_table(ActionEvent event) throws IOException {
        GTONTA("addtable.fxml",event);
        Alert alrrt=new Alert(Alert.AlertType.INFORMATION);
        alrrt.setContentText("make sure the values of TID,H_ID and W_ID are integer// Valid values!");
        alrrt.showAndWait();

    }
    @FXML
    void tableviewclicked_table(MouseEvent event) {
        try {
            if (event.getClickCount() >1) {
                hastable x=his_table.getSelectionModel().getSelectedItem();
                edittable_tid.setText(x.getHis_tid());
                edittable_hid.setText(x.getHis_hid());
                edittable_wid.setText(x.getHis_wid());
            }
        }
        catch (Exception exp){

        }
    }
    @FXML
    void godelete_table(ActionEvent event) {
        try {
            if(   !(his_table.getSelectionModel().isEmpty())  ) {
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLSTMNT = "DELETE FROM T_TABLE WHERE TID=";
                SQLSTMNT +=his_table.getSelectionModel().getSelectedItem().getHis_tid();
                stmnt.executeQuery(SQLSTMNT);
                his_table.getItems().clear();
                con.close();
                Alert alrrt = new Alert(Alert.AlertType.INFORMATION);
                alrrt.setContentText("The Selected Hall::Cell was Deleted Successfully!!");
                his_table.getItems().clear();
                alrrt.showAndWait();
            }
            else{
                Alert alrrt = new Alert(Alert.AlertType.WARNING);
                alrrt.setContentText("Please Select a Table you'd like to delete!.");
                alrrt.showAndWait();
            }
        }
        catch(SQLException exp) {
            String msg="The Table you're trying to delete seems to has Items on it.\n would you like to delete the items?\n" +
                    "Press Ok to do so.\n Press Cancel to Abort";
            Alert alrrt = new Alert(Alert.AlertType.INFORMATION,msg,ButtonType.OK,ButtonType.CANCEL);
            Optional<ButtonType> choose = alrrt.showAndWait();
            if(choose.get()==ButtonType.OK){
                try {
                    OracleDataSource ods = new OracleDataSource();
                    ods.setURL(URL1);
                    ods.setUser(User);
                    ods.setPassword(Password);
                    Connection con = ods.getConnection();
                    Statement stmnt = con.createStatement();
                    String SQLSTMNT="DELETE FROM HAS_A WHERE T_ID=";
                    SQLSTMNT+=his_table.getSelectionModel().getSelectedItem().getHis_tid();
                    stmnt.executeQuery(SQLSTMNT);
                    SQLSTMNT = "DELETE FROM T_TABLE WHERE TID=";
                    SQLSTMNT +=his_table.getSelectionModel().getSelectedItem().getHis_tid();
                    stmnt.executeQuery(SQLSTMNT);
                    Alert wedidit=new Alert(Alert.AlertType.INFORMATION);
                    his_table.getItems().clear();
                    wedidit.setContentText("The Table & The items on it were successfully deleted!!");
                    wedidit.showAndWait();
                    alrrt.close();
                    con.close();
                }
                catch(Exception expp){

                }
            }
            else if(choose.get()==ButtonType.CANCEL){
                alrrt.close();
            }
        }
    }

    @FXML
    void gohalls(ActionEvent event) throws IOException {

        GTOA("1Edit.fxml",event);

    }

    @FXML
    void gosearch_table(ActionEvent event) {
        try {
            edittable_tid.clear();
            edittable_hid.clear();
            edittable_wid.clear();
            his_table.getItems().clear();
            String t_id;
            //
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(URL1);
            ods.setUser(User);
            ods.setPassword(Password);
            Connection con = ods.getConnection();
            Statement stmnt = con.createStatement();
            //
            t_id = tnm_tf.getText();
           // if (!(t_id.equals(""))) {
                String SQLSTMNT="SELECT TID,TOTALCOST,TOTALCALORIEINTAKE,H_ID,W_ID FROM T_TABLE WHERE TID=";
                SQLSTMNT+=tnm_tf.getText();
               // System.out.println(SQLSTMNT);
                Statement stmmnt=con.createStatement();
                ResultSet rs=stmmnt.executeQuery(SQLSTMNT);
                rs.next(); String tid,tcost,tcalin,hid,wid;
                tid=rs.getString(1); tcost=rs.getString(2); tcalin=rs.getString(3);
                hid=rs.getString(4); wid=rs.getString(5);
                hastable x=new hastable(); x.setHis_tid(tid); x.setHis_tot_cost(tcost); x.setHis_tot_cal(tcalin);
                x.setHis_wid(wid); x.setHis_hid(hid);
                ObservableList<hastable>list=his_table.getItems();
                list.add(x);
                his_table.setItems(list);
                tnm_tf.clear();
                con.close();
           // }
            /*
            else {
                Alert alrt=new Alert(Alert.AlertType.ERROR);
                alrt.setContentText("You should the path TID to be able to do SEARCH!");
                alrt.showAndWait();
                con.close();
            }
            */

        }
        catch(Exception exp){

        }
    }

    @FXML
    void siginbuttononaction(ActionEvent event) {
    }
    @FXML
    void weshalledit_table(ActionEvent event) throws IOException{
        try {
            String hid,tid,wid;
            hid=edittable_hid.getText();
            wid=edittable_wid.getText();
            tid=edittable_tid.getText();
            if(!((hid.equals(""))&&(wid.equals(""))&&(tid.equals(""))) ) {
                hastable x = his_table.getSelectionModel().getSelectedItem();
                x.setHis_hid(hid);
                x.setHis_tid(tid);
                x.setHis_wid(wid);
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLSTMNT = "UPDATE T_TABLE";
                SQLSTMNT += " SET H_ID=" + x.getHis_hid() + ", W_ID=" + x.getHis_wid() + " WHERE TID=" + x.getHis_tid();
                stmnt.executeQuery(SQLSTMNT);
                con.close();
                Alert alrrt = new Alert(Alert.AlertType.INFORMATION);
                alrrt.setContentText("Table-Cell was Edited Successfully:");
                alrrt.showAndWait();
            }
            else{
                Alert alrrt = new Alert(Alert.AlertType.CONFIRMATION);
                alrrt.setContentText("Please Select a Table to be able to edit it!.");
                alrrt.showAndWait();
            }
        }
        catch(SQLException exp){
            Alert alrt=new Alert(Alert.AlertType.ERROR);
            alrt.setContentText(exp.getMessage());
            alrt.showAndWait();
        }
    }
    @FXML
    public void logoutbtn(MouseEvent event) throws IOException{
        if (event.getClickCount() > 0) GTO(loginfxml, event);
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