package gzb.tools.cache;

public interface GzbCache {
    //根据key  获取一个唯一自增数字
    public Long getIncr(String key);

    //根据key  获取值
    public String get(String key);

    //根据key  获取值 并修改失效时间为当前时间+mm
    public String get(String key, long mm);

    //根据key  修改或新增值
    public void set(String key, String val);

    //根据key  修改或新增值 并修改或指定失效时间 为当前时间+mm
    public void set(String key, String val, long mm);

    //根据key  删除
    public String del(String key);

}
