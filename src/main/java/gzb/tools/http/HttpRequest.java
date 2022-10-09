package gzb.tools.http;

import gzb.tools.Tools;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpRequest {
    //    网址
    private String url;
    //    类型[POST,GET]
    private String mode;
    //    协议头
    private HashMap<String, String> headers;
    //    cookie
    private String cookies;
    //    提交数据
    private String parameter;

    //    返回数据
    public String data;
    //    返回数据
    public byte[] byteData;
    public String encode="UTF-8";
    public HttpRequest(){

    }
    public HttpRequest(String url, String mode, String parameter, HashMap<String, String> headers, String cookies,String encode) {
        this.encode=encode==null?"UTF-8":encode;
        /**
         * url
         * 访问类型
         * 协议头
         * cookie
         * 提交数据
         */
        this.url = url;
        this.mode = mode;
        this.headers = headers;
        this.cookies = cookies;
        this.parameter = parameter;
        if (mode.equals("POST")) {
            this.httpPost(this.url, this.parameter, this.headers, this.cookies,encode);
        } else if (mode.equals("GET")) {
            this.httpGet(this.url, this.parameter, this.headers, this.cookies,encode);
        } else if (mode.equals("POST_BYTE")) {
            this.httpPostByte(this.url, this.parameter, this.headers, this.cookies,encode);
        }


    }

    public String getCookies() {
        return this.cookies;
    }


    public HttpRequest httpGet(String url){
        return httpGet(url,null,null,null,null);
    }
    public HttpRequest httpPost(String url, String param){
        return httpPost(url,param,null,null,null);
    }
    public HttpRequest httpGet(String url, String param){
        return httpPostByte(url,param,null,null,null);
    }
    /**
     * 向指定URL发送GET方式的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数
     **/
    public HttpRequest httpGet(String url, String param, HashMap<String, String> headers, String cookie,String encode) {
        this.encode=encode==null?"UTF-8":encode;
        StringBuilder result = new StringBuilder();
        String urlName = url + (param==null?"": "?"+ param);
        try {
            URL realUrl = new URL(urlName);
            SslUtils.ignoreSsl();
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            if (cookie != null) {
                conn.setRequestProperty("Cookie", cookie);
            }

            //建立实际的连接
            conn.connect();
            //获取所有的响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            //遍历所有的响应头字段
//            for (String key : map.keySet()) {
//                System.out.println(key + "-->" + map.get(key));
//            }
            // 定义 BufferedReader输入流来读取URL的响应
            InputStream inputStream=conn.getInputStream();
            this.byteData=inputStream.readAllBytes();
            //取cookie
            StringBuilder sessionId = new StringBuilder();
            String key = null;
            for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
                if (key.equalsIgnoreCase("set-cookie")) {
                    sessionId.append(conn.getHeaderField(i)).append(";");
                }
            }
            this.cookies = sessionId.toString();
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常" + e);
            e.printStackTrace();
        }

        return this;
    }


    /**
     * 向指定URL发送POST方式的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数
     */
    public HttpRequest httpPost(String url, String param, HashMap<String, String> headers, String cookie,String encode) {
        this.encode=encode==null?"UTF-8":encode;
        StringBuilder result = new StringBuilder();
        String line;
        try {
            URL realUrl = new URL(url);
            SslUtils.ignoreSsl();
            //打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            //设置通用的请求属性
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String k = entry.getKey();
                    String v = entry.getValue();
                    conn.addRequestProperty(k, v);
                }
            }
            if (cookie != null) {
                conn.setRequestProperty("Cookie", cookie);
            }
            conn.setDoOutput(true);
            conn.setDoInput(true);
            //获取URLConnection对象对应的输出流
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            System.out.println(param);
            out.print(param);
            //flush输出流的缓冲
            out.flush();

            // 定义 BufferedReader输入流来读取URL的响应
            InputStream inputStream=conn.getInputStream();
            this.byteData=inputStream.readAllBytes();
            //取cookie
            String sessionId = "";
            String cookieVal = "";
            String key = null;
            for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
                if (key.equalsIgnoreCase("set-cookie")) {
                    cookieVal = conn.getHeaderField(i);
                    sessionId = sessionId + cookieVal + ";";
                }
            }
            this.cookies = sessionId;
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常" + e);
            e.printStackTrace();
        }

        this.data = result.toString();
        return this;
    }

    /**
     * 向指定URL发送POST方式的请求
     *
     * @param url   发送请求的URL
     * @param param 请求参数
     */
    public HttpRequest httpPostByte(String url, String param, HashMap<String, String> headers, String cookie,String encode) {
        this.encode=encode==null?"UTF-8":encode;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url);
            SslUtils.ignoreSsl();
            //打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            //设置通用的请求属性
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    String k = entry.getKey();
                    String v = entry.getValue();
                    conn.addRequestProperty(k, v);
                }
            }
            if (cookie != null) {
                conn.setRequestProperty("Cookie", cookie);
            }
            conn.setDoOutput(true);
            conn.setDoInput(true);
            if (param == null) {
                param = "";
            }
            //获取URLConnection对象对应的输出流
            DataOutputStream out = new DataOutputStream(
                    conn.getOutputStream());
            // 发送请求参数
            out.writeBytes(param);
            //flush输出流的缓冲
            out.flush();
            // 定义 BufferedReader输入流来读取URL的响应
            InputStream inputStream=conn.getInputStream();
            this.byteData=inputStream.readAllBytes();
            //取cookie
            String sessionId = "";
            String cookieVal = "";
            String key = null;
            for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
                if (key.equalsIgnoreCase("set-cookie")) {
                    cookieVal = conn.getHeaderField(i);
                    sessionId = sessionId + cookieVal + ";";
                }
            }
            this.cookies = sessionId;
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常" + e);
            e.printStackTrace();
        }

        this.data = result.toString();
        return this;
    }

    @Override
    public String toString() {
        try {
            return new String(this.byteData,this.encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    public byte[] toByte() {
        return this.byteData;
    }
    public File toFile(String filePath,String fileName) throws IOException {
        File file=new File(filePath);
        file.mkdirs();
        String newPath=null;
        filePath=filePath.replaceAll("\\\\","/");
        if (filePath.substring(filePath.length()-1,filePath.length()).equals("/")){
            newPath=filePath;
        }else{
            newPath=filePath+"/";
        }
        file=new File(filePath+fileName);
        Tools.fileSaveByte(file,this.byteData,false);
        return file;
    }

}
