package com.vcampus.net;

import java.io.Serializable;

public class Message implements Serializable {
    private String statusCode;
    private String data;
    private String module;
    private String operation;

    public Message(String statusCode, String data) {
        this.statusCode = statusCode;
        this.data = data;
        this.module = "test";
    }

    public Message(String statusCode, String data, String module, String operation) {
        this.statusCode = statusCode;
        this.data = data;
        this.module = module;
        this.operation = operation;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
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
