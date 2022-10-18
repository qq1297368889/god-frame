package gzb.tools.config;

import gzb.Auto;
import gzb.tools.Tools;
import gzb.tools.cache.Cache;
import gzb.tools.cache.GzbCacheMap;
import gzb.tools.cache.GzbCacheMsql;
import gzb.tools.cache.GzbCacheRedis;
import gzb.tools.groovy.GroovyLoadV3;
import gzb.tools.groovy.GroovyLoadV4;
import jline.internal.Log;
import org.springframework.boot.system.ApplicationHome;

import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StaticClasses {
    public static GroovyLoadV4 groovyLoad;
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
    public static String uploadPathTmp;
    public static String staticPath;
    public static int asyBatchNum;
    public static int asySleepHm;

    public static int flowApiMax;
    public static int flowStaticMax;

    public static String groovyApiFolder;


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
    static {
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
            groovyLoadUrl = Tools.configGetString("gzb.groovy.load.url", null);
            uploadPath = Tools.configGetString("gzb.upload.path", System.getProperty("java.io.tmpdir"));
            uploadPathTmp = Tools.configGetString("gzb.upload.path.tmp", System.getProperty("java.io.tmpdir"));


            asyBatchNum = Tools.configGetInteger("gzb.db.asy.batch.num", "10000");
            asySleepHm = Tools.configGetInteger("gzb.db.asy.sleep.hm", "1000");
            staticPath = Tools.configGetString("gzb.static.path", null);
            groovyApiFolder = Tools.configGetString("gzb.groovy.api.folder", null);

            flowApiMax = Tools.configGetInteger("gzb.flow.api.max", "0");
            flowStaticMax = Tools.configGetInteger("gzb.flow.static.max", "0");

            json_code = Tools.configGetString("gzb.json.code", "code");
            json_state = Tools.configGetString("gzb.json.state", "state");
            json_message = Tools.configGetString("gzb.json.message", "message");
            json_jump = Tools.configGetString("gzb.json.jump", "jump");
            json_data = Tools.configGetString("gzb.json.data", "data");
            json_page = Tools.configGetString("gzb.json.page", "page");
            json_limit = Tools.configGetString("gzb.json.limit", "limit");
            json_count = Tools.configGetString("gzb.json.count", "count");
            json_next = Tools.configGetString("gzb.json.next", "next");


            staticPath=staticPath.replaceAll("\\.\\./","");
            groovyApiFolder = groovyApiFolder.replaceAll("\\\\", "/");



            lock1 = new ReentrantLock();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        printAll();


    }
    static {
        if (StaticClasses.cacheType.equals("map")) {
            Cache.gzbCache = new GzbCacheMap();
        } else if (StaticClasses.cacheType.equals("redis")) {
            Cache.gzbCache = new GzbCacheRedis();
        } else if (StaticClasses.cacheType.equals("msql")) {
            Cache.gzbCache = new GzbCacheMsql();
        }
    }


    static void printAll() {
        System.out.println("StaticClasses{" +
                "groovyLoad=" + groovyLoad +
                ", lock1=" + lock1 +
                ", showLog=" + showLog +
                ", sessionType='" + sessionType + '\'' +
                ", sessionUseTime=" + sessionUseTime +
                ", httpsession=" + httpsession +
                ", cacheType='" + cacheType + '\'' +
                ", thisDataBaseName='" + thisDataBaseName + '\'' +
                ", groovyLoadType='" + groovyLoadType + '\'' +
                ", groovyLoadUrl='" + groovyLoadUrl + '\'' +
                ", loginPage='" + loginPage + '\'' +
                ", uploadPath='" + uploadPath + '\'' +
                ", uploadPathTmp='" + uploadPathTmp + '\'' +
                ", staticPath='" + staticPath + '\'' +
                ", asyBatchNum=" + asyBatchNum +
                ", asySleepHm=" + asySleepHm +
                ", flowApiMax=" + flowApiMax +
                ", flowStaticMax=" + flowStaticMax +
                ", groovyApiFolder='" + groovyApiFolder + '\'' +
                ", json_code='" + json_code + '\'' +
                ", json_state='" + json_state + '\'' +
                ", json_message='" + json_message + '\'' +
                ", json_jump='" + json_jump + '\'' +
                ", json_data='" + json_data + '\'' +
                ", json_entity_data='" + json_entity_data + '\'' +
                ", json_page='" + json_page + '\'' +
                ", json_limit='" + json_limit + '\'' +
                ", json_count='" + json_count + '\'' +
                ", json_next='" + json_next + '\'' +
                ", devName=" + devName +
                '}');


    }
}
