package top.gotoeasy.framework.ioc.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import top.gotoeasy.framework.aop.EnhanceBuilder;
import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.core.config.DefaultConfig;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.core.reflect.ScanBuilder;
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
    protected void init() {
        // 扫描创建AOP拦截处理对象
        List<Object> aopList = getAopList();
        aopList.forEach(bean -> super.put(beanNameStrategy.getName(bean.getClass()), bean));

        // 扫描创建注解Bean对象
        List<Object> beanList = getBeanList(aopList);
        beanList.forEach(bean -> super.put(beanNameStrategy.getName(bean.getClass()), bean));

        // 注入
        for ( Object bean : mapIoc.values() ) {
            injectObject(bean);
        }

    }

    /**
     * 注入
     * 
     * @param bean 待注入处理的Bean对象
     */
    private void injectObject(Object bean) {
        // 字段注入
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

        // 方法注入
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
     * 扫描创建AOP拦截处理对象
     * 
     * @return AOP拦截处理对象列表
     */
    @SuppressWarnings("unchecked")
    protected List<Object> getAopList() {
        List<Object> list = new ArrayList<>();

        String packages = DefaultConfig.getInstance().getString("ioc.scan");
        List<Class<?>> classlist = ScanBuilder.get().packages(packages).typeAnnotations(Aop.class).getClasses();
        for ( Class<?> clas : classlist ) {
            list.add(createInstance(clas));
        }
        return list;
    }

    /**
     * 扫描创建注解Bean对象
     * 
     * @param aopList AOP拦截处理对象列表
     * @return Bean对象列表
     */
    @SuppressWarnings("unchecked")
    protected List<Object> getBeanList(List<Object> aopList) {
        List<Object> list = new ArrayList<>();

        String packages = DefaultConfig.getInstance().getString("ioc.scan");
        List<Class<?>> classlist = ScanBuilder.get().packages(packages).typeAnnotations(Component.class).getClasses();
        Object bean;
        for ( Class<?> clas : classlist ) {
            bean = EnhanceBuilder.get().setSuperclass(clas).matchAopList(aopList).build();
            list.add(bean);
        }
        return list;
    }

    /**
     * 创建对象
     * 
     * @param clas 类
     * @return 对象
     */
    private Object createInstance(Class<?> clas) {

        try {
            return clas.newInstance();
        } catch (Exception e) {
            throw new IocException(e);
        }

    }

}
