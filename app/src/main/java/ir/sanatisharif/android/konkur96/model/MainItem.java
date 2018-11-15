package ir.sanatisharif.android.konkur96.model;

import java.util.ArrayList;

/**
 * Created by Mohamad on 10/6/2018.
 */

public class MainItem {
    private int id;
    private int type;
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public ArrayList<?> getItems() {
        return items;
    }

    public void setItems(ArrayList<?> items) {
        this.items = items;
    }
}
