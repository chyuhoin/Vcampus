package com.vcampus.server.service;

import com.vcampus.dao.ExamDao;
import com.vcampus.dao.LessonDao;
import com.vcampus.pojo.Exam;
import com.vcampus.pojo.Lesson;

import java.util.List;

public class TestService implements Service{
    public boolean addExam(Exam user) {
        boolean res;
        try {
            res= ExamDao.addExam(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean addAllExam(Exam user) {
        //首先，将user的innerID取出，视为课程ID
        //通过课程ID查到innerID
        //依次构建Exam类
        //依次添加
        boolean res=false;
        List<Lesson> data = null;
        try {
            data= LessonDao.search("lessonID",user.getinnerID());
            for(Lesson lesson:data){
                Exam exam = new Exam(lesson.getInnerID(), user.getTeacherID(),user.getTime());
                res= ExamDao.addExam(exam);
                if(!res)return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean deleteExam(String innerID) {
        boolean res=false;
        try {
            res= ExamDao.deleteExam(innerID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean deleteAllExam(String lessonID) {
        //通过课程ID查到innerID
        //依次删除
        boolean res=false;
        List<Lesson> data = null;
        try {
            data= LessonDao.search("lessonID",lessonID);
            for(Lesson lesson:data){
                res= ExamDao.deleteExam(lesson.getInnerID());
                if(!res)return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
}
