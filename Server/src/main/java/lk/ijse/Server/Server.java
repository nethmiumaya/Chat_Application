package lk.ijse.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
   private static ArrayList<ClientHandler> clients = new ArrayList<>();



    public static void main(String[] args) {

            try {
                ServerSocket serverSocket = new ServerSocket(5000);
                System.out.println("Server Started");
                while (true){
                    Socket localsocket = serverSocket.accept();
                    System.out.println("client connected");
                    ClientHandler clientHandler = new ClientHandler(localsocket,clients);
                    clients.add(clientHandler);
                    clientHandler.start();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

