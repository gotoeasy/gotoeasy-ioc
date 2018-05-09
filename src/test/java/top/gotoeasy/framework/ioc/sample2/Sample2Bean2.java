package top.gotoeasy.framework.ioc.sample2;

import top.gotoeasy.framework.ioc.annotation.Component;

@Component()
public class Sample2Bean2 {

    public String hello(String name) {
        return "Hello " + name;
    }
}
