package com.liliangshan.web.annotation;

import java.lang.annotation.*;

/************************************
 * Autowired
 * @author liliangshan
 * @date 2020/5/27
 ************************************/
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {

    boolean required() default true;
    
}
