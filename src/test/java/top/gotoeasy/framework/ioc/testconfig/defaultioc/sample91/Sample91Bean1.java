package top.gotoeasy.framework.ioc.testconfig.defaultioc.sample91;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Sample91Bean1 {

    private Sample91Bean2 bean;

    @Autowired
    public Sample91Bean1(Sample91Bean2 bean) {
        this.bean = bean;
    }

    public Sample91Bean2 getBean() {
        return bean;
    }

}
