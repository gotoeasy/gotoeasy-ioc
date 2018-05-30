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
import top.gotoeasy.framework.ioc.beanconfig.config22.Student2201
import top.gotoeasy.framework.ioc.beanconfig.config23.Student23
import top.gotoeasy.framework.ioc.beanconfig.config24.Student24
import top.gotoeasy.framework.ioc.beanconfig.config25.Student25
import top.gotoeasy.framework.ioc.beanconfig.config26.Bean2601
import top.gotoeasy.framework.ioc.beanconfig.config26.Bean2602
import top.gotoeasy.framework.ioc.beanconfig.config26.Bean2603
import top.gotoeasy.framework.ioc.beanconfig.config99.Book99
import top.gotoeasy.framework.ioc.beanconfig.config99.Car99
import top.gotoeasy.framework.ioc.beanconfig.config99.School99
import top.gotoeasy.framework.ioc.beanconfig.config99.Student99
import top.gotoeasy.framework.ioc.exception.IocException
import top.gotoeasy.framework.ioc.impl.DefaultIoc
import top.gotoeasy.framework.ioc.util.CmnAnno
import top.gotoeasy.framework.ioc.util.CmnIoc
import top.gotoeasy.framework.ioc.util.CmnXml
import top.gotoeasy.framework.ioc.xml.Beans
import top.gotoeasy.framework.ioc.xml.ObjectFactory
import top.gotoeasy.framework.ioc.xml.Beans.XmlBean


class IocTest extends Specification {


    @Test
    def void "99 组合配置的正常例子测试"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config99");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config99/beans.xml");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        Ioc ioc = new DefaultIoc();
        Book99 history = ioc.getBean("history")
        Book99 english = ioc.getBean("english")
        Student99 xiaoming = ioc.getBean("xiaoming")

        history.getName() == "历史"
        english.getName() == "英语"
        xiaoming.getName() == "小明"
        xiaoming.getAge() == 23
        xiaoming.getTeacher().getName() == "张老师"

        Car99 car99 = ioc.getBean(Car99.class)
        car99.run()
        School99 school99 = ioc.getBean(School99.class)
        school99.getTeacherZhang().getName() == "张老师"

        Student99 jacky = ioc.getBean("jacky")
        Student99 zhangsan = ioc.getBean("zhangsan")
        jacky.getName() == "Jacky"
        jacky.getAge() == 26
        zhangsan.getName() == "张某人"
        zhangsan.getAge() == 26
        zhangsan.getTeacher() != null
    }

    @Test
    def void "1 编码配置Bean，懒装载，查看log可见ccc没有初始化"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config01");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

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
    def void "2 重复的编码配置id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config02");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    def void "3 重复的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config03");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }

    @Test
    def void "4 单纯XML配置文件配置Bean"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config04");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config04/beans.xml");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

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
    def void "5 重复的扫描注解id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config05");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    def void "6 重复的XML配置bean id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config06");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config06/beans.xml");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    def void "7 编码配置Bean初始化失败"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config07");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        ioc.getBean("myBook")
        then:
        thrown(IocException)
    }


    @Test
    def void "8 xml的id和扫描id重复"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config08");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config08/beans.xml");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }

    @Test
    def void "9-1 xml的bean引用不存在的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config09");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config09/beans.xml");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    def void "9-2 xml的bean构造方法引用不存在的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config09");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config09/beans2.xml");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    def void "9-3 xml的属性注入引用不存在的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config09");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config09/beans3.xml");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }

    @Test
    def void "9-4 XML的Bean配置找不到相应的构造方法"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config09");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config09/beans4.xml");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        ioc.getBean("lisi0904")
        then:
        thrown(IocException)
    }


    @Test
    def void "10 编码bean的方法参数引用不存在的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config10");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }

    @Test
    def void "11 扫描bean的各种错误配置检查"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config11");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    def void "12 扫描bean不支持多个构造方法同时注入"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config12");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }


    @Test
    def void "13 aop注入"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config13");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

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
    def void "14 扫描bean字段注入不存在的id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config14");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }

    @Test
    def void "15 扫描bean方法注入失败"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config15");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        ioc.getBean("student15")
        then:
        thrown(IocException)
    }

    @Test
    def void "16 aop字段注入失败"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config16");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        Ioc ioc = new DefaultIoc();
        ioc.getBean("aop16Before", Aop16Before.class)
        then:
        thrown(IocException)
    }

    @Test
    def void "17 扫描bean构造方法注入"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config17");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        Ioc ioc = new DefaultIoc();
        Student17 student = ioc.getBean("student17")

        student.getName() == "张三"
        student.getAge() == 25
    }

    @Test
    def void "18 DefaultIoc put id重复"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config18");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        DefaultIoc ioc = new DefaultIoc();
        ioc.put("aaa", 0)
        ioc.put("aaa", 1)
        then:
        thrown(IocException)
    }

    @Test
    def void "19 Bean定义的xml配置文件读取失败"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config18");
        DefaultConfig.getInstance().set("ioc.config.file", "beans.xml");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        DefaultIoc ioc = new DefaultIoc();
        then:
        thrown(IocException)
    }

    @Test
    def void "20 静态方法类调用私有构造方法，仅为满足覆盖率"() {

        expect:
        Constructor<?> constructor1 = CmnXml.class.getDeclaredConstructor()
        constructor1.setAccessible(true)
        Constructor<?> constructor2 = CmnIoc.class.getDeclaredConstructor()
        constructor2.setAccessible(true)
        Constructor<?> constructor3 = CmnAnno.class.getDeclaredConstructor()
        constructor3.setAccessible(true)

        when:
        constructor1.newInstance()
        then:
        notThrown(Exception)

        when:
        constructor2.newInstance()
        then:
        notThrown(Exception)

        when:
        constructor3.newInstance()
        then:
        notThrown(Exception)
    }

    @Test
    def void "21 ObjectFactory，工具生成后未使用的类，仅单纯跑下满足覆盖率"() {

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

    @Test
    def void "22 扫描bean循环依赖"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config22");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        DefaultIoc ioc = new DefaultIoc();
        ioc.getBean(Student2201.class)
        then:
        thrown(IocException)
    }


    @Test
    def void "23 启动完全加载模式"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config23");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().set("ioc.lazyload", "false"); // 启动完全加载

        when:
        DefaultIoc ioc = new DefaultIoc();
        ioc.getBean(Student23.class)
        then:
        noExceptionThrown()
    }


    @Test
    def void "24 xml构造方法引用xml配置的简单值对象，可以不写class"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config24");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config24/beans.xml");
        DefaultConfig.getInstance().set("ioc.lazyload", "false"); // 启动完全加载

        DefaultIoc ioc = new DefaultIoc();
        Student24 student = ioc.getBean(Student24.class)
        student.getAge() == 25
    }

    @Test
    def void "25 无法创建未定义的Bean id"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config25");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        when:
        DefaultIoc ioc = new DefaultIoc();
        Student25 student = ioc.getBean(Student25.class)
        then:
        thrown(IocException)
    }


    @Test
    def void "26 相互依赖但不是循环依赖，要跑起来"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config26");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config26/beans.xml");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        DefaultIoc ioc = new DefaultIoc();
        Bean2601 bean2601 = ioc.getBean(Bean2601.class)
        Bean2602 bean2602 = ioc.getBean(Bean2602.class)
        Bean2601 xmlBean2601 = ioc.getBean("xmlBean2601")
        Bean2602 xmlBean2602 = ioc.getBean("xmlBean2602")

        bean2601.getBean() != null
        bean2601.getBean().getCnt() == 100
        bean2601.getBean2() != null
        bean2601.getBean2().getCnt() == 100
        bean2601.getCnt() == 100

        bean2602.getBean() != null
        bean2602.getBean().getCnt() == 100
        bean2602.getBean1() != null
        bean2602.getBean1().getCnt() == 100
        bean2602.getCnt() == 100

        xmlBean2601.getBean() != null
        xmlBean2601.getBean().getCnt() == 100
        xmlBean2601.getBean2() == null
        xmlBean2601.getCnt() == 100

        xmlBean2602.getBean() != null
        xmlBean2602.getBean().getCnt() == 100
        xmlBean2602.getBean1() != null
        xmlBean2602.getBean1().getCnt() == 100
        xmlBean2602.getCnt() == 100

        Bean2603 bean2603 = ioc.getBean("xmlBean2603")
        bean2603.getBean2604().getBean2605() != null

    }


    @Test
    def void "26-2 相互依赖但不是循环依赖，要跑起来-启动完全加载方式"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config26");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/beanconfig/config26/beans.xml");
        DefaultConfig.getInstance().set("ioc.lazyload", "0"); // 启动完全加载方式

        DefaultIoc ioc = new DefaultIoc();
        Bean2601 bean2601 = ioc.getBean(Bean2601.class)
        Bean2602 bean2602 = ioc.getBean(Bean2602.class)
        Bean2601 xmlBean2601 = ioc.getBean("xmlBean2601")
        Bean2602 xmlBean2602 = ioc.getBean("xmlBean2602")

        bean2601.getBean() != null
        bean2601.getBean().getCnt() == 100
        bean2601.getBean2() != null
        bean2601.getBean2().getCnt() == 100
        bean2601.getCnt() == 100

        bean2602.getBean() != null
        bean2602.getBean().getCnt() == 100
        bean2602.getBean1() != null
        bean2602.getBean1().getCnt() == 100
        bean2602.getCnt() == 100

        xmlBean2601.getBean() != null
        xmlBean2601.getBean().getCnt() == 100
        xmlBean2601.getBean2() == null
        xmlBean2601.getCnt() == 100

        xmlBean2602.getBean() != null
        xmlBean2602.getBean().getCnt() == 100
        xmlBean2602.getBean1() != null
        xmlBean2602.getBean1().getCnt() == 100
        xmlBean2602.getCnt() == 100

        Bean2603 bean2603 = ioc.getBean("xmlBean2603")
        bean2603.getBean2604().getBean2605() != null

    }

    @Test
    def void "27 自定义注解有@Component也是OK的"() {

        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.beanconfig.config27");
        DefaultConfig.getInstance().remove("ioc.config.file");
        DefaultConfig.getInstance().remove("ioc.lazyload"); // 默认懒加载

        DefaultIoc ioc = new DefaultIoc();
        def bean2701 = ioc.getBean("bean2701")
        def bean2702 = ioc.getBean("aaa")
        def bean2703 = ioc.getBean("bean2703")
        bean2701 != null
        bean2702 != null
        bean2703 != null
    }
}
