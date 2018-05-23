package top.gotoeasy.framework.ioc.beanconfig.config26;

import java.lang.reflect.Method;

import top.gotoeasy.framework.aop.Enhance;
import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.aop.annotation.Before;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.ioc.annotation.Autowired;

@Aop
public class Aop26 {

    private static final Log log = LoggerFactory.getLogger(Aop26.class);

    @Autowired
    private Bean2602         bean;

    @Before
    public void before(Method method, Enhance enhance) {
        log.debug("@Before {}.{}", enhance.getClass().getCanonicalName(), method.getName());
    }

    public Bean2602 getBean() {
        return bean;
    }

}
