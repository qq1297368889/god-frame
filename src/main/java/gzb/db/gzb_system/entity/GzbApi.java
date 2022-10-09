package gzb.db.gzb_system.entity;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.tools.Tools;
import gzb.tools.ListPage;
import gzb.tools.entity.AutoSqlEntity;
import java.util.ArrayList;
import java.util.List;
public class GzbApi {
    private java.lang.Long gzbApiId;
    private java.lang.String gzbApiName;
    private java.lang.Integer gzbApiState;
    private java.lang.String gzbApiCode;
    private java.lang.String gzbApiIdOperation="=";
    private java.lang.String gzbApiNameOperation="=";
    private java.lang.String gzbApiStateOperation="=";
    private java.lang.String gzbApiCodeOperation="=";
    private java.util.List<?>list;
    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
    public GzbApi() {
    }
    public GzbApi(String json) {
        String tmp;
        tmp = Tools.textMid(json, "\"gzbApiId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbApiId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbApiIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbApiIdOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbApiName\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbApiName = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbApiNameOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbApiNameOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbApiState\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbApiState = java.lang.Integer.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbApiStateOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbApiStateOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbApiCode\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbApiCode = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbApiCodeOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbApiCodeOperation = tmp;}
    }
    @Override
    public String toString() {
        return toJson();
    }
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (gzbApiId != null){sb.append("\"gzbApiId\":\"").append(gzbApiId).append("\",");}
        if (gzbApiName != null){sb.append("\"gzbApiName\":\"").append(gzbApiName).append("\",");}
        if (gzbApiState != null){sb.append("\"gzbApiState\":\"").append(gzbApiState).append("\",");}
        if (gzbApiCode != null){sb.append("\"gzbApiCode\":\"").append(gzbApiCode).append("\"");}
        if (list != null){sb.append(",\"data\":").append(list.toString()).append(",");}
        if (sb.substring(sb.length()-1,sb.length()).equals(","))sb.delete(sb.length()-1,sb.length()).equals(",");
        sb.append("}");
        return sb.toString();
    }
    public java.lang.Long getGzbApiId() {
        return this.gzbApiId;
    }
    public GzbApi setGzbApiId(java.lang.Long gzbApiId) {
        this.gzbApiId = gzbApiId;
        return this;
    }
    public GzbApi setGzbApiId(java.lang.Long gzbApiId,java.lang.String gzbApiIdOperation) {
        this.gzbApiId = gzbApiId;
        this.gzbApiIdOperation = gzbApiIdOperation;
        return this;
    }
    public java.lang.String getGzbApiName() {
        return this.gzbApiName;
    }
    public GzbApi setGzbApiName(java.lang.String gzbApiName) {
        this.gzbApiName = gzbApiName;
        return this;
    }
    public GzbApi setGzbApiName(java.lang.String gzbApiName,java.lang.String gzbApiNameOperation) {
        this.gzbApiName = gzbApiName;
        this.gzbApiNameOperation = gzbApiNameOperation;
        return this;
    }
    public java.lang.Integer getGzbApiState() {
        return this.gzbApiState;
    }
    public GzbApi setGzbApiState(java.lang.Integer gzbApiState) {
        this.gzbApiState = gzbApiState;
        return this;
    }
    public GzbApi setGzbApiState(java.lang.Integer gzbApiState,java.lang.String gzbApiStateOperation) {
        this.gzbApiState = gzbApiState;
        this.gzbApiStateOperation = gzbApiStateOperation;
        return this;
    }
    public java.lang.String getGzbApiCode() {
        return this.gzbApiCode;
    }
    public GzbApi setGzbApiCode(java.lang.String gzbApiCode) {
        this.gzbApiCode = gzbApiCode;
        return this;
    }
    public GzbApi setGzbApiCode(java.lang.String gzbApiCode,java.lang.String gzbApiCodeOperation) {
        this.gzbApiCode = gzbApiCode;
        this.gzbApiCodeOperation = gzbApiCodeOperation;
        return this;
    }
    public AutoSqlEntity toWhere(String sql){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append(sql+" where ");
        if (this.gzbApiId !=null){sb.append("gzb_api_id ").append(gzbApiIdOperation).append(" ? and ");list.add(this.gzbApiId);}
        if (this.gzbApiName !=null){sb.append("gzb_api_name ").append(gzbApiNameOperation).append(" ? and ");list.add(this.gzbApiName);}
        if (this.gzbApiState !=null){sb.append("gzb_api_state ").append(gzbApiStateOperation).append(" ? and ");list.add(this.gzbApiState);}
        if (this.gzbApiCode !=null){sb.append("gzb_api_code ").append(gzbApiCodeOperation).append(" ? and ");list.add(this.gzbApiCode);}
        if (sb.substring(sb.length()-5,sb.length()).equals(" and "))sb.delete(sb.length()-5,sb.length());
        if (sb.substring(sb.length()-6,sb.length()).equals("where "))sb.delete(sb.length()-6,sb.length());
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toSelect(){
        return toWhere("select * from "+DataBase.gzbApiName);
    }
    public AutoSqlEntity toDelete(){
        return toWhere("delete from "+DataBase.gzbApiName);
    }
    public AutoSqlEntity toUpdate(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("update "+DataBase.gzbApiName+" set ");
        if (this.gzbApiId !=null){sb.append("gzb_api_id=?, ");list.add(this.gzbApiId);}
        if (this.gzbApiName !=null){sb.append("gzb_api_name=?, ");list.add(this.gzbApiName);}
        if (this.gzbApiState !=null){sb.append("gzb_api_state=?, ");list.add(this.gzbApiState);}
        if (this.gzbApiCode !=null){sb.append("gzb_api_code=?, ");list.add(this.gzbApiCode);}
        if (sb.substring(sb.length()-2,sb.length()).equals(", "))sb.delete(sb.length()-2,sb.length()-1);
        if (sb.substring(sb.length()-4,sb.length()).equals("set "))sb.delete(sb.length()-4,sb.length());
        sb.append("where gzb_api_id=?");list.add(this.gzbApiId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toInsert(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("insert into "+DataBase.gzbApiName+"(gzb_api_id,gzb_api_name,gzb_api_state,gzb_api_code) values(?,?,?,?)");
        list.add(this.gzbApiId);
        list.add(this.gzbApiName);
        list.add(this.gzbApiState);
        list.add(this.gzbApiCode);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public GzbApi find(BaseDao dao) {
        return dao.gzbApiFind(this);
    }

    public GzbApi findCache(BaseDao dao, int mm) {
        return dao.gzbApiFindCache(this, mm);
    }

    public List<GzbApi> query(BaseDao dao) {
        return dao.gzbApiQuery(this);
    }

    public List<GzbApi> queryCache(BaseDao dao, int mm) {
        return dao.gzbApiQueryCache(this, mm);
    }

    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {
        return dao.gzbApiQuery(this, page, limit, maxPage, maxLimit);
    }

    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {
        return dao.gzbApiQueryCache(this, page, limit, maxPage, maxLimit, mm);
    }

    public int save(BaseDao dao) {
        return dao.gzbApiInsert(this);
    }

    public int update(BaseDao dao) {
        return dao.gzbApiUpdate(this);
    }

    public int delete(BaseDao dao) {
        return dao.gzbApiDelete(this);
    }

    public int saveAsy(BaseDao dao) {
        return dao.gzbApiInsertAsy(this);
    }

    public int updateAsy(BaseDao dao) {
        return dao.gzbApiUpdateAsy(this);
    }

    public int deleteAsy(BaseDao dao) {
        return dao.gzbApiDeleteAsy(this);
    }
}