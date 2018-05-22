package top.gotoeasy.framework.ioc.beanconfig.config15;

import java.util.HashMap;

import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;

@BeanConfig
public class MyBeanConfig15 {

    @Bean("a")
    public Object aaa() {
        return new HashMap<>();
    }

}
