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

import top.gotoeasy.framework.aop.Enhance;
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

    /**
     * 初始化全部Bean
     * 
     * @param map Bean定义Map
     * @param aopList AOP拦截处理类对象列表
     */
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

    /**
     * 初始化一个Bean
     * 
     * @param map Bean定义Map
     * @param name Bean名称
     * @param aopList AOP拦截处理类对象列表
     * @return Bean对象
     */
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

    /**
     * 构造方法的参数初始化
     * 
     * @param beanDefine Bean定义
     * @param map Bean定义Map
     * @param aopList AOP拦截处理类对象列表
     */
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

    /**
     * AOP拦截处理类对象优先生成
     * 
     * @param map Bean定义Map
     * @return AOP拦截处理类对象列表
     */
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

                if ( !super.mapIoc.containsKey(refName) ) {
                    throw new IocException("找不到指定名称的Bean对象:" + refName);
                }

                field.setAccessible(true);
                try {
                    field.set(bean, super.getBean(refName));
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

            if ( !super.mapIoc.containsKey(refName) ) {
                throw new IocException("找不到指定名称的Bean对象:" + refName);
            }
            args[i] = super.getBean(refName);
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

        private boolean        creatting;
    }

}
