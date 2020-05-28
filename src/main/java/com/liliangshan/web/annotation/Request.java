package com.liliangshan.web.annotation;

import java.lang.annotation.*;

/************************************
 * Request
 * @author liliangshan
 * @date 2020/5/25
 ************************************/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Request {

    String value() default "";

}
