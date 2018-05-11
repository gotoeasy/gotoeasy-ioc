package top.gotoeasy.framework.ioc.config.sample95;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Sample95Bean1 {

    @Autowired("sss")
    private Sample95Bean1 sample95Bean1;

    public Sample95Bean1 getSample95Bean1() {
        return sample95Bean1;
    }

}
