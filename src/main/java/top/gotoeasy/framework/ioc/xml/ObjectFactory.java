//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.05
//

package top.gotoeasy.framework.ioc.xml;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * XML标签对象工厂类
 * <p>
 * 本代码用xjc命令生成
 * </p>
 * 
 * @since 2018/5
 * @author 青松
 */
@XmlRegistry
public class ObjectFactory {

    public Beans createBeans() {
        return new Beans();
    }

    public Beans.Bean createBeansBean() {
        return new Beans.Bean();
    }

    public Beans.Bean.Constructor createBeansBeanConstructor() {
        return new Beans.Bean.Constructor();
    }

    public Beans.Bean.Property createBeansBeanProperty() {
        return new Beans.Bean.Property();
    }

    public Beans.Bean.Constructor.Arg createBeansBeanConstructorArg() {
        return new Beans.Bean.Constructor.Arg();
    }

}
