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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

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

    private ResourceBundle languageDisplay;

    private String user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(local.getLanguage().equals("fr")){
            languageDisplay = ResourceBundle.getBundle("utility/Lang_Fr", local);
            usernameLbl.setText(languageDisplay.getString("usernameLbl"));
            passwordLbl.setText(languageDisplay.getString("passwordLbl"));
            loginBtn.setText(languageDisplay.getString("login"));
        } else {
            languageDisplay = ResourceBundle.getBundle("utility/Lang_En", local);
        }
        ZoneId localZone = ZoneId.of(TimeZone.getDefault().getID());
        zoneLabel.setText(String.valueOf(localZone));
    }

    public void userLogin(ActionEvent actionEvent) throws IOException, SQLException {

        Instant loginTime = Instant.now();

        try{

            ResultSet rs = UserQuery.getUser(userNameText.getText(), passwordText.getText());

            if(rs.next() != false){

                user = userNameText.getText();

                FileWriter loginAttempt = new FileWriter("D:\\College\\WGU - Computer Science\\C195 Software 2\\login_activity.txt", true);
                BufferedWriter bw = new BufferedWriter(loginAttempt);
                bw.write(user + " " + loginTime + " UTC," + " login successfull");
                bw.newLine();
                bw.close();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainScreen.fxml"));
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                Parent root = loader.load();

                Scene scene = new Scene(root,1128,793);
                stage.setScene(scene);

                MainScreen mainScreen = loader.getController();
                mainScreen.getCurrentUser(user);

                stage.show();
            }else if(userNameText.getText().isEmpty()){

                FileWriter loginAttempt = new FileWriter("D:\\College\\WGU - Computer Science\\C195 Software 2\\login_activity.txt", true);
                BufferedWriter bw = new BufferedWriter(loginAttempt);
                bw.write(user + " " + loginTime + " UTC," + " login unsuccessfull");
                bw.newLine();
                bw.close();

                throw new NullPointerException(languageDisplay.getString("usernameError"));
            } else if (passwordText.getText().isEmpty()) {

                FileWriter loginAttempt = new FileWriter("D:\\College\\WGU - Computer Science\\C195 Software 2\\login_activity.txt", true);
                BufferedWriter bw = new BufferedWriter(loginAttempt);
                bw.write(user + " " + loginTime + " UTC," + " login unsuccessfull");
                bw.newLine();
                bw.close();

                throw new NullPointerException(languageDisplay.getString("passwordError"));
            }else{

                FileWriter loginAttempt = new FileWriter("D:\\College\\WGU - Computer Science\\C195 Software 2\\login_activity.txt", true);
                BufferedWriter bw = new BufferedWriter(loginAttempt);
                bw.write(user + " " + loginTime + " login unsuccessfull");
                bw.newLine();
                bw.close();

                throw new NullPointerException(languageDisplay.getString("nullError"));
            }

        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(languageDisplay.getString("inputError"));
            alert.setHeaderText(languageDisplay.getString("invalidInput"));
            alert.setContentText(e.getMessage());

            alert.showAndWait();
        }
    }
}
