package top.gotoeasy.framework.ioc.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * XML标签类
 * <p>
 * 本代码经xjc命令生成后整理
 * </p>
 * 
 * @since 2018/5
 * @author 青松
 */
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
@XmlType(name = "", propOrder = {"bean"})
@XmlRootElement(name = "beans")
public class Beans {

    protected List<Beans.XmlBean> xmlBean;

    @XmlElement(name = "bean")
    public List<Beans.XmlBean> getBean() {
        if ( xmlBean == null ) {
            xmlBean = new ArrayList<>();
        }
        return this.xmlBean;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "bean", propOrder = {"constructor", "property"})
    public static class XmlBean {

        protected Beans.XmlBean.Constructor    constructor;
        protected List<Beans.XmlBean.Property> property;
        @XmlAttribute(name = "id", required = true)
        protected String                       id;
        @XmlAttribute(name = "class")
        protected String                       clazz;
        @XmlAttribute(name = "value")
        protected String                       value;
        @XmlAttribute(name = "ref")
        protected String                       ref;

        public Beans.XmlBean.Constructor getConstructor() {
            return constructor;
        }

        public void setConstructor(Beans.XmlBean.Constructor value) {
            this.constructor = value;
        }

        public List<Beans.XmlBean.Property> getPropertyList() {
            if ( property == null ) {
                property = new ArrayList<>();
            }
            return this.property;
        }

        public String getId() {
            return id;
        }

        public void setId(String value) {
            this.id = value;
        }

        public String getClazz() {
            return clazz;
        }

        public void setClazz(String value) {
            this.clazz = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getRef() {
            return ref;
        }

        public void setRef(String value) {
            this.ref = value;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "constructor", propOrder = {"arg"})
        public static class Constructor {

            protected List<Beans.XmlBean.Constructor.Arg> arg;

            public List<Beans.XmlBean.Constructor.Arg> getArgList() {
                if ( arg == null ) {
                    arg = new ArrayList<>();
                }
                return this.arg;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Arg {

                @XmlAttribute(name = "value")
                protected String value;
                @XmlAttribute(name = "class")
                protected String clazz;
                @XmlAttribute(name = "ref")
                protected String ref;

                public String getValue() {
                    return value;
                }

                public void setValue(String value) {
                    this.value = value;
                }

                public String getClazz() {
                    return clazz;
                }

                public void setClazz(String value) {
                    this.clazz = value;
                }

                public String getRef() {
                    return ref;
                }

                public void setRef(String value) {
                    this.ref = value;
                }

            }

        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "")
        public static class Property {

            @XmlAttribute(name = "name", required = true)
            protected String name;
            @XmlAttribute(name = "class")
            protected String clazz;
            @XmlAttribute(name = "value")
            protected String value;
            @XmlAttribute(name = "ref")
            protected String ref;

            public String getName() {
                return name;
            }

            public void setName(String value) {
                this.name = value;
            }

            public String getClazz() {
                return clazz;
            }

            public void setClazz(String value) {
                this.clazz = value;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }

            public String getRef() {
                return ref;
            }

            public void setRef(String value) {
                this.ref = value;
            }

        }

    }

}
