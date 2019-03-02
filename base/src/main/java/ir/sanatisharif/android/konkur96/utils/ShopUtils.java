package ir.sanatisharif.android.konkur96.utils;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import ir.sanatisharif.android.konkur96.api.Models.AttributeModel;
import ir.sanatisharif.android.konkur96.api.Models.MainDataModel;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductPhotoModel;
import ir.sanatisharif.android.konkur96.api.Models.TypeModel;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.model.DownloadUrl;
import ir.sanatisharif.android.konkur96.model.MainAttrType;
import ir.sanatisharif.android.konkur96.model.MainShopItem;
import ir.sanatisharif.android.konkur96.model.ProductSliderModel;
import ir.sanatisharif.android.konkur96.model.ProductType;
import ir.sanatisharif.android.konkur96.model.Video;

public class ShopUtils {

    public static String formatPrice(long number){

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }

    public static String formatPrice(int number){

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }


    public static ArrayList<MainShopItem> convertToMainShopModel(MainModel apiModel, boolean first){

        ArrayList<MainShopItem> items = new ArrayList<>();
        ArrayList<MainDataModel> blockData = apiModel.getBlock().getData();
        MainShopItem item;


        if (apiModel.getMainBanner() != null && first){

            //---------------------- slider ----------------------------------------------------
            item = new MainShopItem();
            item.setId(0);
            item.setType(AppConstants.SHOP_SLIDER_ITEM);
            item.setItems(apiModel.getMainBanner());
            items.add(item);
        }

        for (int i = 0 ; i < blockData.size(); i++){

            MainDataModel temp = blockData.get(i);

            if (temp.getProducts() != null && temp.getProducts().size() > 0){
                //---------------------- block products ----------------------------------------------------
                if (temp.isOffer()){
                    item = new MainShopItem();
                    item.setId(i);
                    item.setType(AppConstants.INCREDIBLEOFFER_ITEM_SET);
                    item.setTitle(temp.getTitle());
                    item.setItems(temp.getProducts());
                    if (temp.getUrl() != null){

                        item.setMore(true);
                        item.setUrl(temp.getUrl());

                    }else {

                        item.setMore(false);
                    }
                    items.add(item);
                }else {

                    item = new MainShopItem();
                    item.setId(i);
                    item.setType(AppConstants.CATEGORY_SHOP_ITEM_SET);
                    item.setTitle(temp.getTitle());
                    item.setItems(temp.getProducts());
                    if (temp.getUrl() != null){

                        item.setMore(true);
                        item.setUrl(temp.getUrl());

                    }else {

                        item.setMore(false);
                    }
                    items.add(item);
                }

            }

            if (temp.getBanners() != null && temp.getBanners().size() > 0){
                //---------------------- block banners ----------------------------------------------------
                item = new MainShopItem();
                item.setId(i);
                item.setType(AppConstants.SHOP_BANNER_ITEM);
                item.setTitle(temp.getTitle());
                item.setItems(temp.getBanners());
                items.add(item);
            }
        }

        return  items;
    }


    public static ArrayList<ProductSliderModel> convertToProductSliderModel(ArrayList<ProductPhotoModel> photos){

        ArrayList<ProductSliderModel> items = new ArrayList<>();

        for (int i = 0 ; i < photos.size(); i++){

            ProductPhotoModel temp = photos.get(i);

            items.add(new ProductSliderModel(temp.getTitle(), temp.getUrl(), i));
        }


        return items;
    }

    public static Spanned setHTMLText(String text){

        if (null != text){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
            } else {
                return Html.fromHtml(text);
            }
        }else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(" ", Html.FROM_HTML_MODE_COMPACT);
            } else {
                return Html.fromHtml(" ");
            }
        }


    }

    public static Video createVideoModelByURL(String url){


        ArrayList<DownloadUrl> downloadUrls = new ArrayList<>();

        DownloadUrl downloadUrl = new DownloadUrl();
        downloadUrl.setUrl(url);
        downloadUrls.add(downloadUrl);

        Video item = new Video();
        item.setDownloadUrls(downloadUrls);


        return item;
    }

    public static ProductType getType(TypeModel typeModel){

        if (typeModel != null){

            switch (typeModel.getType().trim()) {
                case "simple":

                    return ProductType.SIMPLE;

                case "selectable":

                    return ProductType.SELECTABLE;

                case "configurable":

                    return ProductType.CONFIGURABLE;

                default:

                    return null;
            }

        }else {

            return null;
        }

    }


    public static MainAttrType getMainAttrType(AttributeModel attributeModel){

        if (attributeModel != null){

            switch (attributeModel.getControl().trim()) {
                case "simple":

                    return MainAttrType.SIMPLE;

                case "checkBox":

                    return MainAttrType.CHECKBOX;

                case "dropDown":

                    return MainAttrType.DROPDOWN;

                default:

                    return null;
            }

        }else {

            return null;
        }

    }
}
