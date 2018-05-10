package top.gotoeasy.framework.ioc.sample95;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Sample95Bean1 {

    @Autowired("sss")
    private Sample95Bean1 sample95Bean1;

}
