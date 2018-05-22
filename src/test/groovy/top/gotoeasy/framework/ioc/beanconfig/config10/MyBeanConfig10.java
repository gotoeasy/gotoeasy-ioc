package top.gotoeasy.framework.ioc.beanconfig.config10;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;

@BeanConfig
public class MyBeanConfig10 {

    @Bean
    public Object myMap(@Autowired("bbb") Object obj) {
        return obj;
    }

}
