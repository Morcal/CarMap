package cn.go.lyqdh.carmap.modle;

/**
 * Created by lyqdh on 2016/1/10.
 */
public class LeftMenu {
    private String title;
    private int icon;

    public LeftMenu(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}
