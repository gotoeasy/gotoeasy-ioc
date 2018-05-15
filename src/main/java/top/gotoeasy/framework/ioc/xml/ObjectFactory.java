//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
//

package top.gotoeasy.framework.ioc.xml;

import javax.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the top.gotoeasy.framework.ioc.xml package.
 * <p>
 * An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups. Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package:
     * top.gotoeasy.framework.ioc.xml
     */
    public ObjectFactory() {
        //
    }

    /**
     * Create an instance of {@link Beans }
     */
    public Beans createBeans() {
        return new Beans();
    }

    /**
     * Create an instance of {@link Beans.Bean }
     */
    public Beans.Bean createBeansBean() {
        return new Beans.Bean();
    }

    /**
     * Create an instance of {@link Beans.Bean.Constructor }
     */
    public Beans.Bean.Constructor createBeansBeanConstructor() {
        return new Beans.Bean.Constructor();
    }

    /**
     * Create an instance of {@link Beans.Bean.Property }
     */
    public Beans.Bean.Property createBeansBeanProperty() {
        return new Beans.Bean.Property();
    }

    /**
     * Create an instance of {@link Beans.Bean.Constructor.Arg }
     */
    public Beans.Bean.Constructor.Arg createBeansBeanConstructorArg() {
        return new Beans.Bean.Constructor.Arg();
    }

}
