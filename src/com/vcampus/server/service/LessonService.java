package com.vcampus.server.service;

import com.vcampus.dao.LessonDao;
import com.vcampus.dao.StudentDao;
import com.vcampus.dao.UserDao;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;

import java.util.List;
public class LessonService implements Service{
    public boolean addLesson(Lesson user) {
        boolean res = false;
        try {
//            if(!UserDao.search(user.getStudentID()))//用户管理没有这个ID
//                res=false;
//            else if(StudentDao.search("studentID", user.getStudentID()).isEmpty())//学籍管理没有对应数据
//                res = StudentDao.addStudent(user);
//            else if(!StudentDao.deleteStudent(user.getStudentID()))//删除信息失败
//                res=false;
//            else res=StudentDao.addStudent(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
}
