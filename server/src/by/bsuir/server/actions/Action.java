package by.bsuir.server.actions;

import by.bsuir.server.database.DataBaseHandler;
import by.bsuir.server.database.IdGenerator;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.sql.SQLException;

public class Action implements Runnable {
    protected Socket clientSocket = null;
    private static BufferedReader in;
    private static BufferedWriter out;
    private static InputStream input;
    private static OutputStream output;

    public Action(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            input = clientSocket.getInputStream();
            output = clientSocket.getOutputStream();
            in = new BufferedReader( new InputStreamReader( clientSocket.getInputStream() ) );
            out = new BufferedWriter( new OutputStreamWriter( clientSocket.getOutputStream() ) );

            String exit = "ok";
            while (!exit.equals( "exit" )) {
                exit = "ok";

                String str = in.readLine();

                if(in!=null) {
                    System.out.println("я получил: " + str);
                    String who = null;

                    switch (str) {
                        case "authorization": {
                            who = authorization();
                            break;
                        }
                        case "registration": {
                            registration();
                            who = "user";
                            break;
                        }
                        case "back": {
                            exit = "ok";
                            break;
                        }
                        case "exit": {
                            exit = "exit";
                            output.close();
                            input.close();
                            break;
                        }

                        default:
                            break;
                    }

                    switch (who) {
                        case "admin":
                            exit = menuAdmin();
                            break;

                        case "user":
                            exit = menuUser();
                            break;

                        default : break;
                    }
                }


            }

            out.close();///
            in.close();///
        }
        catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private String menuAdmin() throws SQLException, ClassNotFoundException {
        DataBaseHandler handler = new DataBaseHandler();
        String users = handler.getUsers();
        String projects = handler.getProjects();
        String menu = "work";
        try {
            out.write( users + '\n' );
            out.flush();
            System.out.println( "я отправил: " + users );

            out.write(projects+'\n');
            out.flush();
            System.out.println("я оправил проекты: " + projects);

            while (!menu.equals( "back" )) {
                menu = in.readLine();
                System.out.println( "я получил: " + menu );
                switch (menu) {
                    case "addUSD": {
                        String newRate = in.readLine();
                        System.out.println( "я получил: " + newRate);
                       // handler.addUSD(newRate);//
                        break;
                    }
                    case "getReports": {
                        String userId = in.readLine();
                        System.out.println( "я получил: " + userId );
                   //     String reports = handler.getReportsUser( userId );//
                  //      out.write( reports + '\n' );//
                        out.flush();
                        break;
                    }

                    case "deleteUser":{
                        int userId = in.read();
                        System.out.println( "я получил: " + userId );
                        handler.deleteUser(userId);
                        break;
                    }
                    case "searchLogin":{
                        String userLogin = in.readLine();
                        String search = handler.checkUser(userLogin);
                        out.write( search + '\n' );
                        out.flush();
                        System.out.println("я отправил: " + search);
                        break;
                    }
                    case "getProject":{
                        getProject();
                        break;
                    }
                    case "statistics":{
               //         handler.saveStatistics();//
                        break;
                    }

                    case "back": {
                        menu = "back";
                        break;
                    }
                    default:break;
                }
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return menu;

    }

    protected void registration() {
        try {
            String user;

            user = in.readLine();
            if (user != null) {
                System.out.println("я получил: " + user);
                if (!user.equals("back")) {
                    JSONObject userJson = new JSONObject(user);
                    int id = IdGenerator.getInstance("user").getNextId();

                    String firstName = userJson.getString("firstName");
                    String lastName = userJson.getString("lastName");
                    String email = userJson.getString("email");
                    String userName = userJson.getString("userName");
                    String password = userJson.getString("password");

                    DataBaseHandler handler = new DataBaseHandler();

                    String signIn = handler.checkUser(userName);
                    if(signIn.equals(userName)){
                        out.write("exist" + '\n');
                        out.flush();
                        System.out.println("я отправил: " + "exist");
                    }
                    else {
                      String sign = handler.signUpUser(firstName, lastName, email, userName, password);
                      out.write(sign + '\n');
                      out.flush();
                      System.out.println("я отправил: " + sign);
                    }
                }
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    protected String authorization() {
        String who = null;
        try {
            String user = in.readLine();
            JSONObject userJson = new JSONObject( user );

            String userName = userJson.getString( "userName" );
            String password = userJson.getString( "password" );

            DataBaseHandler handler = new DataBaseHandler();
            String sign = handler.signInAdmin( userName, password );
            if(!sign.equals("nobody")){
                who = "admin";
                output.write( "admin\n".getBytes() );
                out.flush();
                System.out.println( "я отправил: " + "admin" );
           /*     out.write( sign + '\n' );
                out.flush();
                System.out.println( "я отправил: " + sign );*//////////////////////////////

            }

          /*  if( userName.equals( "myadmin" ) && password.equals( "myadmin" ) ) {
                output.write( "admin\n".getBytes() );
                who = "admin";
            }*/
            else {

                //DataBaseHandler handler = new DataBaseHandler();

                 sign = handler.signInUser( userName, password );

                if(sign.equals( "nobody" )){
                    who = "nobody";
                    output.write( "nobody\n".getBytes() );
                    out.flush();
                    System.out.println( "я отправил: " + "nobody" );

                }
                else {
                    who = "user";
                    output.write( "user\n".getBytes() );
                    out.flush();
                    System.out.println( "я отправил: " + "user" );
         /*           out.write( sign + '\n' );
                    out.flush();
                    System.out.println( "я отправил: " + sign );*//////////////////////////

                }

            }


        }
        catch (IOException | JSONException | SQLException e) {
            e.printStackTrace();
        }
        return who;
    }

    public String menuUser() throws IOException {
        DataBaseHandler handler = new DataBaseHandler();
   //     String idUser = in.readLine();
   //    String companies = handler.getCompanies(idUser); //
        String menu2 = "work";
        try {
     //       out.write( companies + '\n' );//

            out.flush();
    //        System.out.println( "я отправил: " + companies );//


            while (!menu2.equals( "exit" )) {
                menu2 = in.readLine();
                System.out.println( "я получил: " + menu2 );
                switch (menu2) {
                    case "calculate": {
                        String Id = in.readLine();
                        int IDUser = Integer.parseInt( Id );
                        String type = in.readLine();
                        System.out.println( "я получил: " + type );

                        String reportValue = in.readLine();
                        System.out.println( "я получил: " + reportValue );
                        JSONObject res = new JSONObject( reportValue );
    /*                    Report report = new Report( res.getString( "date" ), type, res.getDouble( "x1" ), res.getDouble( "x2" ), res.getDouble( "x3" ),
                                res.getDouble( "x4" ), res.getDouble( "x5" ), res.getDouble( "x6" ),
                                res.getDouble( "x7" ), res.getDouble( "x8" ), res.getDouble( "x9" ), res.getInt( "IDCompany" ) );

                        out.write( report.getResult() + '\n' );
                        out.flush();
                        System.out.println( "я отправил:" + report.getResult() );
                        handler.addReport( report ,IDUser );*////////
                        break;
                    }
                    case "getReports": {
                        String companyId = in.readLine();
                        System.out.println( "я получил: " + companyId );
       //                 String reports = handler.getReportsCompany( companyId );//
         //               out.write( reports + '\n' );//
                        out.flush();
                        break;
                    }
                    case "addCompany": {
                        String Id = in.readLine();
                        int IDUser = Integer.parseInt( Id );
                        // String reportValue = in.readLine();
                        String company = in.readLine();
                        System.out.println( "я получил" + company );
     //                   handler.addCompany( company , IDUser);//
                        break;
                    }
                    case "txt":{
       //                 Report report = handler.getLastReport();//
       //                 report.saveTXT();//
                        break;
                    }
                    case "pdf":{
      //                  Report report = handler.getLastReport();//
       //                 report.savePDF();//
                        break;
                    }
                    case "exit": {
                        menu2 = "exit";
                        break;
                    }
                    default:break;
                }
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return menu2;
    }

    protected void getProject(){
        try {
            String project;
            project = in.readLine();
            if(!project.equals( "back" )) {
                JSONObject projectJson = new JSONObject( project );
             //   int id = IdGenerator.getInstance( "user" ).getNextId();

                String director = projectJson.getString( "director" );
                String operator = projectJson.getString( "operator" );
                String presenter = projectJson.getString( "presenter" );
                String projectName = projectJson.getString( "projectName" );
                int assesment = projectJson.getInt( "assesment" );
                String format = projectJson.getString( "format" );
                int studioNumber = projectJson.getInt( "studioNumber" );

                DataBaseHandler handler = new DataBaseHandler();
                String input = handler.projectInput( director, operator, presenter, projectName, assesment, format, studioNumber );
                out.write( input + '\n' );
                out.flush();
                System.out.println( "я отправил: " + input );
            }else {return;}
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}