package top.gotoeasy.framework.ioc.sample94;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;

@Aop
public class Sample94Aop {

    private static final Log log = LoggerFactory.getLogger(Sample94Aop.class);

    private Sample94Aop() {
        log.debug("Sample94Aop");
    }

    public static String hello(String name) {
        return "Hello " + name;
    }
}
