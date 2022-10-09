package gzb.db;

import com.google.gson.JsonObject;
import gzb.tools.ListPage;
import gzb.tools.Tools;
import gzb.tools.log.LogImpl;

import java.sql.*;
import java.util.Date;
import java.util.*;

public class DataBaseNew {
    static gzb.tools.log.Log Log=new LogImpl(DataBaseNew.class);
    private DB db = null;
    public Map<String, Object[]> map = new HashMap();
public DataBaseNew(){}

    public DataBaseNew(String db_url, String db_acc, String db_pwd, String db_class, String db_name, String db_auto, String db_threadMax, String db_overtime) {
        try {
            this.db = new DB(db_url, db_acc, db_pwd, db_class, db_name, db_auto, db_threadMax, db_overtime);
            initDataBaseEntity();
        } catch (Exception e) {

        }
    }

    public DataBaseNew(DB db) {
        try {
            this.db = db;
            initDataBaseEntity();
        } catch (Exception e) {

        }
    }
public String test1(String msg,String msg2){
        return msg+" "+msg2;
}
    public final ListPage select(String mapName, String sql, Object[] para, int page, int limit, int maxPae, int maxLimit) {
        if (page > maxPae) {
            page = maxPae;
        }
        if (limit > maxLimit) {
            limit = maxLimit;
        }
        if (page < 1) {
            page = 1;
        }
        if (limit < 1) {
            limit = 1;
        }
        List<JsonObject> list = select(mapName, sql, para);
        ListPage listPage = new ListPage();
        listPage.setList(list);
        listPage.setPage(page);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    /**
     * 执行查询 返回json数据
     */
    public final List<JsonObject> select(String mapName, String sql, Object[] para) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        mapName = mapName.replaceAll(" ", "");
        Object[] columnNames = map.get(mapName);
        List<JsonObject> list = new ArrayList<>();
        JsonObject jsonObject;
        try {
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            String temp;
            sb.append(mapName + ".query:").append(sql).append(";参数:");
            if (para != null) {
                for (int i = 0; i < para.length; i++) {
                    sb.append(para[i]).append(",");
                    ps.setObject(i + 1, para[i].toString());
                }
            }

            long t1 = new Date().getTime();
            rs = ps.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";耗时:").append(t2 - t1).append("毫秒");
            Log.sql(sb.toString());
            String a = "," + Tools.textMid(sql, "select ", " from", 1).replaceAll(" ", "") + ",";
            a = a.toLowerCase();
            while (rs.next()) {
                jsonObject = new JsonObject();
                for (int i = 0; i < columnNames.length; i++) {
                    if (a.equals(",*,") || a.indexOf("," + columnNames[i].toString().toLowerCase() + ",") > -1) {
                        temp = rs.getString(columnNames[i].toString());
                        if (temp != null) {
                            jsonObject.addProperty(columnNames[i].toString(), temp);
                        }
                    }
                }
                list.add(jsonObject);
            }
            return list;
        } catch (Exception e) {
            Log.e(e, sb.toString());
            return null;
        } finally {
            db.close(conn, rs, ps);
        }
    }

    /**
     * 批量 执行插入 修改 删除 操作 返回影响行数
     */
    public final int update_insert_batch(String sql, List<Object[]> list) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("batch:").append(sql);
            conn = db.getConnection();
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(sql);
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) == null) {
                        continue;
                    }
                    for (int i1 = 0; i1 < list.get(i).length; i1++) {
                        ps.setObject(i1 + 1, list.get(i)[i1]);
                    }
                    ps.addBatch();
                }
            }
            long t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            long t2 = new Date().getTime();
            sb.append(";耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID冲突" + sb.toString());
            return -1;
        } catch (Exception e) {
            Log.e(e, sb.toString());
            return -1;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
            db.close(conn, rs, ps);
        }
    }

    /**
     * 执行插入 修改 删除 操作 返回影响行数
     */
    public final int update_insert(String sql, Object[] para) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            sb.append("single:").append(sql);
            conn = db.getConnection();
            ps = conn.prepareStatement(sql);
            if (para != null) {
                for (int i1 = 0; i1 < para.length; i1++) {
                    ps.setObject(i1 + 1, para[i1]);
                }
            }
            long t1 = new Date().getTime();
            int res = ps.executeUpdate();
            long t2 = new Date().getTime();
            sb.append(";耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            Log.sql(sb.toString());
            return res;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID冲突" + sb.toString());
            return -1;
        } catch (Exception e) {
            Log.e(e, sb.toString());
            return -1;
        } finally {
            db.close(conn, rs, ps);
        }
    }


    public final void initDataBaseEntity() throws SQLException {
        Connection conn = db.getConnection();
        DatabaseMetaData meta = conn.getMetaData();
        PreparedStatement ps = null;
        try {
            ResultSet rs = meta.getTables(null, null, null, Tools.toArrayString("TABLE"));
            while (rs.next()) {
                String tbName = rs.getString("TABLE_NAME").toLowerCase();
                ps = conn.prepareStatement("select * from " + tbName + " limit 1");
                ResultSetMetaData col = ps.getMetaData();
                List<String> columnNames = new ArrayList<String>();
                for (int i = 1; i <= col.getColumnCount(); i++) {
                    columnNames.add(col.getColumnName(i));
                }
                map.put(tbName, columnNames.toArray());
                //Log.print(tbName, columnNames);
            }
        }finally {

            db.close(conn,null,ps);
        }

    }


}
