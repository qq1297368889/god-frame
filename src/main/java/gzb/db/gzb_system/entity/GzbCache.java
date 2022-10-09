package gzb.db.gzb_system.entity;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.tools.Tools;
import gzb.tools.ListPage;
import gzb.tools.entity.AutoSqlEntity;
import java.util.ArrayList;
import java.util.List;
public class GzbCache {
    private java.lang.Long gzbCacheId;
    private java.lang.String gzbCacheKey;
    private java.lang.String gzbCacheVal;
    private java.lang.String gzbCacheEndTime;
    private java.lang.String gzbCacheNewTime;
    private java.lang.String gzbCacheIdOperation="=";
    private java.lang.String gzbCacheKeyOperation="=";
    private java.lang.String gzbCacheValOperation="=";
    private java.lang.String gzbCacheEndTimeOperation="=";
    private java.lang.String gzbCacheNewTimeOperation="=";
    private java.util.List<?>list;
    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
    public GzbCache() {
    }
    public GzbCache(String json) {
        String tmp;
        tmp = Tools.textMid(json, "\"gzbCacheId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbCacheId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbCacheIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbCacheIdOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbCacheKey\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbCacheKey = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbCacheKeyOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbCacheKeyOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbCacheVal\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbCacheVal = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbCacheValOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbCacheValOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbCacheEndTime\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbCacheEndTime = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbCacheEndTimeOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbCacheEndTimeOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbCacheNewTime\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbCacheNewTime = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbCacheNewTimeOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbCacheNewTimeOperation = tmp;}
    }
    @Override
    public String toString() {
        return toJson();
    }
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (gzbCacheId != null){sb.append("\"gzbCacheId\":\"").append(gzbCacheId).append("\",");}
        if (gzbCacheKey != null){sb.append("\"gzbCacheKey\":\"").append(gzbCacheKey).append("\",");}
        if (gzbCacheVal != null){sb.append("\"gzbCacheVal\":\"").append(gzbCacheVal).append("\",");}
        if (gzbCacheEndTime != null){sb.append("\"gzbCacheEndTime\":\"").append(gzbCacheEndTime).append("\",");}
        if (gzbCacheNewTime != null){sb.append("\"gzbCacheNewTime\":\"").append(gzbCacheNewTime).append("\"");}
        if (list != null){sb.append(",\"data\":").append(list.toString()).append(",");}
        if (sb.substring(sb.length()-1,sb.length()).equals(","))sb.delete(sb.length()-1,sb.length()).equals(",");
        sb.append("}");
        return sb.toString();
    }
    public java.lang.Long getGzbCacheId() {
        return this.gzbCacheId;
    }
    public GzbCache setGzbCacheId(java.lang.Long gzbCacheId) {
        this.gzbCacheId = gzbCacheId;
        return this;
    }
    public GzbCache setGzbCacheId(java.lang.Long gzbCacheId,java.lang.String gzbCacheIdOperation) {
        this.gzbCacheId = gzbCacheId;
        this.gzbCacheIdOperation = gzbCacheIdOperation;
        return this;
    }
    public java.lang.String getGzbCacheKey() {
        return this.gzbCacheKey;
    }
    public GzbCache setGzbCacheKey(java.lang.String gzbCacheKey) {
        this.gzbCacheKey = gzbCacheKey;
        return this;
    }
    public GzbCache setGzbCacheKey(java.lang.String gzbCacheKey,java.lang.String gzbCacheKeyOperation) {
        this.gzbCacheKey = gzbCacheKey;
        this.gzbCacheKeyOperation = gzbCacheKeyOperation;
        return this;
    }
    public java.lang.String getGzbCacheVal() {
        return this.gzbCacheVal;
    }
    public GzbCache setGzbCacheVal(java.lang.String gzbCacheVal) {
        this.gzbCacheVal = gzbCacheVal;
        return this;
    }
    public GzbCache setGzbCacheVal(java.lang.String gzbCacheVal,java.lang.String gzbCacheValOperation) {
        this.gzbCacheVal = gzbCacheVal;
        this.gzbCacheValOperation = gzbCacheValOperation;
        return this;
    }
    public java.lang.String getGzbCacheEndTime() {
        return this.gzbCacheEndTime;
    }
    public GzbCache setGzbCacheEndTime(java.lang.String gzbCacheEndTime) {
        this.gzbCacheEndTime = gzbCacheEndTime;
        return this;
    }
    public GzbCache setGzbCacheEndTime(java.lang.String gzbCacheEndTime,java.lang.String gzbCacheEndTimeOperation) {
        this.gzbCacheEndTime = gzbCacheEndTime;
        this.gzbCacheEndTimeOperation = gzbCacheEndTimeOperation;
        return this;
    }
    public java.lang.String getGzbCacheNewTime() {
        return this.gzbCacheNewTime;
    }
    public GzbCache setGzbCacheNewTime(java.lang.String gzbCacheNewTime) {
        this.gzbCacheNewTime = gzbCacheNewTime;
        return this;
    }
    public GzbCache setGzbCacheNewTime(java.lang.String gzbCacheNewTime,java.lang.String gzbCacheNewTimeOperation) {
        this.gzbCacheNewTime = gzbCacheNewTime;
        this.gzbCacheNewTimeOperation = gzbCacheNewTimeOperation;
        return this;
    }
    public AutoSqlEntity toWhere(String sql){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append(sql+" where ");
        if (this.gzbCacheId !=null){sb.append("gzb_cache_id ").append(gzbCacheIdOperation).append(" ? and ");list.add(this.gzbCacheId);}
        if (this.gzbCacheKey !=null){sb.append("gzb_cache_key ").append(gzbCacheKeyOperation).append(" ? and ");list.add(this.gzbCacheKey);}
        if (this.gzbCacheVal !=null){sb.append("gzb_cache_val ").append(gzbCacheValOperation).append(" ? and ");list.add(this.gzbCacheVal);}
        if (this.gzbCacheEndTime !=null){sb.append("gzb_cache_end_time ").append(gzbCacheEndTimeOperation).append(" ? and ");list.add(this.gzbCacheEndTime);}
        if (this.gzbCacheNewTime !=null){sb.append("gzb_cache_new_time ").append(gzbCacheNewTimeOperation).append(" ? and ");list.add(this.gzbCacheNewTime);}
        if (sb.substring(sb.length()-5,sb.length()).equals(" and "))sb.delete(sb.length()-5,sb.length());
        if (sb.substring(sb.length()-6,sb.length()).equals("where "))sb.delete(sb.length()-6,sb.length());
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toSelect(){
        return toWhere("select * from "+DataBase.gzbCacheName);
    }
    public AutoSqlEntity toDelete(){
        return toWhere("delete from "+DataBase.gzbCacheName);
    }
    public AutoSqlEntity toUpdate(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("update "+DataBase.gzbCacheName+" set ");
        if (this.gzbCacheId !=null){sb.append("gzb_cache_id=?, ");list.add(this.gzbCacheId);}
        if (this.gzbCacheKey !=null){sb.append("gzb_cache_key=?, ");list.add(this.gzbCacheKey);}
        if (this.gzbCacheVal !=null){sb.append("gzb_cache_val=?, ");list.add(this.gzbCacheVal);}
        if (this.gzbCacheEndTime !=null){sb.append("gzb_cache_end_time=?, ");list.add(this.gzbCacheEndTime);}
        if (this.gzbCacheNewTime !=null){sb.append("gzb_cache_new_time=?, ");list.add(this.gzbCacheNewTime);}
        if (sb.substring(sb.length()-2,sb.length()).equals(", "))sb.delete(sb.length()-2,sb.length()-1);
        if (sb.substring(sb.length()-4,sb.length()).equals("set "))sb.delete(sb.length()-4,sb.length());
        sb.append("where gzb_cache_id=?");list.add(this.gzbCacheId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toInsert(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("insert into "+DataBase.gzbCacheName+"(gzb_cache_id,gzb_cache_key,gzb_cache_val,gzb_cache_end_time,gzb_cache_new_time) values(?,?,?,?,?)");
        list.add(this.gzbCacheId);
        list.add(this.gzbCacheKey);
        list.add(this.gzbCacheVal);
        list.add(this.gzbCacheEndTime);
        list.add(this.gzbCacheNewTime);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public GzbCache find(BaseDao dao) {
        return dao.gzbCacheFind(this);
    }

    public GzbCache findCache(BaseDao dao, int mm) {
        return dao.gzbCacheFindCache(this, mm);
    }

    public List<GzbCache> query(BaseDao dao) {
        return dao.gzbCacheQuery(this);
    }

    public List<GzbCache> queryCache(BaseDao dao, int mm) {
        return dao.gzbCacheQueryCache(this, mm);
    }

    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {
        return dao.gzbCacheQuery(this, page, limit, maxPage, maxLimit);
    }

    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {
        return dao.gzbCacheQueryCache(this, page, limit, maxPage, maxLimit, mm);
    }

    public int save(BaseDao dao) {
        return dao.gzbCacheInsert(this);
    }

    public int update(BaseDao dao) {
        return dao.gzbCacheUpdate(this);
    }

    public int delete(BaseDao dao) {
        return dao.gzbCacheDelete(this);
    }

    public int saveAsy(BaseDao dao) {
        return dao.gzbCacheInsertAsy(this);
    }

    public int updateAsy(BaseDao dao) {
        return dao.gzbCacheUpdateAsy(this);
    }

    public int deleteAsy(BaseDao dao) {
        return dao.gzbCacheDeleteAsy(this);
    }
}