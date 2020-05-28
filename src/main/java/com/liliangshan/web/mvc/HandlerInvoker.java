package com.liliangshan.web.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/************************************
 * HandlerInvoker
 * @author liliangshan
 * @date 2020/5/24
 ************************************/
public interface HandlerInvoker {

    void invoke(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception ;

}
