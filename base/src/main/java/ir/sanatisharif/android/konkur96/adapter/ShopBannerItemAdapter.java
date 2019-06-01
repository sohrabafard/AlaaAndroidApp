package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.MainBannerModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;


public class ShopBannerItemAdapter extends RecyclerView.Adapter<ShopBannerItemAdapter.BannerHolder> {
    
    private ArrayList<MainBannerModel> itemsList;
    private Context                    mContext;
    private RequestOptions             requestOptions;
    private int                        h, w;
    
    public ShopBannerItemAdapter(Context context, ArrayList<MainBannerModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        
        setSize();
    }
    
    private void setSize() {
        h = AppConfig.itemHeight - 34;
        w = (int) (AppConfig.width * 0.75f);
        
        requestOptions = new RequestOptions()
                .override(AppConfig.width, h)
                .transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.round_image)))
                .override(w, h)
                .fitCenter();
    }
    
    @Override
    public BannerHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {
        return new BannerHolder(LayoutInflater.from(mContext).inflate(R.layout.banner_item, parent, false));
    }
    
    @Override
    public void onBindViewHolder(final BannerHolder holder, final int position) {
        
        MainBannerModel item = itemsList.get(position);
        
        holder.txtTitle.setText(item.getTitle());
        
        holder.imgItem.getLayoutParams().width = w;
        holder.imgItem.getLayoutParams().height = h;
        holder.txtTitle.getLayoutParams().width = w;
        
        Glide.with(AppConfig.context)
                .load(item.getUrl())
                .apply(requestOptions)
                .thumbnail(0.1f)
                .into(new SimpleTarget<Drawable>(w, h) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        holder.imgItem.setImageDrawable(resource);
                    }
                });
    }
    
    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }
    
    
    public class BannerHolder extends RecyclerView.ViewHolder {
        
        private LinearLayout layout_click;
        private TextView     txtTitle;
        private ImageView    imgItem;
        
        private BannerHolder(View view) {
            super(view);
            
            layout_click = view.findViewById(R.id.layout_click);
            txtTitle = view.findViewById(R.id.txt_title);
            imgItem = view.findViewById(R.id.imgItem);
            
            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
        }
    }
    
    
}