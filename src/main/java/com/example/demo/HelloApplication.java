package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 830, 476);
        stage.setTitle("ABYAT");
        stage.setScene(scene);
        stage.setResizable(false);
        Image x=new Image("C:\\Users\\hp\\Downloads\\democashierfinale\\demo\\demo\\src\\main\\resources\\com\\example\\demo\\316741304_1850431091984088_2968029368833723444_n.png");
        stage.getIcons().add(x);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}