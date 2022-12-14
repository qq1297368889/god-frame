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
    public static DB db = new DB("gzb_system");
    static {
        try {
            ThreadPool.start(new GzbThread() {
                @Override
                public void start() throws Exception {
                    while (true){
                        contentManagerName = db.division(contentManagerName,Tools.configGetInteger("gzb.db.gzb_system.division.content_manager","0"));
                        fileManagerName = db.division(fileManagerName,Tools.configGetInteger("gzb.db.gzb_system.division.file_manager","0"));
                        gzbApiName = db.division(gzbApiName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_api","0"));
                        gzbCacheName = db.division(gzbCacheName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_cache","0"));
                        gzbGroupName = db.division(gzbGroupName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_group","0"));
                        gzbRightName = db.division(gzbRightName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_right","0"));
                        gzbUsersName = db.division(gzbUsersName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_users","0"));
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
    //0?????????  1???????????? 2???????????? 3????????????
    public static final String division(String mapName, int lv) {
        Object[] arr = Tools.toArray("", "_yyyy", "_yyyy_MM", "_yyyy_MM_dd");
        if (lv == 0 || lv > 3) {
            Log.i("??????????????????" + mapName);
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
                    Log.i("??????????????????" + newMapName);
                } catch (Exception e) {
                    try {
                        Log.i("????????????" + newMapName);
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
