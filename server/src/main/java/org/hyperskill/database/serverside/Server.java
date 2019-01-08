package org.hyperskill.database.serverside;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        Protocol protocol = new Protocol();
        try (ServerSocket server = new ServerSocket(portNumber)) {
            while (true) {
                try (
                        Socket socket = server.accept(); // accepting a new client
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String inputLine, outputLine;
                    inputLine = input.readUTF(); // reading a message
                    outputLine = protocol.processInput(inputLine);
                    output.writeUTF(outputLine); // resend it to the client
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
