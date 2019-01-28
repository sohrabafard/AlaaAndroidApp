package ir.sanatisharif.android.konkur96.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ir.sanatisharif.android.konkur96.R;

public class MoreProductAdapter extends RecyclerView.Adapter<MoreProductAdapter.MyViewHolder> {
    private String[] mDataset;

    static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtMoreProduct;
        ImageView imgMoreProduct;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtMoreProduct = itemView.findViewById(R.id.txt_more_product);
            imgMoreProduct = itemView.findViewById(R.id.img_more_product);
        }


    }

    public MoreProductAdapter(String[] myDataset) {
        mDataset = myDataset;
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
        //dummay data
        holder.txtMoreProduct.setText("Product");
        holder.imgMoreProduct.setImageResource(R.drawable.ala_ok);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}