package ir.sanatisharif.android.konkur96.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
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
import ir.sanatisharif.android.konkur96.api.Models.ItemCardReviewMOdel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;


public class CardReviewProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private DeleteListener deleteListener;
    private ArrayList<ItemCardReviewMOdel> items;

    private RecyclerView childRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private CardChildAdapter adapter;



    public CardReviewProductAdapter(Context context, ArrayList<ItemCardReviewMOdel> items, DeleteListener deleteListener) {

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

            final ItemCardReviewMOdel model = items.get(position);
            final AddToCardModel product = model.getOrderproducts().get(0);


            final String title = product.getProduct().getName();
            final int price = product.getProduct().getPrice().getMfinal();
            final int discount = product.getProduct().getPrice().getBase();
            final String image = product.getProduct().getPhoto();

            final NoGrandProductViewHolder itemRowHolderNoGrand = (NoGrandProductViewHolder) holder;

            itemRowHolderNoGrand.txtTitle.setText(title);
            itemRowHolderNoGrand.txtPrice.setText(ShopUtils.formatPrice(price) + " تومان ");

            if (discount > 0){
                itemRowHolderNoGrand.txtDiscount.setVisibility(View.VISIBLE);
                itemRowHolderNoGrand.txtDiscount.setText(ShopUtils.formatPrice(discount) + " تومان ");

            }else {

                itemRowHolderNoGrand.txtDiscount.setVisibility(View.GONE);

            }

            itemRowHolderNoGrand.btnDel.setOnClickListener(view -> deleteListener.onClickDelete(product.getId()));

            Glide.with(context).load(image).into(itemRowHolderNoGrand.imageView);


        }else {

            final ItemCardReviewMOdel model = items.get(position);

            final GrandProductViewHolder itemRowHolderGrand = (GrandProductViewHolder) holder;

            final String title = model.getGrand().getName();
            final String image = model.getGrand().getPhoto();

            itemRowHolderGrand.txtTitle.setText(title);
            Glide.with(context).load(image).into(itemRowHolderGrand.imageView);



            //recyclerView
            itemRowHolderGrand.recyclerView.setHasFixedSize(true);
            itemRowHolderGrand.recyclerView.setNestedScrollingEnabled(false);
            linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            itemRowHolderGrand.recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new CardChildAdapter(context, model.getOrderproducts(), id -> deleteListener.onClickDelete(id));
            itemRowHolderGrand.recyclerView.setAdapter(adapter);
            itemRowHolderGrand.recyclerView.setItemAnimator(new DefaultItemAnimator());

        }
    }

    @Override
    public int getItemViewType(int position) {

        if (items.get(position).getGrand() == null) {

            return 1;

        }else {

            return 2;
        }

    }

    class GrandProductViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView txtTitle;
        ImageView imageView;


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

    public interface DeleteListener {

        void onClickDelete(int id);

    }



}

