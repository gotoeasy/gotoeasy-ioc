package top.gotoeasy.framework.ioc.util;

import java.io.File;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import top.gotoeasy.framework.ioc.exception.IocException;
import top.gotoeasy.framework.ioc.xml.Beans;
import top.gotoeasy.framework.ioc.xml.Beans.Bean;

public class CmnXml {

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

    public static List<Bean> getXmlBeanDefines() {
        JAXBContext context;
        try {
            context = JAXBContext.newInstance(Beans.class);

            Unmarshaller shaller = context.createUnmarshaller();

            shaller.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new File("e:/000/gotoeasy-beans.xsd")));

            JAXBElement<Beans> root = shaller.unmarshal(new StreamSource(new File("e:/000/NewFile.xml")), Beans.class);
            Beans xmlBeans = root.getValue();
            return xmlBeans.getBean();
        } catch (Exception e) {
            throw new IocException("Bean定义的xml配置文件读取失败", e);
        }
    }
}
