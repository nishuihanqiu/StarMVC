package com.liliangshan.web.util;

import com.liliangshan.web.annotation.Autowired;
import com.liliangshan.web.annotation.Controller;
import com.liliangshan.web.annotation.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/************************************
 * ReflectionUtils
 * @author liliangshan
 * @date 2020/5/25
 ************************************/
public class BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private static final Map<Class<?>, Object> beanMap = new HashMap<>();

    /**
     * 包扫描类
     */
    private static List<Class<?>> classesByPackageName(String packageName) {
        List<Class<?>> classList = new ArrayList<>();
        toClassesByPackageName(classList, packageName);
        return classList;
    }

    private static void toClassesByPackageName(List<Class<?>> classList, String packageName) {
        URL url = ClassUtils.getClassLoader().getResource(packageName.replaceAll("\\.", "/"));
        if (url == null) {
            return;
        }
        File dir = new File(url.getFile());
        if (!dir.isDirectory()) {
            return;
        }
        File[] directories = dir.listFiles();
        if (directories == null || directories.length == 0) {
            return;
        }

        for (File file : directories) {
            if (file.isDirectory()) {
                toClassesByPackageName(classList, packageName + "." + file.getName());
            } else {
                String className = packageName + "." + file.getName().replace(".class", "");
                classList.add(ClassUtils.loadClass(className, false));
            }
        }
    }

    /**
     * 生成实例 目前只支持无参构造函数
     */
    public static synchronized void registerBeans(String packageName) {
        List<Class<?>> classList = classesByPackageName(packageName);
        try {
            for (Class<?> clazz : classList) {
                if (isValidatedBeanClass(clazz)) {
                    beanMap.put(clazz, clazz.newInstance());
                }
            }
            registerAutowiredBeans(classList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new Error("初始化 Bean 出错！", e);
        }
    }

    private static void registerAutowiredBeans(List<Class<?>> classList) throws Exception {
        for (Class<?> aClass : classList) {
            setAutowiredFields(aClass);
            setAutowiredMethods(aClass);
        }
    }

    private static void setAutowiredFields(Class<?> tClass) throws IllegalAccessException {
        if (!isValidatedBeanClass(tClass)) {
            return;
        }
        for (Field it : tClass.getDeclaredFields()) {
            if (isValidatedAutowiredField(it)) {
                Object instance = beanMap.get(tClass);
                if (instance != null) {
                    Object value = beanMap.get(it.getType());
                    it.setAccessible(true);
                    it.set(instance, value);
                }
            }
        }
    }

    private static void setAutowiredMethods(Class<?> tClass) throws InvocationTargetException, IllegalAccessException {
        if (!isValidatedBeanClass(tClass)) {
            return;
        }
        Object instance = beanMap.get(tClass);
        if (instance == null) {
            return;
        }
        Method[] methods = tClass.getDeclaredMethods();
        for (Method it : methods) {
            if (isValidatedAutowiredMethod(it)) {
                Class<?>[] parameterTypes = it.getParameterTypes();
                Object[] parameterValues = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    parameterValues[i] = beanMap.get(parameterTypes[i]);
                }
                it.setAccessible(true);
                it.invoke(instance, parameterValues);
            }
        }
    }

    private static boolean isValidatedBeanClass(Class<?> tClass) {
        return tClass.isAnnotationPresent(Controller.class) || tClass.isAnnotationPresent(Service.class);
    }

    private static boolean isValidatedAutowiredField(Field field) {
        if (!field.isAnnotationPresent(Autowired.class)) {
            return false;
        }
        Autowired autowired = field.getAnnotation(Autowired.class);
        return autowired.required();
    }

    private static boolean isValidatedAutowiredMethod(Method method) {
        if (!method.isAnnotationPresent(Autowired.class)) {
            return false;
        }
        Autowired autowired = method.getAnnotation(Autowired.class);
        return autowired.required();
    }

    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) {
        if (!beanMap.containsKey(clazz)) {
            synchronized (beanMap) {
                if (!beanMap.containsKey(clazz)) {
                    registerBeans(clazz.getPackage().getName());
                }
            }
        }
        if (!beanMap.containsKey(clazz)) {
            throw new RuntimeException("无法根据类名获取实例！" + clazz);
        }
        return (T) beanMap.get(clazz);
    }

    /**
     * 设置 Bean 实例
     */
    public static void setBean(Class<?> clazz, Object obj) {
        beanMap.put(clazz, obj);
    }

    public static List<Class<?>> getClassList(String packageName, Class<? extends Annotation> annotationClass) {
        List<Class<?>> classList = classesByPackageName(packageName);
        return classList.stream().filter(aClass -> aClass.isAnnotationPresent(annotationClass)).collect(Collectors.toList());
    }

}
