package com.liliangshan.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/************************************
 * ViewResolver
 * @author liliangshan
 * @date 2020/5/24
 ************************************/
public interface ViewResolver {

    void resolveView(HttpServletRequest request, HttpServletResponse response, Object data) throws Exception;

}
