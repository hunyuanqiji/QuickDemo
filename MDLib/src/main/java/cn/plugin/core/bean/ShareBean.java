package cn.plugin.core.bean;

/**
 * Created by yy on 2019/1/16.
 **/
public class ShareBean {

    public static final int TYPE_WEIXIN = 0;
    public static final int TYPE_WEIXIN_CIRCLE = 1;
    public static final int TYPE_WEIBO = 2;

    private String Name;
    private int IconRes;
    private int Type;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getIconRes() {
        return IconRes;
    }

    public void setIconRes(int iconRes) {
        IconRes = iconRes;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
