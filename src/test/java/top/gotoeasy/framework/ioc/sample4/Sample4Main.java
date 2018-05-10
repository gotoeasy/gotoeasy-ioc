package top.gotoeasy.framework.ioc.sample4;

import top.gotoeasy.framework.core.config.DefaultConfig;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.ioc.impl.DefaultIoc;

public class Sample4Main {

    private static final Log log = LoggerFactory.getLogger(Sample4Main.class);

    public static void main(String[] args) {

        // 构造注入、字段注入、方法注入、AOP
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample4");

        DefaultIoc ioc = new DefaultIoc();
        Sample4Bean1 bean = ioc.getBean(Sample4Bean1.class);

        log.debug("Sample4Bean1: {}", bean);
        log.debug("Sample4Bean1.getBean(): {}", bean.getBean());
        log.debug("Sample4Bean1.getSample4Bean2(): {}", bean.getSample4Bean2());
        log.debug("Sample4Bean1.getSample4Bean2().getBean(): {}", bean.getSample4Bean2().getBean());
        log.debug("Sample4Bean1.getSample4Bean2().hello(\"world\"): {}", bean.getSample4Bean2().hello("world"));
    }
}
