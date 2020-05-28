package com.liliangshan.web.mvc;

import javax.servlet.http.HttpServletRequest;

/************************************
 * HandlerMapping
 * @author liliangshan
 * @date 2020/5/24
 ************************************/
public interface HandlerMapping {

    Handler getHandler(HttpServletRequest request);

}
