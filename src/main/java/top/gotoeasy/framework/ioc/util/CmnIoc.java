package top.gotoeasy.framework.ioc.util;

import top.gotoeasy.framework.ioc.Ioc;
import top.gotoeasy.framework.ioc.impl.DefaultIoc;

/**
 * IOC工具类
 * 
 * @since 2018/5
 * @author 青松
 */
public class CmnIoc {

    private static final Ioc ioc = new DefaultIoc();

    private CmnIoc() {
    }

    /**
     * 按名称取得对象
     * 
     * @param name 名称
     * @return 对象
     */
    public static Object getBean(String name) {
        return ioc.getBean(name);
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
    public static <T> T getBean(Class<T> clas) {
        return ioc.getBean(clas);
    }

    /**
     * 按名称取得并转换成指定类对象
     * 
     * @param <T> 类
     * @param name 名称
     * @param clas 指定类
     * @return 对象
     */
    public static <T> T getBean(String name, Class<T> clas) {
        return ioc.getBean(name, clas);
    }

}
