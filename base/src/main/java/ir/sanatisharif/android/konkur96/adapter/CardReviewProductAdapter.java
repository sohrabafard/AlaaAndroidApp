package ir.sanatisharif.android.konkur96.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.AddToCardModel;
import ir.sanatisharif.android.konkur96.api.Models.ItemCardReviewMOdel;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;


public class CardReviewProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    private Context        context;
    private DeleteListener deleteListener;
    private List<Object>   items = new ArrayList<>();
    
    
    private LinearLayoutManager linearLayoutManager;
    private CardChildAdapter    adapter;
    
    
    public CardReviewProductAdapter(Context context, DeleteListener deleteListener) {
        
        this.deleteListener = deleteListener;
        this.context = context;
    }
    
    public void setItems(@NonNull final ArrayList<ItemCardReviewMOdel> items) {
        int c = this.items.size();
        this.items.clear();
        this.notifyDataSetChanged();
        
        if (items.isEmpty())
            return;
        
        List<AddToCardModel>      noGrandProducts = new ArrayList<>();
        List<ItemCardReviewMOdel> grandProducts   = new ArrayList<>();
        
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getGrand() == null) {
                noGrandProducts.addAll(items.get(i).getOrderproducts());
            } else {
                grandProducts.add(items.get(i));
            }
        }
        this.items.addAll(noGrandProducts);
        this.items.addAll(grandProducts);
        this.notifyDataSetChanged();
    }
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        if (viewType == 1) {
            return new NoGrandProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card_reviw_nogrand, parent, false));
        } else {
            
            return new GrandProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card_review_grand, parent, false));
        }
    }
    
    @Override
    public int getItemCount() {
        if (items != null)
            return items.size();
        else
            return 0;
    }
    
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        // To Determine View Type
        int viewType = getItemViewType(position);
        
        if (viewType == 1) {
            
            final AddToCardModel product = (AddToCardModel) items.get(position);
            
            final String title    = product.getProduct().getName();
            final int    price    = product.getProduct().getPrice().getMfinal();
            final int    discount = product.getProduct().getPrice().getBase();
            final String image    = product.getProduct().getPhoto();
            
            final NoGrandProductViewHolder itemRowHolderNoGrand = (NoGrandProductViewHolder) holder;
            
            itemRowHolderNoGrand.txtTitle.setText(title);
            itemRowHolderNoGrand.txtPrice.setText(ShopUtils.formatPrice(price) + " تومان ");
            
            if (discount > 0) {
                itemRowHolderNoGrand.txtDiscount.setVisibility(View.VISIBLE);
                itemRowHolderNoGrand.txtDiscount.setText(
                        ShopUtils.formatPrice(discount) + " تومان ");
                
            } else {
                itemRowHolderNoGrand.txtDiscount.setVisibility(View.GONE);
            }
            
            itemRowHolderNoGrand.btnDel.setOnClickListener(view -> deleteListener.onClickDelete(product.getId()));
            
            Glide.with(context)
                    .load(image)
                    .thumbnail(0.1f)
                    .into(itemRowHolderNoGrand.imageView);
            
        } else {
            
            final ItemCardReviewMOdel model = (ItemCardReviewMOdel) items.get(position);
            
            final GrandProductViewHolder itemRowHolderGrand = (GrandProductViewHolder) holder;
            
            final String title = model.getGrand().getName();
            final String image = model.getGrand().getPhoto();
            
            itemRowHolderGrand.txtTitle.setText(title);
            Glide.with(context)
                    .load(image)
                    .thumbnail(0.1f)
                    .into(itemRowHolderGrand.imageView);
            
            
            //recyclerView
            itemRowHolderGrand.recyclerView.setHasFixedSize(true);
            itemRowHolderGrand.recyclerView.setNestedScrollingEnabled(false);
            linearLayoutManager =
                    new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            itemRowHolderGrand.recyclerView.setLayoutManager(linearLayoutManager);
            adapter =
                    new CardChildAdapter(context, model.getOrderproducts(), id -> deleteListener.onClickDelete(id));
            itemRowHolderGrand.recyclerView.setAdapter(adapter);
            itemRowHolderGrand.recyclerView.setItemAnimator(new DefaultItemAnimator());
            
        }
    }
    
    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof AddToCardModel) {
            return 1;
        } else {
            return 2;
        }
    }
    
    public interface DeleteListener {
        
        void onClickDelete(int id);
        
    }
    
    class GrandProductViewHolder extends RecyclerView.ViewHolder {
        
        RecyclerView recyclerView;
        TextView     txtTitle;
        ImageView    imageView;
        
        
        GrandProductViewHolder(View itemView) {
            super(itemView);
            
            txtTitle = itemView.findViewById(R.id.title);
            recyclerView = itemView.findViewById(R.id.recyclerView_child);
            imageView = itemView.findViewById(R.id.img);
        }
    }
    
    class NoGrandProductViewHolder extends RecyclerView.ViewHolder {
        
        ImageView imageView, btnDel;
        TextView txtTitle, txtPrice, txtDiscount;
        
        
        NoGrandProductViewHolder(View itemView) {
            super(itemView);
            
            imageView = itemView.findViewById(R.id.img);
            btnDel = itemView.findViewById(R.id.btn_del);
            txtTitle = itemView.findViewById(R.id.title);
            txtPrice = itemView.findViewById(R.id.price);
            txtDiscount = itemView.findViewById(R.id.txt_discount);
            txtDiscount.setPaintFlags(txtDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            
        }
    }
    
    
}

