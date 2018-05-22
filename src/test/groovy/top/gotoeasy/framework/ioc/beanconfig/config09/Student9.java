package top.gotoeasy.framework.ioc.beanconfig.config09;

public class Student9 {

    private int    age;
    private String phone;
    private String name;

    public Student9() {
        this.name = "匿名";
    }

    public Student9(String name) {
        this.name = name;
    }

    public Student9(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
