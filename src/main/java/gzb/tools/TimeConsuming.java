package gzb.tools;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class TimeConsuming {
    static Map<String,Long> map=new ConcurrentHashMap<>();
    public static final void start(String key){
        map.put(key,new Date().getTime());
    }
    public static final String end(String key){
        return "耗时：["+(new Date().getTime()-map.get(key))+"]";
    }
    public static final void endPrint(String key){
        System.out.println("耗时：["+(new Date().getTime()-map.get(key))+"]");
    }
}
