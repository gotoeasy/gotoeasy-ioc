package top.gotoeasy.framework.ioc.config;

import top.gotoeasy.framework.ioc.impl.BeanDefine2;

/**
 * Bean定义类
 * <p>
 * 配置文件配置Bean时使用的Bean定义信息<br>
 * </p>
 * 
 * @since 2018/05
 * @author 青松
 */
public class ConfigBeanDefine extends BeanDefine2 {

    private ConfigBeanReference[] configBeanReferences;

    public ConfigBeanReference[] getConfigBeanReferences() {
        return configBeanReferences;
    }

    public void setConfigBeanReferences(ConfigBeanReference[] configBeanReferences) {
        this.configBeanReferences = configBeanReferences;
    }

}
