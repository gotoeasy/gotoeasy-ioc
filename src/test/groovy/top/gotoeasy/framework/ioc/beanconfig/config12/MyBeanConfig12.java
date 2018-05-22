package top.gotoeasy.framework.ioc.beanconfig.config12;

import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;

@BeanConfig
public class MyBeanConfig12 {

    @Bean("a")
    public Object aaa() {
        return 1;
    }

    @Bean("b")
    public Object bbb() {
        return 1;
    }

    @Bean("c")
    public Object ccc() {
        return 1;
    }
}
