package top.gotoeasy.framework.ioc.beanconfig

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification
import top.gotoeasy.framework.core.config.DefaultConfig
import top.gotoeasy.framework.ioc.Ioc
import top.gotoeasy.framework.ioc.beanconfig.config04.Student4
import top.gotoeasy.framework.ioc.exception.IocException
import top.gotoeasy.framework.ioc.impl.DefaultIoc


class BeanConfigTest extends Specification {

    @Test
    public void "1 编码配置Bean，懒装载，查看log可见ccc没有初始化"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config01");

        Ioc ioc = new DefaultIoc();
        Map<String, Object> map = ioc.getBean("myMap")
        map.get("cnt") == null

        ioc.getBean("myMap2") != null

        Map<String, Object>  map2 = ioc.getBean("aaa")
        map2.get("cnt") == 1

        Map<String, Object>  map3 = ioc.getBean("bbb")
        map3.get("cnt") == 1
    }

    @Test
    public void "2 重复的编码配置id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config02");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    public void "3 重复的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config03");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }

    @Test
    public void "4 单纯XML配置文件配置Bean"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config04");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config04/beans.xml");
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
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config05");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    public void "6 重复的XML配置bean id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config06");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config06/beans.xml");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    public void "7 编码配置Bean初始化失败"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config07");

        when:
        Ioc ioc = new DefaultIoc();
        ioc.getBean("myBook")
        then:
        thrown(IocException)
    }


    @Test
    public void "8 xml的id和扫描id重复"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config08");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config08/beans.xml");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }
}
