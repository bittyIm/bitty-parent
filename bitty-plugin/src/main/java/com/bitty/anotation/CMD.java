package com.bitty.anotation;

import com.bitty.proto.Message;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CMD {
    //顺序
    int order() default 0;

    String name()  default "";

    Message.MessageFrame.Cmd value();
}
