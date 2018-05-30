package top.gotoeasy.framework.ioc.util;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Method;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.ioc.annotation.Component;

public class CmnAnno {

    private CmnAnno() {
        // 避免被new
    }

    /**
     * 判断注解是否"继承"自Component
     * 
     * @param annotation 注解
     * @return true:是/false:否
     */
    public static boolean isSubComponentAnnotation(Annotation annotation) {

        Annotation[] annotations = annotation.annotationType().getAnnotations();
        for ( Annotation anno : annotations ) {
            if ( anno instanceof Documented || anno instanceof Retention || anno instanceof Target ) {
                continue;
            }

            if ( Component.class.isInstance(anno) ) {
                return true;
            }

            if ( isSubComponentAnnotation(anno) ) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断注解类是否"继承"自Component
     * 
     * @param clas 类
     * @return value值
     */
    public static String getComponentAnnotationValue(Class<?> clas) {

        if ( clas.isAnnotationPresent(Component.class) ) {
            // @Component
            Component component = clas.getAnnotation(Component.class);
            return component.value();
        } else if ( clas.isAnnotationPresent(Aop.class) ) {
            // @Aop
            Aop aop = clas.getAnnotation(Aop.class);
            return aop.value();
        } else {
            // @@Component
            Annotation[] annotations = clas.getAnnotations();
            for ( Annotation anno : annotations ) {
                if ( isSubComponentAnnotation(anno) ) {
                    return getAnnotationValue(anno);
                }
            }
        }

        return "";
    }

    private static String getAnnotationValue(Annotation anno) {
        try {
            Method method = anno.getClass().getMethod("value");
            return (String)method.invoke(anno);
        } catch (Exception e) {
            return "";
        }
    }
}
