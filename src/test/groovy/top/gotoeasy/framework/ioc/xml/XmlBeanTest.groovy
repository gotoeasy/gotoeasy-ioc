package top.gotoeasy.framework.ioc.xml

import static org.junit.Assert.*

import org.junit.Test

import spock.lang.Specification
import top.gotoeasy.framework.core.config.DefaultConfig
import top.gotoeasy.framework.ioc.util.CmnIoc
import top.gotoeasy.framework.ioc.xml.config.Student


class XmlBeanTest  extends Specification {

    @Test
    public void "1单纯XML配置文件配置Bean"() {
        expect:
        DefaultConfig.getInstance().set("ioc.scan", "top.gotoeasy.framework.ioc.xml");
        DefaultConfig.getInstance().set("ioc.config.file", "top/gotoeasy/framework/ioc/xml/config/beans.xml");

        Student student = CmnIoc.getBean("student")
        int age = CmnIoc.getBean("age")
        Student zhangsan = CmnIoc.getBean("zhangsan")
        Student lisi = CmnIoc.getBean("lisi")
        Student wangwu = CmnIoc.getBean("wangwu")
        Student xiaohong = CmnIoc.getBean("xiaohong")

        student.getName() == "匿名"
        age == 20
        zhangsan.getName() == "匿名"
        lisi.getAge() == 20
        lisi.getPhone() == "13812345678"
        wangwu.getAge() == 20
        wangwu.getPhone() == "13611112222"
        wangwu.getName() == "王五"
        xiaohong.getName() == "小红"
        xiaohong.getAge() == 20
    }
}
