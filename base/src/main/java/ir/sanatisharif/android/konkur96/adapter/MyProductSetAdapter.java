package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
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
import ir.sanatisharif.android.konkur96.api.Models.ProductSetModel;
import ir.sanatisharif.android.konkur96.fragment.FilterTagsFrg;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class MyProductSetAdapter extends RecyclerView.Adapter<MyProductSetAdapter.MyViewHolder> {

    private ArrayList<ProductSetModel> itemsList;
    private Context mContext;


    public MyProductSetAdapter(Context context, ArrayList<ProductSetModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_productset, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        ProductSetModel item = itemsList.get(position);

        holder.title.setText(item.getName());
        Glide.with(mContext).load(item.getPhoto()).into(holder.imageView);



        //holder.cardView.setOnClickListener(view -> addFrg(MoreProductSetFragment.newInstance(item.getContentUrl()),"MoreProductSetFragment"));
        holder.cardView.setOnClickListener(view -> addFrg(FilterTagsFrg.newInstance(item.getContentUrl(), null), "MoreProductSetFragment"));


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imageView;
        public CardView cardView;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.txt_title);
            imageView = view.findViewById(R.id.imgset);
            cardView = view.findViewById(R.id.card_body);
        }
    }


}