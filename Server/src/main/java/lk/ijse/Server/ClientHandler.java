package lk.ijse.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    private Socket localSocket;

    private ArrayList<ClientHandler> clients;
    private BufferedReader bufferedReader;
    private PrintWriter printWriter;


    public ClientHandler(Socket localSocket,ArrayList<ClientHandler> clients){

        try {
            this.localSocket = localSocket;
            this.clients = clients;
            this.bufferedReader = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
            this.printWriter = new PrintWriter(localSocket.getOutputStream(),true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    @Override
    public void run(){
        try {
            String message;
            while ((message = bufferedReader.readLine())!=null){
                for (ClientHandler client:clients) {
                    System.out.println(message);
                    client.printWriter.println(message);
                }
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try {
                bufferedReader.close();
                localSocket.close();
                printWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
