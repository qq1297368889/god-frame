package gzb.tools.http;

import gzb.tools.Tools;
import gzb.tools.log.LogImpl;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTP {

    static gzb.tools.log.Log Log=new LogImpl(HTTP.class);
    Map<String, String> cookies = new HashMap<>();
    Map<String, String> headers = new HashMap<>();
    byte[] bytes;

    public static void main(String[] args) throws Exception {
        HTTP http = new HTTP();
        ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        ScriptEngine engine = scriptEngineManager.getEngineByName("JavaScript");
        List<String> list = Arrays.asList("element1", "element2", "element3");
        engine.put("http", http);
        String script = "print('the size of javaList is ' + javaList.size());\n" +
                "print('javaList elements:');" +
                "for (i in javaList) {\n" +
                "print(javaList[i]);\n" +
                "}";
        engine.eval(script);
        System.out.println("===============================================");

    }
    public HTTP() {
        chromeHeaders();
    }

    public HTTP(Map<String, String> cookies, Map<String, String> headers) {
        this.cookies = cookies;
        this.headers = headers;
    }

    public HTTP(String cookieStr, Map<String, String> headers) {
        addCookies(cookieStr);
        if (headers==null){
            chromeHeaders();
        }else{
            this.headers = headers;
        }
    }


    //设置为 谷歌的协议头 ua
    public void chromeHeaders(){
        headers=new HashMap<>();
        headers.put("accept","application/json, text/javascript, */*; q=0.01");
        headers.put("user-agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/104.0.0.0 Safari/537.36");
        headers.put("x-requested-with","XMLHttpRequest");
    }
    //cookie 序列化成字符串
    public String cookieToString() {
        String all = "";
        if (cookies != null) {
            for (Map.Entry<String, String> entry : cookies.entrySet()) {
                all += entry.getKey() + "=" + entry.getValue() + "; ";
            }
        }
        return all;
    }

    //添加cookie 字符串 支持多个
    public HTTP addCookies(String cookieStr) {
        cookieStr = cookieStr.replaceAll("; ", ";");
        String[] ss1 = cookieStr.split(";");
        cookies = cookies == null ? new HashMap<>() : cookies;
        for (int i = 0; i < ss1.length; i++) {
            String[] ss2 = ss1[i].split("=", 2);
            if (ss2.length == 2) {
                cookies.put(ss2[0], ss2[1]);
            }
        }
        return this;
    }

    //添加一个 k，v形式的  cookie
    public HTTP addCookies(String k, String v) {
        cookies = cookies == null ? new HashMap<>() : cookies;
        cookies.put(k, v);
        return this;
    }


    /**
     * 参数1 请求地址
     * 参数2 请求参数 post模式下 data有效 get模式 无视data
     * 参数3 访问方式 1 post ,11 post byte, 0get
     */
    public HTTP request(String url, String data, int met) {
        try {
            SslUtils.ignoreSsl();
            URLConnection conn = new URL(url).openConnection();
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    conn.addRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            conn.setRequestProperty("cookie", cookieToString());
            if (met == 1) {
                //POST
                conn.setDoOutput(true);
                conn.setDoInput(true);
                PrintWriter out = new PrintWriter(conn.getOutputStream());
                out.print(data);
                out.flush();
            } else if (met == 11) {
                //POST BYTE
                conn.setDoOutput(true);
                conn.setDoInput(true);
                DataOutputStream out = new DataOutputStream(conn.getOutputStream());
                out.writeBytes(data);
                out.flush();
            } else {
                conn.connect();
            }
            InputStream inputStream = conn.getInputStream();
            this.bytes = new byte[inputStream.available()];
            inputStream.read(this.bytes);
            String key = null;
            for (int i = 1; (key = conn.getHeaderFieldKey(i)) != null; i++) {
                if (key.equalsIgnoreCase("set-cookie")) {
                    addCookies(conn.getHeaderField(i));
                }
            }
            return this;
        } catch (Exception e) {
            Log.i("http访问出现异常：" + url + "  " + data);
        }
        return null;
    }

    public HTTP httpGet(String url) {
        return request(url, null, 0);
    }

    public HTTP httpPost(String url, String data) {
        return request(url, data, 1);
    }
    public HTTP httpPostByte(String url, String data) {
        return request(url, data, 11);
    }
    public String httpGetString(String url) {
        return httpGet(url).toString();
    }
    public String httpPostString(String url, String data) {
        return httpPost(url,data).toString();
    }
    public String httpPostByteString(String url, String data) {
        return httpPostByte(url,data).toString();
    }


    @Override
    public String toString() {
        return new String(this.bytes);
    }
    public byte[] toByte() {
        return this.bytes;
    }
    public File toFile(String filePath, String fileName) throws IOException {
        File file=new File(filePath);
        file.mkdirs();
        String newPath=null;
        filePath=filePath.replaceAll("\\\\","/");
        if (filePath.substring(filePath.length()-1,filePath.length()).equals("/")==false){
            newPath=filePath+"/";
        }
        file=new File(filePath+fileName);
        Tools.fileSaveByte(file,this.bytes,false);
        return file;
    }
}
