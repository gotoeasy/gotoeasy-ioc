package top.gotoeasy.framework.ioc.beanconfig.config15;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Student15 {

    private String name;

    @Autowired
    public void setName(@Autowired("a") String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
