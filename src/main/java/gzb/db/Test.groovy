package gzb.db
import gzb.db.gzb_system.DataBase
import gzb.db.gzb_system.dao.BaseDao
import gzb.db.gzb_system.dao.BaseDaoImpl
import gzb.tools.DateTime
import gzb.tools.Tools
import gzb.tools.log.ColorEnum
import gzb.tools.log.Log
import gzb.tools.log.LogImpl
import gzb.tools.thread.GzbThread
import gzb.tools.thread.ThreadPool

class Test {
    static BaseDao dao = new BaseDaoImpl();
    static Log log = new LogImpl(Test.class)

    static void main(String[] args) {
        test2()
    }

    static def test2() {

    }

    static def test1() {
        DateTime dateTime = new DateTime();
        long a = new Date().getTime();
        ThreadPool.start(new GzbThread() {
            @Override
            void start() throws Exception {
                for (int i = 0; i < 1000000; i++) {
                    //DataBase.db.addAsyInfo
                    //DataBase.db.runSqlUpdateOrSaveOrDelete
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
        }, "test-ins", false, 5)

        while (true) {
            /*         ListPage<GzbUsers> listPage=dao.gzbUsersQuery(new GzbUsers(), Tools.getRandomInt(200,50),Tools.getRandomInt(200,50),200,200);
                     List<GzbUsers> list=listPage.getList();
                     for(GzbUsers gu : list){
                         gu.setGzbUsersTime(new DateTime().toString())
                         gu.update(dao);//该函数 sql显示 已关闭
                     }*/
            System.out.println(ColorEnum.getColor(5) + (new Date().getTime() - a))
            sleep(100)
        }


    }
}
