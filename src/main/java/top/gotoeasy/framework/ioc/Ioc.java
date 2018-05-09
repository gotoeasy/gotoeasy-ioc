package top.gotoeasy.framework.ioc;

/**
 * IOC接口
 * 
 * @since 2018/3
 * @author 青松
 */
public interface Ioc {

    /**
     * 按名称取得对象
     * 
     * @param name 名称
     * @return 对象
     */
    public Object getBean(String name);

    /**
     * 按类取得对象
     * <p/>
     * 名称由类名按策略取得
     * 
     * @param claz 指定类
     * @return 对象
     */
    public <T> T getBean(Class<T> claz);

    /**
     * 按名称取得并转换成指定类对象
     * 
     * @param name 名称
     * @param claz 指定类
     * @return 对象
     */
    public <T> T getBean(String name, Class<T> claz);

}
