package com.example.loopstr;

public class BusinessMessage {
    private String busId;
    private String message;

    public BusinessMessage(String id, String message) {
        this.busId = id;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String id) {
        this.busId = id;
    }
}
