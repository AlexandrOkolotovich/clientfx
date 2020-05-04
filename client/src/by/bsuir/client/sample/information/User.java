package by.bsuir.client.sample.information;

import by.bsuir.client.sample.connectoin.Client;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public final class User {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;

    private static User instance;

    public static synchronized User getInstance(){
        if(instance == null){
            instance = new User();
        }
        return instance;
    }



    private User() {
        try{
            // client.Client.getInstance().send("setUser");
          //  String str = client.Client.getInstance().get();
            String str = Client.getInstance().get();
            JSONObject json = new JSONObject(str);
            id = json.getInt("id");
            firstName = json.getString("firstname");
            lastName = json.getString("lastname");
            email =json.getString("email");
            userName = json.getString("username");
            password = json.getString("password");


        } catch (IOException| JSONException e) {
            System.err.println(e);
        }
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

