package by.bsuir.client.sample.information;

import by.bsuir.client.sample.connectoin.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public final class CollectionProjects{

    private ObservableList<Project> projects = FXCollections.observableArrayList();

    private ObservableList<Project> selectedProject = FXCollections.observableArrayList();



    private static CollectionProjects instance;

    public static synchronized CollectionProjects getInstance(){
        if(instance == null){
            instance = new CollectionProjects();
        }
        return instance;
    }

    public ObservableList<Project> getProjects() {
        return projects;
    }

    public ObservableList<Project> getSelectedProjectProjects() {
        return selectedProject;
    }

    public void delete(Project project){
        projects.remove(project);
    }

    public void deletePrTask(Project project){
        selectedProject.remove(project);
    }

    public void change(Project project){

    }

    public void selectProject(Project project){
        if(selectedProject.size()>2){
            Alert alert = new Alert(  Alert.AlertType.WARNING);
            alert.setTitle( "Ошибка" );
            alert.setHeaderText( "В задаче уже выбранно 3 проекта" );
            alert.showAndWait();
        }
        else {
            selectedProject.add(project);
        }
    }

    public void fillData(){
        try {
            projects.removeAll(projects);
            String array = Client.getInstance().get();
            JSONArray newArray = new JSONArray( array );
            int count = newArray.length();
            for(int i = 0; i<count; i++) {
                JSONObject object = newArray.getJSONObject( i );
                Integer id = object.getInt("id");
                String director = object.getString( "director" );
                String operator = object.getString( "operator" );
                String presenter = object.getString( "presenter" );
                String projectName = object.getString( "projectName" );
                int assesment = object.getInt("assesment");
                String format = object.getString( "format" );
                int studioNumber = object.getInt("studioNumber");
                Project project = new Project(id, director, operator, presenter, projectName, assesment, format, studioNumber );
                projects.add( project );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }


    public void fillNewProject(){

        try {
            String array = Client.getInstance().get();
            JSONObject object = new JSONObject(array);

                Integer id = object.getInt("id");
                String director = object.getString( "director" );
                String operator = object.getString( "operator" );
                String presenter = object.getString( "presenter" );
                String projectName = object.getString( "projectName" );
                int assesment = object.getInt("assesment");
                String format = object.getString( "format" );
                int studioNumber = object.getInt("studioNumber");
                Project project = new Project(id, director, operator, presenter, projectName, assesment, format, studioNumber );
                projects.add( project );


        } catch (JSONException | IOException e) {
            e.printStackTrace();
        }


    }



    public Project getLastProject(final Collection c) {
        final Iterator itr = c.iterator();
        Project lastElement = (Project) itr.next();
        while(itr.hasNext()) {
            lastElement = (Project) itr.next();
        }
        return lastElement;
    }


}





