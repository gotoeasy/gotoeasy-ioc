package top.gotoeasy.framework.ioc.impl;

import java.lang.reflect.Constructor;

public class BeanDefine2 {

    protected String         name;
    protected Class<?>       clas;
    protected Constructor<?> constructor;
    protected Object[]       initargs;

    protected boolean        creatting;

    protected boolean        isScan = true;

}
