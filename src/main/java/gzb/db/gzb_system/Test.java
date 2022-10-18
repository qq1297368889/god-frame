package gzb.db.gzb_system;
import gzb.db.gzb_system.dao.BaseDao;
import gzb.db.gzb_system.dao.BaseDaoImpl;
import gzb.db.gzb_system.entity.GzbUsers;
import gzb.tools.DateTime;
import gzb.tools.Tools;
import gzb.tools.log.ColorEnum;
import gzb.tools.log.Log;
import gzb.tools.log.LogImpl;
import gzb.tools.thread.ThreadPool;
import java.util.Date;
public class Test {
    static final BaseDao dao=new BaseDaoImpl();
    static final Log log=new LogImpl(Test.class);
    public static void main(String[] args) throws InterruptedException {
        test1();
    }
    //测试插入 500W数据 总耗时 16秒  平均一秒 插入 31W
    public static void test1() throws InterruptedException {
        DateTime dateTime = new DateTime();
        long a = new Date().getTime();
        ThreadPool.start(() -> {
            for (int i = 0; i < 100000; i++) {
                DataBase.db.addAsyInfo("insert into " + DataBase.gzbUsersName + "(gzb_users_id,gzb_users_acc,gzb_users_pwd,gzb_users_phone,gzb_users_mailbox,gzb_users_time,gzb_users_state) values(?,?,?,?,?,?,?)"
                        , new Object[]{DataBase.db.getOnlyIdDistributed(),Tools.getRandomString(8),Tools.toMd5(Tools.getRandomString(8).getBytes()),
                                Tools.getRandomInt(99999, 10000) + "" + Tools.getRandomInt(99999, 10000) + "" + Tools.getRandomInt(9, 1),
                                Tools.getRandomString(8) + "@qq.com",dateTime.toString(),1});
            }
        }, "test-ins", false, 5);//5代表启动五个线程
        while (true) {
            System.out.println(ColorEnum.getColor(5) + (new Date().getTime() - a));//偷个懒 手动计时
            Thread.sleep(1000);
        }
    }
    public static void test2() throws InterruptedException {
        //数据多的话 加个索引 否则 慢死你
        log.i(new GzbUsers().setGzbUsersAcc("admin1").setGzbUsersPwd("admin1").setGzbUsersState(1).save(dao));//插入
        log.i(new GzbUsers().setGzbUsersAcc("admin1").query(dao).toString());//查询多条
        log.i(new GzbUsers().setGzbUsersAcc("admin1").find(dao).toString());//查询单条
        log.i(new GzbUsers().setGzbUsersAcc("admin1").query(dao,1,1,100,100).toString());//查询分页

        log.i(new GzbUsers().setGzbUsersAcc("admin1").find(dao).setGzbUsersPwd("update").update(dao));//查 改

        log.i(new GzbUsers().setGzbUsersAcc("admin1").query(dao));//查询多条
        log.i(new GzbUsers().setGzbUsersAcc("admin1").find(dao));//查询单条
        log.i(new GzbUsers().setGzbUsersAcc("admin1").query(dao,1,1,100,100));//查询分页
        log.i(new GzbUsers().setGzbUsersAcc("admin1").find(dao).delete(dao));//查 删
    }
}
