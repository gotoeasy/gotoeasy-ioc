package top.gotoeasy.framework.ioc.beanconfig.config26;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Bean2605 {

    @Autowired
    private Bean2603 bean2603;

    public Bean2603 getBean2603() {
        return bean2603;
    }

    public void setBean2603(Bean2603 bean2603) {
        this.bean2603 = bean2603;
    }

}
