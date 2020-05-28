package com.liliangshan.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/************************************
 * IOUtils
 * @author liliangshan
 * @date 2020/5/25
 ************************************/
public class IOUtils {

    private static Logger logger = LoggerFactory.getLogger(IOUtils.class);

    public static String read(BufferedReader reader) {
        StringBuilder builder = new StringBuilder();
        String content;
        try {
            while ((content = reader.readLine()) != null) {
                builder.append(content);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(reader);
        }
        return builder.toString();
    }

    public static String read(InputStream is, int byteLength) {
        StringBuilder builder = new StringBuilder();
        byte[] bytes = new byte[byteLength];
        int position = 0;
        try {
            while ((position = is.read(bytes)) != -1) {
                builder.append(new String(bytes, 0, position));
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            close(is);
        }
        return builder.toString();
    }

    public static String read(InputStream is) {
        return read(is, 2048);
    }

    public static String read(HttpServletRequest request) {
        try {
            return read(request.getInputStream(), request.getContentLength());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
