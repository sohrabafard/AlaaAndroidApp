package ir.sanatisharif.android.konkur96.model;

import java.util.List;

import ir.sanatisharif.android.konkur96.api.Models.ContentModel;
import ir.sanatisharif.android.konkur96.api.Models.MainBannerModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.api.Models.SetModel;

/**
 * Created by Mohamad on 10/6/2018.
 */

public class Block {

    private int                   id;
    private int                   type;
    private String                title;
    private boolean               offer;
    private int                   order;
    private String                url;
    private List<MainBannerModel> sliders;
    private List<SetModel>        sets;
    private List<ContentModel>    contents;
    private List<MainBannerModel> banners;
    private List<ProductModel>    products;
    private List<Video>           videos;

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

    public boolean isOffer() {
        return offer;
    }

    public void setOffer(boolean offer) {
        this.offer = offer;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<MainBannerModel> getSliders() {
        return sliders;
    }

    public void setSliders(List<MainBannerModel> sliders) {
        this.sliders = sliders;
    }

    public List<SetModel> getSets() {
        return sets;
    }

    public void setSets(List<SetModel> sets) {
        this.sets = sets;
    }

    public List<ContentModel> getContents() {
        return contents;
    }

    public void setContents(List<ContentModel> contents) {
        this.contents = contents;
    }

    public List<MainBannerModel> getBanners() {
        return banners;
    }

    public void setBanners(List<MainBannerModel> banners) {
        this.banners = banners;
    }

    public List<ProductModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductModel> products) {
        this.products = products;
    }

    public List<Video> getVideos() {
        return videos;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }
}
