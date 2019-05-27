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
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.model.filter.FilterBaseModel;
import ir.sanatisharif.android.konkur96.model.filter.VideoCourse;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;


public class MoreFilesProductAdapter extends RecyclerView.Adapter<MoreFilesProductAdapter.MyViewHolder> {

    private List<? extends FilterBaseModel> itemsList;
    private Context mContext;


    public MoreFilesProductAdapter(Context context, ArrayList<VideoCourse> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_productset_more, parent, false);

        return new MoreFilesProductAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        VideoCourse item = (VideoCourse) itemsList.get(position);

        holder.title.setText(item.getName());
        Glide.with(mContext)
                .load(item.getThumbnail())
                .thumbnail(0.1f)
                .into(holder.imageView);


        holder.cardView.setOnClickListener(view -> addFrg(DetailsVideoFrg.newInstance(itemsList, position), "DetailsVideoFrg"));


    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public CardView cardView;
        public ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.txt_title);
            cardView = view.findViewById(R.id.card_body);
            imageView = view.findViewById(R.id.img);
        }
    }
}