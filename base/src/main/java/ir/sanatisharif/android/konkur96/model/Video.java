package ir.sanatisharif.android.konkur96.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Mohamad on 10/30/2018.
 */

public class Video implements Serializable{

    private int id;
    private String name;
    private String path;
    private String duration;
    private String size;
    private boolean checked;
    private ArrayList<DownloadUrl> downloadUrls=new ArrayList<>();

    public Video() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public ArrayList<DownloadUrl> getDownloadUrls() {
        return downloadUrls;
    }

    public void setDownloadUrls(ArrayList<DownloadUrl> downloadUrls) {
        this.downloadUrls = downloadUrls;
    }
}
