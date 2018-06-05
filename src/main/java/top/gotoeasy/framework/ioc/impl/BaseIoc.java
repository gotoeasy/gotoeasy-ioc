package top.gotoeasy.framework.ioc.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.core.util.CmnSpi;
import top.gotoeasy.framework.ioc.Ioc;
import top.gotoeasy.framework.ioc.exception.IocException;
import top.gotoeasy.framework.ioc.strategy.BeanNameStrategy;

/**
 * IOC基类
 * 
 * @since 2018/03
 * @author 青松
 */
public class BaseIoc implements Ioc {

    private static final Log      log              = LoggerFactory.getLogger(BaseIoc.class);

    /** Bean容器 */
    protected Map<String, Object> mapIoc           = new ConcurrentHashMap<>();
    /** Bean名称策略 */
    protected BeanNameStrategy    beanNameStrategy = CmnSpi.loadSpiInstance(BeanNameStrategy.class);

    /**
     * 存放Bean对象
     * 
     * @param name 名称
     * @param bean 对象
     */
    public void put(String name, Object bean) {
        if ( mapIoc.containsKey(name) ) {
            throw new IocException("Bean的ID有重复：" + name);
        }
        mapIoc.put(name, bean);
        log.debug("IOC添加对象[{}:{}]", name, bean);
    }

    /**
     * 按名称取Bean对象
     * 
     * @param name 名称
     * @return Bean对象
     */
    @Override
    public Object getBean(String name) {
        return mapIoc.get(name);
    }

    /**
     * 按名称取Bean对象并自动转换
     * 
     * @param <T> 类
     * @param name 名称
     * @return Bean对象
     */
    @SuppressWarnings("unchecked")
    @Override
    public <T> T getBean(String name, Class<T> claz) {
        return (T)getBean(name);
    }

    /**
     * 按类取得对象
     * <p>
     * 名称由类名按策略取得
     * </p>
     * 
     * @param <T> 类
     * @param clas 指定类
     * @return 对象
     */
    @Override
    public <T> T getBean(Class<T> clas) {
        return getBean(beanNameStrategy.getName(clas), clas);
    }

}
