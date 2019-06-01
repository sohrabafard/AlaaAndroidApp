package ir.sanatisharif.android.konkur96.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.activity.GalleryFullView;
import ir.sanatisharif.android.konkur96.api.Models.ProductPhotoModel;
import ir.sanatisharif.android.konkur96.model.FullScreenModel;

public class GalleryWorker {
    
    private final String                       TAG_MODEL = "TAG_MODEL";
    private       Context                      context;
    private       ArrayList<ProductPhotoModel> images;
    
    
    public GalleryWorker(Context context) {
        this.context = context;
    }
    
    
    public void openFullView(int position) {
        
        FullScreenModel fullScreenModel = new FullScreenModel(position, images);
        
        if (getImages() != null && getImages().size() != 0) {
            Intent showFullView = new Intent(context, GalleryFullView.class);
            Bundle bundle       = new Bundle();
            bundle.putParcelable(TAG_MODEL, fullScreenModel);
            showFullView.putExtras(bundle);
            showFullView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(showFullView);
        } else {
            Toast.makeText(context, "تصویری برای نمایش وجود ندارد", Toast.LENGTH_LONG).show();
        }
    }
    
    public ArrayList<ProductPhotoModel> getImages() {
        return images;
    }
    
    public void setImages(ArrayList<ProductPhotoModel> images) {
        this.images = images;
    }
    
}
