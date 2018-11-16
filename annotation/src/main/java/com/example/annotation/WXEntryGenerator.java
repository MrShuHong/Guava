package com.example.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface WXEntryGenerator {

    //申明该注解类需要的包名
    String getPackageName();

    //声明该注解所要生成的java类需要继承哪个父类
    Class<?> getSupperName();
}
