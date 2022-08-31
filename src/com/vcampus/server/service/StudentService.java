package com.vcampus.server.service;

import com.vcampus.dao.StudentDao;
import com.vcampus.pojo.Student;
import com.vcampus.dao.UserDao;

import java.util.List;

/**
 * 学生服务
 *
 * @author ietot
 * @date 2022/08/31
 */
public class StudentService implements Service{
    /**
     * 增加学生
     *
     * @param user 用户
     * @return boolean
     */
    public boolean addStudent(Student user) {
        boolean res;
        List<Student> students = null;
        try {
            students = StudentDao.search("studentID", user.getStudentID());
            if(!UserDao.search(user.getStudentID())) {
                //用户管理没有这个ID
                res = false;
            }
            else if(students.isEmpty()) {//学籍管理没有对应数据
                res = StudentDao.addStudent(user);
            }
            else if(!StudentDao.deleteStudent(user.getStudentID())) { //删除信息失败
                res = false;
            }
            else {
                res = StudentDao.addStudent(user);
                if(!res) StudentDao.addStudent(students.get(0));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }

    /**
     * 查看所有学生
     *
     * @return {@link List}<{@link Student}>
     */
    public List<Student> viewAllStudents() {
        List<Student> res = null;
        try {
            res = StudentDao.search();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 查看特定学生
     *
     * @param field 指标
     * @param value 数据
     * @return {@link List}<{@link Student}>
     */
    public List<Student> viewSpecificStudents(String field,Object value) {
        List<Student> res = null;
        try {
            res = StudentDao.search(field, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 删除学生
     *
     * @param studentID 学生ID
     * @return boolean
     */
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
