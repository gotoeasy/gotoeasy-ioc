package top.gotoeasy.framework.ioc.config.sample96;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component("sample96Bean1")
public class Sample96Bean1 {

    private Sample96Bean2 sample96Bean2;

    @Autowired
    public void setSample96Bean1(@Autowired("xxxx") Sample96Bean2 sample96Bean2) {
        this.sample96Bean2 = sample96Bean2;
    }

    public Sample96Bean2 getSample96Bean2() {
        return sample96Bean2;
    }

}
