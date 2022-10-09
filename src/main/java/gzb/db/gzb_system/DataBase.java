package gzb.db.gzb_system;

import gzb.db.DB;
import gzb.tools.*;
import gzb.tools.thread.GzbThread;
import gzb.tools.thread.ThreadPool;

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
    public static String gzbRightName="gzb_right";
    public static String gzbUsersName="gzb_users";
    public static String gzbtestName="gzbtest";
    public static String testName="test";
    public static DB db = new DB("gzb_system");
    public static Map<String, List<Object[]>> mapAskSql = new HashMap<>();
    public static Lock lockAsy = new ReentrantLock();

    static {
        try {
            contentManagerName =division(contentManagerName,Tools.configGetInteger("gzb.db.gzb_system.division.content_manager","0"));
            fileManagerName =division(fileManagerName,Tools.configGetInteger("gzb.db.gzb_system.division.file_manager","0"));
            gzbApiName =division(gzbApiName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_api","0"));
            gzbCacheName =division(gzbCacheName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_cache","0"));
            gzbRightName =division(gzbRightName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_right","0"));
            gzbUsersName =division(gzbUsersName,Tools.configGetInteger("gzb.db.gzb_system.division.gzb_users","0"));
            gzbtestName =division(gzbtestName,Tools.configGetInteger("gzb.db.gzb_system.division.gzbtest","0"));
            testName =division(testName,Tools.configGetInteger("gzb.db.gzb_system.division.test","0"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static final int addAsyInfo(String sql, Object[] objs) {
        DataBase.lockAsy.lock();
        try {
            List<Object[]> list = DataBase.mapAskSql.get(sql);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(objs);
            DataBase.mapAskSql.put(sql, list);
            return 1;
        } finally {
            DataBase.lockAsy.unlock();
        }
    }

    static {
        ThreadPool.start(new GzbThread() {
            @Override
            public void start() {
                Connection conn = null;
                ResultSet rs = null;
                PreparedStatement ps = null;
                while (true) {
                    try {
                        if (mapAskSql.size() == 0) {
                            Thread.sleep(100);
                            continue;
                        }
                        Map<String, List<Object[]>> map = mapAskSql;
                        List<Object[]> list = null;
                        mapAskSql = new HashMap<>();
                        lockAsy.lock();
                        lockAsy.unlock();
                        conn = DataBase.db.getConnection();
                        try {
                            conn.setAutoCommit(false);
                            for (Iterator<Map.Entry<String, List<Object[]>>> it = map.entrySet().iterator(); it.hasNext(); ) {
                                long t1 = new Date().getTime();
                                StringBuilder sb = new StringBuilder();
                                Map.Entry<String, List<Object[]>> en = it.next();
                                ps = conn.prepareStatement(en.getKey());
                                list = en.getValue();
                                sb.append("异步操作").append(en.getKey())
                                        .append("[")
                                        .append(list.size())
                                        .append("条]:");
                                for (int i = 0; i < list.size(); i++) {
                                    Object[] objs = list.get(i);
                                    sb.append("{");
                                    for (int j = 0; j < objs.length; j++) {
                                        ps.setObject(j + 1, objs[j]);
                                        if (j == objs.length - 1) {
                                            sb.append("'").append(objs[j]).append("'");
                                        } else {
                                            sb.append("'").append(objs[j]).append("',");
                                        }
                                    }

                                    if (i == list.size() - 1) {
                                        sb.append("}\r\n");
                                    } else {
                                        sb.append("},");
                                    }
                                    ps.addBatch();
                                }
                                long t2 = new Date().getTime();
                                sb.append("组装耗时:");
                                sb.append(t2 - t1);
                                sb.append("毫秒");
                                t1 = new Date().getTime();
                                int[] res = ps.executeBatch();
                                conn.commit();
                                t2 = new Date().getTime();
                                sb.append(";执行耗时:");
                                sb.append(t2 - t1);
                                sb.append("毫秒");
                                Log.sql(sb.toString());
                            }
                        } finally {
                            try {
                                conn.setAutoCommit(true);
                                DataBase.db.close(conn, rs, ps);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        Log.e(e);
                    }
                }
            }
        }, "DataBase.asy", false);
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
