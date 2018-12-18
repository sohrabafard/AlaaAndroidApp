package ir.sanatisharif.android.konkur96.model;

/**
 * Created by Mohamad on 10/11/2018.
 */

public class ViewSlider {

    private String text;
    private String imgUrl;
    private String intentUrl;
    private int kindOfIntent;

    public ViewSlider() {}

    public ViewSlider(String text, String imgUrl) {
        this.text = text;
        this.imgUrl = imgUrl;
    }

    public ViewSlider(String text, String imgUrl, String intentUrl, int kindOfIntent) {
        this.text = text;
        this.imgUrl = imgUrl;
        this.intentUrl = intentUrl;
        this.kindOfIntent = kindOfIntent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIntentUrl() {
        return intentUrl;
    }

    public void setIntentUrl(String intentUrl) {
        this.intentUrl = intentUrl;
    }

    public int getKindOfIntent() {
        return kindOfIntent;
    }

    public void setKindOfIntent(int kindOfIntent) {
        this.kindOfIntent = kindOfIntent;
    }
}
