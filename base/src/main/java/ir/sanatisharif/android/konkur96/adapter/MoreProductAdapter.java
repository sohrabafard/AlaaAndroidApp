package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.model.MoreProductModel;

public class MoreProductAdapter extends RecyclerView.Adapter<MoreProductAdapter.MyViewHolder> {


    private ArrayList<MoreProductModel> moreProductModels;
    private Context context;


    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtTitleMoreProduct;
        TextView txtAuthorMoreProduct;
        TextView txtPriceMore;
        ImageView imgMoreProduct;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtTitleMoreProduct = itemView.findViewById(R.id.txt_titel_more);
            txtAuthorMoreProduct = itemView.findViewById(R.id.txt_author_more);
            txtPriceMore = itemView.findViewById(R.id.txt_price_more);
            imgMoreProduct = itemView.findViewById(R.id.img_item_more);
        }

    }

    public MoreProductAdapter(Context context, ArrayList<MoreProductModel> moreProductModels) {
        this.context = context;
        this.moreProductModels = moreProductModels;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_more_product, parent, false);

        MyViewHolder vh = new MyViewHolder(itemView);
        return vh;
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        MoreProductModel testModel = moreProductModels.get(position);

        //dummay data

            holder.txtTitleMoreProduct.setText(testModel.getTitle());
            holder.txtAuthorMoreProduct.setText(testModel.getAuthor());
            holder.txtPriceMore.setText(testModel.getPrice());

            Glide.with(context)
                    .load(testModel.getImageUrl())
                    .into(holder.imgMoreProduct);




    }

    @Override
    public int getItemCount() {
        return moreProductModels.size();
    }
}