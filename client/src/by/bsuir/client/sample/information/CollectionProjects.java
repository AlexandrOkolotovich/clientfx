package by.bsuir.client.sample.information;

import by.bsuir.client.sample.connectoin.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public final class CollectionProjects{

    private ObservableList<Project> projects = FXCollections.observableArrayList();

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

    public void delete(Project project){
        projects.remove(project);
    }

    public void fillData(){
        try {
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

}

