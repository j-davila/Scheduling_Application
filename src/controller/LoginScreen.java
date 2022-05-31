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
import utility.UserQuery;

import javax.swing.plaf.synth.Region;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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

    public void userLogin(ActionEvent actionEvent) throws IOException, SQLException {

        /*Remove after testing*/
        String user = userNameText.getText();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent root = loader.load();

        Scene scene = new Scene(root,1156,762);
        stage.setScene(scene);

        MainScreen mainScreen = loader.getController();
        mainScreen.getCurrentUser(user);

        stage.show();
        /*Remove after testing*/


        //Login code
        // If login successful then go to mainScreen

//        String user;
//
//        ResultSet rs = UserQuery.getUser(userNameText.getText(), passwordText.getText());
//
//        if(rs.next() != false){
//
//            user = userNameText.getText();
//
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
//            Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
//            Parent root = loader.load();
//
//            Scene scene = new Scene(root,1156,762);
//            stage.setScene(scene);
//
//            MainScreen mainScreen = loader.getController();
//            mainScreen.getCurrentUser(user);
//
//            stage.show();
//        }
    }
}
