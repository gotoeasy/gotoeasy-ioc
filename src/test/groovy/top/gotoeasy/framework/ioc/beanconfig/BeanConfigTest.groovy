package top.gotoeasy.framework.ioc.beanconfig

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification
import top.gotoeasy.framework.core.config.DefaultConfig
import top.gotoeasy.framework.ioc.Ioc
import top.gotoeasy.framework.ioc.beanconfig.config4.Student4
import top.gotoeasy.framework.ioc.exception.IocException
import top.gotoeasy.framework.ioc.impl.DefaultIoc


class BeanConfigTest extends Specification {

    @Test
    public void "1 编码配置Bean，懒装载，查看log可见ccc没有初始化"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config1");

        Ioc ioc = new DefaultIoc();
        Map<String, Object> map = ioc.getBean("myMap")
        map.get("cnt") == null

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

    @Test
    public void "4 单纯XML配置文件配置Bean"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config4");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config4/beans.xml");
        Ioc ioc = new DefaultIoc();

        Student4 student = ioc.getBean("student")
        def age = ioc.getBean("age")
        Student4 zhansan = ioc.getBean("zhangsan")
        Student4 lisi = ioc.getBean("lisi")
        Student4 wangwu = ioc.getBean("wangwu")
        Student4 xiaohong = ioc.getBean("xiaohong")

        student.getName() == "匿名"
        age == 20
        zhansan.getName() == "匿名"
        lisi.getPhone() == "13812345678"
        wangwu.getName() == "王五"
        wangwu.getAge() == 20
    }

    @Test
    public void "5 重复的扫描注解id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config5");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }
}
