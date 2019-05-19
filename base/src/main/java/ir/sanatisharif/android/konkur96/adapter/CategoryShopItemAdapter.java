package ir.sanatisharif.android.konkur96.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.fragment.ExtraItemFrg;
import ir.sanatisharif.android.konkur96.fragment.ProductDetailFragment;
import ir.sanatisharif.android.konkur96.model.IncredibleOffer;
import ir.sanatisharif.android.konkur96.model.ShopItem;
import ir.sanatisharif.android.konkur96.ui.view.CustomShopItemView;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class CategoryShopItemAdapter extends RecyclerView.Adapter<CategoryShopItemAdapter.ContentHolder> {

    private ArrayList<ProductModel> itemsList;
    private Context mContext;


    public CategoryShopItemAdapter(Context context, ArrayList<ProductModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {

        /*CustomShopItemView itemView = new CustomShopItemView(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));*/

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_shop_item, parent, false);
        return new ContentHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(final ContentHolder holder, final int position) {


        ProductModel item = itemsList.get(position);

        holder.title.setText(item.getName());
        holder.price.setText(ShopUtils.formatPrice(item.getPrice().getMfinal()) + " تومان ");
        if (item.getPrice().getDiscount() > 0) {

            holder.discount.setText(ShopUtils.formatPrice(item.getPrice().getBase()) + " تومان ");

        } else {

            holder.discount.setText("");
        }
        Glide.with(mContext).load(item.getPhoto()).into(holder.image);


        holder.cardViewRoot.setOnClickListener(view -> addFrg(ProductDetailFragment.newInstance(item), "ProductDetailFragment"));

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class ContentHolder extends RecyclerView.ViewHolder {

        public TextView title, price, discount;
        public ImageView image;
        public CardView cardViewRoot;

        public ContentHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txtTitle);
            price = itemView.findViewById(R.id.txtPrice);
            discount = itemView.findViewById(R.id.txtDiscount);
            image = itemView.findViewById(R.id.imgItem);
            cardViewRoot = itemView.findViewById(R.id.cardViewRoot);

            title.setTypeface(AppConfig.fontIRSensNumber);
            price.setTypeface(AppConfig.fontIRSensNumber);
            discount.setTypeface(AppConfig.fontIRSensNumber);

            discount.setPaintFlags(discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }

        /*public CustomShopItemView getCustomCatItem() {
            return customShopItemView;
        }*/
    }

}