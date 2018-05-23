package top.gotoeasy.framework.ioc.beanconfig.config99;

public class Student99 {

    private String    name;
    private int       age;

    private School99  school99;
    private Teacher99 teacher99;

    public Student99() {

    }

    public Student99(String name) {
        this.name = name;
    }

    public Student99(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Teacher99 getTeacher() {
        return teacher99;
    }

    public void setTeacher(Teacher99 teacher99) {
        this.teacher99 = teacher99;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public School99 getSchool() {
        return school99;
    }

    public void setSchool(School99 school99) {
        this.school99 = school99;
    }

}
