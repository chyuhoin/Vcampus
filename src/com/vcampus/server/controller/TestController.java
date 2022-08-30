package com.vcampus.server.controller;

import com.google.gson.Gson;
import com.vcampus.net.Message;
import com.vcampus.pojo.Exam;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.Teacher;
import com.vcampus.server.service.TestService;

import java.util.HashMap;

public class TestController implements Controller{

    @Override
    public Message check(Message msg){
        TestService service = new TestService();
        Gson gson = new Gson();
        switch (msg.getOperation()) {
            case "post":
                //添加考试信息,基于内部ID
                //传入exam的类
                Exam exam1 = gson.fromJson(msg.getData(), Exam.class);
                if(service.addExam(exam1)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "postall":
                //添加考试信息，基于课程ID
                //传入exam的类,此时innnerID传课程ID
                Exam exam2 = gson.fromJson(msg.getData(), Exam.class);
                if(service.addAllExam(exam2)) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "delete":
                //删除考试
                //输入内部ID
                Exam exam3 = gson.fromJson(msg.getData(), Exam.class);
//                String innerID=msg.getData();
//                if(service.deleteExam(innerID)) return new Message("200", "{res: 'OK'}");
                if(service.deleteExam(exam3.getinnerID())) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "deleteall":
                //删除考试
                //输入课程ID
                Exam exam4 = gson.fromJson(msg.getData(), Exam.class);
//                String lessonID=msg.getData();
//                if(service.deleteAllExam(lessonID)) return new Message("200", "{res: 'OK'}");
                if(service.deleteAllExam(exam4.getinnerID())) return new Message("200", "{res: 'OK'}");
                else return new Message("200", "{res: 'NO'}");
            case "showstudent":
                //学生查看自己安排的考试
                //输入学生ID
                Student studentID = gson.fromJson(msg.getData(), Student.class);
//                String studentID = msg.getData();
                HashMap<String, Object> map1 = new HashMap<>();
                map1.put("res", service.searchMyExamForStudent(studentID.getStudentID()));
                return new Message("200", gson.toJson(map1));
            case "showteacher":
                //老师查看自己监考的考试
                //输入老师ID
                Teacher teacherID = gson.fromJson(msg.getData(), Teacher.class);
//                String teacherID = msg.getData();
                HashMap<String, Object> map2= new HashMap<>();
                map2.put("res", service.searchMyExamForTeacher(teacherID.getTeacherID()));
                return new Message("200", gson.toJson(map2));
            case"showmyteacher":
                //显示老师上的课的考试
                //输入老师ID
                Teacher myteacherID = gson.fromJson(msg.getData(), Teacher.class);
//                String myteacherID = msg.getData();
                HashMap<String, Object> map3= new HashMap<>();
                map3.put("res", service.searchMyTeacherExam(myteacherID.getTeacherID()));
                return new Message("200", gson.toJson(map3));
            default:
                return new Message("404", "{res: 'Wrong Request!'}");
        }
    }
}
