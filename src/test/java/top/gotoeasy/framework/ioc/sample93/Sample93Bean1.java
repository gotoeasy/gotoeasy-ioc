package top.gotoeasy.framework.ioc.sample93;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component("sample92Bean2")
public class Sample93Bean1 {

    @Autowired
    public Sample93Bean1(Sample93Bean2 sample93Bean2) {

    }

    @Autowired
    public Sample93Bean1(Sample93Bean2 sample93Bean2, Sample93Bean2 bean2) {

    }
}
