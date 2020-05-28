package com.liliangshan.web.mvc.bean;

import java.util.HashMap;
import java.util.Map;

/************************************
 * View
 * @author liliangshan
 * @date 2020/5/26
 ************************************/
public class View {

    private String name;
    private boolean redirected = false;
    private Map<String, Object> data; // 相关数据

    public View(String name) {
        this.name = name;
        this.redirected = name.startsWith("/");
        this.data = new HashMap<>();
    }

    public View addValue(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public View addValues(Map<String, Object> values) {
        if (values == null || values.isEmpty()) {
            return this;
        }
        this.data.putAll(values);
        return this;
    }

    public boolean isRedirect() {
        return redirected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Object> getData() {
        return data;
    }

}
