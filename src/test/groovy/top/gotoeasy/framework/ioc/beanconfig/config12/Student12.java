package top.gotoeasy.framework.ioc.beanconfig.config12;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Student12 {

    @Autowired
    public Student12(@Autowired("a") Object obj2) {

    }

    @Autowired
    public Student12(@Autowired("b") Object obj, @Autowired("c") Object obj2) {

    }

}
