package gzb.api

import gzb.db.gzb_system.dao.BaseDao
import gzb.db.gzb_system.dao.BaseDaoImpl
import gzb.db.gzb_system.entity.FileManager
import gzb.db.gzb_system.entity.GzbUsers
import gzb.tools.DateTime
import gzb.tools.Tools
import gzb.tools.config.StaticClasses
import gzb.tools.entity.UploadEntity
import gzb.tools.img.DrawmageUtil
import gzb.tools.img.PicUtils
import gzb.tools.log.Log
import gzb.tools.log.LogImpl
import gzb.tools.session.Session

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.text.SimpleDateFormat

class SystemAction {
    //static String webPath = "system"
    static BaseDao dao = new BaseDaoImpl();
    static Log log = new LogImpl(SystemAction.class);

    HttpServletRequest request;
    HttpServletResponse response;
    Session session;
    DateTime dateTime = new DateTime();
    //system/imageCode
    //system/register?acc=123456&pwd=123456&mailbox=123@qq.com&phone=12345678954&code=
    //system/login?acc=123456&pwd=123456&code=
    //system/readUserInfo
    //system/readFile?id=
    //uploadFile


    public Object imageCode() {
        try {
            session.put("system.imageCode", new DrawmageUtil().verification(response, 2));
            return null;
        } catch (Exception e) {
            log.req(e, "服务器发生错误", request);
            return Tools.jsonError();
        }
    }

    public Object register(String code, String acc, String mailbox,String phone, String pwd, Integer login) {
        try {
            if (Tools.isString(code, 4, 4) || Tools.isString(mailbox, 6, 32) || Tools.isString(phone, 11, 11) || Tools.isString(acc, 5, 16) || Tools.isString(pwd, 5, 16)) {
                return Tools.jsonFail("注册失败，输入参数不正确");
            }
            String code1 = session.getString("system.imageCode");
            session.delete("system.imageCode");
            if (code.equals(code1) == false) {
                return Tools.jsonFail("注册失败，验证码不正确");
            }
            GzbUsers gzbUsers = dao.gzbUsersFind(new GzbUsers().setGzbUsersAcc(acc));
            if (gzbUsers != null) {
                return Tools.jsonFail("注册失败，账号已存在");
            }
            gzbUsers = new GzbUsers();
            gzbUsers.setGzbUsersAcc(acc)
                    .setGzbUsersPwd(pwd)
                    .setGzbUsersMailbox(mailbox)
                    .setGzbUsersPhone(phone)
                    .setGzbUsersState(1)
                    .setGzbUsersTime(dateTime.toString());
            dao.gzbUsersInsert(gzbUsers);
            if (login != null && login == 1) {
                session.put("system.login", gzbUsers.toString());
                return Tools.jsonSuccess("注册成功", "{\"token\":\"" + session.getId() + "\"}");
            }
            return Tools.jsonJump("注册成功", StaticClasses.loginPage, "{\"token\":\"" + session.getId() + "\"}");
        } catch (Exception e) {
            log.req(e, "服务器发生错误", request);
            return Tools.jsonError();
        }
    }

    public Object login(String code, String acc, String pwd) {
        try {
            if (Tools.isString(code, 4, 4) || Tools.isString(acc, 5, 16) || Tools.isString(pwd, 5, 16)) {
                return Tools.jsonFail("登陆失败，输入参数不正确");
            }
            String code1 = session.getString("system.imageCode");
            session.delete("system.imageCode");
            if (code.equals(code1) == false) {
                return Tools.jsonFail("登陆失败，验证码不正确");
            }
            GzbUsers gzbUsers = dao.gzbUsersFind(new GzbUsers().setGzbUsersAcc(acc).setGzbUsersPwd(pwd));
            if (gzbUsers == null) {
                return Tools.jsonFail("登陆失败，账号或密码不正确");
            }
            if (gzbUsers.getGzbUsersState() < 1) {
                return Tools.jsonFail("登陆失败，账号状态异常");
            }
            session.put("system.login", gzbUsers.toString());
            return Tools.jsonJump("登陆成功", "index.html", "{\"token\":\"" + session.getId() + "\"}");
        } catch (Exception e) {
            log.req(e, "服务器发生错误", request);
            return Tools.jsonError();
        }
    }

    public Object readUserInfo(Integer all) {
        try {
            GzbUsers gzbUsers = getLoginInfo();
            if (gzbUsers == null) {
                return Tools.jsonJump(StaticClasses.loginPage);
            }
            if (all == null || all == 0) {
                gzbUsers.setGzbUsersState(null)
                        .setGzbUsersId(null)
                        .setGzbUsersPwd(null);
            }
            return Tools.jsonSuccess("获取用户信息成功", gzbUsers.toString());
        } catch (Exception e) {
            log.req(e, "服务器发生错误", request);
            return Tools.jsonError();
        }
    }

    private GzbUsers getLoginInfo() {
        String tmp = session.getString("system.login");
        if (tmp == null || tmp.length() < 1) {
            return null;
        }
        return new GzbUsers(tmp);
    }

    public Object uploadFile(String name) {
        try {
            Map<String, List<UploadEntity>> map = Tools.webFileUpload(request, StaticClasses.uploadPath);
            List<UploadEntity> list = map.get(name != null && name.length() > 0 ? name : "file");
            List<String> list_json = new ArrayList<>();
            String html = "";
            for (UploadEntity uploadEntity : list) {
                FileManager fileManager = new FileManager();
                fileManager.setFileManagerMd5(uploadEntity.getMd5());
                fileManager = dao.fileManagerFind(fileManager);
                if (fileManager == null) {
                    fileManager = new FileManager();
                    fileManager.setFileManagerMd5(uploadEntity.getMd5());
                    fileManager.setFileManagerName(uploadEntity.getFileName())
                            .setFileManagerReadNum(0)
                            .setFileManagerState(1)
                            .setFileManagerType(uploadEntity.getFileType())
                            .setFileManagerTime(dateTime.toString());
                    dao.fileManagerInsert(fileManager);
                }
                html += "api/system/readFile?id=" + fileManager.getFileManagerId() + "&read=1,";
                list_json.add("{\"fileId\":\"" + fileManager.getFileManagerId() + "\"}");
            }

            return Tools.jsonSuccess("上传完成：" + list_json.size() + "," + html, list_json);
        } catch (Exception e) {
            log.req(e, "ERR", request);
            return Tools.jsonError();
        }
    }

    /**
     * system/readFile?id=1660737179100003100&read=1&t=0.5
     * 读取图片可根据id和md5
     *
     * @param id 数据库id
     * @param read 0或者null 为 下载文件 , 1 为 预览文件
     * @param x 指定缩放宽度 可为空
     * @param y 指定缩放高度 可为空
     * @param t 指定缩放比例 可为空  0.1-1.0
     * @param b 返回结果为base64的文本型数据 可为空 0或者空是不编码 直接返回byte   1是base64一下 返回文本数据
     */
    public Object readFile(long id, String md5, Integer read, Integer x, Integer y, Double t, Integer b) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // 设置时区为GMT
            String dateStr = sdf.format(new DateTime().operation(1000 * 60 * 60 * 24).toDate());
            response.setHeader("Last-Modify", dateStr);
            response.setHeader("Cache-Control", "public,max-age=" + (60 * 60 * 24));
            FileManager fileManager = dao
                    .fileManagerFind(
                            new FileManager()
                                    .setFileManagerId(id)
                                    .setFileManagerMd5(md5 == null || md5.length() < 1 ? null : md5)
                    );
            if (fileManager == null) {
                return null;
            }
            fileManager.setFileManagerReadNum(fileManager.getFileManagerReadNum() + 1);
            dao.fileManagerUpdate(fileManager);
            String etag = request.getHeader("Etag");
            if (etag == null) {
                etag = request.getHeader("If-None-Match");
            }
            if (etag != null) {
                if (etag.equals(fileManager.getFileManagerMd5())) {
                    response.setStatus(304);
                    return null;
                }
            }

            response.setHeader("Etag", fileManager.getFileManagerMd5());
            byte[] bytes;
            File file = new File(StaticClasses.uploadPath + "/" +
                    fileManager.getFileManagerMd5().substring(0, 3) + "/" +
                    fileManager.getFileManagerMd5().substring(3, 6) + "/" +
                    fileManager.getFileManagerMd5().substring(6, 9) + "/" + fileManager.getFileManagerName());
            if (t != null && t > 0) {
                bytes = PicUtils.resize(file, t);
            } else {
                if (x != null && y != null && x > 0 && y > 0) {
                    bytes = PicUtils.resize(file, x, y);
                } else {
                    bytes = PicUtils.resize(file, 0.75);
                }
            }
            if (read == null || read + 0 == 0) {
                response.setHeader("Content-disposition", "attachment;filename=" + fileManager.getFileManagerName() + (b != null && b == 1 ? ".txt" : ""));
            }
            if (b != null && b == 1) {
                return "data:" + fileManager.getFileManagerType() + ";base64," + new String(Base64.getEncoder().encode(bytes));
            }
            response.setContentType("application/octet-stream;charset=UTF-8");
            response.setHeader("content-type", fileManager.getFileManagerType());
            response.setCharacterEncoding("UTF-8");
            response.getOutputStream().write(bytes);
            return null;
        } catch (Exception e) {
            log.req(e, "ERR", request);
            return Tools.jsonError();
        }

    }

}
