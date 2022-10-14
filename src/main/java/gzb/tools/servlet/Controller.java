package gzb.tools.servlet;

import gzb.tools.DateTime;
import gzb.tools.Tools;
import gzb.tools.cache.Cache;
import gzb.tools.config.StaticClasses;
import gzb.tools.entity.GroovyReturnEntity;
import gzb.tools.groovy.GroovyLoadV3;
import gzb.tools.log.Log;
import gzb.tools.log.LogImpl;
import gzb.tools.session.SessionTool;
import io.undertow.servlet.handlers.DefaultServlet;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@WebServlet("/*")
@MultipartConfig
public class Controller extends HttpServlet {
    static int thisFlow = 0;
    static Lock lock = new ReentrantLock();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CPU(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CPU(request, response);
    }


    private void CPU(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            String url = request.getRequestURI();
            while (url.indexOf("//") > -1) {
                url = url.replaceAll("//", "/");
            }
            String[] arr1 = url.split("/");
            StringBuilder sb = new StringBuilder();
            sb.append('/');
            for (int i = 1; i < arr1.length - 1; i++) {
                sb.append(arr1[i]).append('/');
            }
            GroovyReturnEntity groovyReturnEntity = GroovyLoadV3.newObject(sb.toString());
            if (groovyReturnEntity == null) {
                if (flowStaticLimit()) {
                    response.setStatus(403);
                    return;
                }
                staticFun(request, response, url);
                return;
            }
            if (flowApiLimit()) {
                response.setStatus(403);
                return;
            }
            if (groovyReturnEntity.entity.crossDomain) {
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Headers", "*");
                response.setHeader("Access-Control-Allow-Methods", "GET, POST");//, DELETE, PUT, OPTIONS
                response.setHeader("Access-Control-Allow-Private-Network", "true");
                response.setHeader("Access-Control-Allow-Credentials", "true");
                response.setHeader("Access-Control-Max-Age", "180");
                response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            }
            GroovyLoadV3.setVariable(groovyReturnEntity, "request", request);
            GroovyLoadV3.setVariable(groovyReturnEntity, "response", response);
            GroovyLoadV3.setVariable(groovyReturnEntity, "session", SessionTool.getSession(request, response, StaticClasses.sessionUseTime));
            Object object = GroovyLoadV3.callWeb(groovyReturnEntity, arr1[arr1.length - 1], request.getParameterMap(), request);
            if (object != null) {
                sendData(request, response, object.toString(), groovyReturnEntity);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(404);
            return;
        } finally {
            if (StaticClasses.flowStaticMax > 0 || StaticClasses.flowApiMax > 0) {
                lock.lock();
                try {
                    if (thisFlow>0)thisFlow--;
                } finally {
                    lock.unlock();
                }
            }
        }
    }


    private void sendData(HttpServletRequest request, HttpServletResponse response, String data, GroovyReturnEntity groovyReturnEntity) {
        try {
            if (data != null) {
                response.setHeader("Content-Type", groovyReturnEntity.entity.contentType);
                response.getWriter().write(data);
                response.getWriter().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //静态资源处理 仅仅是开发测试用  所以不加缓存 正式环境  请使用nginx
    private void staticFun(HttpServletRequest request, HttpServletResponse response, String url) {
        if (url.equals("/")) {
            url = "/index.html";
        }
        File file = new File(StaticClasses.staticPath + url);
        if (file.exists()) {
            response.setHeader("Cache-Control", "public,max-age=3600");
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
                Path path = file.toPath();
                response.setHeader("Content-Type", Files.probeContentType(path));
                byte[] bytes = new byte[1024 * 5];
                int len = 0;
                while ((len = bufferedInputStream.read(bytes)) != -1) {
                    response.getOutputStream().write(bytes, 0, len);
                }
                response.getOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
                response.setStatus(403);
            }
        } else {
            response.setStatus(404);
        }
    }


    public boolean flowStaticLimit() {
        if (StaticClasses.flowStaticMax == 0) {
            return false;
        }
        lock.lock();
        try {
            if (thisFlow >= StaticClasses.flowStaticMax) {
                thisFlow++;
                return true;
            } else {
                thisFlow++;
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    public boolean flowApiLimit() {
        if (StaticClasses.flowApiMax == 0) {
            return false;
        }
        lock.lock();
        try {
            if (thisFlow >= StaticClasses.flowApiMax) {
                thisFlow++;
                return true;
            } else {
                thisFlow++;
                return false;
            }
        } finally {
            lock.unlock();
        }
    }
}
