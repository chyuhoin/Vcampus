package com.vcampus.net;

import java.io.Serializable;

/**
 * 前后端传递的消息
 * 格式为
 * status    请求发起者的身份
 * data      使用JSON格式打包的数据
 * module    请求所属的模块
 * operation 请求进行的操作（增删改查）
 *
 * @author ZhongHaoyuan
 */
public class Message implements Serializable {
    private String status; // 请求发起者的身份
    private String data; // 使用JSON格式打包的数据
    private String module; // 请求所属的模块
    private String operation; // 请求进行的操作（增删改查）

    /**
     * Instantiates a new Message.
     *
     * @param status the status
     * @param data   the data
     */
    public Message(String status, String data) {
        this.status = status;
        this.data = data;
        this.module = "test";
    }

    /**
     * Instantiates a new Message.
     *
     * @param status    the status
     * @param data      the data
     * @param module    the module
     * @param operation the operation
     */
    public Message(String status, String data, String module, String operation) {
        this.status = status;
        this.data = data;
        this.module = module;
        this.operation = operation;
    }

    /**
     * Gets module.
     *
     * @return the module
     */
    public String getModule() {
        return module;
    }

    /**
     * Sets module.
     *
     * @param module the module
     */
    public void setModule(String module) {
        this.module = module;
    }

    /**
     * Gets operation.
     *
     * @return the operation
     */
    public String getOperation() {
        return operation;
    }

    /**
     * Sets operation.
     *
     * @param operation the operation
     */
    public void setOperation(String operation) {
        this.operation = operation;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets data.
     *
     * @return the data
     */
    public String getData() {
        return data;
    }

    /**
     * Sets data.
     *
     * @param data the data
     */
    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "status='" + status + '\'' +
                ", data='" + data + '\'' +
                ", module='" + module + '\'' +
                ", operation='" + operation + '\'' +
                '}';
    }
}
