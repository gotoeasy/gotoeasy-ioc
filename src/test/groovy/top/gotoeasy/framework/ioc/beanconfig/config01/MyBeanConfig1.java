package top.gotoeasy.framework.ioc.beanconfig.config01;

import java.util.HashMap;
import java.util.Map;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;

@BeanConfig
public class MyBeanConfig1 {

    protected Object getMyObject() {
        // 非public方法不起作用
        return new Object();
    }

    public void getMyObject2() {
        // 无返回值不起作用
    }

    public Object getMyObject3() {
        // 非@Bean方法不起作用
        return new Object();
    }

    @Bean("myMap2")
    public Map<String, Object> myMap(MyMap<String, Object> myMap) {
        return myMap;
    }

    @Bean("aaa")
    public Map<String, Object> myMap2(@Autowired("bbb") Map<String, Object> map) {
        return map;
    }

    @Bean("bbb")
    public Map<String, Object> myMap3() {
        Map<String, Object> map = new HashMap<>();
        map.put("cnt", 1);

        return map;
    }

    @Bean("ccc")
    public Map<String, Object> myMap4() {
        Map<String, Object> map = new HashMap<>();
        map.put("cnt", 1);

        return map;
    }

}
