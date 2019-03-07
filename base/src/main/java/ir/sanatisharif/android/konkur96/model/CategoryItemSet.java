package ir.sanatisharif.android.konkur96.model;

import java.util.ArrayList;

/**
 * Created by Mohamad on 11/15/2018.
 */

public class CategoryItemSet extends Item {

    private String duration;
    private int contents_count;
    private String url;
    private String shortName;
    private Tags tags = new Tags();

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public int getContents_count() {
        return contents_count;
    }

    public void setContents_count(int contents_count) {
        this.contents_count = contents_count;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Tags getTags() {
        return tags;
    }

    public void setTags(Tags tags) {
        this.tags = tags;
    }
}
