package gzb.db.gzb_system.entity;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.tools.Tools;
import gzb.tools.ListPage;
import gzb.tools.entity.AutoSqlEntity;
import java.util.ArrayList;
import java.util.List;
public class GzbUsers {
    private java.lang.Long gzbUsersId;
    private java.lang.String gzbUsersAcc;
    private java.lang.String gzbUsersPwd;
    private java.lang.String gzbUsersPhone;
    private java.lang.String gzbUsersMailbox;
    private java.lang.String gzbUsersTime;
    private java.lang.Integer gzbUsersState;
    private java.lang.String gzbUsersIdOperation="=";
    private java.lang.String gzbUsersAccOperation="=";
    private java.lang.String gzbUsersPwdOperation="=";
    private java.lang.String gzbUsersPhoneOperation="=";
    private java.lang.String gzbUsersMailboxOperation="=";
    private java.lang.String gzbUsersTimeOperation="=";
    private java.lang.String gzbUsersStateOperation="=";
    private java.util.List<?>list;
    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
    public GzbUsers() {
    }
    public GzbUsers(String json) {
        String tmp;
        tmp = Tools.textMid(json, "\"gzbUsersId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbUsersIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersIdOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbUsersAcc\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersAcc = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbUsersAccOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersAccOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbUsersPwd\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersPwd = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbUsersPwdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersPwdOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbUsersPhone\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersPhone = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbUsersPhoneOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersPhoneOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbUsersMailbox\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersMailbox = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbUsersMailboxOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersMailboxOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbUsersTime\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersTime = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbUsersTimeOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersTimeOperation = tmp;}
        tmp = Tools.textMid(json, "\"gzbUsersState\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersState = java.lang.Integer.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"gzbUsersStateOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.gzbUsersStateOperation = tmp;}
    }
    @Override
    public String toString() {
        return toJson();
    }
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (gzbUsersId != null){sb.append("\"gzbUsersId\":\"").append(gzbUsersId).append("\",");}
        if (gzbUsersAcc != null){sb.append("\"gzbUsersAcc\":\"").append(gzbUsersAcc).append("\",");}
        if (gzbUsersPwd != null){sb.append("\"gzbUsersPwd\":\"").append(gzbUsersPwd).append("\",");}
        if (gzbUsersPhone != null){sb.append("\"gzbUsersPhone\":\"").append(gzbUsersPhone).append("\",");}
        if (gzbUsersMailbox != null){sb.append("\"gzbUsersMailbox\":\"").append(gzbUsersMailbox).append("\",");}
        if (gzbUsersTime != null){sb.append("\"gzbUsersTime\":\"").append(gzbUsersTime).append("\",");}
        if (gzbUsersState != null){sb.append("\"gzbUsersState\":\"").append(gzbUsersState).append("\"");}
        if (list != null){sb.append(",\"data\":").append(list.toString()).append(",");}
        if (sb.substring(sb.length()-1,sb.length()).equals(","))sb.delete(sb.length()-1,sb.length()).equals(",");
        sb.append("}");
        return sb.toString();
    }
    public java.lang.Long getGzbUsersId() {
        return this.gzbUsersId;
    }
    public GzbUsers setGzbUsersId(java.lang.Long gzbUsersId) {
        this.gzbUsersId = gzbUsersId;
        return this;
    }
    public GzbUsers setGzbUsersId(java.lang.Long gzbUsersId,java.lang.String gzbUsersIdOperation) {
        this.gzbUsersId = gzbUsersId;
        this.gzbUsersIdOperation = gzbUsersIdOperation;
        return this;
    }
    public java.lang.String getGzbUsersAcc() {
        return this.gzbUsersAcc;
    }
    public GzbUsers setGzbUsersAcc(java.lang.String gzbUsersAcc) {
        this.gzbUsersAcc = gzbUsersAcc;
        return this;
    }
    public GzbUsers setGzbUsersAcc(java.lang.String gzbUsersAcc,java.lang.String gzbUsersAccOperation) {
        this.gzbUsersAcc = gzbUsersAcc;
        this.gzbUsersAccOperation = gzbUsersAccOperation;
        return this;
    }
    public java.lang.String getGzbUsersPwd() {
        return this.gzbUsersPwd;
    }
    public GzbUsers setGzbUsersPwd(java.lang.String gzbUsersPwd) {
        this.gzbUsersPwd = gzbUsersPwd;
        return this;
    }
    public GzbUsers setGzbUsersPwd(java.lang.String gzbUsersPwd,java.lang.String gzbUsersPwdOperation) {
        this.gzbUsersPwd = gzbUsersPwd;
        this.gzbUsersPwdOperation = gzbUsersPwdOperation;
        return this;
    }
    public java.lang.String getGzbUsersPhone() {
        return this.gzbUsersPhone;
    }
    public GzbUsers setGzbUsersPhone(java.lang.String gzbUsersPhone) {
        this.gzbUsersPhone = gzbUsersPhone;
        return this;
    }
    public GzbUsers setGzbUsersPhone(java.lang.String gzbUsersPhone,java.lang.String gzbUsersPhoneOperation) {
        this.gzbUsersPhone = gzbUsersPhone;
        this.gzbUsersPhoneOperation = gzbUsersPhoneOperation;
        return this;
    }
    public java.lang.String getGzbUsersMailbox() {
        return this.gzbUsersMailbox;
    }
    public GzbUsers setGzbUsersMailbox(java.lang.String gzbUsersMailbox) {
        this.gzbUsersMailbox = gzbUsersMailbox;
        return this;
    }
    public GzbUsers setGzbUsersMailbox(java.lang.String gzbUsersMailbox,java.lang.String gzbUsersMailboxOperation) {
        this.gzbUsersMailbox = gzbUsersMailbox;
        this.gzbUsersMailboxOperation = gzbUsersMailboxOperation;
        return this;
    }
    public java.lang.String getGzbUsersTime() {
        return this.gzbUsersTime;
    }
    public GzbUsers setGzbUsersTime(java.lang.String gzbUsersTime) {
        this.gzbUsersTime = gzbUsersTime;
        return this;
    }
    public GzbUsers setGzbUsersTime(java.lang.String gzbUsersTime,java.lang.String gzbUsersTimeOperation) {
        this.gzbUsersTime = gzbUsersTime;
        this.gzbUsersTimeOperation = gzbUsersTimeOperation;
        return this;
    }
    public java.lang.Integer getGzbUsersState() {
        return this.gzbUsersState;
    }
    public GzbUsers setGzbUsersState(java.lang.Integer gzbUsersState) {
        this.gzbUsersState = gzbUsersState;
        return this;
    }
    public GzbUsers setGzbUsersState(java.lang.Integer gzbUsersState,java.lang.String gzbUsersStateOperation) {
        this.gzbUsersState = gzbUsersState;
        this.gzbUsersStateOperation = gzbUsersStateOperation;
        return this;
    }
    public AutoSqlEntity toWhere(String sql){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append(sql+" where ");
        if (this.gzbUsersId !=null){sb.append("gzb_users_id ").append(gzbUsersIdOperation).append(" ? and ");list.add(this.gzbUsersId);}
        if (this.gzbUsersAcc !=null){sb.append("gzb_users_acc ").append(gzbUsersAccOperation).append(" ? and ");list.add(this.gzbUsersAcc);}
        if (this.gzbUsersPwd !=null){sb.append("gzb_users_pwd ").append(gzbUsersPwdOperation).append(" ? and ");list.add(this.gzbUsersPwd);}
        if (this.gzbUsersPhone !=null){sb.append("gzb_users_phone ").append(gzbUsersPhoneOperation).append(" ? and ");list.add(this.gzbUsersPhone);}
        if (this.gzbUsersMailbox !=null){sb.append("gzb_users_mailbox ").append(gzbUsersMailboxOperation).append(" ? and ");list.add(this.gzbUsersMailbox);}
        if (this.gzbUsersTime !=null){sb.append("gzb_users_time ").append(gzbUsersTimeOperation).append(" ? and ");list.add(this.gzbUsersTime);}
        if (this.gzbUsersState !=null){sb.append("gzb_users_state ").append(gzbUsersStateOperation).append(" ? and ");list.add(this.gzbUsersState);}
        if (sb.substring(sb.length()-5,sb.length()).equals(" and "))sb.delete(sb.length()-5,sb.length());
        if (sb.substring(sb.length()-6,sb.length()).equals("where "))sb.delete(sb.length()-6,sb.length());
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toSelect(){
        return toWhere("select * from "+DataBase.gzbUsersName);
    }
    public AutoSqlEntity toDelete(){
        return toWhere("delete from "+DataBase.gzbUsersName);
    }
    public AutoSqlEntity toUpdate(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("update "+DataBase.gzbUsersName+" set ");
        if (this.gzbUsersId !=null){sb.append("gzb_users_id=?, ");list.add(this.gzbUsersId);}
        if (this.gzbUsersAcc !=null){sb.append("gzb_users_acc=?, ");list.add(this.gzbUsersAcc);}
        if (this.gzbUsersPwd !=null){sb.append("gzb_users_pwd=?, ");list.add(this.gzbUsersPwd);}
        if (this.gzbUsersPhone !=null){sb.append("gzb_users_phone=?, ");list.add(this.gzbUsersPhone);}
        if (this.gzbUsersMailbox !=null){sb.append("gzb_users_mailbox=?, ");list.add(this.gzbUsersMailbox);}
        if (this.gzbUsersTime !=null){sb.append("gzb_users_time=?, ");list.add(this.gzbUsersTime);}
        if (this.gzbUsersState !=null){sb.append("gzb_users_state=?, ");list.add(this.gzbUsersState);}
        if (sb.substring(sb.length()-2,sb.length()).equals(", "))sb.delete(sb.length()-2,sb.length()-1);
        if (sb.substring(sb.length()-4,sb.length()).equals("set "))sb.delete(sb.length()-4,sb.length());
        sb.append("where gzb_users_id=?");list.add(this.gzbUsersId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toInsert(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("insert into "+DataBase.gzbUsersName+"(gzb_users_id,gzb_users_acc,gzb_users_pwd,gzb_users_phone,gzb_users_mailbox,gzb_users_time,gzb_users_state) values(?,?,?,?,?,?,?)");
        list.add(this.gzbUsersId);
        list.add(this.gzbUsersAcc);
        list.add(this.gzbUsersPwd);
        list.add(this.gzbUsersPhone);
        list.add(this.gzbUsersMailbox);
        list.add(this.gzbUsersTime);
        list.add(this.gzbUsersState);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public GzbUsers find(BaseDao dao) {
        return dao.gzbUsersFind(this);
    }

    public GzbUsers findCache(BaseDao dao, int mm) {
        return dao.gzbUsersFindCache(this, mm);
    }

    public List<GzbUsers> query(BaseDao dao) {
        return dao.gzbUsersQuery(this);
    }

    public List<GzbUsers> queryCache(BaseDao dao, int mm) {
        return dao.gzbUsersQueryCache(this, mm);
    }

    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {
        return dao.gzbUsersQuery(this, page, limit, maxPage, maxLimit);
    }

    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {
        return dao.gzbUsersQueryCache(this, page, limit, maxPage, maxLimit, mm);
    }

    public int save(BaseDao dao) {
        return dao.gzbUsersInsert(this);
    }

    public int update(BaseDao dao) {
        return dao.gzbUsersUpdate(this);
    }

    public int delete(BaseDao dao) {
        return dao.gzbUsersDelete(this);
    }

    public int saveAsy(BaseDao dao) {
        return dao.gzbUsersInsertAsy(this);
    }

    public int updateAsy(BaseDao dao) {
        return dao.gzbUsersUpdateAsy(this);
    }

    public int deleteAsy(BaseDao dao) {
        return dao.gzbUsersDeleteAsy(this);
    }
}