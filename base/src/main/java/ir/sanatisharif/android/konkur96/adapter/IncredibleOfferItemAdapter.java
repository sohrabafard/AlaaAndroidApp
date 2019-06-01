package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.fragment.ProductDetailFragment;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class IncredibleOfferItemAdapter extends RecyclerView.Adapter<IncredibleOfferItemAdapter.ContentHolder> {
    
    private ArrayList<ProductModel> itemsList;
    private Context                 mContext;
    
    
    public IncredibleOfferItemAdapter(Context context, ArrayList<ProductModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }
    
    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {
        
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_shop_item, parent, false);
        return new ContentHolder(itemView);
    }
    
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
        Glide.with(mContext)
                .load(item.getPhoto())
                .thumbnail(0.1f)
                .into(holder.image);
        
        
        holder.cardViewRoot.setOnClickListener(view -> addFrg(ProductDetailFragment.newInstance(item), "ProductDetailFragment"));
        
        
    }
    
    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }
    
    
    public class ContentHolder extends RecyclerView.ViewHolder {
        
        public TextView title, price, discount;
        public ImageView image;
        public CardView  cardViewRoot;
        
        public ContentHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
            price = itemView.findViewById(R.id.txtPrice);
            discount = itemView.findViewById(R.id.txtDiscount);
            image = itemView.findViewById(R.id.imgItem);
            cardViewRoot = itemView.findViewById(R.id.cardViewRoot);
            
            title.setTypeface(AppConfig.fontIRSensNumber);
            price.setTypeface(AppConfig.fontIRSensNumber);
            discount.setTypeface(AppConfig.fontIRSensNumber);
            
            discount.setPaintFlags(discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        
        
    }
    
}