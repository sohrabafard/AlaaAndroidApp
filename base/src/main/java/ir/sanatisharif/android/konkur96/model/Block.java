package ir.sanatisharif.android.konkur96.model;

import java.util.List;

import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.model.main_page.Banner;
import ir.sanatisharif.android.konkur96.model.main_page.Content;
import ir.sanatisharif.android.konkur96.model.main_page.MainBanner;
import ir.sanatisharif.android.konkur96.model.main_page.Set;

/**
 * Created by Mohamad on 10/6/2018.
 */

public class Block {

    private int id;
    private int type;
    private String title;
    private boolean offer;
    private int order;
    private String url;
    private List<MainBanner> sliders;
    private List<Set> sets;
    private List<Content> contents;
    private List<Banner> banners;
    private List<ProductModel> products;
    private List<Video> videos;

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

    public List<MainBanner> getSliders() {
        return sliders;
    }

    public void setSliders(List<MainBanner> sliders) {
        this.sliders = sliders;
    }

    public List<Set> getSets() {
        return sets;
    }

    public void setSets(List<Set> sets) {
        this.sets = sets;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public List<Banner> getBanners() {
        return banners;
    }

    public void setBanners(List<Banner> banners) {
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
