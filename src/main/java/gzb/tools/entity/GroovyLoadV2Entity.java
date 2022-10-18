package gzb.tools.entity;

import groovy.lang.GroovyObject;

import java.util.List;
import java.util.Map;

public class GroovyLoadV2Entity {
    public String path;
    public Class aClass;
    public Object obj;
    public long updateTime;
    public String contentType;
    public boolean crossDomain;
    public boolean single;
    //参数信息 方法名 参数类型列表
    public Map<String, List<String>> parameterName;
    public Map<String, Class[]> parameterType;

    public GroovyLoadV2Entity(String path, Class aClass, Object obj, long updateTime,
                              Map<String, List<String>> parameterName, Map<String, Class[]> parameterType,
                              String contentType,boolean crossDomain,boolean single) {
        this.path = path;
        this.aClass = aClass;
        this.updateTime = updateTime;
        this.parameterName = parameterName;
        this.parameterType = parameterType;
        this.contentType = contentType;
        this.crossDomain = crossDomain;
        this.obj = obj;
        this.single = single;
    }
}
