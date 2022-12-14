package gzb.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import gzb.db.gzb_system.DataBase;
import gzb.tools.DateTime;
import gzb.tools.Tools;
import gzb.tools.cache.Cache;
import gzb.tools.config.StaticClasses;
import gzb.tools.log.ColorEnum;
import gzb.tools.log.LogImpl;
import gzb.tools.thread.GzbThread;
import gzb.tools.thread.ThreadPool;

import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DB {
    static gzb.tools.log.Log Log = new LogImpl(DB.class);
    public Lock lock = new ReentrantLock();
    public String db_url;
    public String db_acc;
    public String db_pwd;
    public String db_name;
    public String db_class;
    public String db_auto;
    public String db_threadMax;
    public String db_overtime;
    public HikariDataSource cpds = null;
    public Map<String, AsyEntity> mapAskSql = new HashMap<>();
    public Lock lockAsk = new ReentrantLock();

    public DB() {
        readConfig(StaticClasses.thisDataBaseName);
        initConnection(db_url, db_acc, db_pwd, db_class, db_name, db_auto, db_threadMax, db_overtime);
        asyThread();
    }

    public DB(String db_name) {
        readConfig(db_name);
        initConnection(db_url, db_acc, db_pwd, db_class, db_name, db_auto, db_threadMax, db_overtime);
        asyThread();
    }

    public DB(String db_url, String db_acc, String db_pwd, String db_class, String db_name, String db_auto, String db_threadMax, String db_overtime) {
        initConnection(db_url, db_acc, db_pwd, db_class, db_name, db_auto, db_threadMax, db_overtime);
        asyThread();
    }

    public final void readConfig(String db_name) {
        this.lock.lock();
        try {
            this.db_name = db_name;
            this.db_url = Tools.configGetString("gzb.db." + db_name + ".url", null);
            this.db_class = Tools.configGetString("gzb.db." + db_name + ".class", "com.mysql.jdbc.Driver");
            this.db_acc = Tools.configGetString("gzb.db." + db_name + ".acc", "root");
            this.db_pwd = Tools.configGetString("gzb.db." + db_name + ".pwd", "root");
            this.db_auto = Tools.configGetString("gzb.db." + db_name + ".auto", "true");
            this.db_threadMax = Tools.configGetString("gzb.db." + db_name + ".threadMax", (Runtime.getRuntime().availableProcessors() * 2) + "");
            this.db_overtime = Tools.configGetString("gzb.db." + db_name + ".overtime", "3000");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            this.lock.unlock();
        }
    }

    public final void readConfig() {
        try {
            readConfig(Tools.configGetString("gzb.db.thisDataBaseName", null));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final void initConnection(String db_url, String db_acc, String db_pwd, String db_class, String db_name, String db_auto, String db_threadMax, String db_overtime) {
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(db_url);
            config.setDriverClassName(db_class);
            config.setUsername(db_acc);
            config.setPassword(db_pwd);
            config.setAutoCommit(Boolean.valueOf(db_auto));
            config.setMaximumPoolSize(Integer.valueOf(db_threadMax));
            config.setConnectionTimeout(Integer.valueOf(db_overtime));
            config.addDataSourceProperty("nullCatalogMeansCurrent", true);
            cpds = new HikariDataSource(config);
            Log.i("mysql:" + db_name + "???????????????");
        } catch (Exception e) {
            Log.e(e, "mysql:" + db_name + "???????????????");
        }
    }

    public final int runSqlUpdateOrSaveOrDelete(String sql, Object[] para) {
        if (para == null) {
            para = Tools.toArray();
        }
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstate = null;
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(";??????:");
        try {
            conn = getConnection();
            pstate = conn.prepareStatement(sql);
            for (int i = 0; i < para.length; i++) {
                pstate.setObject(i + 1, para[i]);
                sb.append(para[i]).append(",");
            }
            long t1 = new Date().getTime();
            int res = pstate.executeUpdate();
            long t2 = new Date().getTime();
            sb.append(";??????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res;
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            close(conn, rs, pstate);
        }
        return -1;
    }

    public final Connection getConnection() throws SQLException {
        return cpds.getConnection();
    }

    public final String getKey(String sql, Object[] arr) {
        String key = sql;
        if (arr != null) {
            for (Object o : arr) {
                key += "_" + o;
            }
        }
        return key;
    }

    public final void close(Connection conn, ResultSet rs, PreparedStatement pstate) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstate != null) {
                pstate.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            Log.e(e);
        }
    }

    //??????????????????????????? long id ??????mysql bigint?????????????????????????????????denName??????????????????1000?????????????????????id ????????????
    public final Long getOnlyIdDistributed() {
        return Tools.getOnlyIdDistributed();
    }

    //???????????????????????????id redis ?????? map  ??????  int??????id ????????????
    public int getOnlyIdNumber(String mapName, String idName) {
        return Tools.getOnlyIdNumber(mapName, idName, this);
    }

    //???????????????????????????id int??????id ????????????
    public int getMaxId_db_private(String mapName, String idName) {
        String sql = "select " + idName + " from " + mapName + " order by " + idName + " desc limit 1";
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstate = null;
        StringBuilder sb = new StringBuilder();
        sb.append(mapName + ".getMaxId_private:").append(sql).append(";??????:");
        try {
            conn = getConnection();
            pstate = conn.prepareStatement(sql);
            long t1 = new Date().getTime();
            rs = pstate.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";??????:");
            sb.append(t2 - t1);
            sb.append("??????");

            if (rs.next()) {
                String data = rs.getString(idName);
                sb.append(",res:").append(data);
                return Integer.valueOf(data);
            } else {
                return 0;
            }
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            close(conn, rs, pstate);
            Log.sql(sb.toString());
        }
        return 0;
    }

    public int addAsyInfo(String sql, Object[] objs) {
        AsyEntity asyEntity;
        lockAsk.lock();
        try {
            asyEntity = mapAskSql.get(sql);
            if (asyEntity == null) {
                asyEntity = new AsyEntity();
                mapAskSql.put(sql, asyEntity);
            }
        } finally {
            lockAsk.unlock();
        }
        asyEntity.lock.lock();
        try {
            asyEntity.list.add(objs);
        } finally {
            asyEntity.lock.unlock();
        }
        return 1;
    }

    public void asyThread() {
        DB db = this;
        int b = Runtime.getRuntime().availableProcessors();
        ThreadPool.start(() -> {
            Connection conn = null;
            ResultSet rs = null;
            PreparedStatement ps = null;
            long t1 = 0l, t2 = 0l, t3 = 0l, t4 = 0l;
            Object[] objs;
            AsyEntity asyEntity;
            Map<String, AsyEntity> map = null;
            StringBuilder sb;
            Map.Entry<String, AsyEntity> en;
            while (true) {
                try {
                    boolean run1 = true;
                    lockAsk.lock();
                    try {
                        if (mapAskSql == null || mapAskSql.size() == 0) {
                            run1 = false;
                        } else {
                            map = mapAskSql;
                            mapAskSql = new HashMap<>();
                        }
                    } finally {
                        lockAsk.unlock();
                    }
                    if (!run1) {
                        Thread.sleep(StaticClasses.asySleepHm);
                        continue;
                    }
                    conn = db.getConnection();
                    try {
                        conn.setAutoCommit(false);
                        for (Iterator<Map.Entry<String, AsyEntity>> it = map.entrySet().iterator(); it.hasNext(); ) {
                            sb = new StringBuilder();
                            en = it.next();
                            ps = conn.prepareStatement(en.getKey());
                            asyEntity = en.getValue();
                            if (asyEntity == null || asyEntity.list.size() < 1) {
                                continue;
                            }
                            int num = 0;
                            t1 = new Date().getTime();
                            asyEntity.lock.lock();
                            try {
                                for (Iterator<Object[]> iterator = asyEntity.list.iterator(); iterator.hasNext(); ) {
                                    objs = iterator.next();
                                    for (int j = 0; j < objs.length; j++) {
                                        ps.setObject(j + 1, objs[j]);
                                    }
                                    ps.addBatch();
                                    num++;
                                    if (num % StaticClasses.asyBatchNum == 0 || !iterator.hasNext()) {
                                        t2 = new Date().getTime();
                                        t3 = new Date().getTime();
                                        ps.executeBatch();
                                        conn.commit();
                                        t4 = new Date().getTime();
                                        sb.append("????????????:")
                                                .append(t2 - t1)
                                                .append("??????")
                                                .append(",????????????:")
                                                .append(t4 - t3)
                                                .append("??????")
                                                .append("[")
                                                .append(num)
                                                .append("???]")
                                                .append(",SQL:")
                                                .append(en.getKey());
                                        Log.sql(sb.toString());
                                        sb.delete(0, sb.length());
                                        num = 0;
                                        t1 = new Date().getTime();
                                    }
                                }
                            } finally {
                                asyEntity.lock.unlock();
                            }
                        }
                    } finally {
                        try {
                            conn.setAutoCommit(true);
                            db.close(conn, rs, ps);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    Log.e(e, "DB.asy");
                }
            }
        }, "DB.asy", false, b);

    }

    //????????? ????????????
    public final String division(String mapName, int lv) {
        Object[] arr = Tools.toArray("", "_yyyy", "_yyyy_MM", "_yyyy_MM_dd");
        if (lv == 0 || lv > 3) {
            return mapName;
        }
        String newMapName = null;
        String newSql = "";
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            for (int i = 0; i < 2; i++) {
                newMapName = mapName;
                newMapName += new DateTime().monthAdd(2 - (i + 1)).format(arr[lv].toString());
                try {
                    ps = conn.prepareStatement("show create table " + newMapName);
                    rs = ps.executeQuery();
                } catch (Exception e) {
                    try {
                        ps = conn.prepareStatement("show create table " + mapName);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            newSql = rs.getString(2);
                            newSql = newSql.replace("`" + mapName + "`", "`" + newMapName + "`");
                            runSqlUpdateOrSaveOrDelete(newSql, null);
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            close(conn, rs, ps);
        }

        return newMapName;
    }
}

class AsyEntity {
    public Lock lock = new ReentrantLock();
    public List<Object[]> list = new ArrayList<>();
}

