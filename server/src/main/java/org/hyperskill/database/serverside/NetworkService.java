package org.hyperskill.database.serverside;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.hyperskill.database.common.Request;
import org.hyperskill.database.common.Response;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class NetworkService implements Runnable {
    private final ServerSocket serverSocket;
    private final ExecutorService pool;
    JsonStorage storage;


    public NetworkService(int port, int poolSize)
            throws IOException {
        serverSocket = new ServerSocket(port);
        pool = Executors.newFixedThreadPool(poolSize);
        storage = JsonStorage.getINSTANCE();
    }

    public void run() { // run the service
        try {
            for (; ; ) {
                pool.execute(new Handler(serverSocket.accept(), storage));
            }
        } catch (IOException ex) {
            pool.shutdown();
        }
    }
}

class Handler implements Runnable {
    private final Socket socket;
    private JsonStorage storage;

    Handler(Socket socket, JsonStorage storage) {
        this.socket = socket;
        this.storage = storage;
    }

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

    public void run() {
        try (
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            String inputLine, outputLine;
            inputLine = input.readUTF(); // reading a message
            Gson gson = new Gson();
            Request request = gson.fromJson(inputLine, Request.class);
            Response response = processInput(storage, request);
            String out = gson.toJson(response);
            output.writeUTF(out); // resend it to the client
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}