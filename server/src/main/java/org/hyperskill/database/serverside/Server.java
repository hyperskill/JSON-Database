package org.hyperskill.database.serverside;

import java.io.IOException;

public class Server {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java Server <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        try {
            NetworkService nws = new NetworkService(portNumber, 10);
            nws.run();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
/*

        try (ServerSocket server = new ServerSocket(portNumber)) {
            while (true) {
                try (
                        Socket socket = server.accept(); // accepting a new client
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String inputLine;
                    inputLine = input.readUTF(); // reading a message
                    Gson gson = new Gson();
                    Request request = gson.fromJson(inputLine, Request.class);
                    Response response = processInput(storage, request);
                    String out = gson.toJson(response);
                    output.writeUTF(out); // resend it to the client
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        */
    }
/*
    private static Response processInput(JsonStorage storage, Request input) {
        Objects.requireNonNull(input, "Request cannot be empty in JsonProtocol.processInput");
        Response response;
        switch (input.getType()) {
            case GET:
                JsonElement result = storage.get(input.getKey());
                if (result != null) {
                    response = new Response("ok", result.toString(), null);
                } else {
                    response = new Response("fail", null, "No such key");
                }
                break;
            case SET:
                storage.set(input.getKey(), input.getValue());
                response = new Response("Ok", null, null);
                break;
            case DELETE:
                if (storage.get(input.getKey()) != null) {
                    storage.delete(input.getKey());
                    response = new Response("ok", null, null);
                } else {
                    response = new Response("fail", null, "No such key");
                }
                break;
            default:
                throw new IllegalArgumentException(input.getType().toString() + " is not allowed command");
        }
        return response;
    }
    */
}
