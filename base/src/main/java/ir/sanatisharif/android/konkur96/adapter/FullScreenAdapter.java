package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.ProductPhotoModel;

public class FullScreenAdapter extends PagerAdapter {

    Context                      context;
    ArrayList<ProductPhotoModel> items;
    LayoutInflater               inflater;
    private int selectedPosition;

    public FullScreenAdapter(Context context, ArrayList<ProductPhotoModel> items, int selectedPos) {
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

        inflater =
                (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View
                itemView       =
                inflater.inflate(R.layout.item_fullscreen_pager, container, false);
        ProductPhotoModel temp = getModel(position);

        PhotoView photoView;
        photoView = itemView.findViewById(R.id.img_fullscreen);


        try {
            Glide.with(context)
                    .load(temp.getUrl())
                    .thumbnail(0.1f)
                    .into(photoView);


        }
        catch (Exception e) {

        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }


    private ProductPhotoModel getModel(int position) {

        return items.get(position);
    }

}
