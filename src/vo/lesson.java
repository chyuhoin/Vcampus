package vo;

public class lesson {
    private String lessonID;
    private String name;
    private String teacher;
    private int maxSize;
    private int leftSize;
    private String time;

    public lesson(String lessonID, String name, String teacher, int maxSize, int leftSize, String time) {
        this.lessonID = lessonID;
        this.name = name;
        this.teacher = teacher;
        this.maxSize = maxSize;
        this.leftSize = leftSize;
        this.time = time;
    }

    public String getLessonID() {
        return lessonID;
    }

    public void setLessonID(String lessonID) {
        this.lessonID = lessonID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public int getLeftSize() {
        return leftSize;
    }

    public void setLeftSize(int leftSize) {
        this.leftSize = leftSize;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "lesson{" +
                "lessonID='" + lessonID + '\'' +
                ", name='" + name + '\'' +
                ", teacher='" + teacher + '\'' +
                ", maxSize=" + maxSize +
                ", leftSize=" + leftSize +
                ", time='" + time + '\'' +
                '}';
    }
}
