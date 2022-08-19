package com.vcampus.server.service;

import com.vcampus.dao.StudentDao;
import com.vcampus.pojo.Student;
import com.vcampus.dao.UserDao;

import java.util.List;

public class StudentService implements Service{
    public boolean addStudent(Student user) {
        boolean res;
        try {
            if(!UserDao.search(user.getStudentID()))//用户管理没有这个ID
                res=false;
            else if(StudentDao.search("studentID", user.getStudentID()).isEmpty())//学籍管理没有对应数据
                res = StudentDao.addStudent(user);
            else if(!StudentDao.deleteStudent(user.getStudentID()))//删除信息失败
                res=false;
            else res=StudentDao.addStudent(user);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public List<Student> viewAllStudents() {
        List<Student> res = null;
        try {
            res = StudentDao.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public List<Student> viewSpecificStudents(String field,Object value) {
        List<Student> res = null;
        try {
            res = StudentDao.search(field, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public boolean deleteStudent(String studentID) {
        boolean res;
        try {
            res = StudentDao.deleteStudent(studentID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
}
