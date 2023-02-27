package com.example.smartstore.execption;

public class StoreException extends RuntimeException {
    private String message;

    private Object object;

    private Object[] args;

    private Boolean status;


    public StoreException(String message) {
        super(message);
        this.message = message;
        this.status = false;
        this.object = null;
    }


    public StoreException(Exception ex) {
        super(ex);
        this.message = ex.getMessage();
        this.status = false;
        this.object = null;
    }


    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
