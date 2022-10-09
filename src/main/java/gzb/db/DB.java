package gzb.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import gzb.tools.DateTime;
import gzb.tools.Tools;
import gzb.tools.cache.Cache;
import gzb.tools.config.StaticClasses;
import gzb.tools.log.LogImpl;
import java.sql.*;
import java.util.Date;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DB {
    static gzb.tools.log.Log Log=new LogImpl(DB.class);
    //固定
    public static Integer subId = 100000;
    public static Lock lock=new ReentrantLock();

    public static String db_url;
    public static String db_acc;
    public static String db_pwd;
    public static String db_name;
    public static String db_class;
    public static String db_auto;
    public static String db_threadMax;
    public static String db_overtime;
    private HikariDataSource cpds = null;
    public DB() {
        readConfig(StaticClasses.thisDataBaseName);
        initConnection(db_url, db_acc, db_pwd, db_class, db_name, db_auto, db_threadMax, db_overtime);
    }

    public DB(String db_name) {
        readConfig(db_name);
        initConnection(db_url, db_acc, db_pwd, db_class, db_name, db_auto, db_threadMax, db_overtime);
    }

    public DB(String db_url, String db_acc, String db_pwd, String db_class, String db_name, String db_auto, String db_threadMax, String db_overtime) {
        initConnection(db_url, db_acc, db_pwd, db_class, db_name, db_auto, db_threadMax, db_overtime);
    }

    public final void readConfig(String db_name) {
        this.lock.lock();
        try {
            this.db_name=db_name;
            this.db_url = Tools.configGetString("gzb.db." + db_name + ".url",null);
            this.db_class = Tools.configGetString("gzb.db." + db_name + ".class",null);
            this.db_acc = Tools.configGetString("gzb.db." + db_name + ".acc",null);
            this.db_pwd = Tools.configGetString("gzb.db." + db_name + ".pwd",null);
            this.db_auto = Tools.configGetString("gzb.db." + db_name + ".auto",null);
            this.db_threadMax = Tools.configGetString("gzb.db." + db_name + ".threadMax",null);
            this.db_overtime = Tools.configGetString("gzb.db." + db_name + ".overtime",null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        } finally {
            this.lock.unlock();
        }
    }
    public final void readConfig() {
        try {
            readConfig(Tools.configGetString("gzb.db.thisDataBaseName",null));
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
            Log.i("mysql:" + db_name + "初始化成功");
        } catch (Exception e) {
            Log.e(e, "mysql:" + db_name + "初始化失败");
        }
    }

    public final int runSqlUpdateOrSaveOrDelete(String sql, Object[] para){
        if (para == null) {
            para = Tools.toArray();
        }
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstate = null;
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(";参数:");
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
            sb.append(";耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
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
    public final Long getOnlyIdDistributed() {
        this.lock.lock();
        try {
            this.subId++;
            if (this.subId > 999999) {
                this.subId = 100000;
            }

            return Long.valueOf( new DateTime().toStampInt()+(this.subId.toString())+ StaticClasses.devName);
        } finally {
            this.lock.unlock();
        }
    }

    //返回一个不会重复的id redis 或者 map  自增
    public int getOnlyIdNumber(String mapName, String idName) {
        Long id;
        this.lock.lock();
        try {
            id = Cache.gzbCache.getIncr("db_" + mapName + "_" + idName + "_auto_incr");
            if (id == null) {
                id = getMaxId_db_private(mapName, idName);
                if (id == null) {
                    id = 0l;
                }
                Cache.gzbCache.set("db_" + mapName + "_" + idName + "_auto_incr", id.toString());
            }
        } finally {
            this.lock.unlock();
        }
        return Integer.valueOf(id.toString());
    }

    public Long getMaxId_db_private(String mapName, String idName) {
        String sql = "select " + idName + " from " + mapName + " order by " + idName + " desc limit 1";
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstate = null;
        StringBuilder sb = new StringBuilder();
        sb.append(mapName + ".getMaxId_private:").append(sql).append(";参数:");
        try {
            conn = getConnection();
            pstate = conn.prepareStatement(sql);
            long t1 = new Date().getTime();
            rs = pstate.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            Log.sql(sb.toString());
            if (rs.next()) {
                return Long.valueOf(rs.getString(idName));
            } else {
                return 0l;
            }
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            close(conn, rs, pstate);
        }
        return 0l;
    }



}
