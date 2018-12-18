package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.Observer;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.fragment.ContentFrg;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.fragment.ExtraItemFrg;
import ir.sanatisharif.android.konkur96.model.CategoryItemSet;
import ir.sanatisharif.android.konkur96.model.Content;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.ui.view.CustomCatItem;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class ContentItemAdapter extends RecyclerView.Adapter<ContentItemAdapter.ContentHolder> {

    private ArrayList<Content> itemsList;
    private Context mContext;


    public ContentItemAdapter(Context context, ArrayList<Content> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {

        CustomCatItem itemView = new CustomCatItem(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new ContentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ContentHolder holder, final int position) {

        Content item = itemsList.get(position);
        holder.getCustomCatItem().setItem(item, position);

        holder.getCustomCatItem().setOnClickItem(new CustomCatItem.OnClickItem() {
            @Override
            public void OnClick(int position, Object item) {

                addFrg(ExtraItemFrg.newInstance(AppConstants.CONTENT_ITEM_SET), "ExtraItemFrg");
            }
        });


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class ContentHolder extends RecyclerView.ViewHolder {

        CustomCatItem customCatItem;

        public ContentHolder(View itemView) {
            super(itemView);
            customCatItem = (CustomCatItem) itemView;
        }

        public CustomCatItem getCustomCatItem() {
            return customCatItem;
        }
    }

}