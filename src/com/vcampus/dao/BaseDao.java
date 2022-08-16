package com.vcampus.dao;

import com.vcampus.dao.utils.databaseConn;

import java.sql.Connection;

public class BaseDao {
    static Connection conn = databaseConn.getConn();
}
