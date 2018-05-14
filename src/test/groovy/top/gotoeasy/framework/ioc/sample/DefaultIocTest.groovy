package top.gotoeasy.framework.ioc.sample

import static org.junit.Assert.*

import java.lang.reflect.Constructor

import org.junit.Test

import spock.lang.Specification
import top.gotoeasy.framework.core.config.DefaultConfig
import top.gotoeasy.framework.ioc.impl.DefaultIoc
import top.gotoeasy.framework.ioc.sample1.Sample1Bean1
import top.gotoeasy.framework.ioc.sample2.Sample2Aop
import top.gotoeasy.framework.ioc.sample2.Sample2Bean1
import top.gotoeasy.framework.ioc.sample3.Sample3Aop
import top.gotoeasy.framework.ioc.sample3.Sample3Bean1
import top.gotoeasy.framework.ioc.sample4.Sample4Bean1
import top.gotoeasy.framework.ioc.util.CmnIoc

class DefaultIocTest extends Specification {

    @Test
    public void "1无参数构造,单纯字段注入,无AOP"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample1");
        DefaultIoc ioc = new DefaultIoc();
        Sample1Bean1 bean = ioc.getBean(Sample1Bean1.class);

        bean.getClass() == Sample1Bean1.class
        bean.getSample1Bean2() != null
    }

    @Test
    public void "2无参数构造,单纯字段注入,有AOP"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample2");
        DefaultIoc ioc = new DefaultIoc();
        Sample2Bean1 bean = ioc.getBean(Sample2Bean1.class);

        bean.getSample2Bean2() != null
        ioc.getBean(Sample2Aop.class).getBean().hello("world") == "Hello world"
    }

    @Test
    public void "3无参数构造,字段注入+方法注入,有AOP"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample3");
        DefaultIoc ioc = new DefaultIoc();
        Sample3Bean1 bean = ioc.getBean(Sample3Bean1.class);

        bean.getSample3Bean2() != null
        ioc.getBean(Sample3Aop.class).getBean1() != null
        ioc.getBean(Sample3Aop.class).getBean2().hello("world") == "Hello world"
    }

    @Test
    public void "4构造注入、字段注入、方法注入、AOP"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample4");
        DefaultIoc ioc = new DefaultIoc();
        Sample4Bean1 bean = ioc.getBean(Sample4Bean1.class);

        bean.getBean() != null
        bean.getSample4Bean2() != null
        bean.getSample4Bean2().getBean() != null
        bean.getSample4Bean2().hello("world") == "Hello world"
    }


    @Test
    public void "91循环依赖异常"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample.config.sample91");
        when:
        DefaultIoc ioc = new DefaultIoc();
        then:
        def ex = thrown(Exception)
        ex != null
    }

    @Test
    public void "92Bean名重复"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample.config.sample92");
        when:
        DefaultIoc ioc = new DefaultIoc();
        then:
        def ex = thrown(Exception)
        ex != null
    }

    @Test
    public void "93构造方法重复注入"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample.config.sample93");
        when:
        DefaultIoc ioc = new DefaultIoc();
        then:
        def ex = thrown(Exception)
        ex != null
    }

    @Test
    public void "94按默认构造方法创建失败"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample.config.sample94");
        when:
        DefaultIoc ioc = new DefaultIoc();
        then:
        def ex = thrown(Exception)
        ex != null
    }

    @Test
    public void "95字段注入，找不到指定名称的Bean对象"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample.config.sample95");
        when:
        DefaultIoc ioc = new DefaultIoc();
        then:
        def ex = thrown(Exception)
        ex != null
    }

    @Test
    public void "96方法注入，找不到指定名称的Bean对象"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample.config.sample96");
        when:
        DefaultIoc ioc = new DefaultIoc();
        then:
        def ex = thrown(Exception)
        ex != null
    }

    @Test
    public void "97方法注入失败"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample.config.sample97");
        when:
        DefaultIoc ioc = new DefaultIoc();
        then:
        def ex = thrown(Exception)
        ex != null
    }

    @Test
    public void "98字段注入失败"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample.config.sample98");
        when:
        DefaultIoc ioc = new DefaultIoc();
        then:
        def ex = thrown(Exception)
        ex != null
    }

    @Test
    public void "99构造方法注入，找不到指定名称的Bean定义"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample.config.sample99");
        when:
        DefaultIoc ioc = new DefaultIoc();
        then:
        def ex = thrown(Exception)
        ex != null
    }

    @Test
    public void "100 CmnIoc测试"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.sample1");

        CmnIoc.getBean("sample1Bean1") != null
        CmnIoc.getBean("sample1Bean1", Sample1Bean1.class) != null
        CmnIoc.getBean(Sample1Bean1.class) != null


        // 私有构造方法
        Constructor<?> constructor = CmnIoc.class.getDeclaredConstructor()
        constructor.setAccessible(true)

        when:
        constructor.newInstance()

        then:
        notThrown(Exception)
    }
}
