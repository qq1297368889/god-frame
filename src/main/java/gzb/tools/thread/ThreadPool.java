package gzb.tools.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    int poolMax = 100;
    GzbThread[] gzbThreads = new GzbThread[poolMax];
    int[] states = new int[poolMax];//线程状态 0无任务 1任务执行中

    public ThreadPool() {

    }

    public void startThread(GzbThread gzbThread) {

    }

    public static void start(GzbThread gzbThread, String name, Boolean daemon, Integer num) {
        num = num == null ? 1 : num;
        for (int i = 0; i < num; i++) {
            Thread thread = new Thread() {
                @Override
                public void run() {
                    try {
                        gzbThread.start();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            if (daemon != null) {
                thread.setDaemon(daemon);
            }
            if (name != null) {
                thread.setName(name + "-" + i);
            }
            thread.start();
            System.out.println(thread.getName() + ",线程启动........");
        }

    }

    public static void start(GzbThread gzbThread) {
        start(gzbThread, null, null, 1);
    }

    public static void start(GzbThread gzbThread, String name) {
        start(gzbThread, name, null, 1);
    }

    public static void start(GzbThread gzbThread, String name, Boolean daemon) {
        start(gzbThread, name, daemon, 1);
    }

}
