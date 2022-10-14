package gzb.db.gzb_system.dao;
import gzb.db.gzb_system.entity.ContentManager;
import gzb.db.gzb_system.entity.FileManager;
import gzb.db.gzb_system.entity.GzbApi;
import gzb.db.gzb_system.entity.GzbCache;
import gzb.db.gzb_system.entity.GzbGroup;
import gzb.db.gzb_system.entity.GzbRight;
import gzb.db.gzb_system.entity.GzbUsers;
import gzb.tools.ListPage;
import java.util.List;
public interface BaseDao {
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param contentManagerId 实体类 主键ID
     * @return ContentManager 实体类对象
     * */
    ContentManager contentManagerFind(java.lang.Long contentManagerId);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return ContentManager 实体类对象
     * */
    ContentManager contentManagerFind(String sql, Object[] arr);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param contentManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @return ContentManager 实体类对象
     * */
    ContentManager contentManagerFind(ContentManager contentManager);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param contentManagerId 实体类 主键ID
     * @param mm 缓存时间（秒）
     * @return ContentManager 实体类对象
     * */
    ContentManager contentManagerFindCache(java.lang.Long contentManagerId,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return ContentManager 实体类对象
     * */
    ContentManager contentManagerFindCache(String sql, Object[] arr,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param contentManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return ContentManager 实体类对象
     * */
    ContentManager contentManagerFindCache(ContentManager contentManager,int mm);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return List<ContentManager> 对象
     * */
    List<ContentManager> contentManagerQuery(String sql, Object[] arr);
    /**
     * 查询数据
     * @param contentManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @return List<ContentManager> 对象
     * */
    List<ContentManager> contentManagerQuery(ContentManager contentManager);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @return ListPage 对象
     * */
    ListPage contentManagerQuery(String sql, Object[] arr, int page, int limit);
    /**
     * 查询数据 返回分页对象
     * @param contentManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @return ListPage 对象
     * */
    ListPage contentManagerQuery(ContentManager contentManager, int page, int limit, int maxPage, int maxLimit);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return List<ContentManager> 对象
     * */
    List<ContentManager> contentManagerQueryCache(String sql, Object[] arr, int mm);
    /**
     * 查询数据
     * @param contentManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return List<ContentManager> 对象
     * */
    List<ContentManager> contentManagerQueryCache(ContentManager contentManager, int mm);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage contentManagerQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    /**
     * 查询数据 返回分页对象
     * @param contentManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage contentManagerQueryCache(ContentManager contentManager, int page, int limit, int maxPage, int maxLimit, int mm);
    /**
     * 删除数据
     * @param contentManager 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int contentManagerDelete(ContentManager contentManager);
    /**
     * 插入数据
     * @param contentManager 实体类对象 框架会根据该实体类对象生成Insert语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int contentManagerInsert(ContentManager contentManager);
    /**
     * 修改数据
     * @param contentManager 实体类对象 框架会根据该实体类对象生成Update语句 （while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int contentManagerUpdate(ContentManager contentManager);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句
     * @param autoId 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int contentManagerInsertBatch(List<ContentManager> list, boolean autoId);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int contentManagerInsertBatch(List<ContentManager> list);
    /**
     * 批量插入数据
     * @param sql sql语句
     * @param list list的每一条数据都与 sql的?对应
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int contentManagerBatch(String sql, List<Object[]> list);
    /**
     * 异步批量插入数据
     * @param contentManager 实体类对象 框架会根据该实体类对象生成Inser语句
     * @param auto 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int contentManagerInsertAsy(ContentManager contentManager, boolean auto);
    /**
     * 异步批量插入数据
     * @param contentManager 实体类对象 框架会根据该实体类对象生成Inser语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int contentManagerInsertAsy(ContentManager contentManager);
    /**
     * 异步批量删除数据
     * @param contentManager 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int contentManagerDeleteAsy(ContentManager contentManager);
    /**
     * 异步批量修改数据
     * @param contentManager 实体类对象 框架会根据该实体类对象生成Update语句（while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int contentManagerUpdateAsy(ContentManager contentManager);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param fileManagerId 实体类 主键ID
     * @return FileManager 实体类对象
     * */
    FileManager fileManagerFind(java.lang.Long fileManagerId);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return FileManager 实体类对象
     * */
    FileManager fileManagerFind(String sql, Object[] arr);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param fileManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @return FileManager 实体类对象
     * */
    FileManager fileManagerFind(FileManager fileManager);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param fileManagerId 实体类 主键ID
     * @param mm 缓存时间（秒）
     * @return FileManager 实体类对象
     * */
    FileManager fileManagerFindCache(java.lang.Long fileManagerId,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return FileManager 实体类对象
     * */
    FileManager fileManagerFindCache(String sql, Object[] arr,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param fileManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return FileManager 实体类对象
     * */
    FileManager fileManagerFindCache(FileManager fileManager,int mm);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return List<FileManager> 对象
     * */
    List<FileManager> fileManagerQuery(String sql, Object[] arr);
    /**
     * 查询数据
     * @param fileManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @return List<FileManager> 对象
     * */
    List<FileManager> fileManagerQuery(FileManager fileManager);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @return ListPage 对象
     * */
    ListPage fileManagerQuery(String sql, Object[] arr, int page, int limit);
    /**
     * 查询数据 返回分页对象
     * @param fileManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @return ListPage 对象
     * */
    ListPage fileManagerQuery(FileManager fileManager, int page, int limit, int maxPage, int maxLimit);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return List<FileManager> 对象
     * */
    List<FileManager> fileManagerQueryCache(String sql, Object[] arr, int mm);
    /**
     * 查询数据
     * @param fileManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return List<FileManager> 对象
     * */
    List<FileManager> fileManagerQueryCache(FileManager fileManager, int mm);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage fileManagerQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    /**
     * 查询数据 返回分页对象
     * @param fileManager 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage fileManagerQueryCache(FileManager fileManager, int page, int limit, int maxPage, int maxLimit, int mm);
    /**
     * 删除数据
     * @param fileManager 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int fileManagerDelete(FileManager fileManager);
    /**
     * 插入数据
     * @param fileManager 实体类对象 框架会根据该实体类对象生成Insert语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int fileManagerInsert(FileManager fileManager);
    /**
     * 修改数据
     * @param fileManager 实体类对象 框架会根据该实体类对象生成Update语句 （while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int fileManagerUpdate(FileManager fileManager);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句
     * @param autoId 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int fileManagerInsertBatch(List<FileManager> list, boolean autoId);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int fileManagerInsertBatch(List<FileManager> list);
    /**
     * 批量插入数据
     * @param sql sql语句
     * @param list list的每一条数据都与 sql的?对应
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int fileManagerBatch(String sql, List<Object[]> list);
    /**
     * 异步批量插入数据
     * @param fileManager 实体类对象 框架会根据该实体类对象生成Inser语句
     * @param auto 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int fileManagerInsertAsy(FileManager fileManager, boolean auto);
    /**
     * 异步批量插入数据
     * @param fileManager 实体类对象 框架会根据该实体类对象生成Inser语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int fileManagerInsertAsy(FileManager fileManager);
    /**
     * 异步批量删除数据
     * @param fileManager 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int fileManagerDeleteAsy(FileManager fileManager);
    /**
     * 异步批量修改数据
     * @param fileManager 实体类对象 框架会根据该实体类对象生成Update语句（while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int fileManagerUpdateAsy(FileManager fileManager);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbApiId 实体类 主键ID
     * @return GzbApi 实体类对象
     * */
    GzbApi gzbApiFind(java.lang.Long gzbApiId);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return GzbApi 实体类对象
     * */
    GzbApi gzbApiFind(String sql, Object[] arr);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成查询语句
     * @return GzbApi 实体类对象
     * */
    GzbApi gzbApiFind(GzbApi gzbApi);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbApiId 实体类 主键ID
     * @param mm 缓存时间（秒）
     * @return GzbApi 实体类对象
     * */
    GzbApi gzbApiFindCache(java.lang.Long gzbApiId,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return GzbApi 实体类对象
     * */
    GzbApi gzbApiFindCache(String sql, Object[] arr,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return GzbApi 实体类对象
     * */
    GzbApi gzbApiFindCache(GzbApi gzbApi,int mm);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return List<GzbApi> 对象
     * */
    List<GzbApi> gzbApiQuery(String sql, Object[] arr);
    /**
     * 查询数据
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成查询语句
     * @return List<GzbApi> 对象
     * */
    List<GzbApi> gzbApiQuery(GzbApi gzbApi);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @return ListPage 对象
     * */
    ListPage gzbApiQuery(String sql, Object[] arr, int page, int limit);
    /**
     * 查询数据 返回分页对象
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @return ListPage 对象
     * */
    ListPage gzbApiQuery(GzbApi gzbApi, int page, int limit, int maxPage, int maxLimit);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return List<GzbApi> 对象
     * */
    List<GzbApi> gzbApiQueryCache(String sql, Object[] arr, int mm);
    /**
     * 查询数据
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return List<GzbApi> 对象
     * */
    List<GzbApi> gzbApiQueryCache(GzbApi gzbApi, int mm);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage gzbApiQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    /**
     * 查询数据 返回分页对象
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage gzbApiQueryCache(GzbApi gzbApi, int page, int limit, int maxPage, int maxLimit, int mm);
    /**
     * 删除数据
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbApiDelete(GzbApi gzbApi);
    /**
     * 插入数据
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成Insert语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbApiInsert(GzbApi gzbApi);
    /**
     * 修改数据
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成Update语句 （while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbApiUpdate(GzbApi gzbApi);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句
     * @param autoId 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbApiInsertBatch(List<GzbApi> list, boolean autoId);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbApiInsertBatch(List<GzbApi> list);
    /**
     * 批量插入数据
     * @param sql sql语句
     * @param list list的每一条数据都与 sql的?对应
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbApiBatch(String sql, List<Object[]> list);
    /**
     * 异步批量插入数据
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成Inser语句
     * @param auto 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbApiInsertAsy(GzbApi gzbApi, boolean auto);
    /**
     * 异步批量插入数据
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成Inser语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbApiInsertAsy(GzbApi gzbApi);
    /**
     * 异步批量删除数据
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbApiDeleteAsy(GzbApi gzbApi);
    /**
     * 异步批量修改数据
     * @param gzbApi 实体类对象 框架会根据该实体类对象生成Update语句（while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbApiUpdateAsy(GzbApi gzbApi);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbCacheId 实体类 主键ID
     * @return GzbCache 实体类对象
     * */
    GzbCache gzbCacheFind(java.lang.Long gzbCacheId);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return GzbCache 实体类对象
     * */
    GzbCache gzbCacheFind(String sql, Object[] arr);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成查询语句
     * @return GzbCache 实体类对象
     * */
    GzbCache gzbCacheFind(GzbCache gzbCache);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbCacheId 实体类 主键ID
     * @param mm 缓存时间（秒）
     * @return GzbCache 实体类对象
     * */
    GzbCache gzbCacheFindCache(java.lang.Long gzbCacheId,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return GzbCache 实体类对象
     * */
    GzbCache gzbCacheFindCache(String sql, Object[] arr,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return GzbCache 实体类对象
     * */
    GzbCache gzbCacheFindCache(GzbCache gzbCache,int mm);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return List<GzbCache> 对象
     * */
    List<GzbCache> gzbCacheQuery(String sql, Object[] arr);
    /**
     * 查询数据
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成查询语句
     * @return List<GzbCache> 对象
     * */
    List<GzbCache> gzbCacheQuery(GzbCache gzbCache);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @return ListPage 对象
     * */
    ListPage gzbCacheQuery(String sql, Object[] arr, int page, int limit);
    /**
     * 查询数据 返回分页对象
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @return ListPage 对象
     * */
    ListPage gzbCacheQuery(GzbCache gzbCache, int page, int limit, int maxPage, int maxLimit);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return List<GzbCache> 对象
     * */
    List<GzbCache> gzbCacheQueryCache(String sql, Object[] arr, int mm);
    /**
     * 查询数据
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return List<GzbCache> 对象
     * */
    List<GzbCache> gzbCacheQueryCache(GzbCache gzbCache, int mm);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage gzbCacheQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    /**
     * 查询数据 返回分页对象
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage gzbCacheQueryCache(GzbCache gzbCache, int page, int limit, int maxPage, int maxLimit, int mm);
    /**
     * 删除数据
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbCacheDelete(GzbCache gzbCache);
    /**
     * 插入数据
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成Insert语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbCacheInsert(GzbCache gzbCache);
    /**
     * 修改数据
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成Update语句 （while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbCacheUpdate(GzbCache gzbCache);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句
     * @param autoId 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbCacheInsertBatch(List<GzbCache> list, boolean autoId);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbCacheInsertBatch(List<GzbCache> list);
    /**
     * 批量插入数据
     * @param sql sql语句
     * @param list list的每一条数据都与 sql的?对应
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbCacheBatch(String sql, List<Object[]> list);
    /**
     * 异步批量插入数据
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成Inser语句
     * @param auto 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbCacheInsertAsy(GzbCache gzbCache, boolean auto);
    /**
     * 异步批量插入数据
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成Inser语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbCacheInsertAsy(GzbCache gzbCache);
    /**
     * 异步批量删除数据
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbCacheDeleteAsy(GzbCache gzbCache);
    /**
     * 异步批量修改数据
     * @param gzbCache 实体类对象 框架会根据该实体类对象生成Update语句（while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbCacheUpdateAsy(GzbCache gzbCache);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbGroupId 实体类 主键ID
     * @return GzbGroup 实体类对象
     * */
    GzbGroup gzbGroupFind(java.lang.Long gzbGroupId);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return GzbGroup 实体类对象
     * */
    GzbGroup gzbGroupFind(String sql, Object[] arr);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成查询语句
     * @return GzbGroup 实体类对象
     * */
    GzbGroup gzbGroupFind(GzbGroup gzbGroup);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbGroupId 实体类 主键ID
     * @param mm 缓存时间（秒）
     * @return GzbGroup 实体类对象
     * */
    GzbGroup gzbGroupFindCache(java.lang.Long gzbGroupId,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return GzbGroup 实体类对象
     * */
    GzbGroup gzbGroupFindCache(String sql, Object[] arr,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return GzbGroup 实体类对象
     * */
    GzbGroup gzbGroupFindCache(GzbGroup gzbGroup,int mm);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return List<GzbGroup> 对象
     * */
    List<GzbGroup> gzbGroupQuery(String sql, Object[] arr);
    /**
     * 查询数据
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成查询语句
     * @return List<GzbGroup> 对象
     * */
    List<GzbGroup> gzbGroupQuery(GzbGroup gzbGroup);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @return ListPage 对象
     * */
    ListPage gzbGroupQuery(String sql, Object[] arr, int page, int limit);
    /**
     * 查询数据 返回分页对象
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @return ListPage 对象
     * */
    ListPage gzbGroupQuery(GzbGroup gzbGroup, int page, int limit, int maxPage, int maxLimit);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return List<GzbGroup> 对象
     * */
    List<GzbGroup> gzbGroupQueryCache(String sql, Object[] arr, int mm);
    /**
     * 查询数据
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return List<GzbGroup> 对象
     * */
    List<GzbGroup> gzbGroupQueryCache(GzbGroup gzbGroup, int mm);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage gzbGroupQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    /**
     * 查询数据 返回分页对象
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage gzbGroupQueryCache(GzbGroup gzbGroup, int page, int limit, int maxPage, int maxLimit, int mm);
    /**
     * 删除数据
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbGroupDelete(GzbGroup gzbGroup);
    /**
     * 插入数据
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成Insert语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbGroupInsert(GzbGroup gzbGroup);
    /**
     * 修改数据
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成Update语句 （while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbGroupUpdate(GzbGroup gzbGroup);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句
     * @param autoId 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbGroupInsertBatch(List<GzbGroup> list, boolean autoId);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbGroupInsertBatch(List<GzbGroup> list);
    /**
     * 批量插入数据
     * @param sql sql语句
     * @param list list的每一条数据都与 sql的?对应
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbGroupBatch(String sql, List<Object[]> list);
    /**
     * 异步批量插入数据
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成Inser语句
     * @param auto 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbGroupInsertAsy(GzbGroup gzbGroup, boolean auto);
    /**
     * 异步批量插入数据
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成Inser语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbGroupInsertAsy(GzbGroup gzbGroup);
    /**
     * 异步批量删除数据
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbGroupDeleteAsy(GzbGroup gzbGroup);
    /**
     * 异步批量修改数据
     * @param gzbGroup 实体类对象 框架会根据该实体类对象生成Update语句（while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbGroupUpdateAsy(GzbGroup gzbGroup);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbRightId 实体类 主键ID
     * @return GzbRight 实体类对象
     * */
    GzbRight gzbRightFind(java.lang.Long gzbRightId);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return GzbRight 实体类对象
     * */
    GzbRight gzbRightFind(String sql, Object[] arr);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成查询语句
     * @return GzbRight 实体类对象
     * */
    GzbRight gzbRightFind(GzbRight gzbRight);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbRightId 实体类 主键ID
     * @param mm 缓存时间（秒）
     * @return GzbRight 实体类对象
     * */
    GzbRight gzbRightFindCache(java.lang.Long gzbRightId,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return GzbRight 实体类对象
     * */
    GzbRight gzbRightFindCache(String sql, Object[] arr,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return GzbRight 实体类对象
     * */
    GzbRight gzbRightFindCache(GzbRight gzbRight,int mm);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return List<GzbRight> 对象
     * */
    List<GzbRight> gzbRightQuery(String sql, Object[] arr);
    /**
     * 查询数据
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成查询语句
     * @return List<GzbRight> 对象
     * */
    List<GzbRight> gzbRightQuery(GzbRight gzbRight);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @return ListPage 对象
     * */
    ListPage gzbRightQuery(String sql, Object[] arr, int page, int limit);
    /**
     * 查询数据 返回分页对象
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @return ListPage 对象
     * */
    ListPage gzbRightQuery(GzbRight gzbRight, int page, int limit, int maxPage, int maxLimit);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return List<GzbRight> 对象
     * */
    List<GzbRight> gzbRightQueryCache(String sql, Object[] arr, int mm);
    /**
     * 查询数据
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return List<GzbRight> 对象
     * */
    List<GzbRight> gzbRightQueryCache(GzbRight gzbRight, int mm);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage gzbRightQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    /**
     * 查询数据 返回分页对象
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage gzbRightQueryCache(GzbRight gzbRight, int page, int limit, int maxPage, int maxLimit, int mm);
    /**
     * 删除数据
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbRightDelete(GzbRight gzbRight);
    /**
     * 插入数据
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成Insert语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbRightInsert(GzbRight gzbRight);
    /**
     * 修改数据
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成Update语句 （while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbRightUpdate(GzbRight gzbRight);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句
     * @param autoId 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbRightInsertBatch(List<GzbRight> list, boolean autoId);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbRightInsertBatch(List<GzbRight> list);
    /**
     * 批量插入数据
     * @param sql sql语句
     * @param list list的每一条数据都与 sql的?对应
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbRightBatch(String sql, List<Object[]> list);
    /**
     * 异步批量插入数据
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成Inser语句
     * @param auto 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbRightInsertAsy(GzbRight gzbRight, boolean auto);
    /**
     * 异步批量插入数据
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成Inser语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbRightInsertAsy(GzbRight gzbRight);
    /**
     * 异步批量删除数据
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbRightDeleteAsy(GzbRight gzbRight);
    /**
     * 异步批量修改数据
     * @param gzbRight 实体类对象 框架会根据该实体类对象生成Update语句（while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbRightUpdateAsy(GzbRight gzbRight);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbUsersId 实体类 主键ID
     * @return GzbUsers 实体类对象
     * */
    GzbUsers gzbUsersFind(java.lang.Long gzbUsersId);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return GzbUsers 实体类对象
     * */
    GzbUsers gzbUsersFind(String sql, Object[] arr);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成查询语句
     * @return GzbUsers 实体类对象
     * */
    GzbUsers gzbUsersFind(GzbUsers gzbUsers);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbUsersId 实体类 主键ID
     * @param mm 缓存时间（秒）
     * @return GzbUsers 实体类对象
     * */
    GzbUsers gzbUsersFindCache(java.lang.Long gzbUsersId,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return GzbUsers 实体类对象
     * */
    GzbUsers gzbUsersFindCache(String sql, Object[] arr,int mm);
    /**
     * 查询单条数据，如果查询结果不是一条那么会返回 null
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return GzbUsers 实体类对象
     * */
    GzbUsers gzbUsersFindCache(GzbUsers gzbUsers,int mm);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @return List<GzbUsers> 对象
     * */
    List<GzbUsers> gzbUsersQuery(String sql, Object[] arr);
    /**
     * 查询数据
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成查询语句
     * @return List<GzbUsers> 对象
     * */
    List<GzbUsers> gzbUsersQuery(GzbUsers gzbUsers);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @return ListPage 对象
     * */
    ListPage gzbUsersQuery(String sql, Object[] arr, int page, int limit);
    /**
     * 查询数据 返回分页对象
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @return ListPage 对象
     * */
    ListPage gzbUsersQuery(GzbUsers gzbUsers, int page, int limit, int maxPage, int maxLimit);
    /**
     * 查询数据
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param mm 缓存时间（秒）
     * @return List<GzbUsers> 对象
     * */
    List<GzbUsers> gzbUsersQueryCache(String sql, Object[] arr, int mm);
    /**
     * 查询数据
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成查询语句
     * @param mm 缓存时间（秒）
     * @return List<GzbUsers> 对象
     * */
    List<GzbUsers> gzbUsersQueryCache(GzbUsers gzbUsers, int mm);
    /**
     * 查询数据 返回分页对象
     * @param sql sql语句
     * @param arr 对应sql中的 ?
     * @param page 页码
     * @param limit 每页长度
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage gzbUsersQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    /**
     * 查询数据 返回分页对象
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成查询语句
     * @param page 页码
     * @param limit 每页长度
     * @param maxPage 最大页码，无法超出
     * @param maxLimit 最大每页长度，无法超出
     * @param mm 缓存时间（秒）
     * @return ListPage 对象
     * */
    ListPage gzbUsersQueryCache(GzbUsers gzbUsers, int page, int limit, int maxPage, int maxLimit, int mm);
    /**
     * 删除数据
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbUsersDelete(GzbUsers gzbUsers);
    /**
     * 插入数据
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成Insert语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbUsersInsert(GzbUsers gzbUsers);
    /**
     * 修改数据
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成Update语句 （while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbUsersUpdate(GzbUsers gzbUsers);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句
     * @param autoId 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbUsersInsertBatch(List<GzbUsers> list, boolean autoId);
    /**
     * 批量插入数据
     * @param list 实体类List 框架会根据该List对象生成Insert语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbUsersInsertBatch(List<GzbUsers> list);
    /**
     * 批量插入数据
     * @param sql sql语句
     * @param list list的每一条数据都与 sql的?对应
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbUsersBatch(String sql, List<Object[]> list);
    /**
     * 异步批量插入数据
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成Inser语句
     * @param auto 为true时 自动给每个对象生成一个 主键id 否则 不做操作 默认为false
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbUsersInsertAsy(GzbUsers gzbUsers, boolean auto);
    /**
     * 异步批量插入数据
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成Inser语句 且 自动给每个对象生成一个 主键id （自己给的id会被覆盖掉）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbUsersInsertAsy(GzbUsers gzbUsers);
    /**
     * 异步批量删除数据
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成Delete语句
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbUsersDeleteAsy(GzbUsers gzbUsers);
    /**
     * 异步批量修改数据
     * @param gzbUsers 实体类对象 框架会根据该实体类对象生成Update语句（while条件只能是 主键id）
     * @return int 大于0为执行成功，小于0为出现异常
     * */
    int gzbUsersUpdateAsy(GzbUsers gzbUsers);

}