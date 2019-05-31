package ir.sanatisharif.android.konkur96.model;

import java.util.ArrayList;

public class MainShopItem {
    private int          id;
    private int          type;
    private String       title;
    private String       url;
    private boolean      more;
    private ArrayList<?> items;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isMore() {
        return more;
    }

    public void setMore(boolean more) {
        this.more = more;
    }

    public ArrayList<?> getItems() {
        return items;
    }

    public void setItems(ArrayList<?> items) {
        this.items = items;
    }
}
