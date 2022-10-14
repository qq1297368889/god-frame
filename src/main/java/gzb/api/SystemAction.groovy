package gzb.api


import gzb.db.gzb_system.dao.BaseDao
import gzb.db.gzb_system.dao.BaseDaoImpl
import gzb.db.gzb_system.entity.FileManager
import gzb.db.gzb_system.entity.GzbUsers
import gzb.tools.DateTime
import gzb.tools.Tools
import gzb.tools.cache.GzbCache
import gzb.tools.cache.GzbCacheMap
import gzb.tools.cache.GzbCacheMsql
import gzb.tools.cache.GzbCacheRedis
import gzb.tools.config.StaticClasses
import gzb.tools.entity.UploadEntity
import gzb.tools.groovy.AutoLoad
import gzb.tools.groovy.Request
import gzb.tools.img.PicUtils
import gzb.tools.log.Log
import gzb.tools.log.LogImpl
import gzb.tools.session.Session

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.nio.file.Files
import java.text.SimpleDateFormat

@Request(url = "system", contentType = "application/json;charset=UTF-8", crossDomain = false)
class SystemAction {
    static BaseDao dao = new BaseDaoImpl();
    static Log log = new LogImpl(SystemAction.class);

    HttpServletRequest request;
    HttpServletResponse response;
    Session session;
    DateTime dateTime = new DateTime();
    //http://ip:port/system/lockWindow
    public Object lockWindow() {
        try {
            Runtime.getRuntime().exec("rundll32.exe user32.dll,LockWorkStation");
            return Tools.jsonSuccess();
        } catch (Exception e) {
            log.req(e, "服务器发生错误", request);
            return Tools.jsonError();
        }
    }

    public Object imageCode(Integer t) {
        try {
            if (t == null || t == 0) {
                session.put("system.imageCode", Tools.getPictureCode1(response, 2));
            } else {
                session.put("system.imageCode", Tools.getPictureCode2(response));
            }

            return null;
        } catch (Exception e) {
            log.req(e, "服务器发生错误", request);
            return Tools.jsonError();
        }
    }

    public Object register(String code, String acc, String mailbox, String phone, String pwd, Integer login) {
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
        return new GzbUsers().setGzbUsersId(new GzbUsers(tmp).getGzbUsersId()).find(dao);
    }

    public Object uploadFile(UploadEntity[] files) {
        try {
            List<String> list_json = new ArrayList<>();
            for (UploadEntity uploadEntity : files) {
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
                    if (uploadEntity.getFile().length() > 10240000) {
                        fileManager.setFileManagerState(0);
                    }
                    dao.fileManagerInsert(fileManager);
                }
                list_json.add("{\"fileId\":\"" + fileManager.getFileManagerId() + "\"}");
            }

            return Tools.jsonSuccess("上传完成：" + list_json.size() + "个文件,注意:大于10M的文件 可以上传但是无法访问", list_json);
        } catch (Exception e) {
            log.req(e, "ERR", request);
            return Tools.jsonError();
        }
    }

    public Object uploadFile1(UploadEntity files) {
        try {
            UploadEntity uploadEntity = files
            List<String> list_json = new ArrayList<>();
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
                if (uploadEntity.getFile().length() > 10240000) {
                    fileManager.setFileManagerState(0);
                }
                dao.fileManagerInsert(fileManager);
            }
            list_json.add("{\"fileId\":\"" + fileManager.getFileManagerId() + "\"}");
            return Tools.jsonSuccess("上传完成：" + list_json.size() + "个文件,注意:大于10M的文件 可以上传但是无法访问", list_json);
        } catch (Exception e) {
            log.req(e, "ERR", request);
            return Tools.jsonError();
        }
    }

    public Object uploadFile2(File files) {
        try {
            List<String> list_json = new ArrayList<>();
            File file = files
            String md5 = Tools.fileToMd5(file);
            FileManager fileManager = new FileManager();
            fileManager.setFileManagerMd5(md5);
            fileManager = dao.fileManagerFind(fileManager);
            if (fileManager == null) {
                fileManager = new FileManager();
                fileManager.setFileManagerMd5(md5);
                fileManager.setFileManagerName(file.getName())
                        .setFileManagerReadNum(0)
                        .setFileManagerState(1)
                        .setFileManagerType(Files.probeContentType(file.toPath()))
                        .setFileManagerTime(dateTime.toString());
                if (file.length() > 10240000) {
                    fileManager.setFileManagerState(0);
                }
                dao.fileManagerInsert(fileManager);
            }
            list_json.add("{\"fileId\":\"" + fileManager.getFileManagerId() + "\"}");

            return Tools.jsonSuccess("上传完成：" + list_json.size() + "个文件,注意:大于10M的文件 可以上传但是无法访问", list_json)
        } catch (Exception e) {
            log.req(e, "ERR", request);
            return Tools.jsonError();
        }
    }

    public Object uploadFile3(File[] files) {
        try {
            List<String> list_json = new ArrayList<>();
            for (File file : files) {
                String md5 = Tools.fileToMd5(file);
                FileManager fileManager = new FileManager();
                fileManager.setFileManagerMd5(md5);
                fileManager = dao.fileManagerFind(fileManager);
                if (fileManager == null) {
                    fileManager = new FileManager();
                    fileManager.setFileManagerMd5(md5);
                    fileManager.setFileManagerName(file.getName())
                            .setFileManagerReadNum(0)
                            .setFileManagerState(1)
                            .setFileManagerType(Files.probeContentType(file.toPath()))
                            .setFileManagerTime(dateTime.toString());
                    if (file.length() > 10240000) {
                        fileManager.setFileManagerState(0);
                    }
                    dao.fileManagerInsert(fileManager);
                }
                list_json.add("{\"fileId\":\"" + fileManager.getFileManagerId() + "\"}");
            }

            return Tools.jsonSuccess("上传完成：" + list_json.size() + "个文件,注意:大于10M的文件 可以上传但是无法访问", list_json)
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
            FileManager fileManager = new FileManager()
                    .setFileManagerId(id)
                    .setFileManagerState(1)
                    .setFileManagerMd5(md5 == null || md5.length() < 1 ? null : md5).findCache(dao,60*60);
            if (fileManager == null) {
                return Tools.jsonFail("文件不存在或禁止访问");
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
            response.setHeader("Etags", fileManager.getFileManagerMd5());

            byte[] bytes;
            File file = new File(Tools.toMD5Path(StaticClasses.uploadPath, fileManager.getFileManagerMd5()) + fileManager.getFileManagerName());
            if (t != null && t > 0) {
                bytes = PicUtils.resize(file, t);
            } else {
                if (x != null && y != null && x > 0 && y > 0) {
                    bytes = PicUtils.resize(file, x, y);
                } else {
                    bytes = Tools.fileReadByte(file);
                }
            }
            if (read == null || read + 0 == 0) {
                response.setHeader("Content-disposition", "attachment;filename=" + fileManager.getFileManagerName() + (b != null && b == 1 ? ".txt" : ""));
            } else if (b != null && b + 0 == 1) {
                return "data:" + fileManager.getFileManagerType() + ";base64," + new String(Base64.getEncoder().encode(bytes));
            }
            response.setHeader("content-type", fileManager.getFileManagerType());
            response.setCharacterEncoding("UTF-8");
            response.getOutputStream().write(bytes);
            response.getOutputStream().flush();
            return null;
        } catch (Exception e) {
            log.req(e, "ERR", request);
            return Tools.jsonError();
        }

    }

}
