package top.gotoeasy.framework.ioc.beanconfig.config17;

import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;

@BeanConfig
public class MyBeanConfig17 {

    @Bean
    public String name() {
        return "张三";
    }

    @Bean
    public int age() {
        return 25;
    }

    @Bean
    public int object() {
        return 25;
    }
}
