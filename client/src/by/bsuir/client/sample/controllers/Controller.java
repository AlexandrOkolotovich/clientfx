package by.bsuir.client.sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import by.bsuir.client.sample.connectoin.Client;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button authorization;

    @FXML
    private Button registration;

    @FXML
    private Button exit;

    @FXML
    void initialize() {
        registration.setOnAction(actionEvent -> {
            try {
                Stage stage = new Stage();                                                                      //переделать пути
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "by/bsuir/client/sample/view/registration.fxml")));
                stage.setTitle("Регистрация");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
               // client.Client.getInstance().send("registration");
                Client.getInstance().send("registration");
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        authorization.setOnAction(actionEvent -> {
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource(
                        "by/bsuir/client/sample/view/authorization.fxml")));
                stage.setTitle("Авторизация");
                stage.setResizable(false);
                stage.setScene(new Scene(root));
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
                //client.Client.getInstance().send("authorization");
                Client.getInstance().send("authorization");
                stage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        });

        exit.setOnAction(actionEvent -> {
            Client.getInstance().send("exit");
            Client.getInstance().close();
            Stage stage = (Stage) exit.getScene().getWindow();
            stage.close();
        });

    }
}
    /*public void registration(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();                                                                      //переделать пути
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("client/FXML/Registration.fxml")));
            stage.setTitle("Регистрация");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            client.Client.getInstance().send("registration");
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }*/

    /*public void authorization(ActionEvent actionEvent) {
        try {
            Stage stage = new Stage();                                                                      //переделать путь
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("client/FXML/Authorization.fxml")));
            stage.setTitle("Авторизация");
            stage.setResizable(false);
            stage.setScene(new Scene(root));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(((Node)actionEvent.getSource()).getScene().getWindow());
            client.Client.getInstance().send("authorization");
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }*/
/*
    public void exit(ActionEvent actionEvent) {
        Client.getInstance().send("exit");
        Client.getInstance().close();
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }*/


