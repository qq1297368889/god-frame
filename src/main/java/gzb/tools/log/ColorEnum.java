package gzb.tools.log;
public enum ColorEnum {
    /**
     * 白色
     */
    WHITE("\33[0m",0),
    /**
     * //红色
     */
    RED("\33[1m\33[31m", 1),
    /**
     * //绿色
     */
    GREEN("\33[1m\33[32m", 2),
    /**
     * //黄色
     */
    YELLOW("\33[1m\33[33m", 3),
    /**
     * //蓝色
     */
    BLUE("\33[1m\33[34m", 4),
    /**
     * //粉色
     */
    PINK("\33[1m\33[35m",5),
    /**
     * //青色
     */
    CYAN("\33[1m\33[36m",6);
    // 成员变量
    private String name;
    private int index;
    // 构造方法
    private ColorEnum(String name, int index) {
        this.name = name;
        this.index = index;
    }
    // 普通方法
    public static String getColor(int index) {
        for (ColorEnum c : ColorEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    // get set 方法
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getIndex() {
        return index;
    }
    public void setIndex(int index) {
        this.index = index;
    }
}


