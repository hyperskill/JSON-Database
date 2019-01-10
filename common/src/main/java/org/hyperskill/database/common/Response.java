package org.hyperskill.database.common;

public class Response {
    public enum TYPE {OK, FAIL}
    private TYPE type;
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
