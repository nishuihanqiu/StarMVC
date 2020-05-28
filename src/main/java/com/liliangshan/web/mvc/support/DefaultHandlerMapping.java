package com.liliangshan.web.mvc.support;

import com.liliangshan.web.mvc.Handler;
import com.liliangshan.web.mvc.HandlerMapping;
import com.liliangshan.web.mvc.bean.Action;
import com.liliangshan.web.util.WebUtils;
import com.liliangshan.web.util.ActionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/************************************
 * DefaultHandlerMapping
 * @author liliangshan
 * @date 2020/5/24
 ************************************/
public class DefaultHandlerMapping implements HandlerMapping {

    @Override
    public Handler getHandler(HttpServletRequest request) {
        String method = request.getMethod();
        String requestPath = WebUtils.getRequestPath(request);
        // 去掉当前请求路径末尾的“/”
        if (requestPath.endsWith("/")) {
            requestPath = requestPath.substring(0, requestPath.length() - 1);
        }
        Handler handler = null;
        Map<Action, HandlerSupport> handlers = ActionUtils.getHandlers();
        for (Map.Entry<Action, HandlerSupport> entry : handlers.entrySet()) {
            Action key = entry.getKey();
            String actionMethod = key.getMethod();
            String actionPath = key.getPath();
            if (actionMethod.equals(method) && actionPath.equals(requestPath)) {
                handler = entry.getValue();
                break;
            }
        }
        return handler;
    }

}
