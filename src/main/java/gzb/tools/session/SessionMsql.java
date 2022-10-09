package gzb.tools.session;

import gzb.tools.Tools;
import gzb.tools.cache.GzbCache;
import gzb.tools.cache.GzbCacheMsql;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionMsql implements Session{
    public static GzbCache gzbCache= new GzbCacheMsql();
    String id;
    //有效 秒数 不支持 小于0的数值
    long mm;

    public SessionMsql(HttpServletRequest request, HttpServletResponse response, long mm){
        if (request == null || response == null){
            return;
        }
        String id=null;
        id= request.getParameter("token");
        if (id == null || id.length() != 32){
            id= request.getParameter("session");
            if (id == null || id.length() != 32){
                id= Tools.CookieGet("session",request);
                if (id == null || id.length() != 32){
                    id= request.getHeader("token");
                    if (id == null || id.length() != 32){
                        id=Tools.getUUID(Tools.getRandomString(19));
                        Tools.CookieSet("session",id,(int) mm,response,request);
                    }
                }
            }
        }
        this.id=id;
        this.mm=mm;
    }

    @Override
    public String getId() {
        return this.id;
    }
    @Override
    public void put(String key, String val) {
        gzbCache.set(id+"-"+key,val,mm);
    }
    @Override
    public Integer getInt(String key) {
        Object obj=gzbCache.get(id+"-"+key);
        if (obj==null){
            return null;
        }
        return Integer.valueOf(obj.toString());
    }
    @Override
    public String getString(String key) {
        Object obj=gzbCache.get(id+"-"+key);
        if (obj==null){
            return null;
        }
        return String.valueOf(obj.toString());
    }
    @Override
    public Object getObject(String key) {
        return gzbCache.get(id+"-"+key);
    }
    @Override
    public Object delete(String key) {
        return gzbCache.del(id+"-"+key);
    }

    @Override
    public long getIncr(String key) {
        return gzbCache.getIncr(id+"-"+key);
    }

}
