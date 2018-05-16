//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2018.05
//

package top.gotoeasy.framework.ioc.xml;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * anonymous complex type的 Java 类。
 * <p>
 * 以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="bean">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="constructor" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="arg" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                     &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="property" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"bean"})
@XmlRootElement(name = "beans")
public class Beans {

    protected List<Beans.Bean> bean;

    /**
     * Gets the value of the bean property.
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bean property.
     * <p>
     * For example, to add a new item, do as follows:
     * 
     * <pre>
     * getBean().add(newItem);
     * </pre>
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Beans.Bean }
     */
    public List<Beans.Bean> getBean() {
        if ( bean == null ) {
            bean = new ArrayList<>();
        }
        return this.bean;
    }

    /**
     * <p>
     * anonymous complex type的 Java 类。
     * <p>
     * 以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="constructor" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="arg" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                           &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="property" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="id" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {"constructor", "property"})
    public static class Bean {

        protected Beans.Bean.Constructor    constructor;
        protected List<Beans.Bean.Property> property;
        @XmlAttribute(name = "id", required = true)
        protected String                    id;
        @XmlAttribute(name = "class")
        protected String                    clazz;
        @XmlAttribute(name = "value")
        protected String                    value;
        @XmlAttribute(name = "ref")
        protected String                    ref;

        /**
         * 获取constructor属性的值。
         * 
         * @return
         *         possible object is
         *         {@link Beans.Bean.Constructor }
         */
        public Beans.Bean.Constructor getConstructor() {
            return constructor;
        }

        /**
         * 设置constructor属性的值。
         * 
         * @param value
         *            allowed object is
         *            {@link Beans.Bean.Constructor }
         */
        public void setConstructor(Beans.Bean.Constructor value) {
            this.constructor = value;
        }

        /**
         * Gets the value of the property property.
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the property property.
         * <p>
         * For example, to add a new item, do as follows:
         * 
         * <pre>
         * getProperty().add(newItem);
         * </pre>
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Beans.Bean.Property }
         */
        public List<Beans.Bean.Property> getProperty() {
            if ( property == null ) {
                property = new ArrayList<>();
            }
            return this.property;
        }

        /**
         * 获取id属性的值。
         * 
         * @return
         *         possible object is
         *         {@link String }
         */
        public String getId() {
            return id;
        }

        /**
         * 设置id属性的值。
         * 
         * @param value
         *            allowed object is
         *            {@link String }
         */
        public void setId(String value) {
            this.id = value;
        }

        /**
         * 获取clazz属性的值。
         * 
         * @return
         *         possible object is
         *         {@link String }
         */
        public String getClazz() {
            return clazz;
        }

        /**
         * 设置clazz属性的值。
         * 
         * @param value
         *            allowed object is
         *            {@link String }
         */
        public void setClazz(String value) {
            this.clazz = value;
        }

        /**
         * 获取value属性的值。
         * 
         * @return
         *         possible object is
         *         {@link String }
         */
        public String getValue() {
            return value;
        }

        /**
         * 设置value属性的值。
         * 
         * @param value
         *            allowed object is
         *            {@link String }
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * 获取ref属性的值。
         * 
         * @return
         *         possible object is
         *         {@link String }
         */
        public String getRef() {
            return ref;
        }

        /**
         * 设置ref属性的值。
         * 
         * @param value
         *            allowed object is
         *            {@link String }
         */
        public void setRef(String value) {
            this.ref = value;
        }

        /**
         * <p>
         * anonymous complex type的 Java 类。
         * <p>
         * 以下模式片段指定包含在此类中的预期内容。
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="arg" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
         *                 &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"arg"})
        public static class Constructor {

            protected List<Beans.Bean.Constructor.Arg> arg;

            /**
             * Gets the value of the arg property.
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the arg property.
             * <p>
             * For example, to add a new item, do as follows:
             * 
             * <pre>
             * getArg().add(newItem);
             * </pre>
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Beans.Bean.Constructor.Arg }
             */
            public List<Beans.Bean.Constructor.Arg> getArg() {
                if ( arg == null ) {
                    arg = new ArrayList<>();
                }
                return this.arg;
            }

            /**
             * <p>
             * anonymous complex type的 Java 类。
             * <p>
             * 以下模式片段指定包含在此类中的预期内容。
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
             *       &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Arg {

                @XmlAttribute(name = "value")
                protected String value;
                @XmlAttribute(name = "class")
                protected String clazz;
                @XmlAttribute(name = "ref")
                protected String ref;

                /**
                 * 获取value属性的值。
                 * 
                 * @return
                 *         possible object is
                 *         {@link String }
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * 设置value属性的值。
                 * 
                 * @param value
                 *            allowed object is
                 *            {@link String }
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * 获取clazz属性的值。
                 * 
                 * @return
                 *         possible object is
                 *         {@link String }
                 */
                public String getClazz() {
                    return clazz;
                }

                /**
                 * 设置clazz属性的值。
                 * 
                 * @param value
                 *            allowed object is
                 *            {@link String }
                 */
                public void setClazz(String value) {
                    this.clazz = value;
                }

                /**
                 * 获取ref属性的值。
                 * 
                 * @return
                 *         possible object is
                 *         {@link String }
                 */
                public String getRef() {
                    return ref;
                }

                /**
                 * 设置ref属性的值。
                 * 
                 * @param value
                 *            allowed object is
                 *            {@link String }
                 */
                public void setRef(String value) {
                    this.ref = value;
                }

            }

        }

        /**
         * <p>
         * anonymous complex type的 Java 类。
         * <p>
         * 以下模式片段指定包含在此类中的预期内容。
         * 
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="class" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
         *       &lt;attribute name="ref" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         */
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

            /**
             * 获取name属性的值。
             * 
             * @return
             *         possible object is
             *         {@link String }
             */
            public String getName() {
                return name;
            }

            /**
             * 设置name属性的值。
             * 
             * @param value
             *            allowed object is
             *            {@link String }
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * 获取clazz属性的值。
             * 
             * @return
             *         possible object is
             *         {@link String }
             */
            public String getClazz() {
                return clazz;
            }

            /**
             * 设置clazz属性的值。
             * 
             * @param value
             *            allowed object is
             *            {@link String }
             */
            public void setClazz(String value) {
                this.clazz = value;
            }

            /**
             * 获取value属性的值。
             * 
             * @return
             *         possible object is
             *         {@link String }
             */
            public String getValue() {
                return value;
            }

            /**
             * 设置value属性的值。
             * 
             * @param value
             *            allowed object is
             *            {@link String }
             */
            public void setValue(String value) {
                this.value = value;
            }

            /**
             * 获取ref属性的值。
             * 
             * @return
             *         possible object is
             *         {@link String }
             */
            public String getRef() {
                return ref;
            }

            /**
             * 设置ref属性的值。
             * 
             * @param value
             *            allowed object is
             *            {@link String }
             */
            public void setRef(String value) {
                this.ref = value;
            }

        }

    }

}
