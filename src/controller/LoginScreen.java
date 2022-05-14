package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {

    @FXML
    private TextField userNameText;

    @FXML
    private TextField passwordText;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void userLogin(ActionEvent actionEvent) throws IOException {

        // If login successful then go to mainScreen, open connection to DB

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainScreen.fxml")));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root,1100,450);
        stage.setScene(scene);
        stage.show();
    }
}
