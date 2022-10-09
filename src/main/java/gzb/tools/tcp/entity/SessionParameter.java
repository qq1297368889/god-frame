package gzb.tools.tcp.entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SessionParameter {
    public int serverSendThreadState = 0;//默认0 运行  等于 -1 停止
    public List<String> smgList = new CopyOnWriteArrayList<>();
    public Socket socket;
    public BufferedReader bufferedReader;
    public BufferedWriter bufferedWriter;
    public String ip;
    public int port;
    public String user;
}
