package top.gotoeasy.framework.ioc.testconfig.defaultioc.sample97;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component("sample96Bean1")
public class Sample97Bean1 {

    private Sample97Bean2 sample97Bean2;

    @Autowired
    public void setSample97Bean1(@Autowired("sample96Bean1") Sample97Bean2 sample97Bean2) {
        this.sample97Bean2 = sample97Bean2;
    }

    public Sample97Bean2 getSample97Bean2() {
        return sample97Bean2;
    }

}
