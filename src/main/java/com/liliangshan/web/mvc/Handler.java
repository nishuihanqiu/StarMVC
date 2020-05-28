package com.liliangshan.web.mvc;

import java.lang.reflect.Method;

/************************************
 * Handler
 * @author liliangshan
 * @date 2020/5/24
 ************************************/
public interface Handler {

    Class<?> getControllerClass();

    Method getControllerMethod();


}
