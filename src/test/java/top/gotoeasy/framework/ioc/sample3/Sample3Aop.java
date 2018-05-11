package top.gotoeasy.framework.ioc.sample3;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.aop.annotation.Before;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.ioc.annotation.Autowired;

@Aop("aop")
public class Sample3Aop {

    private static final Log log = LoggerFactory.getLogger(Sample3Aop.class);

    private Sample3Bean1     bean1;
    private Sample3Bean2     bean2;

    @Before("*.Sample3Bean2.hello(*)")
    public void before() {
        log.debug("@Before");
    }

    public Sample3Bean1 getBean1() {
        return bean1;
    }

    public Sample3Bean2 getBean2() {
        return bean2;
    }

    @Autowired
    public void setBean(Sample3Bean1 bean1, Sample3Bean2 bean2) {
        this.bean1 = bean1;
        this.bean2 = bean2;
    }

}
