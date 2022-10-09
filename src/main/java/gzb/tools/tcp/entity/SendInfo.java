package gzb.tools.tcp.entity;

import java.io.BufferedWriter;
import java.net.Socket;

public class SendInfo {
    Socket socket;
    String msg;
    BufferedWriter bufferedWriter;

    public BufferedWriter getBufferedWriter() {
        return bufferedWriter;
    }

    public void setBufferedWriter(BufferedWriter bufferedWriter) {
        this.bufferedWriter = bufferedWriter;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
