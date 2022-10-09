package gzb.tools.groovy;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyObject;
import gzb.tools.Tools;
import gzb.tools.config.StaticClasses;
import gzb.tools.entity.GroovyLoadV2Entity;
import gzb.tools.entity.GroovyReturnEntity;
import gzb.tools.log.ColorEnum;
import gzb.tools.thread.GzbThread;
import gzb.tools.thread.ThreadPool;
import java.lang.reflect.Method;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroovyLoadV2 {
    public static Map<String, GroovyLoadV2Entity> groovyClassMap = new HashMap<>();
    public static Map<String, String> fileName_webPath = new HashMap<>();
    private static GroovyObject object;
    private static String groovyPackage;

    static {
        init();
    }



    public static final void init() {
        try {
             groovyPackage = Tools.configGetString("gzb.groovyPackage", "defVal");
            groovyPackage=groovyPackage.replaceAll("\\\\","/");
            String[] ss1 = groovyPackage.split(",");
            for (String folder : ss1) {
                loadFolderNotRepeat(folder);
            }
            if (StaticClasses.groovyLoadType.equals("file")) {
                ThreadPool.start(new GzbThread() {
                    @Override
                    public void start() throws Exception {
                        while (true) {
                            try {
                                for (String folder : ss1) {
                                    List<File> list = Tools.fileSub(folder, 2);
                                    while (list.size() > 0) {
                                        File file = list.remove(list.size() - 1);
                                        if (file.getName().indexOf(".java") > -1 || file.getName().indexOf(".groovy") > -1) {
                                            String webPath = fileName_webPath.get(file.getParent() + "/" + file.getName());
                                            if (webPath == null) {
                                                loadFile(file);
                                            } else {
                                                GroovyLoadV2Entity groovyLoadV2Entity = groovyClassMap.get(webPath);
                                                if (groovyLoadV2Entity.updateTime != file.lastModified()) {
                                                    loadFile(file);
                                                }
                                            }
                                        }
                                    }
                                }
                                //fileName_webPath.put(file.getParent()+"/"+file.getName(),webPath.toString());
                                Thread.sleep(1000);
                            } catch (Exception e) {
                                e.printStackTrace();
                                break;
                            }
                        }
                    }
                }, "GroovyLoadV2", true);
            } else if (StaticClasses.groovyLoadType.equals("http")) {
                String urls = Tools.configGetString("gzb.groovy.load.urls", "");
                String[] arr1 = urls.split(",");
                for (int i = 0; i < arr1.length; i++) {
                    loadNet(arr1[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadFile(File file) throws Exception {
        GroovyObject object;
        GroovyLoadV2Entity entity;
        GroovyClassLoader classLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader());
        Class testGroovyClass = classLoader.parseClass(new GroovyCodeSource(file));

        Object webPath = null;
        Map<String, List<String>> parameterName = new HashMap<>();
        Map<String, Class[]> parameterType = new HashMap<>();

        Method[] methods = testGroovyClass.getMethods();
        object = (GroovyObject) testGroovyClass.getDeclaredConstructor().newInstance();
        try {
            webPath = object.getProperty("webPath");
        } catch (Exception e) {
            int t=1;
            if (groovyPackage.substring(groovyPackage.length()-1,groovyPackage.length()).equals("/")){
                t=2;
            }
            String []ss1=groovyPackage.split("/");
            String []ss2=testGroovyClass.getName().split(ss1[ss1.length-t]);
            webPath = ss2[ss2.length-t].replaceAll("\\.","/");
        }
        if (webPath.toString().substring(0,1).equals("/")==false){
            webPath="/"+webPath;
        }
        fileName_webPath.put(file.getParent() + "/" + file.getName(), webPath.toString());
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.equals("main")
                    || methodName.equals("getMetaClass")
                    || methodName.equals("setMetaClass")
                    || methodName.indexOf("$") > -1
                    || methodName.equals("wait")
                    || methodName.equals("equals")
                    || methodName.equals("toString")
                    || methodName.equals("hashCode")
                    || methodName.equals("getClass")
                    || methodName.equals("notify")
                    || methodName.equals("notifyAll")
                    || methodName.equals("getProperty")
                    || methodName.equals("setProperty")
                    || methodName.equals("invokeMethod")
                    || methodName.substring(0, 3).equals("get")
                    || methodName.substring(0, 3).equals("set")

            ) {
                //System.out.println("shield:" + webPath.toString() + "/" + methodName);
                continue;
            }
            try {
                //List<String> param = AsmMethods.getParamNamesByAsm(method);//出现bug  改为从源码获取
                List<String> param = getMethodParameterNames(file, method.getName(), method.getParameterTypes());
                System.out.print(ColorEnum.getColor(2)+"servlet:" + webPath.toString() + "/" + method.getName());
                if (param != null && param.size() > 0) {
                    parameterName.put(method.getName(), param);
                    parameterType.put(method.getName(), method.getParameterTypes());
                    System.out.print(param);
                }
            } catch (Exception e) {
            }
            System.out.println();
        }
        entity = new GroovyLoadV2Entity(file.getParent() + "/" + file.getName(), testGroovyClass, file.lastModified(), parameterName, parameterType);
        System.out.println("载入代码：" + file.getParent() + "/" + file.getName());
        groovyClassMap.put(webPath.toString(), entity);
    }
    public static void loadFileNotRepeat(File file) throws Exception {
        GroovyObject object;
        GroovyLoadV2Entity entity;
        GroovyClassLoader classLoader = new GroovyClassLoader(Thread.currentThread().getContextClassLoader());
        Class testGroovyClass = classLoader.parseClass(new GroovyCodeSource(file));

        Object webPath = null;
        Map<String, List<String>> parameterName = new HashMap<>();
        Map<String, Class[]> parameterType = new HashMap<>();

        Method[] methods = testGroovyClass.getMethods();
        object = (GroovyObject) testGroovyClass.getDeclaredConstructor().newInstance();
        try {
            webPath = object.getProperty("webPath");
        } catch (Exception e) {
            int t=1;
            if (groovyPackage.substring(groovyPackage.length()-1,groovyPackage.length()).equals("/")){
              t=2;
            }
            String []ss1=groovyPackage.split("/");
            String []ss2=testGroovyClass.getName().split(ss1[ss1.length-t]);
            webPath = ss2[ss2.length-t].replaceAll("\\.","/");
        }
        if (webPath.toString().substring(0,1).equals("/")==false){
            webPath="/"+webPath;
        }
        if (groovyClassMap.get(webPath.toString())!=null){
            System.out.println(ColorEnum.getColor(5)+"终止运行，servlet(重复)类:" + testGroovyClass.getSimpleName());
            throw  new Exception();
        }
        fileName_webPath.put(file.getParent() + "/" + file.getName(), webPath.toString());
        for (Method method : methods) {
            String methodName = method.getName();
            if (methodName.equals("main")
                    || methodName.equals("getMetaClass")
                    || methodName.equals("setMetaClass")
                    || methodName.indexOf("$") > -1
                    || methodName.equals("wait")
                    || methodName.equals("equals")
                    || methodName.equals("toString")
                    || methodName.equals("hashCode")
                    || methodName.equals("getClass")
                    || methodName.equals("notify")
                    || methodName.equals("notifyAll")
                    || methodName.equals("getProperty")
                    || methodName.equals("setProperty")
                    || methodName.equals("invokeMethod")
                    || methodName.substring(0, 3).equals("get")
                    || methodName.substring(0, 3).equals("set")

            ) {
                //System.out.println("shield:" + webPath.toString() + "/" + methodName);
                continue;
            }
            try {
                //List<String> param = AsmMethods.getParamNamesByAsm(method);//出现bug  改为从源码获取
                List<String> param = getMethodParameterNames(file, method.getName(), method.getParameterTypes());
                System.out.print(ColorEnum.getColor(2)+"servlet:" + webPath.toString() + "/" + method.getName());
                if (param != null && param.size() > 0) {
                    parameterName.put(method.getName(), param);
                    parameterType.put(method.getName(), method.getParameterTypes());
                    System.out.print(param);
                }
            } catch (Exception e) {
            }
            System.out.println();
        }
        entity = new GroovyLoadV2Entity(file.getParent() + "/" + file.getName(), testGroovyClass, file.lastModified(), parameterName, parameterType);
        System.out.println("载入代码：" + file.getParent() + "/" + file.getName());
        groovyClassMap.put(webPath.toString(), entity);
    }

    public static List<String> getMethodParameterNames(File file, String name, Class<?>[] classes) throws Exception {
        String allCode = Tools.fileReadString(file);
        List<String> list = new ArrayList<>();

        while (allCode.indexOf("  ")>-1){
            allCode=allCode.replaceAll("  ", " ");
        }
        while (allCode.indexOf(", ")>-1){
            allCode=allCode.replaceAll(", ", ",");
        }
        allCode=allCode
                .replaceAll("\t", "")
                .replaceAll("\r\n", "")
                .replaceAll("\r", "")
                .replaceAll("\n", "")
                .replaceAll("  ", " ")
                .replaceAll(", ", ",")
                .replaceAll(name + "\\(", name + " (")
                .replaceAll(" \\)", ")");
        List<String> list1 = Tools.textMid(allCode, name + " (", ")");
        for (int i = 0; i < list1.size(); i++) {
            String[] ss1 = list1.get(i).split(",");
            if (ss1.length + 0 == classes.length + 0) {
                int len = 0;
                for (int i1 = 0; i1 < classes.length; i1++) {
                    String[] ss2 = ss1[i1].split(" ");
                    if (ss2.length > 1) {
                        if (classes[i1].getName().indexOf(ss2[0]) > -1) {
                            len++;
                        }
                    }
                }
                if (len+0==classes.length+0){
                    for (int i1 = 0; i1 < ss1.length; i1++) {
                        String[] ss2 = ss1[i1].split(" ");
                        if (ss2.length > 1) {
                            list.add(ss2[1]);
                        }
                    }
                }
                return list;
            }

        }

        return list;
    }

    public static void loadFolder(String folder) throws Exception {
        List<File> list = Tools.fileSub(folder, 2);
        while (list.size() > 0) {
            File file = list.remove(list.size() - 1);
            if (file.getName().indexOf(".java") > -1 || file.getName().indexOf(".groovy") > -1) {
                loadFile(file);
            }
        }
    }
    public static void loadFolderNotRepeat(String folder) throws Exception {
        List<File> list = Tools.fileSub(folder, 2);
        while (list.size() > 0) {
            File file = list.remove(list.size() - 1);
            if (file.getName().indexOf(".java") > -1 || file.getName().indexOf(".groovy") > -1) {
                loadFileNotRepeat(file);
            }
        }
    }

    public static void loadNet(String httpUrl) throws Exception {

    }

    public static GroovyReturnEntity newObject(String className) throws Exception {
        GroovyReturnEntity groovyReturnEntity = new GroovyReturnEntity();
        groovyReturnEntity.entity = groovyClassMap.get(className);
        if (groovyReturnEntity.entity == null) {
            return null;
        }
        groovyReturnEntity.object = (GroovyObject) groovyReturnEntity.entity.aClass.getDeclaredConstructor().newInstance();
        return groovyReturnEntity;
    }

    public static Object call(GroovyReturnEntity groovyReturnEntity, String name, Map<String, Object[]> parameter){
        GroovyObject object = groovyReturnEntity.object;
        GroovyLoadV2Entity entity = groovyReturnEntity.entity;
        List<String> list_name;
        Class[] classes;
        if (entity == null) {
            return null;
        }

        if (name.equals("main")
                || name.equals("getMetaClass")
                || name.equals("setMetaClass")
                || name.indexOf("$") > -1
                || name.equals("wait")
                || name.equals("equals")
                || name.equals("toString")
                || name.equals("hashCode")
                || name.equals("getClass")
                || name.equals("notify")
                || name.equals("notifyAll")
                || name.equals("getProperty")
                || name.equals("setProperty")
                || name.equals("invokeMethod")
                || name.equals("find")
                || name.substring(0, 3).equals("get")
                || name.substring(0, 3).equals("set")

        ) {
            return Tools.jsonFail("API:404");
        }

        if (entity.parameterName == null) {
            list_name = new ArrayList<>();
        } else {
            list_name = entity.parameterName.get(name);
        }
        if (entity.parameterType == null) {
            classes = new Class[0];
        } else {
            classes = entity.parameterType.get(name);
        }
        List<Object> listPara = new ArrayList<>();
        if (list_name == null) {
            list_name = new ArrayList<>();
        }
        for (int i = 0; i < list_name.size(); i++) {
            Object[] o1 = null;
            if (parameter != null) {
                o1 = parameter.get(list_name.get(i));
            }
            if (o1 == null || o1.length < 1) {
                listPara.add(null);
            } else {
                if (classes[i].getName().equals("[I")) {
                    int[] arr = new int[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Integer.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[S")) {
                    short[] arr = new short[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Short.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[J")) {
                    long[] arr = new long[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Long.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[F")) {
                    float[] arr = new float[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Float.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[Z")) {
                    boolean[] arr = new boolean[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Boolean.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[D")) {
                    double[] arr = new double[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Double.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[C")) {
                    char[] arr = new char[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = o1[k].toString().toCharArray()[0];
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[B")) {
                    byte[] arr = new byte[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Byte.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[Ljava.lang.Integer;")) {
                    Integer[] arr = new Integer[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Integer.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[Ljava.lang.Short;")) {
                    Short[] arr = new Short[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Short.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[Ljava.lang.Long;")) {
                    Long[] arr = new Long[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Long.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[Ljava.lang.Float;")) {
                    Float[] arr = new Float[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Float.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[Ljava.lang.Boolean;")) {
                    Boolean[] arr = new Boolean[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Boolean.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[Ljava.lang.Double;")) {
                    Double[] arr = new Double[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Double.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[Ljava.lang.Character;")) {
                    Character[] arr = new Character[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Character.valueOf(o1[k].toString().toCharArray()[0]);
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[Ljava.lang.Byte;")) {
                    Byte[] arr = new Byte[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = Byte.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[Ljava.lang.String;")) {
                    String[] arr = new String[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = String.valueOf(o1[k].toString());
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("[Ljava.io.File;")) {
                    File[] arr = new File[o1.length];
                    for (int k = 0; k < o1.length; k++) {
                        arr[k] = (File) o1[k];
                    }
                    listPara.add(arr);
                } else if (classes[i].getName().equals("java.io.File")) {
                    listPara.add((File) o1[0]);
                } else if (classes[i].getName().equals("double")) {
                    listPara.add(Double.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("int")) {
                    listPara.add(Integer.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("byte")) {
                    listPara.add(Byte.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("short")) {
                    listPara.add(Short.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("long")) {
                    listPara.add(Long.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("float")) {
                    listPara.add(Float.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("boolean")) {
                    listPara.add(Boolean.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("char")) {
                    listPara.add(o1[0].toString().toCharArray()[0]);
                } else if (classes[i].getName().equals("java.lang.String")) {
                    listPara.add(String.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("java.lang.Integer")) {
                    listPara.add(Integer.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("java.lang.Double")) {
                    listPara.add(Double.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("java.lang.Boolean")) {
                    listPara.add(Boolean.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("java.lang.Float")) {
                    listPara.add(Float.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("java.lang.Long")) {
                    listPara.add(Long.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("java.lang.Short")) {
                    listPara.add(Short.valueOf(o1[0].toString()));
                } else if (classes[i].getName().equals("java.lang.Character")) {
                    listPara.add(Character.valueOf(o1[0].toString().toCharArray()[0]));
                } else if (classes[i].getName().equals("java.lang.Byte")) {
                    listPara.add(Byte.valueOf(o1[0].toString()));
                } else {
                    listPara.add(null);
                }

            }
        }
        return object.invokeMethod(name, listPara.toArray());
    }

    public static Object getVariable(GroovyReturnEntity groovyReturnEntity, String name) throws Exception {
        GroovyObject object = groovyReturnEntity.object;
        GroovyLoadV2Entity entity = groovyReturnEntity.entity;
        try {
            return object.getProperty(name);
        } catch (Exception e) {
            return null;
        }
    }

    public static Object setVariable(GroovyReturnEntity groovyReturnEntity, String name, Object value) throws Exception {
        GroovyObject object = groovyReturnEntity.object;
        GroovyLoadV2Entity entity = groovyReturnEntity.entity;
        try {
            object.setProperty(name, value);
            return value;
        } catch (Exception e) {
            return null;
        }
    }


}
