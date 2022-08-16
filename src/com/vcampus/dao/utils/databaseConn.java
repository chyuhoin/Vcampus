package com.vcampus.dao.utils;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import static java.sql.Connection.TRANSACTION_REPEATABLE_READ;

public class databaseConn {
    public static Connection getConn()  {
        //1.定义了4个字符串用来保存连接信息
        String driverClass ;
        String dbUrl ;
        String user ;
        String password ;

        //2.从jdbc配置文件里加载信息
        try{
        InputStream in= new BufferedInputStream(new FileInputStream(
                new File("jdbc.properties")
        ));
        Properties prop = new Properties();

        prop.load(in);
        driverClass = prop.getProperty("driver");
        dbUrl = prop.getProperty("jdbcUrl");
        user = prop.getProperty("user");
        password = prop.getProperty("password");
        Class.forName(driverClass);
        Connection conn = DriverManager.getConnection(dbUrl,user,password);
        conn.setTransactionIsolation(TRANSACTION_REPEATABLE_READ);//设置隔离级别为3级
            return conn;
        } catch (Exception e)
        {
            System.out.println("??????????");
            return null;
        }

    }
}


