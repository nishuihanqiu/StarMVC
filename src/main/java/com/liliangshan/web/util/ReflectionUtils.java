package com.liliangshan.web.util;

import com.liliangshan.web.annotation.Body;
import com.liliangshan.web.annotation.Query;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;

/************************************
 * ReflectionUtils
 * @author liliangshan
 * @date 2020/5/26
 ************************************/
public class ReflectionUtils {

    public static Object[] getMethodParameters(HttpServletRequest req, HttpServletResponse resp, Method method) {
        //获取方法的参数列表
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            return new Object[0];
        }
        Object[] paramValues = new Object[parameterTypes.length];
        Map<String, String[]> parameterMap = req.getParameterMap();
        Annotation[][] annotations = method.getParameterAnnotations();

        String[] values = null;
        for (int i = 0; i < parameterTypes.length; i++) {
            //根据参数名称，做某些处理
            Class<?> paramType = parameterTypes[i];
            if (paramType.getSimpleName().equals(HttpServletRequest.class.getSimpleName())) {
                //参数类型已明确，这边强转类型
                paramValues[i] = req;
                continue;
            }
            if (paramType.getSimpleName().equals(HttpServletResponse.class.getSimpleName())) {
                paramValues[i] = resp;
                continue;
            }
            Annotation[] paramAnnotations = annotations[i];
            for (Annotation an : paramAnnotations) {
                if (an instanceof Query) {
                    Query query = (Query) an;
                    values = parameterMap.get(query.value());
                    if (values == null || values.length == 0) {
                        paramValues[i] = null;
                    } else {
                        paramValues[i] = handleTypeValue(paramType, values[0]);
                    }
                    break;
                } else if (an instanceof Body) {
                    paramValues[i] = JsonUtils.fromJSON(IOUtils.read(req), paramType);
                    break;
                }
            }
        }
        return paramValues;
    }

    public static Object handleTypeValue(Class<?> paramType, String value) {
        if (paramType == Short.class || paramType == short.class) {
            return Integer.parseInt(value);
        } else if (paramType == Integer.class || paramType == int.class) {
            return Integer.parseInt(value);
        } else if (paramType == Long.class || paramType == long.class) {
            return Long.parseLong(value);
        } else if (paramType == Float.class || paramType == float.class) {
            return Float.parseFloat(value);
        } else if (paramType == Double.class || paramType == double.class) {
            return Double.parseDouble(value);
        } else if (paramType == Boolean.class || paramType == boolean.class) {
            if (value.contains("true") || value.contains("false")) {
                return Boolean.valueOf(value);
            }
            return Integer.parseInt(value) == 1;
        } else if (paramType == Byte.class || paramType == byte.class) {
            return Byte.parseByte(value);
        } else if (paramType == Character.class || paramType == char.class) {
            return value.toCharArray()[0];
        }
        return value;
    }

}
