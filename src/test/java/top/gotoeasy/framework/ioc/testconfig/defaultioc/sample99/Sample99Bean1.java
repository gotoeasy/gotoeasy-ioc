package top.gotoeasy.framework.ioc.testconfig.defaultioc.sample99;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component("sample96Bean1")
public class Sample99Bean1 {

    private Sample99Bean2 sample99Bean2;

    @Autowired
    public Sample99Bean1(@Autowired("xxx") Sample99Bean2 sample99Bean2) {
        this.sample99Bean2 = sample99Bean2;
    }

    public Sample99Bean2 getSample97Bean2() {
        return sample99Bean2;
    }

}
