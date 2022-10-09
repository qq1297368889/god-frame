package gzb;

import gzb.db.DB;
import gzb.tools.Tools;
import gzb.tools.log.LogImpl;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Auto {

    public static void main(String[] args) {
        generate(null, "pay_system", "*", true,true);
    }

    /**
     * @param projectPath 项目路径
     * @param dbName      数据库名称 需要和配置文件中对应
     * @param tableName   需要生成哪些表 *表示所有表 否则就 表名/表名/
     * @param hump  true 命名 驼峰   false 和数据库保持一致
     */
    public static final void generate(String projectPath, String dbName, String tableName, boolean baseDao,boolean hump) {
        if (hump){
            generateHump(projectPath,dbName,tableName,baseDao);
        }else{
            generateOriginal(projectPath,dbName,tableName,baseDao);
        }
    }
    /**
     * @param projectPath 项目路径
     * @param dbName      数据库名称 需要和配置文件中对应
     * @param tableName   需要生成哪些表 *表示所有表 否则就 表名/表名/
     */
    public static final void generateHump(String projectPath, String dbName, String tableName, boolean baseDao) {
        String tmp = Tools.ProjectPath;
        if (projectPath != null) {
            Tools.ProjectPath = projectPath;
        }
        AutoHump.generate(dbName, tableName, baseDao);
        if (projectPath != null) {
            Tools.ProjectPath = tmp;
        }

    }

    /**
     * @param projectPath 项目路径
     * @param dbName      数据库名称 需要和配置文件中对应
     * @param tableName   需要生成哪些表 *表示所有表 否则就 表名/表名/
     */
    public static final void generateOriginal(String projectPath, String dbName, String tableName, boolean baseDao) {

        String tmp = Tools.ProjectPath;
        if (projectPath != null) {
            Tools.ProjectPath = projectPath;
        }
        AutoOriginal.generate(dbName, tableName, baseDao);
        if (projectPath != null) {
            Tools.ProjectPath = tmp;
        }
    }
}

class AutoHump {
    static gzb.tools.log.Log Log = new LogImpl(AutoHump.class);
    static String dbName = "gzb_system";
    static int type = 0;//0 basedao  1dao
    static String pkg;
    private static DB db;
    static String mapName = "*";

    public static void generate(String dbName1, String mapName1, boolean baseDao) {
        try {
            dbName = dbName1;
            mapName = mapName1;
            db = new DB(dbName);
            type = baseDao ? 0 : 1;
            pkg = Tools.getProjectPath() + "../src/main/java/gzb/";
            List<DB_entity> list = getMapInfo(mapName);
            Log.print(list);

            String code, path;
            //database 生成
            code = dataBaseCode(list);
            path = pkg + "/db/" + dbName + "/";
            new File(path).mkdirs();
            Tools.fileSaveString(path + "DataBase.java", code, false);
            if (type == 0) {
                //baseDaoCode 生成
                code = baseDaoCode(list);
                path = pkg + "/db/" + dbName + "/dao/";
                new File(path).mkdirs();
                Tools.fileSaveString(path + "BaseDao.java", code, false);
                //baseDaoImplCode 生成
                code = baseDaoImplCode(list);
                path = pkg + "/db/" + dbName + "/dao/";
                new File(path).mkdirs();
                Tools.fileSaveString(path + "BaseDaoImpl.java", code, false);
            }
            for (DB_entity db_entity : list) {
                if (type == 0) {
                    //实体类生成
                    code = entityCodeBaseDao(db_entity);
                    path = pkg + "/db/" + dbName + "/entity/";
                    new File(path).mkdirs();
                    Tools.fileSaveString(path + lowStr_hump(db_entity.name, true) + ".java", code, false);
                } else {
                    //实体类生成
                    code = entityCodeDao(db_entity);
                    path = pkg + "/db/" + dbName + "/entity/";
                    new File(path).mkdirs();
                    Tools.fileSaveString(path + lowStr_hump(db_entity.name, true) + ".java", code, false);
                    // Dao 生成
                    code = daoCode(db_entity);
                    path = pkg + "/db/" + dbName + "/dao/";
                    new File(path).mkdirs();
                    Tools.fileSaveString(path + lowStr_hump((db_entity.name), true) + "Dao.java", code, false);

                    // DaoImpl 生成
                    code = daoImplCode(db_entity);
                    path = pkg + "/db/" + dbName + "/dao/";
                    new File(path).mkdirs();
                    Tools.fileSaveString(path + lowStr_hump((db_entity.name), true) + "DaoImpl.java", code, false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String dataBaseCode(List<DB_entity> list) {
        String code0 = "";
        String code1 = "";
        for (DB_entity entity : list) {
            String 表名大写 = lowStr_d(entity.name);
            String 表名小写 = lowStr_x(entity.name);
            String id = (lowStr_x(entity.id));
            code0 += "    public static String " + lowStr_hump(表名小写) + "Name=\"" + 表名小写 + "\";\n";
            code1 += "            " + lowStr_hump(表名小写) + "Name =division(" + lowStr_hump(表名小写) + "Name,Tools.configGetInteger(\"gzb.db." + dbName + ".division." + lowStr_x(entity.name) + "\",\"0\"));\n";
        }

        String code = "package gzb.db." + dbName + ";\n" +
                "\n" +
                "import gzb.db.DB;\n" +
                "import gzb.tools.*;\n" +
                "import gzb.tools.thread.GzbThread;\n" +
                "import gzb.tools.thread.ThreadPool;\n" +
                "\n" +
                "import java.sql.*;\n" +
                "import java.util.*;\n" +
                "import java.util.Date;\n" +
                "import java.util.concurrent.locks.Lock;\n" +
                "import java.util.concurrent.locks.ReentrantLock;\n" +
                "import gzb.tools.log.Log;\n" +
                "import gzb.tools.log.LogImpl;\n" +
                "\n" +
                "public class DataBase {\n" +
                "    static Log Log=new LogImpl(DataBase.class);\n" +
                code0 +
                "    public static DB db = new DB(\"" + dbName + "\");\n" +
                "    public static Map<String, List<Object[]>> mapAskSql = new HashMap<>();\n" +
                "    public static Lock lockAsy = new ReentrantLock();\n" +
                "\n" +
                "    static {\n" +
                "        try {\n" +
                code1 +
                "\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public static final int addAsyInfo(String sql, Object[] objs) {\n" +
                "        DataBase.lockAsy.lock();\n" +
                "        try {\n" +
                "            List<Object[]> list = DataBase.mapAskSql.get(sql);\n" +
                "            if (list == null) {\n" +
                "                list = new ArrayList<>();\n" +
                "            }\n" +
                "            list.add(objs);\n" +
                "            DataBase.mapAskSql.put(sql, list);\n" +
                "            return 1;\n" +
                "        } finally {\n" +
                "            DataBase.lockAsy.unlock();\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    static {\n" +
                "        ThreadPool.start(new GzbThread() {\n" +
                "            @Override\n" +
                "            public void start() {\n" +
                "                Connection conn = null;\n" +
                "                ResultSet rs = null;\n" +
                "                PreparedStatement ps = null;\n" +
                "                while (true) {\n" +
                "                    try {\n" +
                "                        if (mapAskSql.size() == 0) {\n" +
                "                            Thread.sleep(100);\n" +
                "                            continue;\n" +
                "                        }\n" +
                "                        Map<String, List<Object[]>> map = mapAskSql;\n" +
                "                        List<Object[]> list = null;\n" +
                "                        mapAskSql = new HashMap<>();\n" +
                "                        lockAsy.lock();\n" +
                "                        lockAsy.unlock();\n" +
                "                        conn = DataBase.db.getConnection();\n" +
                "                        try {\n" +
                "                            conn.setAutoCommit(false);\n" +
                "                            for (Iterator<Map.Entry<String, List<Object[]>>> it = map.entrySet().iterator(); it.hasNext(); ) {\n" +
                "                                long t1 = new Date().getTime();\n" +
                "                                StringBuilder sb = new StringBuilder();\n" +
                "                                Map.Entry<String, List<Object[]>> en = it.next();\n" +
                "                                ps = conn.prepareStatement(en.getKey());\n" +
                "                                list = en.getValue();\n" +
                "                                sb.append(\"异步操作\").append(en.getKey())\n" +
                "                                        .append(\"[\")\n" +
                "                                        .append(list.size())\n" +
                "                                        .append(\"条]:\");\n" +
                "                                for (int i = 0; i < list.size(); i++) {\n" +
                "                                    Object[] objs = list.get(i);\n" +
                "                                    sb.append(\"{\");\n" +
                "                                    for (int j = 0; j < objs.length; j++) {\n" +
                "                                        ps.setObject(j + 1, objs[j]);\n" +
                "                                        if (j == objs.length - 1) {\n" +
                "                                            sb.append(\"'\").append(objs[j]).append(\"'\");\n" +
                "                                        } else {\n" +
                "                                            sb.append(\"'\").append(objs[j]).append(\"',\");\n" +
                "                                        }\n" +
                "                                    }\n" +
                "\n" +
                "                                    if (i == list.size() - 1) {\n" +
                "                                        sb.append(\"}\\r\\n\");\n" +
                "                                    } else {\n" +
                "                                        sb.append(\"},\");\n" +
                "                                    }\n" +
                "                                    ps.addBatch();\n" +
                "                                }\n" +
                "                                long t2 = new Date().getTime();\n" +
                "                                sb.append(\"组装耗时:\");\n" +
                "                                sb.append(t2 - t1);\n" +
                "                                sb.append(\"毫秒\");\n" +
                "                                t1 = new Date().getTime();\n" +
                "                                int[] res = ps.executeBatch();\n" +
                "                                conn.commit();\n" +
                "                                t2 = new Date().getTime();\n" +
                "                                sb.append(\";执行耗时:\");\n" +
                "                                sb.append(t2 - t1);\n" +
                "                                sb.append(\"毫秒\");\n" +
                "                                Log.sql(sb.toString());\n" +
                "                            }\n" +
                "                        } finally {\n" +
                "                            try {\n" +
                "                                conn.setAutoCommit(true);\n" +
                "                                DataBase.db.close(conn, rs, ps);\n" +
                "                            } catch (SQLException e) {\n" +
                "                                e.printStackTrace();\n" +
                "                            }\n" +
                "                        }\n" +
                "                    } catch (Exception e) {\n" +
                "                        Log.e(e);\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }, \"DataBase.asy\", false);\n" +
                "    }\n" +
                "    //0不开启  1按年分表 2按月分表 3按天分表\n" +
                "    public static final String division(String mapName, int lv) {\n" +
                "        Object[] arr = Tools.toArray(\"\", \"_yyyy\", \"_yyyy_MM\", \"_yyyy_MM_dd\");\n" +
                "        if (lv == 0 || lv > 3) {\n" +
                "            Log.i(\"未开启分表：\" + mapName);\n" +
                "            return mapName;\n" +
                "        }\n" +
                "        String newMapName = null;\n" +
                "        String newSql = \"\";\n" +
                "        Connection conn = null;\n" +
                "        ResultSet rs = null;\n" +
                "        PreparedStatement ps = null;\n" +
                "        try {\n" +
                "            conn = db.getConnection();\n" +
                "            for (int i = 0; i < 2; i++) {\n" +
                "                newMapName = mapName;\n" +
                "                newMapName += new DateTime().monthAdd(2-(i+1)).format(arr[lv].toString());\n" +
                "                try {\n" +
                "                    ps = conn.prepareStatement(\"show create table \" + newMapName);\n" +
                "                    rs = ps.executeQuery();\n" +
                "                    Log.i(\"已存在分表：\" + newMapName);\n" +
                "                } catch (Exception e) {\n" +
                "                    try {\n" +
                "                        Log.i(\"创建表：\" + newMapName);\n" +
                "                        ps = conn.prepareStatement(\"show create table \" + mapName);\n" +
                "                        rs = ps.executeQuery();\n" +
                "                        while (rs.next()) {\n" +
                "                            newSql = rs.getString(2);\n" +
                "                            newSql = newSql.replace(\"`\" + mapName + \"`\", \"`\" + newMapName + \"`\");\n" +
                "                            db.runSqlUpdateOrSaveOrDelete(newSql, null);\n" +
                "                        }\n" +
                "                    } catch (SQLException throwables) {\n" +
                "                        throwables.printStackTrace();\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        } catch (SQLException throwables) {\n" +
                "            throwables.printStackTrace();\n" +
                "        } finally {\n" +
                "            db.close(conn, rs, ps);\n" +
                "        }\n" +
                "\n" +
                "        return newMapName;\n" +
                "    }\n" +

                "}\n";


        return code;
    }

    public static String baseDaoCode(List<DB_entity> list) {
        String code1 = "";
        String code2 = "";
        for (DB_entity db_entity : list) {
            String 表名_驼峰_首字母小写 = lowStr_hump(db_entity.name);
            String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰_首字母小写);
            String 表名_首字母大写 = lowStr_hump(db_entity.name);
            String 表名_首字母小写 = lowStr_hump(db_entity.name);
            String id = lowStr_x(lowStr_x(db_entity.id));
            code2 += "import gzb.db." + dbName + ".entity." + 表名_驼峰_首字母大写 + ";\n";
            code1 +=
                    "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(Long " + lowStr_hump(id) + ");\n" +
                            "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(String sql, Object[] arr);\n" +
                            "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(Long " + id + ",int mm);\n" +
                            "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(String sql, Object[] arr,int mm);\n" +
                            "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ",int mm);\n" +
                            "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr);\n" +
                            "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    ListPage " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr, int page, int limit);\n" +
                            "    ListPage " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit);\n" +
                            "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int mm);\n" +
                            "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int mm);\n" +
                            "    ListPage " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int page, int limit, int mm);\n" +
                            "    ListPage " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit, int mm);\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Delete(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Insert(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Update(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list, boolean autoId);\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list);\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Batch(String sql, List<Object[]> list);\n" +
                            "    int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", boolean auto);\n" +
                            "    int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    int " + 表名_驼峰_首字母小写 + "DeleteAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    int " + 表名_驼峰_首字母小写 + "UpdateAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n";
        }

        String code = "package gzb.db." + dbName + ".dao;\n" +
                code2 +
                "import gzb.tools.ListPage;\n" +
                "import java.util.List;\n" +
                "public interface BaseDao {\n" +
                code1 + "\n" +
                "}";
        return code;
    }

    public static String baseDaoImplCode(List<DB_entity> list) {
        String code = "";
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        for (DB_entity db_entity : list) {
            String 表名_驼峰_首字母小写 = lowStr_hump(db_entity.name);
            String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰_首字母小写);
            String 表名_首字母大写 = lowStr_hump(db_entity.name);
            String 表名_首字母小写 = lowStr_hump(db_entity.name);
            String id = lowStr_x(lowStr_x(db_entity.id));
            code1 = "";
            for (int i = 0; i < db_entity.subName.size(); i++) {
                String 列名大写 = lowStr_hump(db_entity.subName.get(i), true);
                String 列名小写 = lowStr_hump(db_entity.subName.get(i));
                code1 += "                if (a.equals(\",*,\") || a.indexOf(\"," + 列名小写 + ",\") > -1 || a.indexOf(\"," + db_entity.subName.get(i) + ",\") > -1) {\n" +
                        "                    temp = rs.getString(\"" + db_entity.subName.get(i) + "\");\n" +
                        "                    if (temp != null) {\n" +
                        "                        en.set" + 列名大写 + "(" + db_entity.subType.get(i) + ".valueOf(temp));\n" +
                        "                    }\n" +
                        "                }\n";
            }
//////////////////////////////////////////////////////////////
            code2 += "import gzb.db." + dbName + ".entity." + 表名_驼峰_首字母大写 + ";\n";
            code3 += "    public final List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "ToList(String json) {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                    "        if (json.length()<3){\n" +
                    "            return list;\n" +
                    "        }\n" +
                    "        json = json.substring(2, json.length() - 2);\n" +
                    "        String[] ss1 = json.replaceAll(\"}, \\\\{\", \"},{\").split(\"},\\\\{\");\n" +
                    "        for (int i = 0; i < ss1.length; i++) {\n" +
                    "            list.add(new " + 表名_驼峰_首字母大写 + "(\"{\" + ss1[i] + \"}\"));\n" +
                    "        }\n" +
                    "        return list;\n" +
                    "    }\n";
            code4 += "    @Override\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(Long " + id + ") {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(\"select * from \"+DataBase." + 表名_驼峰_首字母小写 + "Name+\" where " + id + "=?\", Tools.toArray(" + id + "));\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +
                    "    @Override\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(String sql, Object[] arr) {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +
                    "    @Override\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(ase.sql, ase.objs);\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +


                    "    @Override\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ",int mm) {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql, ase.objs,mm);\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(Long " + id + ",int mm) {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(\"select * from \"+DataBase." + 表名_驼峰_首字母小写 + "Name+\" where " + id + "=?\", Tools.toArray(" + id + "),mm);\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +
                    "    @Override\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(String sql, Object[] arr,int mm) {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(sql, arr,mm);\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +


                    "    @Override\n" +
                    "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr) {\n" +
                    "        Connection conn = null;\n" +
                    "        ResultSet rs = null;\n" +
                    "        PreparedStatement ps = null;\n" +
                    "        if (arr == null) {\n" +
                    "            arr = Tools.toArray();\n" +
                    "        }\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                    "        StringBuilder sb = new StringBuilder();\n" +
                    "        sb.append(sql).append(\";参数:\");\n" +
                    "        try {\n" +
                    "            conn = DataBase.db.getConnection();\n" +
                    "            ps = conn.prepareStatement(sql);\n" +
                    "            " + 表名_驼峰_首字母大写 + " en;\n" +
                    "            String temp = \"\";\n" +
                    "            for (int i = 0; i < arr.length; i++) {\n" +
                    "                sb.append(arr[i]).append(\",\");\n" +
                    "                ps.setObject(i + 1, arr[i].toString());\n" +
                    "            }\n" +
                    "            long t1 = new Date().getTime();\n" +
                    "            rs = ps.executeQuery();\n" +
                    "            long t2 = new Date().getTime();\n" +
                    "            sb.append(\";查询耗时:\").append(t2 - t1).append(\"毫秒\");\n" +
                    "            t1 = new Date().getTime();\n" +
                    "            String a = \",\" + Tools.textMid(sql, \"select \", \" from\", 1).replaceAll(\" \", \"\") + \",\";\n" +
                    "            while (rs.next()) {\n" +
                    "                en = new " + 表名_驼峰_首字母大写 + "();\n" +
                    code1 +
                    "                list.add(en);\n" +
                    "            }\n" +
                    "            t2 = new Date().getTime();\n" +
                    "            sb.append(\";组装对象耗时:\").append(t2 - t1).append(\"毫秒\");\n" +
                    "            Log.sql(sb.toString());\n" +
                    "        } catch (Exception e) {\n" +
                    "            Log.e(e, sb.toString());\n" +
                    "        } finally {\n" +
                    "            DataBase.db.close(conn, rs, ps);\n" +
                    "        }\n" +
                    "        return list;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        return " + 表名_驼峰_首字母小写 + "Query(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public ListPage " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr, int page, int limit) {\n" +
                    "        ListPage listPage = new ListPage();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                    "        listPage.limitList(list, page, limit);\n" +
                    "        return listPage;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public ListPage " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit) {\n" +
                    "        limit=limit>maxLimit ? maxLimit : limit;\n" +
                    "        page=page>maxPage ? maxPage : page;\n" +
                    "        ListPage listPage = new ListPage();\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(ase.sql + \" limit \"+(maxPage*limit), ase.objs);\n" +
                    "        listPage.limitList(list, page, limit);\n" +
                    "        return listPage;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int mm) {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                    "        String key = DataBase.db.getKey(sql, arr);\n" +
                    "        String str = Cache.gzbCache.get(key);\n" +
                    "        if (str == null) {\n" +
                    "            Log.sql(\"Miss:\" + key);\n" +
                    "            list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                    "            Cache.gzbCache.set(key, list.toString(), mm);\n" +
                    "        } else {\n" +
                    "            Log.sql(\"Hit:\" + key);\n" +
                    "            list = " + 表名_驼峰_首字母小写 + "ToList(str);\n" +
                    "        }\n" +
                    "        return list;\n" +
                    "    }\n" +
                    "    @Override\n" +
                    "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int mm) {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        return " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql, ase.objs, mm);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public ListPage " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int page, int limit, int mm) {\n" +
                    "        ListPage listPage = new ListPage();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(sql, arr, mm);\n" +
                    "        listPage.limitList(list, page, limit);\n" +
                    "        return listPage;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public ListPage " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit, int mm) {\n" +
                    "        limit=limit>maxLimit ? maxLimit : limit;\n" +
                    "        page=page > maxPage ? maxPage : page;\n" +
                    "        ListPage listPage = new ListPage();\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql+ \" limit \"+(maxPage*limit), ase.objs, mm);\n" +
                    "        listPage.limitList(list, page, limit);\n" +
                    "        return listPage;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Delete(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toDelete();\n" +
                    "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Insert(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        " + 表名_驼峰_首字母小写 + ".set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toInsert();\n" +
                    "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Update(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                    "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        return " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母小写 + ", true);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", boolean auto) {\n" +
                    "        if (auto) {\n" +
                    "            " + 表名_驼峰_首字母小写 + ".set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                    "        }\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toInsert();\n" +
                    "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "DeleteAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                    "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "UpdateAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                    "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list) {\n" +
                    "        return " + 表名_驼峰_首字母小写 + "Batch(list, true);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list, boolean autoId) {\n" +
                    "        Connection conn = null;\n" +
                    "        ResultSet rs = null;\n" +
                    "        PreparedStatement ps = null;\n" +
                    "        StringBuilder sb = new StringBuilder();\n" +
                    "        try {\n" +
                    "            long t1 = new Date().getTime();\n" +
                    "            for (int i = 0; i < list.size(); i++) {\n" +
                    "                if (autoId) {\n" +
                    "                    list.get(i).set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                    "                }\n" +
                    "                AutoSqlEntity ase = list.get(i).toInsert();\n" +
                    "                if (i == 0) {\n" +
                    "                    sb.append(\"Batch:\").append(ase.sql).append(\";参数:\");\n" +
                    "                    conn = DataBase.db.getConnection();\n" +
                    "                    conn.setAutoCommit(false);\n" +
                    "                    ps = conn.prepareStatement(ase.sql);\n" +
                    "                }\n" +
                    "                for (int i1 = 0; i1 < ase.objs.length; i1++) {\n" +
                    "                    ps.setObject(i1 + 1, ase.objs[i1]);\n" +
                    "                }\n" +
                    "                sb.append(DataBase.db.getKey(ase.sql, ase.objs));\n" +
                    "                ps.addBatch();\n" +
                    "            }\n" +
                    "            long t2 = new Date().getTime();\n" +
                    "            sb.append(\";组装耗时:\");\n" +
                    "            sb.append(t2 - t1);\n" +
                    "            sb.append(\"毫秒\");\n" +
                    "            t1 = new Date().getTime();\n" +
                    "            int[] res = ps.executeBatch();\n" +
                    "            conn.commit();\n" +
                    "            t2 = new Date().getTime();\n" +
                    "            sb.append(\";执行耗时:\");\n" +
                    "            sb.append(t2 - t1);\n" +
                    "            sb.append(\"毫秒\");\n" +
                    "            Log.sql(sb.toString());\n" +
                    "            return res.length;\n" +
                    "        } catch (SQLIntegrityConstraintViolationException e) {\n" +
                    "            Log.e(e, \"ID冲突\" + sb.toString());\n" +
                    "            return -1;\n" +
                    "        } catch (Exception e) {\n" +
                    "            Log.e(e, sb.toString());\n" +
                    "            return -1;\n" +
                    "        } finally {\n" +
                    "            try {\n" +
                    "                conn.setAutoCommit(true);\n" +
                    "                DataBase.db.close(conn, rs, ps);\n" +
                    "            } catch (SQLException throwables) {\n" +
                    "                throwables.printStackTrace();\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Batch(String sql, List<Object[]> list) {\n" +
                    "        Connection conn = null;\n" +
                    "        ResultSet rs = null;\n" +
                    "        PreparedStatement ps = null;\n" +
                    "        StringBuilder sb = new StringBuilder();\n" +
                    "        try {\n" +
                    "            long t1 = new Date().getTime();\n" +
                    "            for (int i = 0; i < list.size(); i++) {\n" +
                    "                if (i == 0) {\n" +
                    "                    sb.append(\"Batch:\").append(sql).append(\";参数:\");\n" +
                    "                    conn = DataBase.db.getConnection();\n" +
                    "                    conn.setAutoCommit(false);\n" +
                    "                    ps = conn.prepareStatement(sql);\n" +
                    "                }\n" +
                    "                for (int i1 = 0; i1 < list.get(i).length; i1++) {\n" +
                    "                    ps.setObject(i1 + 1, list.get(i)[i1]);\n" +
                    "                }\n" +
                    "                sb.append(DataBase.db.getKey(sql, list.get(i)));\n" +
                    "                ps.addBatch();\n" +
                    "            }\n" +
                    "            long t2 = new Date().getTime();\n" +
                    "            sb.append(\";组装耗时:\");\n" +
                    "            sb.append(t2 - t1);\n" +
                    "            sb.append(\"毫秒\");\n" +
                    "            t1 = new Date().getTime();\n" +
                    "            int[] res = ps.executeBatch();\n" +
                    "            conn.commit();\n" +
                    "            t2 = new Date().getTime();\n" +
                    "            sb.append(\";执行耗时:\");\n" +
                    "            sb.append(t2 - t1);\n" +
                    "            sb.append(\"毫秒\");\n" +
                    "            Log.sql(sb.toString());\n" +
                    "            return res.length;\n" +
                    "        } catch (SQLIntegrityConstraintViolationException e) {\n" +
                    "            Log.e(e, \"ID冲突\" + sb.toString());\n" +
                    "            return -1;\n" +
                    "        } catch (Exception e) {\n" +
                    "            Log.e(e, sb.toString());\n" +
                    "            return -1;\n" +
                    "        } finally {\n" +
                    "            try {\n" +
                    "                conn.setAutoCommit(true);\n" +
                    "                DataBase.db.close(conn, rs, ps);\n" +
                    "            } catch (SQLException throwables) {\n" +
                    "                throwables.printStackTrace();\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n";
        }
        code = "package gzb.db." + dbName + ".dao;\n" +
                "import gzb.db." + dbName + ".DataBase;\n" +
                code2 +
                "import java.sql.*;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.Date;\n" +
                "import java.util.List;\n" +
                "import gzb.tools.ListPage;\n" +
                "import gzb.tools.Tools;\n" +
                "import gzb.tools.cache.Cache;\n" +
                "import gzb.tools.entity.AutoSqlEntity;\n" +
                "import gzb.tools.log.Log;\n" +
                "import gzb.tools.log.LogImpl;\n" +
                "\n" +
                "public class BaseDaoImpl implements BaseDao {\n" +
                "    static Log Log=new LogImpl(BaseDaoImpl.class);\n" +
                code3 +
                code4 +
                "}"
        ;

        return code;
    }


    public static String daoCode(DB_entity entity) {
        String 表名_驼峰_首字母小写 = lowStr_hump(entity.name);
        String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰_首字母小写);

        String id = lowStr_x(lowStr_x(entity.id));

        String code = "package gzb.db." + dbName + ".dao;\n" +
                "import gzb.db." + dbName + ".entity." + 表名_驼峰_首字母大写 + ";\n" +
                "import gzb.tools.ListPage;\n" +
                "import java.util.List;\n" +
                "public interface " + 表名_驼峰_首字母大写 + "Dao {\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(Long " + lowStr_hump(id) + ");\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(String sql, Object[] arr);\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(Long " + id + ",int mm);\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(String sql, Object[] arr,int mm);\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ",int mm);\n" +
                "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr);\n" +
                "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    ListPage " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr, int page, int limit);\n" +
                "    ListPage " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit);\n" +
                "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int mm);\n" +
                "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int mm);\n" +
                "    ListPage " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int page, int limit, int mm);\n" +
                "    ListPage " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit, int mm);\n" +
                "    int " + 表名_驼峰_首字母小写 + "Delete(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    int " + 表名_驼峰_首字母小写 + "Insert(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    int " + 表名_驼峰_首字母小写 + "Update(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list, boolean autoId);\n" +
                "    int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list);\n" +
                "    int " + 表名_驼峰_首字母小写 + "Batch(String sql, List<Object[]> list);\n" +
                "    int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", boolean auto);\n" +
                "    int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    int " + 表名_驼峰_首字母小写 + "DeleteAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    int " + 表名_驼峰_首字母小写 + "UpdateAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "}";


        return code;
    }

    public static String daoImplCode(DB_entity entity) {
        String 表名_驼峰_首字母小写 = lowStr_hump(entity.name);
        String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰_首字母小写);

        String id = lowStr_x(lowStr_x(entity.id));
        String code0 = "";
        for (int i = 0; i < entity.subName.size(); i++) {
            String 列名大写 = lowStr_hump(entity.subName.get(i), true);
            String 列名小写 = lowStr_hump(entity.subName.get(i));


            code0 += "                if (a.equals(\",*,\") || a.indexOf(\"," + 列名小写 + ",\") > -1 || a.indexOf(\"," + entity.subName.get(i) + ",\") > -1) {\n" +
                    "                    temp = rs.getString(\"" + entity.subName.get(i) + "\");\n" +
                    "                    if (temp != null) {\n" +
                    "                        en.set" + 列名大写 + "(" + entity.subType.get(i) + ".valueOf(temp));\n" +
                    "                    }\n" +
                    "                }\n";
        }
        String code = "package gzb.db." + dbName + ".dao;\n" +
                "import gzb.db." + dbName + ".DataBase;\n" +
                "import gzb.db." + dbName + ".entity." + 表名_驼峰_首字母大写 + ";\n" +
                "import gzb.tools.*;\n" +
                "import java.sql.*;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.Date;\n" +
                "import java.util.List;\n" +
                "import gzb.tools.cache.Cache;\n" +
                "import gzb.tools.entity.AutoSqlEntity;\n" +
                "import gzb.tools.log.Log;\n" +
                "import gzb.tools.log.LogImpl;\n" +
                "\n" +
                "public class " + 表名_驼峰_首字母大写 + "DaoImpl implements " + 表名_驼峰_首字母大写 + "Dao {\n" +
                "    static Log Log=new LogImpl(" + 表名_驼峰_首字母大写 + "DaoImpl.class);\n" +
                "    public final List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "ToList(String json) {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                "        json = json.substring(2, json.length() - 2);\n" +
                "        String[] ss1 = json.replaceAll(\"}, \\\\{\", \"},{\").split(\"},\\\\{\");\n" +
                "        for (int i = 0; i < ss1.length; i++) {\n" +
                "            list.add(new " + 表名_驼峰_首字母大写 + "(\"{\" + ss1[i] + \"}\"));\n" +
                "        }\n" +
                "        return list;\n" +
                "    }\n" +
                "    @Override\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(Long " + id + ") {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(\"select * from \"+DataBase." + 表名_驼峰_首字母小写 + "Name+\" where " + id + "=?\", Tools.toArray(" + id + "));\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(String sql, Object[] arr) {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(ase.sql, ase.objs);\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +


                "    @Override\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ",int mm) {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql, ase.objs,mm);\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(Long " + id + ",int mm) {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(\"select * from \"+DataBase." + 表名_驼峰_首字母小写 + "Name+\" where " + id + "=?\", Tools.toArray(" + id + "),mm);\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(String sql, Object[] arr,int mm) {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(sql, arr,mm);\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +


                "    @Override\n" +
                "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr) {\n" +
                "        Connection conn = null;\n" +
                "        ResultSet rs = null;\n" +
                "        PreparedStatement ps = null;\n" +
                "        if (arr == null) {\n" +
                "            arr = Tools.toArray();\n" +
                "        }\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                "        StringBuilder sb = new StringBuilder();\n" +
                "        sb.append(sql).append(\";参数:\");\n" +
                "        try {\n" +
                "            conn = DataBase.db.getConnection();\n" +
                "            ps = conn.prepareStatement(sql);\n" +
                "            " + 表名_驼峰_首字母大写 + " en;\n" +
                "            String temp = \"\";\n" +
                "            for (int i = 0; i < arr.length; i++) {\n" +
                "                sb.append(arr[i]).append(\",\");\n" +
                "                ps.setObject(i + 1, arr[i].toString());\n" +
                "            }\n" +
                "            long t1 = new Date().getTime();\n" +
                "            rs = ps.executeQuery();\n" +
                "            long t2 = new Date().getTime();\n" +
                "            sb.append(\";查询耗时:\").append(t2 - t1).append(\"毫秒\");\n" +
                "            t1 = new Date().getTime();\n" +
                "            String a = \",\" + Tools.textMid(sql, \"select \", \" from\", 1).replaceAll(\" \", \"\") + \",\";\n" +
                "            while (rs.next()) {\n" +
                "                en = new " + 表名_驼峰_首字母大写 + "();\n" +
                code0 +
                "                list.add(en);\n" +
                "            }\n" +
                "            t2 = new Date().getTime();\n" +
                "            sb.append(\";组装对象耗时:\").append(t2 - t1).append(\"毫秒\");\n" +
                "            Log.sql(sb.toString());\n" +
                "        } catch (Exception e) {\n" +
                "            Log.e(e, sb.toString());\n" +
                "        } finally {\n" +
                "            DataBase.db.close(conn, rs, ps);\n" +
                "        }\n" +
                "        return list;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        return " + 表名_驼峰_首字母小写 + "Query(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public ListPage " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr, int page, int limit) {\n" +
                "        ListPage listPage = new ListPage();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                "        listPage.limitList(list, page, limit);\n" +
                "        return listPage;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public ListPage " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit) {\n" +
                "        limit=limit>maxLimit ? maxLimit : limit;\n" +
                "        page=page>maxPage ? maxPage : page;\n" +
                "        ListPage listPage = new ListPage();\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(ase.sql + \" limit \"+(maxPage*limit), ase.objs);\n" +
                "        listPage.limitList(list, page, limit);\n" +
                "        return listPage;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int mm) {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                "        String key = DataBase.db.getKey(sql, arr);\n" +
                "        String str = Cache.gzbCache.get(key);\n" +
                "        if (str == null) {\n" +
                "            Log.sql(\"Miss:\" + key);\n" +
                "            list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                "            Cache.gzbCache.set(key, list.toString(), mm);\n" +
                "        } else {\n" +
                "            Log.sql(\"Hit:\" + key);\n" +
                "            list = " + 表名_驼峰_首字母小写 + "ToList(str);\n" +
                "        }\n" +
                "        return list;\n" +
                "    }\n" +
                "    @Override\n" +
                "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int mm) {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        return " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql, ase.objs, mm);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public ListPage " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int page, int limit, int mm) {\n" +
                "        ListPage listPage = new ListPage();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(sql, arr, mm);\n" +
                "        listPage.limitList(list, page, limit);\n" +
                "        return listPage;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public ListPage " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit, int mm) {\n" +
                "        limit=limit>maxLimit ? maxLimit : limit;\n" +
                "        page=page > maxPage ? maxPage : page;\n" +
                "        ListPage listPage = new ListPage();\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql+ \" limit \"+(maxPage*limit), ase.objs, mm);\n" +
                "        listPage.limitList(list, page, limit);\n" +
                "        return listPage;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Delete(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toDelete();\n" +
                "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Insert(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        " + 表名_驼峰_首字母小写 + ".set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toInsert();\n" +
                "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Update(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        return " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母小写 + ", true);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", boolean auto) {\n" +
                "        if (auto) {\n" +
                "            " + 表名_驼峰_首字母小写 + ".set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                "        }\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toInsert();\n" +
                "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "DeleteAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "UpdateAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list) {\n" +
                "        return " + 表名_驼峰_首字母小写 + "Batch(list, true);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list, boolean autoId) {\n" +
                "        Connection conn = null;\n" +
                "        ResultSet rs = null;\n" +
                "        PreparedStatement ps = null;\n" +
                "        StringBuilder sb = new StringBuilder();\n" +
                "        try {\n" +
                "            long t1 = new Date().getTime();\n" +
                "            for (int i = 0; i < list.size(); i++) {\n" +
                "                if (autoId) {\n" +
                "                    list.get(i).set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                "                }\n" +
                "                AutoSqlEntity ase = list.get(i).toInsert();\n" +
                "                if (i == 0) {\n" +
                "                    sb.append(\"Batch:\").append(ase.sql).append(\";参数:\");\n" +
                "                    conn = DataBase.db.getConnection();\n" +
                "                    conn.setAutoCommit(false);\n" +
                "                    ps = conn.prepareStatement(ase.sql);\n" +
                "                }\n" +
                "                for (int i1 = 0; i1 < ase.objs.length; i1++) {\n" +
                "                    ps.setObject(i1 + 1, ase.objs[i1]);\n" +
                "                }\n" +
                "                sb.append(DataBase.db.getKey(ase.sql, ase.objs));\n" +
                "                ps.addBatch();\n" +
                "            }\n" +
                "            long t2 = new Date().getTime();\n" +
                "            sb.append(\";组装耗时:\");\n" +
                "            sb.append(t2 - t1);\n" +
                "            sb.append(\"毫秒\");\n" +
                "            t1 = new Date().getTime();\n" +
                "            int[] res = ps.executeBatch();\n" +
                "            conn.commit();\n" +
                "            t2 = new Date().getTime();\n" +
                "            sb.append(\";执行耗时:\");\n" +
                "            sb.append(t2 - t1);\n" +
                "            sb.append(\"毫秒\");\n" +
                "            Log.sql(sb.toString());\n" +
                "            return res.length;\n" +
                "        } catch (SQLIntegrityConstraintViolationException e) {\n" +
                "            Log.e(e, \"ID冲突\" + sb.toString());\n" +
                "            return -1;\n" +
                "        } catch (Exception e) {\n" +
                "            Log.e(e, sb.toString());\n" +
                "            return -1;\n" +
                "        } finally {\n" +
                "            try {\n" +
                "                conn.setAutoCommit(true);\n" +
                "                DataBase.db.close(conn, rs, ps);\n" +
                "            } catch (SQLException throwables) {\n" +
                "                throwables.printStackTrace();\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Batch(String sql, List<Object[]> list) {\n" +
                "        Connection conn = null;\n" +
                "        ResultSet rs = null;\n" +
                "        PreparedStatement ps = null;\n" +
                "        StringBuilder sb = new StringBuilder();\n" +
                "        try {\n" +
                "            long t1 = new Date().getTime();\n" +
                "            for (int i = 0; i < list.size(); i++) {\n" +
                "                if (i == 0) {\n" +
                "                    sb.append(\"Batch:\").append(sql).append(\";参数:\");\n" +
                "                    conn = DataBase.db.getConnection();\n" +
                "                    conn.setAutoCommit(false);\n" +
                "                    ps = conn.prepareStatement(sql);\n" +
                "                }\n" +
                "                for (int i1 = 0; i1 < list.get(i).length; i1++) {\n" +
                "                    ps.setObject(i1 + 1, list.get(i)[i1]);\n" +
                "                }\n" +
                "                sb.append(DataBase.db.getKey(sql, list.get(i)));\n" +
                "                ps.addBatch();\n" +
                "            }\n" +
                "            long t2 = new Date().getTime();\n" +
                "            sb.append(\";组装耗时:\");\n" +
                "            sb.append(t2 - t1);\n" +
                "            sb.append(\"毫秒\");\n" +
                "            t1 = new Date().getTime();\n" +
                "            int[] res = ps.executeBatch();\n" +
                "            conn.commit();\n" +
                "            t2 = new Date().getTime();\n" +
                "            sb.append(\";执行耗时:\");\n" +
                "            sb.append(t2 - t1);\n" +
                "            sb.append(\"毫秒\");\n" +
                "            Log.sql(sb.toString());\n" +
                "            return res.length;\n" +
                "        } catch (SQLIntegrityConstraintViolationException e) {\n" +
                "            Log.e(e, \"ID冲突\" + sb.toString());\n" +
                "            return -1;\n" +
                "        } catch (Exception e) {\n" +
                "            Log.e(e, sb.toString());\n" +
                "            return -1;\n" +
                "        } finally {\n" +
                "            try {\n" +
                "                conn.setAutoCommit(true);\n" +
                "                DataBase.db.close(conn, rs, ps);\n" +
                "            } catch (SQLException throwables) {\n" +
                "                throwables.printStackTrace();\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "}";


        return code;
    }


    public static String entityCodeBaseDao(DB_entity entity) {
        String 表名 = lowStr_d((entity.name));
        String 表名_驼峰 = lowStr_hump(entity.name);
        String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰);
        String 表名_首字母大写 = lowStr_hump(entity.name);
        String 表名_首字母小写 = lowStr_hump(entity.name);
        表名 = 表名_驼峰;

        String id = lowStr_x(lowStr_x(entity.id));
        String code = "";
        String code0 = "";
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        String code5 = "";
        String code6 = "";
        String code7 = "";
        String code8 = "";
        String code9 = "";
        for (int i = 0; i < entity.subName.size(); i++) {
            String 列名_小写 = lowStr_x(entity.subName.get(i));
            String 列名_驼峰_小写 = lowStr_hump(列名_小写);
            String 列名_大写 = lowStr_d(列名_小写);
            String 列名_驼峰_大写 = lowStr_d(列名_驼峰_小写);
            code9 += "    private java.lang.String " + 列名_驼峰_小写 + "Operation=\"=\";\n";
            code4 += "        if (this." + 列名_驼峰_小写 + " !=null){sb.append(\"" + 列名_小写 + " \").append(" + 列名_驼峰_小写 + "Operation).append(\" ? and \");list.add(this." + 列名_驼峰_小写 + ");}\n";
            code8 += "        list.add(this." + 列名_驼峰_小写 + ");\n";
            code5 += "        if (this." + 列名_驼峰_小写 + " !=null){sb.append(\"" + 列名_小写 + "=?, \");list.add(this." + 列名_驼峰_小写 + ");}\n";

            code1 += "        tmp = Tools.textMid(json, \"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\", \"\\\"\", 1);\n" +
                    "        if (tmp != null && tmp.length() > 0){this." + 列名_驼峰_小写 + " = " + entity.subType.get(i) + ".valueOf(tmp);}\n";

            code1 += "        tmp = Tools.textMid(json, \"\\\"" + 列名_驼峰_小写 + "Operation\\\":\\\"\", \"\\\"\", 1);\n" +
                    "        if (tmp != null && tmp.length() > 0){this." + 列名_驼峰_小写 + "Operation = tmp;}\n";


            code3 += "    public " + entity.subType.get(i) + " get" + 列名_驼峰_大写 + "() {\n" +
                    "        return this." + 列名_驼峰_小写 + ";\n" +
                    "    }\n" +
                    "    public " + 表名_驼峰_首字母大写 + " set" + 列名_驼峰_大写 + "(" + entity.subType.get(i) + " " + 列名_驼峰_小写 + ") {\n" +
                    "        this." + 列名_驼峰_小写 + " = " + 列名_驼峰_小写 + ";\n" +
                    "        return this;\n" +
                    "    }\n" +
                    "    public " + 表名_驼峰_首字母大写 + " set" + 列名_驼峰_大写 + "(" + entity.subType.get(i) + " " + 列名_驼峰_小写 + ",java.lang.String " + 列名_驼峰_小写 + "Operation) {\n" +
                    "        this." + 列名_驼峰_小写 + " = " + 列名_驼峰_小写 + ";\n" +
                    "        this." + 列名_驼峰_小写 + "Operation = " + 列名_驼峰_小写 + "Operation;\n" +
                    "        return this;\n" +
                    "    }\n";
            code0 += "    private " + entity.subType.get(i) + " " + 列名_驼峰_小写 + ";\n";
            if (i == entity.subName.size() - 1) {
                code6 += 列名_小写 + "";
                code7 += "?";
                code2 += "        if (" + 列名_驼峰_小写 + " != null){sb.append(\"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\").append(" + 列名_驼峰_小写 + ").append(\"\\\"\");}\n";
            } else {
                code6 += 列名_小写 + ",";
                code7 += "?,";
                code2 += "        if (" + 列名_驼峰_小写 + " != null){sb.append(\"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\").append(" + 列名_驼峰_小写 + ").append(\"\\\",\");}\n";
            }
        }
        code += "package gzb.db." + dbName + ".entity;\n" +
                "import gzb.db." + dbName + ".DataBase;\n" +
                "import gzb.db." + dbName + ".dao.BaseDao;\n" +
                "import gzb.tools.Tools;\n" +
                "import gzb.tools.ListPage;\n" +
                "import gzb.tools.entity.AutoSqlEntity;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "public class " + 表名_驼峰_首字母大写 + " {\n" +
                code0 +
                code9 +
                "    private java.util.List<?>list;\n" +
                "    public List<?> getList() {\n" +
                "        return list;\n" +
                "    }\n" +
                "    public void setList(List<?> list) {\n" +
                "        this.list = list;\n" +
                "    }\n" +
                "    public " + 表名_驼峰_首字母大写 + "() {\n" +
                "    }\n" +
                "    public " + 表名_驼峰_首字母大写 + "(String json) {\n" +
                "        String tmp;\n" +
                code1 +
                "    }\n" +
                "    @Override\n" +
                "    public String toString() {\n" +
                "        return toJson();\n" +
                "    }\n" +
                "    public String toJson() {\n" +
                "        StringBuilder sb = new StringBuilder();\n" +
                "        sb.append(\"{\");\n" +
                code2 +
                "        if (list != null){sb.append(\",\\\"data\\\":\").append(list.toString()).append(\",\");}\n" +
                "        if (sb.substring(sb.length()-1,sb.length()).equals(\",\"))sb.delete(sb.length()-1,sb.length()).equals(\",\");\n" +
                "        sb.append(\"}\");\n" +
                "        return sb.toString();\n" +
                "    }\n" +
                code3 +
                "    public AutoSqlEntity toWhere(String sql){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(sql+\" where \");\n" +
                code4 +
                "        if (sb.substring(sb.length()-5,sb.length()).equals(\" and \"))sb.delete(sb.length()-5,sb.length());\n" +
                "        if (sb.substring(sb.length()-6,sb.length()).equals(\"where \"))sb.delete(sb.length()-6,sb.length());\n" +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +
                "    public AutoSqlEntity toSelect(){\n" +
                "        return toWhere(\"select * from \"+DataBase." + 表名 + "Name);\n" +
                "    }\n" +
                "    public AutoSqlEntity toDelete(){\n" +
                "        return toWhere(\"delete from \"+DataBase." + 表名 + "Name);\n" +
                "    }\n" +
                "    public AutoSqlEntity toUpdate(){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(\"update \"+DataBase." + 表名 + "Name+\" set \");\n" +
                code5 +
                "        if (sb.substring(sb.length()-2,sb.length()).equals(\", \"))sb.delete(sb.length()-2,sb.length()-1);\n" +
                "        if (sb.substring(sb.length()-4,sb.length()).equals(\"set \"))sb.delete(sb.length()-4,sb.length());\n" +
                "        sb.append(\"where " + id + "=?\");list.add(this." + lowStr_hump(id) + ");\n" +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +
                "    public AutoSqlEntity toInsert(){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(\"insert into \"+DataBase." + 表名 + "Name+\"(" + code6 + ") values(" + code7 + ")\");\n" +
                code8 +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +

                "    public " + lowStr_d(表名) + " find(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Find(this);\n" +
                "    }\n" +
                "\n" +
                "    public " + lowStr_d(表名) + " findCache(BaseDao dao, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "FindCache(this, mm);\n" +
                "    }\n" +
                "\n" +
                "    public List<" + lowStr_d(表名) + "> query(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Query(this);\n" +
                "    }\n" +
                "\n" +
                "    public List<" + lowStr_d(表名) + "> queryCache(BaseDao dao, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "QueryCache(this, mm);\n" +
                "    }\n" +
                "\n" +
                "    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {\n" +
                "        return dao." + lowStr_x(表名) + "Query(this, page, limit, maxPage, maxLimit);\n" +
                "    }\n" +
                "\n" +
                "    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "QueryCache(this, page, limit, maxPage, maxLimit, mm);\n" +
                "    }\n" +
                "\n" +
                "    public int save(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Insert(this);\n" +
                "    }\n" +
                "\n" +
                "    public int update(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Update(this);\n" +
                "    }\n" +
                "\n" +
                "    public int delete(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Delete(this);\n" +
                "    }\n" +
                "\n" +
                "    public int saveAsy(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "InsertAsy(this);\n" +
                "    }\n" +
                "\n" +
                "    public int updateAsy(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "UpdateAsy(this);\n" +
                "    }\n" +
                "\n" +
                "    public int deleteAsy(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "DeleteAsy(this);\n" +
                "    }\n" +
                "}";
        return code;
    }

    public static String entityCodeDao(DB_entity entity) {
        String 表名 = lowStr_d((entity.name));
        String 表名_驼峰 = lowStr_hump(entity.name);
        String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰);
        String 表名_首字母大写 = lowStr_hump(entity.name);
        String 表名_首字母小写 = lowStr_hump(entity.name);
        表名 = 表名_驼峰;

        String id = lowStr_x(lowStr_x(entity.id));
        String code = "";
        String code0 = "";
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        String code5 = "";
        String code6 = "";
        String code7 = "";
        String code8 = "";
        String code9 = "";
        for (int i = 0; i < entity.subName.size(); i++) {
            String 列名_小写 = lowStr_x(entity.subName.get(i));
            String 列名_驼峰_小写 = lowStr_hump(列名_小写);
            String 列名_大写 = lowStr_d(列名_小写);
            String 列名_驼峰_大写 = lowStr_d(列名_驼峰_小写);
            code9 += "    private java.lang.String " + 列名_驼峰_小写 + "Operation=\"=\";\n";
            code4 += "        if (this." + 列名_驼峰_小写 + " !=null){sb.append(\"" + 列名_小写 + " \").append(" + 列名_驼峰_小写 + "Operation).append(\" ? and \");list.add(this." + 列名_驼峰_小写 + ");}\n";
            code8 += "        list.add(this." + 列名_驼峰_小写 + ");\n";
            code5 += "        if (this." + 列名_驼峰_小写 + " !=null){sb.append(\"" + 列名_小写 + "=?, \");list.add(this." + 列名_驼峰_小写 + ");}\n";

            code1 += "        tmp = Tools.textMid(json, \"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\", \"\\\"\", 1);\n" +
                    "        if (tmp != null && tmp.length() > 0){this." + 列名_驼峰_小写 + " = " + entity.subType.get(i) + ".valueOf(tmp);}\n";

            code1 += "        tmp = Tools.textMid(json, \"\\\"" + 列名_驼峰_小写 + "Operation\\\":\\\"\", \"\\\"\", 1);\n" +
                    "        if (tmp != null && tmp.length() > 0){this." + 列名_驼峰_小写 + "Operation = tmp;}\n";


            code3 += "    public " + entity.subType.get(i) + " get" + 列名_驼峰_大写 + "() {\n" +
                    "        return this." + 列名_驼峰_小写 + ";\n" +
                    "    }\n" +
                    "    public " + 表名_驼峰_首字母大写 + " set" + 列名_驼峰_大写 + "(" + entity.subType.get(i) + " " + 列名_驼峰_小写 + ") {\n" +
                    "        this." + 列名_驼峰_小写 + " = " + 列名_驼峰_小写 + ";\n" +
                    "        return this;\n" +
                    "    }\n" +
                    "    public " + 表名_驼峰_首字母大写 + " set" + 列名_驼峰_大写 + "(" + entity.subType.get(i) + " " + 列名_驼峰_小写 + ",java.lang.String " + 列名_驼峰_小写 + "Operation) {\n" +
                    "        this." + 列名_驼峰_小写 + " = " + 列名_驼峰_小写 + ";\n" +
                    "        this." + 列名_驼峰_小写 + "Operation = " + 列名_驼峰_小写 + "Operation;\n" +
                    "        return this;\n" +
                    "    }\n";
            code0 += "    private " + entity.subType.get(i) + " " + 列名_驼峰_小写 + ";\n";
            if (i == entity.subName.size() - 1) {
                code6 += 列名_小写 + "";
                code7 += "?";
                code2 += "        if (" + 列名_驼峰_小写 + " != null){sb.append(\"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\").append(" + 列名_驼峰_小写 + ").append(\"\\\"\");}\n";
            } else {
                code6 += 列名_小写 + ",";
                code7 += "?,";
                code2 += "        if (" + 列名_驼峰_小写 + " != null){sb.append(\"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\").append(" + 列名_驼峰_小写 + ").append(\"\\\",\");}\n";
            }
        }
        code += "package gzb.db." + dbName + ".entity;\n" +
                "import gzb.db." + dbName + ".DataBase;\n" +
                "import gzb.db." + dbName + ".dao." + lowStr_d(表名) + "Dao;\n" +
                "import gzb.tools.Tools;\n" +
                "import gzb.tools.ListPage;\n" +
                "import gzb.tools.entity.AutoSqlEntity;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "public class " + 表名_驼峰_首字母大写 + " {\n" +
                code0 +
                code9 +
                "    private java.util.List<?>list;\n" +
                "    public List<?> getList() {\n" +
                "        return list;\n" +
                "    }\n" +
                "    public void setList(List<?> list) {\n" +
                "        this.list = list;\n" +
                "    }\n" +
                "    public " + 表名_驼峰_首字母大写 + "() {\n" +
                "    }\n" +
                "    public " + 表名_驼峰_首字母大写 + "(String json) {\n" +
                "        String tmp;\n" +
                code1 +
                "    }\n" +
                "    @Override\n" +
                "    public String toString() {\n" +
                "        return toJson();\n" +
                "    }\n" +
                "    public String toJson() {\n" +
                "        StringBuilder sb = new StringBuilder();\n" +
                "        sb.append(\"{\");\n" +
                code2 +
                "        if (list != null){sb.append(\",\\\"data\\\":\").append(list.toString()).append(\",\");}\n" +
                "        if (sb.substring(sb.length()-1,sb.length()).equals(\",\"))sb.delete(sb.length()-1,sb.length()).equals(\",\");\n" +
                "        sb.append(\"}\");\n" +
                "        return sb.toString();\n" +
                "    }\n" +
                code3 +
                "    public AutoSqlEntity toWhere(String sql){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(sql+\" where \");\n" +
                code4 +
                "        if (sb.substring(sb.length()-5,sb.length()).equals(\" and \"))sb.delete(sb.length()-5,sb.length());\n" +
                "        if (sb.substring(sb.length()-6,sb.length()).equals(\"where \"))sb.delete(sb.length()-6,sb.length());\n" +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +
                "    public AutoSqlEntity toSelect(){\n" +
                "        return toWhere(\"select * from \"+DataBase." + 表名 + "Name);\n" +
                "    }\n" +
                "    public AutoSqlEntity toDelete(){\n" +
                "        return toWhere(\"delete from \"+DataBase." + 表名 + "Name);\n" +
                "    }\n" +
                "    public AutoSqlEntity toUpdate(){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(\"update \"+DataBase." + 表名 + "Name+\" set \");\n" +
                code5 +
                "        if (sb.substring(sb.length()-2,sb.length()).equals(\", \"))sb.delete(sb.length()-2,sb.length()-1);\n" +
                "        if (sb.substring(sb.length()-4,sb.length()).equals(\"set \"))sb.delete(sb.length()-4,sb.length());\n" +
                "        sb.append(\"where " + id + "=?\");list.add(this." + lowStr_hump(id) + ");\n" +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +
                "    public AutoSqlEntity toInsert(){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(\"insert into \"+DataBase." + 表名 + "Name+\"(" + code6 + ") values(" + code7 + ")\");\n" +
                code8 +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +


                "    public " + lowStr_d(表名) + " find(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Find(this);\n" +
                "    }\n" +
                "\n" +
                "    public " + lowStr_d(表名) + " findCache(" + lowStr_d(表名) + "Dao dao, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "FindCache(this, mm);\n" +
                "    }\n" +
                "\n" +
                "    public List<" + lowStr_d(表名) + "> query(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Query(this);\n" +
                "    }\n" +
                "\n" +
                "    public List<" + lowStr_d(表名) + "> queryCache(" + lowStr_d(表名) + "Dao dao, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "QueryCache(this, mm);\n" +
                "    }\n" +
                "\n" +
                "    public ListPage query(" + lowStr_d(表名) + "Dao dao, int page, int limit, int maxPage, int maxLimit) {\n" +
                "        return dao." + lowStr_x(表名) + "Query(this, page, limit, maxPage, maxLimit);\n" +
                "    }\n" +
                "\n" +
                "    public ListPage queryCache(" + lowStr_d(表名) + "Dao dao, int page, int limit, int maxPage, int maxLimit, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "QueryCache(this, page, limit, maxPage, maxLimit, mm);\n" +
                "    }\n" +
                "\n" +
                "    public int save(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Insert(this);\n" +
                "    }\n" +
                "\n" +
                "    public int update(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Update(this);\n" +
                "    }\n" +
                "\n" +
                "    public int delete(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Delete(this);\n" +
                "    }\n" +
                "\n" +
                "    public int saveAsy(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "InsertAsy(this);\n" +
                "    }\n" +
                "\n" +
                "    public int updateAsy(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "UpdateAsy(this);\n" +
                "    }\n" +
                "\n" +
                "    public int deleteAsy(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "DeleteAsy(this);\n" +
                "    }\n" +
                "}";
        return code;
    }

    public static List<DB_entity> getMapInfo(String mapNames) throws SQLException {
        mapNames = mapNames.toLowerCase();
        List<DB_entity> list = new ArrayList<DB_entity>();
        Connection conn = db.getConnection();
        DatabaseMetaData meta = conn.getMetaData();
        PreparedStatement ps = null;
        ResultSet rs = meta.getTables(null, null, null, Tools.toArrayString("TABLE"));
        while (rs.next()) {
            String tbname = rs.getString("TABLE_NAME").toLowerCase();
            if (mapNames.equals("*") == false && mapNames.indexOf(tbname + "/") < 0) {
                System.out.println("跳过表:" + tbname);
                continue;
            }
            DB_entity mi = new DB_entity();
            mi.name = tbname;
            ps = conn.prepareStatement("select * from " + tbname + " limit 1");
            ResultSetMetaData col = ps.getMetaData();
            ResultSet rst = meta.getPrimaryKeys(null, null, tbname);
            rst.next();
            String idname = rst.getString("COLUMN_NAME");
            mi.id = idname;
            mi.subName = new ArrayList<String>();
            mi.subType = new ArrayList<String>();
            for (int i = 1; i <= col.getColumnCount(); i++) {
                String columnClassName = col.getColumnClassName(i);
                String columnName = col.getColumnName(i);
                if ("[B".equals(columnClassName)) {
                    columnClassName = "java.lang.Byte";
                }
                if ("java.sql.Timestamp".equals(columnClassName)) {
                    columnClassName = "java.lang.String";
                }
                if ("java.time.LocalDateTime".equals(columnClassName)) {
                    columnClassName = "java.lang.String";
                }
                if ("java.lang.Boolean".equals(columnClassName)) {
                    columnClassName = "java.lang.Integer";
                }
                mi.subName.add(columnName);
                mi.subType.add(columnClassName);
                if (mi.id.equals(columnName)) {
                    mi.idType = columnClassName;
                }


            }
            list.add(mi);
        }

        return list;
    }

    //首字母转大写
    public static String lowStr_d(String s) {
        return s.substring(0, 1).toUpperCase() + (s.substring(1, s.length()));
    }

    //首字母转小写
    public static String lowStr_x(String s) {
        return s.substring(0, 1).toLowerCase() + (s.substring(1, s.length()));
    }

    public static String lowStr_hump(String str, boolean InitialCase) {
        String[] ss1 = str.split("_");
        String newString = "";
        for (int i = 0; i < ss1.length; i++) {
            if (i == 0) {
                if (InitialCase) {
                    newString += lowStr_d(ss1[i]);
                } else {
                    newString += (ss1[i]);
                }

            } else {
                newString += lowStr_d(ss1[i]);
            }
        }
        return newString;
    }

    public static String lowStr_hump(String str) {
        return lowStr_hump(str, false);
    }
}

class AutoOriginal {
    static gzb.tools.log.Log Log = new LogImpl(AutoOriginal.class);
    static String dbName = "script";
    static int type = 0;//0 basedao  1dao
    static String pkg;
    private static DB db;
    static String mapName = "*";

    public static void generate(String dbName1, String mapName1, boolean baseDao) {
        try {
            dbName = dbName1;
            mapName = mapName1;
            db = new DB(dbName);
            type = baseDao ? 0 : 1;
            pkg = Tools.getProjectPath() + "../src/main/java/gzb/";
            List<DB_entity> list = getMapInfo(mapName);
            Log.print(list);

            String code, path;
            //database 生成
            code = dataBaseCode(list);
            path = pkg + "/db/" + dbName + "/";
            new File(path).mkdirs();
            Tools.fileSaveString(path + "DataBase.java", code, false);
            if (type == 0) {
                //baseDaoCode 生成
                code = baseDaoCode(list);
                path = pkg + "/db/" + dbName + "/dao/";
                new File(path).mkdirs();
                Tools.fileSaveString(path + "BaseDao.java", code, false);
                //baseDaoImplCode 生成
                code = baseDaoImplCode(list);
                path = pkg + "/db/" + dbName + "/dao/";
                new File(path).mkdirs();
                Tools.fileSaveString(path + "BaseDaoImpl.java", code, false);
            }
            for (DB_entity db_entity : list) {
                if (type == 0) {
                    //实体类生成
                    code = entityCodeBaseDao(db_entity);
                    path = pkg + "/db/" + dbName + "/entity/";
                    new File(path).mkdirs();
                    Tools.fileSaveString(path + lowStr_hump(db_entity.name, true) + ".java", code, false);
                } else {
                    //实体类生成
                    code = entityCodeDao(db_entity);
                    path = pkg + "/db/" + dbName + "/entity/";
                    new File(path).mkdirs();
                    Tools.fileSaveString(path + lowStr_hump(db_entity.name, true) + ".java", code, false);
                    // Dao 生成
                    code = daoCode(db_entity);
                    path = pkg + "/db/" + dbName + "/dao/";
                    new File(path).mkdirs();
                    Tools.fileSaveString(path + lowStr_hump((db_entity.name), true) + "Dao.java", code, false);

                    // DaoImpl 生成
                    code = daoImplCode(db_entity);
                    path = pkg + "/db/" + dbName + "/dao/";
                    new File(path).mkdirs();
                    Tools.fileSaveString(path + lowStr_hump((db_entity.name), true) + "DaoImpl.java", code, false);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static String dataBaseCode(List<DB_entity> list) {
        String code0 = "";
        String code1 = "";
        for (DB_entity entity : list) {
            String 表名大写 = lowStr_d(entity.name);
            String 表名小写 = lowStr_x(entity.name);
            String id = (lowStr_x(entity.id));
            code0 += "    public static String " + lowStr_hump(表名小写) + "Name=\"" + 表名小写 + "\";\n";
            code1 += "            " + lowStr_hump(表名小写) + "Name =division(" + lowStr_hump(表名小写) + "Name,Tools.configGetInteger(\"gzb.db." + dbName + ".division." + lowStr_x(entity.name) + "\",\"0\"));\n";
        }

        String code = "package gzb.db." + dbName + ";\n" +
                "\n" +
                "import gzb.db.DB;\n" +
                "import gzb.tools.*;\n" +
                "import gzb.tools.thread.GzbThread;\n" +
                "import gzb.tools.thread.ThreadPool;\n" +
                "\n" +
                "import java.sql.*;\n" +
                "import java.util.*;\n" +
                "import java.util.Date;\n" +
                "import java.util.concurrent.locks.Lock;\n" +
                "import java.util.concurrent.locks.ReentrantLock;\n" +
                "import gzb.tools.log.Log;\n" +
                "import gzb.tools.log.LogImpl;\n" +
                "\n" +
                "public class DataBase {\n" +
                "    static Log Log=new LogImpl(DataBase.class);\n" +
                code0 +
                "    public static DB db = new DB(\"" + dbName + "\");\n" +
                "    public static Map<String, List<Object[]>> mapAskSql = new HashMap<>();\n" +
                "    public static Lock lockAsy = new ReentrantLock();\n" +
                "\n" +
                "    static {\n" +
                "        try {\n" +
                code1 +
                "\n" +
                "        } catch (Exception e) {\n" +
                "            e.printStackTrace();\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "\n" +
                "    public static final int addAsyInfo(String sql, Object[] objs) {\n" +
                "        DataBase.lockAsy.lock();\n" +
                "        try {\n" +
                "            List<Object[]> list = DataBase.mapAskSql.get(sql);\n" +
                "            if (list == null) {\n" +
                "                list = new ArrayList<>();\n" +
                "            }\n" +
                "            list.add(objs);\n" +
                "            DataBase.mapAskSql.put(sql, list);\n" +
                "            return 1;\n" +
                "        } finally {\n" +
                "            DataBase.lockAsy.unlock();\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    static {\n" +
                "        ThreadPool.start(new GzbThread() {\n" +
                "            @Override\n" +
                "            public void start() {\n" +
                "                Connection conn = null;\n" +
                "                ResultSet rs = null;\n" +
                "                PreparedStatement ps = null;\n" +
                "                while (true) {\n" +
                "                    try {\n" +
                "                        if (mapAskSql.size() == 0) {\n" +
                "                            Thread.sleep(100);\n" +
                "                            continue;\n" +
                "                        }\n" +
                "                        Map<String, List<Object[]>> map = mapAskSql;\n" +
                "                        List<Object[]> list = null;\n" +
                "                        mapAskSql = new HashMap<>();\n" +
                "                        lockAsy.lock();\n" +
                "                        lockAsy.unlock();\n" +
                "                        conn = DataBase.db.getConnection();\n" +
                "                        try {\n" +
                "                            conn.setAutoCommit(false);\n" +
                "                            for (Iterator<Map.Entry<String, List<Object[]>>> it = map.entrySet().iterator(); it.hasNext(); ) {\n" +
                "                                long t1 = new Date().getTime();\n" +
                "                                StringBuilder sb = new StringBuilder();\n" +
                "                                Map.Entry<String, List<Object[]>> en = it.next();\n" +
                "                                ps = conn.prepareStatement(en.getKey());\n" +
                "                                list = en.getValue();\n" +
                "                                sb.append(\"异步操作\").append(en.getKey())\n" +
                "                                        .append(\"[\")\n" +
                "                                        .append(list.size())\n" +
                "                                        .append(\"条]:\");\n" +
                "                                for (int i = 0; i < list.size(); i++) {\n" +
                "                                    Object[] objs = list.get(i);\n" +
                "                                    sb.append(\"{\");\n" +
                "                                    for (int j = 0; j < objs.length; j++) {\n" +
                "                                        ps.setObject(j + 1, objs[j]);\n" +
                "                                        if (j == objs.length - 1) {\n" +
                "                                            sb.append(\"'\").append(objs[j]).append(\"'\");\n" +
                "                                        } else {\n" +
                "                                            sb.append(\"'\").append(objs[j]).append(\"',\");\n" +
                "                                        }\n" +
                "                                    }\n" +
                "\n" +
                "                                    if (i == list.size() - 1) {\n" +
                "                                        sb.append(\"}\\r\\n\");\n" +
                "                                    } else {\n" +
                "                                        sb.append(\"},\");\n" +
                "                                    }\n" +
                "                                    ps.addBatch();\n" +
                "                                }\n" +
                "                                long t2 = new Date().getTime();\n" +
                "                                sb.append(\"组装耗时:\");\n" +
                "                                sb.append(t2 - t1);\n" +
                "                                sb.append(\"毫秒\");\n" +
                "                                t1 = new Date().getTime();\n" +
                "                                int[] res = ps.executeBatch();\n" +
                "                                conn.commit();\n" +
                "                                t2 = new Date().getTime();\n" +
                "                                sb.append(\";执行耗时:\");\n" +
                "                                sb.append(t2 - t1);\n" +
                "                                sb.append(\"毫秒\");\n" +
                "                                Log.sql(sb.toString());\n" +
                "                            }\n" +
                "                        } finally {\n" +
                "                            try {\n" +
                "                                conn.setAutoCommit(true);\n" +
                "                                DataBase.db.close(conn, rs, ps);\n" +
                "                            } catch (SQLException e) {\n" +
                "                                e.printStackTrace();\n" +
                "                            }\n" +
                "                        }\n" +
                "                    } catch (Exception e) {\n" +
                "                        Log.e(e);\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }, \"DataBase.asy\", false);\n" +
                "    }\n" +
                "    //0不开启  1按年分表 2按月分表 3按天分表\n" +
                "    public static final String division(String mapName, int lv) {\n" +
                "        Object[] arr = Tools.toArray(\"\", \"_yyyy\", \"_yyyy_MM\", \"_yyyy_MM_dd\");\n" +
                "        if (lv == 0 || lv > 3) {\n" +
                "            Log.i(\"未开启分表：\" + mapName);\n" +
                "            return mapName;\n" +
                "        }\n" +
                "        String newMapName = null;\n" +
                "        String newSql = \"\";\n" +
                "        Connection conn = null;\n" +
                "        ResultSet rs = null;\n" +
                "        PreparedStatement ps = null;\n" +
                "        try {\n" +
                "            conn = db.getConnection();\n" +
                "            for (int i = 0; i < 2; i++) {\n" +
                "                newMapName = mapName;\n" +
                "                newMapName += new DateTime().monthAdd(2-(i+1)).format(arr[lv].toString());\n" +
                "                try {\n" +
                "                    ps = conn.prepareStatement(\"show create table \" + newMapName);\n" +
                "                    rs = ps.executeQuery();\n" +
                "                    Log.i(\"已存在分表：\" + newMapName);\n" +
                "                } catch (Exception e) {\n" +
                "                    try {\n" +
                "                        Log.i(\"创建表：\" + newMapName);\n" +
                "                        ps = conn.prepareStatement(\"show create table \" + mapName);\n" +
                "                        rs = ps.executeQuery();\n" +
                "                        while (rs.next()) {\n" +
                "                            newSql = rs.getString(2);\n" +
                "                            newSql = newSql.replace(\"`\" + mapName + \"`\", \"`\" + newMapName + \"`\");\n" +
                "                            db.runSqlUpdateOrSaveOrDelete(newSql, null);\n" +
                "                        }\n" +
                "                    } catch (SQLException throwables) {\n" +
                "                        throwables.printStackTrace();\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        } catch (SQLException throwables) {\n" +
                "            throwables.printStackTrace();\n" +
                "        } finally {\n" +
                "            db.close(conn, rs, ps);\n" +
                "        }\n" +
                "\n" +
                "        return newMapName;\n" +
                "    }\n" +

                "}\n";


        return code;
    }

    public static String baseDaoCode(List<DB_entity> list) {
        String code1 = "";
        String code2 = "";
        for (DB_entity db_entity : list) {
            String 表名_驼峰_首字母小写 = lowStr_hump(db_entity.name);
            String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰_首字母小写);
            String 表名_首字母大写 = lowStr_hump(db_entity.name);
            String 表名_首字母小写 = lowStr_hump(db_entity.name);
            String id = lowStr_x(lowStr_x(db_entity.id));
            code2 += "import gzb.db." + dbName + ".entity." + 表名_驼峰_首字母大写 + ";\n";
            code1 +=
                    "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(Long " + lowStr_hump(id) + ");\n" +
                            "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(String sql, Object[] arr);\n" +
                            "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(Long " + id + ",int mm);\n" +
                            "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(String sql, Object[] arr,int mm);\n" +
                            "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ",int mm);\n" +
                            "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr);\n" +
                            "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    ListPage " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr, int page, int limit);\n" +
                            "    ListPage " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit);\n" +
                            "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int mm);\n" +
                            "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int mm);\n" +
                            "    ListPage " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int page, int limit, int mm);\n" +
                            "    ListPage " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit, int mm);\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Delete(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Insert(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Update(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list, boolean autoId);\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list);\n" +
                            "    int " + 表名_驼峰_首字母小写 + "Batch(String sql, List<Object[]> list);\n" +
                            "    int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", boolean auto);\n" +
                            "    int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    int " + 表名_驼峰_首字母小写 + "DeleteAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                            "    int " + 表名_驼峰_首字母小写 + "UpdateAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n";
        }

        String code = "package gzb.db." + dbName + ".dao;\n" +
                code2 +
                "import gzb.tools.ListPage;\n" +
                "import java.util.List;\n" +
                "public interface BaseDao {\n" +
                code1 + "\n" +
                "}";
        return code;
    }

    public static String baseDaoImplCode(List<DB_entity> list) {
        String code = "";
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        for (DB_entity db_entity : list) {
            String 表名_驼峰_首字母小写 = lowStr_hump(db_entity.name);
            String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰_首字母小写);
            String 表名_首字母大写 = lowStr_hump(db_entity.name);
            String 表名_首字母小写 = lowStr_hump(db_entity.name);
            String id = lowStr_x(lowStr_x(db_entity.id));
            code1 = "";
            for (int i = 0; i < db_entity.subName.size(); i++) {
                String 列名大写 = lowStr_hump(db_entity.subName.get(i), true);
                String 列名小写 = lowStr_hump(db_entity.subName.get(i));
                code1 += "                if (a.equals(\",*,\") || a.indexOf(\"," + 列名小写 + ",\") > -1 || a.indexOf(\"," + db_entity.subName.get(i) + ",\") > -1) {\n" +
                        "                    temp = rs.getString(\"" + db_entity.subName.get(i) + "\");\n" +
                        "                    if (temp != null) {\n" +
                        "                        en.set" + 列名大写 + "(" + db_entity.subType.get(i) + ".valueOf(temp));\n" +
                        "                    }\n" +
                        "                }\n";
            }
//////////////////////////////////////////////////////////////
            code2 += "import gzb.db." + dbName + ".entity." + 表名_驼峰_首字母大写 + ";\n";
            code3 += "    public final List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "ToList(String json) {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                    "        if (json.length()<3){\n" +
                    "            return list;\n" +
                    "        }\n" +
                    "        json = json.substring(2, json.length() - 2);\n" +
                    "        String[] ss1 = json.replaceAll(\"}, \\\\{\", \"},{\").split(\"},\\\\{\");\n" +
                    "        for (int i = 0; i < ss1.length; i++) {\n" +
                    "            list.add(new " + 表名_驼峰_首字母大写 + "(\"{\" + ss1[i] + \"}\"));\n" +
                    "        }\n" +
                    "        return list;\n" +
                    "    }\n";
            code4 += "    @Override\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(Long " + id + ") {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(\"select * from \"+DataBase." + 表名_驼峰_首字母小写 + "Name+\" where " + id + "=?\", Tools.toArray(" + id + "));\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +
                    "    @Override\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(String sql, Object[] arr) {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +
                    "    @Override\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(ase.sql, ase.objs);\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +


                    "    @Override\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ",int mm) {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql, ase.objs,mm);\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(Long " + id + ",int mm) {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(\"select * from \"+DataBase." + 表名_驼峰_首字母小写 + "Name+\" where " + id + "=?\", Tools.toArray(" + id + "),mm);\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +
                    "    @Override\n" +
                    "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(String sql, Object[] arr,int mm) {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(sql, arr,mm);\n" +
                    "        if (list.size() != 1) {\n" +
                    "            return null;\n" +
                    "        }\n" +
                    "        return list.get(0);\n" +
                    "    }\n" +


                    "    @Override\n" +
                    "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr) {\n" +
                    "        Connection conn = null;\n" +
                    "        ResultSet rs = null;\n" +
                    "        PreparedStatement ps = null;\n" +
                    "        if (arr == null) {\n" +
                    "            arr = Tools.toArray();\n" +
                    "        }\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                    "        StringBuilder sb = new StringBuilder();\n" +
                    "        sb.append(sql).append(\";参数:\");\n" +
                    "        try {\n" +
                    "            conn = DataBase.db.getConnection();\n" +
                    "            ps = conn.prepareStatement(sql);\n" +
                    "            " + 表名_驼峰_首字母大写 + " en;\n" +
                    "            String temp = \"\";\n" +
                    "            for (int i = 0; i < arr.length; i++) {\n" +
                    "                sb.append(arr[i]).append(\",\");\n" +
                    "                ps.setObject(i + 1, arr[i].toString());\n" +
                    "            }\n" +
                    "            long t1 = new Date().getTime();\n" +
                    "            rs = ps.executeQuery();\n" +
                    "            long t2 = new Date().getTime();\n" +
                    "            sb.append(\";查询耗时:\").append(t2 - t1).append(\"毫秒\");\n" +
                    "            t1 = new Date().getTime();\n" +
                    "            String a = \",\" + Tools.textMid(sql, \"select \", \" from\", 1).replaceAll(\" \", \"\") + \",\";\n" +
                    "            while (rs.next()) {\n" +
                    "                en = new " + 表名_驼峰_首字母大写 + "();\n" +
                    code1 +
                    "                list.add(en);\n" +
                    "            }\n" +
                    "            t2 = new Date().getTime();\n" +
                    "            sb.append(\";组装对象耗时:\").append(t2 - t1).append(\"毫秒\");\n" +
                    "            Log.sql(sb.toString());\n" +
                    "        } catch (Exception e) {\n" +
                    "            Log.e(e, sb.toString());\n" +
                    "        } finally {\n" +
                    "            DataBase.db.close(conn, rs, ps);\n" +
                    "        }\n" +
                    "        return list;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        return " + 表名_驼峰_首字母小写 + "Query(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public ListPage " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr, int page, int limit) {\n" +
                    "        ListPage listPage = new ListPage();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                    "        listPage.limitList(list, page, limit);\n" +
                    "        return listPage;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public ListPage " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit) {\n" +
                    "        limit=limit>maxLimit ? maxLimit : limit;\n" +
                    "        page=page>maxPage ? maxPage : page;\n" +
                    "        ListPage listPage = new ListPage();\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(ase.sql + \" limit \"+(maxPage*limit), ase.objs);\n" +
                    "        listPage.limitList(list, page, limit);\n" +
                    "        return listPage;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int mm) {\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                    "        String key = DataBase.db.getKey(sql, arr);\n" +
                    "        String str = Cache.gzbCache.get(key);\n" +
                    "        if (str == null) {\n" +
                    "            Log.sql(\"Miss:\" + key);\n" +
                    "            list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                    "            Cache.gzbCache.set(key, list.toString(), mm);\n" +
                    "        } else {\n" +
                    "            Log.sql(\"Hit:\" + key);\n" +
                    "            list = " + 表名_驼峰_首字母小写 + "ToList(str);\n" +
                    "        }\n" +
                    "        return list;\n" +
                    "    }\n" +
                    "    @Override\n" +
                    "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int mm) {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        return " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql, ase.objs, mm);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public ListPage " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int page, int limit, int mm) {\n" +
                    "        ListPage listPage = new ListPage();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(sql, arr, mm);\n" +
                    "        listPage.limitList(list, page, limit);\n" +
                    "        return listPage;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public ListPage " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit, int mm) {\n" +
                    "        limit=limit>maxLimit ? maxLimit : limit;\n" +
                    "        page=page > maxPage ? maxPage : page;\n" +
                    "        ListPage listPage = new ListPage();\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                    "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql+ \" limit \"+(maxPage*limit), ase.objs, mm);\n" +
                    "        listPage.limitList(list, page, limit);\n" +
                    "        return listPage;\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Delete(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toDelete();\n" +
                    "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Insert(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        " + 表名_驼峰_首字母小写 + ".set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toInsert();\n" +
                    "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Update(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                    "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        return " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母小写 + ", true);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", boolean auto) {\n" +
                    "        if (auto) {\n" +
                    "            " + 表名_驼峰_首字母小写 + ".set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                    "        }\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toInsert();\n" +
                    "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "DeleteAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                    "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "UpdateAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                    "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                    "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list) {\n" +
                    "        return " + 表名_驼峰_首字母小写 + "Batch(list, true);\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list, boolean autoId) {\n" +
                    "        Connection conn = null;\n" +
                    "        ResultSet rs = null;\n" +
                    "        PreparedStatement ps = null;\n" +
                    "        StringBuilder sb = new StringBuilder();\n" +
                    "        try {\n" +
                    "            long t1 = new Date().getTime();\n" +
                    "            for (int i = 0; i < list.size(); i++) {\n" +
                    "                if (autoId) {\n" +
                    "                    list.get(i).set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                    "                }\n" +
                    "                AutoSqlEntity ase = list.get(i).toInsert();\n" +
                    "                if (i == 0) {\n" +
                    "                    sb.append(\"Batch:\").append(ase.sql).append(\";参数:\");\n" +
                    "                    conn = DataBase.db.getConnection();\n" +
                    "                    conn.setAutoCommit(false);\n" +
                    "                    ps = conn.prepareStatement(ase.sql);\n" +
                    "                }\n" +
                    "                for (int i1 = 0; i1 < ase.objs.length; i1++) {\n" +
                    "                    ps.setObject(i1 + 1, ase.objs[i1]);\n" +
                    "                }\n" +
                    "                sb.append(DataBase.db.getKey(ase.sql, ase.objs));\n" +
                    "                ps.addBatch();\n" +
                    "            }\n" +
                    "            long t2 = new Date().getTime();\n" +
                    "            sb.append(\";组装耗时:\");\n" +
                    "            sb.append(t2 - t1);\n" +
                    "            sb.append(\"毫秒\");\n" +
                    "            t1 = new Date().getTime();\n" +
                    "            int[] res = ps.executeBatch();\n" +
                    "            conn.commit();\n" +
                    "            t2 = new Date().getTime();\n" +
                    "            sb.append(\";执行耗时:\");\n" +
                    "            sb.append(t2 - t1);\n" +
                    "            sb.append(\"毫秒\");\n" +
                    "            Log.sql(sb.toString());\n" +
                    "            return res.length;\n" +
                    "        } catch (SQLIntegrityConstraintViolationException e) {\n" +
                    "            Log.e(e, \"ID冲突\" + sb.toString());\n" +
                    "            return -1;\n" +
                    "        } catch (Exception e) {\n" +
                    "            Log.e(e, sb.toString());\n" +
                    "            return -1;\n" +
                    "        } finally {\n" +
                    "            try {\n" +
                    "                conn.setAutoCommit(true);\n" +
                    "                DataBase.db.close(conn, rs, ps);\n" +
                    "            } catch (SQLException throwables) {\n" +
                    "                throwables.printStackTrace();\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    @Override\n" +
                    "    public int " + 表名_驼峰_首字母小写 + "Batch(String sql, List<Object[]> list) {\n" +
                    "        Connection conn = null;\n" +
                    "        ResultSet rs = null;\n" +
                    "        PreparedStatement ps = null;\n" +
                    "        StringBuilder sb = new StringBuilder();\n" +
                    "        try {\n" +
                    "            long t1 = new Date().getTime();\n" +
                    "            for (int i = 0; i < list.size(); i++) {\n" +
                    "                if (i == 0) {\n" +
                    "                    sb.append(\"Batch:\").append(sql).append(\";参数:\");\n" +
                    "                    conn = DataBase.db.getConnection();\n" +
                    "                    conn.setAutoCommit(false);\n" +
                    "                    ps = conn.prepareStatement(sql);\n" +
                    "                }\n" +
                    "                for (int i1 = 0; i1 < list.get(i).length; i1++) {\n" +
                    "                    ps.setObject(i1 + 1, list.get(i)[i1]);\n" +
                    "                }\n" +
                    "                sb.append(DataBase.db.getKey(sql, list.get(i)));\n" +
                    "                ps.addBatch();\n" +
                    "            }\n" +
                    "            long t2 = new Date().getTime();\n" +
                    "            sb.append(\";组装耗时:\");\n" +
                    "            sb.append(t2 - t1);\n" +
                    "            sb.append(\"毫秒\");\n" +
                    "            t1 = new Date().getTime();\n" +
                    "            int[] res = ps.executeBatch();\n" +
                    "            conn.commit();\n" +
                    "            t2 = new Date().getTime();\n" +
                    "            sb.append(\";执行耗时:\");\n" +
                    "            sb.append(t2 - t1);\n" +
                    "            sb.append(\"毫秒\");\n" +
                    "            Log.sql(sb.toString());\n" +
                    "            return res.length;\n" +
                    "        } catch (SQLIntegrityConstraintViolationException e) {\n" +
                    "            Log.e(e, \"ID冲突\" + sb.toString());\n" +
                    "            return -1;\n" +
                    "        } catch (Exception e) {\n" +
                    "            Log.e(e, sb.toString());\n" +
                    "            return -1;\n" +
                    "        } finally {\n" +
                    "            try {\n" +
                    "                conn.setAutoCommit(true);\n" +
                    "                DataBase.db.close(conn, rs, ps);\n" +
                    "            } catch (SQLException throwables) {\n" +
                    "                throwables.printStackTrace();\n" +
                    "            }\n" +
                    "        }\n" +
                    "    }\n";
        }
        code = "package gzb.db." + dbName + ".dao;\n" +
                "import gzb.db." + dbName + ".DataBase;\n" +
                code2 +
                "import java.sql.*;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.Date;\n" +
                "import java.util.List;\n" +
                "import gzb.tools.ListPage;\n" +
                "import gzb.tools.Tools;\n" +
                "import gzb.tools.cache.Cache;\n" +
                "import gzb.tools.entity.AutoSqlEntity;\n" +
                "import gzb.tools.log.Log;\n" +
                "import gzb.tools.log.LogImpl;\n" +
                "\n" +
                "public class BaseDaoImpl implements BaseDao {\n" +
                "    static Log Log=new LogImpl(BaseDaoImpl.class);\n" +
                code3 +
                code4 +
                "}"
        ;

        return code;
    }


    public static String daoCode(DB_entity entity) {
        String 表名_驼峰_首字母小写 = lowStr_hump(entity.name);
        String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰_首字母小写);

        String id = lowStr_x(lowStr_x(entity.id));

        String code = "package gzb.db." + dbName + ".dao;\n" +
                "import gzb.db." + dbName + ".entity." + 表名_驼峰_首字母大写 + ";\n" +
                "import gzb.tools.ListPage;\n" +
                "import java.util.List;\n" +
                "public interface " + 表名_驼峰_首字母大写 + "Dao {\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(Long " + lowStr_hump(id) + ");\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(String sql, Object[] arr);\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(Long " + id + ",int mm);\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(String sql, Object[] arr,int mm);\n" +
                "    " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ",int mm);\n" +
                "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr);\n" +
                "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    ListPage " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr, int page, int limit);\n" +
                "    ListPage " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit);\n" +
                "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int mm);\n" +
                "    List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int mm);\n" +
                "    ListPage " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int page, int limit, int mm);\n" +
                "    ListPage " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit, int mm);\n" +
                "    int " + 表名_驼峰_首字母小写 + "Delete(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    int " + 表名_驼峰_首字母小写 + "Insert(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    int " + 表名_驼峰_首字母小写 + "Update(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list, boolean autoId);\n" +
                "    int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list);\n" +
                "    int " + 表名_驼峰_首字母小写 + "Batch(String sql, List<Object[]> list);\n" +
                "    int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", boolean auto);\n" +
                "    int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    int " + 表名_驼峰_首字母小写 + "DeleteAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "    int " + 表名_驼峰_首字母小写 + "UpdateAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ");\n" +
                "}";


        return code;
    }

    public static String daoImplCode(DB_entity entity) {
        String 表名_驼峰_首字母小写 = lowStr_hump(entity.name);
        String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰_首字母小写);

        String id = lowStr_x(lowStr_x(entity.id));
        String code0 = "";
        for (int i = 0; i < entity.subName.size(); i++) {
            String 列名大写 = lowStr_hump(entity.subName.get(i), true);
            String 列名小写 = lowStr_hump(entity.subName.get(i));


            code0 += "                if (a.equals(\",*,\") || a.indexOf(\"," + 列名小写 + ",\") > -1 || a.indexOf(\"," + entity.subName.get(i) + ",\") > -1) {\n" +
                    "                    temp = rs.getString(\"" + entity.subName.get(i) + "\");\n" +
                    "                    if (temp != null) {\n" +
                    "                        en.set" + 列名大写 + "(" + entity.subType.get(i) + ".valueOf(temp));\n" +
                    "                    }\n" +
                    "                }\n";
        }
        String code = "package gzb.db." + dbName + ".dao;\n" +
                "import gzb.db." + dbName + ".DataBase;\n" +
                "import gzb.db." + dbName + ".entity." + 表名_驼峰_首字母大写 + ";\n" +
                "import gzb.tools.*;\n" +
                "import java.sql.*;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.Date;\n" +
                "import java.util.List;\n" +
                "import gzb.tools.cache.Cache;\n" +
                "import gzb.tools.entity.AutoSqlEntity;\n" +
                "import gzb.tools.log.Log;\n" +
                "import gzb.tools.log.LogImpl;\n" +
                "\n" +
                "public class " + 表名_驼峰_首字母大写 + "DaoImpl implements " + 表名_驼峰_首字母大写 + "Dao {\n" +
                "    static Log Log=new LogImpl(" + 表名_驼峰_首字母大写 + "DaoImpl.class);\n" +
                "    public final List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "ToList(String json) {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                "        json = json.substring(2, json.length() - 2);\n" +
                "        String[] ss1 = json.replaceAll(\"}, \\\\{\", \"},{\").split(\"},\\\\{\");\n" +
                "        for (int i = 0; i < ss1.length; i++) {\n" +
                "            list.add(new " + 表名_驼峰_首字母大写 + "(\"{\" + ss1[i] + \"}\"));\n" +
                "        }\n" +
                "        return list;\n" +
                "    }\n" +
                "    @Override\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(Long " + id + ") {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(\"select * from \"+DataBase." + 表名_驼峰_首字母小写 + "Name+\" where " + id + "=?\", Tools.toArray(" + id + "));\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(String sql, Object[] arr) {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "Find(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(ase.sql, ase.objs);\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +


                "    @Override\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ",int mm) {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql, ase.objs,mm);\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(Long " + id + ",int mm) {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(\"select * from \"+DataBase." + 表名_驼峰_首字母小写 + "Name+\" where " + id + "=?\", Tools.toArray(" + id + "),mm);\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +
                "    @Override\n" +
                "    public " + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + "FindCache(String sql, Object[] arr,int mm) {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(sql, arr,mm);\n" +
                "        if (list.size() != 1) {\n" +
                "            return null;\n" +
                "        }\n" +
                "        return list.get(0);\n" +
                "    }\n" +


                "    @Override\n" +
                "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr) {\n" +
                "        Connection conn = null;\n" +
                "        ResultSet rs = null;\n" +
                "        PreparedStatement ps = null;\n" +
                "        if (arr == null) {\n" +
                "            arr = Tools.toArray();\n" +
                "        }\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                "        StringBuilder sb = new StringBuilder();\n" +
                "        sb.append(sql).append(\";参数:\");\n" +
                "        try {\n" +
                "            conn = DataBase.db.getConnection();\n" +
                "            ps = conn.prepareStatement(sql);\n" +
                "            " + 表名_驼峰_首字母大写 + " en;\n" +
                "            String temp = \"\";\n" +
                "            for (int i = 0; i < arr.length; i++) {\n" +
                "                sb.append(arr[i]).append(\",\");\n" +
                "                ps.setObject(i + 1, arr[i].toString());\n" +
                "            }\n" +
                "            long t1 = new Date().getTime();\n" +
                "            rs = ps.executeQuery();\n" +
                "            long t2 = new Date().getTime();\n" +
                "            sb.append(\";查询耗时:\").append(t2 - t1).append(\"毫秒\");\n" +
                "            t1 = new Date().getTime();\n" +
                "            String a = \",\" + Tools.textMid(sql, \"select \", \" from\", 1).replaceAll(\" \", \"\") + \",\";\n" +
                "            while (rs.next()) {\n" +
                "                en = new " + 表名_驼峰_首字母大写 + "();\n" +
                code0 +
                "                list.add(en);\n" +
                "            }\n" +
                "            t2 = new Date().getTime();\n" +
                "            sb.append(\";组装对象耗时:\").append(t2 - t1).append(\"毫秒\");\n" +
                "            Log.sql(sb.toString());\n" +
                "        } catch (Exception e) {\n" +
                "            Log.e(e, sb.toString());\n" +
                "        } finally {\n" +
                "            DataBase.db.close(conn, rs, ps);\n" +
                "        }\n" +
                "        return list;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        return " + 表名_驼峰_首字母小写 + "Query(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public ListPage " + 表名_驼峰_首字母小写 + "Query(String sql, Object[] arr, int page, int limit) {\n" +
                "        ListPage listPage = new ListPage();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                "        listPage.limitList(list, page, limit);\n" +
                "        return listPage;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public ListPage " + 表名_驼峰_首字母小写 + "Query(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit) {\n" +
                "        limit=limit>maxLimit ? maxLimit : limit;\n" +
                "        page=page>maxPage ? maxPage : page;\n" +
                "        ListPage listPage = new ListPage();\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "Query(ase.sql + \" limit \"+(maxPage*limit), ase.objs);\n" +
                "        listPage.limitList(list, page, limit);\n" +
                "        return listPage;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int mm) {\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = new ArrayList<>();\n" +
                "        String key = DataBase.db.getKey(sql, arr);\n" +
                "        String str = Cache.gzbCache.get(key);\n" +
                "        if (str == null) {\n" +
                "            Log.sql(\"Miss:\" + key);\n" +
                "            list = " + 表名_驼峰_首字母小写 + "Query(sql, arr);\n" +
                "            Cache.gzbCache.set(key, list.toString(), mm);\n" +
                "        } else {\n" +
                "            Log.sql(\"Hit:\" + key);\n" +
                "            list = " + 表名_驼峰_首字母小写 + "ToList(str);\n" +
                "        }\n" +
                "        return list;\n" +
                "    }\n" +
                "    @Override\n" +
                "    public List<" + 表名_驼峰_首字母大写 + "> " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int mm) {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        return " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql, ase.objs, mm);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public ListPage " + 表名_驼峰_首字母小写 + "QueryCache(String sql, Object[] arr, int page, int limit, int mm) {\n" +
                "        ListPage listPage = new ListPage();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(sql, arr, mm);\n" +
                "        listPage.limitList(list, page, limit);\n" +
                "        return listPage;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public ListPage " + 表名_驼峰_首字母小写 + "QueryCache(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", int page, int limit, int maxPage, int maxLimit, int mm) {\n" +
                "        limit=limit>maxLimit ? maxLimit : limit;\n" +
                "        page=page > maxPage ? maxPage : page;\n" +
                "        ListPage listPage = new ListPage();\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toSelect();\n" +
                "        List<" + 表名_驼峰_首字母大写 + "> list = " + 表名_驼峰_首字母小写 + "QueryCache(ase.sql+ \" limit \"+(maxPage*limit), ase.objs, mm);\n" +
                "        listPage.limitList(list, page, limit);\n" +
                "        return listPage;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Delete(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toDelete();\n" +
                "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Insert(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        " + 表名_驼峰_首字母小写 + ".set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toInsert();\n" +
                "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Update(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                "        return DataBase.db.runSqlUpdateOrSaveOrDelete(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        return " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母小写 + ", true);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "InsertAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ", boolean auto) {\n" +
                "        if (auto) {\n" +
                "            " + 表名_驼峰_首字母小写 + ".set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                "        }\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toInsert();\n" +
                "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "DeleteAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "UpdateAsy(" + 表名_驼峰_首字母大写 + " " + 表名_驼峰_首字母小写 + ") {\n" +
                "        AutoSqlEntity ase = " + 表名_驼峰_首字母小写 + ".toUpdate();\n" +
                "        return DataBase.addAsyInfo(ase.sql, ase.objs);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list) {\n" +
                "        return " + 表名_驼峰_首字母小写 + "Batch(list, true);\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Batch(List<" + 表名_驼峰_首字母大写 + "> list, boolean autoId) {\n" +
                "        Connection conn = null;\n" +
                "        ResultSet rs = null;\n" +
                "        PreparedStatement ps = null;\n" +
                "        StringBuilder sb = new StringBuilder();\n" +
                "        try {\n" +
                "            long t1 = new Date().getTime();\n" +
                "            for (int i = 0; i < list.size(); i++) {\n" +
                "                if (autoId) {\n" +
                "                    list.get(i).set" + lowStr_hump(id, true) + "(DataBase.db.getOnlyIdDistributed());\n" +
                "                }\n" +
                "                AutoSqlEntity ase = list.get(i).toInsert();\n" +
                "                if (i == 0) {\n" +
                "                    sb.append(\"Batch:\").append(ase.sql).append(\";参数:\");\n" +
                "                    conn = DataBase.db.getConnection();\n" +
                "                    conn.setAutoCommit(false);\n" +
                "                    ps = conn.prepareStatement(ase.sql);\n" +
                "                }\n" +
                "                for (int i1 = 0; i1 < ase.objs.length; i1++) {\n" +
                "                    ps.setObject(i1 + 1, ase.objs[i1]);\n" +
                "                }\n" +
                "                sb.append(DataBase.db.getKey(ase.sql, ase.objs));\n" +
                "                ps.addBatch();\n" +
                "            }\n" +
                "            long t2 = new Date().getTime();\n" +
                "            sb.append(\";组装耗时:\");\n" +
                "            sb.append(t2 - t1);\n" +
                "            sb.append(\"毫秒\");\n" +
                "            t1 = new Date().getTime();\n" +
                "            int[] res = ps.executeBatch();\n" +
                "            conn.commit();\n" +
                "            t2 = new Date().getTime();\n" +
                "            sb.append(\";执行耗时:\");\n" +
                "            sb.append(t2 - t1);\n" +
                "            sb.append(\"毫秒\");\n" +
                "            Log.sql(sb.toString());\n" +
                "            return res.length;\n" +
                "        } catch (SQLIntegrityConstraintViolationException e) {\n" +
                "            Log.e(e, \"ID冲突\" + sb.toString());\n" +
                "            return -1;\n" +
                "        } catch (Exception e) {\n" +
                "            Log.e(e, sb.toString());\n" +
                "            return -1;\n" +
                "        } finally {\n" +
                "            try {\n" +
                "                conn.setAutoCommit(true);\n" +
                "                DataBase.db.close(conn, rs, ps);\n" +
                "            } catch (SQLException throwables) {\n" +
                "                throwables.printStackTrace();\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    public int " + 表名_驼峰_首字母小写 + "Batch(String sql, List<Object[]> list) {\n" +
                "        Connection conn = null;\n" +
                "        ResultSet rs = null;\n" +
                "        PreparedStatement ps = null;\n" +
                "        StringBuilder sb = new StringBuilder();\n" +
                "        try {\n" +
                "            long t1 = new Date().getTime();\n" +
                "            for (int i = 0; i < list.size(); i++) {\n" +
                "                if (i == 0) {\n" +
                "                    sb.append(\"Batch:\").append(sql).append(\";参数:\");\n" +
                "                    conn = DataBase.db.getConnection();\n" +
                "                    conn.setAutoCommit(false);\n" +
                "                    ps = conn.prepareStatement(sql);\n" +
                "                }\n" +
                "                for (int i1 = 0; i1 < list.get(i).length; i1++) {\n" +
                "                    ps.setObject(i1 + 1, list.get(i)[i1]);\n" +
                "                }\n" +
                "                sb.append(DataBase.db.getKey(sql, list.get(i)));\n" +
                "                ps.addBatch();\n" +
                "            }\n" +
                "            long t2 = new Date().getTime();\n" +
                "            sb.append(\";组装耗时:\");\n" +
                "            sb.append(t2 - t1);\n" +
                "            sb.append(\"毫秒\");\n" +
                "            t1 = new Date().getTime();\n" +
                "            int[] res = ps.executeBatch();\n" +
                "            conn.commit();\n" +
                "            t2 = new Date().getTime();\n" +
                "            sb.append(\";执行耗时:\");\n" +
                "            sb.append(t2 - t1);\n" +
                "            sb.append(\"毫秒\");\n" +
                "            Log.sql(sb.toString());\n" +
                "            return res.length;\n" +
                "        } catch (SQLIntegrityConstraintViolationException e) {\n" +
                "            Log.e(e, \"ID冲突\" + sb.toString());\n" +
                "            return -1;\n" +
                "        } catch (Exception e) {\n" +
                "            Log.e(e, sb.toString());\n" +
                "            return -1;\n" +
                "        } finally {\n" +
                "            try {\n" +
                "                conn.setAutoCommit(true);\n" +
                "                DataBase.db.close(conn, rs, ps);\n" +
                "            } catch (SQLException throwables) {\n" +
                "                throwables.printStackTrace();\n" +
                "            }\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "}";


        return code;
    }


    public static String entityCodeBaseDao(DB_entity entity) {
        String 表名 = lowStr_d((entity.name));
        String 表名_驼峰 = lowStr_hump(entity.name);
        String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰);
        String 表名_首字母大写 = lowStr_hump(entity.name);
        String 表名_首字母小写 = lowStr_hump(entity.name);
        表名 = 表名_驼峰;

        String id = lowStr_x(lowStr_x(entity.id));
        String code = "";
        String code0 = "";
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        String code5 = "";
        String code6 = "";
        String code7 = "";
        String code8 = "";
        String code9 = "";
        for (int i = 0; i < entity.subName.size(); i++) {
            String 列名_小写 = lowStr_x(entity.subName.get(i));
            String 列名_驼峰_小写 = lowStr_hump(列名_小写);
            String 列名_大写 = lowStr_d(列名_小写);
            String 列名_驼峰_大写 = lowStr_d(列名_驼峰_小写);
            code9 += "    private java.lang.String " + 列名_驼峰_小写 + "Operation=\"=\";\n";
            code4 += "        if (this." + 列名_驼峰_小写 + " !=null){sb.append(\"" + 列名_小写 + " \").append(" + 列名_驼峰_小写 + "Operation).append(\" ? and \");list.add(this." + 列名_驼峰_小写 + ");}\n";
            code8 += "        list.add(this." + 列名_驼峰_小写 + ");\n";
            code5 += "        if (this." + 列名_驼峰_小写 + " !=null){sb.append(\"" + 列名_小写 + "=?, \");list.add(this." + 列名_驼峰_小写 + ");}\n";

            code1 += "        tmp = Tools.textMid(json, \"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\", \"\\\"\", 1);\n" +
                    "        if (tmp != null && tmp.length() > 0){this." + 列名_驼峰_小写 + " = " + entity.subType.get(i) + ".valueOf(tmp);}\n";

            code1 += "        tmp = Tools.textMid(json, \"\\\"" + 列名_驼峰_小写 + "Operation\\\":\\\"\", \"\\\"\", 1);\n" +
                    "        if (tmp != null && tmp.length() > 0){this." + 列名_驼峰_小写 + "Operation = tmp;}\n";


            code3 += "    public " + entity.subType.get(i) + " get" + 列名_驼峰_大写 + "() {\n" +
                    "        return this." + 列名_驼峰_小写 + ";\n" +
                    "    }\n" +
                    "    public " + 表名_驼峰_首字母大写 + " set" + 列名_驼峰_大写 + "(" + entity.subType.get(i) + " " + 列名_驼峰_小写 + ") {\n" +
                    "        this." + 列名_驼峰_小写 + " = " + 列名_驼峰_小写 + ";\n" +
                    "        return this;\n" +
                    "    }\n" +
                    "    public " + 表名_驼峰_首字母大写 + " set" + 列名_驼峰_大写 + "(" + entity.subType.get(i) + " " + 列名_驼峰_小写 + ",java.lang.String " + 列名_驼峰_小写 + "Operation) {\n" +
                    "        this." + 列名_驼峰_小写 + " = " + 列名_驼峰_小写 + ";\n" +
                    "        this." + 列名_驼峰_小写 + "Operation = " + 列名_驼峰_小写 + "Operation;\n" +
                    "        return this;\n" +
                    "    }\n";
            code0 += "    private " + entity.subType.get(i) + " " + 列名_驼峰_小写 + ";\n";
            if (i == entity.subName.size() - 1) {
                code6 += 列名_小写 + "";
                code7 += "?";
                code2 += "        if (" + 列名_驼峰_小写 + " != null){sb.append(\"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\").append(" + 列名_驼峰_小写 + ").append(\"\\\"\");}\n";
            } else {
                code6 += 列名_小写 + ",";
                code7 += "?,";
                code2 += "        if (" + 列名_驼峰_小写 + " != null){sb.append(\"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\").append(" + 列名_驼峰_小写 + ").append(\"\\\",\");}\n";
            }
        }
        code += "package gzb.db." + dbName + ".entity;\n" +
                "import gzb.db." + dbName + ".DataBase;\n" +
                "import gzb.db." + dbName + ".dao.BaseDao;\n" +
                "import gzb.tools.Tools;\n" +
                "import gzb.tools.ListPage;\n" +
                "import gzb.tools.entity.AutoSqlEntity;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "public class " + 表名_驼峰_首字母大写 + " {\n" +
                code0 +
                code9 +
                "    private java.util.List<?>list;\n" +
                "    public List<?> getList() {\n" +
                "        return list;\n" +
                "    }\n" +
                "    public void setList(List<?> list) {\n" +
                "        this.list = list;\n" +
                "    }\n" +
                "    public " + 表名_驼峰_首字母大写 + "() {\n" +
                "    }\n" +
                "    public " + 表名_驼峰_首字母大写 + "(String json) {\n" +
                "        String tmp;\n" +
                code1 +
                "    }\n" +
                "    @Override\n" +
                "    public String toString() {\n" +
                "        return toJson();\n" +
                "    }\n" +
                "    public String toJson() {\n" +
                "        StringBuilder sb = new StringBuilder();\n" +
                "        sb.append(\"{\");\n" +
                code2 +
                "        if (list != null){sb.append(\",\\\"data\\\":\").append(list.toString()).append(\",\");}\n" +
                "        if (sb.substring(sb.length()-1,sb.length()).equals(\",\"))sb.delete(sb.length()-1,sb.length()).equals(\",\");\n" +
                "        sb.append(\"}\");\n" +
                "        return sb.toString();\n" +
                "    }\n" +
                code3 +
                "    public AutoSqlEntity toWhere(String sql){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(sql+\" where \");\n" +
                code4 +
                "        if (sb.substring(sb.length()-5,sb.length()).equals(\" and \"))sb.delete(sb.length()-5,sb.length());\n" +
                "        if (sb.substring(sb.length()-6,sb.length()).equals(\"where \"))sb.delete(sb.length()-6,sb.length());\n" +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +
                "    public AutoSqlEntity toSelect(){\n" +
                "        return toWhere(\"select * from \"+DataBase." + 表名 + "Name);\n" +
                "    }\n" +
                "    public AutoSqlEntity toDelete(){\n" +
                "        return toWhere(\"delete from \"+DataBase." + 表名 + "Name);\n" +
                "    }\n" +
                "    public AutoSqlEntity toUpdate(){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(\"update \"+DataBase." + 表名 + "Name+\" set \");\n" +
                code5 +
                "        if (sb.substring(sb.length()-2,sb.length()).equals(\", \"))sb.delete(sb.length()-2,sb.length()-1);\n" +
                "        if (sb.substring(sb.length()-4,sb.length()).equals(\"set \"))sb.delete(sb.length()-4,sb.length());\n" +
                "        sb.append(\"where " + id + "=?\");list.add(this." + lowStr_hump(id) + ");\n" +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +
                "    public AutoSqlEntity toInsert(){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(\"insert into \"+DataBase." + 表名 + "Name+\"(" + code6 + ") values(" + code7 + ")\");\n" +
                code8 +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +

                "    public " + lowStr_d(表名) + " find(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Find(this);\n" +
                "    }\n" +
                "\n" +
                "    public " + lowStr_d(表名) + " findCache(BaseDao dao, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "FindCache(this, mm);\n" +
                "    }\n" +
                "\n" +
                "    public List<" + lowStr_d(表名) + "> query(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Query(this);\n" +
                "    }\n" +
                "\n" +
                "    public List<" + lowStr_d(表名) + "> queryCache(BaseDao dao, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "QueryCache(this, mm);\n" +
                "    }\n" +
                "\n" +
                "    public ListPage query(BaseDao dao, int page, int limit, int maxPage, int maxLimit) {\n" +
                "        return dao." + lowStr_x(表名) + "Query(this, page, limit, maxPage, maxLimit);\n" +
                "    }\n" +
                "\n" +
                "    public ListPage queryCache(BaseDao dao, int page, int limit, int maxPage, int maxLimit, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "QueryCache(this, page, limit, maxPage, maxLimit, mm);\n" +
                "    }\n" +
                "\n" +
                "    public int save(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Insert(this);\n" +
                "    }\n" +
                "\n" +
                "    public int update(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Update(this);\n" +
                "    }\n" +
                "\n" +
                "    public int delete(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Delete(this);\n" +
                "    }\n" +
                "\n" +
                "    public int saveAsy(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "InsertAsy(this);\n" +
                "    }\n" +
                "\n" +
                "    public int updateAsy(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "UpdateAsy(this);\n" +
                "    }\n" +
                "\n" +
                "    public int deleteAsy(BaseDao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "DeleteAsy(this);\n" +
                "    }\n" +
                "}";
        return code;
    }

    public static String entityCodeDao(DB_entity entity) {
        String 表名 = lowStr_d((entity.name));
        String 表名_驼峰 = lowStr_hump(entity.name);
        String 表名_驼峰_首字母大写 = lowStr_d(表名_驼峰);
        String 表名_首字母大写 = lowStr_hump(entity.name);
        String 表名_首字母小写 = lowStr_hump(entity.name);
        表名 = 表名_驼峰;

        String id = lowStr_x(lowStr_x(entity.id));
        String code = "";
        String code0 = "";
        String code1 = "";
        String code2 = "";
        String code3 = "";
        String code4 = "";
        String code5 = "";
        String code6 = "";
        String code7 = "";
        String code8 = "";
        String code9 = "";
        for (int i = 0; i < entity.subName.size(); i++) {
            String 列名_小写 = lowStr_x(entity.subName.get(i));
            String 列名_驼峰_小写 = lowStr_hump(列名_小写);
            String 列名_大写 = lowStr_d(列名_小写);
            String 列名_驼峰_大写 = lowStr_d(列名_驼峰_小写);
            code9 += "    private java.lang.String " + 列名_驼峰_小写 + "Operation=\"=\";\n";
            code4 += "        if (this." + 列名_驼峰_小写 + " !=null){sb.append(\"" + 列名_小写 + " \").append(" + 列名_驼峰_小写 + "Operation).append(\" ? and \");list.add(this." + 列名_驼峰_小写 + ");}\n";
            code8 += "        list.add(this." + 列名_驼峰_小写 + ");\n";
            code5 += "        if (this." + 列名_驼峰_小写 + " !=null){sb.append(\"" + 列名_小写 + "=?, \");list.add(this." + 列名_驼峰_小写 + ");}\n";

            code1 += "        tmp = Tools.textMid(json, \"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\", \"\\\"\", 1);\n" +
                    "        if (tmp != null && tmp.length() > 0){this." + 列名_驼峰_小写 + " = " + entity.subType.get(i) + ".valueOf(tmp);}\n";

            code1 += "        tmp = Tools.textMid(json, \"\\\"" + 列名_驼峰_小写 + "Operation\\\":\\\"\", \"\\\"\", 1);\n" +
                    "        if (tmp != null && tmp.length() > 0){this." + 列名_驼峰_小写 + "Operation = tmp;}\n";


            code3 += "    public " + entity.subType.get(i) + " get" + 列名_驼峰_大写 + "() {\n" +
                    "        return this." + 列名_驼峰_小写 + ";\n" +
                    "    }\n" +
                    "    public " + 表名_驼峰_首字母大写 + " set" + 列名_驼峰_大写 + "(" + entity.subType.get(i) + " " + 列名_驼峰_小写 + ") {\n" +
                    "        this." + 列名_驼峰_小写 + " = " + 列名_驼峰_小写 + ";\n" +
                    "        return this;\n" +
                    "    }\n" +
                    "    public " + 表名_驼峰_首字母大写 + " set" + 列名_驼峰_大写 + "(" + entity.subType.get(i) + " " + 列名_驼峰_小写 + ",java.lang.String " + 列名_驼峰_小写 + "Operation) {\n" +
                    "        this." + 列名_驼峰_小写 + " = " + 列名_驼峰_小写 + ";\n" +
                    "        this." + 列名_驼峰_小写 + "Operation = " + 列名_驼峰_小写 + "Operation;\n" +
                    "        return this;\n" +
                    "    }\n";
            code0 += "    private " + entity.subType.get(i) + " " + 列名_驼峰_小写 + ";\n";
            if (i == entity.subName.size() - 1) {
                code6 += 列名_小写 + "";
                code7 += "?";
                code2 += "        if (" + 列名_驼峰_小写 + " != null){sb.append(\"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\").append(" + 列名_驼峰_小写 + ").append(\"\\\"\");}\n";
            } else {
                code6 += 列名_小写 + ",";
                code7 += "?,";
                code2 += "        if (" + 列名_驼峰_小写 + " != null){sb.append(\"\\\"" + 列名_驼峰_小写 + "\\\":\\\"\").append(" + 列名_驼峰_小写 + ").append(\"\\\",\");}\n";
            }
        }
        code += "package gzb.db." + dbName + ".entity;\n" +
                "import gzb.db." + dbName + ".DataBase;\n" +
                "import gzb.db." + dbName + ".dao." + lowStr_d(表名) + "Dao;\n" +
                "import gzb.tools.Tools;\n" +
                "import gzb.tools.ListPage;\n" +
                "import gzb.tools.entity.AutoSqlEntity;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n" +
                "public class " + 表名_驼峰_首字母大写 + " {\n" +
                code0 +
                code9 +
                "    private java.util.List<?>list;\n" +
                "    public List<?> getList() {\n" +
                "        return list;\n" +
                "    }\n" +
                "    public void setList(List<?> list) {\n" +
                "        this.list = list;\n" +
                "    }\n" +
                "    public " + 表名_驼峰_首字母大写 + "() {\n" +
                "    }\n" +
                "    public " + 表名_驼峰_首字母大写 + "(String json) {\n" +
                "        String tmp;\n" +
                code1 +
                "    }\n" +
                "    @Override\n" +
                "    public String toString() {\n" +
                "        return toJson();\n" +
                "    }\n" +
                "    public String toJson() {\n" +
                "        StringBuilder sb = new StringBuilder();\n" +
                "        sb.append(\"{\");\n" +
                code2 +
                "        if (list != null){sb.append(\",\\\"data\\\":\").append(list.toString()).append(\",\");}\n" +
                "        if (sb.substring(sb.length()-1,sb.length()).equals(\",\"))sb.delete(sb.length()-1,sb.length()).equals(\",\");\n" +
                "        sb.append(\"}\");\n" +
                "        return sb.toString();\n" +
                "    }\n" +
                code3 +
                "    public AutoSqlEntity toWhere(String sql){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(sql+\" where \");\n" +
                code4 +
                "        if (sb.substring(sb.length()-5,sb.length()).equals(\" and \"))sb.delete(sb.length()-5,sb.length());\n" +
                "        if (sb.substring(sb.length()-6,sb.length()).equals(\"where \"))sb.delete(sb.length()-6,sb.length());\n" +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +
                "    public AutoSqlEntity toSelect(){\n" +
                "        return toWhere(\"select * from \"+DataBase." + 表名 + "Name);\n" +
                "    }\n" +
                "    public AutoSqlEntity toDelete(){\n" +
                "        return toWhere(\"delete from \"+DataBase." + 表名 + "Name);\n" +
                "    }\n" +
                "    public AutoSqlEntity toUpdate(){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(\"update \"+DataBase." + 表名 + "Name+\" set \");\n" +
                code5 +
                "        if (sb.substring(sb.length()-2,sb.length()).equals(\", \"))sb.delete(sb.length()-2,sb.length()-1);\n" +
                "        if (sb.substring(sb.length()-4,sb.length()).equals(\"set \"))sb.delete(sb.length()-4,sb.length());\n" +
                "        sb.append(\"where " + id + "=?\");list.add(this." + lowStr_hump(id) + ");\n" +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +
                "    public AutoSqlEntity toInsert(){\n" +
                "        List<Object> list=new ArrayList<>();\n" +
                "        StringBuilder sb=new StringBuilder();\n" +
                "        sb.append(\"insert into \"+DataBase." + 表名 + "Name+\"(" + code6 + ") values(" + code7 + ")\");\n" +
                code8 +
                "        return new AutoSqlEntity(sb.toString(),list.toArray());\n" +
                "    }\n" +


                "    public " + lowStr_d(表名) + " find(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Find(this);\n" +
                "    }\n" +
                "\n" +
                "    public " + lowStr_d(表名) + " findCache(" + lowStr_d(表名) + "Dao dao, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "FindCache(this, mm);\n" +
                "    }\n" +
                "\n" +
                "    public List<" + lowStr_d(表名) + "> query(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Query(this);\n" +
                "    }\n" +
                "\n" +
                "    public List<" + lowStr_d(表名) + "> queryCache(" + lowStr_d(表名) + "Dao dao, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "QueryCache(this, mm);\n" +
                "    }\n" +
                "\n" +
                "    public ListPage query(" + lowStr_d(表名) + "Dao dao, int page, int limit, int maxPage, int maxLimit) {\n" +
                "        return dao." + lowStr_x(表名) + "Query(this, page, limit, maxPage, maxLimit);\n" +
                "    }\n" +
                "\n" +
                "    public ListPage queryCache(" + lowStr_d(表名) + "Dao dao, int page, int limit, int maxPage, int maxLimit, int mm) {\n" +
                "        return dao." + lowStr_x(表名) + "QueryCache(this, page, limit, maxPage, maxLimit, mm);\n" +
                "    }\n" +
                "\n" +
                "    public int save(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Insert(this);\n" +
                "    }\n" +
                "\n" +
                "    public int update(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Update(this);\n" +
                "    }\n" +
                "\n" +
                "    public int delete(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "Delete(this);\n" +
                "    }\n" +
                "\n" +
                "    public int saveAsy(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "InsertAsy(this);\n" +
                "    }\n" +
                "\n" +
                "    public int updateAsy(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "UpdateAsy(this);\n" +
                "    }\n" +
                "\n" +
                "    public int deleteAsy(" + lowStr_d(表名) + "Dao dao) {\n" +
                "        return dao." + lowStr_x(表名) + "DeleteAsy(this);\n" +
                "    }\n" +
                "}";
        return code;
    }

    public static List<DB_entity> getMapInfo(String mapNames) throws SQLException {
        mapNames = mapNames.toLowerCase();
        List<DB_entity> list = new ArrayList<DB_entity>();
        Connection conn = db.getConnection();
        DatabaseMetaData meta = conn.getMetaData();
        PreparedStatement ps = null;
        ResultSet rs = meta.getTables(null, null, null, Tools.toArrayString("TABLE"));
        while (rs.next()) {
            String tbname = rs.getString("TABLE_NAME").toLowerCase();
            if (mapNames.equals("*") == false && mapNames.indexOf(tbname + "/") < 0) {
                System.out.println("跳过表:" + tbname);
                continue;
            }
            DB_entity mi = new DB_entity();
            mi.name = tbname;
            ps = conn.prepareStatement("select * from " + tbname + " limit 1");
            ResultSetMetaData col = ps.getMetaData();
            ResultSet rst = meta.getPrimaryKeys(null, null, tbname);
            rst.next();
            String idname = rst.getString("COLUMN_NAME");
            mi.id = idname;
            mi.subName = new ArrayList<String>();
            mi.subType = new ArrayList<String>();
            for (int i = 1; i <= col.getColumnCount(); i++) {
                String columnClassName = col.getColumnClassName(i);
                String columnName = col.getColumnName(i);
                if ("[B".equals(columnClassName)) {
                    columnClassName = "java.lang.Byte";
                }
                if ("java.sql.Timestamp".equals(columnClassName)) {
                    columnClassName = "java.lang.String";
                }
                if ("java.time.LocalDateTime".equals(columnClassName)) {
                    columnClassName = "java.lang.String";
                }
                if ("java.lang.Boolean".equals(columnClassName)) {
                    columnClassName = "java.lang.Integer";
                }
                mi.subName.add(columnName);
                mi.subType.add(columnClassName);
                if (mi.id.equals(columnName)) {
                    mi.idType = columnClassName;
                }


            }
            list.add(mi);
        }

        return list;
    }

    //首字母转大写
    public static String lowStr_d(String s) {
        return s.substring(0, 1).toUpperCase() + (s.substring(1, s.length()));
    }

    //首字母转小写
    public static String lowStr_x(String s) {
        return s.substring(0, 1).toLowerCase() + (s.substring(1, s.length()));
    }

    public static String lowStr_hump(String str, boolean InitialCase) {
        if (InitialCase) {
            return lowStr_d(str);
        } else {
            return lowStr_x(str);
        }
    }

    public static String lowStr_hump(String str) {
        return lowStr_hump(str, false);
    }
}

class DB_entity {
    public String name = "";
    public String id = "";
    public String idType = "";
    public List<String> subName = null;
    public List<String> subType = null;

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", idType='" + idType + '\'' +
                ", subName=" + subName +
                ", subType=" + subType +
                '}';
    }
}