package org.hyperskill.database.serverside;

import com.google.gson.JsonElement;
import org.hyperskill.database.common.Request;
import org.hyperskill.database.common.Response;

import java.util.Objects;

public class JsonProtocol {
    JsonStorage storage = new JsonStorage();

    public Response processInput(Request input) {
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
}
