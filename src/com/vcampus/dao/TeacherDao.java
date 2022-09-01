package com.vcampus.dao;


import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.Teacher;
import org.junit.Test;

import java.rmi.MarshalledObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.vcampus.dao.utils.ClassTable.getTimeIndex;

/**
 * 老师信息相关操作的Dao层
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class TeacherDao extends UserDao {
    /**
     * 添加老师
     *
     * @param teacher 老师
     * @return {@link Boolean}
     */
    public static Boolean addTeacher(Teacher teacher){
       try {
           addClass(teacher,"tb_TEACHER");
           return true;
       }catch (Exception e){
           System.out.println("wrong");
           return false;
       }
    }

    /**
     * 删除老师
     *
     * @param teacherID 老师id
     * @return {@link Boolean}
     *///删除一个老师
    public static Boolean deleteTeacher(String teacherID){
        try {
            delete("teacherID",teacherID,"tb_TEACHER");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 搜索老师
     *
     * @param field 字段
     * @param value 值
     * @return {@link List}<{@link Teacher}>
     * @throws Exception 异常
     */
    public static List<Teacher> searchTeacher(String field,String value) throws Exception {
        String sql = "select * from tb_TEACHER where "+field+" ='"+value+"'";

        List<Map<String,Object>> resultList = CRUD.Query(sql,conn);
        System.out.println(resultList);
        List<Teacher> result = new ArrayList<>();
        for(Map<String ,Object>map:resultList){
            Teacher teacher = mapToBean.map2Bean(map,Teacher.class);
            result.add(teacher);
        }
        return result;
    }

    /**
     * 选择课
     *
     * @param teacherID 老师id
     * @param innerID   内部id
     * @return {@link Boolean}
     */
    public static Boolean selectLesson(String teacherID,String innerID){
        try {
            String sql = "select timeTable from tb_LESSONTABLEFORTEACHER where teacherID = '" + teacherID + "'";
            List<Map<String, Object>> resultList = CRUD.Query(sql, conn);
            if(resultList.isEmpty()){
                String sqlextra="insert into tb_LESSONTABLEFORTEACHER (teacherID)values('"+teacherID+"')";
                CRUD.update(sqlextra,conn);
                resultList = CRUD.Query(sql,conn);
            }
            String sql2 ="select time from tb_LESSON where innerID ='"+innerID+"'";
            String lessonTime = (String) CRUD.Query(sql2,conn).get(0).get("time");
            String myTable = (String) resultList.get(0).get("timeTable");
            List<Integer>tableIndex = getTimeIndex(lessonTime);
            String[] table=myTable.split(",");
            for(Integer temp:tableIndex){
                table[temp]=innerID;
            }
            String resultTable=String.join(",",table);
            String sql3="update tb_LESSONTABLEFORTEACHER set timeTable ='"+resultTable+"' where teacherID ='"+teacherID+"'";
            CRUD.update(sql3,conn);
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 退课
     *
     * @param teacherID 老师id
     * @param innerID   内部id
     * @return {@link Boolean}
     */
    public static Boolean returnLesson(String teacherID,String innerID){
        try {
            String sql = "update tb_LESSONTABLEFORTEACHER set timeTable = REPLACE(timeTable,'"+innerID+"','0') where teacherID ='"+teacherID+"'";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 得到课表
     *
     * @param teacherID 老师id
     * @return {@link String}
     * @throws Exception 异常
     */
    public static String getLessonTable(String teacherID) throws Exception {
        String sql = "select timeTable from tb_LESSONTABLEFORTEACHER where teacherID ='"+teacherID+"'";
        List<Map<String,Object>> resultList = CRUD.Query(sql,conn);
        return (String) resultList.get(0).get("timeTable");
    }
}
