package top.gotoeasy.framework.ioc.beanconfig.config;

import java.util.HashMap;
import java.util.Map;

import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;

@BeanConfig
public class MyBeanConfig {

    @Bean
    public Map<String, Object> myMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("xxxx", 0);

        return map;
    }

    @Bean("aaa")
    public Map<String, Object> myMap2() {
        Map<String, Object> map = new HashMap<>();
        map.put("xxxx", 1);

        return map;
    }

}
