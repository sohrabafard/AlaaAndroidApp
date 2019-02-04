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
import ir.sanatisharif.android.konkur96.api.Models.MainBannerModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.ui.GlideApp;

public class ViewSliderAdapterInShop extends PagerAdapter {

    private Context mContext;
    private ImageView img;
    private List<MainBannerModel> imageList;
    private LayoutInflater inflater;

    public ViewSliderAdapterInShop(Context context, List<MainBannerModel> list) {
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
                .load(imageList.get(position).getUrl())
                .fitCenter()
                //.override(AppConfig.width, AppConfig.itemHeight)
                .into(new SimpleTarget<Drawable>(1280, 500) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        img.setImageDrawable(resource);
                    }
                });
        img.setOnClickListener(view -> {

//                if (imageList.get(position).getKindOfIntent() == AppConstants.LINK_TO_EXTERNAL) {
//                    Utils.loadUrl(imageList.get(position).getIntentUrl(), AppConfig.context);
//                } else if (imageList.get(position).getKindOfIntent() == AppConstants.LINK_TO_WEB_VIEW) {
//                    // push to webView Fragment with URL address
//                }
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
        return imageList.get(position).getTitle();
    }
}