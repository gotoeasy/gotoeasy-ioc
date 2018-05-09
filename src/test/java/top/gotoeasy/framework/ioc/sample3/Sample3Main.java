package top.gotoeasy.framework.ioc.sample3;

import top.gotoeasy.framework.core.config.DefaultConfig;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.ioc.impl.DefaultIoc;

public class Sample3Main {

    private static final Log log = LoggerFactory.getLogger(Sample3Main.class);

    public static void main(String[] args) {

        // 无参数构造,字段注入+方法注入,有AOP
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample3");

        DefaultIoc ioc = new DefaultIoc();
        Sample3Bean1 bean = ioc.getBean(Sample3Bean1.class);

        log.debug("Sample3Bean1: {}", bean);
        log.debug("Sample3Bean1.getSample3Bean2(): {}", bean.getSample3Bean2());
        log.debug("Sample3Aop.getBean1(): {}", ioc.getBean(Sample3Aop.class).getBean1());
        log.debug("Sample3Aop.getBean2().hello(\"world\"): {}", ioc.getBean(Sample3Aop.class).getBean2().hello("world"));
    }
}
