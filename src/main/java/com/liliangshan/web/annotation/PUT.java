package com.liliangshan.web.annotation;

import java.lang.annotation.*;

/************************************
 * Put
 * @author liliangshan
 * @date 2020/5/25
 ************************************/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PUT {

    String value() default "";

}
