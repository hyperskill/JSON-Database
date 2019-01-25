package org.hyperskill.database.common;

import java.util.Objects;

public class Request {
    public enum TYPE {SET, GET, DELETE}
    private TYPE type;
    private String key;
    private String value;

    public Request(String type, String key, String value) throws IllegalArgumentException {
        Objects.requireNonNull(type);
        Objects.requireNonNull(key);

        this.type = validateCommand(type);

        if (this.type == TYPE.SET && value == null) {
            throw new IllegalArgumentException("Set command requires value, got null");
        }
        this.key = key;
        this.value = value;
    }

    public TYPE getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static TYPE validateCommand(String word) {
        switch (word.trim().toLowerCase()) {
            case "set":
                return TYPE.SET;
            case "get":
                return TYPE.GET;
            case "delete":
                return TYPE.DELETE;
            default:
                throw new IllegalArgumentException(word + " is not allowed command");
        }

    }
}
