package top.gotoeasy.framework.ioc.beanconfig.config6;

import top.gotoeasy.framework.ioc.annotation.Component;

@Component("Jacky")
public class Student6_2 {

    private int    age;
    private String phone;
    private String name;

    public Student6_2() {
        this.name = "匿名";
    }

    public Student6_2(String name) {
        this.name = name;
    }

    public Student6_2(String name, int age) {
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
