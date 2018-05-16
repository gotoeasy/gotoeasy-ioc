package top.gotoeasy.framework.ioc.util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import top.gotoeasy.framework.core.converter.ConvertUtil;
import top.gotoeasy.framework.core.util.CmnClass;
import top.gotoeasy.framework.ioc.exception.IocException;
import top.gotoeasy.framework.ioc.xml.Beans;
import top.gotoeasy.framework.ioc.xml.Beans.Bean;

public class CmnXml {

    private static final Map<String, Class<?>> map = new HashMap<>();

    public static void main(String[] args) {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(Beans.class);

            Unmarshaller shaller = context.createUnmarshaller();

            shaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File("e:/000/gotoeasy-beans.xsd")));

            JAXBElement<Beans> root = shaller.unmarshal(new StreamSource(new File("e:/000/NewFile.xml")), Beans.class);
            Beans xmlBeans = root.getValue();
            System.err.println(xmlBeans.getBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Bean> getXmlBeanDefines() {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(Beans.class);

            Unmarshaller shaller = context.createUnmarshaller();

            shaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File("e:/000/gotoeasy-beans.xsd")));

            JAXBElement<Beans> root = shaller.unmarshal(new StreamSource(new File("e:/000/NewFile.xml")), Beans.class);
            Beans xmlBeans = root.getValue();

            Map<String, Bean> map = new HashMap<>();
            xmlBeans.getBean().forEach(bean -> map.put(bean.getId(), bean));
            return map;
        } catch (Exception e) {
            throw new IocException("Bean定义的xml配置文件读取失败", e);
        }
    }

    public static Class<?> getBeanClass(String clazz) {
        return map.containsKey(clazz) ? map.get(clazz) : CmnClass.loadClass(clazz);
    }

    public static Object getBeanValue(String clazz, String value) {
        return ConvertUtil.convert(value, getBeanClass(clazz));
    }

    static {
        map.put("int", int.class);
        map.put("long", long.class);
        map.put("short", short.class);
        map.put("float", float.class);
        map.put("double", double.class);
        map.put("boolean", boolean.class);
        map.put("char", char.class);
        map.put("Integer", Integer.class);
        map.put("Long", Long.class);
        map.put("Short", Short.class);
        map.put("Float", Float.class);
        map.put("Double", Double.class);
        map.put("Boolean", Boolean.class);
        map.put("Character", Character.class);
        map.put("String", String.class);
    }
}
