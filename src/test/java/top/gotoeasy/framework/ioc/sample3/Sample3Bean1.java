package top.gotoeasy.framework.ioc.sample3;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Sample3Bean1 {

    @Autowired
    private Sample3Bean2 sample3Bean2;

    public Sample3Bean2 getSample3Bean2() {
        return sample3Bean2;
    }

    public void setSample3Bean2(Sample3Bean2 sample3Bean2) {
        this.sample3Bean2 = sample3Bean2;
    }

}
