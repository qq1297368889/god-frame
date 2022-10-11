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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/*")
@MultipartConfig
public class Controller extends HttpServlet {
    static Log log = new LogImpl(Controller.class);

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
            if (StaticClasses.flowType == 3) {
                if (!flowLimit(url)) {
                    response.setStatus(403);
                    return;
                }
            }
            String[] arr1 = url.split("/");
            if (arr1.length < 2) {
                if (StaticClasses.flowType == 2) {
                    if (!flowLimit(url)) {
                        response.setStatus(403);
                        return;
                    }
                }
                staticFun(request, response, url);
                return;
            }
            StringBuilder sb = new StringBuilder();
            sb.append('/');
            for (int i = 1; i < arr1.length - 1; i++) {
                sb.append(arr1[i]).append('/');
            }
            GroovyReturnEntity groovyReturnEntity = GroovyLoadV3.newObject(sb.toString());
            if (groovyReturnEntity == null) {
                if (StaticClasses.flowType == 2) {
                    if (!flowLimit(url)) {
                        response.setStatus(403);
                        return;
                    }
                }
                staticFun(request, response, url);
                return;
            }
            if (StaticClasses.flowType == 1) {
                if (!flowLimit(url)) {
                    response.setStatus(403);
                    return;
                }
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
            Map<String, Object[]> map = new HashMap<>();
            Map<String, String[]> map_str = request.getParameterMap();
            for (Map.Entry<String, String[]> en : map_str.entrySet()) {
                String[] ss1 = en.getValue();
                if (ss1 == null || ss1.length < 1) {
                    continue;
                }
                Object[] objs = new Object[ss1.length];
                for (int i = 0; i < ss1.length; i++) {
                    objs[i] = ss1[i];
                }
                map.put(en.getKey(), objs);
            }
            GroovyLoadV3.setVariable(groovyReturnEntity, "request", request);
            GroovyLoadV3.setVariable(groovyReturnEntity, "response", response);
            GroovyLoadV3.setVariable(groovyReturnEntity, "session", SessionTool.getSession(request, response, StaticClasses.sessionUseTime));
            Object object = GroovyLoadV3.call(groovyReturnEntity, arr1[arr1.length - 1], map);
            if (object != null) {
                sendData(request, response, object.toString(), groovyReturnEntity);
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(404);
            return;
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


    public boolean flowLimit(String path) {
        DateTime dateTime = new DateTime();
        //yyyy年MM月dd号HH点mm分ss秒
        String[] ss1 = path.split("\\.");
        if (StaticClasses.flowTypeException.indexOf(ss1[ss1.length - 1] + "/") > -1) {
            return true;
        }
        int a = 0;
        if (StaticClasses.flowTypeSecond > 0) {
            a = Cache.gzbCache.getIncr(dateTime.format("yyyy-MM-dd-HH-mm-ss") + "_" + path);
            if (a > StaticClasses.flowTypeSecond) {
                log.i("触发 秒级限流");
                return false;
            }
        }
        if (StaticClasses.flowTypeMinute > 0) {
            a = Cache.gzbCache.getIncr(dateTime.format("yyyy-MM-dd-HH-mm") + "_" + path);
            if (a > StaticClasses.flowTypeMinute) {
                log.i("触发 分级限流");
                return false;
            }
        }
        if (StaticClasses.flowTypeHour > 0) {
            a = Cache.gzbCache.getIncr(dateTime.format("yyyy-MM-dd-HH") + "_" + path);
            if (a > StaticClasses.flowTypeHour) {
                log.i("触发 时级限流");
                return false;
            }
        }
        if (StaticClasses.flowTypeDay > 0) {
            a = Cache.gzbCache.getIncr(dateTime.format("yyyy-MM-dd") + "_" + path);
            if (a > StaticClasses.flowTypeDay) {
                log.i("出发天级限流");
                return false;
            }
        }
        return true;
    }
}
