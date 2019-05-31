package ir.sanatisharif.android.konkur96.model;

import java.util.ArrayList;

public class MainBoughtItem {
    private int          id;
    private int          type;
    private String       title;
    private ArrayList<?> items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public ArrayList<?> getItems() {
        return items;
    }

    public void setItems(ArrayList<?> items) {
        this.items = items;
    }

}
