package top.gotoeasy.framework.ioc.beanconfig.config7;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;
import top.gotoeasy.framework.ioc.annotation.Component;

@BeanConfig
public class MyBeanConfig7 {

    @Bean
    public Book myBook(@Autowired("book1") Book book) {
        return book;
    }

    @Bean
    public Object book1(Book book) {
        return new Object();
    }

    @Component
    public static class Book {

    }
}
