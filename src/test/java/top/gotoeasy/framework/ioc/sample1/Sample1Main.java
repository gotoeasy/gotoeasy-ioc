package top.gotoeasy.framework.ioc.sample1;

import top.gotoeasy.framework.core.config.DefaultConfig;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.ioc.impl.DefaultIoc;

public class Sample1Main {

    private static final Log log = LoggerFactory.getLogger(Sample1Main.class);

    public static void main(String[] args) {

        // 无参数构造,单纯字段注入,无AOP
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample1");

        DefaultIoc ioc = new DefaultIoc();
        Sample1Bean1 bean = ioc.getBean(Sample1Bean1.class);

        log.debug("Sample1Bean1: {}", bean);
        log.debug("Sample1Bean1.getSample1Bean2(): {}", bean.getSample1Bean2());
    }
}
