package gzb.db.gzb_system.entity;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.tools.Tools;
import gzb.tools.ListPage;
import gzb.tools.entity.AutoSqlEntity;
import java.util.ArrayList;
import java.util.List;
public class Users {
    private java.lang.Long usersId;
    private java.lang.String usersAcc;
    private java.lang.String usersPwd;
    private java.lang.String usersState;
    private java.lang.String usersIdOperation="=";
    private java.lang.String usersAccOperation="=";
    private java.lang.String usersPwdOperation="=";
    private java.lang.String usersStateOperation="=";
    private java.util.List<?>list;
    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
    public Users() {
    }
    public Users(String json) {
        String tmp;
        tmp = Tools.textMid(json, "\"usersId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.usersId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"usersIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.usersIdOperation = tmp;}
        tmp = Tools.textMid(json, "\"usersAcc\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.usersAcc = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"usersAccOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.usersAccOperation = tmp;}
        tmp = Tools.textMid(json, "\"usersPwd\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.usersPwd = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"usersPwdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.usersPwdOperation = tmp;}
        tmp = Tools.textMid(json, "\"usersState\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.usersState = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"usersStateOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.usersStateOperation = tmp;}
    }
    @Override
    public String toString() {
        return toJson();
    }
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (usersId != null){sb.append("\"usersId\":\"").append(usersId).append("\",");}
        if (usersAcc != null){sb.append("\"usersAcc\":\"").append(usersAcc).append("\",");}
        if (usersPwd != null){sb.append("\"usersPwd\":\"").append(usersPwd).append("\",");}
        if (usersState != null){sb.append("\"usersState\":\"").append(usersState).append("\"");}
        if (list != null){sb.append(",\"data\":").append(list.toString()).append(",");}
        if (sb.substring(sb.length()-1,sb.length()).equals(","))sb.delete(sb.length()-1,sb.length()).equals(",");
        sb.append("}");
        return sb.toString();
    }
    public java.lang.Long getUsersId() {
        return this.usersId;
    }
    public Users setUsersId(java.lang.Long usersId) {
        this.usersId = usersId;
        return this;
    }
    public Users setUsersId(java.lang.Long usersId,java.lang.String usersIdOperation) {
        this.usersId = usersId;
        this.usersIdOperation = usersIdOperation;
        return this;
    }
    public java.lang.String getUsersAcc() {
        return this.usersAcc;
    }
    public Users setUsersAcc(java.lang.String usersAcc) {
        this.usersAcc = usersAcc;
        return this;
    }
    public Users setUsersAcc(java.lang.String usersAcc,java.lang.String usersAccOperation) {
        this.usersAcc = usersAcc;
        this.usersAccOperation = usersAccOperation;
        return this;
    }
    public java.lang.String getUsersPwd() {
        return this.usersPwd;
    }
    public Users setUsersPwd(java.lang.String usersPwd) {
        this.usersPwd = usersPwd;
        return this;
    }
    public Users setUsersPwd(java.lang.String usersPwd,java.lang.String usersPwdOperation) {
        this.usersPwd = usersPwd;
        this.usersPwdOperation = usersPwdOperation;
        return this;
    }
    public java.lang.String getUsersState() {
        return this.usersState;
    }
    public Users setUsersState(java.lang.String usersState) {
        this.usersState = usersState;
        return this;
    }
    public Users setUsersState(java.lang.String usersState,java.lang.String usersStateOperation) {
        this.usersState = usersState;
        this.usersStateOperation = usersStateOperation;
        return this;
    }
    public AutoSqlEntity toWhere(String sql){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append(sql+" where ");
        if (this.usersId !=null){sb.append("users_id ").append(usersIdOperation).append(" ? and ");list.add(this.usersId);}
        if (this.usersAcc !=null){sb.append("users_acc ").append(usersAccOperation).append(" ? and ");list.add(this.usersAcc);}
        if (this.usersPwd !=null){sb.append("users_pwd ").append(usersPwdOperation).append(" ? and ");list.add(this.usersPwd);}
        if (this.usersState !=null){sb.append("users_state ").append(usersStateOperation).append(" ? and ");list.add(this.usersState);}
        if (sb.substring(sb.length()-5,sb.length()).equals(" and "))sb.delete(sb.length()-5,sb.length());
        if (sb.substring(sb.length()-6,sb.length()).equals("where "))sb.delete(sb.length()-6,sb.length());
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toSelect(){
        return toWhere("select * from "+DataBase.usersName);
    }
    public AutoSqlEntity toDelete(){
        return toWhere("delete from "+DataBase.usersName);
    }
    public AutoSqlEntity toUpdate(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("update "+DataBase.usersName+" set ");
        if (this.usersId !=null){sb.append("users_id=?, ");list.add(this.usersId);}
        if (this.usersAcc !=null){sb.append("users_acc=?, ");list.add(this.usersAcc);}
        if (this.usersPwd !=null){sb.append("users_pwd=?, ");list.add(this.usersPwd);}
        if (this.usersState !=null){sb.append("users_state=?, ");list.add(this.usersState);}
        if (sb.substring(sb.length()-2,sb.length()).equals(", "))sb.delete(sb.length()-2,sb.length()-1);
        if (sb.substring(sb.length()-4,sb.length()).equals("set "))sb.delete(sb.length()-4,sb.length());
        sb.append("where users_id=?");list.add(this.usersId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toInsert(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("insert into "+DataBase.usersName+"(users_id,users_acc,users_pwd,users_state) values(?,?,?,?)");
        list.add(this.usersId);
        list.add(this.usersAcc);
        list.add(this.usersPwd);
        list.add(this.usersState);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public String toInsert2(){
        return new StringBuilder().append("insert into "+DataBase.usersName+"(users_id,users_acc,users_pwd,users_state) values('")
                .append(this.usersId==null?"":this.usersId)
                .append("','")
                .append(this.usersAcc==null?"":this.usersAcc)
                .append("','")
                .append(this.usersPwd==null?"":this.usersPwd)
                .append("','")
                .append(this.usersState==null?"":this.usersState)
                .append("')").toString();
    }
    public Users find(BaseDao dao) {
        return dao.usersFind(this);
    }

    public Users findCache(BaseDao dao, int mm) {
        return dao.usersFindCache(this, mm);
    }

    public List<Users> query(BaseDao dao) {
        return dao.usersQuery(this);
    }

    public List<Users> queryCache(BaseDao dao, int mm) {
        return dao.usersQueryCache(this, mm);
    }

    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {
        return dao.usersQuery(this, page, limit, maxPage, maxLimit);
    }

    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {
        return dao.usersQueryCache(this, page, limit, maxPage, maxLimit, mm);
    }

    public int save(BaseDao dao) {
        return dao.usersInsert(this);
    }

    public int update(BaseDao dao) {
        return dao.usersUpdate(this);
    }

    public int delete(BaseDao dao) {
        return dao.usersDelete(this);
    }

    public int saveAsy(BaseDao dao) {
        return dao.usersInsertAsy(this);
    }

    public int updateAsy(BaseDao dao) {
        return dao.usersUpdateAsy(this);
    }

    public int deleteAsy(BaseDao dao) {
        return dao.usersDeleteAsy(this);
    }
}