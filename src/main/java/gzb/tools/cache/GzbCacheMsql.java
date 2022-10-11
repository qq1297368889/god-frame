package gzb.tools.cache;

import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.db.gzb_system.dao.BaseDaoImpl;
import gzb.tools.DateTime;
import gzb.tools.log.LogImpl;
import gzb.tools.thread.GzbThread;
import gzb.tools.thread.ThreadPool;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GzbCacheMsql implements GzbCache {

    static gzb.tools.log.Log Log=new LogImpl(GzbCacheMsql.class);
    public static BaseDao dao = new BaseDaoImpl();
    public static Lock lock = new ReentrantLock();

    static {
        ThreadPool.start(new GzbThread() {
            @Override
            public void start() throws InterruptedException {
                while (true) {
                    lock.lock();
                    try {
                        DataBase.db.runSqlUpdateOrSaveOrDelete("delete from " + DataBase.gzbCacheName + " where gzb_cache_end_time < '" + new DateTime().toString() + "'", null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }
                    try {
                        Thread.sleep(1000 * 60);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "GzbCacheMsql");
    }

    public int getIncr(String key) {
        int id=0;
        lock.lock();
        try {
            gzb.db.gzb_system.entity.GzbCache gc = dao.gzbCacheFind("select * from " + DataBase.gzbCacheName + " where gzb_cache_key='" +
                    key + "' and gzb_cache_end_time > '" + new DateTime().toString() + "'", null);
            if (gc == null) {
                gc = new gzb.db.gzb_system.entity.GzbCache().setGzbCacheEndTime("2999-01-01 01:01:01")
                        .setGzbCacheKey(key)
                        .setGzbCacheNewTime(new DateTime().toString())
                        .setGzbCacheVal("0");
                dao.gzbCacheInsert(gc);
            }
            String tmp =gc.getGzbCacheVal();
            tmp = tmp == null || tmp.length() < 1 ? "0" : tmp;
            id = Integer.valueOf(tmp)+1;
            gc.setGzbCacheVal(String.valueOf(id));
            gc.update(dao);
        }catch (Exception e){
            Log.e(e);
            id=0;
        }finally {
            lock.unlock();
        }
        return id;
    }

    public String get(String key) {
        gzb.db.gzb_system.entity.GzbCache gc = dao.gzbCacheFind("select * from " + DataBase.gzbCacheName + " where gzb_cache_key='" +
                key + "' and gzb_cache_end_time > '" + new DateTime().toString() + "'", null);
        if (gc == null) {
            return null;
        }
        return gc.getGzbCacheVal();
    }

    public String get(String key, long mm) {
        gzb.db.gzb_system.entity.GzbCache gc = dao.gzbCacheFind("select * from " + DataBase.gzbCacheName + " where gzb_cache_key='" +
                key + "' and gzb_cache_end_time > '" + new DateTime().toString() + "'", null);
        if (gc == null) {
            return null;
        }
        if (mm > 0) {
            lock.lock();
            try {
                gc.setGzbCacheEndTime(mm == 0 ? "2999-01-01 01:01:01" : new DateTime(gc.getGzbCacheEndTime()).operation(1000 * mm).toString());
                dao.gzbCacheUpdate(gc);
            } finally {
                lock.unlock();
            }
        }
        return gc.getGzbCacheVal();
    }

    public void set(String key, String val) {
        set(key, val, 0);
    }

    public void set(String key, String val, long mm) {
        lock.lock();
        try {
            gzb.db.gzb_system.entity.GzbCache gc = dao.gzbCacheFind("select * from " + DataBase.gzbCacheName + " where gzb_cache_key='" +
                    key + "' and gzb_cache_end_time > '" + new DateTime().toString() + "'", null);
            if (gc == null) {
                gc = new gzb.db.gzb_system.entity.GzbCache().setGzbCacheEndTime(mm == 0 ? "2999-01-01 01:01:01" : new DateTime().operation(mm * 1000).toString())
                        .setGzbCacheKey(key)
                        .setGzbCacheNewTime(new DateTime().toString())
                        .setGzbCacheVal(val);
                dao.gzbCacheInsert(gc);
            } else {
                gc.setGzbCacheVal(val).setGzbCacheEndTime(mm == 0 ? "2999-01-01 01:01:01" : new DateTime().operation(mm * 1000).toString());
                dao.gzbCacheUpdate(gc);
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public String del(String key) {
        gzb.db.gzb_system.entity.GzbCache gc = dao.gzbCacheFind("select * from " + DataBase.gzbCacheName + " where gzb_cache_key='" +
                key + "' and gzb_cache_end_time > '" + new DateTime().toString() + "'", null);
        if (gc == null) {
            return null;
        }
        dao.gzbCacheDelete(gc);
        return gc.getGzbCacheVal();
    }


}