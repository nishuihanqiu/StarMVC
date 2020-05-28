package com.liliangshan.web.mvc.bean;

/************************************
 * Action
 * @author liliangshan
 * @date 2020/5/28
 ************************************/
public class Action {

    private String method;
    private String path;

    public Action(String method, String path) {
        this.method = method;
        this.path = path;
    }

    public String getMethod() {
        return this.method;
    }

    public String getPath() {
        return path;
    }

}
