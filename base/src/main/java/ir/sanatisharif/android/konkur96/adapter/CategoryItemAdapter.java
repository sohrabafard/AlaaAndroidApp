package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.model.CategoryItemSet;
import ir.sanatisharif.android.konkur96.ui.view.CustomItemView;

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

        CustomItemView itemView = new CustomItemView(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryHolder holder, final int position) {

        CategoryItemSet item = itemsList.get(position);
        holder.customItemView.setClickItem(position, item);
        holder.customItemView.setTitle(item.getTitle());
        holder.customItemView.setAuthor(item.getAuthor());
      //  holder.customItemView.setContentCount(6);
        holder.customItemView.setImage(item.getImageUrl());


        holder.getCustomCatItem().setOnClickItem(new CustomItemView.OnClickItem() {
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

        CustomItemView customItemView;

        public CategoryHolder(View itemView) {
            super(itemView);
            customItemView = (CustomItemView) itemView;
        }

        public CustomItemView getCustomCatItem() {
            return customItemView;
        }

    }

}