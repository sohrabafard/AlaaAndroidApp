package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ir.sanatisharif.android.konkur96.R;

public class PageViewAdapter extends PagerAdapter{

    private Context context;
    private int[] path;
    private LayoutInflater inflater;

    public PageViewAdapter(Context context, int[] path) {
        this.context = context;
        this.path = path;
    }


    @Override
    public int getCount() {
        return path.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView image;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_swipe, container, false);
        image = view.findViewById(R.id.img_swipe);

        Glide.with(image)
                .load(path[position])
                .into(image);

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView)object);
    }
}

