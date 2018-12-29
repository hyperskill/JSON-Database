package org.hyperskill.database.serverside;

import java.io.*;
import java.net.*;
import java.util.*;

//java -cp target/...jar com.kkk.app

public class Server {
    private static final int PORT = 34522;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                Session session = new Session(server.accept());
                session.run(); // it does not block this thread
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class Session {
    private final Socket socket;

    public Session(Socket socketForClient) {
        this.socket = socketForClient;
    }

    public void run() {
        try (
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
          System.out.println("Server started!");
            while(true) {
                String msg = input.readUTF();

                  System.out.println("Recieved: " + msg);
                  output.writeUTF(msg);
                  System.out.println("Sent: " + msg);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
