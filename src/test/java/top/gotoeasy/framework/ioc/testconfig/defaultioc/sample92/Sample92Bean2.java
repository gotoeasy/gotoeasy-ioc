package top.gotoeasy.framework.ioc.testconfig.defaultioc.sample92;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Sample92Bean2 {

    @Autowired
    private Sample92Bean1 sample92Bean1;

    public Sample92Bean1 getSample4Bean1() {
        return sample92Bean1;
    }

}
