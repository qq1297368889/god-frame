package gzb.db.gzb_system.entity;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.tools.Tools;
import gzb.tools.ListPage;
import gzb.tools.entity.AutoSqlEntity;
import java.util.ArrayList;
import java.util.List;
public class Test {
    private java.lang.Long testId;
    private java.lang.String testName;
    private java.lang.Integer testState;
    private java.lang.String testIdOperation="=";
    private java.lang.String testNameOperation="=";
    private java.lang.String testStateOperation="=";
    private java.util.List<?>list;
    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
    public Test() {
    }
    public Test(String json) {
        String tmp;
        tmp = Tools.textMid(json, "\"testId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.testId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"testIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.testIdOperation = tmp;}
        tmp = Tools.textMid(json, "\"testName\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.testName = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"testNameOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.testNameOperation = tmp;}
        tmp = Tools.textMid(json, "\"testState\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.testState = java.lang.Integer.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"testStateOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.testStateOperation = tmp;}
    }
    @Override
    public String toString() {
        return toJson();
    }
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (testId != null){sb.append("\"testId\":\"").append(testId).append("\",");}
        if (testName != null){sb.append("\"testName\":\"").append(testName).append("\",");}
        if (testState != null){sb.append("\"testState\":\"").append(testState).append("\"");}
        if (list != null){sb.append(",\"data\":").append(list.toString()).append(",");}
        if (sb.substring(sb.length()-1,sb.length()).equals(","))sb.delete(sb.length()-1,sb.length()).equals(",");
        sb.append("}");
        return sb.toString();
    }
    public java.lang.Long getTestId() {
        return this.testId;
    }
    public Test setTestId(java.lang.Long testId) {
        this.testId = testId;
        return this;
    }
    public Test setTestId(java.lang.Long testId,java.lang.String testIdOperation) {
        this.testId = testId;
        this.testIdOperation = testIdOperation;
        return this;
    }
    public java.lang.String getTestName() {
        return this.testName;
    }
    public Test setTestName(java.lang.String testName) {
        this.testName = testName;
        return this;
    }
    public Test setTestName(java.lang.String testName,java.lang.String testNameOperation) {
        this.testName = testName;
        this.testNameOperation = testNameOperation;
        return this;
    }
    public java.lang.Integer getTestState() {
        return this.testState;
    }
    public Test setTestState(java.lang.Integer testState) {
        this.testState = testState;
        return this;
    }
    public Test setTestState(java.lang.Integer testState,java.lang.String testStateOperation) {
        this.testState = testState;
        this.testStateOperation = testStateOperation;
        return this;
    }
    public AutoSqlEntity toWhere(String sql){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append(sql+" where ");
        if (this.testId !=null){sb.append("test_id ").append(testIdOperation).append(" ? and ");list.add(this.testId);}
        if (this.testName !=null){sb.append("test_name ").append(testNameOperation).append(" ? and ");list.add(this.testName);}
        if (this.testState !=null){sb.append("test_state ").append(testStateOperation).append(" ? and ");list.add(this.testState);}
        if (sb.substring(sb.length()-5,sb.length()).equals(" and "))sb.delete(sb.length()-5,sb.length());
        if (sb.substring(sb.length()-6,sb.length()).equals("where "))sb.delete(sb.length()-6,sb.length());
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toSelect(){
        return toWhere("select * from "+DataBase.testName);
    }
    public AutoSqlEntity toDelete(){
        return toWhere("delete from "+DataBase.testName);
    }
    public AutoSqlEntity toUpdate(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("update "+DataBase.testName+" set ");
        if (this.testId !=null){sb.append("test_id=?, ");list.add(this.testId);}
        if (this.testName !=null){sb.append("test_name=?, ");list.add(this.testName);}
        if (this.testState !=null){sb.append("test_state=?, ");list.add(this.testState);}
        if (sb.substring(sb.length()-2,sb.length()).equals(", "))sb.delete(sb.length()-2,sb.length()-1);
        if (sb.substring(sb.length()-4,sb.length()).equals("set "))sb.delete(sb.length()-4,sb.length());
        sb.append("where test_id=?");list.add(this.testId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toInsert(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("insert into "+DataBase.testName+"(test_id,test_name,test_state) values(?,?,?)");
        list.add(this.testId);
        list.add(this.testName);
        list.add(this.testState);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public Test find(BaseDao dao) {
        return dao.testFind(this);
    }

    public Test findCache(BaseDao dao, int mm) {
        return dao.testFindCache(this, mm);
    }

    public List<Test> query(BaseDao dao) {
        return dao.testQuery(this);
    }

    public List<Test> queryCache(BaseDao dao, int mm) {
        return dao.testQueryCache(this, mm);
    }

    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {
        return dao.testQuery(this, page, limit, maxPage, maxLimit);
    }

    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {
        return dao.testQueryCache(this, page, limit, maxPage, maxLimit, mm);
    }

    public int save(BaseDao dao) {
        return dao.testInsert(this);
    }

    public int update(BaseDao dao) {
        return dao.testUpdate(this);
    }

    public int delete(BaseDao dao) {
        return dao.testDelete(this);
    }

    public int saveAsy(BaseDao dao) {
        return dao.testInsertAsy(this);
    }

    public int updateAsy(BaseDao dao) {
        return dao.testUpdateAsy(this);
    }

    public int deleteAsy(BaseDao dao) {
        return dao.testDeleteAsy(this);
    }
}