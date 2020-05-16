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

            out.close();
            in.close();
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
                    case "deleteUser":{
                        int userId = in.read();
                        System.out.println( "я получил: " + userId );
                        handler.deleteUser(userId);
                        break;
                    }
                    case "deleteProject":{
                        int projectId = in.read();
                        System.out.println( "я получил: " + projectId );
                        handler.deleteProject(projectId);
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
                    case "getSinglSolution":{
                        getSinglSolution();
                        break;
                    }
                    case "getNum":{
                        getAns();
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

            }


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

        String projects = handler.getProjects();
        String menu2 = "work";
        try {
            out.write(projects+'\n');
            out.flush();
            System.out.println("я оправил проекты: " + projects);


            while (!menu2.equals( "back" )) {
                menu2 = in.readLine();
                System.out.println( "я получил: " + menu2 );
                switch (menu2) {

                    case "getProject":{
                        getProject();
                        break;
                    }
                    case "getSinglSolution":{
                        getSinglSolution();
                        break;
                    }
                    case "getNum":{
                        getAns();
                        break;
                    }
                    case "back": {
                        menu2 = "back";
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
                int id = IdGenerator.getInstance( "project" ).getNextId();

                String director = projectJson.getString( "director" );
                String operator = projectJson.getString( "operator" );
                String presenter = projectJson.getString( "presenter" );
                String projectName = projectJson.getString( "projectName" );
                int assesment = projectJson.getInt( "assesment" );
                String format = projectJson.getString( "format" );
                int studioNumber = projectJson.getInt( "studioNumber" );

                DataBaseHandler handler = new DataBaseHandler();
                String input = handler.projectInput( id, director, operator, presenter, projectName, assesment, format, studioNumber );
                out.write( input + '\n' );
                out.flush();
                System.out.println( "я отправил: " + input );
                out.write( input + '\n' );
                out.flush();
                System.out.println( "я отправил новый проект: " + input );
            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    protected void getSinglSolution(){
        try {
            String task;
            task = in.readLine();
            if(!task.equals( "back" )) {
                JSONObject taskJson = new JSONObject( task );

                int el11 = taskJson.getInt( "el11" );
                int el12 = taskJson.getInt( "el12" );
                int el13 = taskJson.getInt( "el13" );
                int el14 = taskJson.getInt( "el14" );
                int el15 = taskJson.getInt( "el15" );
                int el16 = taskJson.getInt( "el16" );
                int el17 = taskJson.getInt( "el17" );
                int el18 = taskJson.getInt( "el18" );
                int el19 = taskJson.getInt( "el19" );
                int el110 = taskJson.getInt( "el110" );
                int el111 = taskJson.getInt( "el111" );
                int el112 = taskJson.getInt( "el112" );

                int el21 = taskJson.getInt( "el21" );
                int el22 = taskJson.getInt( "el22" );
                int el23 = taskJson.getInt( "el23" );
                int el24 = taskJson.getInt( "el24" );
                int el25 = taskJson.getInt( "el25" );
                int el26 = taskJson.getInt( "el26" );
                int el27 = taskJson.getInt( "el27" );
                int el28 = taskJson.getInt( "el28" );
                int el29 = taskJson.getInt( "el29" );
                int el210 = taskJson.getInt( "el210" );
                int el211 = taskJson.getInt( "el211" );
                int el212 = taskJson.getInt( "el212" );

                int el31 = taskJson.getInt( "el31" );
                int el32 = taskJson.getInt( "el32" );
                int el33 = taskJson.getInt( "el33" );
                int el34 = taskJson.getInt( "el34" );
                int el35 = taskJson.getInt( "el35" );
                int el36 = taskJson.getInt( "el36" );
                int el37 = taskJson.getInt( "el37" );
                int el38 = taskJson.getInt( "el38" );
                int el39 = taskJson.getInt( "el39" );
                int el310 = taskJson.getInt( "el310" );
                int el311 = taskJson.getInt( "el311" );
                int el312 = taskJson.getInt( "el312" );

                int[][] array = {{el11, el12, el13, el14, el15, el16, el17, el18, el19, el110, el111, el112},
                        {el21, el22, el23, el24, el25, el26, el27, el28, el29, el210, el211, el212},
                        {el31, el32, el33, el34, el35, el36, el37, el38, el39, el310, el311, el312}};

                for(int i=0; i<3; i++){
                    for(int j=0; j<12; j++){
                        System.out.print(array[i][j]+ " ");
                    }
                    System.out.print("\n");
                }

                float[] array_yd = new float[12];
                for(int i=0; i<12; i++){
                    array_yd[i]=((float)(array[1][i]+array[2][i]))/array[0][i];
                }

                for(int i=0; i<12; i++){
                    System.out.print(array_yd[i]+ " ");
                }
                System.out.print("\n");

                float[] array_av =new float[3];
                for (int i=0; i<3; i++){
                    array_av[i]=0;
                    for(int j=i; j<12; j+=3){
                        array_av[i]+=array_yd[j];
                    }
                    array_av[i]/=4;
                }

               // for (int i = 0; i < 3; i++) array_av[i] /= 4;

                for(int i=0; i<3; i++){
                    System.out.print(array_av[i] + " ");
                }
                System.out.print("\n");

                float[] array_ans = new float[3];
                for(int i=0; i<3; i++){
                    array_ans[i]=0;
                    for(int j=i; j<12; j+=3){
                        array_ans[i] += (array_yd[j] - array_av[i]) * (array_yd[j] - array_av[i]);
                    }
                    array_ans[i]/=3;
                }

                for(int i=0; i<3; i++){
                    System.out.print(array_ans[i]+ " ");
                }
                System.out.print("\n");

                String av_pr1 = Float.toString(array_av[0]);
                String av_pr2 = Float.toString(array_av[1]);
                String av_pr3 = Float.toString(array_av[2]);

                String ans_pr1 = Float.toString(array_ans[0]);
                String ans_pr2 = Float.toString(array_ans[1]);
                String ans_pr3 = Float.toString(array_ans[2]);

                out.write( av_pr1 + '\n' );
                out.flush();
                System.out.println( "я отправил среднюю удельную эф Пр1: " + av_pr1 );

                out.write( av_pr2 + '\n' );
                out.flush();
                System.out.println( "я отправил среднюю удельную эф Пр2: " + av_pr2 );

                out.write( av_pr3 + '\n' );
                out.flush();
                System.out.println( "я отправил среднюю удельную эф Пр3: " + av_pr3 );

                out.write( ans_pr1 + '\n' );
                out.flush();
                System.out.println( "я отправил риски Пр1: " + ans_pr1 );

                out.write( ans_pr2 + '\n' );
                out.flush();
                System.out.println( "я отправил риски Пр2: " + ans_pr2 );

                out.write( ans_pr3 + '\n' );
                out.flush();
                System.out.println( "я отправил риски Пр3: " + ans_pr3 );

            }
        }
        catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

     private void getAns() throws IOException {
         String av_pr1 = in.readLine();
         String av_pr2 = in.readLine();
         String av_pr3 = in.readLine();

         String ans_pr1 = in.readLine();
         String ans_pr2 = in.readLine();
         String ans_pr3 = in.readLine();

         String av_pr = in.readLine();

         float av1 = Float.parseFloat(av_pr1);
         float av2 = Float.parseFloat(av_pr2);
         float av3 = Float.parseFloat(av_pr3);

         float ans1 = Float.parseFloat(ans_pr1);
         float ans2 = Float.parseFloat(ans_pr2);
         float ans3 = Float.parseFloat(ans_pr3);

         float av=Float.parseFloat(av_pr);

         float[] array_otv={ans1, ans2, ans3};
         float[] array_av={av1, av2, av3};

         int mini = -1;
         float min = 999999;

         for(int i = 0; i < 3; i++)
             if (min > array_otv[i] && array_av[i] >= av) {
             mini = i;
             min = array_otv[i];
         }

         if (mini == -1) {
             out.write( "error" +'\n' );
             out.flush();
         }
         else {
             int mini1=mini+1;
             String num = Integer.toString(mini1);
             out.write( num+ '\n' );
             out.flush();
             System.out.println("я отправил "+ num);
         }
     }

}