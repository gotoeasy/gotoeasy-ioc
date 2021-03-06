package top.gotoeasy.framework.ioc.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 编码方式的Bean注解
 * 
 * @since 2018/05
 * @author 青松
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Bean {

    /**
     * Bean名称 （默认为方法名，需自定义时设定）
     * 
     * @return Bean名称
     */
    String value() default "";
}
