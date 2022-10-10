package gzb.db.gzb_system;

import gzb.db.gzb_system.dao.BaseDao;
import gzb.db.gzb_system.dao.BaseDaoImpl;
import gzb.db.gzb_system.entity.GzbUsers;
import gzb.tools.ListPage;
import gzb.tools.Tools;
import gzb.tools.log.Log;
import gzb.tools.log.LogImpl;

public class Test {
    static BaseDao dao = new BaseDaoImpl();
    static Log log = new LogImpl(Test.class);
    public static void main(String[] args) {
        ListPage<GzbUsers> listPage=dao.gzbUsersQuery(new GzbUsers(), Tools.getRandomInt(200,50),1000,200,200);

    }
}
