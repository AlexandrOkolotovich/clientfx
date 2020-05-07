package by.bsuir.client.sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            boolean key = true;
            JSONObject userJson = new JSONObject();
            if( signUpName.getText().isEmpty() || signUpName.getText()==null
                    || signUpName.getText().length()<4 || signUpName.getText().length()>12
            ||checkName(signUpName.getText())==false){key = false;}
            else {userJson.put("firstname", signUpName.getText().trim());}
            if( signUpLastName.getText().isEmpty() || signUpLastName.getText()==null
                    || signUpLastName.getText().length()<4 || signUpLastName.getText().length()>12
                    ||checkName(signUpLastName.getText())==false){key = false;}
            else{userJson.put("lastname", signUpLastName.getText().trim());}
            if( signUpEmail.getText().isEmpty() || signUpEmail.getText()==null
                    || signUpEmail.getText().length()<4 || signUpEmail.getText().length()>32
            || checkMail(signUpEmail.getText())==false){key = false;}
            else{userJson.put("email", signUpEmail.getText().trim());}
            if( login_field.getText().isEmpty() || login_field.getText()==null
                    || login_field.getText().length()<4 || login_field.getText().length()>12
                    || checkUserName(login_field.getText())==false){key = false;}
            else{userJson.put("username", login_field.getText().trim());}
            if( password_field.getText().isEmpty() || password_field.getText()==null
                    || password_field.getText().length()<4 || password_field.getText().length()>12){key = false;}
            else{userJson.put("password", password_field.getText().trim());}
            if(key == true) {//false
             //   client.Client.getInstance().send( userJson.toString() );
                Client.getInstance().send( userJson.toString() );
                User user = User.getInstance();
                try {
                    signUpButton.getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("/by/bsuir/client/sample/view/sample.fxml"));

                    loader.load();

                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
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
            loader.setLocation(getClass().getResource("/by/bsuir/client/sample/view/sample.fxml"));

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

    private boolean checkMail(String source){
        Pattern pattern = Pattern.compile("^([a-z0-9_\\.-]+)@([a-z0-9_\\.-]+)\\.([a-z\\.]{2,6})$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(source);

        if (!matcher.matches()) {
            return false;
        }
        else{
            return true;
        }
    }

    private boolean checkName(String source){
        Pattern pattern = Pattern.compile("^([А-Я][а-я]+)$");
        Matcher matcher = pattern.matcher(source);

        if (!matcher.matches()) {
            System.out.println("false");
            return false;
        }
        else{
            System.out.println("true");
            return true;
        }
    }

    private boolean checkUserName(String source){
        Pattern pattern = Pattern.compile("^([A-z0-9_\\.-]+)$");
        Matcher matcher = pattern.matcher(source);

        if (!matcher.matches()) {
            return false;
        }
        else{
            return true;
        }
    }
}

