package gzb.tools.servlet;

import gzb.tools.Tools;
import gzb.tools.config.StaticClasses;
import gzb.tools.entity.GroovyReturnEntity;
import gzb.tools.groovy.GroovyLoadV2;
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
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/*")
@MultipartConfig
public class Controller extends HttpServlet {
    static Log log=new LogImpl(Controller.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CPU(request, response,0);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        CPU(request, response,1);
    }


    private void CPU(HttpServletRequest request, HttpServletResponse response,int types) {
        try {
            log.i(request.getRequestURI());
            String[] arr1 = request.getRequestURI()
                    .split("/");
            if (arr1.length<2){
                if (types==0){
                    super.doGet(request,response);
                }else{
                    super.doPost(request,response);
                }
                return ;
            }
            String className="";
            String funName=arr1[arr1.length-1];
            for (int i = 2; i < arr1.length-1; i++) {
                className+=arr1[i]+(i+1==arr1.length-1 ? "" : "/");
            }
            className="/"+className;
            while(className.indexOf("//")>-1){
                className=className.replaceAll("//","/");
            }
            log.i(className);
            log.i(funName);
            response.setHeader("Content-Type", "application/json;charset=UTF-8");
            GroovyReturnEntity groovyReturnEntity = GroovyLoadV2.newObject(className);
            if (groovyReturnEntity == null) {
                if (types==0){
                    super.doGet(request,response);
                }else{
                    super.doPost(request,response);
                }
                return;
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
            GroovyLoadV2.setVariable(groovyReturnEntity, "request", request);
            GroovyLoadV2.setVariable(groovyReturnEntity, "response", response);
            GroovyLoadV2.setVariable(groovyReturnEntity, "session", SessionTool.getSession(request,response, StaticClasses.sessionUseTime));
            Object object = GroovyLoadV2.call(groovyReturnEntity,funName, map);
            if (object != null) {
                sendJson(response, object.toString());
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendJson(response, Tools.jsonFail(request.getRequestURI() + " API:400"));
            return;
        }
    }

    private void currentLimiting() {
    }

    private void sendJson(HttpServletResponse response, String data) {
        try {
            if (data != null) {
                response.getWriter().write(data);
                response.getWriter().flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
