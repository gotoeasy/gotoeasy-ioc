package top.gotoeasy.framework.ioc.beanconfig.config3;

import java.util.HashMap;
import java.util.Map;

import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;
import top.gotoeasy.framework.ioc.annotation.Component;

@BeanConfig
public class MyBeanConfig3 {

    @Bean
    public Map<String, Object> myMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("cnt", 0);

        return map;
    }

    @Component
    public static class MyMap {

    }

}
