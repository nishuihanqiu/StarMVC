package com.liliangshan.web.server;

import com.liliangshan.web.mvc.DispatcherServlet;

/************************************
 * Bootstrap
 * @author liliangshan
 * @date 2020/5/27
 ************************************/
public class Bootstrap {

    public static void run() {
        HttpServer server = new HttpServer(8080, DispatcherServlet.class);
        server.start();
    }

}
