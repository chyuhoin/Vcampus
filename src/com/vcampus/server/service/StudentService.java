package com.vcampus.server.service;

import com.vcampus.dao.StudentDao;
import com.vcampus.pojo.Student;

import java.util.List;

public class StudentService implements Service{
    public boolean addStudent(Student user) {
        boolean res;
        try {
            res = StudentDao.addStudent(user);
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
