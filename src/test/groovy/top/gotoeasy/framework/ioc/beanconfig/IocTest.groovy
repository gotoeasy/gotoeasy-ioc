package top.gotoeasy.framework.ioc.beanconfig

import static org.junit.Assert.*

import java.lang.reflect.Constructor

import org.junit.Test

import spock.lang.Specification
import top.gotoeasy.framework.core.config.DefaultConfig
import top.gotoeasy.framework.ioc.Ioc
import top.gotoeasy.framework.ioc.beanconfig.config04.Student4
import top.gotoeasy.framework.ioc.beanconfig.config13.Aop13Before
import top.gotoeasy.framework.ioc.beanconfig.config13.Student13
import top.gotoeasy.framework.ioc.beanconfig.config16.Aop16Before
import top.gotoeasy.framework.ioc.beanconfig.config17.Student17
import top.gotoeasy.framework.ioc.exception.IocException
import top.gotoeasy.framework.ioc.impl.DefaultIoc
import top.gotoeasy.framework.ioc.util.CmnIoc
import top.gotoeasy.framework.ioc.util.CmnXml
import top.gotoeasy.framework.ioc.xml.Beans
import top.gotoeasy.framework.ioc.xml.ObjectFactory
import top.gotoeasy.framework.ioc.xml.Beans.XmlBean


class IocTest extends Specification {

    @Test
    public void "1 编码配置Bean，懒装载，查看log可见ccc没有初始化"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config01");
        DefaultConfig.getInstance().remove("ioc.config.file");

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
        DefaultConfig.getInstance().remove("ioc.config.file");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    public void "3 重复的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config03");
        DefaultConfig.getInstance().remove("ioc.config.file");

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
        DefaultConfig.getInstance().remove("ioc.config.file");

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
        DefaultConfig.getInstance().remove("ioc.config.file");

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

    @Test
    public void "9-1 xml的bean引用不存在的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config09");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config09/beans.xml");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    public void "9-2 xml的bean构造方法引用不存在的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config09");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config09/beans2.xml");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    public void "9-3 xml的属性注入引用不存在的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config09");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config09/beans3.xml");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }

    @Test
    public void "9-4 XML的Bean配置找不到相应的构造方法"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config09");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config09/beans4.xml");

        when:
        Ioc ioc = new DefaultIoc();
        ioc.getBean("lisi0904")
        then:
        thrown(IocException)
    }


    @Test
    public void "10 编码bean的方法参数引用不存在的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config10");
        DefaultConfig.getInstance().remove("ioc.config.file");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }

    @Test
    public void "11 扫描bean的各种错误配置检查"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config11");
        DefaultConfig.getInstance().remove("ioc.config.file");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    public void "12 扫描bean不支持多个构造方法同时注入"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config12");
        DefaultConfig.getInstance().remove("ioc.config.file");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    public void "13 aop注入"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config13");
        DefaultConfig.getInstance().remove("ioc.config.file");

        Ioc ioc = new DefaultIoc();

        Student13 tom = ioc.getBean("student13" , Student13.class)
        Aop13Before aop = ioc.getBean(Aop13Before.class)

        tom.getName() == "tom"
        aop.getStudent1301() != null;
        aop.getStudent1302() != null;

        CmnIoc.getBean(Aop13Before.class).getStudent1301() != null;
        CmnIoc.getBean("student13") != null
        CmnIoc.getBean("student13" , Student13.class).getName() == "tom"
    }


    @Test
    public void "14 扫描bean字段注入不存在的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config14");
        DefaultConfig.getInstance().remove("ioc.config.file");

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }

    @Test
    public void "15 扫描bean方法注入失败"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config15");
        DefaultConfig.getInstance().remove("ioc.config.file");

        when:
        Ioc ioc = new DefaultIoc();
        ioc.getBean("student15")
        then:
        thrown(IocException)
    }

    @Test
    public void "16 aop字段注入失败"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config16");
        DefaultConfig.getInstance().remove("ioc.config.file");

        when:
        Ioc ioc = new DefaultIoc();
        ioc.getBean("aop16Before", Aop16Before.class)
        then:
        thrown(IocException)
    }

    @Test
    public void "17 扫描bean构造方法注入"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config17");
        DefaultConfig.getInstance().remove("ioc.config.file");

        Ioc ioc = new DefaultIoc();
        Student17 student = ioc.getBean("student17")

        student.getName() == "张三"
        student.getAge() == 25
    }

    @Test
    public void "18 DefaultIoc put id重复"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config18");
        DefaultConfig.getInstance().remove("ioc.config.file");

        when:
        DefaultIoc ioc = new DefaultIoc();
        ioc.put("aaa", 0)
        ioc.put("aaa", 1)
        then:
        thrown(IocException)
    }

    @Test
    public void "19 Bean定义的xml配置文件读取失败"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config18");
        DefaultConfig.getInstance().set("ioc.config.file", "beans.xml");

        when:
        DefaultIoc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }

    @Test
    public void "20 静态方法类调用私有构造方法，仅为满足覆盖率"() {

        expect:
        Constructor<?> constructor1 = CmnXml.class.getDeclaredConstructor()
        constructor1.setAccessible(true)
        Constructor<?> constructor2 = CmnIoc.class.getDeclaredConstructor()
        constructor2.setAccessible(true)

        when:
        constructor1.newInstance()
        then:
        notThrown(Exception)

        when:
        constructor2.newInstance()
        then:
        notThrown(Exception)
    }

    @Test
    public void "21 ObjectFactory，工具生成后未使用的类，仅单纯跑下满足覆盖率"() {

        expect:
        ObjectFactory fac = new ObjectFactory();
        Beans beans = fac.createBeans()
        XmlBean bean = fac.createBeansBean()
        XmlBean.Constructor xmlConstructor = fac.createBeansBeanConstructor()
        XmlBean.Constructor.Arg arg = fac.createBeansBeanConstructorArg()
        arg.setClazz("String")
        arg.setValue("tom")
        arg.setRef(null)
        xmlConstructor.getArgList().add(arg);
        XmlBean.Property xmlPropertyName = fac.createBeansBeanProperty()
        xmlPropertyName.setName("name")
        xmlPropertyName.setClazz("String")
        xmlPropertyName.setValue("jacky")
        XmlBean.Property xmlPropertyAge = fac.createBeansBeanProperty()
        xmlPropertyAge.setClazz("int")
        xmlPropertyAge.setRef("age")

        bean.setConstructor(xmlConstructor)
        bean.getPropertyList().add(xmlPropertyName)
        bean.getPropertyList().add(xmlPropertyAge)
        bean.setId("id")
        bean.setClazz("")
        bean.setRef("")
        bean.setValue("")
    }
}