package top.gotoeasy.framework.ioc.beanconfig.config99;

import top.gotoeasy.framework.ioc.annotation.Component;

@Component("teacherLiu")
public class Teacher99 {

    private String   name;
    private School99 school99;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School99 getSchool() {
        return school99;
    }

    public void setSchool(School99 school99) {
        this.school99 = school99;
    }

}
