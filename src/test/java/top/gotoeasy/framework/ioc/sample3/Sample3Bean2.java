package top.gotoeasy.framework.ioc.sample3;

import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Sample3Bean2 {

    public String hello(String name) {
        return "Hello " + name;
    }
}
