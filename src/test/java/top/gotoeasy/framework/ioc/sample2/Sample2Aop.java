package top.gotoeasy.framework.ioc.sample2;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.aop.annotation.Before;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.ioc.annotation.Autowired;

@Aop
public class Sample2Aop {

    private static final Log log = LoggerFactory.getLogger(Sample2Aop.class);

    @Autowired
    private Sample2Bean2     bean;

    @Before("*.Sample2Bean2.hello(*)")
    public void before() {
        log.debug("@Before");
    }

    public Sample2Bean2 getBean() {
        return bean;
    }

}
