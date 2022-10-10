package gzb.db.gzb_system.dao;
import gzb.db.gzb_system.entity.ContentManager;
import gzb.db.gzb_system.entity.FileManager;
import gzb.db.gzb_system.entity.GzbApi;
import gzb.db.gzb_system.entity.GzbCache;
import gzb.db.gzb_system.entity.GzbGroup;
import gzb.db.gzb_system.entity.GzbRight;
import gzb.db.gzb_system.entity.GzbUsers;
import gzb.db.gzb_system.entity.Gzbtest;
import gzb.db.gzb_system.entity.Test;
import gzb.db.gzb_system.entity.Users;
import gzb.tools.ListPage;
import java.util.List;
public interface BaseDao {
    ContentManager contentManagerFind(Long contentManagerId);
    ContentManager contentManagerFind(String sql, Object[] arr);
    ContentManager contentManagerFind(ContentManager contentManager);
    ContentManager contentManagerFindCache(Long content_manager_id,int mm);
    ContentManager contentManagerFindCache(String sql, Object[] arr,int mm);
    ContentManager contentManagerFindCache(ContentManager contentManager,int mm);
    List<ContentManager> contentManagerQuery(String sql, Object[] arr);
    List<ContentManager> contentManagerQuery(ContentManager contentManager);
    ListPage contentManagerQuery(String sql, Object[] arr, int page, int limit);
    ListPage contentManagerQuery(ContentManager contentManager, int page, int limit, int maxPage, int maxLimit);
    List<ContentManager> contentManagerQueryCache(String sql, Object[] arr, int mm);
    List<ContentManager> contentManagerQueryCache(ContentManager contentManager, int mm);
    ListPage contentManagerQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    ListPage contentManagerQueryCache(ContentManager contentManager, int page, int limit, int maxPage, int maxLimit, int mm);
    int contentManagerDelete(ContentManager contentManager);
    int contentManagerInsert(ContentManager contentManager);
    int contentManagerUpdate(ContentManager contentManager);
    int contentManagerBatch(List<ContentManager> list, boolean autoId);
    int contentManagerBatch(List<ContentManager> list);
    int contentManagerBatch(String sql, List<Object[]> list);
    int contentManagerInsertAsy(ContentManager contentManager, boolean auto);
    int contentManagerInsertAsy(ContentManager contentManager);
    int contentManagerDeleteAsy(ContentManager contentManager);
    int contentManagerUpdateAsy(ContentManager contentManager);
    FileManager fileManagerFind(Long fileManagerId);
    FileManager fileManagerFind(String sql, Object[] arr);
    FileManager fileManagerFind(FileManager fileManager);
    FileManager fileManagerFindCache(Long file_manager_id,int mm);
    FileManager fileManagerFindCache(String sql, Object[] arr,int mm);
    FileManager fileManagerFindCache(FileManager fileManager,int mm);
    List<FileManager> fileManagerQuery(String sql, Object[] arr);
    List<FileManager> fileManagerQuery(FileManager fileManager);
    ListPage fileManagerQuery(String sql, Object[] arr, int page, int limit);
    ListPage fileManagerQuery(FileManager fileManager, int page, int limit, int maxPage, int maxLimit);
    List<FileManager> fileManagerQueryCache(String sql, Object[] arr, int mm);
    List<FileManager> fileManagerQueryCache(FileManager fileManager, int mm);
    ListPage fileManagerQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    ListPage fileManagerQueryCache(FileManager fileManager, int page, int limit, int maxPage, int maxLimit, int mm);
    int fileManagerDelete(FileManager fileManager);
    int fileManagerInsert(FileManager fileManager);
    int fileManagerUpdate(FileManager fileManager);
    int fileManagerBatch(List<FileManager> list, boolean autoId);
    int fileManagerBatch(List<FileManager> list);
    int fileManagerBatch(String sql, List<Object[]> list);
    int fileManagerInsertAsy(FileManager fileManager, boolean auto);
    int fileManagerInsertAsy(FileManager fileManager);
    int fileManagerDeleteAsy(FileManager fileManager);
    int fileManagerUpdateAsy(FileManager fileManager);
    GzbApi gzbApiFind(Long gzbApiId);
    GzbApi gzbApiFind(String sql, Object[] arr);
    GzbApi gzbApiFind(GzbApi gzbApi);
    GzbApi gzbApiFindCache(Long gzb_api_id,int mm);
    GzbApi gzbApiFindCache(String sql, Object[] arr,int mm);
    GzbApi gzbApiFindCache(GzbApi gzbApi,int mm);
    List<GzbApi> gzbApiQuery(String sql, Object[] arr);
    List<GzbApi> gzbApiQuery(GzbApi gzbApi);
    ListPage gzbApiQuery(String sql, Object[] arr, int page, int limit);
    ListPage gzbApiQuery(GzbApi gzbApi, int page, int limit, int maxPage, int maxLimit);
    List<GzbApi> gzbApiQueryCache(String sql, Object[] arr, int mm);
    List<GzbApi> gzbApiQueryCache(GzbApi gzbApi, int mm);
    ListPage gzbApiQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    ListPage gzbApiQueryCache(GzbApi gzbApi, int page, int limit, int maxPage, int maxLimit, int mm);
    int gzbApiDelete(GzbApi gzbApi);
    int gzbApiInsert(GzbApi gzbApi);
    int gzbApiUpdate(GzbApi gzbApi);
    int gzbApiBatch(List<GzbApi> list, boolean autoId);
    int gzbApiBatch(List<GzbApi> list);
    int gzbApiBatch(String sql, List<Object[]> list);
    int gzbApiInsertAsy(GzbApi gzbApi, boolean auto);
    int gzbApiInsertAsy(GzbApi gzbApi);
    int gzbApiDeleteAsy(GzbApi gzbApi);
    int gzbApiUpdateAsy(GzbApi gzbApi);
    GzbCache gzbCacheFind(Long gzbCacheId);
    GzbCache gzbCacheFind(String sql, Object[] arr);
    GzbCache gzbCacheFind(GzbCache gzbCache);
    GzbCache gzbCacheFindCache(Long gzb_cache_id,int mm);
    GzbCache gzbCacheFindCache(String sql, Object[] arr,int mm);
    GzbCache gzbCacheFindCache(GzbCache gzbCache,int mm);
    List<GzbCache> gzbCacheQuery(String sql, Object[] arr);
    List<GzbCache> gzbCacheQuery(GzbCache gzbCache);
    ListPage gzbCacheQuery(String sql, Object[] arr, int page, int limit);
    ListPage gzbCacheQuery(GzbCache gzbCache, int page, int limit, int maxPage, int maxLimit);
    List<GzbCache> gzbCacheQueryCache(String sql, Object[] arr, int mm);
    List<GzbCache> gzbCacheQueryCache(GzbCache gzbCache, int mm);
    ListPage gzbCacheQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    ListPage gzbCacheQueryCache(GzbCache gzbCache, int page, int limit, int maxPage, int maxLimit, int mm);
    int gzbCacheDelete(GzbCache gzbCache);
    int gzbCacheInsert(GzbCache gzbCache);
    int gzbCacheUpdate(GzbCache gzbCache);
    int gzbCacheBatch(List<GzbCache> list, boolean autoId);
    int gzbCacheBatch(List<GzbCache> list);
    int gzbCacheBatch(String sql, List<Object[]> list);
    int gzbCacheInsertAsy(GzbCache gzbCache, boolean auto);
    int gzbCacheInsertAsy(GzbCache gzbCache);
    int gzbCacheDeleteAsy(GzbCache gzbCache);
    int gzbCacheUpdateAsy(GzbCache gzbCache);
    GzbGroup gzbGroupFind(Long gzbGroupId);
    GzbGroup gzbGroupFind(String sql, Object[] arr);
    GzbGroup gzbGroupFind(GzbGroup gzbGroup);
    GzbGroup gzbGroupFindCache(Long gzb_group_id,int mm);
    GzbGroup gzbGroupFindCache(String sql, Object[] arr,int mm);
    GzbGroup gzbGroupFindCache(GzbGroup gzbGroup,int mm);
    List<GzbGroup> gzbGroupQuery(String sql, Object[] arr);
    List<GzbGroup> gzbGroupQuery(GzbGroup gzbGroup);
    ListPage gzbGroupQuery(String sql, Object[] arr, int page, int limit);
    ListPage gzbGroupQuery(GzbGroup gzbGroup, int page, int limit, int maxPage, int maxLimit);
    List<GzbGroup> gzbGroupQueryCache(String sql, Object[] arr, int mm);
    List<GzbGroup> gzbGroupQueryCache(GzbGroup gzbGroup, int mm);
    ListPage gzbGroupQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    ListPage gzbGroupQueryCache(GzbGroup gzbGroup, int page, int limit, int maxPage, int maxLimit, int mm);
    int gzbGroupDelete(GzbGroup gzbGroup);
    int gzbGroupInsert(GzbGroup gzbGroup);
    int gzbGroupUpdate(GzbGroup gzbGroup);
    int gzbGroupBatch(List<GzbGroup> list, boolean autoId);
    int gzbGroupBatch(List<GzbGroup> list);
    int gzbGroupBatch(String sql, List<Object[]> list);
    int gzbGroupInsertAsy(GzbGroup gzbGroup, boolean auto);
    int gzbGroupInsertAsy(GzbGroup gzbGroup);
    int gzbGroupDeleteAsy(GzbGroup gzbGroup);
    int gzbGroupUpdateAsy(GzbGroup gzbGroup);
    GzbRight gzbRightFind(Long gzbRightId);
    GzbRight gzbRightFind(String sql, Object[] arr);
    GzbRight gzbRightFind(GzbRight gzbRight);
    GzbRight gzbRightFindCache(Long gzb_right_id,int mm);
    GzbRight gzbRightFindCache(String sql, Object[] arr,int mm);
    GzbRight gzbRightFindCache(GzbRight gzbRight,int mm);
    List<GzbRight> gzbRightQuery(String sql, Object[] arr);
    List<GzbRight> gzbRightQuery(GzbRight gzbRight);
    ListPage gzbRightQuery(String sql, Object[] arr, int page, int limit);
    ListPage gzbRightQuery(GzbRight gzbRight, int page, int limit, int maxPage, int maxLimit);
    List<GzbRight> gzbRightQueryCache(String sql, Object[] arr, int mm);
    List<GzbRight> gzbRightQueryCache(GzbRight gzbRight, int mm);
    ListPage gzbRightQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    ListPage gzbRightQueryCache(GzbRight gzbRight, int page, int limit, int maxPage, int maxLimit, int mm);
    int gzbRightDelete(GzbRight gzbRight);
    int gzbRightInsert(GzbRight gzbRight);
    int gzbRightUpdate(GzbRight gzbRight);
    int gzbRightBatch(List<GzbRight> list, boolean autoId);
    int gzbRightBatch(List<GzbRight> list);
    int gzbRightBatch(String sql, List<Object[]> list);
    int gzbRightInsertAsy(GzbRight gzbRight, boolean auto);
    int gzbRightInsertAsy(GzbRight gzbRight);
    int gzbRightDeleteAsy(GzbRight gzbRight);
    int gzbRightUpdateAsy(GzbRight gzbRight);
    GzbUsers gzbUsersFind(Long gzbUsersId);
    GzbUsers gzbUsersFind(String sql, Object[] arr);
    GzbUsers gzbUsersFind(GzbUsers gzbUsers);
    GzbUsers gzbUsersFindCache(Long gzb_users_id,int mm);
    GzbUsers gzbUsersFindCache(String sql, Object[] arr,int mm);
    GzbUsers gzbUsersFindCache(GzbUsers gzbUsers,int mm);
    List<GzbUsers> gzbUsersQuery(String sql, Object[] arr);
    List<GzbUsers> gzbUsersQuery(GzbUsers gzbUsers);
    ListPage gzbUsersQuery(String sql, Object[] arr, int page, int limit);
    ListPage gzbUsersQuery(GzbUsers gzbUsers, int page, int limit, int maxPage, int maxLimit);
    List<GzbUsers> gzbUsersQueryCache(String sql, Object[] arr, int mm);
    List<GzbUsers> gzbUsersQueryCache(GzbUsers gzbUsers, int mm);
    ListPage gzbUsersQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    ListPage gzbUsersQueryCache(GzbUsers gzbUsers, int page, int limit, int maxPage, int maxLimit, int mm);
    int gzbUsersDelete(GzbUsers gzbUsers);
    int gzbUsersInsert(GzbUsers gzbUsers);
    int gzbUsersUpdate(GzbUsers gzbUsers);
    int gzbUsersBatch(List<GzbUsers> list, boolean autoId);
    int gzbUsersBatch(List<GzbUsers> list);
    int gzbUsersBatch(String sql, List<Object[]> list);
    int gzbUsersInsertAsy(GzbUsers gzbUsers, boolean auto);
    int gzbUsersInsertAsy(GzbUsers gzbUsers);
    int gzbUsersDeleteAsy(GzbUsers gzbUsers);
    int gzbUsersUpdateAsy(GzbUsers gzbUsers);
    Gzbtest gzbtestFind(Long gzbTestId);
    Gzbtest gzbtestFind(String sql, Object[] arr);
    Gzbtest gzbtestFind(Gzbtest gzbtest);
    Gzbtest gzbtestFindCache(Long gzbTestId,int mm);
    Gzbtest gzbtestFindCache(String sql, Object[] arr,int mm);
    Gzbtest gzbtestFindCache(Gzbtest gzbtest,int mm);
    List<Gzbtest> gzbtestQuery(String sql, Object[] arr);
    List<Gzbtest> gzbtestQuery(Gzbtest gzbtest);
    ListPage gzbtestQuery(String sql, Object[] arr, int page, int limit);
    ListPage gzbtestQuery(Gzbtest gzbtest, int page, int limit, int maxPage, int maxLimit);
    List<Gzbtest> gzbtestQueryCache(String sql, Object[] arr, int mm);
    List<Gzbtest> gzbtestQueryCache(Gzbtest gzbtest, int mm);
    ListPage gzbtestQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    ListPage gzbtestQueryCache(Gzbtest gzbtest, int page, int limit, int maxPage, int maxLimit, int mm);
    int gzbtestDelete(Gzbtest gzbtest);
    int gzbtestInsert(Gzbtest gzbtest);
    int gzbtestUpdate(Gzbtest gzbtest);
    int gzbtestBatch(List<Gzbtest> list, boolean autoId);
    int gzbtestBatch(List<Gzbtest> list);
    int gzbtestBatch(String sql, List<Object[]> list);
    int gzbtestInsertAsy(Gzbtest gzbtest, boolean auto);
    int gzbtestInsertAsy(Gzbtest gzbtest);
    int gzbtestDeleteAsy(Gzbtest gzbtest);
    int gzbtestUpdateAsy(Gzbtest gzbtest);
    Test testFind(Long testId);
    Test testFind(String sql, Object[] arr);
    Test testFind(Test test);
    Test testFindCache(Long test_id,int mm);
    Test testFindCache(String sql, Object[] arr,int mm);
    Test testFindCache(Test test,int mm);
    List<Test> testQuery(String sql, Object[] arr);
    List<Test> testQuery(Test test);
    ListPage testQuery(String sql, Object[] arr, int page, int limit);
    ListPage testQuery(Test test, int page, int limit, int maxPage, int maxLimit);
    List<Test> testQueryCache(String sql, Object[] arr, int mm);
    List<Test> testQueryCache(Test test, int mm);
    ListPage testQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    ListPage testQueryCache(Test test, int page, int limit, int maxPage, int maxLimit, int mm);
    int testDelete(Test test);
    int testInsert(Test test);
    int testUpdate(Test test);
    int testBatch(List<Test> list, boolean autoId);
    int testBatch(List<Test> list);
    int testBatch(String sql, List<Object[]> list);
    int testInsertAsy(Test test, boolean auto);
    int testInsertAsy(Test test);
    int testDeleteAsy(Test test);
    int testUpdateAsy(Test test);
    Users usersFind(Long usersId);
    Users usersFind(String sql, Object[] arr);
    Users usersFind(Users users);
    Users usersFindCache(Long users_id,int mm);
    Users usersFindCache(String sql, Object[] arr,int mm);
    Users usersFindCache(Users users,int mm);
    List<Users> usersQuery(String sql, Object[] arr);
    List<Users> usersQuery(Users users);
    ListPage usersQuery(String sql, Object[] arr, int page, int limit);
    ListPage usersQuery(Users users, int page, int limit, int maxPage, int maxLimit);
    List<Users> usersQueryCache(String sql, Object[] arr, int mm);
    List<Users> usersQueryCache(Users users, int mm);
    ListPage usersQueryCache(String sql, Object[] arr, int page, int limit, int mm);
    ListPage usersQueryCache(Users users, int page, int limit, int maxPage, int maxLimit, int mm);
    int usersDelete(Users users);
    int usersInsert(Users users);
    int usersUpdate(Users users);
    int usersBatch(List<Users> list, boolean autoId);
    int usersBatch(List<Users> list);
    int usersBatch(String sql, List<Object[]> list);
    int usersInsertAsy(Users users, boolean auto);
    int usersInsertAsy(Users users);
    int usersDeleteAsy(Users users);
    int usersUpdateAsy(Users users);

}