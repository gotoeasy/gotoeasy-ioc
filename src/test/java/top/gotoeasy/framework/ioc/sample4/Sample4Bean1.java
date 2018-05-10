package top.gotoeasy.framework.ioc.sample4;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Sample4Bean1 {

    @Autowired
    private Sample4Bean2 sample4Bean2;

    private Sample4Bean2 bean;

    public Sample4Bean2 getSample4Bean2() {
        return sample4Bean2;
    }

    public void setSample4Bean2(Sample4Bean2 sample4Bean2) {
        this.sample4Bean2 = sample4Bean2;
    }

    public Sample4Bean2 getBean() {
        return bean;
    }

    @Autowired
    public void setBean(Sample4Bean2 bean) {
        this.bean = bean;
    }

}
