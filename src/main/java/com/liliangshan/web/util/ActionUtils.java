package com.liliangshan.web.util;

import com.liliangshan.web.annotation.*;
import com.liliangshan.web.core.Constants;
import com.liliangshan.web.mvc.bean.Action;
import com.liliangshan.web.mvc.support.HandlerSupport;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/************************************
 * WrapperUtils
 * @author liliangshan
 * @date 2020/5/25
 ************************************/
public class ActionUtils {

    private static Map<Action, HandlerSupport> handlers = new HashMap<>();

    static {
        List<Class<?>> controllerClasses = BeanUtils.getClassList(Constants.BASE_PACKAGE, Controller.class);
        if (!controllerClasses.isEmpty()) {
            for (Class<?> clazz : controllerClasses) {
                handlers.putAll(getAction(clazz));
            }
        }
    }

    private static Map<Action, HandlerSupport> getAction(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        Map<Action, HandlerSupport> handlers = new HashMap<>();
        for (Method method : methods) {
            Action wrapper = getHandlerSupport(clazz, method);
            if (wrapper != null) {
                handlers.put(wrapper, new HandlerSupport(clazz, method));
            }
        }
        return handlers;
    }

    private static Action getHandlerSupport(Class<?> clazz, Method method) {
        String path = "";
        if (clazz.isAnnotationPresent(Request.class)) {
            path = clazz.getAnnotation(Request.class).value();
        }
        if (method.isAnnotationPresent(GET.class)) {
            path = path + method.getAnnotation(GET.class).value();
            return new Action(Constants.MVC_GET, path);
        } else if (method.isAnnotationPresent(POST.class)) {
            path = path + method.getAnnotation(POST.class).value();
            return new Action(Constants.MVC_POST, path);
        } else if (method.isAnnotationPresent(PUT.class)) {
            path = path + method.getAnnotation(PUT.class).value();
            return new Action(Constants.MVC_PUT, path);
        } else if (method.isAnnotationPresent(DELETE.class)) {
            path = path + method.getAnnotation(DELETE.class).value();
            return new Action(Constants.MVC_DELETE, path);
        }
        return null;
    }

    public static Map<Action, HandlerSupport> getHandlers() {
        return handlers;
    }
}
