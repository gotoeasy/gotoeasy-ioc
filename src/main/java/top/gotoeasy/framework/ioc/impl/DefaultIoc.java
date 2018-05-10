package top.gotoeasy.framework.ioc.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import top.gotoeasy.framework.aop.EnhanceBuilder;
import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.core.config.DefaultConfig;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.core.reflect.ScanBuilder;
import top.gotoeasy.framework.core.util.CmnString;
import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;
import top.gotoeasy.framework.ioc.exception.IocException;

/**
 * IOC默认实现类
 * 
 * @since 2018/05
 * @author 青松
 */
public class DefaultIoc extends BaseIoc {

    private static final Log log = LoggerFactory.getLogger(DefaultIoc.class);

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

        // 扫描取得@Aop、@Component的定义信息
        Map<String, BeanDefine> map = getBeanDefineMap();

        // 创建AOP对象
        List<Object> aopList = initAopBeans(map);

        // 创建Bean对象
        initComponentBeans(map, aopList);

        // 注入
        for ( Object bean : mapIoc.values() ) {
            injectObject(bean);
        }

    }

    private void initComponentBeans(Map<String, BeanDefine> map, List<Object> aopList) {

        Iterator<Entry<String, BeanDefine>> it = map.entrySet().iterator();
        Entry<String, BeanDefine> entry;
        BeanDefine beanDefine;
        while ( it.hasNext() ) {
            entry = it.next();
            beanDefine = entry.getValue();

            initComponentBean(map, beanDefine.name, aopList);
            it.remove();
        }

    }

    private Object initComponentBean(Map<String, BeanDefine> map, String name, List<Object> aopList) {
        if ( super.mapIoc.containsKey(name) ) {
            return super.getBean(name);
        }

        BeanDefine beanDefine = map.get(name);
        getAndSetInitargs(beanDefine, map, aopList);

        Object bean = EnhanceBuilder.get().setSuperclass(beanDefine.clas).setConstructorArgs(beanDefine.constructor, beanDefine.initargs)
                .matchAopList(aopList).build();
        super.put(beanDefine.name, bean);
        return bean;
    }

    private void getAndSetInitargs(BeanDefine beanDefine, Map<String, BeanDefine> map, List<Object> aopList) {
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

            if ( super.mapIoc.containsKey(paramBeanName) ) {
                beanDefine.initargs[i] = super.getBean(paramBeanName);
            } else {
                if ( !map.containsKey(paramBeanName) ) {
                    throw new IocException("找不到名称为[" + paramBeanName + "]的Bean定义");
                }

                if ( beanDefine.creatting ) {
                    throw new IocException("循环依赖导致初始化失败：" + paramBeanName);
                }

                beanDefine.creatting = true;
                beanDefine.initargs[i] = initComponentBean(map, paramBeanName, aopList);
            }
        }

    }

    private List<Object> initAopBeans(Map<String, BeanDefine> map) {
        List<Object> list = new ArrayList<>();

        Iterator<Entry<String, BeanDefine>> it = map.entrySet().iterator();
        Entry<String, BeanDefine> entry;
        BeanDefine beanDefine;
        Object bean;
        while ( it.hasNext() ) {
            entry = it.next();
            beanDefine = entry.getValue();
            if ( beanDefine.clas.isAnnotationPresent(Aop.class) ) {
                bean = createInstance(beanDefine.clas);
                list.add(bean);
                it.remove();
                super.put(beanDefine.name, bean);
            }
        }

        return list;
    }

    /**
     * 注入
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

        Field[] fields = bean.getClass().getDeclaredFields();
        Object val;
        for ( Field field : fields ) {
            if ( field.isAnnotationPresent(Autowired.class) ) {
                val = mapIoc.get(beanNameStrategy.getName(field.getType()));
                if ( val == null ) {
                    log.warn("字段注入值为null：{}", field);
                }

                field.setAccessible(true);
                try {
                    field.set(bean, val);
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

        Method[] methods = bean.getClass().getDeclaredMethods();
        for ( Method method : methods ) {
            if ( method.isAnnotationPresent(Autowired.class) ) {
                Class<?>[] classes = method.getParameterTypes();
                Object[] args = new Object[classes.length];
                for ( int i = 0; i < args.length; i++ ) {
                    args[i] = mapIoc.get(beanNameStrategy.getName(classes[i]));
                    if ( args[i] == null ) {
                        log.warn("方法第{}参数注入值为null：{}", i, method);
                    }

                    try {
                        method.invoke(bean, args);
                    } catch (Exception e) {
                        throw new IocException("方法注入失败:" + method, e);
                    }
                }
            }
        }
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

    private BeanDefine getBeanDefine(Class<?> clas) {
        BeanDefine beanDefine = new BeanDefine();
        beanDefine.name = beanNameStrategy.getName(clas);
        beanDefine.clas = clas;
        beanDefine.constructor = getAutowiredConstructor(clas);
        return beanDefine;
    }

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

        private boolean        creatting;
    }

}
