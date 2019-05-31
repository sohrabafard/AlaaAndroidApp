package ir.sanatisharif.android.konkur96.model;

/**
 * Created by Mohamad on 10/6/2018.
 */

public class BannerItem {

    private int    id;
    private String title;
    private String url;
    private int    intent;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIntent() {
        return intent;
    }

    public void setIntent(int intent) {
        this.intent = intent;
    }
}
