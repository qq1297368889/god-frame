package gzb.db.gzb_system.entity;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.tools.Tools;
import gzb.tools.ListPage;
import gzb.tools.entity.AutoSqlEntity;
import java.util.ArrayList;
import java.util.List;
public class GzbGroup {
    private java.lang.Long gzbGroupId;
    private java.lang.String gzbGroupName;
    private java.lang.String gzbGroupState;
    private java.lang.String gzbGroupIdOperation="=";
    private java.lang.String gzbGroupNameOperation="=";
    private java.lang.String gzbGroupStateOperation="=";
    private java.util.List<?>list;
    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
    public GzbGroup() {
    }
    public GzbGroup(String json) {
        String tmp;
        tmp = Tools.textMid(json, "\"gzbGroupId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbGroupId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbGroupIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbGroupIdOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbGroupName\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbGroupName = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbGroupNameOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbGroupNameOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbGroupState\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbGroupState = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbGroupStateOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbGroupStateOperation = tmp;}
    }
    @Override
    public String toString() {
        return toJson();
    }
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (gzbGroupId != null){sb.append("\"gzbGroupId\":\"").append(gzbGroupId).append("\",");}
        if (gzbGroupName != null){sb.append("\"gzbGroupName\":\"").append(gzbGroupName).append("\",");}
        if (gzbGroupState != null){sb.append("\"gzbGroupState\":\"").append(gzbGroupState).append("\"");}
        if (list != null){sb.append(",\"data\":").append(list.toString()).append(",");}
        if (sb.substring(sb.length()-1,sb.length()).equals(","))sb.delete(sb.length()-1,sb.length()).equals(",");
        sb.append("}");
        return sb.toString();
    }
    public java.lang.Long getGzbGroupId() {
        return this.gzbGroupId;
    }
    public GzbGroup setGzbGroupId(java.lang.Long gzbGroupId) {
        this.gzbGroupId = gzbGroupId;
        return this;
    }
    public GzbGroup setGzbGroupId(java.lang.Long gzbGroupId,java.lang.String gzbGroupIdOperation) {
        this.gzbGroupId = gzbGroupId;
        this.gzbGroupIdOperation = gzbGroupIdOperation;
        return this;
    }
    public java.lang.String getGzbGroupName() {
        return this.gzbGroupName;
    }
    public GzbGroup setGzbGroupName(java.lang.String gzbGroupName) {
        this.gzbGroupName = gzbGroupName;
        return this;
    }
    public GzbGroup setGzbGroupName(java.lang.String gzbGroupName,java.lang.String gzbGroupNameOperation) {
        this.gzbGroupName = gzbGroupName;
        this.gzbGroupNameOperation = gzbGroupNameOperation;
        return this;
    }
    public java.lang.String getGzbGroupState() {
        return this.gzbGroupState;
    }
    public GzbGroup setGzbGroupState(java.lang.String gzbGroupState) {
        this.gzbGroupState = gzbGroupState;
        return this;
    }
    public GzbGroup setGzbGroupState(java.lang.String gzbGroupState,java.lang.String gzbGroupStateOperation) {
        this.gzbGroupState = gzbGroupState;
        this.gzbGroupStateOperation = gzbGroupStateOperation;
        return this;
    }
    public AutoSqlEntity toWhere(String sql){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append(sql+" where ");
        if (this.gzbGroupId !=null){sb.append("gzb_group_id ").append(gzbGroupIdOperation).append(" ? and ");list.add(this.gzbGroupId);}
        if (this.gzbGroupName !=null){sb.append("gzb_group_name ").append(gzbGroupNameOperation).append(" ? and ");list.add(this.gzbGroupName);}
        if (this.gzbGroupState !=null){sb.append("gzb_group_state ").append(gzbGroupStateOperation).append(" ? and ");list.add(this.gzbGroupState);}
        if (sb.substring(sb.length()-5,sb.length()).equals(" and "))sb.delete(sb.length()-5,sb.length());
        if (sb.substring(sb.length()-6,sb.length()).equals("where "))sb.delete(sb.length()-6,sb.length());
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toSelect(){
        return toWhere("select * from "+DataBase.gzbGroupName);
    }
    public AutoSqlEntity toDelete(){
        return toWhere("delete from "+DataBase.gzbGroupName);
    }
    public AutoSqlEntity toUpdate(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("update "+DataBase.gzbGroupName+" set ");
        if (this.gzbGroupId !=null){sb.append("gzb_group_id=?, ");list.add(this.gzbGroupId);}
        if (this.gzbGroupName !=null){sb.append("gzb_group_name=?, ");list.add(this.gzbGroupName);}
        if (this.gzbGroupState !=null){sb.append("gzb_group_state=?, ");list.add(this.gzbGroupState);}
        if (sb.substring(sb.length()-2,sb.length()).equals(", "))sb.delete(sb.length()-2,sb.length()-1);
        if (sb.substring(sb.length()-4,sb.length()).equals("set "))sb.delete(sb.length()-4,sb.length());
        sb.append("where gzb_group_id=?");list.add(this.gzbGroupId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toInsert(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("insert into "+DataBase.gzbGroupName+"(gzb_group_id,gzb_group_name,gzb_group_state) values(?,?,?)");
        list.add(this.gzbGroupId);
        list.add(this.gzbGroupName);
        list.add(this.gzbGroupState);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public String toInsert2(){
        return new StringBuilder().append("insert into "+DataBase.gzbGroupName+"(gzb_group_id,gzb_group_name,gzb_group_state) values('")
                .append(this.gzbGroupId==null?"":this.gzbGroupId)
                .append("','")
                .append(this.gzbGroupName==null?"":this.gzbGroupName)
                .append("','")
                .append(this.gzbGroupState==null?"":this.gzbGroupState)
                .append("')").toString();
    }
    public GzbGroup find(BaseDao dao) {
        return dao.gzbGroupFind(this);
    }

    public GzbGroup findCache(BaseDao dao, int mm) {
        return dao.gzbGroupFindCache(this, mm);
    }

    public List<GzbGroup> query(BaseDao dao) {
        return dao.gzbGroupQuery(this);
    }

    public List<GzbGroup> queryCache(BaseDao dao, int mm) {
        return dao.gzbGroupQueryCache(this, mm);
    }

    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {
        return dao.gzbGroupQuery(this, page, limit, maxPage, maxLimit);
    }

    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {
        return dao.gzbGroupQueryCache(this, page, limit, maxPage, maxLimit, mm);
    }

    public int save(BaseDao dao) {
        return dao.gzbGroupInsert(this);
    }

    public int update(BaseDao dao) {
        return dao.gzbGroupUpdate(this);
    }

    public int delete(BaseDao dao) {
        return dao.gzbGroupDelete(this);
    }

    public int saveAsy(BaseDao dao) {
        return dao.gzbGroupInsertAsy(this);
    }

    public int updateAsy(BaseDao dao) {
        return dao.gzbGroupUpdateAsy(this);
    }

    public int deleteAsy(BaseDao dao) {
        return dao.gzbGroupDeleteAsy(this);
    }
}