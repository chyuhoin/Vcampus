package com.vcampus.server.service;

import com.vcampus.dao.*;
import com.vcampus.dao.utils.ClassTable;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.Teacher;

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
        List<Lesson> lessons = null;
        try {
            lessons = LessonDao.search("lessonID", user.getLessonID());
            if (lessons.isEmpty())//没有对应课程ID
                res = false;
            else if (lessons.get(0).getInnerID() == null)//此时为空课程
                if (LessonDao.deleteSpecificLesson(user.getLessonID()))
                {//删除成功
                    res = LessonDao.addLesson(user);
                    if(!res)//添加失败，恢复
                        LessonDao.addLesson(lessons.get(0));
                    else{//添加课程成功，现在添加老师的课表
                        res=TeacherDao.selectLesson(user.getTeacherID(),user.getInnerID());
                        if(!res){//添加老师课表失败，现在恢复
                            LessonDao.addLesson(lessons.get(0));
                        }
                    }
                }
                else {//删除失败
                    res = false;
                }
            else{//此时已经没有空课程
                lessons=LessonDao.search("innerID", user.getInnerID());
                if (lessons.isEmpty())//此时为添加课程
                    if(LessonDao.addLesson(user))//添加成功
                        res=TeacherDao.selectLesson(user.getTeacherID(),user.getInnerID());
                    else res=false;
                else{//此时为修改课程
                    if (LessonDao.deleteLesson(user.getInnerID()))
                    {//删除成功
                        res = LessonDao.addLesson(user);
                        if(!res)//添加失败，恢复
                            LessonDao.addLesson(lessons.get(0));
                        else{//添加成功，现在开始添加老师课表
                            if(TeacherDao.returnLesson(lessons.get(0).getTeacherID(),lessons.get(0).getInnerID())) {
                                //先退选之前的课,且成功
                                res = TeacherDao.selectLesson(user.getTeacherID(), user.getInnerID());
                                if(!res){//老师选课失败，恢复
                                    LessonDao.addLesson(lessons.get(0));
                                    TeacherDao.selectLesson(lessons.get(0).getTeacherID(),lessons.get(0).getInnerID());
                                }
                            }
                            else res=false;
                        }
                    }
                    else {//删除失败
                        res = false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public List<String> showRoom(String time) {
        List<String> room=null;
        try {
            room=LessonDao.abledRoom(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return room;
    }
    public List<Teacher> viewTeachers(String abledMajor) {
        List<Teacher> res = null;
        try {
            res = TeacherDao.searchTeacher("abledMajor",abledMajor);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public String showTecherTime(String teacherID) {
        String time=null;
        try {
            String res = TeacherDao.getLessonTable(teacherID);
            String[] tmp = res.split(",");//根据，切分字符串
            List<Teacher>teacher=TeacherDao.searchTeacher("teacherID",teacherID);
            if(teacher.isEmpty())return null;
            List<Integer>Liketime= ClassTable.getTimeIndex(teacher.get(0).getTime());
            for(Integer liketime:Liketime){
                if(tmp[liketime].equals("0"))//如果非偏好时间没有选课，则将其变为1
                    tmp[liketime]="1";
            }
            time = String.join(",",tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
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
    public boolean addRoom(String roomID, String innerID) {
        boolean res;
        try {
            List<Lesson>lesson=LessonDao.search("innerID",innerID);
            if(lesson.isEmpty())return false;
            res=LessonDao.selectLessonForClassroom(lesson.get(0).getTime(),innerID,roomID);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
    public boolean delete(String deleteID) {
        boolean res=false;
        List<Lesson> lessons=null;
        try {
            lessons=LessonDao.search("lessonID",deleteID);
            if(lessons.isEmpty())//没有这门课
                res=false;
            else for(Lesson lesson:lessons){
                res= deleteone(lesson.getInnerID());
                if(!res)return res;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean deleteone(String deleteoneID) {
        boolean res;
        List<Lesson> lessons=null;
        try {
            lessons=LessonDao.search("innerID",deleteoneID);
            if(lessons.isEmpty())//没有这门课
                res=false;
            else{
                //先执行老师退课函数
                //res=老师退课函数(lessons.get(0).getTeacherID());
                //if(!res)return res;
                //再执行学生退课函数
                List<String> data = null;
                data=LessonDao.searchStudent(deleteoneID);
                for(String student:data){
                    res= LessonDao.returnLesson(student,deleteoneID);
                    if(!res)return res;
                }
                //再执行删除考试函数
                res=ExamDao.deleteExam(deleteoneID);
                if(!res)return res;
                //再执行删除课程函数
                res=LessonDao.deleteLesson(deleteoneID);
                if(!res)return res;
            }
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
    public List<Student> getTeacher(String lessonID) {
        List<Student> res = null;
        List<Lesson>data=null;
        List<Student> tmp = null;
        try {
            data=LessonDao.search("lessonID",lessonID);
            for(Lesson lesson:data){
                tmp=getSpecificTeacher(lesson.getInnerID());
                for(Student student:tmp){
                    res.add(student);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public List<Student> getSpecificTeacher(String innerID) {
        List<Student> res = null;
        List<String>data=null;
        List<Student> tmp = null;
        try {
            data=LessonDao.searchStudent(innerID);
            for(String student:data){
                tmp=StudentDao.search("studentID",student);
                if(!tmp.isEmpty())res.add(tmp.get(0));
            }
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
    public List<Teacher> searchTeacher(String teacherID) {
        List<Teacher> res = null;
        try {
            res=TeacherDao.searchTeacher("teacherID", teacherID);
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
    public boolean addTeacher(Teacher user) {
        boolean res;
        List<Teacher> teachers = null;
        try {
            teachers = TeacherDao.searchTeacher("teacherID", user.getTeacherID());
            if(!UserDao.search(user.getTeacherID())) {
                //用户管理没有这个ID
                res = false;
            }
            else if(teachers.isEmpty()) {
                //没有对应数据
                //视为添加
                res = TeacherDao.addTeacher(user);
            }
            else if(!TeacherDao.deleteTeacher(user.getTeacherID())) {//视为修改
                //删除信息失败
                res = false;
            }
            else {
                //删除信息成功
                res = TeacherDao.addTeacher(user);
                if(!res) TeacherDao.addTeacher(teachers.get(0));//添加失败，恢复
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
}
