package ir.sanatisharif.android.konkur96.ui.view.autoscrollviewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.MainBannerModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.utils.Utils;

/**
 * Created by Mohamad on 10/11/2018.
 */

public class ViewSliderAdapter extends PagerAdapter {

    private final Context               mContext;
    private       ImageView             img;
    private       List<MainBannerModel> imageList;
    private       LayoutInflater        inflater;
    private       RequestOptions        requestOptions;
    private       int                   h;

    public ViewSliderAdapter(Context context, List<MainBannerModel> list, RequestManager glideRequests) {
        mContext = context;
        imageList = list;
        inflater = LayoutInflater.from(context);

        setSize();
    }

    private void setSize() {
        h = (int) (AppConfig.width * 0.39f);

        requestOptions = new RequestOptions()
                .override(AppConfig.width, h)
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, final int position) {

        ViewGroup
                imageLayout =
                (ViewGroup) inflater.inflate(R.layout.view_slider, collection, false);
        img = imageLayout.findViewById(R.id.imageView);
        img.getLayoutParams().width = AppConfig.width;
        img.getLayoutParams().height = h;


        Glide.with(mContext)
                .load(imageList.get(position).getUrl())
                .thumbnail(0.1f)
                .apply(requestOptions)
                .into(img);
        img.setOnClickListener(view -> Utils.loadUrl(imageList.get(position).getLink(), AppConfig.context));
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