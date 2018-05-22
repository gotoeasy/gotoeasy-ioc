package top.gotoeasy.framework.ioc.beanconfig.config11;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Student11 {

    @Autowired("age")
    private int    age;

    @Autowired
    private String name;

    @Autowired
    public Student11(@Autowired Object obj, @Autowired("xxx") Object obj2) {

    }

    @Autowired
    public void setAbcdEfg() {

    }

    @Autowired
    public void setAbcdEfg(@Autowired Object obj, @Autowired("xxx") Object obj2) {

    }

    @Autowired
    public void setAbcdEfg(Object obj) {

    }
}
