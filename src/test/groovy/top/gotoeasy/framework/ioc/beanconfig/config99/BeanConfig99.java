package top.gotoeasy.framework.ioc.beanconfig.config99;

import top.gotoeasy.framework.ioc.annotation.Autowired;
import top.gotoeasy.framework.ioc.annotation.Bean;
import top.gotoeasy.framework.ioc.annotation.BeanConfig;

@BeanConfig
public class BeanConfig99 {

    @Bean
    public Book99 history() {
        Book99 book = new Book99();
        book.setName("历史");
        return book;
    }

    @Bean("english")
    public Book99 en() {
        Book99 book = new Book99();
        book.setName("英语");
        return book;
    }

    @Bean("xiaoming")
    public Student99 xiaoming(@Autowired("teacherZhang") Teacher99 teacher99) {
        Student99 student = new Student99("小明", 23);
        student.setTeacher(teacher99);
        return student;
    }

}
