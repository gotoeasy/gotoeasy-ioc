package top.gotoeasy.framework.ioc.beanconfig.config26;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Component;

@Component
public class Bean2602 {

    @Autowired("cnt")
    private int      cnt;

    private Bean2601 bean2601;
    @Autowired
    private Bean2601 bean;

    public Bean2602() {
    }

    public Bean2602(Bean2601 bean) {
        this.bean = bean;
    }

    public String hello(String name) {
        return "Hello " + name;
    }

    @Autowired
    public void setBean1(Bean2601 bean2601) {
        this.bean2601 = bean2601;
    }

    public Bean2601 getBean1() {
        return bean2601;
    }

    public Bean2601 getBean() {
        return bean;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

}
