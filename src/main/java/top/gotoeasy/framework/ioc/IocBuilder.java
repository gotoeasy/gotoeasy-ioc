package top.gotoeasy.framework.ioc;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import top.gotoeasy.framework.ioc.strategy.BeanNameStrategy;

public class IocBuilder {

    /** 扫描目标包（可逗号分隔多个包） */
    private List<String>                      listScanPackage      = new ArrayList<>();
    /** 要创建Bean的注解 */
    private List<Class<? extends Annotation>> listBeanAnnotation   = new ArrayList<>();
    /** 要自动注入的注解 */
    private List<Class<? extends Annotation>> listInjectAnnotation = new ArrayList<>();
    /** Bean名称策略 */
    private BeanNameStrategy                  beanNameStrategy     = new BeanNameStrategy() {};

    public Ioc build() {
        // TODO
        return null;
    }

    /**
     * 生成创建器
     * 
     * @return 创建器
     */
    public static IocBuilder get() {
        return new IocBuilder();
    }

    /**
     * 指定Bean名称策略
     * 
     * @return 创建器
     */
    public IocBuilder beanNameStrategy(BeanNameStrategy beanNameStrategy) {
        this.beanNameStrategy = beanNameStrategy;
        return this;
    }

    /**
     * 指定扫描包
     * 
     * @return 创建器
     */
    public IocBuilder scan(String ... packages) {
        for ( String pack : packages ) {
            listScanPackage.add(pack);
        }
        return this;
    }

    /**
     * 指定要创建Bean的注解
     * 
     * @return 创建器
     */
    @SuppressWarnings("unchecked")
    public IocBuilder setBeanAnnotation(Class<? extends Annotation> ... classAnnotations) {
        for ( Class<? extends Annotation> clas : classAnnotations ) {
            listBeanAnnotation.add(clas);
        }
        return this;
    }

    /**
     * 指定要自动注入的注解
     * 
     * @return 创建器
     */
    @SuppressWarnings("unchecked")
    public IocBuilder setInjectAnnotation(Class<? extends Annotation> ... classAnnotations) {
        for ( Class<? extends Annotation> clas : classAnnotations ) {
            listInjectAnnotation.add(clas);
        }
        return this;
    }

}
