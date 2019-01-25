package org.hyperskill.database.common;

public class Response {
    public enum TYPE {OK, FAIL}

    public boolean isOk() {
        if (this.type == TYPE.OK) {
            return true;
        }
        return false;
    }

    public TYPE getType() {
        return type;
    }

    private TYPE type;

    public String getValue() {
        return value;
    }

    public String getReason() {
        return reason;
    }

    private String value;
    private String reason;

    public Response(String type, String value, String reason) {
        switch (type.trim().toLowerCase()) {
            case "ok":
                this.type = TYPE.OK;
                break;
            case "fail":
                this.type = TYPE.FAIL;
                break;
            default:
                throw new IllegalArgumentException(type + " is not allowed command");
        }

        if (this.type == TYPE.FAIL && reason == null) {
            throw new IllegalArgumentException("FAIL response requires reason, got null");
        }
        this.reason = reason;
        this.value = value;
    }
}
