package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import database.JDBC;

import java.util.Objects;

/**
 * Main entry point of the program before passing onto the MainScreen controller.
 * Program allows a user to manage customers and manage appointments scheduled for those customers.
 *
 * @author José L Dávila Montalvo
 * */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
        stage.setScene(new Scene(root,278,374));
        stage.show();

    }

    public static void main(String[] args){

        JDBC.openConnection();
        launch(args);
    }
}
