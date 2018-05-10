package top.gotoeasy.framework.ioc.sample91;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Sample91Bean2 {

    private Sample91Bean1 sample91Bean1;

    @Autowired
    public Sample91Bean2(Sample91Bean1 sample91Bean1) {
        this.sample91Bean1 = sample91Bean1;
    }

    public String hello(String name) {
        return "Hello " + name;
    }

    public Sample91Bean1 getSample4Bean1() {
        return sample91Bean1;
    }

}
