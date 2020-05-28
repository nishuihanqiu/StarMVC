package com.liliangshan.web.util;

import com.liliangshan.web.core.Constants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/************************************
 * WebUtils
 * @author liliangshan
 * @date 2020/5/24
 ************************************/
public class WebUtils {

    private static final Logger logger = LoggerFactory.getLogger(com.liliangshan.web.util.WebUtils.class);

    /**
     * 将数据以 JSON 格式写入响应中
     */
    public static void writeJSON(HttpServletResponse response, Object data) {
        try {
            // 设置响应头
            response.setContentType("application/json"); // 指定内容类型为 JSON 格式
            response.setCharacterEncoding(Constants.UTF_8); // 防止中文乱码
            // 向响应中写入数据
            PrintWriter writer = response.getWriter();
            String json = JsonUtils.toJSON(data);
            logger.info(json);
            writer.write(json); // 转为 JSON 字符串
            writer.flush();
            writer.close();
        } catch (Exception e) {
            logger.error("在响应中写数据出错！", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取请求路径
     */
    public static String getRequestPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = StringUtils.defaultIfEmpty(request.getPathInfo(), "");
        return servletPath + pathInfo;
    }

}

