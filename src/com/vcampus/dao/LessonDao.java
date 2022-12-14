package com.vcampus.dao;

import com.sun.org.apache.bcel.internal.generic.Type;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vcampus.dao.utils.CRUD;
import com.vcampus.dao.utils.ClassTable;
import com.vcampus.dao.utils.databaseConn;
import com.vcampus.dao.utils.mapToBean;
import com.vcampus.pojo.*;
import org.junit.Test;

import java.net.Inet4Address;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.sun.org.apache.bcel.internal.generic.Type.STRING;
import static com.vcampus.dao.utils.ClassTable.addToTable;
import static com.vcampus.dao.utils.ClassTable.compare;

/**
 * 和学生课程有关的课
 *
 * @author 刘骐
 * @date 2022/09/01
 */
public class LessonDao extends BaseDao{
    /**
     * 查询所有课程信息
     *
     * @return {@link List}<{@link Lesson}>
     * @throws Exception 异常
     *///无参时查询所有课程
    public static List<Lesson> search() throws Exception {
        return searchAll(Lesson.class,"tb_LESSON");
    }

    /**
     * 按条件查询课程
     *
     * @param field 字段
     * @param value 值
     * @return {@link List}<{@link Lesson}>
     * @throws Exception 异常
     *///有参按条件查询
    public static List<Lesson> search(String field, Object value) throws Exception {
        return searchBy(Lesson.class,"tb_LESSON",field,value);
    }

    /**
     * 查询学生自己的选课信息
     *
     * @param studentID 学生一卡通号
     * @return {@link List}<{@link Lesson}>
     * @throws Exception 异常
     *///学生查询自己选的课信息,这是个联表查询，父类方法可能没法用
    public static List<Lesson> searchMine(String studentID) throws Exception {
        String sql = "SELECT tb_LESSON.* from tb_LESSON ,tb_STUDENTWITHLESSON " +
                "WHERE tb_LESSON.innerID = tb_STUDENTWITHLESSON.innerID " +
                "and tb_STUDENTWITHLESSON.studentID = '" + studentID + "'";
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
       // System.out.println(resultList);
        List<Lesson> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            Lesson lesson = mapToBean.map2Bean(map, Lesson.class);
            result.add(lesson);
        }
        return result;
    }

    /**
     * 老师搜索自己教的课
     *
     * @param teacherID 老师id
     * @return {@link List}<{@link Lesson}>
     * @throws Exception 异常
     */
    public static List<Lesson> searchMyLesson(String teacherID) throws Exception {
        String sql = "select * from tb_LESSON where teacherID = '"+teacherID+"'";
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
        List<Lesson> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            Lesson lesson = mapToBean.map2Bean(map, Lesson.class);
            result.add(lesson);
        }
        return result;
    }

    /**
     * 查看选一门课的所有学生,返回一卡通号
     *
     * @param innerID 内部id
     * @return {@link List}<{@link String}>
     * @throws Exception 异常
     */
    public static List<String> searchStudent(String innerID) throws Exception {
        String sql = "select studentID from tb_STUDENTWITHLESSON where innerID = '"+innerID+"'";
        List<Map<String, Object>> resultList = CRUD.Query(sql,conn);
        List<String> result = new ArrayList<>();
        for (Map<String, Object> map : resultList) {
            String stu = (String)map.get("studentID");
           result.add(stu);
        }
        return result;
    }

    /**
     * 判断学生能否选课即原因
     *
     * @param studentID 学生证
     * @param innerID   内部id
     * @return int
     * @throws Exception 异常
     */
    public static int judgeLesson(String studentID,String innerID) throws Exception {
        String judgeSql1 = "select * from tb_STUDENTWITHLESSON where studentID = '" + studentID + "' and innerID = '" + innerID + "'";

        List<Map<String, Object>> judge1 = CRUD.Query(judgeSql1,conn);


        if(!judge1.isEmpty())
            return 0;//已选不可选
        else{
            String judgeSql2 = "select * from tb_LESSON where  innerID = '" + innerID + "'";
            List<Map<String, Object>> judge2 = CRUD.Query(judgeSql2,conn);
            int leftSize = (int) judge2.get(0).get("leftSize");
            if(leftSize==0)
                return 1;//已满不可选
            else {
                String sql1 = "select timeTable from tb_LESSONTABLE where studentID = '"+studentID+"'";
                String sql2 = "select time from tb_LESSON where innerID ='"+innerID+"'";
                List<Map<String ,Object>> list1 = CRUD.Query(sql1,conn);
                List<Map<String ,Object>> list2 =CRUD.Query(sql2,conn);
                if(list1.isEmpty()){
                    String sql = "insert into tb_LESSONTABLE (studentID)values('"+studentID+"')";
                    CRUD.update(sql,conn);
                    list1 = CRUD.Query(sql1,conn);
                }
                String time = (String) list2.get(0).get("time");
                String timeTable = (String) list1.get(0).get("timeTable");
                 if(compare(time,timeTable)==false)
                     return 2;//冲突不可选
                else{
                     String sql3="select * from (select tb_STUDENTWITHLESSON.innerID,tb_LESSON.lessonID from tb_LESSON,tb_STUDENTWITHLESSON where tb_LESSON.innerID = tb_STUDENTWITHLESSON.innerID and tb_STUDENTWITHLESSON.studentID  = '"+studentID+"' )as tb_a, (select lessonID from tb_LESSON where innerID ='"+innerID+"' ) as tb_b where tb_b.lessonID = tb_a.lessonID";
                     List<Map<String,Object>> list4 = CRUD.Query(sql3,conn);
                    if(!list4.isEmpty())
                     return 3;//已选同类课程不可选
                     else
                         return 4;//可选
                 }

            }
        }

    }
    //学生选课

    /**
     * 选课
     *
     * @param studentID 学生一卡通号
     * @param innerID   内部id
     * @return {@link Boolean}
     * @throws Exception 异常
     */
    public static Boolean selectLesson(String studentID, String innerID) throws Exception {
//        String judgeSql = "select * from tb_STUDENTWITHLESSON where studentID = '" + studentID + "' and innerID = '" + innerID + "'";
//        List<Map<String, Object>> judge = CRUD.Query(judgeSql,conn);
//        if (judge.isEmpty()) {
            try {
                String sql1 = "update tb_LESSON set leftSize=leftSize-1 where innerID = '" + innerID + "'";
                String sql2 = "insert into tb_STUDENTWITHLESSON (studentID,innerID) values ('" + studentID + "','" + innerID + "')";
                Connection conn = databaseConn.getConn();
                conn.setAutoCommit(false);
                Statement stm = conn.createStatement();
                stm.executeUpdate(sql1);
                stm.executeUpdate(sql2);
                conn.commit();
                conn.setAutoCommit(true);
                addToTable(innerID,studentID,conn);
                return true;
            } catch (Exception e) {
                System.out.println("wrong");
                return false;
            }
//        } else {
//            return false;
//        }
    }

    /**
     * 学生退课
     *
     * @param studentID 学生一卡通号
     * @param innerID   内部id
     * @return {@link Boolean}
     * @throws Exception 异常
     */
    public static Boolean returnLesson(String studentID,String innerID) throws Exception {
            try {
                String sql1 = "update tb_LESSON set leftSize=leftSize+1 where innerID = '" + innerID + "'";
                String sql2 = "delete from tb_STUDENTWITHLESSON where studentID ='"+studentID+"' and innerID = '"+innerID+"'";
                String sql3 = "update tb_LESSONTABLE set timeTable = REPLACE(timeTable,'"+innerID+"','0') where studentID = '"+studentID+"'";
                Connection conn = databaseConn.getConn();
                conn.setAutoCommit(false);
                Statement stm = conn.createStatement();
                stm.executeUpdate(sql1);
                stm.executeUpdate(sql2);
                stm.executeUpdate(sql3);
                conn.commit();
                stm.close();
                conn.close();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

    /**
     * 管理员添加一门课程
     *
     * @param lesson 教训
     * @return {@link Boolean}
     * @throws Exception 异常
     */
    public static Boolean addLesson(Lesson lesson) throws Exception {
//        try {
//            String sql = "insert into tb_LESSON (innerID,lessonID,name,teacherID,maxSize,leftSize,time,school,major,isExam) values(?,?,?,?,?,?,?,?,?,?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//                ps.setString(1,lesson.getInnerID());
//                ps.setString(2,lesson.getLessonID());
//                ps.setString(3,lesson.getName());
//                ps.setString(4,lesson.getTeacherID());
//                ps.setInt(5,lesson.getMaxSize());
//                ps.setInt(6,lesson.getLeftSize());
//                ps.setString(7,lesson.getTime());
//                ps.setString(8,lesson.getSchool());
//                ps.setString(9, lesson.getMajor());
//                ps.setInt(10,lesson.getIsExam());
//            ps.executeUpdate();
//            return true;
//        }catch (Exception e){
//            return false;
//        }
        try {
            addClass(lesson,"tb_LESSON");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }

    }

    /**
     * 管理员删除课程
     *
     * @param innerID 内部id
     * @return {@link Boolean}
     */
    public static Boolean deleteLesson(String innerID){
        try {
            delete("innerID",innerID,"tb_LESSON");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }

    }

    /**
     * 管理员删除同一个编号的所有课程
     *
     * @param lessonID 教训id
     * @return {@link Boolean}
     */
    public static Boolean deleteSpecificLesson(String lessonID){
        try {
            delete("lessonID",lessonID,"tb_LESSON");
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }

    }

    /**
     * 给学生添加成绩
     *
     * @param studentID 学生证
     * @param innerID   内部id
     * @param grade     年级
     * @return {@link Boolean}
     * @throws Exception 异常
     */
    public static Boolean addGrade(String studentID, String innerID, Integer grade) throws Exception {
        String sql = "update tb_STUDENTWITHLESSON set grade = " + String.valueOf(grade) + " where studentID = '" + studentID + "' and innerID = '" + innerID + "'";
        try {
            CRUD.update(sql,conn);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 查询学生成绩
     *
     * @param studentID 学生证
     * @return {@link List}<{@link LessonGrade}>
     * @throws Exception 异常
     */
    public static List<LessonGrade> getGrade(String studentID) throws Exception {
        return searchBy(LessonGrade.class,"tb_STUDENTWITHLESSON","studentID",studentID);
    }

    /**
     * 学生查询课表
     *
     * @param studentID 学生一卡通号
     * @return {@link String}
     * @throws Exception 异常
     */
    public static String getLessonTable(String studentID) throws Exception {
        String sql = "select timeTable from tb_LESSONTABLE where studentID = '"+studentID+"'";
        String result = (String) CRUD.Query(sql,conn).get(0).get("timeTable");
        return result;
    }

    /**
     * 查询可用教室
     *
     * @param time 时间
     * @return {@link List}<{@link String}>
     * @throws Exception 异常
     */
    public static List<String> abledRoom(String time) throws Exception {
        List<String>result = new ArrayList<>();
        List<Integer>index = ClassTable.getTimeIndex(time);
        String sql = "select * from tb_CLASSROOM";
        List<Map<String,Object>>resultList  =CRUD.Query(sql,conn);
        for(Map<String,Object>map:resultList){
            if(compare(time,(String) map.get("timeTable"))==true){
                result.add((String) map.get("roomID"));
            }
        }
        return result;
    }

    /**
     * 给教室添加一门课
     *
     * @param time     时间
     * @param lessonID 教训id
     * @param roomID   房间id
     * @return {@link Boolean}
     */
    public static Boolean selectLessonForClassroom(String time,String lessonID,String roomID){
        try {
            String sql = "select timeTable from tb_CLASSROOM where roomID = '"+roomID+"'";
            List<Map<String,Object>> resultList = CRUD.Query(sql,conn);
            if(resultList.isEmpty()){
                String sql2 = "insert into tb_CLASSROOM (roomID) values ('"+roomID+"')";
                CRUD.update(sql2,conn);
                resultList = CRUD.Query(sql,conn);
            }
            String myTable = (String) resultList.get(0).get("timeTable");
            String [] table = myTable.split(",");
            List<Integer>index = ClassTable.getTimeIndex(time);
            for(Integer temp:index){
                table[temp] = lessonID;
            }
            String timeTable = String.join(",",table);
            String sql2 = "update tb_CLASSROOM set timeTable = '"+timeTable+"' where roomID ='"+roomID+"'";
            CRUD.update(sql2,conn);
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 从教室课表删去一门课
     *
     * @param lessonID 教训id
     * @param roomID   房间id
     * @return {@link Boolean}
     */
    public static Boolean returnLessonForClassroom(String lessonID,String roomID){
        try {
            String sql = "update tb_CLASSROOM set timeTable = REPLACE(timeTable,'"+lessonID+"','0') where roomID ='"+roomID+"'";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 所有教室都删除一门课
     *
     * @param innerID 内部id
     * @return {@link Boolean}
     */
    public static Boolean deleteAllFromClassroom(String innerID){
        try {
            String sql = "update tb_CLASSROOM set timeTable = REPLACE(timeTable,'"+innerID+"','0')";
            CRUD.update(sql,conn);
            return true;
        }catch (Exception e){
            System.out.println("wrong");
            return false;
        }
    }

    /**
     * 搜索内部id搜索学生的成绩
     *
     * @param innerID 内部id
     * @return {@link List}<{@link String}>
     * @throws Exception 异常
     */
    public static List<String> searchByInnerID(String innerID) throws Exception {
        String sql = "select studentID,grade from tb_STUDENTWITHLESSON where innerID = '"+innerID+"'";
        List<Map<String,Object>> resultList = CRUD.Query(sql,conn);
        List<String>result = new ArrayList<>();
        for(Map<String,Object>map:resultList){
            String res = map.get("studentID")+"/"+String.valueOf(map.get("grade"));
            result.add(res);
        }
        return result;
    }
}

