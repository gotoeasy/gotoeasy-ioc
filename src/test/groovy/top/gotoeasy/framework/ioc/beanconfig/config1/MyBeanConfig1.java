package top.gotoeasy.framework.ioc.beanconfig.config1;

import java.util.HashMap;
import java.util.Map;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;
import top.gotoeasy.framework.ioc.annotation.Component;

@BeanConfig
public class MyBeanConfig1 {

    protected Object getMyObject() {
        // 非public方法不起作用
        return new Object();
    }

    public void getMyObject2() {
        // 无返回值不起作用
    }

    public void getMyObject3() {
        //
    }

    @SuppressWarnings("unchecked")
    @Bean("myMap2")
    public Map<String, Object> myMap(MyMap myMap) {
        myMap.put("cnt", 1);
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

    @SuppressWarnings({"serial", "rawtypes"})
    @Component
    public static class MyMap extends HashMap {

    }

}
