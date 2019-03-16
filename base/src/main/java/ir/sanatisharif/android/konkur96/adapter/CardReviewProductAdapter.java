package ir.sanatisharif.android.konkur96.adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.ItemCardReviewMOdel;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.SelectableProduct;
import ir.sanatisharif.android.konkur96.utils.GalleryWorker;
import ir.sanatisharif.android.konkur96.utils.ShopUtils;


public class CardReviewProductAdapter extends RecyclerView.Adapter<CardReviewProductAdapter.MyViewHolder> {

    private Context context;
    private DeleteListener deleteListener;
    private ArrayList<ItemCardReviewMOdel> itemCardReviewMOdels;


    public CardReviewProductAdapter(Context context, ArrayList<ItemCardReviewMOdel> itemCardReviewMOdels, DeleteListener deleteListener) {

        this.deleteListener = deleteListener;
        this.itemCardReviewMOdels = itemCardReviewMOdels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_selectable_product, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public int getItemCount() {
        if (itemCardReviewMOdels != null)
            return itemCardReviewMOdels.size();
        else
            return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {



        holder.checkBox.setOnCheckedChangeListener(null);


        holder.textView.setTypeface(AppConfig.fontIRSensLight);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            holder.checkBox.setButtonTintList(ContextCompat.getColorStateList(context, R.color.checkboxtint));
        }


//


    }

    @SuppressLint("SetTextI18n")
    private void setFinalPrice(TextView textView , ProductModel model) {


        if (model.getPrice().getMfinal() > 0) {

            textView.setText(ShopUtils.formatPrice(model.getPrice().getMfinal()) + " تومان ");

        } else {

            textView.setText(ShopUtils.formatPrice(0) + " تومان ");
        }
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;
        TextView textView, txtFinalPrice;
        RecyclerView recyclerView;


        MyViewHolder(View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkBox);
            textView = itemView.findViewById(R.id.txt_name);
            txtFinalPrice = itemView.findViewById(R.id.txt_final_price);
            recyclerView = itemView.findViewById(R.id.recycler_child);


        }
    }

    public interface DeleteListener {

        void onClickDelete();

    }



}

