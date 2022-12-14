package gzb.db.gzb_system.dao;
import gzb.db.gzb_system.DataBase;
import gzb.db.gzb_system.entity.ContentManager;
import gzb.db.gzb_system.entity.FileManager;
import gzb.db.gzb_system.entity.GzbApi;
import gzb.db.gzb_system.entity.GzbCache;
import gzb.db.gzb_system.entity.GzbGroup;
import gzb.db.gzb_system.entity.GzbRight;
import gzb.db.gzb_system.entity.GzbUsers;
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
    public final List<GzbGroup> gzbGroupToList(String json) {
        List<GzbGroup> list = new ArrayList<>();
        if (json.length()<3){
            return list;
        }
        json = json.substring(2, json.length() - 2);
        String[] ss1 = json.replaceAll("}, \\{", "},{").split("},\\{");
        for (int i = 0; i < ss1.length; i++) {
            list.add(new GzbGroup("{" + ss1[i] + "}"));
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
    @Override
    public ContentManager contentManagerFind(java.lang.Long content_manager_id) {
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
    public ContentManager contentManagerFindCache(java.lang.Long contentManagerId,int mm) {
        List<ContentManager> list = contentManagerQueryCache("select * from "+DataBase.contentManagerName+" where content_manager_id=?", Tools.toArray(contentManagerId),mm);
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
        sb.append(sql).append(";??????:");
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
            sb.append(";????????????:").append(t2 - t1).append("??????");
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
            sb.append(";??????????????????:").append(t2 - t1).append("??????");
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
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int contentManagerDeleteAsy(ContentManager contentManager) {
        AutoSqlEntity ase = contentManager.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int contentManagerUpdateAsy(ContentManager contentManager) {
        AutoSqlEntity ase = contentManager.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int contentManagerInsertBatch(List<ContentManager> list) {
        return contentManagerInsertBatch(list, true);
    }

    @Override
    public int contentManagerInsertBatch(List<ContentManager> list, boolean autoId) {
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
                    sb.append("Batch:").append(ase.sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
                    sb.append("Batch:").append(sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
    public FileManager fileManagerFind(java.lang.Long file_manager_id) {
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
    public FileManager fileManagerFindCache(java.lang.Long fileManagerId,int mm) {
        List<FileManager> list = fileManagerQueryCache("select * from "+DataBase.fileManagerName+" where file_manager_id=?", Tools.toArray(fileManagerId),mm);
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
        sb.append(sql).append(";??????:");
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
            sb.append(";????????????:").append(t2 - t1).append("??????");
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
            sb.append(";??????????????????:").append(t2 - t1).append("??????");
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
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int fileManagerDeleteAsy(FileManager fileManager) {
        AutoSqlEntity ase = fileManager.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int fileManagerUpdateAsy(FileManager fileManager) {
        AutoSqlEntity ase = fileManager.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int fileManagerInsertBatch(List<FileManager> list) {
        return fileManagerInsertBatch(list, true);
    }

    @Override
    public int fileManagerInsertBatch(List<FileManager> list, boolean autoId) {
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
                    sb.append("Batch:").append(ase.sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
                    sb.append("Batch:").append(sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
    public GzbApi gzbApiFind(java.lang.Long gzb_api_id) {
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
    public GzbApi gzbApiFindCache(java.lang.Long gzbApiId,int mm) {
        List<GzbApi> list = gzbApiQueryCache("select * from "+DataBase.gzbApiName+" where gzb_api_id=?", Tools.toArray(gzbApiId),mm);
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
        sb.append(sql).append(";??????:");
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
            sb.append(";????????????:").append(t2 - t1).append("??????");
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
            sb.append(";??????????????????:").append(t2 - t1).append("??????");
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
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbApiDeleteAsy(GzbApi gzbApi) {
        AutoSqlEntity ase = gzbApi.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbApiUpdateAsy(GzbApi gzbApi) {
        AutoSqlEntity ase = gzbApi.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbApiInsertBatch(List<GzbApi> list) {
        return gzbApiInsertBatch(list, true);
    }

    @Override
    public int gzbApiInsertBatch(List<GzbApi> list, boolean autoId) {
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
                    sb.append("Batch:").append(ase.sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
                    sb.append("Batch:").append(sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
    public GzbCache gzbCacheFind(java.lang.Long gzb_cache_id) {
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
    public GzbCache gzbCacheFindCache(java.lang.Long gzbCacheId,int mm) {
        List<GzbCache> list = gzbCacheQueryCache("select * from "+DataBase.gzbCacheName+" where gzb_cache_id=?", Tools.toArray(gzbCacheId),mm);
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
        sb.append(sql).append(";??????:");
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
            sb.append(";????????????:").append(t2 - t1).append("??????");
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
            sb.append(";??????????????????:").append(t2 - t1).append("??????");
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
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbCacheDeleteAsy(GzbCache gzbCache) {
        AutoSqlEntity ase = gzbCache.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbCacheUpdateAsy(GzbCache gzbCache) {
        AutoSqlEntity ase = gzbCache.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbCacheInsertBatch(List<GzbCache> list) {
        return gzbCacheInsertBatch(list, true);
    }

    @Override
    public int gzbCacheInsertBatch(List<GzbCache> list, boolean autoId) {
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
                    sb.append("Batch:").append(ase.sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
                    sb.append("Batch:").append(sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
    public GzbGroup gzbGroupFind(java.lang.Long gzb_group_id) {
        List<GzbGroup> list = gzbGroupQuery("select * from "+DataBase.gzbGroupName+" where gzb_group_id=?", Tools.toArray(gzb_group_id));
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbGroup gzbGroupFind(String sql, Object[] arr) {
        List<GzbGroup> list = gzbGroupQuery(sql, arr);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbGroup gzbGroupFind(GzbGroup gzbGroup) {
        AutoSqlEntity ase = gzbGroup.toSelect();
        List<GzbGroup> list = gzbGroupQuery(ase.sql, ase.objs);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbGroup gzbGroupFindCache(GzbGroup gzbGroup,int mm) {
        AutoSqlEntity ase = gzbGroup.toSelect();
        List<GzbGroup> list = gzbGroupQueryCache(ase.sql, ase.objs,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    public GzbGroup gzbGroupFindCache(java.lang.Long gzbGroupId,int mm) {
        List<GzbGroup> list = gzbGroupQueryCache("select * from "+DataBase.gzbGroupName+" where gzb_group_id=?", Tools.toArray(gzbGroupId),mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public GzbGroup gzbGroupFindCache(String sql, Object[] arr,int mm) {
        List<GzbGroup> list = gzbGroupQueryCache(sql, arr,mm);
        if (list.size() != 1) {
            return null;
        }
        return list.get(0);
    }
    @Override
    public List<GzbGroup> gzbGroupQuery(String sql, Object[] arr) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        if (arr == null) {
            arr = Tools.toArray();
        }
        List<GzbGroup> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append(sql).append(";??????:");
        try {
            conn = DataBase.db.getConnection();
            ps = conn.prepareStatement(sql);
            GzbGroup en;
            String temp = "";
            for (int i = 0; i < arr.length; i++) {
                sb.append(arr[i]).append(",");
                ps.setObject(i + 1, arr[i].toString());
            }
            long t1 = new Date().getTime();
            rs = ps.executeQuery();
            long t2 = new Date().getTime();
            sb.append(";????????????:").append(t2 - t1).append("??????");
            t1 = new Date().getTime();
            String a = "," + Tools.textMid(sql, "select ", " from", 1).replaceAll(" ", "") + ",";
            while (rs.next()) {
                en = new GzbGroup();
                if (a.equals(",*,") || a.indexOf(",gzbGroupId,") > -1 || a.indexOf(",gzb_group_id,") > -1) {
                    temp = rs.getString("gzb_group_id");
                    if (temp != null) {
                        en.setGzbGroupId(java.lang.Long.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbGroupName,") > -1 || a.indexOf(",gzb_group_name,") > -1) {
                    temp = rs.getString("gzb_group_name");
                    if (temp != null) {
                        en.setGzbGroupName(java.lang.String.valueOf(temp));
                    }
                }
                if (a.equals(",*,") || a.indexOf(",gzbGroupState,") > -1 || a.indexOf(",gzb_group_state,") > -1) {
                    temp = rs.getString("gzb_group_state");
                    if (temp != null) {
                        en.setGzbGroupState(java.lang.String.valueOf(temp));
                    }
                }
                list.add(en);
            }
            t2 = new Date().getTime();
            sb.append(";??????????????????:").append(t2 - t1).append("??????");
            Log.sql(sb.toString());
        } catch (Exception e) {
            Log.e(e, sb.toString());
        } finally {
            DataBase.db.close(conn, rs, ps);
        }
        return list;
    }

    @Override
    public List<GzbGroup> gzbGroupQuery(GzbGroup gzbGroup) {
        AutoSqlEntity ase = gzbGroup.toSelect();
        return gzbGroupQuery(ase.sql, ase.objs);
    }

    @Override
    public ListPage gzbGroupQuery(String sql, Object[] arr, int page, int limit) {
        ListPage listPage = new ListPage();
        List<GzbGroup> list = gzbGroupQuery(sql, arr);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbGroupQuery(GzbGroup gzbGroup, int page, int limit, int maxPage, int maxLimit) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page>maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbGroup.toSelect();
        List<GzbGroup> list = gzbGroupQuery(ase.sql + " limit "+(maxPage*limit), ase.objs);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public List<GzbGroup> gzbGroupQueryCache(String sql, Object[] arr, int mm) {
        List<GzbGroup> list = new ArrayList<>();
        String key = DataBase.db.getKey(sql, arr);
        String str = Cache.gzbCache.get(key);
        if (str == null) {
            Log.sql("Miss:" + key);
            list = gzbGroupQuery(sql, arr);
            Cache.gzbCache.set(key, list.toString(), mm);
        } else {
            Log.sql("Hit:" + key);
            list = gzbGroupToList(str);
        }
        return list;
    }
    @Override
    public List<GzbGroup> gzbGroupQueryCache(GzbGroup gzbGroup, int mm) {
        AutoSqlEntity ase = gzbGroup.toSelect();
        return gzbGroupQueryCache(ase.sql, ase.objs, mm);
    }

    @Override
    public ListPage gzbGroupQueryCache(String sql, Object[] arr, int page, int limit, int mm) {
        ListPage listPage = new ListPage();
        List<GzbGroup> list = gzbGroupQueryCache(sql, arr, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public ListPage gzbGroupQueryCache(GzbGroup gzbGroup, int page, int limit, int maxPage, int maxLimit, int mm) {
        limit=limit>maxLimit ? maxLimit : limit;
        page=page > maxPage ? maxPage : page;
        ListPage listPage = new ListPage();
        AutoSqlEntity ase = gzbGroup.toSelect();
        List<GzbGroup> list = gzbGroupQueryCache(ase.sql+ " limit "+(maxPage*limit), ase.objs, mm);
        listPage.limitList(list, page, limit);
        return listPage;
    }

    @Override
    public int gzbGroupDelete(GzbGroup gzbGroup) {
        AutoSqlEntity ase = gzbGroup.toDelete();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbGroupInsert(GzbGroup gzbGroup) {
        gzbGroup.setGzbGroupId(DataBase.db.getOnlyIdDistributed());
        AutoSqlEntity ase = gzbGroup.toInsert();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }

    @Override
    public int gzbGroupUpdate(GzbGroup gzbGroup) {
        AutoSqlEntity ase = gzbGroup.toUpdate();
        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);
    }


    @Override
    public int gzbGroupInsertAsy(GzbGroup gzbGroup) {
        return gzbGroupInsertAsy(gzbGroup, true);
    }

    @Override
    public int gzbGroupInsertAsy(GzbGroup gzbGroup, boolean auto) {
        if (auto) {
        gzbGroup.setGzbGroupId(DataBase.db.getOnlyIdDistributed());
        }
        AutoSqlEntity ase = gzbGroup.toInsert();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbGroupDeleteAsy(GzbGroup gzbGroup) {
        AutoSqlEntity ase = gzbGroup.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbGroupUpdateAsy(GzbGroup gzbGroup) {
        AutoSqlEntity ase = gzbGroup.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbGroupInsertBatch(List<GzbGroup> list) {
        return gzbGroupInsertBatch(list, true);
    }

    @Override
    public int gzbGroupInsertBatch(List<GzbGroup> list, boolean autoId) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (autoId) {
                    list.get(i).setGzbGroupId(DataBase.db.getOnlyIdDistributed());
                }
                AutoSqlEntity ase = list.get(i).toInsert();
                if (i == 0) {
                    sb.append("Batch:").append(ase.sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
    public int gzbGroupBatch(String sql, List<Object[]> list) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        StringBuilder sb = new StringBuilder();
        try {
            long t1 = new Date().getTime();
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    sb.append("Batch:").append(sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
    public GzbRight gzbRightFind(java.lang.Long gzb_right_id) {
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
    public GzbRight gzbRightFindCache(java.lang.Long gzbRightId,int mm) {
        List<GzbRight> list = gzbRightQueryCache("select * from "+DataBase.gzbRightName+" where gzb_right_id=?", Tools.toArray(gzbRightId),mm);
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
        sb.append(sql).append(";??????:");
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
            sb.append(";????????????:").append(t2 - t1).append("??????");
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
                if (a.equals(",*,") || a.indexOf(",gzbRightGroupId,") > -1 || a.indexOf(",gzb_right_group_id,") > -1) {
                    temp = rs.getString("gzb_right_group_id");
                    if (temp != null) {
                        en.setGzbRightGroupId(java.lang.Long.valueOf(temp));
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
            sb.append(";??????????????????:").append(t2 - t1).append("??????");
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
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbRightDeleteAsy(GzbRight gzbRight) {
        AutoSqlEntity ase = gzbRight.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbRightUpdateAsy(GzbRight gzbRight) {
        AutoSqlEntity ase = gzbRight.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbRightInsertBatch(List<GzbRight> list) {
        return gzbRightInsertBatch(list, true);
    }

    @Override
    public int gzbRightInsertBatch(List<GzbRight> list, boolean autoId) {
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
                    sb.append("Batch:").append(ase.sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
                    sb.append("Batch:").append(sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
    public GzbUsers gzbUsersFind(java.lang.Long gzb_users_id) {
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
    public GzbUsers gzbUsersFindCache(java.lang.Long gzbUsersId,int mm) {
        List<GzbUsers> list = gzbUsersQueryCache("select * from "+DataBase.gzbUsersName+" where gzb_users_id=?", Tools.toArray(gzbUsersId),mm);
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
        sb.append(sql).append(";??????:");
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
            sb.append(";????????????:").append(t2 - t1).append("??????");
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
            sb.append(";??????????????????:").append(t2 - t1).append("??????");
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
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbUsersDeleteAsy(GzbUsers gzbUsers) {
        AutoSqlEntity ase = gzbUsers.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbUsersUpdateAsy(GzbUsers gzbUsers) {
        AutoSqlEntity ase = gzbUsers.toUpdate();
        return DataBase.db.addAsyInfo(ase.sql, ase.objs);
    }

    @Override
    public int gzbUsersInsertBatch(List<GzbUsers> list) {
        return gzbUsersInsertBatch(list, true);
    }

    @Override
    public int gzbUsersInsertBatch(List<GzbUsers> list, boolean autoId) {
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
                    sb.append("Batch:").append(ase.sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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
                    sb.append("Batch:").append(sql).append(";??????:");
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
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            t1 = new Date().getTime();
            int[] res = ps.executeBatch();
            conn.commit();
            t2 = new Date().getTime();
            sb.append(";????????????:");
            sb.append(t2 - t1);
            sb.append("??????");
            Log.sql(sb.toString());
            return res.length;
        } catch (SQLIntegrityConstraintViolationException e) {
            Log.e(e, "ID??????" + sb.toString());
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