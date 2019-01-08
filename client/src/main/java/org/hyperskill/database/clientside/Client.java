package org.hyperskill.database.clientside;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    @Parameter(names = {"-host", "-ip"}, description = "IP address")
    String host;

    @Parameter(names = {"-port"}, description = "Port", validateWith = PositiveInteger.class)
    int port;

    @Parameter(names = "-t", description = "Command", validateWith = Commands.class)
    String command;

    @Parameter(names = "-m", description = "Text to save")
    String text;

    @Parameter(names = "-i", description = "Index", validateWith = PositiveInteger.class)
    int index;

    public static void main(String... argv) {
        Client client = new Client();
        JCommander.newBuilder().addObject(client).build().parse(argv);

        if (argv.length < 8) {
            System.err.println(
                    "Usage: java -jar client.jar -ip <hostname> -port <port> -t <get/set/delete> -i <index> [-m <message>]");
            System.exit(1);
        }

        try (
                Socket socket = new Socket(client.host, client.port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            if (client.command.equals("set")) {
                if (client.text != null) {
                    output.writeUTF(client.command + " " + client.index + " " + client.text);
                    System.out.println("Sent: " + client.command + " " + client.index + " " + client.text);
                } else {
                    System.err.println("Text cannot be empty");
                    System.exit(1);
                }
            } else {
                output.writeUTF(client.command + " " + client.index);
                System.out.println("Sent: " + client.command + " " + client.index);
            }
            String receivedMsg = input.readUTF(); // response message
            System.out.println("Received: " + receivedMsg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





