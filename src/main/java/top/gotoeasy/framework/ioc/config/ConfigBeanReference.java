package top.gotoeasy.framework.ioc.config;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Bean引用类
 * <p>
 * 配置文件配置Bean时使用的一个Bean引用信息<br>
 * </p>
 * 
 * @since 2018/05
 * @author 青松
 */
public class ConfigBeanReference {

    private Field    field;

    private String   fieldReference;

    private Method   method;

    private String[] methodArgReferences;

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getFieldReference() {
        return fieldReference;
    }

    public void setFieldReference(String fieldReference) {
        this.fieldReference = fieldReference;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String[] getMethodArgReferences() {
        return methodArgReferences;
    }

    public void setMethodArgReferences(String[] methodArgReferences) {
        this.methodArgReferences = methodArgReferences;
    }

}
