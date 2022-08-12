package com.vcampus.dao;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class databaseConn {
    public static Connection getConn() throws Exception {
        //1׼��4���ַ���
        String driverClass ;
        String dbUrl ;
        String user ;
        String password ;

        //2.����properties�����ļ���ȡ����
        InputStream in= new BufferedInputStream(new FileInputStream(
                new File("jdbc.properties")
        ));
        Properties prop = new Properties();
        prop.load(in);

        driverClass = prop.getProperty("driver");
        dbUrl = prop.getProperty("jdbcUrl");
        user = prop.getProperty("user");
        password = prop.getProperty("password");
        //3.���ػ�ȡ��������
        try {
            Class.forName(driverClass);
            Connection conn = DriverManager.getConnection(dbUrl,user,password);
            return conn;
        }
        catch (Exception e)
        {
            System.out.println("��ȡ����ʧ��");
            return null;
        }
    }
}


