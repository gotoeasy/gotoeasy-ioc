package top.gotoeasy.framework.ioc.beanconfig.config17;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Student17 {

    private String name;
    private int    age;

    @Autowired
    public Student17(@Autowired("name") String name, @Autowired("age") int age, @Autowired Object ingore) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

}
