package top.gotoeasy.framework.ioc.beanconfig.config26;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Bean2601 {

    @Autowired("cnt")
    private int      cnt;

    @Autowired
    private Bean2602 bean2602;

    private Bean2602 bean;

    public Bean2602 getBean2() {
        return bean2602;
    }

    public void setBean2(Bean2602 bean2602) {
        this.bean2602 = bean2602;
    }

    public Bean2602 getBean() {
        return bean;
    }

    @Autowired
    public void setBean(Bean2602 bean) {
        this.bean = bean;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

}
