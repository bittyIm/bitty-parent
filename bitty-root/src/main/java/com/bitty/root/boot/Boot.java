package com.bitty.root.boot;


import com.bitty.anotation.CMD;
import com.bitty.proto.Message;
import com.bitty.root.Handler;
import com.bitty.root.RootProperty;
import lombok.extern.slf4j.Slf4j;
import org.reflections8.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Set;

@Slf4j
public class Boot {
    public Boot(RootProperty property) {
        getClassByAnotation(property);
    }

    public void getClassByAnotation(RootProperty property) {
        log.info("扫描插件 {}", property.getHandler());
        Reflections reflections = new Reflections(property.getHandler()); // 添加方法参数扫描工具

        Set<Class<?>> cmdHandlers = reflections.getTypesAnnotatedWith(CMD.class);
        cmdHandlers.forEach(s -> {
            log.info("扫描到 {}", s);
            Arrays.stream(s.getAnnotations()).forEach(a -> {
                Message.MessageFrame.Cmd key = ((CMD) a).value();
                //TODO 增加注解的处理 事件
                try {
                    Handler handler = (Handler) s.getDeclaredConstructor().newInstance();
//                    if (!Ctx.eventsHandler.containsKey(key)) {
//                        Ctx.eventsHandler.put(key, new ArrayList<>());
//                    }
//                    log.info("Add Handler {} ", ((CMD) a).name());
//                    Ctx.eventsHandler.get(key).add(handler);
                    //获取注解上的 cmd
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }

            });

        });

    }
}
