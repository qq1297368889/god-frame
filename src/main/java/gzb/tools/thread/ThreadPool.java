package gzb.tools.thread;

public class ThreadPool {
    public static void start(GzbThread gzbThread,String name,Boolean daemon,Integer num) {
        num=num==null?1:num;
        for (int i = 0; i < num; i++) {
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
                thread.setName(name+"-"+i);
            }
            thread.start();
            System.out.println(thread.getName()+",线程启动........");
        }

    }
    public static void start(GzbThread gzbThread) {
        start(gzbThread,null,null,1);
    }
    public static void start(GzbThread gzbThread,String name) {
        start(gzbThread,name,null,1);
    }
    public static void start(GzbThread gzbThread,String name,Boolean daemon) {
        start(gzbThread,name,daemon,1);
    }
 
}
