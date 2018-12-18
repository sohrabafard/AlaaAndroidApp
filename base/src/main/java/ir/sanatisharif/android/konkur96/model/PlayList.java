package ir.sanatisharif.android.konkur96.model;

import java.util.ArrayList;

/**
 * Created by Mohamad on 11/19/2018.
 */

public class PlayList extends Item {

    private ArrayList<DownloadUrl> downloadUrls= new ArrayList<>();

    public ArrayList<DownloadUrl> getDownloadUrls() {
        return downloadUrls;
    }

    public void setDownloadUrls(ArrayList<DownloadUrl> downloadUrls) {
        this.downloadUrls = downloadUrls;
    }
}
