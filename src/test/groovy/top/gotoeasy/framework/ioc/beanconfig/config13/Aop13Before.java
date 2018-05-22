package top.gotoeasy.framework.ioc.beanconfig.config13;

import top.gotoeasy.framework.aop.annotation.Aop;
import top.gotoeasy.framework.aop.annotation.Before;
import top.gotoeasy.framework.core.log.Log;
import top.gotoeasy.framework.core.log.LoggerFactory;
import top.gotoeasy.framework.ioc.annotation.Autowired;

@Aop
public class Aop13Before {

    private static final Log log = LoggerFactory.getLogger(Aop13Before.class);

    @Autowired
    private Student1301      student1301;

    private Student1302      student1302;

    @Before(classes = Student13.class)
    public void before() {
        log.debug("@Before");
    }

    public Student1301 getStudent1301() {
        return student1301;
    }

    public Student1302 getStudent1302() {
        return student1302;
    }

    @Autowired
    public void setStudent1302(Student1302 student1302) {
        this.student1302 = student1302;
    }
}
