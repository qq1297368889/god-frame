package gzb.tools.entity;

public class AutoSqlEntity {
    public String sql ="";
    public Object[]objs;
    public AutoSqlEntity(String sql, Object[]objs){
        this.sql=sql;
        this.objs=objs;
    }
}
