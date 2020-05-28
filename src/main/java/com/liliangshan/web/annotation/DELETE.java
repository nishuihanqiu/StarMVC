package com.liliangshan.web.annotation;

import java.lang.annotation.*;

/************************************
 * DELETE
 * @author liliangshan
 * @date 2020/5/25
 ************************************/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DELETE {

    String value() default "";

}
