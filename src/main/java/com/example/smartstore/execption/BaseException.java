package com.example.smartstore.execption;

public class BaseException extends RuntimeException {
    private String message;

    public BaseException() {
        super();
    }

    public BaseException(String msg, Object... args) {
        this.message = format("custom " + msg, args);
    }

    @Override
    public String getMessage() {
        return message;
    }

    private static String format(String msg, Object... args) {
        msg = msg.replace("{}", "%S");
        msg = msg.replace("[]", "%S");
        return String.format(msg, args);
    }
}
