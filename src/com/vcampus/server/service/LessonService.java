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
}
