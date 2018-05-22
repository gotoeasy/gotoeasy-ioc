package top.gotoeasy.framework.ioc.beanconfig.config16;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.ioc.annotation.Autowired;

@Aop
public class Aop16Before {

    @Autowired("aaa")
    private int age;

    public int getAge() {
        return age;
    }

}
