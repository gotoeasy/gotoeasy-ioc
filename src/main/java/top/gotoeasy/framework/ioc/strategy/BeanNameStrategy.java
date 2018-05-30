package top.gotoeasy.framework.ioc.strategy;

import top.gotoeasy.framework.core.util.CmnString;
import top.gotoeasy.framework.ioc.util.CmnAnno;

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

        String name = CmnAnno.getComponentAnnotationValue(clas);
        if ( CmnString.isBlank(name) ) {
            return CmnString.uncapitalize(clas.getSimpleName());
        }

        return name;
    }

}
