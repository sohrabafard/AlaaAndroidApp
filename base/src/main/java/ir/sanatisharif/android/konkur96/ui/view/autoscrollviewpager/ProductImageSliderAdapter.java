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
import ir.sanatisharif.android.konkur96.model.ProductSliderModel;
import ir.sanatisharif.android.konkur96.ui.GlideApp;

public class ProductImageSliderAdapter extends PagerAdapter {

    private Context mContext;
    private ImageView img;
    private List<ProductSliderModel> imageList;
    private LayoutInflater inflater;

    public ProductImageSliderAdapter(Context context, List<ProductSliderModel> list) {
        mContext = context;
        imageList = list;
        inflater = LayoutInflater.from(context);
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