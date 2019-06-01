package ir.sanatisharif.android.konkur96.utils;

import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import ir.sanatisharif.android.konkur96.api.Models.AddToCardModel;
import ir.sanatisharif.android.konkur96.api.Models.AttributeModel;
import ir.sanatisharif.android.konkur96.api.Models.BlockDataModel;
import ir.sanatisharif.android.konkur96.api.Models.CardReviewModel;
import ir.sanatisharif.android.konkur96.api.Models.MainModel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.api.Models.TypeModel;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.model.DownloadUrl;
import ir.sanatisharif.android.konkur96.model.FileDiskModel;
import ir.sanatisharif.android.konkur96.model.MainAttrType;
import ir.sanatisharif.android.konkur96.model.MainShopItem;
import ir.sanatisharif.android.konkur96.model.ProductType;
import ir.sanatisharif.android.konkur96.model.SelectableProduct;

public class ShopUtils {
    
    public static String formatPrice(long number) {
        
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }
    
    public static String formatPrice(int number) {
        
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        return formatter.format(number);
    }
    
    
    public static ArrayList<MainShopItem> convertToMainShopModel(MainModel apiModel, boolean first) {
        
        ArrayList<MainShopItem>   items     = new ArrayList<>();
        ArrayList<BlockDataModel> blockData = apiModel.getBlock().getData();
        MainShopItem              item;
        
        
        if (apiModel.getMainBanner() != null && first) {
            
            //---------------------- slider ----------------------------------------------------
            item = new MainShopItem();
            item.setId(0);
            item.setType(AppConstants.SHOP_SLIDER_ITEM);
            item.setItems(apiModel.getMainBanner());
            items.add(item);
        }
        
        for (int i = 0; i < blockData.size(); i++) {
            
            BlockDataModel temp = blockData.get(i);
            
            if (temp.getProducts() != null && temp.getProducts().size() > 0) {
                //---------------------- block products ----------------------------------------------------
                if (temp.isOffer()) {
                    item = new MainShopItem();
                    item.setId(i);
                    item.setType(AppConstants.INCREDIBLEOFFER_ITEM_SET);
                    item.setTitle(temp.getTitle());
                    item.setItems(temp.getProducts());
                    if (temp.getUrl() != null) {
                        
                        item.setMore(true);
                        item.setUrl(temp.getUrl());
                        
                    } else {
                        
                        item.setMore(false);
                    }
                    items.add(item);
                } else {
                    
                    item = new MainShopItem();
                    item.setId(i);
                    item.setType(AppConstants.CATEGORY_SHOP_ITEM_SET);
                    item.setTitle(temp.getTitle());
                    item.setItems(temp.getProducts());
                    if (temp.getUrl() != null) {
                        
                        item.setMore(true);
                        item.setUrl(temp.getUrl());
                        
                    } else {
                        
                        item.setMore(false);
                    }
                    items.add(item);
                }
                
            }
            
            if (temp.getBanners() != null && temp.getBanners().size() > 0) {
                //---------------------- block banners ----------------------------------------------------
                item = new MainShopItem();
                item.setId(i);
                item.setType(AppConstants.SHOP_BANNER_ITEM);
                item.setTitle(temp.getTitle());
                item.setItems(temp.getBanners());
                items.add(item);
            }
        }
        
        return items;
    }
    
    public static ArrayList<AddToCardModel> convertToAddToCardModelList(CardReviewModel cardReviewModel) {
        
        ArrayList<AddToCardModel> items = new ArrayList<>();
        
        
        for (int i = 0; i < cardReviewModel.getItems().size(); i++) {
            
            items.addAll(cardReviewModel.getItems().get(i).getOrderproducts());
        }
        
        
        return items;
    }
    
    public static String getProductNames(CardReviewModel cardReviewModel) {
        
        ArrayList<AddToCardModel> items      = new ArrayList<>();
        String                    finalNames = "";
        
        
        for (int i = 0; i < cardReviewModel.getItems().size(); i++) {
            
            items.addAll(cardReviewModel.getItems().get(i).getOrderproducts());
        }
        
        for (int i = 0; i < items.size(); i++) {
            
            AddToCardModel temp = items.get(i);
            
            finalNames += temp.getProduct().getName() + " , ";
        }
        
        
        return finalNames;
    }
    
    public static ArrayList<SelectableProduct> convertToSelectableProductModel(ArrayList<ProductModel> list) {
        
        ArrayList<SelectableProduct> items = new ArrayList<>();
        
        if (list != null && list.size() > 0) {
            
            for (int i = 0; i < list.size(); i++) {
                
                ProductModel temp = list.get(i);
                
                items.add(new SelectableProduct(temp, false, null, null));
            }
            
            
            return items;
            
        } else {
            
            return null;
        }
        
        
    }
    
    public static Spanned setHTMLText(String text) {
        
        if (null != text) {
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
            } else {
                return Html.fromHtml(text);
            }
        } else {
            
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                return Html.fromHtml(" ", Html.FROM_HTML_MODE_COMPACT);
            } else {
                return Html.fromHtml(" ");
            }
        }
        
        
    }
    
    public static FileDiskModel createVideoModelByURL(String url) {
        
        
        ArrayList<DownloadUrl> downloadUrls = new ArrayList<>();
        
        DownloadUrl downloadUrl = new DownloadUrl();
        downloadUrl.setUrl(url);
        downloadUrls.add(downloadUrl);
        
        FileDiskModel item = new FileDiskModel();
        // item.setDownloadUrls(downloadUrls);
        
        
        return item;
    }
    
    public static ProductType getType(TypeModel typeModel) {
        
        if (typeModel != null) {
            
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
            
        } else {
            
            return null;
        }
        
    }
    
    
    public static MainAttrType getMainAttrType(AttributeModel attributeModel) {
        
        if (attributeModel != null) {
            
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
            
        } else {
            
            return null;
        }
        
    }
    
    public static List<Integer> removeElements(List<Integer> input, int deleteMe) {
        List<Integer> result = new LinkedList<>();
        
        for (int item : input)
            if (deleteMe != item)
                result.add(item);
        
        return result;
    }
    
    
}
