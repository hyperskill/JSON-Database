package com.hyperskill.database.serverside;




import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        System.out.println("Server started!");
        String address = "127.0.0.1";
        int port = 23456;

        Socket socket = null;
        DataInputStream input = null;
        DataOutputStream output = null;
        try {
            ServerSocket server = new ServerSocket(port, 50, InetAddress.getByName(address));
            socket = server.accept();
            input = new DataInputStream(socket.getInputStream());
            output  = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ask = null;
        try {
            ask = input.readUTF();
            System.out.println("Recieved: " + ask);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String str = null;

        try {
            str = "A record # 12 was sent!";
            System.out.println("Sent: " + str);
            output.writeUTF(str);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
