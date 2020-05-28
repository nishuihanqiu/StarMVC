package com.liliangshan.web.annotation;

import com.liliangshan.web.core.Constants;

import java.lang.annotation.*;

/************************************
 * Query
 * @author liliangshan
 * @date 2020/5/26
 ************************************/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Query {

    String value() default "";

    boolean required() default false;

    String defaultValue() default Constants.DEFAULT_NONE;

}
