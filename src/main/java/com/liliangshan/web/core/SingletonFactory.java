package com.liliangshan.web.core;

import com.liliangshan.web.mvc.HandlerExceptionResolver;
import com.liliangshan.web.mvc.HandlerInvoker;
import com.liliangshan.web.mvc.HandlerMapping;
import com.liliangshan.web.mvc.ViewResolver;
import com.liliangshan.web.mvc.support.DefaultHandlerExceptionResolver;
import com.liliangshan.web.mvc.support.DefaultHandlerInvoker;
import com.liliangshan.web.mvc.support.DefaultHandlerMapping;
import com.liliangshan.web.mvc.support.DefaultViewResolver;
import com.liliangshan.web.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/************************************
 * SingletonFactory
 * @author liliangshan
 * @date 2020/5/28
 ************************************/
public class SingletonFactory {

    private static final Map<String, Object> caches = new ConcurrentHashMap<>();

    private static final String HANDLER_MAPPING = "mvc.web.handler-mapping";
    private static final String HANDLER_INVOKER = "mvc.web.handler-invoker";
    private static final String EXCEPTION_RESOLVER = "mvc.web.exception-resolver";
    private static final String VIEW_RESOLVER = "mvc.web.view-resolver";

    @SuppressWarnings("unchecked")
    private static <T> T make(String cacheKey, Class<T> implClass) {
        // 若缓存中存在对应的实例，则返回该实例
        if (caches.containsKey(cacheKey)) {
            return (T) caches.get(cacheKey);
        }
        // 通过反射创建该实现类对应的实例
        T instance = ClassUtils.newInstance(implClass.getName());
        // 若该实例不为空，则将其放入缓存
        if (instance != null) {
            caches.put(cacheKey, instance);
        }
        // 返回该实例
        return instance;
    }

    public static HandlerMapping handlerMapping() {
        return make(HANDLER_MAPPING, DefaultHandlerMapping.class);
    }

    public static HandlerInvoker handlerInvoker() {
        return make(HANDLER_INVOKER, DefaultHandlerInvoker.class);
    }

    public static HandlerExceptionResolver handlerExceptionResolver() {
        return make(EXCEPTION_RESOLVER, DefaultHandlerExceptionResolver.class);
    }

    public static ViewResolver viewResolver() {
        return make(VIEW_RESOLVER, DefaultViewResolver.class);
    }


}
