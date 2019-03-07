package ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.model.ViewSlider;
import ir.sanatisharif.android.konkur96.model.main_page.MainBanner;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.ui.GlideRequest;
import ir.sanatisharif.android.konkur96.ui.GlideRequests;
import ir.sanatisharif.android.konkur96.utils.Utils;

/**
 * Created by Mohamad on 10/11/2018.
 */

public class ViewSliderAdapter extends PagerAdapter {

    private Context mContext;
    private ImageView img;
    private List<MainBanner> imageList;
    private LayoutInflater inflater;
    private GlideRequest<Drawable> requestBuilder;

    public ViewSliderAdapter(Context context, List<MainBanner> list, GlideRequests glideRequests) {
        mContext = context;
        imageList = list;
        inflater = LayoutInflater.from(context);
        requestBuilder = glideRequests.asDrawable().fitCenter();
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {

        ViewGroup imageLayout = (ViewGroup) inflater.inflate(R.layout.view_slider, collection, false);
        img = (ImageView) imageLayout.findViewById(R.id.imageView);
        int h = (int) (AppConfig.width * 0.39f);
        img.getLayoutParams().width = AppConfig.width;
        img.getLayoutParams().height = h;

        requestBuilder
                .load(imageList.get(position).getUrl())
                .override(AppConfig.width, h)
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.mipmap.ic_launcher)
                .into(new SimpleTarget<Drawable>() {//1280, 500
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        img.setImageDrawable(resource);
                        Log.i("LOG", "onLoadFailed: ok " + imageList.get(position).getUrl());
                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {
                        super.onLoadFailed(errorDrawable);
                        Log.i("LOG", "onLoadFailed: " + errorDrawable.toString());
                    }
                });

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.loadUrl(imageList.get(position).getLink(), AppConfig.context);
            }
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

        String title = imageList.get(position).getTitle();
        return title == null ? "" : title;
    }
}