package by.bsuir.client.sample.information;

import by.bsuir.client.sample.connectoin.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public final class CollectionUsers{

    private ObservableList<UserInf> users = FXCollections.observableArrayList();

    private static CollectionUsers instance;

    public static synchronized CollectionUsers getInstance(){
        if(instance == null){
            instance = new CollectionUsers();
        }
        return instance;
    }

    public ObservableList<UserInf> getUsers() {
        return users;
    }

    public void delete(UserInf user){
        users.remove(user);
    }

    public void fillData(){
        try {
            String array = Client.getInstance().get();
            JSONArray newArray = new JSONArray( array );
            int count = newArray.length();
            for(int i = 0; i<count; i++) {
                JSONObject object = newArray.getJSONObject( i );
                int ids = object.getInt( "id");
                String id = String.valueOf( ids );
                String firstName = object.getString( "firstame" );
                String lastName = object.getString( "lastname" );
                String email = object.getString( "email" );
                String userName = object.getString( "username" );
                UserInf user = new UserInf(id, firstName, lastName, email, userName );
                users.add( user );
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

}
