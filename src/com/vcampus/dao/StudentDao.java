package com.vcampus.dao;

import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDao {
    //search空参时查询所有学生，仅管理员可用
    public static List<Student> search() throws Exception {
        String sql = "select * from tb_STUDENT";
        List<Map<String,Object>> resultList = CRUD.Query(sql);
        List<Student> result = new ArrayList<>();
        for(Map<String,Object> map:resultList){
            Student stu = mapToBean.map2Bean(map,Student.class);
            result.add(stu);
        }
        return result;
    }
}
