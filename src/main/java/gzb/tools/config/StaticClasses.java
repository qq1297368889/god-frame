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

    //对应 tools.json 的 code state msg jump data
    public static String json_code;
    public static String json_state;
    public static String json_message;
    public static String json_jump;
    public static String json_data;
    public static String json_entity_data;


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


            json_code = Tools.configGetString("gzb.json.code", "code");
            json_state = Tools.configGetString("gzb.json.state", "state");
            json_message = Tools.configGetString("gzb.json.message", "message");
            json_jump = Tools.configGetString("gzb.json.jump", "jump");
            json_data = Tools.configGetString("gzb.json.data", "data");
            json_entity_data = Tools.configGetString("gzb.json.entity.data", "data");

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
                "Config:[" +
                        "devName=" + devName + "," +
                        "sessionType=" + sessionType + "," +
                        "cacheType=" + cacheType + "," +
                        "showLog=" + showLog + "]"
        );
    }
}
