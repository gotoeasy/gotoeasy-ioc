package top.gotoeasy.framework.ioc.strategy;

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
    public String getName(Class<?> clas);

}
