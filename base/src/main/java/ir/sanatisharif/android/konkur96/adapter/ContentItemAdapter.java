package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.fragment.ExtraItemFrg;
import ir.sanatisharif.android.konkur96.model.Content;
import ir.sanatisharif.android.konkur96.ui.view.CustomItemView;
import ir.sanatisharif.android.konkur96.utils.Utils;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class ContentItemAdapter extends RecyclerView.Adapter<ContentItemAdapter.ContentHolder> {

    private List<Content> itemsList;
    private Context mContext;


    public ContentItemAdapter(Context context, List<Content> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {

        CustomItemView itemView = new CustomItemView(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new ContentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ContentHolder holder, final int position) {

        Content item = itemsList.get(position);
        //holder.customItemView.setClickItem(position, item);
        holder.customItemView.setTitle(item.getTitle());
        holder.customItemView.setAuthor(item.getAuthor().getLastName());
        holder.customItemView.setContentCount(16);
        holder.customItemView.setImage(item.getPhoto());

        holder.getCustomCatItem().setOnClickItem(new CustomItemView.OnClickItem() {
            @Override
            public void OnClick(int position, Object item) {


                //addFrg(ExtraItemFrg.newInstance(AppConstants.ITEM_CONTENT), "ExtraItemFrg");
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class ContentHolder extends RecyclerView.ViewHolder {

        CustomItemView customItemView;

        public ContentHolder(View itemView) {
            super(itemView);
            customItemView = (CustomItemView) itemView;
        }

        public CustomItemView getCustomCatItem() {
            return customItemView;
        }
    }

}