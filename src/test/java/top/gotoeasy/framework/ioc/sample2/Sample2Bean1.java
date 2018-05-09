package top.gotoeasy.framework.ioc.sample2;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component("beanName")
public class Sample2Bean1 {

    @Autowired
    private Sample2Bean2 sample2Bean2;

    public Sample2Bean2 getSample2Bean2() {
        return sample2Bean2;
    }

    public void setSample2Bean2(Sample2Bean2 sample2Bean2) {
        this.sample2Bean2 = sample2Bean2;
    }

}
