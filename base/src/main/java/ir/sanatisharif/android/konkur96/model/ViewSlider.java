package ir.sanatisharif.android.konkur96.model;

/**
 * Created by Mohamad on 10/11/2018.
 */

public class ViewSlider {

    private int id;
    private int order;
    private String title;
    private String shortDescription;
    private String imgUrl;
    private String intentLink;
    private int kindOfIntent;

    public ViewSlider() {
    }

    public ViewSlider(String text, String imgUrl) {
        this.title = text;
        this.imgUrl = imgUrl;
    }

    public ViewSlider(String text, String imgUrl, String intentUrl, int kindOfIntent) {
        this.title = text;
        this.imgUrl = imgUrl;
        this.intentLink = intentUrl;
        this.kindOfIntent = kindOfIntent;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIntentLink() {
        return intentLink;
    }

    public void setIntentLink(String intentLink) {
        this.intentLink = intentLink;
    }

    public int getKindOfIntent() {
        return kindOfIntent;
    }

    public void setKindOfIntent(int kindOfIntent) {
        this.kindOfIntent = kindOfIntent;
    }
}
