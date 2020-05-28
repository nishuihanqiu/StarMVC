package com.liliangshan.web.mvc.support;

import com.liliangshan.web.mvc.Handler;

import java.lang.reflect.Method;

/************************************
 * HandlerSupport
 * @author liliangshan
 * @date 2020/5/25
 ************************************/
public class HandlerSupport implements Handler {

    private Class<?> controllerClass;
    private Method controllerMethod;

    public HandlerSupport(Class<?> controllerClass, Method controllerMethod) {
        this.controllerClass = controllerClass;
        this.controllerMethod = controllerMethod;
    }

    @Override
    public Class<?> getControllerClass() {
        return controllerClass;
    }

    @Override
    public Method getControllerMethod() {
        return controllerMethod;
    }


}
