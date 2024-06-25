package com.example.demo;
import java.net.URL;
import java.sql.*;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import oracle.jdbc.pool.OracleDataSource;
import java.io.IOException;
import java.util.ResourceBundle;
public class ManagerController implements Initializable {
    ObservableList<hashall> halllist=FXCollections.observableArrayList();
    int enterhalleditctr=0;
    @FXML
    private Label MID_Label;

    @FXML
    private ImageView goEmployees;

    @FXML
    private ImageView goTables;

    @FXML
    private Button go_hall_add;

    @FXML
    private Button go_hall_search;

    @FXML
    private TableView<hashall> halltableview;

    @FXML
    private TableColumn<hashall, String> hidc;

    @FXML
    private TextField hidtf;

    @FXML
    private TableColumn<hashall, String> hnamec;

    @FXML
    private TextField hnametf;

    @FXML
    private TableColumn<hashall, String> kidc;

    @FXML
    private TableColumn<hashall, String> midc;
    //fxmlstrings
    String URL1="jdbc:oracle:thin:@localhost:1521:XE";
    String User="projusersami";
    String Password="654321";
    String loginfxml="hello-view.fxml";
    String slchal="Selectionhall.fxml";
    String addhal="addhall.fxml";
    String edithal="edithall.fxml";
    //fxmlstrings
    private Stage stage;
    private Scene scene;
    private Parent root;
    private Stage choose_h_stage;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            edithall_HIDTF.setEditable(false);
            String mid=HelloController.TF1;
            MID_Label.setText("MID:"+mid);
            hidc.setCellValueFactory(data -> data.getValue().hall_HIDProperty());
            hnamec.setCellValueFactory(data -> data.getValue().hall_HNameProperty());
            kidc.setCellValueFactory(data -> data.getValue().hall_KIDProperty());
            midc.setCellValueFactory(data -> data.getValue().hall_MIDProperty());
            choose_h_stage.initOwner(stage);
            choose_h_stage.initModality(Modality.APPLICATION_MODAL);
        }
        catch (Exception exp){

        }

    }

    @FXML
    void gosearch(ActionEvent event) {
        try {
            edithall_HIDTF.clear();
            edithall_MID.clear();
            edithall_HName.clear();
            edithall_KID.clear();
            halltableview.getItems().clear();
            String h_id, h_name;
            //
            OracleDataSource ods = new OracleDataSource();
            ods.setURL(URL1);
            ods.setUser(User);
            ods.setPassword(Password);
            Connection con = ods.getConnection();
            Statement stmnt = con.createStatement();
            //
            h_id = hidtf.getText();
            h_name = hnametf.getText();
            if (!(h_id.equals(""))) {
                String SQLSTMNT="SELECT HNAME,K_ID,M_ID FROM HALL WHERE HID=";
                SQLSTMNT+=h_id;
                ResultSet rs=stmnt.executeQuery(SQLSTMNT);
                ObservableList<hashall> list=halltableview.getItems();
                while (rs.next()){
                    String hname=rs.getString(1);
                    String kkid=rs.getString(2);
                    String mmid=rs.getString(3);
                    hashall x=new hashall();
                    x.setHall_HID(h_id);
                    x.setHall_HName(hname);
                    x.setHall_KID(kkid);
                    x.setHall_MID(mmid);
                    list.add(x);
                }
                halltableview.setItems(list);
                halltableview.refresh();
                hidtf.clear(); hnametf.clear();
                con.close();
            }

            else if( (h_id.equals("")) && !(h_name.equals("")) ){
                String SQLSTMNT="SELECT HID,K_ID,M_ID FROM HALL WHERE HNAME=";
                SQLSTMNT+="'"+h_name+"'";
                ResultSet rs=stmnt.executeQuery(SQLSTMNT);
                ObservableList<hashall> list=halltableview.getItems();
                while (rs.next()){
                    String hid=rs.getString(1);
                    String kkid=rs.getString(2);
                    String mmid=rs.getString(3);
                    hashall x=new hashall();
                    x.setHall_HID(hid);
                    x.setHall_HName(h_name);
                    x.setHall_KID(kkid);
                    x.setHall_MID(mmid);
                    list.add(x);
                }
                halltableview.setItems(list);
                halltableview.refresh();
                hidtf.clear(); hnametf.clear();
                con.close();

            }
            else if( (h_id.equals("")) && (h_name.equals("")) ){
                Alert alrt=new Alert(Alert.AlertType.ERROR);
                alrt.setContentText("You should fill in at least one path!");
                alrt.showAndWait();
                con.close();
            }
        }
        catch(Exception exp){

        }
    }


    @FXML
    void tableviewclicked(MouseEvent event) {
        try {
            if (event.getClickCount() >1) {
                hashall x=halltableview.getSelectionModel().getSelectedItem();
                edithall_HIDTF.setText(x.getHall_HID());
                edithall_HName.setText(x.getHall_HName());
                edithall_KID.setText(x.getHall_KID());
                edithall_MID.setText(x.getHall_MID());
            }
        }
        catch (Exception exp){

        }
    }

    @FXML
    private Button deletehallbtn;

    @FXML
    private Button edithallbtn;

    @FXML
    private TextField edithall_HIDTF;

    @FXML
    private TextField edithall_HName;

    @FXML
    private TextField edithall_KID;

    @FXML
    private TextField edithall_MID;

    @FXML
    private Button edithall_edit_btn;

    @FXML
    void weshalledit_hall(ActionEvent event) {
        try {
            String hid,hname,kkid,mmid;
            hid=edithall_HIDTF.getText();
            hname=edithall_HName.getText();
            kkid=edithall_KID.getText();
            mmid=edithall_MID.getText();
            if(!((hid.equals(""))&&(hname.equals(""))&&(kkid.equals(""))&&(mmid.equals(""))) ) {
                hashall x = halltableview.getSelectionModel().getSelectedItem();
                x.setHall_HID(hid);
                x.setHall_KID(kkid);
                x.setHall_KID(kkid);
                x.setHall_HName(hname);
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLSTMNT = "UPDATE HALL";
                SQLSTMNT += " SET HNAME=" + "'" + x.getHall_HName() + "'" + ", K_ID=" + x.getHall_KID() + ", M_ID=" + x.getHall_MID() + " WHERE HID=" + x.getHall_HID();
             //   System.out.println(SQLSTMNT);
                stmnt.executeQuery(SQLSTMNT);
                con.close();
                Alert alrrt = new Alert(Alert.AlertType.INFORMATION);
                alrrt.setContentText("Hall-Cell was Edited Successfully:");
                alrrt.showAndWait();
            }
            else{
                Alert alrrt = new Alert(Alert.AlertType.CONFIRMATION);
                alrrt.setContentText("Please Select a HALL to be able to edit it!.");
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
    void goadd(ActionEvent event) {
        try{
            GTONTA(addhal,event);
            Alert alrrt=new Alert(Alert.AlertType.CONFIRMATION);
            alrrt.setContentText("the values of HID,KID and MID are integer values && in between 0 - 1000");
            alrrt.showAndWait();
        }
        catch(Exception exp){

        }
    }
    @FXML
    void gotables(ActionEvent event) throws IOException {
        try {
            GTOA("1Tables.fxml", event);
        }
        catch(Exception exp){

        }

    }
    @FXML
    void godelete(ActionEvent event) {
        try {
            if(   !(halltableview.getSelectionModel().isEmpty())  ) {
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();
                String SQLSTMNT = "DELETE FROM HALL WHERE HID=";
                SQLSTMNT += halltableview.getSelectionModel().getSelectedItem().getHall_HID();
                stmnt.executeQuery(SQLSTMNT);
                con.close();
                Alert alrrt = new Alert(Alert.AlertType.INFORMATION);
                alrrt.setContentText("The Selected Hall::Cell was Deleted Successfully!!");
                alrrt.showAndWait();
            }
            else{
                Alert alrrt = new Alert(Alert.AlertType.WARNING);
                alrrt.setContentText("Please Select a Table you'd like to delete!.");
                alrrt.showAndWait();
            }
        }
        catch(SQLException exp){
            Alert alrrt = new Alert(Alert.AlertType.WARNING);
            alrrt.setContentText(exp.getMessage());
            alrrt.showAndWait();
        }
    }

    @FXML
    private TextField add_hidtf;

    @FXML
    private TextField addhnametf;

    @FXML
    private TextField addkidtf;

    @FXML
    private TextField addmidtf;

    @FXML
    private Button go_add_action;

    @FXML
    void do_add(ActionEvent event) {
        try {
            String a, b, c, d;
            a = add_hidtf.getText();
            b = addhnametf.getText();
            c = addkidtf.getText();
            d = addmidtf.getText();
            if (!(a.equals("") && b.equals("") && c.equals("") && d.equals(""))) {
                hashall x = new hashall();
                x.setHall_HID(a);
                x.setHall_HName(b);
                x.setHall_MID(d);
                x.setHall_KID(c);
                OracleDataSource ods = new OracleDataSource();
                ods.setURL(URL1);
                ods.setUser(User);
                ods.setPassword(Password);
                Connection con = ods.getConnection();
                Statement stmnt = con.createStatement();

                    String SQLSTMNT = "INSERT INTO HALL (HID,HNAME,K_ID,M_ID) VALUES ";
                    SQLSTMNT += "(" + x.getHall_HID() + "," + "'" + x.getHall_HName() + "'" + "," + x.getHall_KID() + "," + x.getHall_MID() + ")";
                    System.out.println(SQLSTMNT);
                    stmnt.executeQuery(SQLSTMNT);
                    con.close();
                    Alert alrt = new Alert(Alert.AlertType.INFORMATION);
                    alrt.setContentText("A Cell Hall was Added Successfully");
                    alrt.showAndWait();
                    add_hidtf.clear();
                    addhnametf.clear();
                    addkidtf.clear();
                    addmidtf.clear();

            } else {
                Alert alrt=new Alert(Alert.AlertType.ERROR);
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









    @FXML
    void siginbuttononaction(ActionEvent event) {

    }
    @FXML
    public void logoutbtn(MouseEvent event) throws IOException{
        if (event.getClickCount() > 0) GTO(loginfxml, event);
    }
    public void GTO(String fxml,MouseEvent event) throws IOException{
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
        stage.show();
    }
    public void GTONTA(String fxml,ActionEvent event) throws IOException{
        Parent root2 = FXMLLoader.load(getClass().getResource(fxml));
        Stage newstage=new Stage();
        newstage.setScene(new Scene(root2));
        newstage.show();
    }
}
