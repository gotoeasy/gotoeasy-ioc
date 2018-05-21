package top.gotoeasy.framework.ioc.beanconfig.config2;

import java.util.HashMap;
import java.util.Map;

import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;

@BeanConfig
public class MyBeanConfig2 {

    @Bean
    public Map<String, Object> myMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("cnt", 0);

        return map;
    }

    @Bean("myMap")
    public Map<String, Object> myMap2() {
        Map<String, Object> map = new HashMap<>();
        map.put("cnt", 1);

        return map;
    }

}
