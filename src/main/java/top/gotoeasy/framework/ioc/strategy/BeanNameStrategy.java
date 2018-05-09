package top.gotoeasy.framework.ioc.strategy;

import top.gotoeasy.framework.aop.Enhance;
import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.core.util.CmnString;
import top.gotoeasy.framework.ioc.annotation.Component;

/**
 * IOC容器Bean名称策略
 * 
 * @since 2018/03
 * @author 青松
 */
public interface BeanNameStrategy {

    /**
     * 按类名取得Bean名称
     * 
     * @param clas 类名
     * @return Bean名称
     */
    public default String getName(Class<?> clas) {
        Class<?> cls = clas;
        if ( Enhance.class.isAssignableFrom(cls) ) {
            // 增强类时，取父类
            cls = clas.getSuperclass();
        }

        String name;
        if ( cls.isAnnotationPresent(Component.class) ) {
            Component component = cls.getAnnotation(Component.class);
            name = component.value();
        } else {
            Aop aop = cls.getAnnotation(Aop.class);
            name = aop.value();
        }

        if ( CmnString.isBlank(name) ) {
            return CmnString.uncapitalize(cls.getSimpleName());
        }

        return name;
    }

}
