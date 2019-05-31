package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.fragment.MyProductSet;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.ContentHolder> {

    private List<ProductModel> itemsList = new ArrayList<>();
    private Context mContext;


    public MyProductAdapter(Context context, ArrayList<ProductModel> itemsList) {
        this.setItems(itemsList);
        this.mContext = context;
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_productset, parent, false);
        return new ContentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ContentHolder holder, final int position) {

        ProductModel item = itemsList.get(position);

        holder.title.setText(item.getName());
        Glide.with(mContext)
                .load(item.getPhoto())
                .thumbnail(0.1f)
                .into(holder.imageView);


        holder.cardView.setOnClickListener(view -> addFrg(MyProductSet.newInstance(item.getSets()), "MyProductSet"));

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public void setItems(ArrayList<ProductModel> objects) {
        itemsList.clear();
        itemsList.addAll(objects);
        this.notifyDataSetChanged();
    }


    public class ContentHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public ImageView imageView;
        public CardView cardView;

        public ContentHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
            imageView = itemView.findViewById(R.id.imgset);
            cardView = itemView.findViewById(R.id.card_body);
        }

       /* public CustomShopItemView getCustomCatItem() {
            return customShopItemView;
        }*/
    }

}