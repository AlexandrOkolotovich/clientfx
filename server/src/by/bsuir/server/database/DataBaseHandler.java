package by.bsuir.server.database;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.*;

public class DataBaseHandler extends Configs {
    Connection dbConnection;

    public Connection getDbConnection()
            throws ClassNotFoundException, SQLException{
        String connectionSting="jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName  + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection=DriverManager.getConnection(connectionSting, dbUser, dbPass);

        return dbConnection;
    }

    public String signUpUser(String firstName, String lastName, String email,
                             String userName, String password){
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_FIRSTNAME + "," + Const.USER_LASTNAME + "," +
                Const.USER_EMAIL + "," + Const.USER_USERNAME + "," + Const.USER_PASSWORD + ")" +
                "VALUES(?,?,?,?,?)";



        try {
                PreparedStatement prSt = getDbConnection().prepareStatement(insert);
                prSt.setString(1, firstName);
                prSt.setString(2, lastName);
                prSt.setString(3, email);
                prSt.setString(4, userName);
                prSt.setString(5, password);
                prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject userJson = new JSONObject();
        try {
            userJson.put("firstName", firstName);
            userJson.put("lastName", lastName);
            userJson.put("email", email);
            userJson.put("userName", userName);
            userJson.put("password", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userJson.toString();
    }

    public String checkUser(String login){
        JSONObject userJson = new JSONObject();
        User user = new User();
        String result =null;
        ResultSet resSet = null;
        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_USERNAME + "=?";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            ResultSet rs = prSt.executeQuery();

            if (rs.next() == false) {
                result = "nobody";
            } else
            { do {

                user.setUserName(rs.getString(5));
                result = user.getUserName();


            } while (rs.next());

                result = user.getUserName();
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public String signInUser(String login, String password) throws SQLException {
        JSONObject userJson = new JSONObject();
        User user = new User();
        String result =null;
        try {
            // Statement stmt = getDbConnection().createStatement();
            String select = "SELECT * FROM "+ Const.USER_TABLE + " WHERE "+ Const.USER_USERNAME +
                    "= ?";
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            //prep.setString(2, password);
            ResultSet rs = prSt.executeQuery();
            if (rs.next() == false) {
                result = "nobody";
            } else
            { do {
                user.setID(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setUserName(rs.getString(5));
                user.setPassword(rs.getString(6));

                userJson.put("id", user.getID());
                userJson.put("firstName", user.getFirstName());
                userJson.put("lastName", user.getLastName());
                userJson.put("email", user.getEmail());
                userJson.put("userName", user.getUserName());
                userJson.put("password", user.getPassword());
            } while (rs.next());
                result = userJson.toString();
            }

        } catch (ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        }
        //if(user.getName().equals("null")) result = user.getName();

        return result;
    }

    public String getUsers() {
        User user;
        JSONObject userJson;
        JSONArray users = new JSONArray(  );
        try {

            String select = "SELECT * FROM "+Const.USER_TABLE;
            PreparedStatement prep1 = getDbConnection().prepareStatement( select );
            ResultSet rs = prep1.executeQuery();
            while (rs.next()){

                user = new User();
                user.setID(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setUserName(rs.getString(5));
                user.setPassword(rs.getString(6));

                userJson = new JSONObject();
                userJson.put("id", user.getID());
                userJson.put("firstName", user.getFirstName());
                userJson.put("lastName", user.getLastName());
                userJson.put("email", user.getEmail());
                userJson.put("userName", user.getUserName());
                userJson.put("password", user.getPassword());

                users.put( userJson );
            }

        } catch (SQLException | ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        }

        return users.toString();
    }

    public void deleteUser(Integer userId) throws SQLException, ClassNotFoundException {
     /*   String deletion = "DELETE * FROM " + Const.USER_TABLE+ " WHERE "+ Const.USER_ID +" = "+ userId;
        PreparedStatement prSt = null;
        prSt = getDbConnection().prepareStatement(deletion);
        prSt.executeUpdate();*/
        String deletion = "DELETE FROM " + Const.USER_TABLE+ " WHERE "+ Const.USER_ID +" = ?";
        PreparedStatement prSt=getDbConnection().prepareStatement(deletion);
        prSt.setInt(1,userId);
        prSt.executeUpdate();


    }



    //потом посмотрю зачем надо
    private String getNumOfUsers() {
        ResultSet rs = null;
        int count = 0;
        try {
            String select = "SELECT COUNT(1) FROM "+ Const.USER_TABLE;
            PreparedStatement prep = getDbConnection().prepareStatement( select );
            rs = prep.executeQuery();
            while (rs.next()) {
                count = rs.getInt( 1 );
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return String.valueOf( count );
    }

    public String signInAdmin(String login, String password) throws SQLException {
        JSONObject userJson = new JSONObject();
        Admin admin = new Admin();
        String result =null;
        try {
            // Statement stmt = getDbConnection().createStatement();
            String select = "SELECT * FROM "+ Const.ADMIN_TABLE + " WHERE "+ Const.ADMIN_USERNAME +
                    "= ?";
            PreparedStatement prSt = getDbConnection().prepareStatement(select);
            prSt.setString(1, login);
            //prep.setString(2, password);
            ResultSet rs = prSt.executeQuery();
            if (rs.next() == false) {

                result = "nobody";
            } else
            { do {
                admin.setID(rs.getInt(1));

                admin.setUserName(rs.getString(2));
                admin.setPassword(rs.getString(3));

                userJson.put("id", admin.getID());
                userJson.put("userName", admin.getUserName());
                userJson.put("password", admin.getPassword());
            } while (rs.next());
                result = userJson.toString();
            }

        } catch (ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        }
        //if(user.getName().equals("null")) result = user.getName();

        return result;
    }

    public String projectInput(String director, String operator, String presenter,
                             String projectName, int assesment, String format, int studioNumber){
        String insert = "INSERT INTO " + Const.PROJECT_TABLE + "(" + Const.PROJECT_DIRECTOR + "," + Const.PROJECT_OPERATOR + "," +
                Const.PROJECT_PRESENTER + "," + Const.PROJECT_NAME + "," + Const.PROJECT_ASSESMENT + "," + Const.PROJECT_FORMAT
                + "," + Const.PROJECT_STUDIONUMBER+ ")" +
                "VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement prSt = getDbConnection().prepareStatement(insert);
            prSt.setString(1, director);
            prSt.setString(2, operator);
            prSt.setString(3, presenter);
            prSt.setString(4, projectName);
            prSt.setInt(5, assesment);
            prSt.setString(6, format);
            prSt.setInt(7, studioNumber);

            prSt.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject userJson = new JSONObject();
        try {
            userJson.put("director", director);
            userJson.put("operator", operator);
            userJson.put("presenter", presenter);
            userJson.put("projectName", projectName);
            userJson.put("assesment", assesment);
            userJson.put("format", format);
            userJson.put("studioNumber", studioNumber);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userJson.toString();
    }

    public String getProjects() {
        Project project;
        JSONObject projectJson;
        JSONArray projects = new JSONArray(  );
        try {

            String select = "SELECT * FROM "+Const.PROJECT_TABLE;
            PreparedStatement prep1 = getDbConnection().prepareStatement( select );
            ResultSet rs = prep1.executeQuery();
            while (rs.next()){

                project = new Project();
                project.setIdprojects(rs.getInt(1));
                project.setDirector(rs.getString(2));
                project.setOperator(rs.getString(3));
                project.setPresenter(rs.getString(4));
                project.setProjectname(rs.getString(5));
                project.setAssesment(rs.getInt(6));
                project.setFormat(rs.getString(7));
                project.setStudionumber(rs.getInt(8));


                projectJson = new JSONObject();
                projectJson.put("id", project.getIdprojects());
                projectJson.put("director", project.getDirector());
                projectJson.put("operator", project.getOperator());
                projectJson.put("presenter", project.getPresenter());
                projectJson.put("projectName", project.getProjectname());
                projectJson.put("assesment", project.getAssesment());
                projectJson.put("format", project.getFormat());
                projectJson.put("studioNumber", project.getStudionumber());

                projects.put( projectJson );
            }

        } catch (SQLException | ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        }

        return projects.toString();
    }


}

