package by.bsuir.server.database;

public class Project {
    private int idprojects;
    private String director;
    private String operator;
    private String presenter;
    private String projectname;
    private int assesment;
    private String format;
    private int studionumber;

    public Project(){}

    public Project(int idprojects, String director, String operator, String presenter, String projectname, int assesment, String format, int studionumber) {
        this.idprojects = idprojects;
        this.director = director;
        this.operator = operator;
        this.presenter = presenter;
        this.projectname = projectname;
        this.assesment = assesment;
        this.format = format;
        this.studionumber = studionumber;
    }

    public int getIdprojects() {
        return idprojects;
    }

    public void setIdprojects(int idprojects) {
        this.idprojects = idprojects;
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
