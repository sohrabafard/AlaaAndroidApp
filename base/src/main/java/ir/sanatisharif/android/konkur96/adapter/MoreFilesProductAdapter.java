package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.content.Intent;
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
import ir.sanatisharif.android.konkur96.activity.VideoPlayActivity;
import ir.sanatisharif.android.konkur96.api.Models.VideoFilesModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;


public class MoreFilesProductAdapter extends RecyclerView.Adapter<MoreFilesProductAdapter.MyViewHolder> {

    private ArrayList<VideoFilesModel> itemsList;
    private Context mContext;


    public MoreFilesProductAdapter(Context context, ArrayList<VideoFilesModel> itemsList) {
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



        VideoFilesModel item = itemsList.get(position);

        holder.title.setText(item.getName());
        Glide.with(mContext).load(item.getThumbnail()).into(holder.imageView);



        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AppConfig.currentActivity, VideoPlayActivity.class);
                intent.putExtra("path", item.getFile().getVideo().get(0).getPath());
                AppConfig.currentActivity.startActivity(intent);

            }
        });


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