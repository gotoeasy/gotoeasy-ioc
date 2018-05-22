package top.gotoeasy.framework.ioc.beanconfig.config14;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Student14 {

    @Autowired("xxxxx")
    private String name;

    public String getName() {
        return name;
    }

}
