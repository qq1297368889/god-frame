package gzb.tools.entity;

import java.util.concurrent.locks.Lock;

public class CacheEntity {
    private String val=null;
    private long useTime=0;
    private Lock lock=null;

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    public long getUseTime() {
        return useTime;
    }

    public void setUseTime(long useTime) {
        this.useTime = useTime;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

    @Override
    public String toString() {
        return "{" +
                "\"val:\"" + val +"\""+
                ", \"useTime:\"" + useTime +"\""+
                ", \"lock:\"" + lock +"\""+
                '}';
    }
}
