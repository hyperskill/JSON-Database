package org.hyperskill.database.common;

import java.util.Objects;

public class Request {
    public enum TYPE {SET, GET, DELETE}
    private TYPE type;
    private String key;
    private String value;

    public Request(String type, String key, String value) {
        Objects.requireNonNull(type);
        Objects.requireNonNull(key);

        switch (type.trim().toLowerCase()) {
            case "set":
                this.type = TYPE.SET;
                break;
            case "get":
                this.type = TYPE.GET;
                break;
            case "delete":
                this.type = TYPE.DELETE;
                break;
            default:
                throw new IllegalArgumentException(type + " is not allowed command");
        }
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
}
