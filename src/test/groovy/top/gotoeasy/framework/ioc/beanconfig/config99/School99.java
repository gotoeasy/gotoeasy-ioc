package top.gotoeasy.framework.ioc.beanconfig.config99;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class School99 {

    @Autowired("teacherZhang")
    private Teacher99 teacherZhang;

    private Teacher99 teacherLi;

    public Teacher99 getTeacherZhang() {
        return teacherZhang;
    }

    public void setTeacherZhang(Teacher99 teacherZhang) {
        this.teacherZhang = teacherZhang;
    }

    public Teacher99 getTeacherLi() {
        return teacherLi;
    }

    @Autowired("teacherLi")
    public void setTeacherLi(Teacher99 teacherLi) {
        this.teacherLi = teacherLi;
    }

}
