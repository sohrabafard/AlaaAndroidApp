package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.AddToCardModel;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;


public class CardChildAdapter extends RecyclerView.Adapter<CardChildAdapter.vHolder> {
    
    private ArrayList<AddToCardModel> itemsList;
    private Context                   mContext;
    private DeleteListener            deleteListener;
    
    
    public CardChildAdapter(Context context, ArrayList<AddToCardModel> itemsList, DeleteListener deleteListener) {
        this.itemsList = itemsList;
        this.mContext = context;
        this.deleteListener = deleteListener;
    }
    
    
    @Override
    public vHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {
        return new vHolder(LayoutInflater.from(mContext).inflate(R.layout.item_card_child, parent, false));
    }
    
    @Override
    public void onBindViewHolder(final vHolder holder, final int position) {
        
        AddToCardModel model = itemsList.get(position);
        
        final String proTitle = model.getProduct().getName();
        final int    price    = model.getProduct().getPrice().getMfinal();
        final int    discount = model.getProduct().getPrice().getBase();
        
        final vHolder itemRowHolderGrand = holder;
        
        
        if (position == 0) {
            
            itemRowHolderGrand.imgArr.setVisibility(View.VISIBLE);
            
        } else {
            
            itemRowHolderGrand.imgArr.setVisibility(View.GONE);
        }
        
        
        itemRowHolderGrand.txtProTitle.setText(proTitle);
        itemRowHolderGrand.txtPrice.setText(ShopUtils.formatPrice(price) + " تومان ");
        
        if (discount > 0) {
            itemRowHolderGrand.txtDiscount.setVisibility(View.VISIBLE);
            itemRowHolderGrand.txtDiscount.setText(ShopUtils.formatPrice(discount) + " تومان ");
            
        } else {
            
            itemRowHolderGrand.txtDiscount.setVisibility(View.GONE);
            
        }
        
        itemRowHolderGrand.btnDel.setOnClickListener(view -> deleteListener.onClickDeleteChild(model.getId()));
        
        
    }
    
    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }
    
    
    public interface DeleteListener {
        
        void onClickDeleteChild(int id);
        
    }
    
    public class vHolder extends RecyclerView.ViewHolder {
        
        ImageView btnDel, imgArr;
        TextView txtPrice, txtProTitle, txtDiscount;
        
        private vHolder(View view) {
            super(view);
            
            
            btnDel = itemView.findViewById(R.id.btn_del);
            imgArr = itemView.findViewById(R.id.img_arr);
            txtProTitle = itemView.findViewById(R.id.pro_titel);
            txtPrice = itemView.findViewById(R.id.price);
            txtDiscount = itemView.findViewById(R.id.txt_discount);
            txtDiscount.setPaintFlags(txtDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}