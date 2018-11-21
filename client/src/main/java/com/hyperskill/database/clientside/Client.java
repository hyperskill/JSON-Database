package com.hyperskill.database.clientside;

import java.io.*;

import java.net.*;
import java.util.Scanner;


public class Client {
    public static void main(String[] args)  {
        System.out.println("Client started!");

        String address = "127.0.0.1";
        int port = 23456;
        DataInputStream input = null;
        DataOutputStream output = null;
        try{
            Socket socket = new Socket(InetAddress.getByName(address), port);
            input =  new DataInputStream(socket.getInputStream());
            output  = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*Scanner scanner = new Scanner(System.in);
        String str = scanner.nextLine();

        int index = 0;

        String s = str.substring(str.indexOf("#")+2);
        index = Integer.parseInt(s);
        System.out.println(index);
        */
        try {
            output.writeUTF("Give me a record # 12");
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Sent: " + " Give me a record # 12");

        try {

            System.out.println("Recieved: " + input.readUTF());
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}

