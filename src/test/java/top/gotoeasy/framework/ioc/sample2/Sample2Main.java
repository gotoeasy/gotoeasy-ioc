package top.gotoeasy.framework.ioc.sample2;

import top.gotoeasy.framework.core.config.DefaultConfig;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.ioc.impl.DefaultIoc;

public class Sample2Main {

    private static final Log log = LoggerFactory.getLogger(Sample2Main.class);

    public static void main(String[] args) {

        // 无参数构造,单纯字段注入,有AOP
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample2");

        DefaultIoc ioc = new DefaultIoc();
        Sample2Bean1 bean = ioc.getBean(Sample2Bean1.class);

        log.debug("Sample2Bean1: {}", bean);
        log.debug("Sample2Bean1.getSample2Bean2(): {}", bean.getSample2Bean2());
        log.debug("Sample2Aop.getBean().hello(\"world\"): {}", ioc.getBean(Sample2Aop.class).getBean().hello("world"));
    }
}
