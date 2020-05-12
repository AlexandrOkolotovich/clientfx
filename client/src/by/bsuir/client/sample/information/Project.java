package by.bsuir.client.sample.information;

import by.bsuir.client.sample.connectoin.Client;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Project {
    private int id;
    private String director;
    private String operator;
    private String presenter;
    private String projectname;
    private int assesment;
    private String format;
    private int studionumber;

    private static Project instance;

    public static synchronized Project getInstance(){
        if(instance == null){
            instance = new Project();
        }
        return instance;
    }

    public Project(int id, String director, String operator, String presenter, String projectname, int assesment, String format, int studionumber) {
        this.id = id;
        this.director = director;
        this.operator = operator;
        this.presenter = presenter;
        this.projectname = projectname;
        this.assesment = assesment;
        this.format = format;
        this.studionumber = studionumber;
    }

    public Project() {
        try {

            String str = Client.getInstance().get();

            JSONObject json = new JSONObject(str);

                id = json.getInt("id");
                director = json.getString("director");
                operator = json.getString("operator");
                presenter = json.getString("presenter");
                projectname = json.getString("projectName");
                assesment = json.getInt("assesment");
                format = json.getString("format");
                studionumber = json.getInt("studioNumber");


        } catch(JSONException | IOException e){
            System.err.println(e);
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public String getProjectname() {
        return projectname;
    }

    public void setProjectname(String projectname) {
        this.projectname = projectname;
    }

    public int getAssesment() {
        return assesment;
    }

    public void setAssesment(int assesment) {
        this.assesment = assesment;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getStudionumber() {
        return studionumber;
    }

    public void setStudionumber(int studionumber) {
        this.studionumber = studionumber;
    }
}


