package gzb.api;

import gzb.db.gzb_system.dao.BaseDao;
import gzb.db.gzb_system.dao.BaseDaoImpl;
import gzb.tools.DateTime;
import gzb.tools.session.Session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseAction {
    public static BaseDao dao=new BaseDaoImpl();

    public HttpServletRequest request;
    public HttpServletResponse response;
    public Session session;
    public DateTime dateTime = new DateTime();
}
