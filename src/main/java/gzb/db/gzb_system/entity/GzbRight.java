package gzb.db.gzb_system.entity;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.tools.Tools;
import gzb.tools.ListPage;
import gzb.tools.entity.AutoSqlEntity;
import java.util.ArrayList;
import java.util.List;
public class GzbRight {
    private java.lang.Long gzbRightId;
    private java.lang.Long gzbRightUsersId;
    private java.lang.Long gzbRightApiId;
    private java.lang.String gzbRightIdOperation="=";
    private java.lang.String gzbRightUsersIdOperation="=";
    private java.lang.String gzbRightApiIdOperation="=";
    private java.util.List<?>list;
    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
    public GzbRight() {
    }
    public GzbRight(String json) {
        String tmp;
        tmp = Tools.textMid(json, "\"gzbRightId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbRightId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbRightIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbRightIdOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbRightUsersId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbRightUsersId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbRightUsersIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbRightUsersIdOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbRightApiId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbRightApiId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbRightApiIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbRightApiIdOperation = tmp;}
    }
    @Override
    public String toString() {
        return toJson();
    }
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (gzbRightId != null){sb.append("\"gzbRightId\":\"").append(gzbRightId).append("\",");}
        if (gzbRightUsersId != null){sb.append("\"gzbRightUsersId\":\"").append(gzbRightUsersId).append("\",");}
        if (gzbRightApiId != null){sb.append("\"gzbRightApiId\":\"").append(gzbRightApiId).append("\"");}
        if (list != null){sb.append(",\"data\":").append(list.toString()).append(",");}
        if (sb.substring(sb.length()-1,sb.length()).equals(","))sb.delete(sb.length()-1,sb.length()).equals(",");
        sb.append("}");
        return sb.toString();
    }
    public java.lang.Long getGzbRightId() {
        return this.gzbRightId;
    }
    public GzbRight setGzbRightId(java.lang.Long gzbRightId) {
        this.gzbRightId = gzbRightId;
        return this;
    }
    public GzbRight setGzbRightId(java.lang.Long gzbRightId,java.lang.String gzbRightIdOperation) {
        this.gzbRightId = gzbRightId;
        this.gzbRightIdOperation = gzbRightIdOperation;
        return this;
    }
    public java.lang.Long getGzbRightUsersId() {
        return this.gzbRightUsersId;
    }
    public GzbRight setGzbRightUsersId(java.lang.Long gzbRightUsersId) {
        this.gzbRightUsersId = gzbRightUsersId;
        return this;
    }
    public GzbRight setGzbRightUsersId(java.lang.Long gzbRightUsersId,java.lang.String gzbRightUsersIdOperation) {
        this.gzbRightUsersId = gzbRightUsersId;
        this.gzbRightUsersIdOperation = gzbRightUsersIdOperation;
        return this;
    }
    public java.lang.Long getGzbRightApiId() {
        return this.gzbRightApiId;
    }
    public GzbRight setGzbRightApiId(java.lang.Long gzbRightApiId) {
        this.gzbRightApiId = gzbRightApiId;
        return this;
    }
    public GzbRight setGzbRightApiId(java.lang.Long gzbRightApiId,java.lang.String gzbRightApiIdOperation) {
        this.gzbRightApiId = gzbRightApiId;
        this.gzbRightApiIdOperation = gzbRightApiIdOperation;
        return this;
    }
    public AutoSqlEntity toWhere(String sql){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append(sql+" where ");
        if (this.gzbRightId !=null){sb.append("gzb_right_id ").append(gzbRightIdOperation).append(" ? and ");list.add(this.gzbRightId);}
        if (this.gzbRightUsersId !=null){sb.append("gzb_right_users_id ").append(gzbRightUsersIdOperation).append(" ? and ");list.add(this.gzbRightUsersId);}
        if (this.gzbRightApiId !=null){sb.append("gzb_right_api_id ").append(gzbRightApiIdOperation).append(" ? and ");list.add(this.gzbRightApiId);}
        if (sb.substring(sb.length()-5,sb.length()).equals(" and "))sb.delete(sb.length()-5,sb.length());
        if (sb.substring(sb.length()-6,sb.length()).equals("where "))sb.delete(sb.length()-6,sb.length());
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toSelect(){
        return toWhere("select * from "+DataBase.gzbRightName);
    }
    public AutoSqlEntity toDelete(){
        return toWhere("delete from "+DataBase.gzbRightName);
    }
    public AutoSqlEntity toUpdate(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("update "+DataBase.gzbRightName+" set ");
        if (this.gzbRightId !=null){sb.append("gzb_right_id=?, ");list.add(this.gzbRightId);}
        if (this.gzbRightUsersId !=null){sb.append("gzb_right_users_id=?, ");list.add(this.gzbRightUsersId);}
        if (this.gzbRightApiId !=null){sb.append("gzb_right_api_id=?, ");list.add(this.gzbRightApiId);}
        if (sb.substring(sb.length()-2,sb.length()).equals(", "))sb.delete(sb.length()-2,sb.length()-1);
        if (sb.substring(sb.length()-4,sb.length()).equals("set "))sb.delete(sb.length()-4,sb.length());
        sb.append("where gzb_right_id=?");list.add(this.gzbRightId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toInsert(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("insert into "+DataBase.gzbRightName+"(gzb_right_id,gzb_right_users_id,gzb_right_api_id) values(?,?,?)");
        list.add(this.gzbRightId);
        list.add(this.gzbRightUsersId);
        list.add(this.gzbRightApiId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public GzbRight find(BaseDao dao) {
        return dao.gzbRightFind(this);
    }

    public GzbRight findCache(BaseDao dao, int mm) {
        return dao.gzbRightFindCache(this, mm);
    }

    public List<GzbRight> query(BaseDao dao) {
        return dao.gzbRightQuery(this);
    }

    public List<GzbRight> queryCache(BaseDao dao, int mm) {
        return dao.gzbRightQueryCache(this, mm);
    }

    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {
        return dao.gzbRightQuery(this, page, limit, maxPage, maxLimit);
    }

    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {
        return dao.gzbRightQueryCache(this, page, limit, maxPage, maxLimit, mm);
    }

    public int save(BaseDao dao) {
        return dao.gzbRightInsert(this);
    }

    public int update(BaseDao dao) {
        return dao.gzbRightUpdate(this);
    }

    public int delete(BaseDao dao) {
        return dao.gzbRightDelete(this);
    }

    public int saveAsy(BaseDao dao) {
        return dao.gzbRightInsertAsy(this);
    }

    public int updateAsy(BaseDao dao) {
        return dao.gzbRightUpdateAsy(this);
    }

    public int deleteAsy(BaseDao dao) {
        return dao.gzbRightDeleteAsy(this);
    }
}