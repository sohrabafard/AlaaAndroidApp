package ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.GalleryFullView;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.FullScreenModel;
import ir.sanatisharif.android.konkur96.model.ImageGalleryModel;
import ir.sanatisharif.android.konkur96.model.ProductSliderModel;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.utils.GalleryWorker;

public class ProductImageSliderAdapter extends PagerAdapter {

    private Context mContext;
    private ImageView img;
    private List<ProductSliderModel> imageList;
    private LayoutInflater inflater;

    // for ImageGallery
    private final String TAG_MODEL = "TAG_MODEL";
    ArrayList<ImageGalleryModel> imagesOb;
    GalleryWorker imgGallery;




    public ProductImageSliderAdapter(Context context, List<ProductSliderModel> list) {
        mContext = context;
        imageList = list;
        inflater = LayoutInflater.from(context);
        imgGallery = new GalleryWorker(mContext);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        ViewGroup imageLayout = (ViewGroup) inflater.inflate(R.layout.view_slider, collection, false);

        img = imageLayout.findViewById(R.id.imageView);
        int h = (int) (AppConfig.width * 0.39f);
        img.getLayoutParams().height = h;
        GlideApp.with(AppConfig.context)
                .load(imageList.get(position).getImgUrl())
                .fitCenter()
                //.override(AppConfig.width, AppConfig.itemHeight)
                .into(new SimpleTarget<Drawable>(1280, 500) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        img.setImageDrawable(resource);
                    }
                });
        img.setOnClickListener(view -> {

            //todo: complete this
            initImages();
            imgGallery.openFullView(1);

        });
        collection.addView(imageLayout);
        return imageLayout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return imageList.get(position).getText();
    }




    // how it's work from another class








    private void initImages() {


        String[] items = {
                "https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171003105152.jpg?w=253&h=142",
                "https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/171005032754.jpg?w=253&h=142",
                "https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142",
                "https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/physics1809261648.jpg?w=253&h=142"
        };

        imagesOb =  new ArrayList<>(items.length);


        for (int i = 0; i < items.length; i++) {
            ImageGalleryModel dummyData = new ImageGalleryModel();
            dummyData.setImagePath(items[i]);
            dummyData.setImageTitle("کازیان " + i);
            dummyData.setImageDesc("بسته ویژه کنکور " + i);
            imagesOb.add(dummyData);
        }
        imgGallery.setImages(imagesOb);

    }

}