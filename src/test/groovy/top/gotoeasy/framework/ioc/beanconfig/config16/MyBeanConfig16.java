package top.gotoeasy.framework.ioc.beanconfig.config16;

import java.util.HashMap;

import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;

@BeanConfig
public class MyBeanConfig16 {

    @Bean("aaa")
    public Object aaa() {
        return new HashMap<>();
    }

}
