package gzb.db.gzb_system.entity;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.tools.Tools;
import gzb.tools.ListPage;
import gzb.tools.entity.AutoSqlEntity;
import java.util.ArrayList;
import java.util.List;
public class ContentManager {
    private java.lang.Long contentManagerId;
    private java.lang.String contentManagerVarchar;
    private java.lang.String contentManagerVarchar1;
    private java.lang.String contentManagerVarchar2;
    private java.lang.String contentManagerVarchar3;
    private java.lang.String contentManagerText;
    private java.lang.String contentManagerText1;
    private java.lang.String contentManagerText2;
    private java.lang.String contentManagerText3;
    private java.lang.String contentManagerTime;
    private java.lang.String contentManagerTime1;
    private java.lang.String contentManagerTime2;
    private java.lang.String contentManagerTime3;
    private java.lang.Integer contentManagerInt;
    private java.lang.Integer contentManagerInt1;
    private java.lang.Integer contentManagerInt2;
    private java.lang.Integer contentManagerInt3;
    private java.lang.Double contentManagerDouble;
    private java.lang.Double contentManagerDouble1;
    private java.lang.Double contentManagerDouble2;
    private java.lang.Double contentManagerDouble3;
    private java.lang.Integer contentManagerState;
    private java.lang.Long contentManagerUsersId;
    private java.lang.String contentManagerIdOperation="=";
    private java.lang.String contentManagerVarcharOperation="=";
    private java.lang.String contentManagerVarchar1Operation="=";
    private java.lang.String contentManagerVarchar2Operation="=";
    private java.lang.String contentManagerVarchar3Operation="=";
    private java.lang.String contentManagerTextOperation="=";
    private java.lang.String contentManagerText1Operation="=";
    private java.lang.String contentManagerText2Operation="=";
    private java.lang.String contentManagerText3Operation="=";
    private java.lang.String contentManagerTimeOperation="=";
    private java.lang.String contentManagerTime1Operation="=";
    private java.lang.String contentManagerTime2Operation="=";
    private java.lang.String contentManagerTime3Operation="=";
    private java.lang.String contentManagerIntOperation="=";
    private java.lang.String contentManagerInt1Operation="=";
    private java.lang.String contentManagerInt2Operation="=";
    private java.lang.String contentManagerInt3Operation="=";
    private java.lang.String contentManagerDoubleOperation="=";
    private java.lang.String contentManagerDouble1Operation="=";
    private java.lang.String contentManagerDouble2Operation="=";
    private java.lang.String contentManagerDouble3Operation="=";
    private java.lang.String contentManagerStateOperation="=";
    private java.lang.String contentManagerUsersIdOperation="=";
    private java.util.List<?>list;
    public List<?> getList() {
        return list;
    }
    public void setList(List<?> list) {
        this.list = list;
    }
    public ContentManager() {
    }
    public ContentManager(String json) {
        String tmp;
        tmp = Tools.textMid(json, "\"contentManagerId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerIdOperation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerVarchar\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerVarchar = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerVarcharOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerVarcharOperation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerVarchar1\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerVarchar1 = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerVarchar1Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerVarchar1Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerVarchar2\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerVarchar2 = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerVarchar2Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerVarchar2Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerVarchar3\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerVarchar3 = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerVarchar3Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerVarchar3Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerText\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerText = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerTextOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerTextOperation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerText1\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerText1 = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerText1Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerText1Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerText2\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerText2 = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerText2Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerText2Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerText3\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerText3 = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerText3Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerText3Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerTime\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerTime = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerTimeOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerTimeOperation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerTime1\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerTime1 = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerTime1Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerTime1Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerTime2\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerTime2 = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerTime2Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerTime2Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerTime3\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerTime3 = java.lang.String.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerTime3Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerTime3Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerInt\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerInt = java.lang.Integer.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerIntOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerIntOperation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerInt1\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerInt1 = java.lang.Integer.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerInt1Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerInt1Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerInt2\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerInt2 = java.lang.Integer.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerInt2Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerInt2Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerInt3\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerInt3 = java.lang.Integer.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerInt3Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerInt3Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerDouble\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerDouble = java.lang.Double.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerDoubleOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerDoubleOperation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerDouble1\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerDouble1 = java.lang.Double.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerDouble1Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerDouble1Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerDouble2\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerDouble2 = java.lang.Double.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerDouble2Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerDouble2Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerDouble3\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerDouble3 = java.lang.Double.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerDouble3Operation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerDouble3Operation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerState\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerState = java.lang.Integer.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerStateOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerStateOperation = tmp;}
        tmp = Tools.textMid(json, "\"contentManagerUsersId\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerUsersId = java.lang.Long.valueOf(tmp);}
        tmp = Tools.textMid(json, "\"contentManagerUsersIdOperation\":\"", "\"", 1);
        if (tmp != null && tmp.length() > 0){this.contentManagerUsersIdOperation = tmp;}
    }
    @Override
    public String toString() {
        return toJson();
    }
    public String toJson() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (contentManagerId != null){sb.append("\"contentManagerId\":\"").append(contentManagerId).append("\",");}
        if (contentManagerVarchar != null){sb.append("\"contentManagerVarchar\":\"").append(contentManagerVarchar).append("\",");}
        if (contentManagerVarchar1 != null){sb.append("\"contentManagerVarchar1\":\"").append(contentManagerVarchar1).append("\",");}
        if (contentManagerVarchar2 != null){sb.append("\"contentManagerVarchar2\":\"").append(contentManagerVarchar2).append("\",");}
        if (contentManagerVarchar3 != null){sb.append("\"contentManagerVarchar3\":\"").append(contentManagerVarchar3).append("\",");}
        if (contentManagerText != null){sb.append("\"contentManagerText\":\"").append(contentManagerText).append("\",");}
        if (contentManagerText1 != null){sb.append("\"contentManagerText1\":\"").append(contentManagerText1).append("\",");}
        if (contentManagerText2 != null){sb.append("\"contentManagerText2\":\"").append(contentManagerText2).append("\",");}
        if (contentManagerText3 != null){sb.append("\"contentManagerText3\":\"").append(contentManagerText3).append("\",");}
        if (contentManagerTime != null){sb.append("\"contentManagerTime\":\"").append(contentManagerTime).append("\",");}
        if (contentManagerTime1 != null){sb.append("\"contentManagerTime1\":\"").append(contentManagerTime1).append("\",");}
        if (contentManagerTime2 != null){sb.append("\"contentManagerTime2\":\"").append(contentManagerTime2).append("\",");}
        if (contentManagerTime3 != null){sb.append("\"contentManagerTime3\":\"").append(contentManagerTime3).append("\",");}
        if (contentManagerInt != null){sb.append("\"contentManagerInt\":\"").append(contentManagerInt).append("\",");}
        if (contentManagerInt1 != null){sb.append("\"contentManagerInt1\":\"").append(contentManagerInt1).append("\",");}
        if (contentManagerInt2 != null){sb.append("\"contentManagerInt2\":\"").append(contentManagerInt2).append("\",");}
        if (contentManagerInt3 != null){sb.append("\"contentManagerInt3\":\"").append(contentManagerInt3).append("\",");}
        if (contentManagerDouble != null){sb.append("\"contentManagerDouble\":\"").append(contentManagerDouble).append("\",");}
        if (contentManagerDouble1 != null){sb.append("\"contentManagerDouble1\":\"").append(contentManagerDouble1).append("\",");}
        if (contentManagerDouble2 != null){sb.append("\"contentManagerDouble2\":\"").append(contentManagerDouble2).append("\",");}
        if (contentManagerDouble3 != null){sb.append("\"contentManagerDouble3\":\"").append(contentManagerDouble3).append("\",");}
        if (contentManagerState != null){sb.append("\"contentManagerState\":\"").append(contentManagerState).append("\",");}
        if (contentManagerUsersId != null){sb.append("\"contentManagerUsersId\":\"").append(contentManagerUsersId).append("\"");}
        if (list != null){sb.append(",\"data\":").append(list.toString()).append(",");}
        if (sb.substring(sb.length()-1,sb.length()).equals(","))sb.delete(sb.length()-1,sb.length()).equals(",");
        sb.append("}");
        return sb.toString();
    }
    public java.lang.Long getContentManagerId() {
        return this.contentManagerId;
    }
    public ContentManager setContentManagerId(java.lang.Long contentManagerId) {
        this.contentManagerId = contentManagerId;
        return this;
    }
    public ContentManager setContentManagerId(java.lang.Long contentManagerId,java.lang.String contentManagerIdOperation) {
        this.contentManagerId = contentManagerId;
        this.contentManagerIdOperation = contentManagerIdOperation;
        return this;
    }
    public java.lang.String getContentManagerVarchar() {
        return this.contentManagerVarchar;
    }
    public ContentManager setContentManagerVarchar(java.lang.String contentManagerVarchar) {
        this.contentManagerVarchar = contentManagerVarchar;
        return this;
    }
    public ContentManager setContentManagerVarchar(java.lang.String contentManagerVarchar,java.lang.String contentManagerVarcharOperation) {
        this.contentManagerVarchar = contentManagerVarchar;
        this.contentManagerVarcharOperation = contentManagerVarcharOperation;
        return this;
    }
    public java.lang.String getContentManagerVarchar1() {
        return this.contentManagerVarchar1;
    }
    public ContentManager setContentManagerVarchar1(java.lang.String contentManagerVarchar1) {
        this.contentManagerVarchar1 = contentManagerVarchar1;
        return this;
    }
    public ContentManager setContentManagerVarchar1(java.lang.String contentManagerVarchar1,java.lang.String contentManagerVarchar1Operation) {
        this.contentManagerVarchar1 = contentManagerVarchar1;
        this.contentManagerVarchar1Operation = contentManagerVarchar1Operation;
        return this;
    }
    public java.lang.String getContentManagerVarchar2() {
        return this.contentManagerVarchar2;
    }
    public ContentManager setContentManagerVarchar2(java.lang.String contentManagerVarchar2) {
        this.contentManagerVarchar2 = contentManagerVarchar2;
        return this;
    }
    public ContentManager setContentManagerVarchar2(java.lang.String contentManagerVarchar2,java.lang.String contentManagerVarchar2Operation) {
        this.contentManagerVarchar2 = contentManagerVarchar2;
        this.contentManagerVarchar2Operation = contentManagerVarchar2Operation;
        return this;
    }
    public java.lang.String getContentManagerVarchar3() {
        return this.contentManagerVarchar3;
    }
    public ContentManager setContentManagerVarchar3(java.lang.String contentManagerVarchar3) {
        this.contentManagerVarchar3 = contentManagerVarchar3;
        return this;
    }
    public ContentManager setContentManagerVarchar3(java.lang.String contentManagerVarchar3,java.lang.String contentManagerVarchar3Operation) {
        this.contentManagerVarchar3 = contentManagerVarchar3;
        this.contentManagerVarchar3Operation = contentManagerVarchar3Operation;
        return this;
    }
    public java.lang.String getContentManagerText() {
        return this.contentManagerText;
    }
    public ContentManager setContentManagerText(java.lang.String contentManagerText) {
        this.contentManagerText = contentManagerText;
        return this;
    }
    public ContentManager setContentManagerText(java.lang.String contentManagerText,java.lang.String contentManagerTextOperation) {
        this.contentManagerText = contentManagerText;
        this.contentManagerTextOperation = contentManagerTextOperation;
        return this;
    }
    public java.lang.String getContentManagerText1() {
        return this.contentManagerText1;
    }
    public ContentManager setContentManagerText1(java.lang.String contentManagerText1) {
        this.contentManagerText1 = contentManagerText1;
        return this;
    }
    public ContentManager setContentManagerText1(java.lang.String contentManagerText1,java.lang.String contentManagerText1Operation) {
        this.contentManagerText1 = contentManagerText1;
        this.contentManagerText1Operation = contentManagerText1Operation;
        return this;
    }
    public java.lang.String getContentManagerText2() {
        return this.contentManagerText2;
    }
    public ContentManager setContentManagerText2(java.lang.String contentManagerText2) {
        this.contentManagerText2 = contentManagerText2;
        return this;
    }
    public ContentManager setContentManagerText2(java.lang.String contentManagerText2,java.lang.String contentManagerText2Operation) {
        this.contentManagerText2 = contentManagerText2;
        this.contentManagerText2Operation = contentManagerText2Operation;
        return this;
    }
    public java.lang.String getContentManagerText3() {
        return this.contentManagerText3;
    }
    public ContentManager setContentManagerText3(java.lang.String contentManagerText3) {
        this.contentManagerText3 = contentManagerText3;
        return this;
    }
    public ContentManager setContentManagerText3(java.lang.String contentManagerText3,java.lang.String contentManagerText3Operation) {
        this.contentManagerText3 = contentManagerText3;
        this.contentManagerText3Operation = contentManagerText3Operation;
        return this;
    }
    public java.lang.String getContentManagerTime() {
        return this.contentManagerTime;
    }
    public ContentManager setContentManagerTime(java.lang.String contentManagerTime) {
        this.contentManagerTime = contentManagerTime;
        return this;
    }
    public ContentManager setContentManagerTime(java.lang.String contentManagerTime,java.lang.String contentManagerTimeOperation) {
        this.contentManagerTime = contentManagerTime;
        this.contentManagerTimeOperation = contentManagerTimeOperation;
        return this;
    }
    public java.lang.String getContentManagerTime1() {
        return this.contentManagerTime1;
    }
    public ContentManager setContentManagerTime1(java.lang.String contentManagerTime1) {
        this.contentManagerTime1 = contentManagerTime1;
        return this;
    }
    public ContentManager setContentManagerTime1(java.lang.String contentManagerTime1,java.lang.String contentManagerTime1Operation) {
        this.contentManagerTime1 = contentManagerTime1;
        this.contentManagerTime1Operation = contentManagerTime1Operation;
        return this;
    }
    public java.lang.String getContentManagerTime2() {
        return this.contentManagerTime2;
    }
    public ContentManager setContentManagerTime2(java.lang.String contentManagerTime2) {
        this.contentManagerTime2 = contentManagerTime2;
        return this;
    }
    public ContentManager setContentManagerTime2(java.lang.String contentManagerTime2,java.lang.String contentManagerTime2Operation) {
        this.contentManagerTime2 = contentManagerTime2;
        this.contentManagerTime2Operation = contentManagerTime2Operation;
        return this;
    }
    public java.lang.String getContentManagerTime3() {
        return this.contentManagerTime3;
    }
    public ContentManager setContentManagerTime3(java.lang.String contentManagerTime3) {
        this.contentManagerTime3 = contentManagerTime3;
        return this;
    }
    public ContentManager setContentManagerTime3(java.lang.String contentManagerTime3,java.lang.String contentManagerTime3Operation) {
        this.contentManagerTime3 = contentManagerTime3;
        this.contentManagerTime3Operation = contentManagerTime3Operation;
        return this;
    }
    public java.lang.Integer getContentManagerInt() {
        return this.contentManagerInt;
    }
    public ContentManager setContentManagerInt(java.lang.Integer contentManagerInt) {
        this.contentManagerInt = contentManagerInt;
        return this;
    }
    public ContentManager setContentManagerInt(java.lang.Integer contentManagerInt,java.lang.String contentManagerIntOperation) {
        this.contentManagerInt = contentManagerInt;
        this.contentManagerIntOperation = contentManagerIntOperation;
        return this;
    }
    public java.lang.Integer getContentManagerInt1() {
        return this.contentManagerInt1;
    }
    public ContentManager setContentManagerInt1(java.lang.Integer contentManagerInt1) {
        this.contentManagerInt1 = contentManagerInt1;
        return this;
    }
    public ContentManager setContentManagerInt1(java.lang.Integer contentManagerInt1,java.lang.String contentManagerInt1Operation) {
        this.contentManagerInt1 = contentManagerInt1;
        this.contentManagerInt1Operation = contentManagerInt1Operation;
        return this;
    }
    public java.lang.Integer getContentManagerInt2() {
        return this.contentManagerInt2;
    }
    public ContentManager setContentManagerInt2(java.lang.Integer contentManagerInt2) {
        this.contentManagerInt2 = contentManagerInt2;
        return this;
    }
    public ContentManager setContentManagerInt2(java.lang.Integer contentManagerInt2,java.lang.String contentManagerInt2Operation) {
        this.contentManagerInt2 = contentManagerInt2;
        this.contentManagerInt2Operation = contentManagerInt2Operation;
        return this;
    }
    public java.lang.Integer getContentManagerInt3() {
        return this.contentManagerInt3;
    }
    public ContentManager setContentManagerInt3(java.lang.Integer contentManagerInt3) {
        this.contentManagerInt3 = contentManagerInt3;
        return this;
    }
    public ContentManager setContentManagerInt3(java.lang.Integer contentManagerInt3,java.lang.String contentManagerInt3Operation) {
        this.contentManagerInt3 = contentManagerInt3;
        this.contentManagerInt3Operation = contentManagerInt3Operation;
        return this;
    }
    public java.lang.Double getContentManagerDouble() {
        return this.contentManagerDouble;
    }
    public ContentManager setContentManagerDouble(java.lang.Double contentManagerDouble) {
        this.contentManagerDouble = contentManagerDouble;
        return this;
    }
    public ContentManager setContentManagerDouble(java.lang.Double contentManagerDouble,java.lang.String contentManagerDoubleOperation) {
        this.contentManagerDouble = contentManagerDouble;
        this.contentManagerDoubleOperation = contentManagerDoubleOperation;
        return this;
    }
    public java.lang.Double getContentManagerDouble1() {
        return this.contentManagerDouble1;
    }
    public ContentManager setContentManagerDouble1(java.lang.Double contentManagerDouble1) {
        this.contentManagerDouble1 = contentManagerDouble1;
        return this;
    }
    public ContentManager setContentManagerDouble1(java.lang.Double contentManagerDouble1,java.lang.String contentManagerDouble1Operation) {
        this.contentManagerDouble1 = contentManagerDouble1;
        this.contentManagerDouble1Operation = contentManagerDouble1Operation;
        return this;
    }
    public java.lang.Double getContentManagerDouble2() {
        return this.contentManagerDouble2;
    }
    public ContentManager setContentManagerDouble2(java.lang.Double contentManagerDouble2) {
        this.contentManagerDouble2 = contentManagerDouble2;
        return this;
    }
    public ContentManager setContentManagerDouble2(java.lang.Double contentManagerDouble2,java.lang.String contentManagerDouble2Operation) {
        this.contentManagerDouble2 = contentManagerDouble2;
        this.contentManagerDouble2Operation = contentManagerDouble2Operation;
        return this;
    }
    public java.lang.Double getContentManagerDouble3() {
        return this.contentManagerDouble3;
    }
    public ContentManager setContentManagerDouble3(java.lang.Double contentManagerDouble3) {
        this.contentManagerDouble3 = contentManagerDouble3;
        return this;
    }
    public ContentManager setContentManagerDouble3(java.lang.Double contentManagerDouble3,java.lang.String contentManagerDouble3Operation) {
        this.contentManagerDouble3 = contentManagerDouble3;
        this.contentManagerDouble3Operation = contentManagerDouble3Operation;
        return this;
    }
    public java.lang.Integer getContentManagerState() {
        return this.contentManagerState;
    }
    public ContentManager setContentManagerState(java.lang.Integer contentManagerState) {
        this.contentManagerState = contentManagerState;
        return this;
    }
    public ContentManager setContentManagerState(java.lang.Integer contentManagerState,java.lang.String contentManagerStateOperation) {
        this.contentManagerState = contentManagerState;
        this.contentManagerStateOperation = contentManagerStateOperation;
        return this;
    }
    public java.lang.Long getContentManagerUsersId() {
        return this.contentManagerUsersId;
    }
    public ContentManager setContentManagerUsersId(java.lang.Long contentManagerUsersId) {
        this.contentManagerUsersId = contentManagerUsersId;
        return this;
    }
    public ContentManager setContentManagerUsersId(java.lang.Long contentManagerUsersId,java.lang.String contentManagerUsersIdOperation) {
        this.contentManagerUsersId = contentManagerUsersId;
        this.contentManagerUsersIdOperation = contentManagerUsersIdOperation;
        return this;
    }
    public AutoSqlEntity toWhere(String sql){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append(sql+" where ");
        if (this.contentManagerId !=null){sb.append("content_manager_id ").append(contentManagerIdOperation).append(" ? and ");list.add(this.contentManagerId);}
        if (this.contentManagerVarchar !=null){sb.append("content_manager_varchar ").append(contentManagerVarcharOperation).append(" ? and ");list.add(this.contentManagerVarchar);}
        if (this.contentManagerVarchar1 !=null){sb.append("content_manager_varchar1 ").append(contentManagerVarchar1Operation).append(" ? and ");list.add(this.contentManagerVarchar1);}
        if (this.contentManagerVarchar2 !=null){sb.append("content_manager_varchar2 ").append(contentManagerVarchar2Operation).append(" ? and ");list.add(this.contentManagerVarchar2);}
        if (this.contentManagerVarchar3 !=null){sb.append("content_manager_varchar3 ").append(contentManagerVarchar3Operation).append(" ? and ");list.add(this.contentManagerVarchar3);}
        if (this.contentManagerText !=null){sb.append("content_manager_text ").append(contentManagerTextOperation).append(" ? and ");list.add(this.contentManagerText);}
        if (this.contentManagerText1 !=null){sb.append("content_manager_text1 ").append(contentManagerText1Operation).append(" ? and ");list.add(this.contentManagerText1);}
        if (this.contentManagerText2 !=null){sb.append("content_manager_text2 ").append(contentManagerText2Operation).append(" ? and ");list.add(this.contentManagerText2);}
        if (this.contentManagerText3 !=null){sb.append("content_manager_text3 ").append(contentManagerText3Operation).append(" ? and ");list.add(this.contentManagerText3);}
        if (this.contentManagerTime !=null){sb.append("content_manager_time ").append(contentManagerTimeOperation).append(" ? and ");list.add(this.contentManagerTime);}
        if (this.contentManagerTime1 !=null){sb.append("content_manager_time1 ").append(contentManagerTime1Operation).append(" ? and ");list.add(this.contentManagerTime1);}
        if (this.contentManagerTime2 !=null){sb.append("content_manager_time2 ").append(contentManagerTime2Operation).append(" ? and ");list.add(this.contentManagerTime2);}
        if (this.contentManagerTime3 !=null){sb.append("content_manager_time3 ").append(contentManagerTime3Operation).append(" ? and ");list.add(this.contentManagerTime3);}
        if (this.contentManagerInt !=null){sb.append("content_manager_int ").append(contentManagerIntOperation).append(" ? and ");list.add(this.contentManagerInt);}
        if (this.contentManagerInt1 !=null){sb.append("content_manager_int1 ").append(contentManagerInt1Operation).append(" ? and ");list.add(this.contentManagerInt1);}
        if (this.contentManagerInt2 !=null){sb.append("content_manager_int2 ").append(contentManagerInt2Operation).append(" ? and ");list.add(this.contentManagerInt2);}
        if (this.contentManagerInt3 !=null){sb.append("content_manager_int3 ").append(contentManagerInt3Operation).append(" ? and ");list.add(this.contentManagerInt3);}
        if (this.contentManagerDouble !=null){sb.append("content_manager_double ").append(contentManagerDoubleOperation).append(" ? and ");list.add(this.contentManagerDouble);}
        if (this.contentManagerDouble1 !=null){sb.append("content_manager_double1 ").append(contentManagerDouble1Operation).append(" ? and ");list.add(this.contentManagerDouble1);}
        if (this.contentManagerDouble2 !=null){sb.append("content_manager_double2 ").append(contentManagerDouble2Operation).append(" ? and ");list.add(this.contentManagerDouble2);}
        if (this.contentManagerDouble3 !=null){sb.append("content_manager_double3 ").append(contentManagerDouble3Operation).append(" ? and ");list.add(this.contentManagerDouble3);}
        if (this.contentManagerState !=null){sb.append("content_manager_state ").append(contentManagerStateOperation).append(" ? and ");list.add(this.contentManagerState);}
        if (this.contentManagerUsersId !=null){sb.append("content_manager_users_id ").append(contentManagerUsersIdOperation).append(" ? and ");list.add(this.contentManagerUsersId);}
        if (sb.substring(sb.length()-5,sb.length()).equals(" and "))sb.delete(sb.length()-5,sb.length());
        if (sb.substring(sb.length()-6,sb.length()).equals("where "))sb.delete(sb.length()-6,sb.length());
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toSelect(){
        return toWhere("select * from "+DataBase.contentManagerName);
    }
    public AutoSqlEntity toDelete(){
        return toWhere("delete from "+DataBase.contentManagerName);
    }
    public AutoSqlEntity toUpdate(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("update "+DataBase.contentManagerName+" set ");
        if (this.contentManagerId !=null){sb.append("content_manager_id=?, ");list.add(this.contentManagerId);}
        if (this.contentManagerVarchar !=null){sb.append("content_manager_varchar=?, ");list.add(this.contentManagerVarchar);}
        if (this.contentManagerVarchar1 !=null){sb.append("content_manager_varchar1=?, ");list.add(this.contentManagerVarchar1);}
        if (this.contentManagerVarchar2 !=null){sb.append("content_manager_varchar2=?, ");list.add(this.contentManagerVarchar2);}
        if (this.contentManagerVarchar3 !=null){sb.append("content_manager_varchar3=?, ");list.add(this.contentManagerVarchar3);}
        if (this.contentManagerText !=null){sb.append("content_manager_text=?, ");list.add(this.contentManagerText);}
        if (this.contentManagerText1 !=null){sb.append("content_manager_text1=?, ");list.add(this.contentManagerText1);}
        if (this.contentManagerText2 !=null){sb.append("content_manager_text2=?, ");list.add(this.contentManagerText2);}
        if (this.contentManagerText3 !=null){sb.append("content_manager_text3=?, ");list.add(this.contentManagerText3);}
        if (this.contentManagerTime !=null){sb.append("content_manager_time=?, ");list.add(this.contentManagerTime);}
        if (this.contentManagerTime1 !=null){sb.append("content_manager_time1=?, ");list.add(this.contentManagerTime1);}
        if (this.contentManagerTime2 !=null){sb.append("content_manager_time2=?, ");list.add(this.contentManagerTime2);}
        if (this.contentManagerTime3 !=null){sb.append("content_manager_time3=?, ");list.add(this.contentManagerTime3);}
        if (this.contentManagerInt !=null){sb.append("content_manager_int=?, ");list.add(this.contentManagerInt);}
        if (this.contentManagerInt1 !=null){sb.append("content_manager_int1=?, ");list.add(this.contentManagerInt1);}
        if (this.contentManagerInt2 !=null){sb.append("content_manager_int2=?, ");list.add(this.contentManagerInt2);}
        if (this.contentManagerInt3 !=null){sb.append("content_manager_int3=?, ");list.add(this.contentManagerInt3);}
        if (this.contentManagerDouble !=null){sb.append("content_manager_double=?, ");list.add(this.contentManagerDouble);}
        if (this.contentManagerDouble1 !=null){sb.append("content_manager_double1=?, ");list.add(this.contentManagerDouble1);}
        if (this.contentManagerDouble2 !=null){sb.append("content_manager_double2=?, ");list.add(this.contentManagerDouble2);}
        if (this.contentManagerDouble3 !=null){sb.append("content_manager_double3=?, ");list.add(this.contentManagerDouble3);}
        if (this.contentManagerState !=null){sb.append("content_manager_state=?, ");list.add(this.contentManagerState);}
        if (this.contentManagerUsersId !=null){sb.append("content_manager_users_id=?, ");list.add(this.contentManagerUsersId);}
        if (sb.substring(sb.length()-2,sb.length()).equals(", "))sb.delete(sb.length()-2,sb.length()-1);
        if (sb.substring(sb.length()-4,sb.length()).equals("set "))sb.delete(sb.length()-4,sb.length());
        sb.append("where content_manager_id=?");list.add(this.contentManagerId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public AutoSqlEntity toInsert(){
        List<Object> list=new ArrayList<>();
        StringBuilder sb=new StringBuilder();
        sb.append("insert into "+DataBase.contentManagerName+"(content_manager_id,content_manager_varchar,content_manager_varchar1,content_manager_varchar2,content_manager_varchar3,content_manager_text,content_manager_text1,content_manager_text2,content_manager_text3,content_manager_time,content_manager_time1,content_manager_time2,content_manager_time3,content_manager_int,content_manager_int1,content_manager_int2,content_manager_int3,content_manager_double,content_manager_double1,content_manager_double2,content_manager_double3,content_manager_state,content_manager_users_id) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        list.add(this.contentManagerId);
        list.add(this.contentManagerVarchar);
        list.add(this.contentManagerVarchar1);
        list.add(this.contentManagerVarchar2);
        list.add(this.contentManagerVarchar3);
        list.add(this.contentManagerText);
        list.add(this.contentManagerText1);
        list.add(this.contentManagerText2);
        list.add(this.contentManagerText3);
        list.add(this.contentManagerTime);
        list.add(this.contentManagerTime1);
        list.add(this.contentManagerTime2);
        list.add(this.contentManagerTime3);
        list.add(this.contentManagerInt);
        list.add(this.contentManagerInt1);
        list.add(this.contentManagerInt2);
        list.add(this.contentManagerInt3);
        list.add(this.contentManagerDouble);
        list.add(this.contentManagerDouble1);
        list.add(this.contentManagerDouble2);
        list.add(this.contentManagerDouble3);
        list.add(this.contentManagerState);
        list.add(this.contentManagerUsersId);
        return new AutoSqlEntity(sb.toString(),list.toArray());
    }
    public ContentManager find(BaseDao dao) {
        return dao.contentManagerFind(this);
    }

    public ContentManager findCache(BaseDao dao, int mm) {
        return dao.contentManagerFindCache(this, mm);
    }

    public List<ContentManager> query(BaseDao dao) {
        return dao.contentManagerQuery(this);
    }

    public List<ContentManager> queryCache(BaseDao dao, int mm) {
        return dao.contentManagerQueryCache(this, mm);
    }

    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {
        return dao.contentManagerQuery(this, page, limit, maxPage, maxLimit);
    }

    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {
        return dao.contentManagerQueryCache(this, page, limit, maxPage, maxLimit, mm);
    }

    public int save(BaseDao dao) {
        return dao.contentManagerInsert(this);
    }

    public int update(BaseDao dao) {
        return dao.contentManagerUpdate(this);
    }

    public int delete(BaseDao dao) {
        return dao.contentManagerDelete(this);
    }

    public int saveAsy(BaseDao dao) {
        return dao.contentManagerInsertAsy(this);
    }

    public int updateAsy(BaseDao dao) {
        return dao.contentManagerUpdateAsy(this);
    }

    public int deleteAsy(BaseDao dao) {
        return dao.contentManagerDeleteAsy(this);
    }
}