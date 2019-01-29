package org.hyperskill.database.serverside;

import com.google.gson.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JsonStorage {
    private static final String FILENAME = "data.json";

    private static JsonObject storage;
    private static ReadWriteLock lock;
    private static Lock readLock;
    private static Lock writeLock;

    private static final JsonStorage INSTANCE = new JsonStorage();

    private JsonStorage() {
        storage = new JsonObject();
        readData();
        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    public static JsonStorage getINSTANCE() {
            return INSTANCE;
    }

    public static void set(String property, String value) {
        writeLock.lock();
        storage.addProperty(property, value);
        commit();
        writeLock.unlock();
    }

    public static JsonElement get(String name) {
        readLock.lock();
        JsonElement element = storage.get(name);
        readLock.unlock();
        return element;
    }

    public static void delete(String name) {
        writeLock.lock();
        storage.remove(name);
        commit();
        writeLock.unlock();
    }

    private static void commit() {
        try (FileOutputStream fos = new FileOutputStream(FILENAME);
             OutputStreamWriter isr = new OutputStreamWriter(fos,
                     StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            gson.toJson(storage, isr);
        } catch (Exception ioe) {
            System.out.println("save db except");
            ioe.printStackTrace();
        }
    }

    private static void readData() {
        Gson gson = new GsonBuilder().create();
        Path path = new File(FILENAME).toPath();
        try {
            JsonParser parser = new JsonParser();
            Object object = parser.parse(new FileReader(FILENAME));
            storage = (JsonObject) object;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
