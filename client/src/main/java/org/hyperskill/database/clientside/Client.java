package org.hyperskill.database.clientside;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.google.gson.Gson;
import org.hyperskill.database.common.Request;
import org.hyperskill.database.common.Response;
import org.hyperskill.database.common.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client {
    @Parameter(names = {"-host", "-ip"}, description = "IP address")
    String host;

    @Parameter(names = {"-port"}, description = "Port", validateWith = PositiveInteger.class)
    int port;

    /*
    @Parameter(names = "-t", description = "Command", validateWith = Commands.class)
    String command;

    @Parameter(names = "-m", description = "Text to save")
    String text;

    @Parameter(names = "-i", description = "Index")
    String index;
    */

    @Parameter(names = "-in", description = "Input file")
    String inputFile;

    @Parameter(names = "-out", description = "Output file")
    String outputFile;

    public static void main(String... argv) {
        Client client = new Client();
        JCommander.newBuilder().addObject(client).build().parse(argv);

        if (argv.length < 6) {
            System.err.println(
                    "Usage: java -jar client.jar -ip <hostname> -port <port> -in <filename> [-out <filename>]");
            System.exit(1);
        }

        try (
                Socket socket = new Socket(client.host, client.port);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            System.out.println("ifile" + client.inputFile);

            Request request = Util.read(client.inputFile);
            Gson gson = new Gson();
            String out = gson.toJson(request);
            output.writeUTF(out);
            System.out.println("Sent: " + out);
            String receivedMsg = input.readUTF(); // response message
            System.out.println("Received: " + receivedMsg);
            Response response = gson.fromJson(receivedMsg, Response.class);
            if (response.isOk()) {
                System.out.println("Request was successful");
                if (client.outputFile != null && request.getType()== Request.TYPE.GET){
                    System.out.println("Value saved to " + client.outputFile);
                    Util.write(response, client.outputFile);
                }
            } else {
                System.out.println("Request was not successful");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}





