package com.vcampus.net;

import java.io.Serializable;

public class Message implements Serializable {
    private String status; // 请求发起者的身份
    private String data; // 使用JSON格式打包的数据
    private String module; // 请求所属的模块
    private String operation; // 请求进行的操作（增删改查）

    public Message(String status, String data) {
        this.status = status;
        this.data = data;
        this.module = "test";
    }

    public Message(String status, String data, String module, String operation) {
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
                "statusCode='" + status + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
