package ir.sanatisharif.android.konkur96.model;

public class ProductSliderModel {

    private String text;
    private String imgUrl;
    private int    id;

    public ProductSliderModel() {
    }

    public ProductSliderModel(String text, String imgUrl) {
        this.text = text;
        this.imgUrl = imgUrl;
    }

    public ProductSliderModel(String text, String imgUrl, int id) {
        this.text = text;
        this.imgUrl = imgUrl;
        this.id = id;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
