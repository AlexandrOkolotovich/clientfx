package by.bsuir.client.sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.bsuir.client.sample.connectoin.Client;
import by.bsuir.client.sample.information.CollectionProjects;
import by.bsuir.client.sample.information.Project;
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

public class UserController{

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button projectButton;

    @FXML
    private Button tasksButton;

    @FXML
    private Button backButton;

    @FXML
    private Pane taskPane;

    @FXML
    private Label statusLabel11;

    @FXML
    private TextField el11;

    @FXML
    private TextField el12;

    @FXML
    private TextField el13;

    @FXML
    private TextField el14;

    @FXML
    private TextField el15;

    @FXML
    private TextField el16;

    @FXML
    private TextField el17;

    @FXML
    private TextField el18;

    @FXML
    private TextField el19;

    @FXML
    private TextField el21;

    @FXML
    private TextField el22;

    @FXML
    private TextField el23;

    @FXML
    private TextField el24;

    @FXML
    private TextField el25;

    @FXML
    private TextField el26;

    @FXML
    private TextField el27;

    @FXML
    private TextField el28;

    @FXML
    private TextField el29;

    @FXML
    private TextField el31;

    @FXML
    private TextField el32;

    @FXML
    private TextField el33;

    @FXML
    private TextField el34;

    @FXML
    private TextField el35;

    @FXML
    private TextField el36;

    @FXML
    private TextField el37;

    @FXML
    private TextField el38;

    @FXML
    private TextField el39;

    @FXML
    private TextField el211;

    @FXML
    private TextField el210;

    @FXML
    private TextField el110;

    @FXML
    private TextField el310;

    @FXML
    private TextField el111;

    @FXML
    private TextField el112;

    @FXML
    private TextField el212;

    @FXML
    private TextField el311;

    @FXML
    private TextField el312;

    @FXML
    private Button singlSolutionButton;

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
    private TextField searchPrField;

    @FXML
    private TableView<Project> selectedPrTable;

    @FXML
    private TableColumn<Project, String> selectedProject;

    @FXML
    void handleCliks(ActionEvent event) {
        if(event.getSource()==projectButton){
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
    void searchProject(ActionEvent event) {
        FilteredList<Project> filterProjects;
        filterProjects = new FilteredList<>(CollectionProjects.getInstance().getProjects(), e->true);
        searchPrField.textProperty().addListener((observableValue, oldValue, newValue)->{
            filterProjects.setPredicate((Project project)->{

                String newVal = newValue.toLowerCase();
                return  project.getDirector().toLowerCase().contains(newVal)
                        || project.getOperator().toLowerCase().contains(newVal)
                        || project.getPresenter().toLowerCase().contains(newVal)
                        || project.getProjectname().toLowerCase().contains(newVal)
                        || project.getFormat().toLowerCase().contains(newVal);

            });
            projectTable.setItems(filterProjects);
        });
    }

    @FXML
    void singlSolution(ActionEvent event) throws IOException {

        boolean key = true;
        JSONObject taskJson = new JSONObject();

        if( el11.getText().isEmpty() || el11.getText()==null || !checkNum(el11.getText()) ){key = false;}
        else {taskJson.put("el11", el11.getText().trim()); }
        if( el12.getText().isEmpty() || el12.getText()==null || !checkNum(el12.getText())){key = false;}
        else {taskJson.put("el12", el12.getText().trim()); }
        if( el13.getText().isEmpty() || el13.getText()==null || !checkNum(el13.getText())){key = false;}
        else {taskJson.put("el13", el13.getText().trim()); }
        if( el21.getText().isEmpty() || el21.getText()==null || !checkNum(el21.getText())){key = false;}
        else {taskJson.put("el21", el21.getText().trim()); }
        if( el22.getText().isEmpty() || el22.getText()==null || !checkNum(el22.getText())){key = false;}
        else {taskJson.put("el22", el22.getText().trim()); }
        if( el23.getText().isEmpty() || el23.getText()==null || !checkNum(el23.getText())){key = false;}
        else {taskJson.put("el23", el23.getText().trim()); }
        if( el24.getText().isEmpty() || el24.getText()==null || !checkNum(el24.getText())){key = false;}
        else {taskJson.put("el24", el24.getText().trim()); }
        if( el25.getText().isEmpty() || el25.getText()==null|| !checkNum(el25.getText()) ){key = false;}
        else {taskJson.put("el25", el25.getText().trim()); }
        if( el26.getText().isEmpty() || el26.getText()==null || !checkNum(el26.getText())){key = false;}
        else {taskJson.put("el26", el26.getText().trim()); }
        if( el27.getText().isEmpty() || el27.getText()==null || !checkNum(el27.getText())){key = false;}
        else {taskJson.put("el27", el27.getText().trim()); }
        if( el28.getText().isEmpty() || el28.getText()==null || !checkNum(el28.getText())){key = false;}
        else {taskJson.put("el28", el28.getText().trim()); }
        if( el29.getText().isEmpty() || el29.getText()==null || !checkNum(el29.getText())){key = false;}
        else {taskJson.put("el29", el29.getText().trim()); }
        if( el210.getText().isEmpty() || el210.getText()==null || !checkNum(el210.getText())){key = false;}
        else {taskJson.put("el210", el210.getText().trim()); }
        if( el211.getText().isEmpty() || el211.getText()==null || !checkNum(el211.getText())){key = false;}
        else {taskJson.put("el211", el211.getText().trim()); }
        if( el212.getText().isEmpty() || el212.getText()==null || !checkNum(el212.getText())){key = false;}
        else {taskJson.put("el212", el212.getText().trim()); }
        if( el31.getText().isEmpty() || el31.getText()==null || !checkNum(el31.getText())){key = false;}
        else {taskJson.put("el31", el31.getText().trim()); }
        if( el32.getText().isEmpty() || el32.getText()==null || !checkNum(el32.getText())){key = false;}
        else {taskJson.put("el32", el32.getText().trim()); }
        if( el33.getText().isEmpty() || el33.getText()==null || !checkNum(el33.getText())){key = false;}
        else {taskJson.put("el33", el33.getText().trim()); }
        if( el34.getText().isEmpty() || el34.getText()==null || !checkNum(el34.getText())){key = false;}
        else {taskJson.put("el34", el34.getText().trim()); }
        if( el35.getText().isEmpty() || el35.getText()==null || !checkNum(el35.getText())){key = false;}
        else {taskJson.put("el35", el35.getText().trim()); }
        if( el36.getText().isEmpty() || el36.getText()==null || !checkNum(el36.getText())){key = false;}
        else {taskJson.put("el36", el36.getText().trim()); }
        if( el37.getText().isEmpty() || el37.getText()==null || !checkNum(el37.getText())){key = false;}
        else {taskJson.put("el37", el37.getText().trim()); }
        if( el38.getText().isEmpty() || el38.getText()==null || !checkNum(el38.getText())){key = false;}
        else {taskJson.put("el38", el38.getText().trim()); }
        if( el39.getText().isEmpty() || el39.getText()==null || !checkNum(el39.getText())){key = false;}
        else {taskJson.put("el39", el39.getText().trim()); }
        if( el310.getText().isEmpty() || el310.getText()==null || !checkNum(el310.getText())){key = false;}
        else {taskJson.put("el310", el310.getText().trim()); }
        if( el311.getText().isEmpty() || el311.getText()==null || !checkNum(el311.getText())){key = false;}
        else {taskJson.put("el311", el311.getText().trim()); }
        if( el312.getText().isEmpty() || el312.getText()==null || !checkNum(el312.getText())){key = false;}
        else {taskJson.put("el312", el312.getText().trim()); }

        if( el14.getText().isEmpty() || el14.getText()==null || el14.getText()==el31.getText()|| !checkNum(el14.getText())){key = false;}
        else {taskJson.put("el14", el14.getText().trim()); }
        if( el15.getText().isEmpty() || el15.getText()==null || el15.getText()==el32.getText() || !checkNum(el15.getText())){key = false;}
        else {taskJson.put("el15", el15.getText().trim()); }
        if( el16.getText().isEmpty() || el16.getText()==null || el16.getText()==el33.getText()|| !checkNum(el16.getText())){key = false;}
        else {taskJson.put("el16", el16.getText().trim()); }
        if( el17.getText().isEmpty() || el17.getText()==null || el17.getText()==el34.getText()|| !checkNum(el17.getText()) ){key = false;}
        else {taskJson.put("el17", el17.getText().trim()); }
        if( el18.getText().isEmpty() || el18.getText()==null || el18.getText()==el35.getText()|| !checkNum(el18.getText())){key = false;}
        else {taskJson.put("el18", el18.getText().trim()); }
        if( el19.getText().isEmpty() || el19.getText()==null || el19.getText()==el35.getText()|| !checkNum(el19.getText())){key = false;}
        else {taskJson.put("el19", el19.getText().trim()); }
        if( el110.getText().isEmpty() || el110.getText()==null || el110.getText()==el36.getText()|| !checkNum(el110.getText())){key = false;}
        else {taskJson.put("el110", el110.getText().trim()); }
        if( el111.getText().isEmpty() || el111.getText()==null || el111.getText()==el37.getText()|| !checkNum(el11.getText())){key = false;}
        else {taskJson.put("el111", el111.getText().trim()); }
        if( el112.getText().isEmpty() || el112.getText()==null || el112.getText()==el38.getText()|| !checkNum(el112.getText())){key = false;}
        else {taskJson.put("el112", el112.getText().trim()); }

        if(key) {
            Client.getInstance().send("getSinglSolution");

            Client.getInstance().send(taskJson.toString());

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/by/bsuir/client/sample/view/Task.fxml"));

            loader.load();

            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
            stage.setTitle("Меню задания");

        }
        else {
            Alert alert = new Alert(  Alert.AlertType.WARNING);
            alert.setTitle( "Ошибка" );
            alert.setHeaderText( "Заполните все поля корректно!" );
            alert.showAndWait();
        }

    }

    @FXML
    void initialize() {
        projectInTable();
    }

    public void projectInTable() {

        CollectionProjects.getInstance().fillData();
        idProject.setCellValueFactory(new PropertyValueFactory<Project, Integer>("id"));
        director.setCellValueFactory(new PropertyValueFactory<Project, String>("director"));
        operator.setCellValueFactory(new PropertyValueFactory<Project, String>("operator"));
        presenter.setCellValueFactory(new PropertyValueFactory<Project, String>("presenter"));
        projectName.setCellValueFactory(new PropertyValueFactory<Project, String>("projectname"));
        assesment.setCellValueFactory(new PropertyValueFactory<Project, Integer>("assesment"));
        format.setCellValueFactory(new PropertyValueFactory<Project, String>("format"));
        studioNumber.setCellValueFactory(new PropertyValueFactory<Project, Integer>("studionumber"));
        projectTable.setItems(CollectionProjects.getInstance().getProjects());

    }

    @FXML
    void adPrForTask(ActionEvent event) {
        Project selectedProject = (Project) projectTable.getSelectionModel().getSelectedItem();
        CollectionProjects.getInstance().selectProject( selectedProject );
        selectedProjectInTable();
    }

    @FXML
    void deletePr(ActionEvent event) {
        Project selectedProject = (Project) selectedPrTable.getSelectionModel().getSelectedItem();
        CollectionProjects.getInstance().deletePrTask( selectedProject );
    }

    public void selectedProjectInTable(){
        selectedProject.setCellValueFactory(new PropertyValueFactory<Project, String>("projectname"));
        selectedPrTable.setItems(CollectionProjects.getInstance().getSelectedProjectProjects());
    }

    private boolean checkNum(String source) {
        Pattern pattern = Pattern.compile("^([0-9]+)$");
        Matcher matcher = pattern.matcher(source);

        if (!matcher.matches()) {
            return false;
        } else {
            return true;
        }
    }
}

