package by.bsuir.client.sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import by.bsuir.client.sample.connectoin.Client;
import by.bsuir.client.sample.information.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.JSONObject;

public class SignUpController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField signUpEmail;

    @FXML
    private PasswordField password_field;

    @FXML
    private TextField signUpLastName;

    @FXML
    private TextField signUpName;

    @FXML
    private TextField login_field;

    @FXML
    private Button signUpButton;

    @FXML
    private Button back;

    @FXML
    void initialize() {
        signUpButton.setOnAction(actionEvent -> {
        //    Client.getInstance().send("registration");

            boolean key = true;
            JSONObject userJson = new JSONObject();
            if( signUpName.getText().isEmpty() || signUpName.getText()==null ){key = false;}
            else {userJson.put("firstname", signUpName.getText().trim());}
            if( signUpLastName.getText().isEmpty() || signUpLastName.getText()==null){key = false;}
            else{userJson.put("lastname", signUpLastName.getText().trim());}
            if( signUpEmail.getText().isEmpty() || signUpEmail.getText()==null ){key = false;}
            else{userJson.put("email", signUpEmail.getText().trim());}
            if( login_field.getText().isEmpty() || login_field.getText()==null ){key = false;}
            else{userJson.put("username", login_field.getText().trim());}
            if( password_field.getText().isEmpty() || password_field.getText()==null ){key = false;}
            else{userJson.put("password", password_field.getText().trim());}
            if(key == true) {//false
             //   client.Client.getInstance().send( userJson.toString() );
                Client.getInstance().send( userJson.toString() );
                User user = User.getInstance();
                try {
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load( Objects.requireNonNull( getClass().getClassLoader().getResource(
                            "by/bsuir/client/sample/view/sample.fxml" ) ) );
                    stage.setTitle( "Меню пользователя" );
                    stage.setResizable( false );
                    stage.setScene( new Scene( root ) );
                    stage.show();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }else {
                Alert alert = new Alert(  Alert.AlertType.WARNING);
                alert.setTitle( "Ошибка" );
                alert.setHeaderText( "Заполните все поля!" );
                alert.showAndWait();
            }
        });

        back.setOnAction(event -> {
            back.getScene().getWindow().hide();
            Client.getInstance().send("back");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Controller.class.getResource("/by/bsuir/client/sample/view/sample.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();

        });
    }
}
