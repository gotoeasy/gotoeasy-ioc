package top.gotoeasy.framework.ioc.beanconfig.config26;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Bean2604 {

    @Autowired
    private Bean2605 bean2605;

    public Bean2605 getBean2605() {
        return bean2605;
    }

    public void setBean2605(Bean2605 bean2605) {
        this.bean2605 = bean2605;
    }

}
