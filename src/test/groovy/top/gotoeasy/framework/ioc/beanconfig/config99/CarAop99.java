package top.gotoeasy.framework.ioc.beanconfig.config99;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.aop.annotation.Before;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;

@Aop
public class CarAop99 {

    private static final Log log = LoggerFactory.getLogger(CarAop99.class);

    @Before(classes = Car99.class)
    public void before() {
        log.debug("@Before");
    }
}
