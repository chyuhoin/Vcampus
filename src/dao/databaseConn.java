package dao;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class databaseConn {
    public static Connection getConn() throws Exception {
        //1准备4个字符串
        String driverClass ;
        String dbUrl ;
        String user ;
        String password ;

        //2.加载properties配置文件获取连接
        InputStream in= new BufferedInputStream(new FileInputStream(
                new File("jdbc.properties")
        ));
        Properties prop = new Properties();
        prop.load(in);

        driverClass = prop.getProperty("driver");
        dbUrl = prop.getProperty("jdbcUrl");
        user = prop.getProperty("user");
        password = prop.getProperty("password");
        //3.返回获取到的连接
        try {
            Class.forName(driverClass);
            Connection conn = DriverManager.getConnection(dbUrl,user,password);
            return conn;
        }
        catch (Exception e)
        {
            System.out.println("获取连接失败");
            return null;
        }
    }
}


