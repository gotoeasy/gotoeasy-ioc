package top.gotoeasy.framework.ioc.sample1;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Sample1Bean1 {

    @Autowired
    private Sample1Bean2 sample1Bean2;

    public Sample1Bean2 getSample1Bean2() {
        return sample1Bean2;
    }

    public void setSample1Bean2(Sample1Bean2 sample1Bean2) {
        this.sample1Bean2 = sample1Bean2;
    }

}
