package by.bsuir.server.connection;

import by.bsuir.server.actions.Action;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer implements Runnable{
    protected int serverPort = 9000;
    protected ServerSocket serverSocket = null;
 //   protected ExecutorService pool=null;//////////
    protected boolean isStopped = false;

    public MultiThreadedServer(int port){
        this.serverPort = port;
  //      pool= Executors.newFixedThreadPool(5);///////////
    }

    @Override
    public void run(){
        openServerSocket();
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                clientSocket = this.serverSocket.accept();
          //      Action runnable = new Action(clientSocket); //////////
         //       pool.execute(runnable);
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Server Stopped.") ;
                    return;
                }
                throw new RuntimeException("Error accepting client connection", e);
            }

            new Thread(
                    new Action(clientSocket)
            ).start();
        }
        System.out.println("Server Stopped.") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;  //true
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        System.out.println("Opening server socket...");
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port " + this.serverPort, e);
        }
    }

}
