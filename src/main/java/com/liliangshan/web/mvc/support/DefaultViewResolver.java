package com.liliangshan.web.mvc.support;

import com.liliangshan.web.core.Constants;
import com.liliangshan.web.mvc.ViewResolver;
import com.liliangshan.web.mvc.bean.View;
import com.liliangshan.web.util.ViewUtils;
import com.liliangshan.web.util.WebUtils;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/************************************
 * DefaultViewResolver
 * @author liliangshan
 * @date 2020/5/26
 ************************************/
public class DefaultViewResolver implements ViewResolver {

    private Configuration configuration = ViewUtils.getTemplateConfig();

    @Override
    public void resolveView(HttpServletRequest request, HttpServletResponse response, Object data) throws Exception {
        response.setCharacterEncoding(Constants.UTF_8);
        if (data instanceof View) {
            this.resolveHtml(request, response, (View) data);
            return;
        }
        this.resolveJson(request, response, data);
    }

    private void resolveHtml(HttpServletRequest request, HttpServletResponse response, View view) throws Exception {
        String templateName = view.getName();
        if (StringUtils.isBlank(templateName)) {
            throw new RuntimeException("template name is empty.");
        }
        if (!templateName.endsWith(".ftl")) {
            templateName = templateName + ".ftl";
        }
        // 将模板和数据结合，并返回浏览器
        Template template = configuration.getTemplate(templateName);
        template.process(view.getData(), response.getWriter());
    }

    private void resolveJson(HttpServletRequest request, HttpServletResponse response, Object data) throws Exception {
        WebUtils.writeJSON(response, data);
    }



}
