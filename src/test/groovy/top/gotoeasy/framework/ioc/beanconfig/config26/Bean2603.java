package top.gotoeasy.framework.ioc.beanconfig.config26;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Bean2603 {

    @Autowired
    private Bean2604 bean2604;

    public Bean2604 getBean2604() {
        return bean2604;
    }

    public void setBean2604(Bean2604 bean2604) {
        this.bean2604 = bean2604;
    }

}
