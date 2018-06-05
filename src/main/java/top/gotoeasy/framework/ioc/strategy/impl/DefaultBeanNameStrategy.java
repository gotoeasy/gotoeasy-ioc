package top.gotoeasy.framework.ioc.strategy.impl;

import top.gotoeasy.framework.core.util.CmnString;
import top.gotoeasy.framework.ioc.strategy.BeanNameStrategy;
import top.gotoeasy.framework.ioc.util.CmnAnno;

/**
 * IOC容器Bean名称的默认策略
 * 
 * @since 2018/06
 * @author 青松
 */
public class DefaultBeanNameStrategy implements BeanNameStrategy {

    /**
     * 按类名取得Bean名称
     * 
     * @param clas 类名
     * @return Bean名称
     */
    @Override
    public String getName(Class<?> clas) {

        String name = CmnAnno.getComponentAnnotationValue(clas);
        if ( CmnString.isBlank(name) ) {
            return CmnString.uncapitalize(clas.getSimpleName());
        }

        return name;
    }

}
