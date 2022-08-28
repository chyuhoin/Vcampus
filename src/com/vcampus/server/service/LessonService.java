package com.vcampus.server.service;

import com.vcampus.dao.*;
import com.vcampus.dao.utils.ClassTable;
import com.vcampus.pojo.Lesson;
import com.vcampus.pojo.LessonGrade;
import com.vcampus.pojo.Student;
import com.vcampus.pojo.Teacher;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

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
            else if (lessons.get(0).getInnerID().equals(lessons.get(0).getLessonID()))//此时为空课程
                if (LessonDao.deleteSpecificLesson(user.getLessonID()))
                {//删除成功
                    res = LessonDao.addLesson(user);
                    if(!res){//添加失败，恢复
                        LessonDao.addLesson(lessons.get(0));
                    }
                    else{//添加课程成功，现在添加老师的课表
                        res=TeacherDao.selectLesson(user.getTeacherID(),user.getInnerID());
                        if(!res){//添加老师课表失败，现在恢复
                            LessonDao.deleteLesson(user.getInnerID());
                            LessonDao.addLesson(lessons.get(0));
                        }
                        else{//添加老师课表成功，现在添加教室
                            res=LessonDao.selectLessonForClassroom(user.getTime(),user.getInnerID(),user.getClassroom());
                            if(!res){//添加老师课表失败，现在恢复
                                LessonDao.deleteLesson(user.getInnerID());
                                TeacherDao.returnLesson(user.getTeacherID(),user.getInnerID());
                                LessonDao.addLesson(lessons.get(0));
                            }
                        }
                    }
                }
                else {//删除失败
                    res = false;
                }
            else{//此时已经没有空课程
                lessons=LessonDao.search("innerID", user.getInnerID());
                if (lessons.isEmpty())//此时为添加课程
                    if(LessonDao.addLesson(user)){//添加成功
                        res=TeacherDao.selectLesson(user.getTeacherID(),user.getInnerID());
                        if(!res){
                            //添加老师课表失败，恢复
                            LessonDao.deleteLesson(user.getInnerID());
                        }
                        else{
                            //添加老师课表成功，现在开始添加教室课表
                            res=LessonDao.selectLessonForClassroom(user.getTime(),user.getInnerID(),user.getClassroom());
                            if(!res){
                                //添加教室课表失败，恢复
                                LessonDao.deleteLesson(user.getInnerID());
                                TeacherDao.returnLesson(user.getTeacherID(),user.getInnerID());
                            }
                        }
                    }
                else res=false;
                else{//此时为修改课程
                    res=setLessonnew(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean addLessonnew(Lesson user) {
        boolean res;
        try {
            if(!LessonDao.search("innerID",user.getInnerID()).isEmpty()){//已经有这个内部ID
//                System.out.println("已经有这个内部ID");
                res=false;
            }
            else {
                user.setLength(setLength(user.getTime()));//设定学时
                res=LessonDao.addLesson(user);
                if(!res){
                    System.out.println("添加失败");
                    return false;
                    }
                if(user.getTeacherID() != null&&user.getStatus()!=0){
                    //添加课程时也添加了老师且此时为有效课
                    //因此要添加老师的课表
                    System.out.println("添加老师课表");
                    res=TeacherDao.selectLesson(user.getTeacherID(),user.getInnerID());
                    if(!res){
                        //添加失败，恢复
                        System.out.println("添加老师课表,失败");
                        LessonDao.deleteLesson(user.getInnerID());
                        return false;
                    }
                }
                if(user.getClassroom()!= null&&user.getStatus()!=0){
                    //添加课程时也添加了教室且此时为有效课
                    //因此要添加教室的课表
                    res=LessonDao.selectLessonForClassroom(user.getTime(),user.getInnerID(),user.getClassroom());
                    if(!res){
                        //添加失败，恢复
                        LessonDao.deleteLesson(user.getInnerID());
                        TeacherDao.deleteTeacher(user.getTeacherID());
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean deleteTest(String innerID) {
        //删除课程
        //如果为有效课且有老师信息，删除老师课表
        //如果为有效课且有教室信息，删除教室信息
        boolean res;
        List<Lesson>lessons=null;
        try {
            lessons=(LessonDao.search("innerID",innerID));
            Lesson user=lessons.get(0);
            if(LessonDao.search("innerID",user.getInnerID()).isEmpty())//没有这个内部ID
                res=false;
            else {
                res=LessonDao.deleteLesson(user.getInnerID());
                if(!res)return false;
                if(user.getTeacherID() != null&&user.getStatus()!=0){
                    //课程有老师且此时为有效课
                    //因此要删除老师的课表
                    res=TeacherDao.returnLesson(user.getTeacherID(),user.getInnerID());
                    if(!res){
                        //删除失败，恢复
                        LessonDao.addLesson(user);
                        return false;
                    }
                }
                if(user.getClassroom()!= null&&user.getStatus()!=0){
                    //添加课程时也添加了教室且此时为有效课
                    //因此要添加教室的课表
                    res=LessonDao.returnLessonForClassroom(user.getInnerID(),user.getClassroom());
                    if(!res){
                        //删除失败，恢复
                        if(user.getTeacherID() != null&&user.getStatus()!=0){
                            TeacherDao.selectLesson(user.getTeacherID(),user.getInnerID());
                        }
                        LessonDao.addLesson(user);
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean setLessonnew(Lesson user) {
        boolean res=false;
        try {
            List<Lesson>lessons=LessonDao.search("innerID",user.getInnerID());
//            System.out.println(lessons);
            if(lessons.isEmpty())//没有这个内部ID
                res=false;
            else {
                if(deleteTest(user.getInnerID())){
                    user.setLength(setLength(user.getTime()));//设定学时
//                    System.out.println(setLength(user.getTime()));
                    res=addLessonnew(user);
                    if(!res){
                        //添加失败，恢复
                        addLessonnew(lessons.get(0));
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
    public List<Teacher> showAllTeacher(String lessonID) {
        List<Teacher> teachers=new ArrayList<>();
        try {
            List<Lesson>lessons=LessonDao.search("lessonID",lessonID);
            for(Lesson lesson:lessons){
                List<Teacher>tmp=TeacherDao.searchTeacher("teacherID",lesson.getTeacherID());
                for(Teacher temp:tmp){
                    teachers.add(temp);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
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
    public List<Teacher> viewTeachersTime(String time,String abledMajor) {
        List<Teacher> res = new ArrayList<>();
        try {
            List<Teacher> tmp = TeacherDao.searchTeacher("abledMajor",abledMajor);
            for(Teacher teacher:tmp){
                boolean isadd=true;
                String teacherID=teacher.getTeacherID();
                String teachertable=TeacherDao.getLessonTable(teacherID);//取得老师课表
                String[] temp = teachertable.split(",");//根据，切分字符串
                List<Integer>times=ClassTable.getTimeIndex(time);
                List<Integer>teacherunlikes=ClassTable.getTimeIndex(teacher.getTime());
                //老师不喜欢的时间
                for(Integer tmptime:times ){
                    for(Integer teacherunlike:teacherunlikes){
                        if(tmptime==teacherunlike){
                            isadd=false;
                            break;
                        }
                    }
                    if(!temp[tmptime].equals("0")){
                    //如果此时老师课表有课或为非偏好时间
                        isadd=false;
                        break;
                    }
                }
                if(isadd)res.add(teacher);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }
    public String showTable(String studentID) {
        String time=null;
        try {
            time = LessonDao.getLessonTable(studentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }
    public String showTableName(String time) {
        String timename=null;
        try {
            List<Lesson>lesson=null;
            String[] tmp = time.split(",");//根据，切分字符串
            for(int i=0;i<65;i++){
                if(!tmp[i].equals("0")){
                    //此时有课程，将内部ID变为课程名字
                    lesson=LessonDao.search("innerID",tmp[i]);
                    tmp[i]=lesson.get(0).getName();
                    tmp[i]=lesson.get(0).getName();
                }
            }
            timename = String.join(",",tmp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timename;
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
    public List<LessonGrade> getGrade(String studentID) {
        List<LessonGrade> res = null;
        try {
            res = LessonDao.getGrade(studentID);
        } catch (Exception e) {
            e.printStackTrace();
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
                res=TeacherDao.returnLesson(lessons.get(0).getTeacherID(),lessons.get(0).getInnerID());
                if(!res)return res;
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
                //再执行教室退课函数
                res=LessonDao.deleteAllFromClassroom(deleteoneID);
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
        List<Student> res = new ArrayList<>();
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
        List<Student> res = new ArrayList<>();
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
                if(true)return true;
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
    public boolean Arrange() {
        boolean res=false;
        try {
            List<Lesson>tmp=LessonDao.search("status",0);
            List<Lesson>lessons=null;
            for(Lesson data:tmp){
                if(!data.getInnerID().equals(data.getLessonID())){//此时不为空课程
                    lessons.add(data);
                }
            }
            if(doArrange(lessons)>0){
                for(Lesson lesson:lessons){
                    res=valueLesson(lesson.getInnerID());
                    if(!res)return false;
                }
                res=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean ArrangeAll() {
        boolean res=false;
        try {
            List<Lesson>tmp=LessonDao.search("status",1);
            for(Lesson data:tmp){
                res=unvalueLesson(data.getInnerID());
                if(!res)return false;
            }
            tmp=LessonDao.search("status",0);
            List<Lesson>lessons=null;
            for(Lesson data:tmp){
                if(!data.getInnerID().equals(data.getLessonID())){//此时不为空课程
                    lessons.add(data);
                }
            }
            if(doArrange(lessons)>0){
                for(Lesson lesson:lessons){
                    res=valueLesson(lesson.getInnerID());
                    if(!res)return false;
                }
                res=true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean valueLesson(String innerID) {
        //把对应课程的状态从0变为1
        boolean res=false;
        try {
            List<Lesson> lessons=LessonDao.search("innerID",innerID);
            lessons.get(0).setStatus(1);
            res=setLessonnew(lessons.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public boolean unvalueLesson(String innerID) {
        //把对应课程的状态从1变为0
        boolean res=false;
        try {
            List<Lesson> lessons=LessonDao.search("innerID",innerID);
            lessons.get(0).setStatus(0);
            res=setLessonnew(lessons.get(0));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return res;
    }
    public Integer doArrange(List<Lesson>lessons) {
        //实现逻辑：采用近似算法
        //lessons为要进行排课的课程集合，先从中取出一个进行处理，其余继续采用这个函数计算总分
        //先用findTime函数给出所有可能的时间排列，再利用scoreTime给出这个时间的分数
        //在此课程视为这个时间的基础上进行其他课程的计算，最后取最高分情况

        Integer max=0;
        Integer position=0;
        Integer tmp=0;
        try {
            Lesson lesson=lessons.get(0);
            lessons.remove(0);
            List<List<Integer>>times=findTime(lesson.getInnerID());
            Integer i=-1;
            for(List<Integer>time:times){
                i++;
                String roomID=LessonDao.abledRoom(returnTime(time)).get(0);
                Lesson user=new Lesson(lesson.getLessonID(),lesson.getName(),lesson.getTeacherID(),lesson.getMaxSize(),lesson.getLeftSize(),returnTime(time),lesson.getSchool(),lesson.getMajor(),lesson.getIsExam(),roomID,lesson.getLength(),1);
                setLessonnew(user);
                tmp=scoreTime(time,lesson.getInnerID())+doArrange(lessons);
                if(tmp>max){
                    max=tmp;
                    position=i;
                }
            }
            String roomID=LessonDao.abledRoom(returnTime(times.get(position))).get(0);
            Lesson user=new Lesson(lesson.getLessonID(),lesson.getName(),lesson.getTeacherID(),lesson.getMaxSize(),lesson.getLeftSize(),returnTime(times.get(position)),lesson.getSchool(),lesson.getMajor(),lesson.getIsExam(),roomID,lesson.getLength(),0);
            setLessonnew(user);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return max;
    }
    public List<List<Integer>> findTime(String innerID) {
        //要求:选择一个合适的时间
        List<List<Integer>> res=null;
        List<Integer>tmp=null;
        try {
            List<Lesson> lesson=LessonDao.search("innerID",innerID);
            Integer length=lesson.get(0).getLength();
            switch(length){
                case 1:{
                    for(int i=0;i<64;i++){
                        tmp=null;
                        tmp.add(i);
                        if(isTimeOK(tmp,innerID))res.add(tmp);
                    }
                    break;
                }
                case 2:{
                    for(int i=0;i<64;i++){
                        tmp.add(i);
                        for(int j=i+1;j<64;j++){
                        tmp.add(j);
                        if(isTimeOK(tmp,innerID))res.add(tmp);
                        tmp.remove(tmp.size()-1);//删除最后一个元素
                        }
                        tmp.remove(tmp.size()-1);//删除最后一个元素
                    }
                    break;
                }
                case 3:{
                    for(int i=0;i<64;i++){
                        tmp.add(i);
                        for(int j=i+1;j<64;j++){
                            tmp.add(j);
                            for(int k=j+1;k<64;k++){
                                tmp.add(k);
                                if(isTimeOK(tmp,innerID))res.add(tmp);
                                tmp.remove(tmp.size()-1);//删除最后一个元素
                            }
                            tmp.remove(tmp.size()-1);//删除最后一个元素
                        }
                        tmp.remove(tmp.size()-1);//删除最后一个元素
                    }
                    break;
                }
                case 4:{
                    for(int i=0;i<64;i++){
                        tmp.add(i);
                        for(int j=i+1;j<64;j++){
                            tmp.add(j);
                            for(int k=j+1;k<64;k++){
                                tmp.add(k);
                                for(int l=k+1;l<64;l++){
                                    tmp.add(l);
                                    if(isTimeOK(tmp,innerID))res.add(tmp);
                                    tmp.remove(tmp.size()-1);//删除最后一个元素
                                }
                                tmp.remove(tmp.size()-1);//删除最后一个元素
                            }
                            tmp.remove(tmp.size()-1);//删除最后一个元素
                        }
                        tmp.remove(tmp.size()-1);//删除最后一个元素
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res;
    }
    public boolean isTimeOK(List<Integer>times,String innerID) {
        //判断对于这个老师来说，这个时间是否可以
        //判断对于教室资源来说，这个时间是否可以
        //判断对于这个专业的课来说，这个时间是否可以
        try {
            List<Lesson> lesson=LessonDao.search("innerID",innerID);
            if(lesson.isEmpty())return false;
            //判断对于这个老师来说，这个时间是否可以
            String teacherID=lesson.get(0).getTeacherID();
            String teachertable=TeacherDao.getLessonTable(teacherID);//取得老师课表
            String[] tmp = teachertable.split(",");//根据，切分字符串
            for(Integer time:times ){
                if(!tmp[time].equals("0")&&!tmp[time].equals("1"))
                    //如果此时老师课表有课
                    return false;
            }
            //判断对于教室资源来说，这个时间是否可以
            if(LessonDao.abledRoom(returnTime(times)).isEmpty())
                return false;
            //判断对于这个专业的课来说，这个时间是否可以
            //此时，这门一定要是有效课，status==1
            List<Lesson> lessons=LessonDao.search("major",lesson.get(0).getMajor());
            for(Lesson tmplesson:lessons){
                if(tmplesson.getStatus()==1&&tmplesson.getLessonID()!=lesson.get(0).getLessonID()){
                    //该课为有效课且不为同一门课
                    List<Integer> intersection = ClassTable.getTimeIndex(tmplesson.getTime()).stream().filter(times::contains).collect(toList());
                    if(!intersection.isEmpty())//此时存在交集，即课程冲突
                        return false;
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    public Integer scoreTime(List<Integer> time,String innerID) {
        //要求:给出一个时间，判断其可得几分
        Integer res=0;//得分
        try {
            List<Integer>day=null;//相对时间 0-12
            List<Integer>week=null;//星期 0-4
            List<Integer>noon=null;//上午，下午，晚上 0-2
            for(Integer tmp:time){
                day.add(tmp%13);
                week.add(tmp/13);
                noon.add((tmp%13)/5);
            }
            //时间尽可能连续
            //时长为2，3，则每节课都连着，为4则2，2相连
            switch(time.size()){
                case 2:{
                    if(time.get(0)+1==time.get(1)&&noon.get(0)==noon.get(1))
                        res+=1;
                    break;
                }
                case 3:{
                    if(time.get(0)+1==time.get(1)&&time.get(0)+2==time.get(2)&&noon.get(0)==noon.get(1)&&noon.get(0)==noon.get(2))
                        res+=1;
                    break;
                }
                case 4:{
                    if(time.get(0)+1==time.get(1)&&time.get(2)+1==time.get(3)&&noon.get(0)==noon.get(1)&&noon.get(2)==noon.get(3))
                        res+=1;
                    break;
                }
            }
            //时间尽可能分散
            //时长为4则尽量离远一点
            switch(time.size()){
                case 4:{
                    if(week.get(0)+2<=week.get(2))
                        res+=1;
                    break;
                }
            }
            //尽可能不占用老师的非偏好时间
            List<Lesson> lesson=LessonDao.search("innerID",innerID);
            //判断对于这个老师来说，这个时间是否可以
            String teacherID=lesson.get(0).getTeacherID();
            String teachertable=TeacherDao.getLessonTable(teacherID);//取得老师课表
            String[] temp = teachertable.split(",");//根据，切分字符串
            for(Integer tmp:time ){
                if(temp[tmp].equals("1"))
                    //如果此时为老师非偏好时间
                    res-=1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return res;
    }
    public String returnTime(List<Integer>times) {
        StringBuilder res= null;
        try {
            Integer day=null;//相对时间 1-13
            Integer week=null;//星期 1-5
            Integer i=0;
            for(Integer time:times){
                day=time%13+1;
                week=time/13+1;
                if(i!=0)res.append(",");
                i++;
                res.append(week.toString()).append("/").append(day.toString()).append("/").append(day.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res.toString();
    }
    public Integer setLength(String time) {
        //给出时间，计算其时长
        Integer length=0;
        try {
            length=ClassTable.getTimeIndex(time).size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return length;
    }
}
