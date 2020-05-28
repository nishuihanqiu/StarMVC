package com.liliangshan.web.util;

import com.alibaba.fastjson.JSON;

/************************************
 * JsonUtils
 * @author liliangshan
 * @date 2020/5/23
 ************************************/
public class JsonUtils {

    public static <T> String toJSON(T obj) {
        return JSON.toJSONString(obj);
    }

    public static <T> T fromJSON(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }



}
