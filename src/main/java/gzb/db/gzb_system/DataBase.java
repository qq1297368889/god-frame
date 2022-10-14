package gzb.db.gzb_system;

import gzb.db.DB;
import gzb.tools.*;
import gzb.tools.thread.GzbThread;
import gzb.tools.thread.ThreadPool;
import gzb.tools.cache.Cache;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import gzb.tools.log.Log;
import gzb.tools.log.LogImpl;

public class DataBase {
    static Log Log=new LogImpl(DataBase.class);
    public static String contentManagerName="content_manager";
    public static String fileManagerName="file_manager";
    public static String gzbApiName="gzb_api";
    public static String gzbCacheName="gzb_cache";
    public static String gzbGroupName="gzb_group";
    public static String gzbRightName="gzb_right";
    public static String gzbUsersName="gzb_users";
    public static String gzbtestName="gzbtest";
    public static String testName="test";
    public static String usersName="users";
    public static DB db = new DB("gzb_system");
    static {
        try {
            Cache.gzbCache.set("db_test_test_id_auto_incr", String.valueOf(db.getMaxId_db_private("test", "test_id")));
            ThreadPool.start(new GzbThread() {
                @Override
                public void start() throws Exception {
                    while (true){
                        contentManagerName = division(contentManagerName,Tools.configGetInteger("gzb.db.gzb_system.division.content_manager","0"));
                        fileManagerName = division(fileManagerName,Tools.configGetInteger("gzb.db.gzb_system.division.file_manager","0"));
                        gzbApiName = division(gzbApiName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_api","0"));
                        gzbCacheName = division(gzbCacheName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_cache","0"));
                        gzbGroupName = division(gzbGroupName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_group","0"));
                        gzbRightName = division(gzbRightName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_right","0"));
                        gzbUsersName = division(gzbUsersName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_users","0"));
                        gzbtestName = division(gzbtestName,Tools.configGetInteger("gzb.db.gzb_system.division.gzbtest","0"));
                        testName = division(testName,Tools.configGetInteger("gzb.db.gzb_system.division.test","0"));
                        usersName = division(usersName,Tools.configGetInteger("gzb.db.gzb_system.division.users","0"));
                        sleep(1000*60);
                    }
                }
                public void sleep(int hm){
                    try {
                        Thread.sleep(hm);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            },"gzb_system.division", false,1); 
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }
    //0不开启  1按年分表 2按月分表 3按天分表
    public static final String division(String mapName, int lv) {
        Object[] arr = Tools.toArray("", "_yyyy", "_yyyy_MM", "_yyyy_MM_dd");
        if (lv == 0 || lv > 3) {
            Log.i("未开启分表：" + mapName);
            return mapName;
        }
        String newMapName = null;
        String newSql = "";
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = db.getConnection();
            for (int i = 0; i < 2; i++) {
                newMapName = mapName;
                newMapName += new DateTime().monthAdd(2-(i+1)).format(arr[lv].toString());
                try {
                    ps = conn.prepareStatement("show create table " + newMapName);
                    rs = ps.executeQuery();
                    Log.i("已存在分表：" + newMapName);
                } catch (Exception e) {
                    try {
                        Log.i("创建表：" + newMapName);
                        ps = conn.prepareStatement("show create table " + mapName);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            newSql = rs.getString(2);
                            newSql = newSql.replace("`" + mapName + "`", "`" + newMapName + "`");
                            db.runSqlUpdateOrSaveOrDelete(newSql, null);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            db.close(conn, rs, ps);
        }

        return newMapName;
    }
}
