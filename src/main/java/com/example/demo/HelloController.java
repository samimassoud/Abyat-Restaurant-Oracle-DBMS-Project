package com.example.demo;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import oracle.jdbc.pool.OracleDataSource;
import java.io.IOException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    public static String SQLSTMNT;
    public static String SQLSTMNT2;
    String URL="jdbc:oracle:thin:@localhost:1521:XE";
    String User="projusersami";
    String Password="654321";
    String mngfxml="1Edit.fxml";
    String cashfxml="homepage.fxml";
    public static String TF1;
    public static String TF2;
    @FXML
    private Button buttonlogo;
    @FXML
    private TextField LoginUsername;
    @FXML
    private PasswordField LoginPassword;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(java.net.URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void siginbuttononaction(ActionEvent event) throws IOException {
        TF1=LoginUsername.getText();
        TF2=LoginPassword.getText();
        if(TF1.isBlank() || TF2.isBlank()){
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Caution!");
            alert.setContentText("You have to fill in both\n Username and Password in order to log in.");
            alert.showAndWait();

        }
        try {

           // DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
           // Connection con=DriverManager.getConnection(URL,User,Password);
            OracleDataSource ods=new OracleDataSource();
            ods.setURL(URL);
            ods.setUser(User);
            ods.setPassword(Password);
            Connection con=ods.getConnection();
             SQLSTMNT="SELECT MID FROM M_MANAGER WHERE MID=";
            SQLSTMNT2="SELECT CID FROM CASHIER WHERE CID=";
            SQLSTMNT+=TF1;
            SQLSTMNT2+=TF1;
            Statement stmnt=con.createStatement();
            ResultSet rs=stmnt.executeQuery(SQLSTMNT);
            if(  (rs.next()) ){
                if(TF2.equals(TF1)) {
                    if(TF1.equals("900")) {
                        GTO(mngfxml, event);
                    }
                    else {
                        GTO("managerwatehouse.fxml",event);
                    }
                    con.close();
                }
                else if(!(TF2.equals(TF1)) && !(TF2.isBlank()) ){
                    Alert WRNPSW=new Alert(Alert.AlertType.ERROR);
                    WRNPSW.setTitle("Caution!");
                    WRNPSW.setContentText("Wrong Password Try Again!");
                    WRNPSW.showAndWait();
                    con.close();
                }
            }
            else  {
                rs=stmnt.executeQuery(SQLSTMNT2);
                if( (rs.next()) ) {
                    if(TF2.equals(TF1)) {
                        GTO(cashfxml, event);
                        con.close();
                    }
                    else if(!(TF2.equals(TF1)) && !(TF2.isBlank()) ){
                        Alert WRNPSW=new Alert(Alert.AlertType.ERROR);
                        WRNPSW.setTitle("Caution!");
                        WRNPSW.setContentText("Wrong Password Try Again!");
                        WRNPSW.showAndWait();
                        con.close();
                    }
                }

            }




        } catch (Exception e) {
            Alert alert=new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Caution!");
            alert.setContentText(e.toString());
            alert.showAndWait();
            e.printStackTrace();
        }




    }

    @FXML
    private Hyperlink instagram;
    @FXML
    private Hyperlink facebook;

    @FXML
    void gohyperlink_fb(ActionEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://ar-ar.facebook.com/AbyatRest/"));
        }
        catch(Exception exp){

        }
    }
    @FXML
    void gohyperlink_in(ActionEvent event){
        try {
            Desktop.getDesktop().browse(new URI("https://www.instagram.com/abyatrest/"));
        }
        catch(Exception exp){

        }

    }

    public void GTO(String fxml,ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource(fxml));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        javafx.scene.image.Image x=new Image("C:\\Users\\hp\\Downloads\\democashierfinale\\demo\\demo\\src\\main\\resources\\com\\example\\demo\\316741304_1850431091984088_2968029368833723444_n.png");
        stage.getIcons().add(x);
        stage.show();
    }



}