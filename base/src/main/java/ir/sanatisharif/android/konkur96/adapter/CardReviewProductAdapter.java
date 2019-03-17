package ir.sanatisharif.android.konkur96.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.AddToCardModel;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;


public class CardReviewProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private DeleteListener deleteListener;
    private ArrayList<AddToCardModel> items;


    public CardReviewProductAdapter(Context context, ArrayList<AddToCardModel> items, DeleteListener deleteListener) {

        this.deleteListener = deleteListener;
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 1) {
            return new NoGrandProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_card_reviw_nogrand, parent, false));
        }else {

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

            final AddToCardModel model = items.get(position);

            final String title = model.getProduct().getName();
            final int price = model.getProduct().getPrice().getMfinal();
            final String image = model.getProduct().getPhoto();

            final NoGrandProductViewHolder itemRowHolderNoGrand = (NoGrandProductViewHolder) holder;

            itemRowHolderNoGrand.txtTitle.setText(title);
            itemRowHolderNoGrand.txtPrice.setText(ShopUtils.formatPrice(price) + " تومان ");

            Glide.with(context).load(image).into(itemRowHolderNoGrand.imageView);


        }else {

            final AddToCardModel model = items.get(position);

            final String title = model.getGrandProduct().getName();
            final String proTitle = model.getProduct().getName();
            final int price = model.getProduct().getPrice().getMfinal();
            final String image = model.getProduct().getPhoto();

            final GrandProductViewHolder itemRowHolderGrand = (GrandProductViewHolder) holder;

            itemRowHolderGrand.txtTitle.setText(title);
            itemRowHolderGrand.txtProTitle.setText(proTitle);
            itemRowHolderGrand.txtPrice.setText(ShopUtils.formatPrice(price) + " تومان ");

            Glide.with(context).load(image).into(itemRowHolderGrand.imageView);


        }
    }

    @Override
    public int getItemViewType(int position) {

        if (items.get(position).getGrandProduct() == null) {

            return 1;

        }else {

            return 2;
        }

    }

    class GrandProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtTitle, txtPrice, txtProTitle;


        GrandProductViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            txtTitle = itemView.findViewById(R.id.title);
            txtProTitle = itemView.findViewById(R.id.pro_titel);
            txtPrice = itemView.findViewById(R.id.price);


        }
    }

    class NoGrandProductViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView txtTitle, txtPrice;


        NoGrandProductViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.img);
            txtTitle = itemView.findViewById(R.id.title);
            txtPrice = itemView.findViewById(R.id.price);

        }
    }

    public interface DeleteListener {

        void onClickDelete();

    }



}

