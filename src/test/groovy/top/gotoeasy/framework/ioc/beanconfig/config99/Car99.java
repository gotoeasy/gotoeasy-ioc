package top.gotoeasy.framework.ioc.beanconfig.config99;

import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Car99 {

    private static final Log log = LoggerFactory.getLogger(Car99.class);

    public void run() {
        log.debug("run.....");
    }
}
