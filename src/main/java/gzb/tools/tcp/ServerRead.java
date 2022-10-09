package gzb.tools.tcp;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gzb.tools.tcp.entity.SendInfo;
import gzb.tools.tcp.entity.SessionParameter;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServerRead extends Thread {
    SessionParameter sp = new SessionParameter();

    @Override
    public void run() {
        System.out.println("启动线程：" + getName());
        try {
            open();
            String data = "";
            while ((data = this.sp.bufferedReader.readLine()) != null) {
                cpu(data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
            System.out.println("线程结束：" + getName());
        }
    }

    public void cpu(String json) {
        System.out.println("请求：" + json);
        JsonParser jp = new JsonParser();
        JsonObject jsonObject = jp.parse(json).getAsJsonObject();
        if (jsonObject.get("api").getAsString().equals("ping")) {
            ping(jsonObject.get("data").getAsString());
        }
    }

    public Socket getSocket() {
        return this.sp.socket;
    }

    public void setSocket(Socket socket) throws IOException {
        this.sp.socket = socket;
        socket.setSoTimeout(10000);
        this.sp.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.sp.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.sp.ip = socket.getInetAddress().getHostAddress();
        this.sp.port = socket.getPort();
        this.sp.user = this.sp.ip + ":" + this.sp.port;
    }

    public void open() throws IOException {
        Server.socketMap.put(this.sp.user, this.sp.socket);
        System.out.println("连接创建>>" + this.sp.user);
        ServerSend serverSend = new ServerSend();
        serverSend.setSp(sp);
        Thread thread = new Thread(serverSend);
        thread.setName("thread-" + sp.socket.getInetAddress().getHostAddress() + ":" + sp.socket.getPort() + "-send");
        thread.start();
    }

    public void close() {
        try {
            System.out.println("连接断开>>" + this.sp.user);
            Server.socketMap.remove(this.sp.user);
            this.sp.serverSendThreadState = -1;
            this.sp.bufferedWriter.close();
            this.sp.bufferedWriter.close();
            this.sp.socket.close();
            this.sp.socket.close();
        } catch (Exception e) {

        }

    }

    public void send(String msg) {
        this.sp.smgList.add(msg);
    }

    public void ping(String base64Data) {
        send("{\"state\":\"1\"}");
    }

}
