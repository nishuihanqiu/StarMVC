package com.liliangshan.web.util;

import com.liliangshan.web.core.Constants;
import freemarker.template.Configuration;

/************************************
 * ViewUtils
 * @author liliangshan
 * @date 2020/5/26
 ************************************/
public class ViewUtils {

    private static Configuration configuration = createConfiguration();

    private static Configuration createConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
        configuration.setClassLoaderForTemplateLoading(ClassUtils.getClassLoader(), Constants.VIEW_TEMPLATE_DIR);
        return configuration;
    }

    public static Configuration getTemplateConfig() {
        return configuration;
    }
}
