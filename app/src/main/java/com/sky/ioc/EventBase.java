package com.sky.ioc;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface EventBase {

    /**
     * setOnClickListener 订阅
     * @return
     */
    String listenerSetter();

    /**
     * 事件监听类型
     * @return
     */
    Class<?> listenerType();

    /**
     * 事件处理
     * @return
     */
    String CallbackMethod();


}
