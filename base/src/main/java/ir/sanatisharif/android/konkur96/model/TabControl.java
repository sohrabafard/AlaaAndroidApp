package ir.sanatisharif.android.konkur96.model;

/**
 * Created by Mohamad on 2/26/2019.
 */

public class TabControl {

    private int     id;
    private String  name;
    private String  title;
    private int     icon;
    private boolean show;

    public TabControl() {

    }

    public TabControl(int id, String name, String title, boolean show) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.show = show;
    }

    public TabControl(int id, String name, String title, int icon, boolean show) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.icon = icon;
        this.show = show;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public TabControl setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public TabControl setName(String name) {
        this.name = name;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public TabControl setTitle(String title) {
        this.title = title;
        return this;
    }

    public boolean isShow() {
        return show;
    }

    public TabControl setShow(boolean show) {
        this.show = show;
        return this;
    }
}
