package by.bsuir.client.sample.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import by.bsuir.client.sample.connectoin.Client;
import by.bsuir.client.sample.information.*;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.json.JSONObject;

public class AdminController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button usersButton;

    @FXML
    private Button projectButton;

    @FXML
    private Button tasksButton;

    @FXML
    private Button backButton;

    @FXML
    private Pane userPane;

    @FXML
    private TableView<UserInf> userTable;

    @FXML
    private TableColumn<UserInf, Integer> id;

    @FXML
    private TableColumn<UserInf, String> firstName;

    @FXML
    private TableColumn<UserInf, String> lastName;

    @FXML
    private TableColumn<UserInf, String> email;

    @FXML
    private TableColumn<UserInf, String> userName;

    @FXML
    private TableColumn<UserInf, String> password;

    @FXML
    private Button search;

    @FXML
    private Button delete;

    @FXML
    private Label statusLabel;

    @FXML
    private Pane projectPane;

    @FXML
    private Label statusLabel1;

    @FXML
    private TableView<Project> projectTable;

    @FXML
    private TableColumn<Project, Integer> idProject;

    @FXML
    private TableColumn<Project, String> director;

    @FXML
    private TableColumn<Project, String> operator;

    @FXML
    private TableColumn<Project, String> presenter;

    @FXML
    private TableColumn<Project, String> projectName;

    @FXML
    private TableColumn<Project, Integer> assesment;

    @FXML
    private TableColumn<Project, String> format;

    @FXML
    private TableColumn<Project, Integer> studioNumber;

    @FXML
    private TextField directorField;

    @FXML
    private TextField operatorField;

    @FXML
    private TextField presenterField;

    @FXML
    private TextField projectNameField;

    @FXML
    private TextField assesmentField;

    @FXML
    private TextField formatName;

    @FXML
    private TextField studioNumberField;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button editButton;

    @FXML
    private Pane taskPane;

    @FXML
    private TextField searchField;

    @FXML
    private Label statusLabel11;

    @FXML
    private TextField loginField;

    @FXML
    void handleCliks(ActionEvent event) {

        if(event.getSource()==usersButton){
            userPane.toFront();
        }
        else if(event.getSource()==projectButton){
            projectPane.toFront();
        }
        else if(event.getSource()==tasksButton){
            taskPane.toFront();
        }
        else if(event.getSource()==backButton){
            backButton.getScene().getWindow().hide();

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
        }
    }

    @FXML
    void searchUser() { //изменить название кнопки и поля

        FilteredList<UserInf> filterUsers;
        filterUsers = new FilteredList<>(CollectionUsers.getInstance().getUsers(), e->true);
        searchField.textProperty().addListener((observableValue, oldValue, newValue)->{
            filterUsers.setPredicate((UserInf user)->{

                String newVal = newValue.toLowerCase();
                return  user.getFirstName().toLowerCase().contains(newVal)
                        || user.getLastName().toLowerCase().contains(newVal)
                        || user.getEmail().toLowerCase().contains(newVal)
                        || user.getUserName().toLowerCase().contains(newVal)
                        || user.getPassword().toLowerCase().contains(newVal);

            });
        userTable.setItems(filterUsers);
        });

    }

    public void deleteUser(ActionEvent actionEvent) {
        UserInf selectedUser = (UserInf) userTable.getSelectionModel().getSelectedItem();
        CollectionUsers.getInstance().delete( selectedUser );
        Client.getInstance().send( "deleteUser" );
        Client.getInstance().send( selectedUser.getId());
    }

    @FXML
    void initialize() {
        userInTable();

        projectInTable();

    }

    @FXML
    void addProject(ActionEvent event) {
        Client.getInstance().send("getProject");

        boolean key = true;
        JSONObject projectJson = new JSONObject();
        if( directorField.getText().isEmpty() || directorField.getText()==null ){key = false;}
        else {projectJson.put("director", directorField.getText().trim());}
        if( operatorField.getText().isEmpty() || operatorField.getText()==null){key = false;}
        else{projectJson.put("operator", operatorField.getText().trim());}
        if( presenterField.getText().isEmpty() || presenterField.getText()==null ){key = false;}
        else{projectJson.put("presenter", presenterField.getText().trim());}
        if( projectNameField.getText().isEmpty() || projectNameField.getText()==null ){key = false;}
        else{projectJson.put("projectName", projectNameField.getText().trim());}
        if( assesmentField.getText().isEmpty() || assesment.getText()==null ){key = false;}
        else{projectJson.put("assesment", assesmentField.getText().trim());}
        if( formatName.getText().isEmpty() || formatName.getText()==null ){key = false;}
        else{projectJson.put("format", formatName.getText().trim());}
        if( studioNumberField.getText().isEmpty() || studioNumberField.getText()==null ){key = false;}
        else{projectJson.put("studioNumber", studioNumberField.getText().trim());}
        if(key = true) {
            Client.getInstance().send( projectJson.toString() );
            Project project = Project.getInstance();

        }else {
            Alert alert = new Alert(  Alert.AlertType.WARNING);
            alert.setTitle( "Ошибка" );
            alert.setHeaderText( "Заполните все поля!" );
            alert.showAndWait();
        }
    }

    public void userInTable(){
        CollectionUsers.getInstance().fillData();
        id.setCellValueFactory(new PropertyValueFactory<UserInf, Integer>("id"));
        firstName.setCellValueFactory(new PropertyValueFactory<UserInf, String>("firstName"));
        lastName.setCellValueFactory(new PropertyValueFactory<UserInf, String>("lastName"));
        email.setCellValueFactory(new PropertyValueFactory<UserInf, String>("email"));
        userName.setCellValueFactory(new PropertyValueFactory<UserInf, String>("userName"));
        password.setCellValueFactory(new PropertyValueFactory<UserInf, String>("password"));
        userTable.setItems( CollectionUsers.getInstance().getUsers());
    }

    public void projectInTable(){
        CollectionProjects.getInstance().fillData();
        idProject.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
        director.setCellValueFactory(new PropertyValueFactory<Project, String>("director"));
        operator.setCellValueFactory(new PropertyValueFactory<Project, String>("operator"));
        presenter.setCellValueFactory(new PropertyValueFactory<Project, String>("presenter"));
        projectName.setCellValueFactory(new PropertyValueFactory<Project, String>("projectName"));
        assesment.setCellValueFactory(new PropertyValueFactory<Project, Integer>("assesment"));
        format.setCellValueFactory(new PropertyValueFactory<Project, String>("format"));
        studioNumber.setCellValueFactory(new PropertyValueFactory<Project, Integer>("studioNumber"));
        projectTable.setItems( CollectionProjects.getInstance().getProjects());
    }

   /* private boolean searchLogin(String str) throws IOException {
        Client.getInstance().send( "searchLogin" );
        Client.getInstance().send(str);
        String search = Client.getInstance().get();
        if(search.equals("nobody")){
            return false;
        }
        else return true;
    }*/
}

