package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.main_page.Banner;


public class BannerItemAdapter extends RecyclerView.Adapter<BannerItemAdapter.BannerHolder> {

    private List<Banner> itemsList;
    private Context mContext;


    public BannerItemAdapter(Context context, List<Banner> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }


    @Override
    public BannerHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {
        return new BannerHolder(LayoutInflater.from(mContext).inflate(R.layout.banner_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final BannerHolder holder, final int position) {

        Banner item = itemsList.get(position);


        int h = AppConfig.itemHeight - 34;
        int w = (int) (AppConfig.width * 0.75f);

        holder.imgItem.getLayoutParams().width = w;
        holder.imgItem.getLayoutParams().height = h;
        holder.txtTitle.getLayoutParams().width = w;

//        GlideApp.with(AppConfig.context)
//                .load(item.getUrl())
//                .transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.round_image)))
//                .override(w, h)
//                .transition(DrawableTransitionOptions.withCrossFade())
//                .into(new SimpleTarget<Drawable>(w, h) {
//                    @Override
//                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
//                        holder.imgItem.setImageDrawable(resource);
//                    }
//                });
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class BannerHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout_click;
        private TextView txtTitle;
        private ImageView imgItem;

        private BannerHolder(View view) {
            super(view);

            layout_click = view.findViewById(R.id.layout_click);
            txtTitle = view.findViewById(R.id.txt_title);
            imgItem = view.findViewById(R.id.imgItem);

            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
        }
    }

}