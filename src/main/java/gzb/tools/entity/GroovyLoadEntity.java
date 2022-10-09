package gzb.tools.entity;

public class GroovyLoadEntity {
    public String path = null;
    public Class class1 = null;
    public long updateTime = 0;

    public GroovyLoadEntity(String path, Class class1, long updateTime) {
        this.path = path;
        this.class1 = class1;
        this.updateTime = updateTime;
    }
}
