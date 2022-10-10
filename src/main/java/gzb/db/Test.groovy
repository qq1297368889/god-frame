package gzb.db

import gzb.db.gzb_system.dao.BaseDao
import gzb.db.gzb_system.dao.BaseDaoImpl
import gzb.db.gzb_system.entity.GzbUsers
import gzb.tools.log.Log
import gzb.tools.log.LogImpl

class Test {
    static BaseDao dao = new BaseDaoImpl();
    static Log log = new LogImpl()

    public static void main(String[] args) {
        test()
    }
    static def test(){
        GzbUsers gzbUsers
        gzbUsers = new GzbUsers().setGzbUsersAcc("123456").setGzbUsersPwd("123456").find(dao)
        log.i(gzbUsers.toString())
        gzbUsers = new GzbUsers().setGzbUsersAcc("123456").setGzbUsersPwd("123456").find(dao)
        log.i(gzbUsers.toString())
        gzbUsers = new GzbUsers().setGzbUsersAcc("123456").setGzbUsersPwd("123456").find(dao)
        log.i(gzbUsers.toString())
    }
}
