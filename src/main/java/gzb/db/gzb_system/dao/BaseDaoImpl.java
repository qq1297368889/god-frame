package gzb.db.gzb_system.dao;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.entity.ContentManager;
import gzb.db.gzb_system.entity.FileManager;
import gzb.db.gzb_system.entity.GzbApi;
import gzb.db.gzb_system.entity.GzbCache;
import gzb.db.gzb_system.entity.GzbRight;
import gzb.db.gzb_system.entity.GzbUsers;
import gzb.db.gzb_system.entity.Gzbtest;
import gzb.db.gzb_system.entity.Test;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import gzb.tools.ListPage;
import gzb.tools.Tools;
import gzb.tools.cache.Cache;
import gzb.tools.entity.AutoSqlEntity;
import gzb.tools.log.Log;
import gzb.tools.log.LogImpl;

public class BaseDaoImpl implements BaseDao {
    static Log Log=new LogImpl(BaseDaoImpl.class);
    public final List<ContentManager> contentManagerToList(String json) {
        List<ContentManager> list = new ArrayList<>();
        if (json.length()<3){
            return list;
        }
        json = json.substring(2, json.length() - 2);
        String[] ss1 = json.replaceAll("}, \\{", "},{").split("},\\{");
        for (int i = 0; i < ss1.length; i++) {
            list.add(new ContentManager("{" + ss1[i] + "}"));
        }
        return list;
    }
    public final List<FileManager> fileManagerToList(String json) {
        List<FileManager> list = new ArrayList<>();
        if (json.length()<3){
            return list;
        }
        json = json.substring(2, json.length() - 2);
        String[] ss1 = json.replaceAll("}, \\{", "},{").split("},\\{");
        for (int i = 0; i < ss1.length; i++) {
            list.add(new FileManager("{" + ss1[i] + "}"));
        }
        return list;
    }
    public final List<GzbApi> gzbApiToList(String json) {
        List<GzbApi> list = new ArrayList<>();
        if (json.length()<3){
            return list;
        }
        json = json.substring(2, json.length() - 2);
        String[] ss1 = json.replaceAll("}, \\{", "},{").split("},\\{");
        for (int i = 0; i < ss1.length; i++) {
            list.add(new GzbApi("{" + ss1[i] + "}"));
        }
        return list;
    }
    public final List<GzbCache> gzbCacheToList(String json) {
        List<GzbCache> list = new ArrayList<>();
        if (json.length()<3){
            return list;
        }
        json = json.substring(2, json.length() - 2);
        String[] ss1 = json.replaceAll("}, \\{", "},{").split("},\\{");
        for (int i = 0; i < ss1.length; i++) {
            list.add(new GzbCache("{" + ss1[i] + "}"));
        }
        return list;
    }
    public final List<GzbRight> gzbRightToList(String json) {
        List<GzbRight> list = new ArrayList<>();
        if (json.length()<3){
            return list;
        }
        json = json.substring(2, json.length() - 2);
        String[] ss1 = json.replaceAll("}, \\{", "},{").split("},\\{");
        for (int i = 0; i < ss1.length; i++) {
            list.add(new GzbRight("{" + ss1[i] + "}"));
        }
        return list;
    }
    public final List<GzbUsers> gzbUsersToList(String json) {
        List<GzbUsers> list = new ArrayList<>();
        if (json.length()<3){
            return list;
        }
        json = json.substring(2, json.length() - 2);
        String[] ss1 = json.replaceAll("}, \\{", "},{").split("},\\{");
        for (int i = 0; i < ss1.length; i++) {
            list.add(new GzbUsers("{" + ss1[i] + "}"));
        }
        return list;
    }
    public final List<Gzbtest> gzbtestToList(String json) {
        List<Gzbtest> list = new ArrayList<>();
        if (json.length()<3){
            return list;
        }
        json = json.substring(2, json.length() - 2);
        String[] ss1 = json.replaceAll("}, \\{", "},{").split("},\\{");
        for (int i = 0; i < ss1.length; i++) {
            list.add(new Gzbtest("{" + ss1[i] + "}"));
        }
        return list;
    }
    public final List<Test> testToList(String json) {
        List<Test> list = new ArrayList<>();
        if (json.length()<3){
            return list;
        }
        json = json.substring(2, json.length() - 2);
        String[] ss1 = json.replaceAll("}, \\{", "},{").split("},\\{");
        for (int i = 0; i < ss1.length; i++) {
            list.add(new Test("{" + ss1[i] + "}"));
        }
        return list;
    }
    @Override
    public ContentManager contentManagerFind(Long content_manager_id) {
        List<ContentManager> list = contentManagerQuery("select * from "+DataBase.contentManagerName+" where content_manager_id=?", Tools.toArray(content_manager_id));
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public ContentManager contentManagerFind(String sql, Object[] arr) {
        List<ContentManager> list = contentManagerQuery(sql, arr);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public ContentManager contentManagerFind(ContentManager contentManager) {
        AutoSqlEntity ase = contentManager.toSelect();
        List<ContentManager> list = contentManagerQuery(ase.sql, ase.objs);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public ContentManager contentManagerFindCache(ContentManager contentManager,int mm) {
        AutoSqlEntity ase = contentManager.toSelect();
        List<ContentManager> list = contentManagerQueryCache(ase.sql, ase.objs,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    public ContentManager contentManagerFindCache(Long content_manager_id,int mm) {
        List<ContentManager> list = contentManagerQueryCache("select * from "+DataBase.contentManagerName+" where content_manager_id=?", Tools.toArray(content_manager_id),mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public ContentManager contentManagerFindCache(String sql, Object[] arr,int mm) {
        List<ContentManager> list = contentManagerQueryCache(sql, arr,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public List<ContentManager> contentManagerQuery(String sql, Object[] arr) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        if (arr == null) {
            arr = Tools.toArray();
        }
        List<ContentManager> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(";参数:");
        try {
            conn = DataBase.db.getConnection();
            ps = conn.prepareStatement(sql);
            ContentManager en;
            String temp = "";
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]).append(",");
                ps.setObject(i + 1, arr[i].toString());
            }
            long t1 = new Date().getTime();
            rs = ps.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";查询耗时:").append(t2 - t1).append("毫秒");
            t1 = new Date().getTime();
            String a = "," + Tools.textMid(sql, "select ", " from", 1).replaceAll(" ", "") + ",";
            while (rs.next()) {
                en = new ContentManager();
                if (a.equals(",*,") || a.indexOf(",contentManagerId,") > -1 || a.indexOf(",content_manager_id,") > -1) {
                    temp = rs.getString("content_manager_id");
                    if (temp != null) {
                        en.setContentManagerId(java.lang.Long.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerVarchar,") > -1 || a.indexOf(",content_manager_varchar,") > -1) {
                    temp = rs.getString("content_manager_varchar");
                    if (temp != null) {
                        en.setContentManagerVarchar(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerVarchar1,") > -1 || a.indexOf(",content_manager_varchar1,") > -1) {
                    temp = rs.getString("content_manager_varchar1");
                    if (temp != null) {
                        en.setContentManagerVarchar1(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerVarchar2,") > -1 || a.indexOf(",content_manager_varchar2,") > -1) {
                    temp = rs.getString("content_manager_varchar2");
                    if (temp != null) {
                        en.setContentManagerVarchar2(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerVarchar3,") > -1 || a.indexOf(",content_manager_varchar3,") > -1) {
                    temp = rs.getString("content_manager_varchar3");
                    if (temp != null) {
                        en.setContentManagerVarchar3(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerText,") > -1 || a.indexOf(",content_manager_text,") > -1) {
                    temp = rs.getString("content_manager_text");
                    if (temp != null) {
                        en.setContentManagerText(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerText1,") > -1 || a.indexOf(",content_manager_text1,") > -1) {
                    temp = rs.getString("content_manager_text1");
                    if (temp != null) {
                        en.setContentManagerText1(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerText2,") > -1 || a.indexOf(",content_manager_text2,") > -1) {
                    temp = rs.getString("content_manager_text2");
                    if (temp != null) {
                        en.setContentManagerText2(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerText3,") > -1 || a.indexOf(",content_manager_text3,") > -1) {
                    temp = rs.getString("content_manager_text3");
                    if (temp != null) {
                        en.setContentManagerText3(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerTime,") > -1 || a.indexOf(",content_manager_time,") > -1) {
                    temp = rs.getString("content_manager_time");
                    if (temp != null) {
                        en.setContentManagerTime(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerTime1,") > -1 || a.indexOf(",content_manager_time1,") > -1) {
                    temp = rs.getString("content_manager_time1");
                    if (temp != null) {
                        en.setContentManagerTime1(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerTime2,") > -1 || a.indexOf(",content_manager_time2,") > -1) {
                    temp = rs.getString("content_manager_time2");
                    if (temp != null) {
                        en.setContentManagerTime2(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerTime3,") > -1 || a.indexOf(",content_manager_time3,") > -1) {
                    temp = rs.getString("content_manager_time3");
                    if (temp != null) {
                        en.setContentManagerTime3(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerInt,") > -1 || a.indexOf(",content_manager_int,") > -1) {
                    temp = rs.getString("content_manager_int");
                    if (temp != null) {
                        en.setContentManagerInt(java.lang.Integer.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerInt1,") > -1 || a.indexOf(",content_manager_int1,") > -1) {
                    temp = rs.getString("content_manager_int1");
                    if (temp != null) {
                        en.setContentManagerInt1(java.lang.Integer.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerInt2,") > -1 || a.indexOf(",content_manager_int2,") > -1) {
                    temp = rs.getString("content_manager_int2");
                    if (temp != null) {
                        en.setContentManagerInt2(java.lang.Integer.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerInt3,") > -1 || a.indexOf(",content_manager_int3,") > -1) {
                    temp = rs.getString("content_manager_int3");
                    if (temp != null) {
                        en.setContentManagerInt3(java.lang.Integer.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerDouble,") > -1 || a.indexOf(",content_manager_double,") > -1) {
                    temp = rs.getString("content_manager_double");
                    if (temp != null) {
                        en.setContentManagerDouble(java.lang.Double.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerDouble1,") > -1 || a.indexOf(",content_manager_double1,") > -1) {
                    temp = rs.getString("content_manager_double1");
                    if (temp != null) {
                        en.setContentManagerDouble1(java.lang.Double.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerDouble2,") > -1 || a.indexOf(",content_manager_double2,") > -1) {
                    temp = rs.getString("content_manager_double2");
                    if (temp != null) {
                        en.setContentManagerDouble2(java.lang.Double.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerDouble3,") > -1 || a.indexOf(",content_manager_double3,") > -1) {
                    temp = rs.getString("content_manager_double3");
                    if (temp != null) {
                        en.setContentManagerDouble3(java.lang.Double.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerState,") > -1 || a.indexOf(",content_manager_state,") > -1) {
                    temp = rs.getString("content_manager_state");
                    if (temp != null) {
                        en.setContentManagerState(java.lang.Integer.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",contentManagerUsersId,") > -1 || a.indexOf(",content_manager_users_id,") > -1) {
                    temp = rs.getString("content_manager_users_id");
                    if (temp != null) {
                        en.setContentManagerUsersId(java.lang.Long.valueOf(temp));
                    }
                }
                list.add(en);
            }
            t2 = new Date().getTime();
            sb.append(";组装对象耗时:").append(t2 - t1).append("毫秒");
            Log.sql(sb.toString());
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            DataBase.db.close(conn, rs, ps);
        }
        return list;
    }

    @Override
    public List<ContentManager> contentManagerQuery(ContentManager contentManager) {
        AutoSqlEntity ase = contentManager.toSelect();
        return contentManagerQuery(ase.sql, ase.objs);
    }

    @Override
    public ListPage contentManagerQuery(String sql, Object[] arr, int page, int limit) {
        ListPage listPage = new ListPage();
        List<ContentManager> list = contentManagerQuery(sql, arr);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage contentManagerQuery(ContentManager contentManager, int page, int limit, int maxPage, int maxLimit) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page>maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = contentManager.toSelect();
        List<ContentManager> list = contentManagerQuery(ase.sql + " limit "+(maxPage*limit), ase.objs);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public List<ContentManager> contentManagerQueryCache(String sql, Object[] arr, int mm) {
        List<ContentManager> list = new ArrayList<>();
        String key = DataBase.db.getKey(sql, arr);
        String str = Cache.gzbCache.get(key);
        if (str == null) {
            Log.sql("Miss:" + key);
            list = contentManagerQuery(sql, arr);
            Cache.gzbCache.set(key, list.toString(), mm);
        } else {
            Log.sql("Hit:" + key);
            list = contentManagerToList(str);
        }
        return list;
    }
    @Override
    public List<ContentManager> contentManagerQueryCache(ContentManager contentManager, int mm) {
        AutoSqlEntity ase = contentManager.toSelect();
        return contentManagerQueryCache(ase.sql, ase.objs, mm);
    }

    @Override
    public ListPage contentManagerQueryCache(String sql, Object[] arr, int page, int limit, int mm) {
        ListPage listPage = new ListPage();
        List<ContentManager> list = contentManagerQueryCache(sql, arr, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage contentManagerQueryCache(ContentManager contentManager, int page, int limit, int maxPage, int maxLimit, int mm) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page > maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = contentManager.toSelect();
        List<ContentManager> list = contentManagerQueryCache(ase.sql+ " limit "+(maxPage*limit), ase.objs, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public int contentManagerDelete(ContentManager contentManager) {
        AutoSqlEntity ase = contentManager.toDelete();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int contentManagerInsert(ContentManager contentManager) {
        contentManager.setContentManagerId(DataBase.db.getOnlyIdDistributed());
        AutoSqlEntity ase = contentManager.toInsert();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int contentManagerUpdate(ContentManager contentManager) {
        AutoSqlEntity ase = contentManager.toUpdate();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }


    @Override
    public int contentManagerInsertAsy(ContentManager contentManager) {
        return contentManagerInsertAsy(contentManager, true);
    }

    @Override
    public int contentManagerInsertAsy(ContentManager contentManager, boolean auto) {
        if (auto) {
            contentManager.setContentManagerId(DataBase.db.getOnlyIdDistributed());
        }
        AutoSqlEntity ase = contentManager.toInsert();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int contentManagerDeleteAsy(ContentManager contentManager) {
        AutoSqlEntity ase = contentManager.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int contentManagerUpdateAsy(ContentManager contentManager) {
        AutoSqlEntity ase = contentManager.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int contentManagerBatch(List<ContentManager> list) {
        return contentManagerBatch(list, true);
    }

    @Override
    public int contentManagerBatch(List<ContentManager> list, boolean autoId) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (autoId) {
                    list.get(i).setContentManagerId(DataBase.db.getOnlyIdDistributed());
                }
                AutoSqlEntity ase = list.get(i).toInsert();
                if (i == 0) {
                    sb.append("Batch:").append(ase.sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(ase.sql);
                }
                for (int i1 = 0; i1 < ase.objs.length; i1++) {
                    ps.setObject(i1 + 1, ase.objs[i1]);
                }
                sb.append(DataBase.db.getKey(ase.sql, ase.objs));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public int contentManagerBatch(String sql, List<Object[]> list) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sb.append("Batch:").append(sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(sql);
                }
                for (int i1 = 0; i1 < list.get(i).length; i1++) {
                    ps.setObject(i1 + 1, list.get(i)[i1]);
                }
                sb.append(DataBase.db.getKey(sql, list.get(i)));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    @Override
    public FileManager fileManagerFind(Long file_manager_id) {
        List<FileManager> list = fileManagerQuery("select * from "+DataBase.fileManagerName+" where file_manager_id=?", Tools.toArray(file_manager_id));
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public FileManager fileManagerFind(String sql, Object[] arr) {
        List<FileManager> list = fileManagerQuery(sql, arr);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public FileManager fileManagerFind(FileManager fileManager) {
        AutoSqlEntity ase = fileManager.toSelect();
        List<FileManager> list = fileManagerQuery(ase.sql, ase.objs);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public FileManager fileManagerFindCache(FileManager fileManager,int mm) {
        AutoSqlEntity ase = fileManager.toSelect();
        List<FileManager> list = fileManagerQueryCache(ase.sql, ase.objs,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    public FileManager fileManagerFindCache(Long file_manager_id,int mm) {
        List<FileManager> list = fileManagerQueryCache("select * from "+DataBase.fileManagerName+" where file_manager_id=?", Tools.toArray(file_manager_id),mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public FileManager fileManagerFindCache(String sql, Object[] arr,int mm) {
        List<FileManager> list = fileManagerQueryCache(sql, arr,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public List<FileManager> fileManagerQuery(String sql, Object[] arr) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        if (arr == null) {
            arr = Tools.toArray();
        }
        List<FileManager> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(";参数:");
        try {
            conn = DataBase.db.getConnection();
            ps = conn.prepareStatement(sql);
            FileManager en;
            String temp = "";
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]).append(",");
                ps.setObject(i + 1, arr[i].toString());
            }
            long t1 = new Date().getTime();
            rs = ps.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";查询耗时:").append(t2 - t1).append("毫秒");
            t1 = new Date().getTime();
            String a = "," + Tools.textMid(sql, "select ", " from", 1).replaceAll(" ", "") + ",";
            while (rs.next()) {
                en = new FileManager();
                if (a.equals(",*,") || a.indexOf(",fileManagerId,") > -1 || a.indexOf(",file_manager_id,") > -1) {
                    temp = rs.getString("file_manager_id");
                    if (temp != null) {
                        en.setFileManagerId(java.lang.Long.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",fileManagerMd5,") > -1 || a.indexOf(",file_manager_md5,") > -1) {
                    temp = rs.getString("file_manager_md5");
                    if (temp != null) {
                        en.setFileManagerMd5(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",fileManagerName,") > -1 || a.indexOf(",file_manager_name,") > -1) {
                    temp = rs.getString("file_manager_name");
                    if (temp != null) {
                        en.setFileManagerName(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",fileManagerTime,") > -1 || a.indexOf(",file_manager_time,") > -1) {
                    temp = rs.getString("file_manager_time");
                    if (temp != null) {
                        en.setFileManagerTime(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",fileManagerState,") > -1 || a.indexOf(",file_manager_state,") > -1) {
                    temp = rs.getString("file_manager_state");
                    if (temp != null) {
                        en.setFileManagerState(java.lang.Integer.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",fileManagerReadNum,") > -1 || a.indexOf(",file_manager_read_num,") > -1) {
                    temp = rs.getString("file_manager_read_num");
                    if (temp != null) {
                        en.setFileManagerReadNum(java.lang.Integer.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",fileManagerType,") > -1 || a.indexOf(",file_manager_type,") > -1) {
                    temp = rs.getString("file_manager_type");
                    if (temp != null) {
                        en.setFileManagerType(java.lang.String.valueOf(temp));
                    }
                }
                list.add(en);
            }
            t2 = new Date().getTime();
            sb.append(";组装对象耗时:").append(t2 - t1).append("毫秒");
            Log.sql(sb.toString());
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            DataBase.db.close(conn, rs, ps);
        }
        return list;
    }

    @Override
    public List<FileManager> fileManagerQuery(FileManager fileManager) {
        AutoSqlEntity ase = fileManager.toSelect();
        return fileManagerQuery(ase.sql, ase.objs);
    }

    @Override
    public ListPage fileManagerQuery(String sql, Object[] arr, int page, int limit) {
        ListPage listPage = new ListPage();
        List<FileManager> list = fileManagerQuery(sql, arr);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage fileManagerQuery(FileManager fileManager, int page, int limit, int maxPage, int maxLimit) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page>maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = fileManager.toSelect();
        List<FileManager> list = fileManagerQuery(ase.sql + " limit "+(maxPage*limit), ase.objs);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public List<FileManager> fileManagerQueryCache(String sql, Object[] arr, int mm) {
        List<FileManager> list = new ArrayList<>();
        String key = DataBase.db.getKey(sql, arr);
        String str = Cache.gzbCache.get(key);
        if (str == null) {
            Log.sql("Miss:" + key);
            list = fileManagerQuery(sql, arr);
            Cache.gzbCache.set(key, list.toString(), mm);
        } else {
            Log.sql("Hit:" + key);
            list = fileManagerToList(str);
        }
        return list;
    }
    @Override
    public List<FileManager> fileManagerQueryCache(FileManager fileManager, int mm) {
        AutoSqlEntity ase = fileManager.toSelect();
        return fileManagerQueryCache(ase.sql, ase.objs, mm);
    }

    @Override
    public ListPage fileManagerQueryCache(String sql, Object[] arr, int page, int limit, int mm) {
        ListPage listPage = new ListPage();
        List<FileManager> list = fileManagerQueryCache(sql, arr, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage fileManagerQueryCache(FileManager fileManager, int page, int limit, int maxPage, int maxLimit, int mm) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page > maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = fileManager.toSelect();
        List<FileManager> list = fileManagerQueryCache(ase.sql+ " limit "+(maxPage*limit), ase.objs, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public int fileManagerDelete(FileManager fileManager) {
        AutoSqlEntity ase = fileManager.toDelete();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int fileManagerInsert(FileManager fileManager) {
        fileManager.setFileManagerId(DataBase.db.getOnlyIdDistributed());
        AutoSqlEntity ase = fileManager.toInsert();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int fileManagerUpdate(FileManager fileManager) {
        AutoSqlEntity ase = fileManager.toUpdate();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }


    @Override
    public int fileManagerInsertAsy(FileManager fileManager) {
        return fileManagerInsertAsy(fileManager, true);
    }

    @Override
    public int fileManagerInsertAsy(FileManager fileManager, boolean auto) {
        if (auto) {
            fileManager.setFileManagerId(DataBase.db.getOnlyIdDistributed());
        }
        AutoSqlEntity ase = fileManager.toInsert();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int fileManagerDeleteAsy(FileManager fileManager) {
        AutoSqlEntity ase = fileManager.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int fileManagerUpdateAsy(FileManager fileManager) {
        AutoSqlEntity ase = fileManager.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int fileManagerBatch(List<FileManager> list) {
        return fileManagerBatch(list, true);
    }

    @Override
    public int fileManagerBatch(List<FileManager> list, boolean autoId) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (autoId) {
                    list.get(i).setFileManagerId(DataBase.db.getOnlyIdDistributed());
                }
                AutoSqlEntity ase = list.get(i).toInsert();
                if (i == 0) {
                    sb.append("Batch:").append(ase.sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(ase.sql);
                }
                for (int i1 = 0; i1 < ase.objs.length; i1++) {
                    ps.setObject(i1 + 1, ase.objs[i1]);
                }
                sb.append(DataBase.db.getKey(ase.sql, ase.objs));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public int fileManagerBatch(String sql, List<Object[]> list) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sb.append("Batch:").append(sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(sql);
                }
                for (int i1 = 0; i1 < list.get(i).length; i1++) {
                    ps.setObject(i1 + 1, list.get(i)[i1]);
                }
                sb.append(DataBase.db.getKey(sql, list.get(i)));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    @Override
    public GzbApi gzbApiFind(Long gzb_api_id) {
        List<GzbApi> list = gzbApiQuery("select * from "+DataBase.gzbApiName+" where gzb_api_id=?", Tools.toArray(gzb_api_id));
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbApi gzbApiFind(String sql, Object[] arr) {
        List<GzbApi> list = gzbApiQuery(sql, arr);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbApi gzbApiFind(GzbApi gzbApi) {
        AutoSqlEntity ase = gzbApi.toSelect();
        List<GzbApi> list = gzbApiQuery(ase.sql, ase.objs);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbApi gzbApiFindCache(GzbApi gzbApi,int mm) {
        AutoSqlEntity ase = gzbApi.toSelect();
        List<GzbApi> list = gzbApiQueryCache(ase.sql, ase.objs,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    public GzbApi gzbApiFindCache(Long gzb_api_id,int mm) {
        List<GzbApi> list = gzbApiQueryCache("select * from "+DataBase.gzbApiName+" where gzb_api_id=?", Tools.toArray(gzb_api_id),mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbApi gzbApiFindCache(String sql, Object[] arr,int mm) {
        List<GzbApi> list = gzbApiQueryCache(sql, arr,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public List<GzbApi> gzbApiQuery(String sql, Object[] arr) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        if (arr == null) {
            arr = Tools.toArray();
        }
        List<GzbApi> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(";参数:");
        try {
            conn = DataBase.db.getConnection();
            ps = conn.prepareStatement(sql);
            GzbApi en;
            String temp = "";
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]).append(",");
                ps.setObject(i + 1, arr[i].toString());
            }
            long t1 = new Date().getTime();
            rs = ps.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";查询耗时:").append(t2 - t1).append("毫秒");
            t1 = new Date().getTime();
            String a = "," + Tools.textMid(sql, "select ", " from", 1).replaceAll(" ", "") + ",";
            while (rs.next()) {
                en = new GzbApi();
                if (a.equals(",*,") || a.indexOf(",gzbApiId,") > -1 || a.indexOf(",gzb_api_id,") > -1) {
                    temp = rs.getString("gzb_api_id");
                    if (temp != null) {
                        en.setGzbApiId(java.lang.Long.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbApiName,") > -1 || a.indexOf(",gzb_api_name,") > -1) {
                    temp = rs.getString("gzb_api_name");
                    if (temp != null) {
                        en.setGzbApiName(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbApiState,") > -1 || a.indexOf(",gzb_api_state,") > -1) {
                    temp = rs.getString("gzb_api_state");
                    if (temp != null) {
                        en.setGzbApiState(java.lang.Integer.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbApiCode,") > -1 || a.indexOf(",gzb_api_code,") > -1) {
                    temp = rs.getString("gzb_api_code");
                    if (temp != null) {
                        en.setGzbApiCode(java.lang.String.valueOf(temp));
                    }
                }
                list.add(en);
            }
            t2 = new Date().getTime();
            sb.append(";组装对象耗时:").append(t2 - t1).append("毫秒");
            Log.sql(sb.toString());
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            DataBase.db.close(conn, rs, ps);
        }
        return list;
    }

    @Override
    public List<GzbApi> gzbApiQuery(GzbApi gzbApi) {
        AutoSqlEntity ase = gzbApi.toSelect();
        return gzbApiQuery(ase.sql, ase.objs);
    }

    @Override
    public ListPage gzbApiQuery(String sql, Object[] arr, int page, int limit) {
        ListPage listPage = new ListPage();
        List<GzbApi> list = gzbApiQuery(sql, arr);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbApiQuery(GzbApi gzbApi, int page, int limit, int maxPage, int maxLimit) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page>maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbApi.toSelect();
        List<GzbApi> list = gzbApiQuery(ase.sql + " limit "+(maxPage*limit), ase.objs);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public List<GzbApi> gzbApiQueryCache(String sql, Object[] arr, int mm) {
        List<GzbApi> list = new ArrayList<>();
        String key = DataBase.db.getKey(sql, arr);
        String str = Cache.gzbCache.get(key);
        if (str == null) {
            Log.sql("Miss:" + key);
            list = gzbApiQuery(sql, arr);
            Cache.gzbCache.set(key, list.toString(), mm);
        } else {
            Log.sql("Hit:" + key);
            list = gzbApiToList(str);
        }
        return list;
    }
    @Override
    public List<GzbApi> gzbApiQueryCache(GzbApi gzbApi, int mm) {
        AutoSqlEntity ase = gzbApi.toSelect();
        return gzbApiQueryCache(ase.sql, ase.objs, mm);
    }

    @Override
    public ListPage gzbApiQueryCache(String sql, Object[] arr, int page, int limit, int mm) {
        ListPage listPage = new ListPage();
        List<GzbApi> list = gzbApiQueryCache(sql, arr, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbApiQueryCache(GzbApi gzbApi, int page, int limit, int maxPage, int maxLimit, int mm) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page > maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbApi.toSelect();
        List<GzbApi> list = gzbApiQueryCache(ase.sql+ " limit "+(maxPage*limit), ase.objs, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public int gzbApiDelete(GzbApi gzbApi) {
        AutoSqlEntity ase = gzbApi.toDelete();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbApiInsert(GzbApi gzbApi) {
        gzbApi.setGzbApiId(DataBase.db.getOnlyIdDistributed());
        AutoSqlEntity ase = gzbApi.toInsert();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbApiUpdate(GzbApi gzbApi) {
        AutoSqlEntity ase = gzbApi.toUpdate();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }


    @Override
    public int gzbApiInsertAsy(GzbApi gzbApi) {
        return gzbApiInsertAsy(gzbApi, true);
    }

    @Override
    public int gzbApiInsertAsy(GzbApi gzbApi, boolean auto) {
        if (auto) {
            gzbApi.setGzbApiId(DataBase.db.getOnlyIdDistributed());
        }
        AutoSqlEntity ase = gzbApi.toInsert();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbApiDeleteAsy(GzbApi gzbApi) {
        AutoSqlEntity ase = gzbApi.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbApiUpdateAsy(GzbApi gzbApi) {
        AutoSqlEntity ase = gzbApi.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbApiBatch(List<GzbApi> list) {
        return gzbApiBatch(list, true);
    }

    @Override
    public int gzbApiBatch(List<GzbApi> list, boolean autoId) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (autoId) {
                    list.get(i).setGzbApiId(DataBase.db.getOnlyIdDistributed());
                }
                AutoSqlEntity ase = list.get(i).toInsert();
                if (i == 0) {
                    sb.append("Batch:").append(ase.sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(ase.sql);
                }
                for (int i1 = 0; i1 < ase.objs.length; i1++) {
                    ps.setObject(i1 + 1, ase.objs[i1]);
                }
                sb.append(DataBase.db.getKey(ase.sql, ase.objs));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public int gzbApiBatch(String sql, List<Object[]> list) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sb.append("Batch:").append(sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(sql);
                }
                for (int i1 = 0; i1 < list.get(i).length; i1++) {
                    ps.setObject(i1 + 1, list.get(i)[i1]);
                }
                sb.append(DataBase.db.getKey(sql, list.get(i)));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    @Override
    public GzbCache gzbCacheFind(Long gzb_cache_id) {
        List<GzbCache> list = gzbCacheQuery("select * from "+DataBase.gzbCacheName+" where gzb_cache_id=?", Tools.toArray(gzb_cache_id));
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbCache gzbCacheFind(String sql, Object[] arr) {
        List<GzbCache> list = gzbCacheQuery(sql, arr);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbCache gzbCacheFind(GzbCache gzbCache) {
        AutoSqlEntity ase = gzbCache.toSelect();
        List<GzbCache> list = gzbCacheQuery(ase.sql, ase.objs);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbCache gzbCacheFindCache(GzbCache gzbCache,int mm) {
        AutoSqlEntity ase = gzbCache.toSelect();
        List<GzbCache> list = gzbCacheQueryCache(ase.sql, ase.objs,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    public GzbCache gzbCacheFindCache(Long gzb_cache_id,int mm) {
        List<GzbCache> list = gzbCacheQueryCache("select * from "+DataBase.gzbCacheName+" where gzb_cache_id=?", Tools.toArray(gzb_cache_id),mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbCache gzbCacheFindCache(String sql, Object[] arr,int mm) {
        List<GzbCache> list = gzbCacheQueryCache(sql, arr,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public List<GzbCache> gzbCacheQuery(String sql, Object[] arr) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        if (arr == null) {
            arr = Tools.toArray();
        }
        List<GzbCache> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(";参数:");
        try {
            conn = DataBase.db.getConnection();
            ps = conn.prepareStatement(sql);
            GzbCache en;
            String temp = "";
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]).append(",");
                ps.setObject(i + 1, arr[i].toString());
            }
            long t1 = new Date().getTime();
            rs = ps.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";查询耗时:").append(t2 - t1).append("毫秒");
            t1 = new Date().getTime();
            String a = "," + Tools.textMid(sql, "select ", " from", 1).replaceAll(" ", "") + ",";
            while (rs.next()) {
                en = new GzbCache();
                if (a.equals(",*,") || a.indexOf(",gzbCacheId,") > -1 || a.indexOf(",gzb_cache_id,") > -1) {
                    temp = rs.getString("gzb_cache_id");
                    if (temp != null) {
                        en.setGzbCacheId(java.lang.Long.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbCacheKey,") > -1 || a.indexOf(",gzb_cache_key,") > -1) {
                    temp = rs.getString("gzb_cache_key");
                    if (temp != null) {
                        en.setGzbCacheKey(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbCacheVal,") > -1 || a.indexOf(",gzb_cache_val,") > -1) {
                    temp = rs.getString("gzb_cache_val");
                    if (temp != null) {
                        en.setGzbCacheVal(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbCacheEndTime,") > -1 || a.indexOf(",gzb_cache_end_time,") > -1) {
                    temp = rs.getString("gzb_cache_end_time");
                    if (temp != null) {
                        en.setGzbCacheEndTime(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbCacheNewTime,") > -1 || a.indexOf(",gzb_cache_new_time,") > -1) {
                    temp = rs.getString("gzb_cache_new_time");
                    if (temp != null) {
                        en.setGzbCacheNewTime(java.lang.String.valueOf(temp));
                    }
                }
                list.add(en);
            }
            t2 = new Date().getTime();
            sb.append(";组装对象耗时:").append(t2 - t1).append("毫秒");
            Log.sql(sb.toString());
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            DataBase.db.close(conn, rs, ps);
        }
        return list;
    }

    @Override
    public List<GzbCache> gzbCacheQuery(GzbCache gzbCache) {
        AutoSqlEntity ase = gzbCache.toSelect();
        return gzbCacheQuery(ase.sql, ase.objs);
    }

    @Override
    public ListPage gzbCacheQuery(String sql, Object[] arr, int page, int limit) {
        ListPage listPage = new ListPage();
        List<GzbCache> list = gzbCacheQuery(sql, arr);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbCacheQuery(GzbCache gzbCache, int page, int limit, int maxPage, int maxLimit) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page>maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbCache.toSelect();
        List<GzbCache> list = gzbCacheQuery(ase.sql + " limit "+(maxPage*limit), ase.objs);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public List<GzbCache> gzbCacheQueryCache(String sql, Object[] arr, int mm) {
        List<GzbCache> list = new ArrayList<>();
        String key = DataBase.db.getKey(sql, arr);
        String str = Cache.gzbCache.get(key);
        if (str == null) {
            Log.sql("Miss:" + key);
            list = gzbCacheQuery(sql, arr);
            Cache.gzbCache.set(key, list.toString(), mm);
        } else {
            Log.sql("Hit:" + key);
            list = gzbCacheToList(str);
        }
        return list;
    }
    @Override
    public List<GzbCache> gzbCacheQueryCache(GzbCache gzbCache, int mm) {
        AutoSqlEntity ase = gzbCache.toSelect();
        return gzbCacheQueryCache(ase.sql, ase.objs, mm);
    }

    @Override
    public ListPage gzbCacheQueryCache(String sql, Object[] arr, int page, int limit, int mm) {
        ListPage listPage = new ListPage();
        List<GzbCache> list = gzbCacheQueryCache(sql, arr, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbCacheQueryCache(GzbCache gzbCache, int page, int limit, int maxPage, int maxLimit, int mm) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page > maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbCache.toSelect();
        List<GzbCache> list = gzbCacheQueryCache(ase.sql+ " limit "+(maxPage*limit), ase.objs, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public int gzbCacheDelete(GzbCache gzbCache) {
        AutoSqlEntity ase = gzbCache.toDelete();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbCacheInsert(GzbCache gzbCache) {
        gzbCache.setGzbCacheId(DataBase.db.getOnlyIdDistributed());
        AutoSqlEntity ase = gzbCache.toInsert();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbCacheUpdate(GzbCache gzbCache) {
        AutoSqlEntity ase = gzbCache.toUpdate();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }


    @Override
    public int gzbCacheInsertAsy(GzbCache gzbCache) {
        return gzbCacheInsertAsy(gzbCache, true);
    }

    @Override
    public int gzbCacheInsertAsy(GzbCache gzbCache, boolean auto) {
        if (auto) {
            gzbCache.setGzbCacheId(DataBase.db.getOnlyIdDistributed());
        }
        AutoSqlEntity ase = gzbCache.toInsert();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbCacheDeleteAsy(GzbCache gzbCache) {
        AutoSqlEntity ase = gzbCache.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbCacheUpdateAsy(GzbCache gzbCache) {
        AutoSqlEntity ase = gzbCache.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbCacheBatch(List<GzbCache> list) {
        return gzbCacheBatch(list, true);
    }

    @Override
    public int gzbCacheBatch(List<GzbCache> list, boolean autoId) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (autoId) {
                    list.get(i).setGzbCacheId(DataBase.db.getOnlyIdDistributed());
                }
                AutoSqlEntity ase = list.get(i).toInsert();
                if (i == 0) {
                    sb.append("Batch:").append(ase.sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(ase.sql);
                }
                for (int i1 = 0; i1 < ase.objs.length; i1++) {
                    ps.setObject(i1 + 1, ase.objs[i1]);
                }
                sb.append(DataBase.db.getKey(ase.sql, ase.objs));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public int gzbCacheBatch(String sql, List<Object[]> list) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sb.append("Batch:").append(sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(sql);
                }
                for (int i1 = 0; i1 < list.get(i).length; i1++) {
                    ps.setObject(i1 + 1, list.get(i)[i1]);
                }
                sb.append(DataBase.db.getKey(sql, list.get(i)));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    @Override
    public GzbRight gzbRightFind(Long gzb_right_id) {
        List<GzbRight> list = gzbRightQuery("select * from "+DataBase.gzbRightName+" where gzb_right_id=?", Tools.toArray(gzb_right_id));
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbRight gzbRightFind(String sql, Object[] arr) {
        List<GzbRight> list = gzbRightQuery(sql, arr);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbRight gzbRightFind(GzbRight gzbRight) {
        AutoSqlEntity ase = gzbRight.toSelect();
        List<GzbRight> list = gzbRightQuery(ase.sql, ase.objs);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbRight gzbRightFindCache(GzbRight gzbRight,int mm) {
        AutoSqlEntity ase = gzbRight.toSelect();
        List<GzbRight> list = gzbRightQueryCache(ase.sql, ase.objs,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    public GzbRight gzbRightFindCache(Long gzb_right_id,int mm) {
        List<GzbRight> list = gzbRightQueryCache("select * from "+DataBase.gzbRightName+" where gzb_right_id=?", Tools.toArray(gzb_right_id),mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbRight gzbRightFindCache(String sql, Object[] arr,int mm) {
        List<GzbRight> list = gzbRightQueryCache(sql, arr,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public List<GzbRight> gzbRightQuery(String sql, Object[] arr) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        if (arr == null) {
            arr = Tools.toArray();
        }
        List<GzbRight> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(";参数:");
        try {
            conn = DataBase.db.getConnection();
            ps = conn.prepareStatement(sql);
            GzbRight en;
            String temp = "";
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]).append(",");
                ps.setObject(i + 1, arr[i].toString());
            }
            long t1 = new Date().getTime();
            rs = ps.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";查询耗时:").append(t2 - t1).append("毫秒");
            t1 = new Date().getTime();
            String a = "," + Tools.textMid(sql, "select ", " from", 1).replaceAll(" ", "") + ",";
            while (rs.next()) {
                en = new GzbRight();
                if (a.equals(",*,") || a.indexOf(",gzbRightId,") > -1 || a.indexOf(",gzb_right_id,") > -1) {
                    temp = rs.getString("gzb_right_id");
                    if (temp != null) {
                        en.setGzbRightId(java.lang.Long.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbRightUsersId,") > -1 || a.indexOf(",gzb_right_users_id,") > -1) {
                    temp = rs.getString("gzb_right_users_id");
                    if (temp != null) {
                        en.setGzbRightUsersId(java.lang.Long.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbRightApiId,") > -1 || a.indexOf(",gzb_right_api_id,") > -1) {
                    temp = rs.getString("gzb_right_api_id");
                    if (temp != null) {
                        en.setGzbRightApiId(java.lang.Long.valueOf(temp));
                    }
                }
                list.add(en);
            }
            t2 = new Date().getTime();
            sb.append(";组装对象耗时:").append(t2 - t1).append("毫秒");
            Log.sql(sb.toString());
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            DataBase.db.close(conn, rs, ps);
        }
        return list;
    }

    @Override
    public List<GzbRight> gzbRightQuery(GzbRight gzbRight) {
        AutoSqlEntity ase = gzbRight.toSelect();
        return gzbRightQuery(ase.sql, ase.objs);
    }

    @Override
    public ListPage gzbRightQuery(String sql, Object[] arr, int page, int limit) {
        ListPage listPage = new ListPage();
        List<GzbRight> list = gzbRightQuery(sql, arr);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbRightQuery(GzbRight gzbRight, int page, int limit, int maxPage, int maxLimit) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page>maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbRight.toSelect();
        List<GzbRight> list = gzbRightQuery(ase.sql + " limit "+(maxPage*limit), ase.objs);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public List<GzbRight> gzbRightQueryCache(String sql, Object[] arr, int mm) {
        List<GzbRight> list = new ArrayList<>();
        String key = DataBase.db.getKey(sql, arr);
        String str = Cache.gzbCache.get(key);
        if (str == null) {
            Log.sql("Miss:" + key);
            list = gzbRightQuery(sql, arr);
            Cache.gzbCache.set(key, list.toString(), mm);
        } else {
            Log.sql("Hit:" + key);
            list = gzbRightToList(str);
        }
        return list;
    }
    @Override
    public List<GzbRight> gzbRightQueryCache(GzbRight gzbRight, int mm) {
        AutoSqlEntity ase = gzbRight.toSelect();
        return gzbRightQueryCache(ase.sql, ase.objs, mm);
    }

    @Override
    public ListPage gzbRightQueryCache(String sql, Object[] arr, int page, int limit, int mm) {
        ListPage listPage = new ListPage();
        List<GzbRight> list = gzbRightQueryCache(sql, arr, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbRightQueryCache(GzbRight gzbRight, int page, int limit, int maxPage, int maxLimit, int mm) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page > maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbRight.toSelect();
        List<GzbRight> list = gzbRightQueryCache(ase.sql+ " limit "+(maxPage*limit), ase.objs, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public int gzbRightDelete(GzbRight gzbRight) {
        AutoSqlEntity ase = gzbRight.toDelete();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbRightInsert(GzbRight gzbRight) {
        gzbRight.setGzbRightId(DataBase.db.getOnlyIdDistributed());
        AutoSqlEntity ase = gzbRight.toInsert();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbRightUpdate(GzbRight gzbRight) {
        AutoSqlEntity ase = gzbRight.toUpdate();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }


    @Override
    public int gzbRightInsertAsy(GzbRight gzbRight) {
        return gzbRightInsertAsy(gzbRight, true);
    }

    @Override
    public int gzbRightInsertAsy(GzbRight gzbRight, boolean auto) {
        if (auto) {
            gzbRight.setGzbRightId(DataBase.db.getOnlyIdDistributed());
        }
        AutoSqlEntity ase = gzbRight.toInsert();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbRightDeleteAsy(GzbRight gzbRight) {
        AutoSqlEntity ase = gzbRight.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbRightUpdateAsy(GzbRight gzbRight) {
        AutoSqlEntity ase = gzbRight.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbRightBatch(List<GzbRight> list) {
        return gzbRightBatch(list, true);
    }

    @Override
    public int gzbRightBatch(List<GzbRight> list, boolean autoId) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (autoId) {
                    list.get(i).setGzbRightId(DataBase.db.getOnlyIdDistributed());
                }
                AutoSqlEntity ase = list.get(i).toInsert();
                if (i == 0) {
                    sb.append("Batch:").append(ase.sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(ase.sql);
                }
                for (int i1 = 0; i1 < ase.objs.length; i1++) {
                    ps.setObject(i1 + 1, ase.objs[i1]);
                }
                sb.append(DataBase.db.getKey(ase.sql, ase.objs));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public int gzbRightBatch(String sql, List<Object[]> list) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sb.append("Batch:").append(sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(sql);
                }
                for (int i1 = 0; i1 < list.get(i).length; i1++) {
                    ps.setObject(i1 + 1, list.get(i)[i1]);
                }
                sb.append(DataBase.db.getKey(sql, list.get(i)));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    @Override
    public GzbUsers gzbUsersFind(Long gzb_users_id) {
        List<GzbUsers> list = gzbUsersQuery("select * from "+DataBase.gzbUsersName+" where gzb_users_id=?", Tools.toArray(gzb_users_id));
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbUsers gzbUsersFind(String sql, Object[] arr) {
        List<GzbUsers> list = gzbUsersQuery(sql, arr);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbUsers gzbUsersFind(GzbUsers gzbUsers) {
        AutoSqlEntity ase = gzbUsers.toSelect();
        List<GzbUsers> list = gzbUsersQuery(ase.sql, ase.objs);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbUsers gzbUsersFindCache(GzbUsers gzbUsers,int mm) {
        AutoSqlEntity ase = gzbUsers.toSelect();
        List<GzbUsers> list = gzbUsersQueryCache(ase.sql, ase.objs,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    public GzbUsers gzbUsersFindCache(Long gzb_users_id,int mm) {
        List<GzbUsers> list = gzbUsersQueryCache("select * from "+DataBase.gzbUsersName+" where gzb_users_id=?", Tools.toArray(gzb_users_id),mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbUsers gzbUsersFindCache(String sql, Object[] arr,int mm) {
        List<GzbUsers> list = gzbUsersQueryCache(sql, arr,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public List<GzbUsers> gzbUsersQuery(String sql, Object[] arr) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        if (arr == null) {
            arr = Tools.toArray();
        }
        List<GzbUsers> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(";参数:");
        try {
            conn = DataBase.db.getConnection();
            ps = conn.prepareStatement(sql);
            GzbUsers en;
            String temp = "";
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]).append(",");
                ps.setObject(i + 1, arr[i].toString());
            }
            long t1 = new Date().getTime();
            rs = ps.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";查询耗时:").append(t2 - t1).append("毫秒");
            t1 = new Date().getTime();
            String a = "," + Tools.textMid(sql, "select ", " from", 1).replaceAll(" ", "") + ",";
            while (rs.next()) {
                en = new GzbUsers();
                if (a.equals(",*,") || a.indexOf(",gzbUsersId,") > -1 || a.indexOf(",gzb_users_id,") > -1) {
                    temp = rs.getString("gzb_users_id");
                    if (temp != null) {
                        en.setGzbUsersId(java.lang.Long.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbUsersAcc,") > -1 || a.indexOf(",gzb_users_acc,") > -1) {
                    temp = rs.getString("gzb_users_acc");
                    if (temp != null) {
                        en.setGzbUsersAcc(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbUsersPwd,") > -1 || a.indexOf(",gzb_users_pwd,") > -1) {
                    temp = rs.getString("gzb_users_pwd");
                    if (temp != null) {
                        en.setGzbUsersPwd(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbUsersPhone,") > -1 || a.indexOf(",gzb_users_phone,") > -1) {
                    temp = rs.getString("gzb_users_phone");
                    if (temp != null) {
                        en.setGzbUsersPhone(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbUsersMailbox,") > -1 || a.indexOf(",gzb_users_mailbox,") > -1) {
                    temp = rs.getString("gzb_users_mailbox");
                    if (temp != null) {
                        en.setGzbUsersMailbox(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbUsersTime,") > -1 || a.indexOf(",gzb_users_time,") > -1) {
                    temp = rs.getString("gzb_users_time");
                    if (temp != null) {
                        en.setGzbUsersTime(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbUsersState,") > -1 || a.indexOf(",gzb_users_state,") > -1) {
                    temp = rs.getString("gzb_users_state");
                    if (temp != null) {
                        en.setGzbUsersState(java.lang.Integer.valueOf(temp));
                    }
                }
                list.add(en);
            }
            t2 = new Date().getTime();
            sb.append(";组装对象耗时:").append(t2 - t1).append("毫秒");
            Log.sql(sb.toString());
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            DataBase.db.close(conn, rs, ps);
        }
        return list;
    }

    @Override
    public List<GzbUsers> gzbUsersQuery(GzbUsers gzbUsers) {
        AutoSqlEntity ase = gzbUsers.toSelect();
        return gzbUsersQuery(ase.sql, ase.objs);
    }

    @Override
    public ListPage gzbUsersQuery(String sql, Object[] arr, int page, int limit) {
        ListPage listPage = new ListPage();
        List<GzbUsers> list = gzbUsersQuery(sql, arr);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbUsersQuery(GzbUsers gzbUsers, int page, int limit, int maxPage, int maxLimit) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page>maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbUsers.toSelect();
        List<GzbUsers> list = gzbUsersQuery(ase.sql + " limit "+(maxPage*limit), ase.objs);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public List<GzbUsers> gzbUsersQueryCache(String sql, Object[] arr, int mm) {
        List<GzbUsers> list = new ArrayList<>();
        String key = DataBase.db.getKey(sql, arr);
        String str = Cache.gzbCache.get(key);
        if (str == null) {
            Log.sql("Miss:" + key);
            list = gzbUsersQuery(sql, arr);
            Cache.gzbCache.set(key, list.toString(), mm);
        } else {
            Log.sql("Hit:" + key);
            list = gzbUsersToList(str);
        }
        return list;
    }
    @Override
    public List<GzbUsers> gzbUsersQueryCache(GzbUsers gzbUsers, int mm) {
        AutoSqlEntity ase = gzbUsers.toSelect();
        return gzbUsersQueryCache(ase.sql, ase.objs, mm);
    }

    @Override
    public ListPage gzbUsersQueryCache(String sql, Object[] arr, int page, int limit, int mm) {
        ListPage listPage = new ListPage();
        List<GzbUsers> list = gzbUsersQueryCache(sql, arr, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbUsersQueryCache(GzbUsers gzbUsers, int page, int limit, int maxPage, int maxLimit, int mm) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page > maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbUsers.toSelect();
        List<GzbUsers> list = gzbUsersQueryCache(ase.sql+ " limit "+(maxPage*limit), ase.objs, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public int gzbUsersDelete(GzbUsers gzbUsers) {
        AutoSqlEntity ase = gzbUsers.toDelete();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbUsersInsert(GzbUsers gzbUsers) {
        gzbUsers.setGzbUsersId(DataBase.db.getOnlyIdDistributed());
        AutoSqlEntity ase = gzbUsers.toInsert();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbUsersUpdate(GzbUsers gzbUsers) {
        AutoSqlEntity ase = gzbUsers.toUpdate();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }


    @Override
    public int gzbUsersInsertAsy(GzbUsers gzbUsers) {
        return gzbUsersInsertAsy(gzbUsers, true);
    }

    @Override
    public int gzbUsersInsertAsy(GzbUsers gzbUsers, boolean auto) {
        if (auto) {
            gzbUsers.setGzbUsersId(DataBase.db.getOnlyIdDistributed());
        }
        AutoSqlEntity ase = gzbUsers.toInsert();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbUsersDeleteAsy(GzbUsers gzbUsers) {
        AutoSqlEntity ase = gzbUsers.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbUsersUpdateAsy(GzbUsers gzbUsers) {
        AutoSqlEntity ase = gzbUsers.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbUsersBatch(List<GzbUsers> list) {
        return gzbUsersBatch(list, true);
    }

    @Override
    public int gzbUsersBatch(List<GzbUsers> list, boolean autoId) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (autoId) {
                    list.get(i).setGzbUsersId(DataBase.db.getOnlyIdDistributed());
                }
                AutoSqlEntity ase = list.get(i).toInsert();
                if (i == 0) {
                    sb.append("Batch:").append(ase.sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(ase.sql);
                }
                for (int i1 = 0; i1 < ase.objs.length; i1++) {
                    ps.setObject(i1 + 1, ase.objs[i1]);
                }
                sb.append(DataBase.db.getKey(ase.sql, ase.objs));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public int gzbUsersBatch(String sql, List<Object[]> list) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sb.append("Batch:").append(sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(sql);
                }
                for (int i1 = 0; i1 < list.get(i).length; i1++) {
                    ps.setObject(i1 + 1, list.get(i)[i1]);
                }
                sb.append(DataBase.db.getKey(sql, list.get(i)));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    @Override
    public Gzbtest gzbtestFind(Long gzbTestId) {
        List<Gzbtest> list = gzbtestQuery("select * from "+DataBase.gzbtestName+" where gzbTestId=?", Tools.toArray(gzbTestId));
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public Gzbtest gzbtestFind(String sql, Object[] arr) {
        List<Gzbtest> list = gzbtestQuery(sql, arr);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public Gzbtest gzbtestFind(Gzbtest gzbtest) {
        AutoSqlEntity ase = gzbtest.toSelect();
        List<Gzbtest> list = gzbtestQuery(ase.sql, ase.objs);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public Gzbtest gzbtestFindCache(Gzbtest gzbtest,int mm) {
        AutoSqlEntity ase = gzbtest.toSelect();
        List<Gzbtest> list = gzbtestQueryCache(ase.sql, ase.objs,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    public Gzbtest gzbtestFindCache(Long gzbTestId,int mm) {
        List<Gzbtest> list = gzbtestQueryCache("select * from "+DataBase.gzbtestName+" where gzbTestId=?", Tools.toArray(gzbTestId),mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public Gzbtest gzbtestFindCache(String sql, Object[] arr,int mm) {
        List<Gzbtest> list = gzbtestQueryCache(sql, arr,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public List<Gzbtest> gzbtestQuery(String sql, Object[] arr) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        if (arr == null) {
            arr = Tools.toArray();
        }
        List<Gzbtest> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(";参数:");
        try {
            conn = DataBase.db.getConnection();
            ps = conn.prepareStatement(sql);
            Gzbtest en;
            String temp = "";
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]).append(",");
                ps.setObject(i + 1, arr[i].toString());
            }
            long t1 = new Date().getTime();
            rs = ps.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";查询耗时:").append(t2 - t1).append("毫秒");
            t1 = new Date().getTime();
            String a = "," + Tools.textMid(sql, "select ", " from", 1).replaceAll(" ", "") + ",";
            while (rs.next()) {
                en = new Gzbtest();
                if (a.equals(",*,") || a.indexOf(",gzbTestId,") > -1 || a.indexOf(",gzbTestId,") > -1) {
                    temp = rs.getString("gzbTestId");
                    if (temp != null) {
                        en.setGzbTestId(java.lang.Long.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbTestAcc,") > -1 || a.indexOf(",gzbTestAcc,") > -1) {
                    temp = rs.getString("gzbTestAcc");
                    if (temp != null) {
                        en.setGzbTestAcc(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbTestPwd,") > -1 || a.indexOf(",gzbTestPwd,") > -1) {
                    temp = rs.getString("gzbTestPwd");
                    if (temp != null) {
                        en.setGzbTestPwd(java.lang.String.valueOf(temp));
                    }
                }
                list.add(en);
            }
            t2 = new Date().getTime();
            sb.append(";组装对象耗时:").append(t2 - t1).append("毫秒");
            Log.sql(sb.toString());
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            DataBase.db.close(conn, rs, ps);
        }
        return list;
    }

    @Override
    public List<Gzbtest> gzbtestQuery(Gzbtest gzbtest) {
        AutoSqlEntity ase = gzbtest.toSelect();
        return gzbtestQuery(ase.sql, ase.objs);
    }

    @Override
    public ListPage gzbtestQuery(String sql, Object[] arr, int page, int limit) {
        ListPage listPage = new ListPage();
        List<Gzbtest> list = gzbtestQuery(sql, arr);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbtestQuery(Gzbtest gzbtest, int page, int limit, int maxPage, int maxLimit) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page>maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbtest.toSelect();
        List<Gzbtest> list = gzbtestQuery(ase.sql + " limit "+(maxPage*limit), ase.objs);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public List<Gzbtest> gzbtestQueryCache(String sql, Object[] arr, int mm) {
        List<Gzbtest> list = new ArrayList<>();
        String key = DataBase.db.getKey(sql, arr);
        String str = Cache.gzbCache.get(key);
        if (str == null) {
            Log.sql("Miss:" + key);
            list = gzbtestQuery(sql, arr);
            Cache.gzbCache.set(key, list.toString(), mm);
        } else {
            Log.sql("Hit:" + key);
            list = gzbtestToList(str);
        }
        return list;
    }
    @Override
    public List<Gzbtest> gzbtestQueryCache(Gzbtest gzbtest, int mm) {
        AutoSqlEntity ase = gzbtest.toSelect();
        return gzbtestQueryCache(ase.sql, ase.objs, mm);
    }

    @Override
    public ListPage gzbtestQueryCache(String sql, Object[] arr, int page, int limit, int mm) {
        ListPage listPage = new ListPage();
        List<Gzbtest> list = gzbtestQueryCache(sql, arr, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbtestQueryCache(Gzbtest gzbtest, int page, int limit, int maxPage, int maxLimit, int mm) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page > maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbtest.toSelect();
        List<Gzbtest> list = gzbtestQueryCache(ase.sql+ " limit "+(maxPage*limit), ase.objs, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public int gzbtestDelete(Gzbtest gzbtest) {
        AutoSqlEntity ase = gzbtest.toDelete();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbtestInsert(Gzbtest gzbtest) {
        gzbtest.setGzbTestId(DataBase.db.getOnlyIdDistributed());
        AutoSqlEntity ase = gzbtest.toInsert();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbtestUpdate(Gzbtest gzbtest) {
        AutoSqlEntity ase = gzbtest.toUpdate();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }


    @Override
    public int gzbtestInsertAsy(Gzbtest gzbtest) {
        return gzbtestInsertAsy(gzbtest, true);
    }

    @Override
    public int gzbtestInsertAsy(Gzbtest gzbtest, boolean auto) {
        if (auto) {
            gzbtest.setGzbTestId(DataBase.db.getOnlyIdDistributed());
        }
        AutoSqlEntity ase = gzbtest.toInsert();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbtestDeleteAsy(Gzbtest gzbtest) {
        AutoSqlEntity ase = gzbtest.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbtestUpdateAsy(Gzbtest gzbtest) {
        AutoSqlEntity ase = gzbtest.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbtestBatch(List<Gzbtest> list) {
        return gzbtestBatch(list, true);
    }

    @Override
    public int gzbtestBatch(List<Gzbtest> list, boolean autoId) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (autoId) {
                    list.get(i).setGzbTestId(DataBase.db.getOnlyIdDistributed());
                }
                AutoSqlEntity ase = list.get(i).toInsert();
                if (i == 0) {
                    sb.append("Batch:").append(ase.sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(ase.sql);
                }
                for (int i1 = 0; i1 < ase.objs.length; i1++) {
                    ps.setObject(i1 + 1, ase.objs[i1]);
                }
                sb.append(DataBase.db.getKey(ase.sql, ase.objs));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public int gzbtestBatch(String sql, List<Object[]> list) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sb.append("Batch:").append(sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(sql);
                }
                for (int i1 = 0; i1 < list.get(i).length; i1++) {
                    ps.setObject(i1 + 1, list.get(i)[i1]);
                }
                sb.append(DataBase.db.getKey(sql, list.get(i)));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
    @Override
    public Test testFind(Long test_id) {
        List<Test> list = testQuery("select * from "+DataBase.testName+" where test_id=?", Tools.toArray(test_id));
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public Test testFind(String sql, Object[] arr) {
        List<Test> list = testQuery(sql, arr);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public Test testFind(Test test) {
        AutoSqlEntity ase = test.toSelect();
        List<Test> list = testQuery(ase.sql, ase.objs);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public Test testFindCache(Test test,int mm) {
        AutoSqlEntity ase = test.toSelect();
        List<Test> list = testQueryCache(ase.sql, ase.objs,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    public Test testFindCache(Long test_id,int mm) {
        List<Test> list = testQueryCache("select * from "+DataBase.testName+" where test_id=?", Tools.toArray(test_id),mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public Test testFindCache(String sql, Object[] arr,int mm) {
        List<Test> list = testQueryCache(sql, arr,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public List<Test> testQuery(String sql, Object[] arr) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        if (arr == null) {
            arr = Tools.toArray();
        }
        List<Test> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(";参数:");
        try {
            conn = DataBase.db.getConnection();
            ps = conn.prepareStatement(sql);
            Test en;
            String temp = "";
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]).append(",");
                ps.setObject(i + 1, arr[i].toString());
            }
            long t1 = new Date().getTime();
            rs = ps.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";查询耗时:").append(t2 - t1).append("毫秒");
            t1 = new Date().getTime();
            String a = "," + Tools.textMid(sql, "select ", " from", 1).replaceAll(" ", "") + ",";
            while (rs.next()) {
                en = new Test();
                if (a.equals(",*,") || a.indexOf(",testId,") > -1 || a.indexOf(",test_id,") > -1) {
                    temp = rs.getString("test_id");
                    if (temp != null) {
                        en.setTestId(java.lang.Long.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",testName,") > -1 || a.indexOf(",test_name,") > -1) {
                    temp = rs.getString("test_name");
                    if (temp != null) {
                        en.setTestName(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",testState,") > -1 || a.indexOf(",test_state,") > -1) {
                    temp = rs.getString("test_state");
                    if (temp != null) {
                        en.setTestState(java.lang.Integer.valueOf(temp));
                    }
                }
                list.add(en);
            }
            t2 = new Date().getTime();
            sb.append(";组装对象耗时:").append(t2 - t1).append("毫秒");
            Log.sql(sb.toString());
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            DataBase.db.close(conn, rs, ps);
        }
        return list;
    }

    @Override
    public List<Test> testQuery(Test test) {
        AutoSqlEntity ase = test.toSelect();
        return testQuery(ase.sql, ase.objs);
    }

    @Override
    public ListPage testQuery(String sql, Object[] arr, int page, int limit) {
        ListPage listPage = new ListPage();
        List<Test> list = testQuery(sql, arr);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage testQuery(Test test, int page, int limit, int maxPage, int maxLimit) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page>maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = test.toSelect();
        List<Test> list = testQuery(ase.sql + " limit "+(maxPage*limit), ase.objs);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public List<Test> testQueryCache(String sql, Object[] arr, int mm) {
        List<Test> list = new ArrayList<>();
        String key = DataBase.db.getKey(sql, arr);
        String str = Cache.gzbCache.get(key);
        if (str == null) {
            Log.sql("Miss:" + key);
            list = testQuery(sql, arr);
            Cache.gzbCache.set(key, list.toString(), mm);
        } else {
            Log.sql("Hit:" + key);
            list = testToList(str);
        }
        return list;
    }
    @Override
    public List<Test> testQueryCache(Test test, int mm) {
        AutoSqlEntity ase = test.toSelect();
        return testQueryCache(ase.sql, ase.objs, mm);
    }

    @Override
    public ListPage testQueryCache(String sql, Object[] arr, int page, int limit, int mm) {
        ListPage listPage = new ListPage();
        List<Test> list = testQueryCache(sql, arr, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage testQueryCache(Test test, int page, int limit, int maxPage, int maxLimit, int mm) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page > maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = test.toSelect();
        List<Test> list = testQueryCache(ase.sql+ " limit "+(maxPage*limit), ase.objs, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public int testDelete(Test test) {
        AutoSqlEntity ase = test.toDelete();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int testInsert(Test test) {
        test.setTestId(DataBase.db.getOnlyIdDistributed());
        AutoSqlEntity ase = test.toInsert();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int testUpdate(Test test) {
        AutoSqlEntity ase = test.toUpdate();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }


    @Override
    public int testInsertAsy(Test test) {
        return testInsertAsy(test, true);
    }

    @Override
    public int testInsertAsy(Test test, boolean auto) {
        if (auto) {
            test.setTestId(DataBase.db.getOnlyIdDistributed());
        }
        AutoSqlEntity ase = test.toInsert();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int testDeleteAsy(Test test) {
        AutoSqlEntity ase = test.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int testUpdateAsy(Test test) {
        AutoSqlEntity ase = test.toUpdate();
        return DataBase.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int testBatch(List<Test> list) {
        return testBatch(list, true);
    }

    @Override
    public int testBatch(List<Test> list, boolean autoId) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (autoId) {
                    list.get(i).setTestId(DataBase.db.getOnlyIdDistributed());
                }
                AutoSqlEntity ase = list.get(i).toInsert();
                if (i == 0) {
                    sb.append("Batch:").append(ase.sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(ase.sql);
                }
                for (int i1 = 0; i1 < ase.objs.length; i1++) {
                    ps.setObject(i1 + 1, ase.objs[i1]);
                }
                sb.append(DataBase.db.getKey(ase.sql, ase.objs));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    @Override
    public int testBatch(String sql, List<Object[]> list) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sb.append("Batch:").append(sql).append(";参数:");
                    conn = DataBase.db.getConnection();
                    conn.setAutoCommit(false);
                    ps = conn.prepareStatement(sql);
                }
                for (int i1 = 0; i1 < list.get(i).length; i1++) {
                    ps.setObject(i1 + 1, list.get(i)[i1]);
                }
                sb.append(DataBase.db.getKey(sql, list.get(i)));
                ps.addBatch();
            }
            long t2 = new Date().getTime();
            sb.append(";组装耗时:");
            sb.append(t2 - t1);
            sb.append("毫秒");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";执行耗时:");
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
                DataBase.db.close(conn, rs, ps);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}