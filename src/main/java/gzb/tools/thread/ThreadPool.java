package gzb.tools.thread;

public class ThreadPool {
    public static void start(GzbThread gzbThread,String name,Boolean daemon) {
        Thread thread = new Thread(){
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
        if (daemon!=null){
            thread.setDaemon(daemon);
        }
        if (name!=null){
            thread.setName(name);
        }
        thread.start();
        System.out.println(thread.getName()+",线程启动........");
    }
    public static void start(GzbThread gzbThread) {
        start(gzbThread,null,null);
    }
    public static void start(GzbThread gzbThread,String name) {
        start(gzbThread,name,null);
    }

}
