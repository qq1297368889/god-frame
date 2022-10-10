package gzb.tools.pressure;

import com.sun.net.httpserver.HttpServer;
import gzb.tools.http.HTTP;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
    static Map<String, String> map = new ConcurrentHashMap<>();
    static long[] req_res;
    static boolean runState = false;

    public static void main(String[] args) throws InterruptedException {
        //http://0088.run/ecSystem/page/index.jsp
        runState = false;
        pressure1("http://192.168.10.104:8080/api/system/readUserInfo", null, 10000, "\"state\":\"1\",");
        //pressure1("http://0088.run/ecSystem/apiAdminListScript?page=1&limit=10", null, 5, "\"state\":\"1\",");
    }
/*103
    请求成功：100/100
    请求失败：0/100
    请求最快：40毫秒
    请求最慢：101毫秒
    请求平均：73毫秒
    请求次数：100，总耗时：103毫秒
1648
    请求成功：5000/5000
    请求失败：0/5000
    请求最快：2毫秒
    请求最慢：1633毫秒
    请求平均：1079毫秒
    请求次数：5000，总耗时：1648毫秒*/
    /**
     * 压力测试 模式1 并发请求  num个线程 同时访问url 一次 打印每次请求的响应时间
     *
     * @param url     请求地址
     * @param data    post参数 本参数不为null  则默认为 post请求 否则 默认为 get请求 忽略本参数
     * @param num     请求次数
     * @param res_suc 预期成功结果
     */

    public static final void pressure1(String url, String data, int num, String res_suc) throws InterruptedException {
        req_res = new long[num];
        map = new ConcurrentHashMap<>();
        new HTTP(); //提前 初始化下 因为 ssl错误证书 耗时

        for (int i = 0; i < num; i++) {
            int id = i;
            new Thread() {
                @Override
                public void run() {
                    long a = 0;
                    long b = 0;
                    String res_str;
                    HTTP http = new HTTP();
                    http.addCookies("session", "1665164749286FRZ0eWsPQ0baUSqtgKC");
                    http.addCookies("loginTok", "TjDk0ggxQHir-mjzb9UK1R89TRY-2lebqjUg7EP/sv8yVa2dX44jRS4EzqHcTCsv6v9J6uqI2nyPe4E-vBRGo48Dh6Exk9k6IT-HRa2rLLf2zKYL7FRToeQTiCrs/FJw");
                    while (runState == false) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    a = new Date().getTime();
                    if (data == null) {
                        res_str = http.httpGet(url).toString();
                    } else {
                        res_str = http.httpPost(url, data).toString();
                    }
                    b = new Date().getTime();
                    req_res[id] = b - a;
                    if (res_str.indexOf(res_suc) > -1) {
                        map.put(id + "", "线程：" + id + "=请求成功,请求耗时：" + (b - a) + "毫秒");
                    } else {
                        map.put(id + "", "线程：" + id + "=请求失败,请求耗时：" + (b - a) + "毫秒");
                    }
                }
            }.start();
        }

        Thread.sleep(1000 * 5);
        runState = true;
        System.out.println("开始请求");
        long a1 = new Date().getTime();
        while (map.size() < num) {
            Thread.sleep(10);
        }
        long b1 = new Date().getTime();
        long max = 0, min = 0, all = 0, ok=0,err=0;
        for (int i = 0; i < req_res.length; i++) {
            if (max == 0 || req_res[i] > max) {
                max = req_res[i];
            }
            if (min == 0 || req_res[i] < min) {
                min = req_res[i];
            }
            all += req_res[i];
            if (map.get(i + "").indexOf("成功")>-1){
                ok++;
            }else{
                err++;
            }
        }
        System.out.println("请求成功：" + ok + "/"+ req_res.length);
        System.out.println("请求失败：" + err + "/"+ req_res.length);
        System.out.println("请求最快：" + min + "毫秒");
        System.out.println("请求最慢：" + max + "毫秒");
        System.out.println("请求平均：" + (all / req_res.length) + "毫秒");
        System.out.println("请求次数：" + req_res.length + "，总耗时：" + (b1 - a1) + "毫秒");
    }
}
