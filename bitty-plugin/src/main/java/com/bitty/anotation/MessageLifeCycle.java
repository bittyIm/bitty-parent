package com.bitty.anotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageLifeCycle {
    //执行顺序
    int order() default 0;

    //plugin 名称
    String name() default "";

    //生命周期名称
    String value();
}
