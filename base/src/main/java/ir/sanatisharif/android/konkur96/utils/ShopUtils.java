package ir.sanatisharif.android.konkur96.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.api.Models.MainDataModel;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.model.MainShopItem;

public class ShopUtils {

    public static String formatPrice(long number){

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }


    public static ArrayList<MainShopItem> convertToMainShopModel(MainModel apiModel){

        ArrayList<MainShopItem> items = new ArrayList<>();
        ArrayList<MainDataModel> blockData = apiModel.getBlock().getData();
        MainShopItem item;


        if (apiModel.getMainBanner() != null){

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

                item = new MainShopItem();
                item.setId(i);
                item.setType(AppConstants.CATEGORY_SHOP_ITEM_SET);
                item.setTitle(temp.getTitle());
                item.setItems(temp.getProducts());
                items.add(item);
            }

            if (temp.getBanners() != null && temp.getBanners().size() > 0){

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
}
