package ir.sanatisharif.android.konkur96.model;

import java.io.Serializable;

/**
 * Created by Mohamad on 11/15/2018.
 */

public class Item implements Serializable {

    private int id;
    private String title;
    private String imageUrl;
    private String author;
    private String view;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
