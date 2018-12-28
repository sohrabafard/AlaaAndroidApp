package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;


import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.model.ImageGalleryModel;

public class FullScreenAdapter extends PagerAdapter {

    Context context;
    ArrayList<ImageGalleryModel> items;
    LayoutInflater inflater;
    private int selectedPosition;

    public FullScreenAdapter(Context context, ArrayList<ImageGalleryModel> items, int selectedPos) {
        this.context = context;
        this.items = items;
        this.selectedPosition = selectedPos;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        inflater = (LayoutInflater) context.getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_fullscreen_pager, container, false);
        ImageGalleryModel temp = getModel(position);

        PhotoView photoView;
        photoView = (PhotoView) itemView.findViewById(R.id.img_fullscreen);


        try {
            Glide.with(context)
                    .load(temp.getImagePath())
                    .into(photoView);


        } catch (Exception e) {

        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ((ViewPager) container).removeView((View) object);
    }


    private ImageGalleryModel getModel(int position) {

        return items.get(position);
    }

}
