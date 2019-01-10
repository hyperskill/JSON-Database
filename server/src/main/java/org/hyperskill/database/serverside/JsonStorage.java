package org.hyperskill.database.serverside;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonStorage {

    private JsonObject storage = new JsonObject();

    public void set(String property, String value) {
        storage.addProperty(property, value);
    }

    public JsonElement get(String name) {
        return storage.get(name);
    }

    public void delete(String name) {
        storage.remove(name);
    }

}
