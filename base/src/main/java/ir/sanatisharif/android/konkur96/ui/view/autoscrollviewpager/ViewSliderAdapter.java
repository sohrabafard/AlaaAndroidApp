package ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.model.ViewSlider;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.utils.Utils;

/**
 * Created by Mohamad on 10/11/2018.
 */

public class ViewSliderAdapter extends PagerAdapter {

    private Context mContext;
    private ImageView img;
    private List<ViewSlider> imageList;
    private LayoutInflater inflater;

    public ViewSliderAdapter(Context context, List<ViewSlider> list) {
        mContext = context;
        imageList = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {
        ViewGroup imageLayout = (ViewGroup) inflater.inflate(R.layout.view_slider, collection, false);

        img = (ImageView) imageLayout.findViewById(R.id.imageView);
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
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (imageList.get(position).getKindOfIntent() == AppConstants.LINK_TO_EXTERNAL) {
                    Utils.loadUrl(imageList.get(position).getIntentUrl(), AppConfig.context);
                } else if (imageList.get(position).getKindOfIntent() == AppConstants.LINK_TO_WEB_VIEW) {
                    // push to webView Fragment with URL address
                }
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
        return imageList.get(position).getText();
    }
}