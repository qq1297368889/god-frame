package gzb.tools.tcp;


import gzb.tools.tcp.entity.SessionParameter;

public class ServerSend extends Thread{
    SessionParameter sp;

    public SessionParameter getSp() {
        return sp;
    }

    public void setSp(SessionParameter sp) {
        this.sp = sp;
    }

    @Override
    public void run() {
        System.out.println("启动线程："+getName());
        try {
            while (true) {
                if (sp.serverSendThreadState == -1) {
                    break;
                }
                if (sp.smgList.size() > 0) {
                    String data=sp.smgList.remove(0);
                    System.out.println("回复：" + data);
                    sp.bufferedWriter.write(data);
                    sp.bufferedWriter.flush();
                }
                sleep(10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            System.out.println("线程结束："+getName());
        }
    }
}
