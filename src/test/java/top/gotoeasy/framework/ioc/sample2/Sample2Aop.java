package top.gotoeasy.framework.ioc.sample2;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.aop.annotation.Before;
import top.gotoeasy.framework.ioc.annotation.Autowired;

@Aop
public class Sample2Aop {

    @Autowired
    private Sample2Bean2 bean;

    @Before("*.Sample2Bean2.hello(*)")
    public void before() {

    }

    public Sample2Bean2 getBean() {
        return bean;
    }

}
