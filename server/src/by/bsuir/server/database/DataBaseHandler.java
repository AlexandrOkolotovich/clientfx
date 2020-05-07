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

    public String signUpUser(String firstname, String lastname, String email,
                             String username, String password){
        String insert = "INSERT INTO " + Const.USER_TABLE + "(" + Const.USER_FIRSTNAME + "," + Const.USER_LASTNAME + "," +
                Const.USER_EMAIL + "," + Const.USER_USERNAME + "," + Const.USER_PASSWORD + ")" +
                "VALUES(?,?,?,?,?)";



        try {
                PreparedStatement prSt = getDbConnection().prepareStatement(insert);
                prSt.setString(1, firstname);
                prSt.setString(2, lastname);
                prSt.setString(3, email);
                prSt.setString(4, username);
                prSt.setString(5, password);
                prSt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        JSONObject userJson = new JSONObject();
        try {
            userJson.put("firstname", firstname);
            userJson.put("lastname", lastname);
            userJson.put("email", email);
            userJson.put("username", username);
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
                user.setID(rs.getInt(1));
                user.setFirstName(rs.getString(2));
                user.setLastName(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setUserName(rs.getString(5));
                user.setPassword(rs.getString(6));

                userJson.put("id", user.getID());
                userJson.put("firstname", user.getFirstName());
                userJson.put("lastname", user.getLastName());
                userJson.put("email", user.getEmail());
                userJson.put("username", user.getUserName());
                userJson.put("password", user.getPassword());
            } while (rs.next());
                result = userJson.toString();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
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
                userJson.put("firstname", user.getFirstName());
                userJson.put("lastname", user.getLastName());
                userJson.put("email", user.getEmail());
                userJson.put("username", user.getUserName());
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
                user.setFirstName(rs.getString(1));
                user.setLastName(rs.getString(2));
                user.setEmail(rs.getString(3));
                user.setUserName(rs.getString(4));
                user.setPassword(rs.getString(5));

                userJson = new JSONObject();
                userJson.put("firstname", user.getFirstName());
                userJson.put("lastname", user.getLastName());
                userJson.put("email", user.getEmail());
                userJson.put("username", user.getUserName());
                userJson.put("password", user.getPassword());

                users.put( userJson );
            }

        } catch (SQLException | ClassNotFoundException | JSONException e) {
            e.printStackTrace();
        }

        return users.toString();
    }

    public void deleteUser(String userId) throws SQLException, ClassNotFoundException {
        String deletion = "DELETE FROM app.user WHERE "+ Const.USER_ID +"="+ userId;
        PreparedStatement prSt = null;
        prSt = getDbConnection().prepareStatement(deletion);
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
                userJson.put("username", admin.getUserName());
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
}

