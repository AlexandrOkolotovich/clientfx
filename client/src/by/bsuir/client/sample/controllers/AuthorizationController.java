package by.bsuir.client.sample.controllers;

import java.io.IOException;
import java.net.URL;
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
import org.json.JSONException;
import org.json.JSONObject;

public class AuthorizationController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private PasswordField password_field;

    @FXML
    private Button authSigInButton;

    @FXML
    private TextField login_field;

    @FXML
    private Button back;

    @FXML
    void initialize() {
        back.setOnAction(event -> {
            back.getScene().getWindow().hide();

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

        authSigInButton.setOnAction(event -> {

            try {
                JSONObject userJson = new JSONObject();
                userJson.put("username", login_field.getText().trim());
                userJson.put("password", password_field.getText().trim());

                // client.Client.getInstance().send(userJson.toString());
                Client.getInstance().send(userJson.toString());
                // String status = client.Client.getInstance().get();
                String status = Client.getInstance().get();
                if (!status.equals("nobody")) {
                    openMenu(status);
                } else {
                    System.out.println("Повторите ввод");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка");
                    alert.setHeaderText("Такой пользователь не зарегистрирован!");
                    alert.showAndWait();
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        });
    }

        public void openMenu(String status) {
            if (status.equals("admin")) {
                try {
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(
                            "by/bsuir/client/sample/view/menuAdmin.fxml"));
                    Parent root = loader.load();
                    stage.setTitle("Меню администратора");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (status.equals("user")) {
            //    try {
                    User user = User.getInstance();
                   /* Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource(
                            "by/bsuir/client/sample/view/menuUser.fxml"));
                    Parent root = loader.load();
                    //Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("client/FXML/MenuUser.fxml")));
                    stage.setTitle("Меню пользователя");
                    stage.setResizable(false);
                    stage.setScene(new Scene(root));
                    stage.show();*/

                    authSigInButton.getScene().getWindow().hide();

                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Controller.class.getResource("by/bsuir/client/sample/view/menuUser.fxml"));

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

           //     } catch (IOException  e) {
           //         e.printStackTrace();
           //     }
            } else {
                System.out.println("Повторите ввод");//дописать
            }
        }
    }
