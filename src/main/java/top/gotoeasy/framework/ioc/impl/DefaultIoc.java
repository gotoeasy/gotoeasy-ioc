package top.gotoeasy.framework.ioc.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import top.gotoeasy.framework.aop.Enhance;
import top.gotoeasy.framework.aop.EnhanceBuilder;
import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.core.config.DefaultConfig;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.core.reflect.ScanBuilder;
import top.gotoeasy.framework.core.util.CmnBean;
import top.gotoeasy.framework.core.util.CmnString;
import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;
import top.gotoeasy.framework.ioc.exception.IocException;
import top.gotoeasy.framework.ioc.util.CmnXml;
import top.gotoeasy.framework.ioc.xml.Beans.Bean;
import top.gotoeasy.framework.ioc.xml.Beans.Bean.Constructor.Arg;
import top.gotoeasy.framework.ioc.xml.Beans.Bean.Property;

/**
 * IOC默认实现类
 * 
 * @since 2018/05
 * @author 青松
 */
public class DefaultIoc extends BaseIoc {

    private static final Log        log     = LoggerFactory.getLogger(DefaultIoc.class);

    // @Component注解Bean
    private Map<String, BeanDefine> mapScan = null;
    // XML配置Bean
    private Map<String, Bean>       mapXml  = null;

    // @Aop拦截处理对象
    private List<Object>            aopList = null;

    // Bean创建状态
    private Map<String, Boolean>    mapBool = new HashMap<>();

    /**
     * 构造方法
     */
    public DefaultIoc() {
        init();
    }

    /**
     * 初始化
     */
    private void init() {

        mapScan = getBeanDefineMap();
        mapXml = CmnXml.getXmlBeanDefines();

        mapXml.forEach((id, bean) -> {
            if ( mapScan.containsKey(id) ) {
                log.error("注解和配置的Bean定义名称有重复 {}:{}/{}", id, mapScan.get(id).clas.getCanonicalName(), bean.getClazz());
                throw new IocException("Bean定义名称重复：" + id);
            }
        });

        // 创建AOP对象
        aopList = initAopBeans();

        // 创建Bean对象
        initComponentBeans();

        // AOP对象最后做注入
        for ( Object bean : aopList ) {
            injectObject(bean);
        }

    }

    /**
     * 初始化全部Bean
     */
    private void initComponentBeans() {
        mapScan.forEach((name, beanDefine) -> initScanBean(name));
        mapXml.forEach((name, bean) -> initXmlBean(name));
    }

    /**
     * 初始化指定Bean
     */
    private Object initBean(String name) {
        if ( super.mapIoc.containsKey(name) ) {
            return super.getBean(name);
        }

        if ( mapXml.containsKey(name) ) {
            return initXmlBean(name);
        } else if ( mapScan.containsKey(name) ) {
            return initScanBean(name);
        }

        throw new IocException("指定Bean未定义：" + name);
    }

    /**
     * 初始化一个XML配置Bean
     * 
     * @param name Bean名称
     * @return Bean对象
     */
    private Object initXmlBean(String name) {
        if ( super.mapIoc.containsKey(name) ) {
            return super.getBean(name);
        }

        if ( mapBool.containsKey(name) ) {
            throw new IocException("Bean循环依赖无法初始化:" + name);
        }
        mapBool.put(name, true); // 创建中

        // 取定义
        Bean bean = mapXml.get(name);
        Constructor<?> constructor = null;
        Object[] initargs = null;

        Class<?> superclass = getXmlBeanClass(bean.getClazz(), bean.getRef());
        if ( bean.getConstructor() != null ) {
            List<Arg> args = bean.getConstructor().getArg();
            constructor = getConstructorByArgs(name, superclass, args);
            initargs = getXmlBeanInitargs(args);
        }

        // 创建
        Object obj;
        if ( CmnString.isNotBlank(bean.getValue()) ) {
            obj = CmnXml.getBeanValue(bean.getClazz(), bean.getValue());
        } else {
            obj = EnhanceBuilder.get().setSuperclass(superclass).setConstructorArgs(constructor, initargs).matchAopList(aopList).build();
        }
        super.put(name, obj);
        mapBool.remove(name); // 创建成功

        // 注入
        List<Property> propertys = bean.getProperty();
        propertys.forEach(property -> {
            if ( CmnString.isNotBlank(property.getRef()) ) {
                CmnBean.setPropertyValue(obj, property.getName(), initBean(property.getRef()));
            } else {
                CmnBean.setPropertyValue(obj, property.getName(), CmnXml.getBeanValue(property.getClazz(), property.getValue()));
            }
        });

        return obj;
    }

    private Object[] getXmlBeanInitargs(List<Arg> args) {
        Object[] initargs = new Object[args.size()];
        Arg arg;
        for ( int i = 0; i < initargs.length; i++ ) {
            arg = args.get(i);
            if ( CmnString.isNotBlank(arg.getRef()) ) {
                initargs[i] = initBean(arg.getRef());
            } else {
                initargs[i] = CmnXml.getBeanValue(arg.getClazz(), arg.getValue());
            }
        }

        return initargs;
    }

    private Constructor<?> getConstructorByArgs(String name, Class<?> superclass, List<Arg> args) {
        Class<?>[] parameterTypes = new Class<?>[args.size()];

        Arg arg;
        for ( int i = 0; i < parameterTypes.length; i++ ) {
            arg = args.get(i);
            parameterTypes[i] = getXmlBeanClass(arg.getClazz(), arg.getRef());
        }

        try {
            return superclass.getConstructor(parameterTypes);
        } catch (Exception e) {
            throw new IocException("XML的Bean配置找不到相应的构造方法，Bean id：" + name, e);
        }
    }

    private Class<?> getXmlBeanClass(String clazz, String ref) {
        if ( CmnString.isNotBlank(clazz) ) {
            return CmnXml.getBeanClass(clazz);
        } else if ( mapScan.containsKey(ref) ) {
            return mapScan.get(ref).clas;
        } else if ( mapXml.containsKey(ref) ) {
            Bean bean = mapXml.get(ref);
            if ( CmnString.isNotBlank(bean.getClazz()) ) {
                return CmnXml.getBeanClass(bean.getClazz());
            }
            return getXmlBeanClass(bean.getClazz(), bean.getRef());
        } else {
            throw new IocException("找不到Bean定义：" + ref);
        }
    }

    /**
     * 初始化一个Bean
     * 
     * @param name Bean名称
     * @return Bean对象
     */
    private Object initScanBean(String name) {
        if ( super.mapIoc.containsKey(name) ) {
            return super.getBean(name);
        }

        if ( mapBool.containsKey(name) ) {
            throw new IocException("Bean循环依赖无法初始化:" + name);
        }
        mapBool.put(name, true); // 创建中

        // 取定义
        BeanDefine beanDefine = mapScan.get(name);
        getAndSetInitargs(beanDefine);

        // 创建
        Object obj = EnhanceBuilder.get().setSuperclass(beanDefine.clas).setConstructorArgs(beanDefine.constructor, beanDefine.initargs)
                .matchAopList(aopList).build();
        super.put(beanDefine.name, obj);
        mapBool.remove(name); // 创建成功

        // 注入
        injectObject(obj);
        return obj;
    }

    /**
     * 构造方法的参数初始化
     * 
     * @param beanDefine Bean定义
     */
    private void getAndSetInitargs(BeanDefine beanDefine) {
        if ( beanDefine.constructor == null ) {
            return;
        }

        beanDefine.initargs = new Object[beanDefine.constructor.getParameterCount()];
        Parameter[] parameters = beanDefine.constructor.getParameters();
        for ( int i = 0; i < parameters.length; i++ ) {
            String paramBeanName = null;
            if ( parameters[i].isAnnotationPresent(Autowired.class) ) {
                Autowired anno = parameters[i].getAnnotation(Autowired.class);
                paramBeanName = anno.value();
            }
            if ( CmnString.isBlank(paramBeanName) ) {
                paramBeanName = beanNameStrategy.getName(parameters[i].getType());
            }

            beanDefine.initargs[i] = initBean(paramBeanName);
        }

    }

    /**
     * AOP拦截处理类对象优先生成（AOP对象的字段注入最后做）
     * 
     * @return AOP拦截处理类对象列表
     */
    private List<Object> initAopBeans() {
        List<Object> list = new ArrayList<>();

        mapScan.forEach((name, beanDefine) -> {
            if ( beanDefine.clas.isAnnotationPresent(Aop.class) ) {
                Object obj = createInstance(beanDefine.clas);
                super.put(beanDefine.name, obj);
                list.add(obj);
            }
        });

        return list;
    }

    /**
     * 扫描Bean的内部字段及方法注入
     * 
     * @param bean 待注入处理的Bean对象
     */
    private void injectObject(Object bean) {
        // 字段注入
        injectByField(bean);
        // 方法注入
        injectByMethod(bean);
    }

    /**
     * 按字段注入
     * 
     * @param bean 待注入处理的Bean对象
     */
    private void injectByField(Object bean) {

        Class<?> targetClass = bean.getClass();
        while ( Enhance.class.isAssignableFrom(targetClass) ) {
            targetClass = targetClass.getSuperclass();
        }

        Field[] fields = targetClass.getDeclaredFields();
        for ( Field field : fields ) {
            if ( field.isAnnotationPresent(Autowired.class) ) {
                Autowired anno = field.getAnnotation(Autowired.class);
                String refName = anno.value();
                if ( CmnString.isBlank(refName) ) {
                    refName = beanNameStrategy.getName(field.getType());
                }

                if ( !mapScan.containsKey(refName) && mapXml.containsKey(refName) ) {
                    throw new IocException("找不到指定名称的Bean对象:" + refName);
                }

                field.setAccessible(true);
                try {
                    field.set(bean, initBean(refName));
                } catch (Exception e) {
                    throw new IocException("字段注入失败:" + field, e);
                }
            }
        }
    }

    /**
     * 按方法注入
     * 
     * @param bean 待注入处理的Bean对象
     */
    private void injectByMethod(Object bean) {

        Class<?> targetClass = bean.getClass();
        while ( Enhance.class.isAssignableFrom(targetClass) ) {
            targetClass = targetClass.getSuperclass();
        }

        Method[] methods = targetClass.getDeclaredMethods();
        for ( Method method : methods ) {
            if ( method.isAnnotationPresent(Autowired.class) ) {
                Object[] args = getInjectMethodArgs(method);
                try {
                    method.invoke(bean, args);
                } catch (Exception e) {
                    throw new IocException("方法注入失败:" + method, e);
                }
            }
        }
    }

    /**
     * 取得方法参数
     * 
     * @param method 方法
     * @return 方法参数
     */
    private Object[] getInjectMethodArgs(Method method) {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];
        for ( int i = 0; i < args.length; i++ ) {
            String refName = null;
            if ( parameters[i].isAnnotationPresent(Autowired.class) ) {
                refName = parameters[i].getAnnotation(Autowired.class).value();
            }

            if ( CmnString.isBlank(refName) ) {
                refName = beanNameStrategy.getName(parameters[i].getType());
            }

            args[i] = initBean(refName);
        }

        return args;
    }

    /**
     * 取得Bean定义
     * 
     * @return Bean定义Map
     */
    @SuppressWarnings("unchecked")
    private Map<String, BeanDefine> getBeanDefineMap() {
        Map<String, BeanDefine> map = new HashMap<>();

        String packages = DefaultConfig.getInstance().getString("ioc.scan");
        List<Class<?>> classlist = ScanBuilder.get().packages(packages).typeAnnotations(Aop.class, Component.class).getClasses();
        BeanDefine beanDefine;
        for ( Class<?> clas : classlist ) {
            beanDefine = getBeanDefine(clas);
            if ( map.containsKey(beanDefine.name) ) {
                log.error("Bean定义名称重复 {}:{}/{}", beanDefine.name, map.get(beanDefine.name).clas.getCanonicalName(), clas.getCanonicalName());
                throw new IocException("Bean定义名称重复：" + beanDefine.name);
            }
            map.put(beanDefine.name, beanDefine);
        }
        return map;
    }

    /**
     * 取得注解方式的Bean定义
     * 
     * @param clas 类
     * @return Bean定义
     */
    private BeanDefine getBeanDefine(Class<?> clas) {
        BeanDefine beanDefine = new BeanDefine();
        beanDefine.name = beanNameStrategy.getName(clas);
        beanDefine.clas = clas;
        beanDefine.constructor = getAutowiredConstructor(clas);
        return beanDefine;
    }

    /**
     * 取得自动注入的构造方法
     * 
     * @param clas 类
     * @return 自动注入的构造方法（null或唯一的一个）
     */
    private Constructor<?> getAutowiredConstructor(Class<?> clas) {
        Constructor<?>[] constructors = clas.getConstructors();
        int cnt = 0;
        Constructor<?> autowiredConstructor = null;
        for ( Constructor<?> constructor : constructors ) {
            if ( constructor.isAnnotationPresent(Autowired.class) ) {
                cnt++;
                autowiredConstructor = constructor;
            }
        }

        if ( cnt > 1 ) {
            throw new IocException("不支持多个构造方法同时注入：" + clas.getCanonicalName());
        }

        return autowiredConstructor;
    }

    /**
     * 创建对象
     * 
     * @param clas 类
     * @return 对象
     */
    private Object createInstance(Class<?> clas) {

        // 无构造方法注入时，按默认构造方法创建对象
        try {
            return clas.newInstance();
        } catch (Exception e) {
            throw new IocException(e);
        }

    }

    /**
     * Bea定义
     */
    private class BeanDefine {

        private String         name;
        private Class<?>       clas;
        private Constructor<?> constructor;
        private Object[]       initargs;

    }

}
