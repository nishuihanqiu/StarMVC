package com.liliangshan.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/************************************
 * HandlerExceptionResolver
 * @author liliangshan
 * @date 2020/5/24
 ************************************/
public interface HandlerExceptionResolver {

    void resolveException(HttpServletRequest request, HttpServletResponse response, Exception e);

}
