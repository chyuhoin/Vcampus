package com.vcampus.net;

import java.io.Serializable;

public class Message implements Serializable {
    private String statusCode;
    private String data;

    public Message(String statusCode, String data) {
        this.statusCode = statusCode;
        this.data = data;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "statusCode='" + statusCode + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
