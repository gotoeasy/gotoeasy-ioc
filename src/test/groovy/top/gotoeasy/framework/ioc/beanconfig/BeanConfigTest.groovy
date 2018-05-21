package top.gotoeasy.framework.ioc.beanconfig

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification
import top.gotoeasy.framework.core.config.DefaultConfig
import top.gotoeasy.framework.ioc.util.CmnIoc


class BeanConfigTest extends Specification {

    @Test
    public void "编码配置Bean"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig");

        Map<String, Object> map = CmnIoc.getBean("myMap")
        map.get("xxxx") == 0

        Map<String, Object>  map2 = CmnIoc.getBean("aaa")
        map2.get("xxxx") == 1
    }
}
