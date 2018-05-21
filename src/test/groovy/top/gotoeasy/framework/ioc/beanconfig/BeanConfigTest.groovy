package top.gotoeasy.framework.ioc.beanconfig

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification
import top.gotoeasy.framework.core.config.DefaultConfig
import top.gotoeasy.framework.ioc.Ioc
import top.gotoeasy.framework.ioc.exception.IocException
import top.gotoeasy.framework.ioc.impl.DefaultIoc


class BeanConfigTest extends Specification {

    @Test
    public void "1 编码配置Bean，懒装载，查看log可见ccc没有初始化"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config1");

        Ioc ioc = new DefaultIoc();
        Map<String, Object> map = ioc.getBean("myMap")
        map.get("cnt") == 1

        Map<String, Object>  map2 = ioc.getBean("aaa")
        map2.get("cnt") == 1

        Map<String, Object>  map3 = ioc.getBean("bbb")
        map3.get("cnt") == 1
    }

    @Test
    public void "2 重复的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config2");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    public void "3 重复的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config3");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }
}
