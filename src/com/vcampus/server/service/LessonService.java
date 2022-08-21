package com.vcampus.server.service;

import com.vcampus.dao.LessonDao;
import com.vcampus.dao.StudentDao;
import com.vcampus.dao.UserDao;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;

import java.util.List;
public class LessonService implements Service{
    public boolean addLesson(Lesson user) {
        boolean res;
        try {
            if(!LessonDao.search("lessonID",user.getLessonID()).isEmpty())//已经有这个课程ID
                res=false;
            else res=LessonDao.addLesson(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean addOneLesson(Lesson user) {
        boolean res;
        try {
            if(LessonDao.search("lessonID",user.getLessonID()).isEmpty())//没有对应课程ID
                res=false;
            else if(!LessonDao.search("innerID",null).isEmpty())//此时为空课程
                //if(删除课程（课程ID）)
                res=LessonDao.addLesson(user);
                //else res=false;
            else //此时已经没有空课程
            if(LessonDao.search("innerID",user.getInnerID()).isEmpty())//此时为添加课程
                res=LessonDao.addLesson(user);
            else //if(删除课程（内部ID）)
                res=LessonDao.addLesson(user);
                //else res=false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public List<Lesson> viewAllLessons() {
        List<Lesson> res = null;
        try {
            res = LessonDao.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public List<Lesson> viewSpecificLessons(String field,Object value) {
        List<Lesson> res = null;
        try {
            res = LessonDao.search(field, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public boolean addGrade(String studentID, String innerID, Integer grade) {
        boolean res;
        try {
            res=LessonDao.addGrade(studentID, innerID, grade);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean deleteone(String deleteoneID) {
        boolean res;
        try {
            res=LessonDao.deleteLesson(deleteoneID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean setLesson(Lesson user) {
        boolean res;
        try {
            if(LessonDao.search("innerID",user.getLessonID()).isEmpty())//没有这个内部ID
                res=false;
            else if(LessonDao.deleteLesson(user.getInnerID()))//删除成功
                res=LessonDao.addLesson(user);//添加课程
            else res=false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public List<Lesson> searchMine(String studentID) {
        List<Lesson> res = null;
        try {
            res = LessonDao.searchMine(studentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public List<Student> searchStudent(String studentID) {
        List<Student> res = null;
        try {
            res=StudentDao.search("studentID", studentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public String judgeLesson(String studentID,String innerID) {
        String res;
        try {
            switch(LessonDao.judgeLesson(studentID,innerID)){
                case 0:
                    res= "Selected";//已选
                    break;
                case 1:
                    res="full";//已满
                    break;
                case 2:
                    res="conflict";//冲突
                    break;
                case 3:
                    res="Optional";//可选
                    break;
                default:
                    res="NO";//错误
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "NO";
        }
        return res;
    }
    public boolean returnLesson(String studentID,String innerID) {
        boolean res;
        try {
            res=LessonDao.returnLesson(studentID, innerID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean selectLesson(String studentID,String innerID) {
        boolean res;
        try {
            res=LessonDao.selectLesson(studentID, innerID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
}
