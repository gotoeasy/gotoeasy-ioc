package top.gotoeasy.framework.ioc.sample94;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.ioc.sample93.Sample93Bean1;

@Aop
public class Sample94Aop {

    private static final Log log = LoggerFactory.getLogger(Sample93Bean1.class);

    private Sample94Aop() {
        log.debug("Sample94Aop");
    }

}
