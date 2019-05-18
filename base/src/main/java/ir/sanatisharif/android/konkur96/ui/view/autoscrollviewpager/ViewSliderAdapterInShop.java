package ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;

import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.MainBannerModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.utils.Utils;

public class ViewSliderAdapterInShop extends PagerAdapter {

    private Context mContext;
    private ImageView img;
    private List<MainBannerModel> imageList;
    private LayoutInflater inflater;
    int h;

    public ViewSliderAdapterInShop(Context context, List<MainBannerModel> list) {
        mContext = context;
        imageList = list;
        inflater = LayoutInflater.from(context);
        setSize();
    }

    private void setSize() {
        h = (int) (AppConfig.width * 0.39f);

    }

    @Override
    public Object instantiateItem(ViewGroup collection, final int position) {

        ViewGroup imageLayout = (ViewGroup) inflater.inflate(R.layout.view_slider, collection, false);
        img = imageLayout.findViewById(R.id.imageView);
        img.getLayoutParams().width = AppConfig.width;
        img.getLayoutParams().height = h;


        Glide.with(mContext)
                .load(imageList.get(position).getUrl())
                .apply(new RequestOptions().override(1280, 720))
                .into(img);

        img.setOnClickListener(view -> {
            Utils.loadUrl(imageList.get(position).getLink(), AppConfig.context);

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