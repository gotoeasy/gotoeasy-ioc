package top.gotoeasy.framework.ioc.sample4;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.aop.annotation.Before;
import top.gotoeasy.framework.ioc.annotation.Autowired;

@Aop
public class Sample4Aop {

    @Autowired
    private Sample4Bean2 bean;

    @Before("*.Sample4Bean2.hello(*)")
    public void before() {

    }

    public Sample4Bean2 getBean() {
        return bean;
    }

}
