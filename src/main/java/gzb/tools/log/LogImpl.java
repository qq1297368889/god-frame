package gzb.tools.log;

import gzb.tools.config.StaticClasses;
import gzb.tools.DateTime;
import gzb.tools.Tools;
import gzb.tools.thread.GzbThread;
import gzb.tools.thread.ThreadPool;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class LogImpl implements Log {
    static StringBuffer infoLog = new StringBuffer();
    static StringBuffer exceptionLog = new StringBuffer();
    static StringBuffer sqlLog = new StringBuffer();
    static StringBuffer requestLog = new StringBuffer();
    static StringBuffer requestExceptionLog = new StringBuffer();
    //static Lock lock = new ReentrantLock();
    String className = null;

    static {
        ThreadPool.start(new GzbThread() {
            @Override
            public void start() {
                try {
                    String rootPath = Tools.getProjectPath();
                    rootPath += "/logs";
                    new File(rootPath).mkdirs();
                    new File(rootPath + "/exception").mkdirs();
                    new File(rootPath + "/sql").mkdirs();
                    new File(rootPath + "/info").mkdirs();
                    new File(rootPath + "/request").mkdirs();
                    new File(rootPath + "/requestException").mkdirs();
                    File file_exception = null;
                    File file_sql = null;
                    File file_info = null;
                    File file_request = null;
                    File file_requestException = null;
                    String file_exception_log = null;
                    String file_sql_log = null;
                    String file_info_log = null;
                    String file_request_log = null;
                    String file_requestException_log = null;
                    String day = null;
                    while (true) {
                        try {
                            if (day == null || !day.equals(new DateTime().format("yyyy-MM-dd HH"))) {
                                day = new DateTime().format("yyyy-MM-dd HH");
                                file_exception = new File(rootPath + "/exception/" + day + ".log");
                                file_sql = new File(rootPath + "/sql/" + day + ".log");
                                file_info = new File(rootPath + "/info/" + day + ".log");
                                file_request = new File(rootPath + "/request/" + day + ".log");
                                file_requestException = new File(rootPath + "/requestException/" + day + ".log");
                                if (!file_exception.exists()) {
                                    file_exception.createNewFile();
                                }
                                if (!file_sql.exists()) {
                                    file_sql.createNewFile();
                                }
                                if (!file_info.exists()) {
                                    file_info.createNewFile();
                                }
                                if (!file_request.exists()) {
                                    file_request.createNewFile();
                                }
                                if (!file_requestException.exists()) {
                                    file_requestException.createNewFile();
                                }
                            }


                            file_sql_log = sqlLog.toString();
                            sqlLog = new StringBuffer();
                            file_info_log = infoLog.toString();
                            infoLog = new StringBuffer();
                            file_exception_log = exceptionLog.toString();
                            exceptionLog = new StringBuffer();
                            file_request_log = requestLog.toString();
                            requestLog = new StringBuffer();
                            file_requestException_log = requestExceptionLog.toString();
                            requestExceptionLog = new StringBuffer();
                            Tools.fileSaveString(file_sql, file_sql_log == null ? "" : file_sql_log, true);
                            Tools.fileSaveString(file_info, file_info_log == null ? "" : file_info_log, true);
                            Tools.fileSaveString(file_exception, file_exception_log == null ? "" : file_exception_log, true);
                            Tools.fileSaveString(file_request, file_request_log == null ? "" : file_request_log, true);
                            Tools.fileSaveString(file_requestException, file_requestException_log == null ? "" : file_requestException_log, true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        sleep(1000 * 2);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public void sleep(long hm) {
                try {
                    Thread.sleep(hm);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Log", false);

    }

    public LogImpl(Class class1) {
        className = class1.getName();
    }

    public LogImpl() {
        className = "def";
    }

    private void append(StringBuffer sb, Object log) {
        StringBuffer sb_prv = new StringBuffer();
        sb_prv.append(new DateTime().formatDateTime())
                .append(" : ")
                .append(className)
                .append(" : ")
                .append(log)
                .append("\r\n");
        if (StaticClasses.showLog) {
            System.out.print(sb_prv.toString());
        }
        sb.append(sb_prv);
    }

    public void e(Exception e) {
        append(exceptionLog, Tools.getExceptionInfo(e));
    }

    public void e(Exception e, Object log) {
        log += ":\r\n" + Tools.getExceptionInfo(e);
        append(exceptionLog, "\\33[1m\\33[31m"+log);
    }

    public void req(Exception e, Object log, HttpServletRequest request) {
        log += ":\r\n" + Tools.getExceptionInfo(e);
        StringBuilder sb = new StringBuilder();
        sb.append(log);
        sb.append("MET=");
        sb.append(request.getMethod());
        sb.append("/");
        sb.append(request.getRequestURL());
        sb.append("\r\n");
        sb.append("协议头：************************************************");
        sb.append("\r\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            sb.append(s);
            sb.append(":");
            sb.append(request.getHeader(s));
            sb.append("\r\n");
        }
        sb.append("\r\n");
        sb.append("参数：************************************************");
        sb.append("\r\n");
        Map<String, String[]> map = request.getParameterMap();
        for (Map.Entry<String, String[]> en : map.entrySet()) {
            String[] ss1 = en.getValue();
            for (String string : ss1) {
                sb.append(en.getKey());
                sb.append("=");
                sb.append((ss1 == null ? "" : string));
                sb.append("&");
            }
        }
        sb.append("\r\n");
        sb.append("请求结束：************************************************");
        sb.append("\r\n");
        append(requestExceptionLog, sb.toString());
    }

    public void i(Object log) {
        append(infoLog, log);
    }

    public void req(Object log, HttpServletRequest request) {
        if (log == null) {
            append(requestLog, reqReturn(request).toString());
        } else {
            append(requestLog, reqReturn(request).append(log).toString());
        }

    }

    public StringBuilder reqReturn(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        sb.append("MET=");
        sb.append(request.getMethod());
        sb.append("/");
        sb.append(request.getRequestURL());
        sb.append("\r\n");
        sb.append("协议头：************************************************");
        sb.append("\r\n");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String s = headerNames.nextElement();
            sb.append(s);
            sb.append(":");
            sb.append(request.getHeader(s));
            sb.append("\r\n");
        }
        sb.append("\r\n");
        sb.append("参数：************************************************");
        sb.append("\r\n");
        Map<String, String[]> map = request.getParameterMap();
        for (Map.Entry<String, String[]> en : map.entrySet()) {
            String[] ss1 = en.getValue();
            for (String string : ss1) {
                sb.append(en.getKey());
                sb.append("=");
                sb.append((ss1 == null ? "" : string));
                sb.append("&");
            }
        }
        sb.append("\r\n");
        sb.append("请求结束：************************************************");
        sb.append("\r\n");
        return sb;
    }

    public void req(HttpServletRequest request) {
        req(null, request);
    }

    public void sql(Object log) {
        append(sqlLog, log);
    }


    public void print(Object[] arr) {
        print(null, arr);
    }

    public void print(String name, Object[] arr) {
        StringBuilder sb = new StringBuilder();
        if (name != null) {
            sb.append(name).append(":");
        }
        for (int i = 0; i < arr.length; i++) {
            if (i == 0) {
                sb.append("[\n");
            }
            sb.append(arr[i].toString());
            if (i == arr.length - 1) {
                sb.append("\n]");
            } else {
                sb.append(",\n");
            }
        }
        i(sb.toString());
    }

    public void print(List<?> arr) {
        print(arr.toArray());
    }

    public void print(String name, List<?> arr) {
        print(name, arr.toArray());
    }
}
