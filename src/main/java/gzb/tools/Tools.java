package gzb.tools;

import gzb.tools.config.StaticClasses;
import gzb.tools.log.LogImpl;
import gzb.tools.entity.UploadEntity;
import org.springframework.boot.system.ApplicationHome;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.zip.*;

public class Tools {
    static gzb.tools.log.Log Log = new LogImpl(Tools.class);
    public static String ProjectPath = null;
    public static String[] ss1 = "QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm0123456789".split("|");

    public static void main(String[] args) {
        String str="12345";
        Log.i(str.substring(str.length()-1,str.length()));
    }
    public static final ArrayList toArrayList() {
        return new ArrayList() {
            @Override
            public String toString() {
                System.out.println("ArrayList.toString");
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < this.size(); i++) {
                    sb.append(this.get(i));
                }
                return sb.toString();
            }
        };
    }

    public static final Object[] toArray(Object... objs) {
        return objs;
    }

    public static final Object[] toArray() {
        return new Object[0];
    }

    public static final String[] toArrayString(Object... objs) {

        String[] ss1 = new String[objs.length];
        for (int i = 0; i < objs.length; i++) {
            ss1[i] = (String) objs[i];
        }
        return ss1;
    }

    public static final Integer[] toArrayInteger(Object... objs) {
        Integer[] ss1 = new Integer[objs.length];
        for (int i = 0; i < objs.length; i++) {
            ss1[i] = (Integer) objs[i];
        }
        return ss1;
    }

    public static final Short[] toArrayShort(Object... objs) {
        Short[] ss1 = new Short[objs.length];
        for (int i = 0; i < objs.length; i++) {
            ss1[i] = (Short) objs[i];
        }
        return ss1;
    }

    public static final Long[] toArrayLong(Object... objs) {
        Long[] ss1 = new Long[objs.length];
        for (int i = 0; i < objs.length; i++) {
            ss1[i] = (Long) objs[i];
        }
        return ss1;
    }

    public static final Float[] toArrayFloat(Object... objs) {
        Float[] ss1 = new Float[objs.length];
        for (int i = 0; i < objs.length; i++) {
            ss1[i] = (Float) objs[i];
        }
        return ss1;
    }

    public static final Boolean[] toArrayBoolean(Object... objs) {
        Boolean[] ss1 = new Boolean[objs.length];
        for (int i = 0; i < objs.length; i++) {
            ss1[i] = (Boolean) objs[i];
        }
        return ss1;
    }

    public static final Double[] toArrayDouble(Object... objs) {
        Double[] ss1 = new Double[objs.length];
        for (int i = 0; i < objs.length; i++) {
            ss1[i] = (Double) objs[i];
        }
        return ss1;
    }

    public static final Character[] toArrayCharacter(Object... objs) {
        Character[] ss1 = new Character[objs.length];
        for (int i = 0; i < objs.length; i++) {
            ss1[i] = (Character) objs[i];
        }
        return ss1;
    }

    public static final Byte[] toArrayByte(Object... objs) {
        Byte[] ss1 = new Byte[objs.length];
        for (int i = 0; i < objs.length; i++) {
            ss1[i] = (Byte) objs[i];
        }
        return ss1;
    }


    public static final Integer[] toArray(Integer... arr) {
        return arr;
    }

    public static final Short[] toArray(Short... arr) {
        return arr;
    }

    public static final Long[] toArray(Long... arr) {
        return arr;
    }

    public static final Float[] toArray(Float... arr) {
        return arr;
    }

    public static final Boolean[] toArray(Boolean... arr) {
        return arr;
    }

    public static final Double[] toArray(Double... arr) {
        return arr;
    }

    public static final Character[] toArray(Character... arr) {
        return arr;
    }

    public static final Byte[] toArray(Byte... arr) {
        return arr;
    }

    public static final String doubleTo2(double data) {
        return String.format("%.2f", data);
    }

    public static final String doubleTo1(double data) {
        return String.format("%.1f", data);
    }


    /**
     * 如果object ==null 或者 =="" 返回true
     */
    public static final boolean isNull(Object object) {
        return object == null || object.toString().length() < 1;
    }

    public static final boolean isString(String str, int min, int max) {
        return isNull(str) || str.length() < min || str.length() > max;
    }

    //json状态 1成功 2失败 3错误 4跳转网址
    public static final String jsonSuccess() {
        return json(1, "成功", "{}", "");
    }

    public static final String jsonSuccess(String msg) {
        return json(1, msg, "{}", "");
    }

    public static final String jsonSuccess(String msg, List<?> list) {
        return json(1, msg, list, "");
    }

    public static final String jsonSuccess(List<?> list) {
        return json(1, "成功", list, "");
    }

    public static final String jsonSuccess(String msg, Object data) {
        return json(1, msg, data, "");
    }

    public static final String jsonFail(String msg) {
        return json(2, msg, "{}", "");
    }

    public static final String jsonError() {
        return json(3, "服务器发生错误,请稍后重试!", "{}", "");
    }

    public static final String jsonError(String msg) {
        return json(3, msg, "{}", "");
    }

    public static final String jsonJump(String msg, String url, List<?> list) {
        return json(4, msg, list, url);
    }

    public static final String jsonJump(String msg, String url, String object) {
        return json(4, msg, object, url);
    }

    public static final String jsonJump(String url) {
        return json(4, "未登录或登录失效,请重新登录~", "{}", url);
    }

    public static final String json(Object state, Object message, Object object, Object jump) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append("{")

                .append("\"")
                .append(StaticClasses.json_code)
                .append("\":\"")
                .append(0)
                .append("\",")

                .append("\"")
                .append(StaticClasses.json_state)
                .append("\":\"")
                .append(state)
                .append("\",")

                .append("\"")
                .append(StaticClasses.json_message)
                .append("\":\"")
                .append(message)
                .append("\",")

                .append("\"")
                .append(StaticClasses.json_jump)
                .append("\":\"")
                .append(jump)
                .append("\",")

                .append("\"")
                .append(StaticClasses.json_data)
                .append("\":")
                .append(object)
                .append("}");
        String json = stringBuilder.toString()
                .replaceAll("\r\n", "\\\\r\\\\n")
                .replaceAll("\r", "\\\\r")
                .replaceAll("\n", "\\\\n")
                .replaceAll("\t", "\\\\t");
        Log.i(json);
        return json;
    }

    public static final Map<String, List<UploadEntity>> webFileUpload(HttpServletRequest request, String filePath) throws Exception {

        Collection<Part> parts = request.getParts();
        if (filePath == null) {
            filePath = System.getProperty("java.io.tmpdir");
        }
        filePath = filePath.replaceAll("\\\\", "/");
        if (filePath.substring(filePath.length() - 1).equals("/") == false) {
            filePath += "/";
        }
        Map<String, List<UploadEntity>> map = new HashMap<>();
        String fileName;
        InputStream inputStream;
        FileOutputStream fos;
        Part part;
        byte[] bytes;
        String md5;
        File file;
        List<UploadEntity> list;
        for (Iterator<Part> iterator = parts.iterator(); iterator.hasNext(); ) {
            part = iterator.next();
            Log.i("类型名称=" + part.getName() +
                    " / 类型=" + part.getContentType() +
                    " / 提交的类型名称=" + part.getSubmittedFileName() +
                    " / 流=" + part.getInputStream());
            inputStream = part.getInputStream();
            bytes = inputStream.readAllBytes();
            md5 = toMd5(bytes);
            String[] ss1 = part.getSubmittedFileName().split("\\.");
            if (ss1.length >= 2) {
                fileName = md5 + "." + ss1[ss1.length - 1];
            } else {
                fileName = md5 + ".data";
            }

            file = new File(filePath + md5.substring(0, 3) + "/" + md5.substring(3, 6) + "/" + md5.substring(6, 9) + "/" + fileName);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();

                fos = new FileOutputStream(file);
                fos.write(bytes, 0, bytes.length);
                inputStream.close();
                fos.flush();
                fos.close();
            } else {
                Log.i("已存在文件：" + filePath + fileName);
            }

            list = map.get(part.getName());
            if (list == null) {
                list = new ArrayList<>();
            }
            UploadEntity uploadEntity = new UploadEntity();
            uploadEntity.setFile(file);
            uploadEntity.setFileName(fileName);
            uploadEntity.setFilePath(filePath + md5.substring(0, 3) + "/" + md5.substring(3, 6) + "/" + md5.substring(6, 9) + "/");
            uploadEntity.setMd5(md5);
            uploadEntity.setFileType(part.getContentType());
            list.add(uploadEntity);
            map.put(part.getName(), list);
        }
        return map;
    }

    public static final void CookieSet(String key, String value, int mm,
                                       HttpServletResponse response, HttpServletRequest request) {

        if (key == null || value == null) {
            return;
        }
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(mm);
        cookie.setPath("/");
        response.addCookie(cookie);

    }

    public static final String CookieGet(String key, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return null;
        }
        for (Cookie cookie : cookies) {
            if (key.equals(cookie.getName())) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static final String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                //根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ip = inet.getHostAddress();
            }
        }
        return ip;
    }


    /**
     * 小数 保留位数
     */
    public static final String doubleSize(double data, int size) {
        return String.format("%." + size + "f", data);
    }

    /**
     * 获取指定长度的随机字符a-z,A-Z,0-9
     */
    public static final String getRandomString(int len) {
        String str = "";
        for (int i = 0; i < len; i++) {
            str += ss1[getRandomInt(ss1.length - 1, 0)];
        }
        return str;
    }

    static Random random = new Random(new Date().getTime() + 100);

    /**
     * 获取整数随机值
     */
    public static final int getRandomInt(int max, int min) {
        return random.nextInt(max - min + 1) + min;
    }

    public static final String getUUID() {
        return new Date().getTime() + getRandomString(10);
    }

    public static final String getUUID(int len) {
        return new Date().getTime() + getRandomString(len);
    }

    public static final String getUUID(String id) {
        return new Date().getTime() + id;
    }


    /**
     * 是否linux服务器
     */
    public static final boolean isLinux() {
        return System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0;
    }

    /**
     * 获取项目路径
     */
    public static final String getProjectPath() {
        if (ProjectPath != null) {
            return ProjectPath;
        }
        ApplicationHome home = new ApplicationHome(Tools.class);
        String basePath = home.getSource().getParentFile().toString();
        ProjectPath = basePath.replaceAll("\\\\", "/") + "/";
        return ProjectPath;
    }

    /**
     * 获取异常信息 详细
     */
    public static final String getExceptionInfo(Exception ex) {
        ByteArrayOutputStream out = null;
        PrintStream pout = null;
        String ret = "";
        try {
            out = new ByteArrayOutputStream();
            pout = new PrintStream(out);
            ex.printStackTrace(pout);
            ret = new String(out.toByteArray());
            out.close();
        } catch (Exception e) {
            return ex.getMessage();
        } finally {
            if (pout != null) {
                pout.close();
            }
        }
        return ret;
    }

    /**
     * 获取锁ReentrantLock
     */
    public static final Lock lockGet() {
        return new ReentrantLock();
    }

    public static final String configGetString(String key, String defVal) throws Exception {
        String fileUrl = getProjectPath() + "application.properties";
        File f = new File(fileUrl);
        if (!f.exists()) {
            fileUrl = getProjectPath() + "classes/application.properties";
            f = new File(fileUrl);
            if (!f.exists()) {
                return null;
            }
        }
        return configGetString(fileUrl, key, defVal);
    }

    public static final String configGetString(String fileUrl, String key, String defVal) throws Exception {
        String[] ss0 = Tools.fileReadArray(fileUrl, "UTF-8");
        for (String string : ss0) {
            if (string != null && string.length() > 0) {
                String[] ss1 = string.split("=", 2);
                if (ss1.length == 2 && ss1[0].equals(key)) {
                    defVal = ss1[1];
                    break;
                }
            }
        }
        return defVal;
    }

    public static final Integer configGetInteger(String key, String defVal) throws Exception {
        return Integer.valueOf(configGetString(key, defVal));
    }

    public static final Integer configGetInteger(String fileUrl, String key, String defVal) throws Exception {
        return Integer.valueOf(configGetString(fileUrl, key, defVal));
    }

    public static final Boolean configGetBoolean(String fileUrl, String key, String defVal) throws Exception {
        return Boolean.valueOf(configGetString(fileUrl, key, defVal));
    }

    public static final Boolean configGetBoolean(String key, String defVal) throws Exception {
        return Boolean.valueOf(configGetString(key, defVal));
    }

    public static final void configSet(String fileUrl, String key, Object val) throws Exception {
        Tools.fileNew(fileUrl);
        String[] ss0 = Tools.fileReadArray(fileUrl, "UTF-8");
        String all = "";
        for (String string : ss0) {
            if (string != null && string.length() > 0) {
                String[] ss1 = string.split("=", 2);
                if (ss1.length == 2 && ss1[0].equals(key)) {
                    all += key + "=" + val + "\r\n";
                } else {
                    all += ss1[0] + "=" + ss1[1] + "\r\n";
                }
            }
        }
        Tools.fileSaveString(fileUrl, all, false);
    }


    public static final String textBase64Encoder(String str) {
        try {
            return textBase64Encoder(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String textBase64Encoder(byte[] strByte) {
        try {
            return new String(Base64.getEncoder().encode(strByte), "UTF-8").replaceAll("=", "_").replaceAll("\\+", "-");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String textBase64Decoder(String str) {
        try {
            str = str.replaceAll("\r", "");
            str = str.replaceAll("\n", "");
            str = str.replaceAll("\t", "");
            str = str.replaceAll("\\s", "");
            return new String(textBase64DecoderByte(str), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final byte[] textBase64DecoderByte(String str) {
        try {
            return Base64.getDecoder().decode(str.replaceAll("_", "=").replaceAll("-", "+"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String textAESEncoder(String str, String pwd1, String pwd2) {
        try {
            byte[] raw = pwd1.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"
            IvParameterSpec iv = new IvParameterSpec(pwd2.getBytes("UTF-8"));//使用CBC模式，需要一个向量iv，可增加加密算法的强度
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
            return textBase64Encoder(cipher.doFinal(str.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String textAESDecoder(String str, String pwd1, String pwd2) {
        try {
            byte[] raw = pwd1.getBytes("UTF-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(pwd2.getBytes("UTF-8"));
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            return new String(cipher.doFinal(textBase64DecoderByte(str)), "UTF-8");
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }


    /**
     * 获取文本MD5
     * 参数1:文本
     * 默认为 UTF-8
     */
    public static final String textToMd5(String str) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return toMd5(str.getBytes("UTF8"));
    }

    /**
     * 获取文本MD5
     * 参数1:byte[]
     */
    public static final String toMd5(byte[] bytes) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest m = MessageDigest.getInstance("MD5");
        m.update(bytes);
        byte s[] = m.digest();
        String result = "";
        for (int i = 0; i < s.length; i++) {
            result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
        }
        while (result.length() < 32) {
            result = "0" + result;
        }
        return result;
    }

    public static final String textMid(String str, String q, String h, int index) {
        int a = 0;
        int b = 0;
        while (index > 0) {
            index--;
            a = str.indexOf(q, b);
            if (a < 0) {
                return null;
            }
            b = str.indexOf(h, a + q.length());
            if (b < 0) {
                return null;
            }
        }
        return str.substring(a + q.length(), b);
    }


    public static final List<String> textMid(String str, String q, String h) {
        int a = 0;
        int b = 0;
        List<String> list = new ArrayList<String>();
        while (true) {
            a = str.indexOf(q, b);
            if (a < 0) {
                break;
            }
            b = str.indexOf(h, a);
            if (b < 0) {
                break;
            }
            list.add(str.substring(a + q.length(), b));
        }
        return list;
    }


//###############################################文件操作@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    /**
     * 重命名文件
     *
     * @param
     * @return
     */
    public static final void fileRename(String oldFileName, String newFileName) {
        File oldFile = new File(oldFileName);
        File newFile = new File(newFileName);
        if (oldFile.exists() && oldFile.isFile()) {
            oldFile.renameTo(newFile);
        }
    }

    /**
     * 保存字符串数据
     * 参数1:文件地址 例如 "d:/a.txt"
     * 参数2:要保存的数据
     * 参数3:是否追加,true追加写入,false覆盖写入
     * 默认为 UTF-8
     */
    public static final void fileSaveArray(File file, Object[] str, String encoding) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
            for (Object object : str) {
                fos.write((object + "\r\n").getBytes(encoding));
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存字符串数据
     * 参数1:文件地址 例如 "d:/a.txt"
     * 参数2:要保存的数据
     * 参数3:是否追加,true追加写入,false覆盖写入
     * 默认为 UTF-8
     */
    public static final void fileSaveList(File file, List<?> str, String encoding) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, true);
            for (Object object : str) {
                fos.write((object + "\r\n").getBytes(encoding));
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 保存字符串数据
     * 参数1:文件地址 例如 "d:/a.txt"
     * 参数2:要保存的数据
     * 参数3:是否追加,true追加写入,false覆盖写入
     * 默认为 UTF-8
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static final void fileSaveString(String file, String str, boolean add) throws UnsupportedEncodingException, IOException {
        fileSaveString(file, str, add, "UTF-8");
    }

    /**
     * 保存字符串数据
     * 参数1:文件地址 例如new File("d:/a.txt")
     * 参数2:要保存的数据
     * 参数3:是否追加,true追加写入,false覆盖写入
     * 默认为 UTF-8
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static final void fileSaveString(File file, String str, boolean add) throws UnsupportedEncodingException, IOException {
        fileSaveString(file, str, add, "UTF-8");
    }

    /**
     * 保存字符串数据
     * 参数1:文件地址 例如 "d:/a.txt"
     * 参数2:要保存的数据
     * 参数3:是否追加,true追加写入,false覆盖写入
     * 参数4:编码
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static final void fileSaveString(String file, String str, boolean add, String encoding) throws UnsupportedEncodingException, IOException {
        fileSaveByte(new File(file), str.getBytes(encoding), add);
    }

    /**
     * 保存字符串数据
     * 参数1:文件地址 例如 new File("d:/a.txt")
     * 参数2:要保存的数据
     * 参数3:是否追加,true追加写入,false覆盖写入
     * 参数4:编码
     *
     * @throws IOException
     * @throws UnsupportedEncodingException
     */
    public static final void fileSaveString(File file, String str, boolean add, String encoding) throws UnsupportedEncodingException, IOException {
        fileSaveByte(file, str.getBytes(encoding), add);
    }


    /**
     * 保存byte数据
     * 参数1:文件地址 例如 "d:/a.txt"
     * 参数2:要保存的数据
     * 参数3:是否追加,true追加写入,false覆盖写入
     *
     * @throws IOException
     */
    public static final void fileSaveByte(String file, byte[] bytes, boolean add) throws IOException {
        fileSaveByte(new File(file), bytes, add);
    }

    /**
     * 保存byte数据
     * 参数1:文件地址 例如 new File("d:/a.txt")
     * 参数2:要保存的数据
     * 参数3:是否追加,true追加写入,false覆盖写入
     *
     * @throws IOException
     */
    public static final void fileSaveByte(File file, byte[] bytes, boolean add) throws IOException {
        FileOutputStream fos = null;
        try {
            fileNew(file);
            fos = new FileOutputStream(file, add);
            fos.write(bytes);
            fos.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw e;
                }
            }
        }

    }

    /**
     * 文件不存在会重新创建目录并创建文件,如果存在则不作操作,
     * 参数1:文件地址 例如 new File("d:/a.txt")
     */
    public static final void fileNew(File file) throws IOException {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
    }

    public static final String fileSaveResources(String file, String rootUrl) throws Exception {
        return fileSaveResources(new File(file), rootUrl);
    }

    /**
     * 文件保存到 某个目录下 会根据文件md5自动新建目录
     */
    public static final String fileSaveResources(File file, String rootUrl) throws Exception {
        String md5 = Tools.fileToMd5(file);
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        String page = rootUrl + "/";
        page += md5.substring(0, 2) + "/";
        page += md5.substring(2, 4) + "/";
        page += md5.substring(4, 6) + "/";
        new File(page).mkdirs();
        page += md5 + "." + suffix;
        File file2 = new File(page);
        if (file2.exists()) {
            return page;//重复文件!
        }
        Tools.fileSaveByte(file2, Tools.fileReadByte(file), false);
        return page;
    }

    /**
     * 文件不存在会重新创建目录并创建文件,如果存在则不作操作,
     * 参数1:文件地址 例如 "d:/a.txt"
     */
    public static final void fileNew(String file) throws IOException {
        fileNew(new File(file));
    }

    /**
     * 读取byte数据
     * 参数1:文件地址 例如 d:/a.txt
     *
     * @throws Exception
     */
    public static final byte[] fileReadByte(String file) throws Exception {
        return fileReadByte(new File(file));
    }

    /**
     * 读取byte数据
     * 参数1:文件地址 例如 new File("d:/a.txt")
     *
     * @throws Exception
     */
    public static final byte[] fileReadByte(File file) throws Exception {
        byte[] bt = null;
        BufferedInputStream bis = null;
        FileInputStream fis = null;
        try {
            if (file.exists()) {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                bt = new byte[(int) file.length()];
                bis.read(bt);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
                if (bis != null) {
                    bis.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (bis != null) {
                    bis.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
        return bt;
    }

    /**
     * 读取字符串数据
     * 参数1:文件地址 例如 new File("d:/a.txt")
     * 参数2:编码
     *
     * @throws Exception
     */
    public static final String fileReadString(File file, String encoding) throws Exception {
        return new String(fileReadByte(file), encoding);
    }

    /**
     * 读取字符串数据
     * 参数1:文件地址 例如 "d:/a.txt"
     * 参数2:编码
     *
     * @throws Exception
     */
    public static final String fileReadString(String file, String encoding) throws Exception {
        return fileReadString(new File(file), encoding);
    }

    /**
     * 读取字符串数据
     * 参数1:文件地址 例如 "d:/a.txt"
     * 默认编码为"UTF-8"
     *
     * @throws Exception
     */
    public static final String fileReadString(String file) throws Exception {
        return fileReadString(new File(file), "UTF-8");
    }

    /**
     * 读取字符串数据
     * 参数1:文件地址 例如 new File("d:/a.txt")
     * 默认编码为"UTF-8"
     *
     * @throws Exception
     */
    public static final String fileReadString(File file) throws Exception {
        return new String(fileReadByte(file), "UTF-8");
    }

    /**
     * 读取字符串数据 返回数组 一行一个
     * 参数1:文件地址 例如 new File("d:/a.txt")
     * 参数2:编码
     *
     * @throws Exception
     */
    public static final String[] fileReadArray(File file, String encoding) throws Exception {
        return new String(fileReadByte(file), encoding).split("\r\n");
    }

    /**
     * 读取字符串数据 返回数组 一行一个
     * 参数1:文件地址 例如 new File("d:/a.txt")
     * 参数2:编码
     *
     * @throws Exception
     */
    public static final String[] fileReadArray(String file, String encoding) throws Exception {
        return new String(fileReadByte(new File(file)), encoding).replaceAll("\r", "").split("\n");
    }

    /**
     * 读取字符串数据 返回数组 一行一个
     * 参数1:文件地址 例如 new File("d:/a.txt")
     * 默认编码为"UTF-8"
     *
     * @throws Exception
     */
    public static final String[] fileReadArray(File file) throws Exception {
        return new String(fileReadByte(file), "UTF-8").split("\r\n");
    }

    /**
     * 获取文件MD5
     * 获取文件MD5
     * 参数1:文件地址 例如 new File("d:/a.txt")
     *
     * @throws Exception
     */
    public static final String fileToMd5(File file) throws Exception {
        return toMd5(fileReadByte(file));
    }

    /**
     * 获取文本MD5
     * 参数1:文件地址 例如 "d:/a.txt"
     *
     * @throws Exception
     */
    public static final String fileToMd5(String file) throws Exception {
        return fileToMd5(new File(file));
    }

    /**
     * 获取目录下文件和目录名
     * 参数1:目录地址 例如 "d:/a.txt"
     * 参数2:retType 为1 返回全部 目录和文件名.为2返回文件名 为3返回目录名
     *
     * @throws Exception
     */
    public static final List<String> fileSubNames(File file, int retType) throws Exception {
        List<String> list = new ArrayList<String>();
        File[] files = file.listFiles();
        if (files == null) {
            return list;
        }
        for (File f : files) {
            if (retType == 2) {
                if (f.isFile()) {
                    list.add(f.getName());
                } else {
                    continue;
                }
            } else if (retType == 3) {
                if (f.isDirectory()) {
                    list.add(f.getName());
                } else {
                    continue;
                }
            } else {
                list.add(f.getName());
            }
        }
        return list;
    }

    public static final List<File> fileSub(String path, int retType) throws Exception {
        List<String> path_list = new ArrayList<String>();
        List<File> ret_list = new ArrayList<File>();
        path_list.add(path);
        while (path_list.size() > 0) {
            String path1 = path_list.remove(0);
            File file = new File(path1);
            List<String> list = fileSubNames(file, 2);
            for (String s : list) {
                if (retType == 2 || retType == 1) {
                    ret_list.add(new File(path1, s));
                }
            }
            list = fileSubNames(file, 3);
            for (String s : list) {
                if (retType == 3 || retType == 1) {
                    ret_list.add(new File(path1, s));
                }
                path_list.add(path1 + "/" + s);
            }
        }
        return ret_list;
    }

    //压缩 参数1 被压缩文件   参数2 压缩到文件
    public static final void fileZipEncoder(String fileUrl, String target) {
        ZipUtil.compress(fileUrl, target);
    }

    //压缩 参数1 被压缩目录   参数2 压缩到文件
    public static final void fileZipEncoderDir(String fileUrl, String target) {
        ZipUtil.compressDir(fileUrl, target);
    }

    //解压  参数1 压缩文件    参数2 解压到 目录
    public static final void fileZipDecoder(String fileUrl, String target) {
        ZipUtil.decompress(fileUrl, target);
    }
}


/**
 * java.util.zip压缩/解压文件
 * ZipUtil zip=new ZipUtil();
 * <p>
 * zip.compress("D:/厚街模板/文档/","D:/厚街模板/文档.zip");
 * zip.decompress("D:/厚街模板/文档.zip", "D:/厚街模板/文档2/");
 */
class ZipUtil {

    /**
     * 缓冲器大小
     */
    private static final int BUFFER = 512;


    /**
     * 得到源文件路径的所有文件
     *
     * @param dirFile 压缩源文件路径
     */
    public static List<File> getAllFile(File dirFile) {

        List<File> fileList = new ArrayList<File>();

        File[] files = dirFile.listFiles();
        for (File file : files) {//文件
            if (file.isFile()) {
                fileList.add(file);
                System.out.println("add file:" + file.getName());
            } else {//目录
                if (file.listFiles().length != 0) {//非空目录
                    fileList.addAll(getAllFile(file));//把递归文件加到fileList中
                } else {//空目录
                    fileList.add(file);
                    System.out.println("add empty dir:" + file.getName());
                }
            }
        }
        return fileList;
    }

    /**
     * 获取相对路径
     *
     * @param dirPath 源文件路径
     * @param file    准备压缩的单个文件
     */
    public static String getRelativePath(String dirPath, File file) {
        File dirFile = new File(dirPath);
        String relativePath = file.getName();

        while (true) {
            file = file.getParentFile();
            if (file == null) break;
            if (file.equals(dirFile)) {
                break;
            } else {
                relativePath = file.getName() + "/" + relativePath;
            }
        }
        return relativePath;
    }


    /**
     * @param destPath 解压目标路径
     * @param fileName 解压文件的相对路径
     */
    public static File createFile(String destPath, String fileName) {

        String[] dirs = fileName.split("/");//将文件名的各级目录分解
        File file = new File(destPath);

        if (dirs.length > 1) {//文件有上级目录
            for (int i = 0; i < dirs.length - 1; i++) {
                file = new File(file, dirs[i]);//依次创建文件对象知道文件的上一级目录
            }

            if (!file.exists()) {
                file.mkdirs();//文件对应目录若不存在，则创建
                try {
                    System.out.println("mkdirs: " + file.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            file = new File(file, dirs[dirs.length - 1]);//创建文件

            return file;
        } else {
            if (!file.exists()) {//若目标路径的目录不存在，则创建
                file.mkdirs();
                try {
                    System.out.println("mkdirs: " + file.getCanonicalPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            file = new File(file, dirs[0]);//创建文件

            return file;
        }

    }

    /**
     * 没有指定压缩目标路径进行压缩,用默认的路径进行压缩
     *
     * @param dirPath 压缩源文件路径
     */
    public static void compress(String dirPath) {

        int firstIndex = dirPath.indexOf("/");
        int lastIndex = dirPath.lastIndexOf("/");
        String zipFileName = dirPath.substring(0, firstIndex + 1) + dirPath.substring(lastIndex + 1);
        compress(dirPath, zipFileName);
    }

    /**
     * 压缩文件
     *
     * @param dirPath     压缩源文件路径
     * @param zipFileName 压缩目标文件路径
     */
    public static void compress(String dirPath, String zipFileName) {

        byte[] buffer = new byte[BUFFER];
        ZipEntry zipEntry = null;
        int readLength = 0;     //每次读取出来的长度

        try {
            // 对输出文件做CRC32校验
            CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(
                    zipFileName), new CRC32());
            ZipOutputStream zos = new ZipOutputStream(cos);
            File file = new File(dirPath);
            zipEntry = new ZipEntry(getRelativePath(dirPath, file));  //
            zipEntry.setSize(file.length());
            zipEntry.setTime(file.lastModified());
            zos.putNextEntry(zipEntry);

            InputStream is = new BufferedInputStream(new FileInputStream(file));

            while ((readLength = is.read(buffer, 0, BUFFER)) != -1) {
                zos.write(buffer, 0, readLength);
            }
            is.close();
            System.out.println("file compress:" + file.getCanonicalPath());
            zos.close();  //最后得关闭流，不然压缩最后一个文件会出错
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 压缩文件  目录
     *
     * @param dirPath     压缩源文件路径
     * @param zipFileName 压缩目标文件路径
     */
    public static void compressDir(String dirPath, String zipFileName) {

        File dirFile = new File(dirPath);
        List<File> fileList = getAllFile(dirFile);

        byte[] buffer = new byte[BUFFER];
        ZipEntry zipEntry = null;
        int readLength = 0;     //每次读取出来的长度

        try {
            // 对输出文件做CRC32校验
            CheckedOutputStream cos = new CheckedOutputStream(new FileOutputStream(
                    zipFileName), new CRC32());
            ZipOutputStream zos = new ZipOutputStream(cos);

            for (File file : fileList) {

                if (file.isFile()) {   //若是文件，则压缩文件

                    zipEntry = new ZipEntry(getRelativePath(dirPath, file));  //
                    zipEntry.setSize(file.length());
                    zipEntry.setTime(file.lastModified());
                    zos.putNextEntry(zipEntry);

                    InputStream is = new BufferedInputStream(new FileInputStream(file));

                    while ((readLength = is.read(buffer, 0, BUFFER)) != -1) {
                        zos.write(buffer, 0, readLength);
                    }
                    is.close();
                    System.out.println("file compress:" + file.getCanonicalPath());
                } else {     //若是空目录，则写入zip条目中

                    zipEntry = new ZipEntry(getRelativePath(dirPath, file));
                    zos.putNextEntry(zipEntry);
                    System.out.println("dir compress: " + file.getCanonicalPath() + "/");
                }
            }
            zos.close();  //最后得关闭流，不然压缩最后一个文件会出错
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 解压
     */
    public static void decompress(String zipFileName, String destPath) {

        try {

            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileName));
            ZipEntry zipEntry = null;
            byte[] buffer = new byte[BUFFER];//缓冲器
            int readLength = 0;//每次读出来的长度
            while ((zipEntry = zis.getNextEntry()) != null) {
                if (zipEntry.isDirectory()) {//若是目录
                    File file = new File(destPath + "/" + zipEntry.getName());
                    if (!file.exists()) {
                        file.mkdirs();
                        System.out.println("mkdirs:" + file.getCanonicalPath());
                        continue;
                    }
                }//若是文件
                File file = createFile(destPath, zipEntry.getName());
                System.out.println("file created: " + file.getCanonicalPath());
                OutputStream os = new FileOutputStream(file);
                while ((readLength = zis.read(buffer, 0, BUFFER)) != -1) {
                    os.write(buffer, 0, readLength);
                }
                os.close();
                System.out.println("file uncompressed: " + file.getCanonicalPath());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


