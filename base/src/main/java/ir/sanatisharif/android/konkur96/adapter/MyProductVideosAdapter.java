package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.model.FileDiskModel;

public class MyProductVideosAdapter extends RecyclerView.Adapter<MyProductVideosAdapter.MyViewHolder> {

    private ArrayList<FileDiskModel> itemsList;
    private Context                  mContext;


    public MyProductVideosAdapter(Context context, ArrayList<FileDiskModel> itemsList) {
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


        FileDiskModel item = itemsList.get(position);

        holder.title.setText(item.getName());

        // holder.cardView.setOnClickListener(view -> addFrg(MoreProductSetFragment.newInstance(item.getContentUrl()),"MoreProductSetFragment"));


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  title;
        public ImageView imageView;
        public CardView  cardView;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.txt_title);
            imageView = view.findViewById(R.id.imgset);
            cardView = view.findViewById(R.id.card_body);
        }
    }


}