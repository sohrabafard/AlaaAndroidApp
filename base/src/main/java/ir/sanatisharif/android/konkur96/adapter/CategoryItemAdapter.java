package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.model.CategoryItemSet;
import ir.sanatisharif.android.konkur96.ui.view.CustomCatItem;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryHolder> {

    private ArrayList<CategoryItemSet> itemsList;
    private Context mContext;


    public CategoryItemAdapter(Context context, ArrayList<CategoryItemSet> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {
        // return new CategoryHolder(LayoutInflater.from(mContext).inflate(R.layout.category_item, parent, false));

        CustomCatItem itemView = new CustomCatItem(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryHolder holder, final int position) {

        //  holder.getCustomCatItem().setItem(item, position);
        CategoryItemSet item = itemsList.get(position);
        holder.customCatItem.setClickItem(position, item);
        holder.customCatItem.setTitle(item.getTitle());
        holder.customCatItem.setAuthor(item.getAuthor());
        holder.customCatItem.setContentCount(6);
        holder.customCatItem.setImage(item.getImageUrl());


        holder.getCustomCatItem().setOnClickItem(new CustomCatItem.OnClickItem() {
            @Override
            public void OnClick(int position, Object item) {

                addFrg(DetailsVideoFrg.newInstance(itemsList.get(position)), "DetailsVideoFrg");
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        CustomCatItem customCatItem;

        public CategoryHolder(View itemView) {
            super(itemView);
            customCatItem = (CustomCatItem) itemView;

        }

        public CustomCatItem getCustomCatItem() {
            return customCatItem;
        }

    }

}