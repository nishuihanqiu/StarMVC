package com.liliangshan.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/************************************
 * WebContext
 * @author liliangshan
 * @date 2020/5/24
 ************************************/
public class WebContext {

    private static final ThreadLocal<WebContext> contextContainers = new ThreadLocal<>();

    private HttpServletRequest request;
    private HttpServletResponse response;

    public static void build(HttpServletRequest request, HttpServletResponse response) {
        WebContext context = new WebContext();
        context.request = request;
        context.response = response;
        contextContainers.set(context);
    }

    public static void destroy() {
        contextContainers.remove();
    }

    public static WebContext getCurrentContext() {
        return contextContainers.get();
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
}
