package com.vcampus.dao.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
提供统一的增删改查接口
 */
public class CRUD {
    public static List<Map<String,Object>> Query(String sql,Connection conn) throws Exception {
       // Connection conn = databaseConn.getConn();
        Statement stm   = conn.createStatement();
        ResultSet rs    =  stm.executeQuery(sql);
        List<Map<String,Object>> result = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();//获取键名
        int columnCount = md.getColumnCount();//获取列的数量
        while(rs.next()){
            Map<String,Object> rowData = new HashMap<>();//声明Map
            for (int i = columnCount; i >= 1; i--) {
                rowData.put(md.getColumnName(i), rs.getObject(i));//获取键名及值
            }
            result.add(rowData);
        }
        stm.close();
       // conn.close();
        return result;
    }
    public static void update(String sql,Connection conn) throws Exception {

         //   Connection conn = databaseConn.getConn();
            Statement stm = conn.createStatement();
            stm.executeUpdate(sql);
    }
}
