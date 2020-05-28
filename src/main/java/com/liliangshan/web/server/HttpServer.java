package com.liliangshan.web.server;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.Servlet;
import java.io.File;
import java.io.IOException;

/************************************
 * HttpServer
 * @author liliangshan
 * @date 2020/5/27
 ************************************/
public class HttpServer {

    private int port;
    private Class<? extends Servlet> servletClass;
    private Tomcat tomcat;
    private final Object lock = new Object();
    private volatile boolean started = false;

    public HttpServer(int port, Class<? extends Servlet> servletClass) {
        this.port = port;
        this.servletClass = servletClass;
        this.tomcat = this.createTomcat();
    }

    private Tomcat createTomcat() {
        Tomcat tomcat = new Tomcat();
        tomcat.getConnector().setPort(port);
        File dirDoc = this.createTempDir("tomcat");
        tomcat.setBaseDir(dirDoc.getAbsolutePath());
        File tempDir = this.createTempDir("tomcat-mvc");
        String contextPath = "";
        Context context = tomcat.addContext(contextPath, tempDir.getAbsolutePath());
        context.setParentClassLoader(HttpServer.class.getClassLoader());
        tomcat.addServlet(contextPath, servletClass.getSimpleName(), servletClass.getName());
        context.addServletMappingDecoded("/*", servletClass.getSimpleName());
        return tomcat;
    }

    public void start() {
        if (started) {
            return;
        }
        synchronized (lock) {
           if (started) {
               return;
           }
            try {
                tomcat.start();
                tomcat.getServer().await();
                started = true;
            } catch (LifecycleException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }
    }

    public boolean isStarted() {
        return started;
    }

    private File createTempDir(String prefix) {
        try {
            File tempDir = File.createTempFile(prefix + ".", "." + getPort());
            tempDir.delete();
            tempDir.mkdir();
            tempDir.deleteOnExit();
            return tempDir;
        } catch (IOException e) {
            throw new RuntimeException("Unable to create tempDir. java.io.tmpdir is set to " + System.getProperty("java.io.tmpdir"), e);
        }
    }

    public int getPort() {
        return port;
    }
}
