package top.gotoeasy.framework.ioc.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import top.gotoeasy.framework.aop.Enhance;
import top.gotoeasy.framework.aop.EnhanceBuilder;
import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.aop.util.AopUtil;
import top.gotoeasy.framework.core.config.DefaultConfig;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.core.reflect.ScanBuilder;
import top.gotoeasy.framework.core.util.CmnBean;
import top.gotoeasy.framework.core.util.CmnString;
import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;
import top.gotoeasy.framework.ioc.annotation.Component;
import top.gotoeasy.framework.ioc.exception.IocException;
import top.gotoeasy.framework.ioc.util.CmnXml;
import top.gotoeasy.framework.ioc.xml.Beans.XmlBean;
import top.gotoeasy.framework.ioc.xml.Beans.XmlBean.Constructor.Arg;
import top.gotoeasy.framework.ioc.xml.Beans.XmlBean.Property;

/**
 * IOC默认实现类
 * 
 * @since 2018/05
 * @author 青松
 */
public class DefaultIoc extends BaseIoc {

    private static final Log              log           = LoggerFactory.getLogger(DefaultIoc.class);

    // @Component注解Bean
    private Map<String, BeanDefine>       mapScan       = null;
    // XML配置Bean
    private Map<String, XmlBean>          mapXml        = null;
    // 编码配置Bean
    private Map<String, BeanConfigDefine> mapBeanConfig = null;

    // @Aop拦截处理对象
    private List<Object>                  aopList       = null;

    // Bean创建状态
    private Map<String, Boolean>          mapBool       = new HashMap<>();

    /**
     * 构造方法
     */
    public DefaultIoc() {
        init();
    }

    /**
     * 按名称取Bean对象
     * 
     * @param name 名称
     * @return Bean对象
     */
    @Override
    public Object getBean(String name) {
        if ( super.mapIoc.containsKey(name) ) {
            return super.mapIoc.get(name);
        }

        Object obj = null;
        if ( mapXml.containsKey(name) ) {
            obj = initXmlBean(name);
            mapXml.remove(name);
        } else if ( mapScan.containsKey(name) ) {
            obj = initScanBean(name);
            mapScan.remove(name);
        } else if ( mapBeanConfig.containsKey(name) ) {
            obj = initBeanConfigDefineBean(name);
            mapBeanConfig.remove(name);
        }
        return obj;
    }

    /**
     * 初始化
     */
    private void init() {

        String packages = DefaultConfig.getInstance().getString("ioc.scan");
        String fileNames = DefaultConfig.getInstance().getString("ioc.config.file");

        mapScan = getBeanDefineMap(packages);
        mapBeanConfig = getBeanConfigDefineMap(packages);
        mapXml = CmnXml.getXmlBeanDefines(fileNames);
        log.trace("扫描读取Bean配置 ------ OK");

        // 检查Bean的id是否有重复、是否依赖未定义的id、是否循环依赖
        checkBeanDefine();
        log.trace("检查Bean配置 --------- OK");

        // 创建AOP对象并注入
        aopList = initAopBeans();
        aopList.forEach(aopBean -> {
            injectByField(aopBean);
            injectByMethod(aopBean);
        });
        log.trace("创建AOP对象并注入 ------ OK");

//        // 编码方式配置的单纯Bean（编码负责对象的创建和注入，直接存放容器）
//        initConfigBean();
//
//        // 创建Bean对象
//        initComponentBeans();
//
//        // XML配置Bean的属性注入
//        injectPropertyOfXmlBean();
//
//        // 扫描Bean的字段及方法注入
//        injectFieldMethodOfScanBean();

    }

    // 创建编码方式配置的Bean（编码负责对象的创建和注入，直接存放容器）
    private Object initBeanConfigDefineBean(String name) {
        BeanConfigDefine beanConfigDefine = mapBeanConfig.get(name);
        Method method = beanConfigDefine.method;
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for ( int i = 0; i < parameters.length; i++ ) {
            String refName = null;
            if ( parameters[i].isAnnotationPresent(Autowired.class) ) {
                refName = parameters[i].getAnnotation(Autowired.class).value();
            }
            if ( CmnString.isBlank(refName) ) {
                refName = beanNameStrategy.getName(parameters[i].getType());
            }

            args[i] = getBean(refName);
        }

        Object obj;
        try {
            obj = beanConfigDefine.method.invoke(beanConfigDefine.clasInstance, args);
        } catch (Exception e) {
            throw new IocException(e);
        }

        // 编码负责对象的创建和注入，直接存放容器
        super.put(name, obj);
        return obj;
    }

    // 检查Bean的id是否有重复、是否依赖未定义的id、是否循环依赖
    private void checkBeanDefine() {
        Set<String> set = new HashSet<>();
        List<String> list = new ArrayList<>();
        mapScan.forEach((id, beanDefine) -> {
            if ( !set.add(id) ) {
                list.add("Bean定义id重复:" + id);
            }
        });
        mapXml.forEach((id, xmlBean) -> {
            if ( !set.add(id) ) {
                list.add("Bean定义id重复:" + id);
            }
        });
        mapBeanConfig.forEach((id, beanConfigDefine) -> {
            if ( !set.add(id) ) {
                list.add("Bean定义id重复:" + id);
            }
        });

        // 检查注入是否正确
        mapScan.forEach((id, beanDefine) -> {
            checkScanBeanArgsInject(set, beanDefine, list);
            checkScanBeanFieldInject(set, beanDefine, list);
            checkScanBeanMethodInject(set, beanDefine, list);
        });
        mapBeanConfig.forEach((id, beanConfigDefine) -> checkBeanConfigMethodArgsInject(set, beanConfigDefine.method, list));
        checkXmlBeanPropertyInject(set, list);
        if ( !list.isEmpty() ) {
            log.error("Bean配置有误{}", list);
            throw new IocException("Bean配置有误:" + list.toString());
        }

        // 检查是否循环
    }

    // 检查XML配置Bean的属性注入是否注入未定义id
    private void checkXmlBeanPropertyInject(Set<String> set, List<String> list) {
        mapXml.forEach((name, xmlBean) -> {
            // 构造方法参数注入检查
            checkXmlBeanArgsInject(set, xmlBean, list);
            // 属性注入检查
            List<Property> propertys = xmlBean.getProperty();
            propertys.forEach(property -> {
                if ( CmnString.isNotBlank(property.getRef()) && !set.contains(property.getRef()) ) {
                    list.add("属性注入找不到指定id的Bean定义:" + property.getRef() + "[xml bean id=" + xmlBean.getId() + "]");
                }
            });

        });
    }

    private void checkXmlBeanArgsInject(Set<String> set, XmlBean xmlBean, List<String> list) {
        // 构造方法参数注入检查
        if ( xmlBean.getConstructor() == null ) {
            return;
        }

        List<Arg> args = xmlBean.getConstructor().getArg();
        if ( args == null ) {
            return;
        }

        for ( int i = 0; i < args.size(); i++ ) {
            Arg arg = args.get(i);
            if ( CmnString.isNotBlank(arg.getRef()) && !set.contains(arg.getRef()) ) {
                list.add("构造方法参数注入找不到指定id的Bean定义:" + arg.getRef() + "[xml bean id=" + xmlBean.getId() + "]");
            }
        }

    }

    // 检查扫描Bean的方法注入是否注入未定义id
    private void checkBeanConfigMethodArgsInject(Set<String> set, Method method, List<String> list) {
        String refName = null;
        Parameter[] parameters = method.getParameters();
        for ( int i = 0; i < parameters.length; i++ ) {
            if ( parameters[i].isAnnotationPresent(Autowired.class) ) {
                refName = parameters[i].getAnnotation(Autowired.class).value();
            }
            if ( CmnString.isBlank(refName) ) {
                refName = beanNameStrategy.getName(parameters[i].getType());
            }

            if ( !set.contains(refName) ) {
                list.add("方法注入找不到指定id的Bean定义:" + refName + "[" + AopUtil.getMethodDesc(method.getDeclaringClass(), method) + "]");
            }
            refName = null;
        }
    }

    // 检查扫描Bean的方法注入是否注入未定义id
    private void checkScanBeanMethodInject(Set<String> set, BeanDefine beanDefine, List<String> list) {
        Class<?> scanBeanClass = beanDefine.clas;
        Method[] methods = scanBeanClass.getDeclaredMethods();
        String refName = null;
        for ( Method method : methods ) {
            if ( !method.isAnnotationPresent(Autowired.class) ) {
                continue;
            }

            if ( method.getParameterCount() == 0 ) {
                list.add("方法无参数，无效的注入:" + scanBeanClass.getCanonicalName() + "." + method.getName() + "()");
            }

            Parameter[] parameters = method.getParameters();
            for ( int i = 0; i < parameters.length; i++ ) {
                if ( method.getParameterCount() == 1 ) {
                    refName = method.getAnnotation(Autowired.class).value();
                }
                if ( CmnString.isBlank(refName) && parameters[i].isAnnotationPresent(Autowired.class) ) {
                    refName = parameters[i].getAnnotation(Autowired.class).value();
                }
                if ( CmnString.isBlank(refName) ) {
                    refName = beanNameStrategy.getName(parameters[i].getType());
                }

                if ( !set.contains(refName) ) {
                    list.add("方法注入找不到指定id的Bean定义:" + refName + "[" + AopUtil.getMethodDesc(scanBeanClass, method) + "]");
                }
                refName = null;
            }
        }
    }

    // 检查扫描Bean的构造方法参数是否注入未定义id
    private void checkScanBeanArgsInject(Set<String> set, BeanDefine beanDefine, List<String> list) {
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

            if ( !set.contains(paramBeanName) ) {
                list.add("构造方法参数注入找不到指定id的Bean定义:" + paramBeanName + "[" + beanDefine.clas.getCanonicalName() + "]");
            }
        }

    }

    // 检查扫描Bean的字段注入是否注入未定义id
    private void checkScanBeanFieldInject(Set<String> set, BeanDefine beanDefine, List<String> list) {
        Field[] fields = beanDefine.clas.getDeclaredFields();
        for ( Field field : fields ) {
            if ( field.isAnnotationPresent(Autowired.class) ) {
                Autowired anno = field.getAnnotation(Autowired.class);
                String refName = anno.value();
                if ( CmnString.isBlank(refName) ) {
                    refName = beanNameStrategy.getName(field.getType());
                }
                if ( !set.contains(refName) ) {
                    list.add("字段注入找不到指定id的Bean定义:" + refName + "[" + beanDefine.clas.getCanonicalName() + ":" + field.getName() + "]");
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    private Map<String, BeanConfigDefine> getBeanConfigDefineMap(String packages) {
        List<Class<?>> classlist = ScanBuilder.get().packages(packages).typeAnnotations(BeanConfig.class).getClasses();

        Map<String, BeanConfigDefine> map = new HashMap<>();
        classlist.forEach(clas -> {
            Method[] methods = clas.getDeclaredMethods();
            Bean annoBean;
            int modifies;
            for ( Method method : methods ) {
                modifies = method.getModifiers();
                if ( !Modifier.isPublic(modifies) || !method.isAnnotationPresent(Bean.class) || void.class.equals(method.getReturnType()) ) {
                    // 非public、没有返回值、没有@Bean，都无视跳过
                    continue;
                }

                annoBean = method.getAnnotation(Bean.class);
                String name = CmnString.isNotBlank(annoBean.value()) ? annoBean.value() : method.getName();

                if ( map.containsKey(name) ) {
                    log.error("Bean配置有误[Bean定义id重复:{}]", name);
                    throw new IocException("Bean id重复(" + name + ")，请检查：" + clas.getCanonicalName());
                }

                BeanConfigDefine beanConfigDefine = new BeanConfigDefine();
                beanConfigDefine.name = name;
                beanConfigDefine.clas = clas;
                beanConfigDefine.clasInstance = createInstance(clas);
                beanConfigDefine.method = method;

                map.put(name, beanConfigDefine);
            }
        });

        return map;
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
        } else if ( mapBeanConfig.containsKey(name) ) {
            return initBeanConfigDefineBean(name);
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
//          if ( mapBool.containsKey(name) ) {
//            throw new IocException("Bean循环依赖无法初始化:" + name);
//        }
//        mapBool.put(name, true); // 创建中

        // 取定义
        XmlBean xmlBean = mapXml.get(name);
        Constructor<?> constructor = null;
        Object[] initargs = null;

        Class<?> targetClass = getXmlBeanClass(xmlBean.getClazz(), xmlBean.getRef());
        if ( xmlBean.getConstructor() != null ) {
            List<Arg> args = xmlBean.getConstructor().getArg();
            constructor = getConstructorByArgs(name, targetClass, args); // name用于异常消息
            initargs = getXmlBeanInitargs(args); // 参数可以有引用注入
        }

        // 创建
        Object obj;
        if ( CmnString.isNotBlank(xmlBean.getValue()) ) {
            obj = CmnXml.getBeanValue(xmlBean.getClazz(), xmlBean.getValue());
        } else {
            obj = EnhanceBuilder.get().setSuperclass(targetClass).setConstructorArgs(constructor, initargs).matchAopList(aopList).build();
        }
        super.put(name, obj);
//        mapBool.remove(name); // 创建成功

        List<Property> propertys = xmlBean.getProperty();
        propertys.forEach(property -> {
            if ( CmnString.isNotBlank(property.getRef()) ) {
                CmnBean.setPropertyValue(obj, property.getName(), getBean(property.getRef())); // 属性可以有引用注入
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
                initargs[i] = getBean(arg.getRef());
            } else {
                initargs[i] = CmnXml.getBeanValue(arg.getClazz(), arg.getValue());
            }
        }

        return initargs;
    }

    private Constructor<?> getConstructorByArgs(String name, Class<?> targetClass, List<Arg> args) {
        Class<?>[] parameterTypes = new Class<?>[args.size()];

        Arg arg;
        for ( int i = 0; i < parameterTypes.length; i++ ) {
            arg = args.get(i);
            parameterTypes[i] = getXmlBeanClass(arg.getClazz(), arg.getRef());
        }

        try {
            return targetClass.getConstructor(parameterTypes);
        } catch (Exception e) {
            throw new IocException("XML的Bean配置找不到相应的构造方法，Bean id：" + name, e);
        }
    }

    // 取得class(简写类要转换,ref则要递归查找类)
    private Class<?> getXmlBeanClass(String clazz, String ref) {
        if ( CmnString.isNotBlank(clazz) ) {
            return CmnXml.getBeanClass(clazz);
        }

        return getBean(ref).getClass();
//        if ( mapScan.containsKey(ref) ) {
//            return mapScan.get(ref).clas;
//        } else if ( mapXml.containsKey(ref) ) {
//            XmlBean xmlBean = mapXml.get(ref);
//            if ( CmnString.isNotBlank(xmlBean.getClazz()) ) {
//                return CmnXml.getBeanClass(xmlBean.getClazz());
//            }
//            return getXmlBeanClass(xmlBean.getClazz(), xmlBean.getRef());
//        } else {
//            throw new IocException("找不到Bean定义：" + ref);
//        }
    }

    /**
     * 初始化一个Bean
     * 
     * @param name Bean名称
     * @return Bean对象
     */
    private Object initScanBean(String name) {
//        if ( mapBool.containsKey(name) ) {
//            throw new IocException("Bean循环依赖无法初始化:" + name);
//        }
//        mapBool.put(name, true); // 创建中

        // 取定义
        BeanDefine beanDefine = mapScan.get(name);
        if ( beanDefine.constructor != null ) {
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

                beanDefine.initargs[i] = getBean(paramBeanName);
            }
        }

        // 创建
        Object obj = EnhanceBuilder.get().setSuperclass(beanDefine.clas).setConstructorArgs(beanDefine.constructor, beanDefine.initargs)
                .matchAopList(aopList).build();
        super.put(beanDefine.name, obj);
//        mapBool.remove(name); // 创建成功

        // 字段注入、方法注入
        injectByField(obj);
        injectByMethod(obj);

        return obj;
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

    // 取得扫描Bean定义
    @SuppressWarnings("unchecked")
    private Map<String, BeanDefine> getBeanDefineMap(String packages) {
        Map<String, BeanDefine> map = new HashMap<>();

        List<Class<?>> classlist = ScanBuilder.get().packages(packages).typeAnnotations(Aop.class, Component.class).getClasses();
        BeanDefine beanDefine;
        for ( Class<?> clas : classlist ) {
            beanDefine = getBeanDefine(clas);
            if ( map.containsKey(beanDefine.name) ) {
                throw new IocException("注解配置的Bean有id重复：" + beanDefine.name);
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
     * 注解Bea定义
     */
    private class BeanDefine {

        private String         name;
        private Class<?>       clas;
        private Constructor<?> constructor;
        private Object[]       initargs;

    }

    /**
     * 编码配置Bea定义
     */
    private class BeanConfigDefine {

        private String   name;
        private Class<?> clas;
        private Object   clasInstance;
        private Method   method;

    }
}
