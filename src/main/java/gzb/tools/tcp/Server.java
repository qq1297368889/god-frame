package gzb.tools.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    public static Map<String, Socket> socketMap = new ConcurrentHashMap<>();
    ServerSocket serverSocket;

    public static void main(String[] args) {
        Server server = new Server();
        server.start(10001);
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("启动服务器[" + port + "]....");

            while (true) {
                Socket socket = serverSocket.accept();
                ServerRead serverRead = new ServerRead();
                serverRead.setSocket(socket);
                Thread thread = new Thread(serverRead);
                thread.setName("thread-"+socket.getInetAddress().getHostAddress()+":"+socket.getPort()+"-read");
                thread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
