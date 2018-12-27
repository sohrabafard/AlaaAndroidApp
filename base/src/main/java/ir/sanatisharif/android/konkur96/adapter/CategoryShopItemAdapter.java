package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.fragment.ExtraItemFrg;
import ir.sanatisharif.android.konkur96.model.IncredibleOffer;
import ir.sanatisharif.android.konkur96.model.ShopItem;
import ir.sanatisharif.android.konkur96.ui.view.CustomShopItemView;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class CategoryShopItemAdapter extends RecyclerView.Adapter<CategoryShopItemAdapter.ContentHolder> {

    private ArrayList<ShopItem> itemsList;
    private Context mContext;


    public CategoryShopItemAdapter(Context context, ArrayList<ShopItem> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {

        CustomShopItemView itemView = new CustomShopItemView(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new ContentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ContentHolder holder, final int position) {



        ShopItem item = itemsList.get(position);

        holder.customShopItemView.setClickItem(position, item);
        holder.customShopItemView.setTitle(item.getTitle());
        holder.customShopItemView.setAuthor(item.getAuthor());
        holder.customShopItemView.setPrice(ShopUtils.formatPrice(item.getPrice()));
        holder.customShopItemView.setVisibilityDiscount(View.GONE);
        holder.customShopItemView.setImage(item.getImageUrl());

        //todo: complete this
       // holder.getCustomCatItem().setOnClickItem((position1, item1) -> addFrg(ExtraItemFrg.newInstance(AppConstants.CONTENT_ITEM_SET), "ExtraItemFrg"));


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class ContentHolder extends RecyclerView.ViewHolder {

        CustomShopItemView customShopItemView;

        public ContentHolder(View itemView) {
            super(itemView);
            customShopItemView = (CustomShopItemView) itemView;
        }

        public CustomShopItemView getCustomCatItem() {
            return customShopItemView;
        }
    }

}