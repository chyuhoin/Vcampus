package com.vcampus.server.controller;

import com.vcampus.net.Message;

import java.sql.SQLException;

public interface Controller {
    Message check(Message msg);
}
