package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.fragment.ProductDetailFragment;
import ir.sanatisharif.android.konkur96.ui.view.CustomShopItemView;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class MoreProductAdapter extends RecyclerView.Adapter<MoreProductAdapter.ContentHolder> {

    private ArrayList<ProductModel> itemsList;
    private Context mContext;


    public MoreProductAdapter(Context context, ArrayList<ProductModel> itemsList) {
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



        ProductModel item = itemsList.get(position);

        holder.customShopItemView.setClickItem(position, item);
        holder.customShopItemView.setTitle(item.getName());
        holder.customShopItemView.setPrice(ShopUtils.formatPrice(item.getPrice().getMfinal()));
        if (item.getPrice().getDiscount() > 0){

            holder.customShopItemView.setDiscount(ShopUtils.formatPrice(item.getPrice().getBase()));

        }
        holder.customShopItemView.setImage(item.getPhoto());

        holder.getCustomCatItem().setOnClickItem((position1, item1) -> addFrg(ProductDetailFragment.newInstance(item),"ProductDetailFragment"));


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