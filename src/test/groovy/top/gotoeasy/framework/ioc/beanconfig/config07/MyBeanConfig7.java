package top.gotoeasy.framework.ioc.beanconfig.config07;

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
    public Integer book1(Book book) {
        return 1;
    }

    @Component
    public static class Book {

    }
}
