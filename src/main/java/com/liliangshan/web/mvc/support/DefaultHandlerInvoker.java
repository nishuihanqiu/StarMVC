package com.liliangshan.web.mvc.support;

import com.liliangshan.web.core.Constants;
import com.liliangshan.web.core.SingletonFactory;
import com.liliangshan.web.mvc.Handler;
import com.liliangshan.web.mvc.HandlerInvoker;
import com.liliangshan.web.mvc.ViewResolver;
import com.liliangshan.web.mvc.bean.View;
import com.liliangshan.web.util.BeanUtils;
import com.liliangshan.web.util.ReflectionUtils;
import com.liliangshan.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/************************************
 * DefaultHandlerInvoker
 * @author liliangshan
 * @date 2020/5/24
 ************************************/
public class DefaultHandlerInvoker implements HandlerInvoker {

    private ViewResolver viewResolver = SingletonFactory.viewResolver();

    @Override
    public void invoke(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception {
        if (handler == null) {
            this.handleNull(request, response);
            return;
        }
        Class<?> aClass = handler.getControllerClass();
        Method method = handler.getControllerMethod();
        Object data = this.execute(request, response, aClass, method);
        viewResolver.resolveView(request, response, data);
    }

    private Object execute(HttpServletRequest request, HttpServletResponse response, Class<?> controllerClass, Method controllerMethod) throws Exception {
        Object instance = BeanUtils.getBean(controllerClass);
        Object[] parameters = ReflectionUtils.getMethodParameters(request, response, controllerMethod);
        controllerMethod.setAccessible(true);
        return controllerMethod.invoke(instance, parameters);
    }

    private void handleNull(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String currentPath = WebUtils.getRequestPath(request);
        if (currentPath.endsWith("/")) {
            currentPath = currentPath.substring(0, currentPath.length() - 1);
        }
        if (currentPath.equals(Constants.HOME_PAGE_PATH)) {
           this.handleHome(request, response);
           return;
        }
        this.handleNotFound(request, response);
    }

    private void handleHome(HttpServletRequest request, HttpServletResponse response) throws Exception {
        viewResolver.resolveView(request, response, new View(Constants.VIEW_TEMPLATE_HOME_PAGE));
    }

    private void handleNotFound(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        viewResolver.resolveView(request, response, new View(Constants.VIEW_TEMPLATE_NOT_FOUND));
    }

}
