package top.gotoeasy.framework.ioc.sample98;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component("sample96Bean1")
public class Sample98Bean1 {

    @Autowired("sample96Bean1")
    private Sample98Bean2 sample98Bean2;

    public Sample98Bean2 getSample97Bean2() {
        return sample98Bean2;
    }

}
