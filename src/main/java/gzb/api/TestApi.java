package gzb.api;

import gzb.db.gzb_system.entity.GzbUsers;
import gzb.tools.Tools;
import gzb.tools.groovy.Request;
import jline.internal.Log;

@Request(url = "testApi", contentType = "application/json;charset=UTF-8", crossDomain = false,single = true)
public class TestApi extends BaseAction {
    public String test(String msg1, String msg2, String msg3) {    //testApi/test?msg1=111&msg2=222&msg3=333
        return Tools.jsonSuccess(msg1 + msg2 + msg3);
    }

    public String test2() {    //testApi/test2
        //插入数据 10条  Tools.getRandomString(12)获取一个随机12位字符串   dateTime.toString()获取当前时间 字符串格式
        for (int i = 0; i < 10; i++) {
            new GzbUsers().setGzbUsersAcc(Tools.getRandomString(12))
                    .setGzbUsersPwd(Tools.getRandomString(12))
                    .setGzbUsersMailbox(Tools.getRandomString(12)+"@qq.com")
                    .setGzbUsersPhone("12345678901")
                    .setGzbUsersState(1)
                    .setGzbUsersTime(dateTime.toString())
                    .saveAsy(dao);
        }
        return Tools.jsonSuccess("OK");
    }
}
