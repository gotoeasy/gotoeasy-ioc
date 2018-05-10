package top.gotoeasy.framework.ioc.sample4;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Sample4Bean2 {

    private Sample4Bean1 sample4Bean1;
    private Sample4Bean1 sample4Bean12;

    @Autowired
    public Sample4Bean2(Sample4Bean1 sample4Bean1, @Autowired("sample4Bean1") Sample4Bean1 bean) {
        this.sample4Bean1 = sample4Bean1;
        this.sample4Bean12 = bean;
    }

    public String hello(String name) {
        return "Hello " + name;
    }

    public Sample4Bean1 getSample4Bean1() {
        return sample4Bean1;
    }

    public Sample4Bean1 getBean() {
        return sample4Bean12;
    }
}
