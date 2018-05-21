package top.gotoeasy.framework.ioc.beanconfig.config5;

import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;

@BeanConfig
public class MyBeanConfig5 {

    @Bean
    public Student5 tom() {

        return new Student5("tom");
    }

}
