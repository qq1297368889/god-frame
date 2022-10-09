package gzb.tools.session;

import gzb.tools.config.StaticClasses;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionTool {
    public static final Session getSession(HttpServletRequest request, HttpServletResponse response, long mm){
        if (StaticClasses.sessionType.equals("redis")){
            return new SessionRedis(request,response,mm);
        }else if (StaticClasses.sessionType.equals("msql")){
            return new SessionMsql(request,response,mm);
        }else{
            return new SessionMap(request,response,mm);
        }
    }
}
