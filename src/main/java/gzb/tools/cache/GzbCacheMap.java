package gzb.tools.cache;

import gzb.tools.entity.CacheEntity;
import gzb.tools.DateTime;
import gzb.tools.log.LogImpl;
import gzb.tools.thread.GzbThread;
import gzb.tools.thread.ThreadPool;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class GzbCacheMap implements GzbCache {

    static gzb.tools.log.Log Log=new LogImpl(GzbCacheMap.class);
    private static Map<String, CacheEntity> map= new HashMap();
    private static final Lock lock = new ReentrantLock();

    static {
        ThreadPool.start(new GzbThread() {
            @Override
            public void start() throws InterruptedException {
                while (true) {
                    try {
                        lock.lock();
                        try {
                            int TimeStamp = new DateTime().toStampInt();
                            for (Iterator<Map.Entry<String, CacheEntity>> it = map.entrySet().iterator(); it.hasNext(); ) {
                                Map.Entry<String, CacheEntity> en = it.next();
                                if (en != null && en.getValue().getUseTime() + 0 > 0 && en.getValue().getUseTime() + 0 < TimeStamp) {
                                    System.out.println("缓存删除：" + en.getKey() + ",this:" + TimeStamp + ",use:" + en.getValue().getUseTime());
                                    it.remove();
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            lock.unlock();
                        }
                        Thread.sleep(1000 * 10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        },"GzbCacheMap",false);

    }

    @Override
    public Long getIncr(String key) {
        CacheEntity ce = map.get(key);
        if (ce == null) {
            lock.lock();
            try {
                ce = map.get(key);
                if (ce == null) {
                    ce = new CacheEntity();
                    ce.setVal("0");
                    ce.setUseTime(0);
                    ce.setLock(new ReentrantLock());
                    map.put(key, ce);
                }
            } catch (Exception e) {
                Log.e(e);
            } finally {
                lock.unlock();
            }
        }
        Long a = 0l;
        ce.getLock().lock();
        try {
            a = Long.valueOf(ce.getVal().toString()) + 1;
            ce.setVal(a.toString());
        } catch (Exception e) {
            Log.e(e);
        } finally {
            ce.getLock().unlock();
        }
        return a;
    }

    @Override
    public String get(String key) {
        CacheEntity ce = map.get(key);
        if (ce == null) {
            return null;
        }
        if (ce.getUseTime() + 0 > 0 && ce.getUseTime() + 0 < new DateTime().toStampInt()) {
            map.remove(key);
            return null;
        }
        return ce.getVal();
    }

    @Override
    public String get(String key, long mm) {
        CacheEntity ce = map.get(key);
        if (ce == null) {
            return null;
        }
        if (ce.getUseTime() + 0 > 0 && ce.getUseTime() + 0 < new DateTime().toStampInt()) {
            map.remove(key);
            return null;
        }
        ce.getLock().lock();
        try {
            ce.setUseTime(new DateTime().toStampInt() + mm);
        } catch (Exception e) {
            Log.e(e);
        } finally {
            ce.getLock().unlock();
        }
        return ce.getVal();
    }

    @Override
    public void set(String key, String val) {
        CacheEntity ce = map.get(key);
        if (ce == null) {
            lock.lock();
            try {
                ce = map.get(key);
                if (ce == null) {
                    ce = new CacheEntity();
                    ce.setVal(val);
                    ce.setUseTime(0);
                    ce.setLock(new ReentrantLock());
                    map.put(key, ce);
                }
            } catch (Exception e) {
                Log.e(e);
            } finally {
                lock.unlock();
            }
        }
        ce.getLock().lock();
        try {
            ce.setVal(val);
            ce.setUseTime(0);
        } catch (Exception e) {
            Log.e(e);
        } finally {
            ce.getLock().unlock();
        }
    }

    @Override
    public void set(String key, String val, long mm) {
        CacheEntity ce = map.get(key);
        if (ce == null) {
            lock.lock();
            try {
                ce = map.get(key);
                if (ce == null) {
                    ce = new CacheEntity();
                    ce.setVal(val);
                    ce.setUseTime(new DateTime().toStampInt() + mm);
                    ce.setLock(new ReentrantLock());
                    map.put(key, ce);
                }
            } catch (Exception e) {
                Log.e(e);
            } finally {
                lock.unlock();
            }
        }
        ce.getLock().lock();
        try {
            ce.setVal(val);
            ce.setUseTime(new DateTime().toStampInt() + mm);
        } catch (Exception e) {
            Log.e(e);
        } finally {
            ce.getLock().unlock();
        }
    }

    @Override
    public String del(String key) {
        CacheEntity ce = map.get(key);
        if (ce == null) {
            return null;
        }
        ce.getLock().lock();
        try {
            return map.remove(key).getVal();
        } catch (Exception e) {
            Log.e(e);
        } finally {
            ce.getLock().unlock();
        }
        return null;
    }
}
