package com.kancy.mybatisplus.generator.view.listener;


import com.kancy.mybatisplus.generator.exception.AlertException;
import com.kancy.mybatisplus.generator.log.Log;
import com.kancy.mybatisplus.generator.utils.ClassUtils;
import com.kancy.mybatisplus.generator.utils.ExceptionUtils;
import com.kancy.mybatisplus.generator.view.listener.handler.DefaultCommandHandler;

import java.util.EventObject;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * DynamicEventHandler
 *
 * @author kancy
 * @date 2020/6/13 15:25
 */
public class DynamicEventHandler implements EventHandler {

    private static final Map<String, CommandHandler> handlerMap = new HashMap<>();

    @Override
    public void handle(String command) {
        handle(command, new EventObject(this));
    }

    @Override
    public void handle(String command, EventObject eventObject) {
        ExceptionUtils.exceptionListener(() -> {
            CommandHandler commandHandler = handlerMap.get(command);
            if (Objects.isNull(commandHandler)){
                commandHandler = handlerMap.get(Command.DEFAULT.name());
            }
            if (Objects.nonNull(commandHandler)){
                commandHandler.handleEvent(eventObject);
            }else {
                throw new AlertException("该功能暂不支持！");
            }
        });
    }

    public static void registerCommandHandler(Class<?> commandHandlerClass, Object ... constructorArgs){
        CommandAction annotation = commandHandlerClass.getDeclaredAnnotation(CommandAction.class);
        if (Objects.nonNull(annotation) && annotation.enable()){
            Object newInstance = ClassUtils.newInstance(commandHandlerClass, constructorArgs);
            if (Objects.nonNull(newInstance) && newInstance instanceof CommandHandler){
                CommandHandler instance = (CommandHandler) newInstance;
                handlerMap.put(annotation.value().name(), instance);
                handlerMap.put(annotation.value().name().toLowerCase(), instance);
                Log.debug("RegisterCommandHandler : %s", annotation.value());
            }
        }
    }

    public static void registerCommandHandler(CommandHandler commandHandler){
        CommandAction annotation = commandHandler.getClass().getDeclaredAnnotation(CommandAction.class);
        if (Objects.nonNull(annotation) && annotation.enable()){
            handlerMap.put(annotation.value().name(), commandHandler);
            handlerMap.put(annotation.value().name().toLowerCase(), commandHandler);
        }
    }

    static {
        registerCommandHandler(new DefaultCommandHandler());
    }
}
