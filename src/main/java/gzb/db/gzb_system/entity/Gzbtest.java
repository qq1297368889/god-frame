package gzb.db.gzb_system.entity;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.tools.Tools;
import gzb.tools.ListPage;
import gzb.tools.entity.AutoSqlEntity;
import java.util.ArrayList;
import java.util.List;
public class Gzbtest {
    private java.lang.Long gzbTestId;
    private java.lang.String gzbTestAcc;
    private java.lang.String gzbTestPwd;
    private java.lang.String gzbTestIdOperation="=";
    private java.lang.String gzbTestAccOperation="=";
    private java.lang.String gzbTestPwdOperation="=";
    private java.util.List<?>list;
    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
    public Gzbtest() {
    }
    public Gzbtest(String json) {
        String tmp;
        tmp = Tools.textMid(json, "\"gzbTestId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbTestId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbTestIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbTestIdOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbTestAcc\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbTestAcc = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbTestAccOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbTestAccOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbTestPwd\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbTestPwd = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbTestPwdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbTestPwdOperation = tmp;}
    }
    @Override
    public String toString() {
        return toJson();
    }
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (gzbTestId != null){sb.append("\"gzbTestId\":\"").append(gzbTestId).append("\",");}
        if (gzbTestAcc != null){sb.append("\"gzbTestAcc\":\"").append(gzbTestAcc).append("\",");}
        if (gzbTestPwd != null){sb.append("\"gzbTestPwd\":\"").append(gzbTestPwd).append("\"");}
        if (list != null){sb.append(",\"data\":").append(list.toString()).append(",");}
        if (sb.substring(sb.length()-1,sb.length()).equals(","))sb.delete(sb.length()-1,sb.length()).equals(",");
        sb.append("}");
        return sb.toString();
    }
    public java.lang.Long getGzbTestId() {
        return this.gzbTestId;
    }
    public Gzbtest setGzbTestId(java.lang.Long gzbTestId) {
        this.gzbTestId = gzbTestId;
        return this;
    }
    public Gzbtest setGzbTestId(java.lang.Long gzbTestId,java.lang.String gzbTestIdOperation) {
        this.gzbTestId = gzbTestId;
        this.gzbTestIdOperation = gzbTestIdOperation;
        return this;
    }
    public java.lang.String getGzbTestAcc() {
        return this.gzbTestAcc;
    }
    public Gzbtest setGzbTestAcc(java.lang.String gzbTestAcc) {
        this.gzbTestAcc = gzbTestAcc;
        return this;
    }
    public Gzbtest setGzbTestAcc(java.lang.String gzbTestAcc,java.lang.String gzbTestAccOperation) {
        this.gzbTestAcc = gzbTestAcc;
        this.gzbTestAccOperation = gzbTestAccOperation;
        return this;
    }
    public java.lang.String getGzbTestPwd() {
        return this.gzbTestPwd;
    }
    public Gzbtest setGzbTestPwd(java.lang.String gzbTestPwd) {
        this.gzbTestPwd = gzbTestPwd;
        return this;
    }
    public Gzbtest setGzbTestPwd(java.lang.String gzbTestPwd,java.lang.String gzbTestPwdOperation) {
        this.gzbTestPwd = gzbTestPwd;
        this.gzbTestPwdOperation = gzbTestPwdOperation;
        return this;
    }
    public AutoSqlEntity toWhere(String sql){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append(sql+" where ");
        if (this.gzbTestId !=null){sb.append("gzbTestId ").append(gzbTestIdOperation).append(" ? and ");list.add(this.gzbTestId);}
        if (this.gzbTestAcc !=null){sb.append("gzbTestAcc ").append(gzbTestAccOperation).append(" ? and ");list.add(this.gzbTestAcc);}
        if (this.gzbTestPwd !=null){sb.append("gzbTestPwd ").append(gzbTestPwdOperation).append(" ? and ");list.add(this.gzbTestPwd);}
        if (sb.substring(sb.length()-5,sb.length()).equals(" and "))sb.delete(sb.length()-5,sb.length());
        if (sb.substring(sb.length()-6,sb.length()).equals("where "))sb.delete(sb.length()-6,sb.length());
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toSelect(){
        return toWhere("select * from "+DataBase.gzbtestName);
    }
    public AutoSqlEntity toDelete(){
        return toWhere("delete from "+DataBase.gzbtestName);
    }
    public AutoSqlEntity toUpdate(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("update "+DataBase.gzbtestName+" set ");
        if (this.gzbTestId !=null){sb.append("gzbTestId=?, ");list.add(this.gzbTestId);}
        if (this.gzbTestAcc !=null){sb.append("gzbTestAcc=?, ");list.add(this.gzbTestAcc);}
        if (this.gzbTestPwd !=null){sb.append("gzbTestPwd=?, ");list.add(this.gzbTestPwd);}
        if (sb.substring(sb.length()-2,sb.length()).equals(", "))sb.delete(sb.length()-2,sb.length()-1);
        if (sb.substring(sb.length()-4,sb.length()).equals("set "))sb.delete(sb.length()-4,sb.length());
        sb.append("where gzbTestId=?");list.add(this.gzbTestId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toInsert(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("insert into "+DataBase.gzbtestName+"(gzbTestId,gzbTestAcc,gzbTestPwd) values(?,?,?)");
        list.add(this.gzbTestId);
        list.add(this.gzbTestAcc);
        list.add(this.gzbTestPwd);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public Gzbtest find(BaseDao dao) {
        return dao.gzbtestFind(this);
    }

    public Gzbtest findCache(BaseDao dao, int mm) {
        return dao.gzbtestFindCache(this, mm);
    }

    public List<Gzbtest> query(BaseDao dao) {
        return dao.gzbtestQuery(this);
    }

    public List<Gzbtest> queryCache(BaseDao dao, int mm) {
        return dao.gzbtestQueryCache(this, mm);
    }

    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {
        return dao.gzbtestQuery(this, page, limit, maxPage, maxLimit);
    }

    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {
        return dao.gzbtestQueryCache(this, page, limit, maxPage, maxLimit, mm);
    }

    public int save(BaseDao dao) {
        return dao.gzbtestInsert(this);
    }

    public int update(BaseDao dao) {
        return dao.gzbtestUpdate(this);
    }

    public int delete(BaseDao dao) {
        return dao.gzbtestDelete(this);
    }

    public int saveAsy(BaseDao dao) {
        return dao.gzbtestInsertAsy(this);
    }

    public int updateAsy(BaseDao dao) {
        return dao.gzbtestUpdateAsy(this);
    }

    public int deleteAsy(BaseDao dao) {
        return dao.gzbtestDeleteAsy(this);
    }
}