package com.vcampus.server.controller;

import com.vcampus.net.Message;

import java.sql.SQLException;

/**
 * The interface Controller.
 *
 * @author ZhongHaoyuan
 */
public interface Controller {
    /**
     * Controller分发请求的核心操作
     *
     * @param msg 前端传过来的消息
     * @return the message
     */
    Message check(Message msg);
}
