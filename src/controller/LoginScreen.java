package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import database.UserQuery;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginScreen implements Initializable {

    @FXML
    private Label usernameLbl;

    @FXML
    private Label passwordLbl;

    @FXML
    private Button loginBtn;

    @FXML
    private Label zoneLabel;

    @FXML
    private TextField userNameText;

    @FXML
    private TextField passwordText;

    private Locale local = Locale.getDefault();

    private ResourceBundle test;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        if(local.getLanguage().equals("fr")){

            test = ResourceBundle.getBundle("utility/Lang_Fr", local);

            usernameLbl.setText(test.getString("usernameLbl"));
            passwordLbl.setText(test.getString("passwordLbl"));
            loginBtn.setText(test.getString("login"));
        } else {
            test = ResourceBundle.getBundle("utility/Lang_En", local);
        }

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

//        try{
//
//            String user;
//
//            ResultSet rs = UserQuery.getUser(userNameText.getText(), passwordText.getText());
//
//            if(rs.next() != false){
//
//                user = userNameText.getText();
//
//                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
//                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
//                Parent root = loader.load();
//
//                Scene scene = new Scene(root,1156,762);
//                stage.setScene(scene);
//
//                MainScreen mainScreen = loader.getController();
//                mainScreen.getCurrentUser(user);
//
//                stage.show();
//            }else if(userNameText.getText().isEmpty()){
//                throw new NullPointerException(test.getString("usernameError"));
//            } else if (passwordText.getText().isEmpty()) {
//                throw new NullPointerException(test.getString("passwordError"));
//            }else{
//                throw new NullPointerException(test.getString("nullError"));
//            }
//
//        }catch(NullPointerException e){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setTitle(test.getString("inputError"));
//            alert.setHeaderText(test.getString("invalidInput"));
//            alert.setContentText(e.getMessage());
//
//            alert.showAndWait();
//        }
    }
}
