package com.liliangshan.web.mvc;

import com.liliangshan.web.core.Constants;
import com.liliangshan.web.core.SingletonFactory;
import com.liliangshan.web.util.BeanUtils;
import com.liliangshan.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/************************************
 * DispatcherServlet
 * @author liliangshan
 * @date 2020/5/24
 ************************************/
public class DispatcherServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private HandlerMapping handlerMapping;
    private HandlerInvoker handlerInvoker;
    private HandlerExceptionResolver exceptionResolver;

    public DispatcherServlet() {
        handlerMapping = SingletonFactory.handlerMapping();
        handlerInvoker = SingletonFactory.handlerInvoker();
        exceptionResolver = SingletonFactory.handlerExceptionResolver();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
       BeanUtils.registerBeans(Constants.BASE_PACKAGE);
        super.init(config);

        logger.info("start servlet");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding(Constants.UTF_8);
        String currentRequestPath = WebUtils.getRequestPath(req);
        if (currentRequestPath.equals("/")) {
            resp.sendRedirect(req.getContextPath() + Constants.HOME_PAGE_PATH);
            return;
        }
        WebContext.build(req, resp);
        try {
            Handler handler = handlerMapping.getHandler(req);
            handlerInvoker.invoke(req, resp, handler);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            exceptionResolver.resolveException(req, resp, e);
        } finally {
            WebContext.destroy();
        }

    }


}
