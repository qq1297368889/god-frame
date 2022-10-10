package gzb.db

import gzb.db.gzb_system.dao.BaseDao
import gzb.db.gzb_system.dao.BaseDaoImpl
import gzb.db.gzb_system.entity.GzbUsers
import gzb.tools.DateTime
import gzb.tools.ListPage
import gzb.tools.Tools
import gzb.tools.log.Log
import gzb.tools.log.LogImpl
import gzb.tools.thread.GzbThread
import gzb.tools.thread.ThreadPool

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class Test {
    static BaseDao dao = new BaseDaoImpl();
    static Log log = new LogImpl(Test.class)

    static void main(String[] args) {
        test()
    }

    static def test() {
        DateTime dateTime = new DateTime();
        log.i("--------------------------"+gzb.db.pay_system.DataBase.db.getOnlyIdNumber("test","test_id"))
        /*       for (int n = 0; n < 10; n++) {
                   ThreadPool.start(new GzbThread() {
                       @Override
                       void start() throws Exception {
                           for (int i = 0; i < 200000; i++) {
                               DataBase.db.addAsyInfo("insert into " + DataBase.gzbUsersName + "(gzb_users_id,gzb_users_acc,gzb_users_pwd,gzb_users_phone,gzb_users_mailbox,gzb_users_time,gzb_users_state) values(?,?,?,?,?,?,?)"
                                       , new Object[]{
                                       DataBase.db.getOnlyIdDistributed(),
                                       Tools.getRandomString(8),
                                       Tools.toMd5(Tools.getRandomString(8).getBytes()),
                                       Tools.getRandomInt(99999, 10000) + "" + Tools.getRandomInt(99999, 10000) + "" + Tools.getRandomInt(9, 1),
                                       Tools.getRandomString(8) + "@qq.com",
                                       dateTime.toString(),
                                       1
                               })
                           }
                       }
                   })
               }
       */
/*        while (true){
            ListPage<GzbUsers> listPage=dao.gzbUsersQuery(new GzbUsers(), Tools.getRandomInt(200,50),Tools.getRandomInt(999,50),200,200);
            List<GzbUsers> list=listPage.getList();
            GzbUsers gzbUsers=list.get(Tools.getRandomInt(listPage.getList().size()-1,0))
            gzbUsers.setGzbUsersTime(new DateTime().toString())
            gzbUsers.updateAsy(dao);
            sleep(100)
        }
 */


    }
}
