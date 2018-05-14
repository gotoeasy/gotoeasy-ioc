package top.gotoeasy.framework.ioc.sample.config.sample93;

import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component("sample92Bean2")
public class Sample93Bean1 {

    private static final Log log = LoggerFactory.getLogger(Sample93Bean1.class);

    @Autowired
    public Sample93Bean1(Sample93Bean2 sample93Bean2) {
        log.debug("@Autowired {}", sample93Bean2);
    }

    @Autowired
    public Sample93Bean1(Sample93Bean2 sample93Bean2, Sample93Bean2 bean2) {
        log.debug("@Autowired {}, {}", sample93Bean2, bean2);
    }
}
