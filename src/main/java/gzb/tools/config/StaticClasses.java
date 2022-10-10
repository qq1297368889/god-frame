package gzb.tools.config;

import gzb.tools.Tools;
import gzb.tools.cache.Cache;
import gzb.tools.cache.GzbCacheMap;
import gzb.tools.cache.GzbCacheRedis;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StaticClasses {
    public static Lock lock1;

    public static boolean showLog = false;
    public static String sessionType;

    public static long sessionUseTime;
    public static boolean httpsession = false;
    public static String cacheType;
    public static String thisDataBaseName;
    public static String groovyLoadType;
    public static String groovyLoadUrl;
    public static String loginPage;
    public static String uploadPath;
    public static int asyBatchNum;
    public static int asySleepHm;

    //对应 tools.json 的 code state msg jump data
    public static String json_code;
    public static String json_state;
    public static String json_message;
    public static String json_jump;
    public static String json_data;
    public static String json_entity_data;
    public static String json_page;
    public static String json_limit;
    public static String json_count;
    public static String json_next;


    //固定 3位数 服务器编号 --- AUTO ---
    public static int devName;
    public static int flow_public;

    static {
        lock1 = new ReentrantLock();
        lock1.lock();
        try {
            loginPage = Tools.configGetString("gzb.system.login.page", "login.html");
            sessionUseTime = Tools.configGetInteger("gzb.session.useTime", "600");
            httpsession = Tools.configGetBoolean("gzb.httpsession", "no");
            thisDataBaseName = Tools.configGetString("gzb.db.thisDataBaseName", "");
            sessionType = Tools.configGetString("gzb.session.type", "map");
            cacheType = Tools.configGetString("gzb.cache.type", "map");
            showLog = Tools.configGetBoolean("gzb.log.showLog", "false");
            devName = Tools.configGetInteger("gzb.devName", "100");
            groovyLoadType = Tools.configGetString("gzb.groovy.load.type", "no");
            groovyLoadUrl = Tools.configGetString("gzb.groovy.load.url", "/");
            flow_public = Tools.configGetInteger("gzb.flow.public", "0");
            uploadPath = Tools.configGetString("gzb.upload.path", "/");
            asyBatchNum = Tools.configGetInteger("gzb.db.asy.batch.num", "10000");
            asySleepHm = Tools.configGetInteger("gzb.db.asy.sleep.hm", "1000");




            json_code = Tools.configGetString("gzb.json.code", "code");
            json_state = Tools.configGetString("gzb.json.state", "state");
            json_message = Tools.configGetString("gzb.json.message", "message");
            json_jump = Tools.configGetString("gzb.json.jump", "jump");
            json_data = Tools.configGetString("gzb.json.data", "data");
            json_page = Tools.configGetString("gzb.json.page", "page");
            json_limit = Tools.configGetString("gzb.json.limit", "limit");
            json_count = Tools.configGetString("gzb.json.count", "count");
            json_next = Tools.configGetString("gzb.json.next", "next");


        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            printAll();
            lock1.unlock();
        }


    }
    static {
        if (StaticClasses.cacheType.equals("map")) {
            Cache.gzbCache = new GzbCacheMap();
        } else if (StaticClasses.cacheType.equals("redis")) {
            Cache.gzbCache = new GzbCacheRedis();
        } else if (StaticClasses.cacheType.equals("msql")) {
            //Cache.gzbCache = new GzbCacheMsql();
        }
    }


    public static void printAll() {
        System.out.println(
                "Config:[" +"\n"+
                        "showLog=" + showLog + "," +"\n"+
                        "sessionType=" + sessionType + "," +"\n"+
                        "sessionUseTime=" + sessionUseTime + "," +"\n"+
                        "httpsession=" + httpsession + "," +"\n"+
                        "cacheType=" + cacheType + "," +"\n"+
                        "thisDataBaseName=" + thisDataBaseName + "," +"\n"+
                        "groovyLoadType=" + groovyLoadType + "," +"\n"+
                        "groovyLoadUrl=" + groovyLoadUrl + "," +"\n"+
                        "loginPage=" + loginPage + "," +"\n"+
                        "uploadPath=" + uploadPath + "," +"\n"+
                        "asyBatchNum=" + asyBatchNum + "," +"\n"+
                        "asySleepHm=" + asySleepHm + "," +"\n"+
                        "json_code=" + json_code + "," +"\n"+
                        "json_state=" + json_state + "," +"\n"+
                        "json_message=" + json_message + "," +"\n"+
                        "json_jump=" + json_jump + "," +"\n"+
                        "json_data=" + json_data + "," +"\n"+
                        "json_entity_data=" + json_entity_data + "," +"\n"+
                        "devName=" + devName + "," +"\n"+
                        "flow_public=" + flow_public + "" +"\n"+
                        "]"
        );


    }
}
