package com.example.loopstr;

public class Message {
    private String id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String message;


    public Message(String id, String message) {
        this.id = id;
        this.message = message;
    }
}
