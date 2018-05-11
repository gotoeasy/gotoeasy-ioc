package top.gotoeasy.framework.ioc.sample91;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.aop.annotation.Before;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;

@Aop
public class Sample91Aop {

    private static final Log log = LoggerFactory.getLogger(Sample91Aop.class);

    @Before("*.Sample99Bean2.hello(*)")
    public void before() {
        log.debug("@Before");
    }

}
