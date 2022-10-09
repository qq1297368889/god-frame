package gzb.db.gzb_system.entity;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.tools.Tools;
import gzb.tools.ListPage;
import gzb.tools.entity.AutoSqlEntity;
import java.util.ArrayList;
import java.util.List;
public class FileManager {
    private java.lang.Long fileManagerId;
    private java.lang.String fileManagerMd5;
    private java.lang.String fileManagerName;
    private java.lang.String fileManagerTime;
    private java.lang.Integer fileManagerState;
    private java.lang.Integer fileManagerReadNum;
    private java.lang.String fileManagerType;
    private java.lang.String fileManagerIdOperation="=";
    private java.lang.String fileManagerMd5Operation="=";
    private java.lang.String fileManagerNameOperation="=";
    private java.lang.String fileManagerTimeOperation="=";
    private java.lang.String fileManagerStateOperation="=";
    private java.lang.String fileManagerReadNumOperation="=";
    private java.lang.String fileManagerTypeOperation="=";
    private java.util.List<?>list;
    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
    public FileManager() {
    }
    public FileManager(String json) {
        String tmp;
        tmp = Tools.textMid(json, "\"fileManagerId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"fileManagerIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerIdOperation = tmp;}
        tmp = Tools.textMid(json, "\"fileManagerMd5\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerMd5 = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"fileManagerMd5Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerMd5Operation = tmp;}
        tmp = Tools.textMid(json, "\"fileManagerName\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerName = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"fileManagerNameOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerNameOperation = tmp;}
        tmp = Tools.textMid(json, "\"fileManagerTime\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerTime = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"fileManagerTimeOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerTimeOperation = tmp;}
        tmp = Tools.textMid(json, "\"fileManagerState\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerState = java.lang.Integer.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"fileManagerStateOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerStateOperation = tmp;}
        tmp = Tools.textMid(json, "\"fileManagerReadNum\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerReadNum = java.lang.Integer.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"fileManagerReadNumOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerReadNumOperation = tmp;}
        tmp = Tools.textMid(json, "\"fileManagerType\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerType = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"fileManagerTypeOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.fileManagerTypeOperation = tmp;}
    }
    @Override
    public String toString() {
        return toJson();
    }
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (fileManagerId != null){sb.append("\"fileManagerId\":\"").append(fileManagerId).append("\",");}
        if (fileManagerMd5 != null){sb.append("\"fileManagerMd5\":\"").append(fileManagerMd5).append("\",");}
        if (fileManagerName != null){sb.append("\"fileManagerName\":\"").append(fileManagerName).append("\",");}
        if (fileManagerTime != null){sb.append("\"fileManagerTime\":\"").append(fileManagerTime).append("\",");}
        if (fileManagerState != null){sb.append("\"fileManagerState\":\"").append(fileManagerState).append("\",");}
        if (fileManagerReadNum != null){sb.append("\"fileManagerReadNum\":\"").append(fileManagerReadNum).append("\",");}
        if (fileManagerType != null){sb.append("\"fileManagerType\":\"").append(fileManagerType).append("\"");}
        if (list != null){sb.append(",\"data\":").append(list.toString()).append(",");}
        if (sb.substring(sb.length()-1,sb.length()).equals(","))sb.delete(sb.length()-1,sb.length()).equals(",");
        sb.append("}");
        return sb.toString();
    }
    public java.lang.Long getFileManagerId() {
        return this.fileManagerId;
    }
    public FileManager setFileManagerId(java.lang.Long fileManagerId) {
        this.fileManagerId = fileManagerId;
        return this;
    }
    public FileManager setFileManagerId(java.lang.Long fileManagerId,java.lang.String fileManagerIdOperation) {
        this.fileManagerId = fileManagerId;
        this.fileManagerIdOperation = fileManagerIdOperation;
        return this;
    }
    public java.lang.String getFileManagerMd5() {
        return this.fileManagerMd5;
    }
    public FileManager setFileManagerMd5(java.lang.String fileManagerMd5) {
        this.fileManagerMd5 = fileManagerMd5;
        return this;
    }
    public FileManager setFileManagerMd5(java.lang.String fileManagerMd5,java.lang.String fileManagerMd5Operation) {
        this.fileManagerMd5 = fileManagerMd5;
        this.fileManagerMd5Operation = fileManagerMd5Operation;
        return this;
    }
    public java.lang.String getFileManagerName() {
        return this.fileManagerName;
    }
    public FileManager setFileManagerName(java.lang.String fileManagerName) {
        this.fileManagerName = fileManagerName;
        return this;
    }
    public FileManager setFileManagerName(java.lang.String fileManagerName,java.lang.String fileManagerNameOperation) {
        this.fileManagerName = fileManagerName;
        this.fileManagerNameOperation = fileManagerNameOperation;
        return this;
    }
    public java.lang.String getFileManagerTime() {
        return this.fileManagerTime;
    }
    public FileManager setFileManagerTime(java.lang.String fileManagerTime) {
        this.fileManagerTime = fileManagerTime;
        return this;
    }
    public FileManager setFileManagerTime(java.lang.String fileManagerTime,java.lang.String fileManagerTimeOperation) {
        this.fileManagerTime = fileManagerTime;
        this.fileManagerTimeOperation = fileManagerTimeOperation;
        return this;
    }
    public java.lang.Integer getFileManagerState() {
        return this.fileManagerState;
    }
    public FileManager setFileManagerState(java.lang.Integer fileManagerState) {
        this.fileManagerState = fileManagerState;
        return this;
    }
    public FileManager setFileManagerState(java.lang.Integer fileManagerState,java.lang.String fileManagerStateOperation) {
        this.fileManagerState = fileManagerState;
        this.fileManagerStateOperation = fileManagerStateOperation;
        return this;
    }
    public java.lang.Integer getFileManagerReadNum() {
        return this.fileManagerReadNum;
    }
    public FileManager setFileManagerReadNum(java.lang.Integer fileManagerReadNum) {
        this.fileManagerReadNum = fileManagerReadNum;
        return this;
    }
    public FileManager setFileManagerReadNum(java.lang.Integer fileManagerReadNum,java.lang.String fileManagerReadNumOperation) {
        this.fileManagerReadNum = fileManagerReadNum;
        this.fileManagerReadNumOperation = fileManagerReadNumOperation;
        return this;
    }
    public java.lang.String getFileManagerType() {
        return this.fileManagerType;
    }
    public FileManager setFileManagerType(java.lang.String fileManagerType) {
        this.fileManagerType = fileManagerType;
        return this;
    }
    public FileManager setFileManagerType(java.lang.String fileManagerType,java.lang.String fileManagerTypeOperation) {
        this.fileManagerType = fileManagerType;
        this.fileManagerTypeOperation = fileManagerTypeOperation;
        return this;
    }
    public AutoSqlEntity toWhere(String sql){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append(sql+" where ");
        if (this.fileManagerId !=null){sb.append("file_manager_id ").append(fileManagerIdOperation).append(" ? and ");list.add(this.fileManagerId);}
        if (this.fileManagerMd5 !=null){sb.append("file_manager_md5 ").append(fileManagerMd5Operation).append(" ? and ");list.add(this.fileManagerMd5);}
        if (this.fileManagerName !=null){sb.append("file_manager_name ").append(fileManagerNameOperation).append(" ? and ");list.add(this.fileManagerName);}
        if (this.fileManagerTime !=null){sb.append("file_manager_time ").append(fileManagerTimeOperation).append(" ? and ");list.add(this.fileManagerTime);}
        if (this.fileManagerState !=null){sb.append("file_manager_state ").append(fileManagerStateOperation).append(" ? and ");list.add(this.fileManagerState);}
        if (this.fileManagerReadNum !=null){sb.append("file_manager_read_num ").append(fileManagerReadNumOperation).append(" ? and ");list.add(this.fileManagerReadNum);}
        if (this.fileManagerType !=null){sb.append("file_manager_type ").append(fileManagerTypeOperation).append(" ? and ");list.add(this.fileManagerType);}
        if (sb.substring(sb.length()-5,sb.length()).equals(" and "))sb.delete(sb.length()-5,sb.length());
        if (sb.substring(sb.length()-6,sb.length()).equals("where "))sb.delete(sb.length()-6,sb.length());
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toSelect(){
        return toWhere("select * from "+DataBase.fileManagerName);
    }
    public AutoSqlEntity toDelete(){
        return toWhere("delete from "+DataBase.fileManagerName);
    }
    public AutoSqlEntity toUpdate(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("update "+DataBase.fileManagerName+" set ");
        if (this.fileManagerId !=null){sb.append("file_manager_id=?, ");list.add(this.fileManagerId);}
        if (this.fileManagerMd5 !=null){sb.append("file_manager_md5=?, ");list.add(this.fileManagerMd5);}
        if (this.fileManagerName !=null){sb.append("file_manager_name=?, ");list.add(this.fileManagerName);}
        if (this.fileManagerTime !=null){sb.append("file_manager_time=?, ");list.add(this.fileManagerTime);}
        if (this.fileManagerState !=null){sb.append("file_manager_state=?, ");list.add(this.fileManagerState);}
        if (this.fileManagerReadNum !=null){sb.append("file_manager_read_num=?, ");list.add(this.fileManagerReadNum);}
        if (this.fileManagerType !=null){sb.append("file_manager_type=?, ");list.add(this.fileManagerType);}
        if (sb.substring(sb.length()-2,sb.length()).equals(", "))sb.delete(sb.length()-2,sb.length()-1);
        if (sb.substring(sb.length()-4,sb.length()).equals("set "))sb.delete(sb.length()-4,sb.length());
        sb.append("where file_manager_id=?");list.add(this.fileManagerId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toInsert(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("insert into "+DataBase.fileManagerName+"(file_manager_id,file_manager_md5,file_manager_name,file_manager_time,file_manager_state,file_manager_read_num,file_manager_type) values(?,?,?,?,?,?,?)");
        list.add(this.fileManagerId);
        list.add(this.fileManagerMd5);
        list.add(this.fileManagerName);
        list.add(this.fileManagerTime);
        list.add(this.fileManagerState);
        list.add(this.fileManagerReadNum);
        list.add(this.fileManagerType);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public FileManager find(BaseDao dao) {
        return dao.fileManagerFind(this);
    }

    public FileManager findCache(BaseDao dao, int mm) {
        return dao.fileManagerFindCache(this, mm);
    }

    public List<FileManager> query(BaseDao dao) {
        return dao.fileManagerQuery(this);
    }

    public List<FileManager> queryCache(BaseDao dao, int mm) {
        return dao.fileManagerQueryCache(this, mm);
    }

    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {
        return dao.fileManagerQuery(this, page, limit, maxPage, maxLimit);
    }

    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {
        return dao.fileManagerQueryCache(this, page, limit, maxPage, maxLimit, mm);
    }

    public int save(BaseDao dao) {
        return dao.fileManagerInsert(this);
    }

    public int update(BaseDao dao) {
        return dao.fileManagerUpdate(this);
    }

    public int delete(BaseDao dao) {
        return dao.fileManagerDelete(this);
    }

    public int saveAsy(BaseDao dao) {
        return dao.fileManagerInsertAsy(this);
    }

    public int updateAsy(BaseDao dao) {
        return dao.fileManagerUpdateAsy(this);
    }

    public int deleteAsy(BaseDao dao) {
        return dao.fileManagerDeleteAsy(this);
    }
}