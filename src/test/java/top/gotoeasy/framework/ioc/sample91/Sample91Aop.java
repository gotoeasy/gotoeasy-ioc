package top.gotoeasy.framework.ioc.sample91;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.aop.annotation.Before;

@Aop
public class Sample91Aop {

    @Before("*.Sample99Bean2.hello(*)")
    public void before() {

    }

}
