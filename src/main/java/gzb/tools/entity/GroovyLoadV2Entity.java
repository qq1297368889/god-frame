package gzb.tools.entity;

import java.util.List;
import java.util.Map;

public class GroovyLoadV2Entity {
    public String path;
    public Class aClass;
    public long updateTime;
    //参数信息 方法名 参数类型列表
    public Map<String, List<String>> parameterName;
    public Map<String, Class[]> parameterType;

    public GroovyLoadV2Entity(String path, Class aClass, long updateTime, Map<String, List<String>> parameterName, Map<String, Class[]> parameterType) {
        this.path = path;
        this.aClass = aClass;
        this.updateTime = updateTime;
        this.parameterName = parameterName;
        this.parameterType = parameterType;

    }
}
