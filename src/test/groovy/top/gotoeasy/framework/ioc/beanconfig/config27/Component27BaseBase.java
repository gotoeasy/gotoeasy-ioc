package top.gotoeasy.framework.ioc.beanconfig.config27;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import top.gotoeasy.framework.ioc.annotation.Component;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface Component27BaseBase {

    String value() default "";
}
