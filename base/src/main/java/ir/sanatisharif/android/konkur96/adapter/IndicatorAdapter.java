package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.Models.ProductPhotoModel;
import ir.sanatisharif.android.konkur96.interfaces.PositionFounder;

public class IndicatorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    private Context                      context;
    private ArrayList<ProductPhotoModel> imageGalleryModels;
    private int                          selectedPosition;
    private PositionFounder              positionFounder;
    
    private int TAG_NOTSELECTED = 0;
    private int TAG_SELECTED    = 1;
    private int TAG_EMPTY       = 2;
    
    public IndicatorAdapter(Context context, ArrayList<ProductPhotoModel> imageGalleryModels, int selectedPosition, PositionFounder pos) {
        this.context = context;
        this.imageGalleryModels = imageGalleryModels;
        this.selectedPosition = selectedPosition;
        this.positionFounder = pos;
        
    }
    
    @Override
    public int getItemViewType(int position) {
        if (position == 0 || position == getItemCount() - 1) {
            return TAG_EMPTY;
        } else {
            if (positionFounder.getPosition() == position - 1) {
                return TAG_SELECTED;
            } else {
                return TAG_NOTSELECTED;
            }
        }
    }
    
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        
        LayoutInflater inflater = LayoutInflater.from(context);
        
        if (viewType == TAG_EMPTY) {
            View vEmpty = inflater.inflate(R.layout.item_empty, parent, false);
            return new ViewHolderEmpty(vEmpty);
        } else {
            if (viewType == TAG_SELECTED) {
                View vSelected = inflater.inflate(R.layout.item_indicator_selected, parent, false);
                return new ViewHolderSelected(vSelected);
            } else {
                View v = inflater.inflate(R.layout.item_indicator, parent, false);
                return new ViewHolder(v);
            }
        }
    }
    
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        
        if (holder.getItemViewType() == TAG_EMPTY) {
            
            ((ViewHolderEmpty) holder).bindView(position - 1);
        } else {
            
            if (holder.getItemViewType() == TAG_SELECTED) {
                ((ViewHolderSelected) holder).bindView(imageGalleryModels.get(position - 1),
                        position + 1);
                
            } else {
                
                ((ViewHolder) holder).bindView(imageGalleryModels.get(position - 1), position + 1);
                
            }
        }

   /*     boolean animation = true;
        if (animation) {
            animation = false;
            Animation loadAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_in);
            holder.itemView.startAnimation(loadAnimation);
        } else {
            animation = true;
            Animation loadAnimation = AnimationUtils.loadAnimation(context, android.R.anim.fade_out);
            holder.itemView.startAnimation(loadAnimation);
        }*/
    }
    
    @Override
    public int getItemCount() {
        return imageGalleryModels.size() + 2;
    }
    
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        assert wm != null;
        Display display = wm.getDefaultDisplay();
        Point   size    = new Point();
        display.getSize(size);
        return size.x;
    }
    
    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgIndicator;
        
        public ViewHolder(View itemView) {
            super(itemView);
            imgIndicator = itemView.findViewById(R.id.img_indicator);
        }
        
        void bindView(final ProductPhotoModel model, final int pos) {
            Glide.with(context)
                    .load(model.getUrl())
                    .thumbnail(0.1f)
                    .into(imgIndicator);
            
            itemView.getLayoutParams().width = getScreenWidth() / 3;
            
            imgIndicator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positionFounder.initPosition(pos - 2);
                }
            });
        }
    }
    
    class ViewHolderSelected extends RecyclerView.ViewHolder {
        ImageView imgIndicator;
        
        public ViewHolderSelected(View itemView) {
            super(itemView);
            imgIndicator = itemView.findViewById(R.id.img_indicator);
        }
        
        void bindView(final ProductPhotoModel model, final int pos) {
            Glide.with(context)
                    .load(model.getUrl())
                    .thumbnail(0.1f)
                    .into(imgIndicator);
            
            itemView.getLayoutParams().width = getScreenWidth() / 3;
        }
    }
    
    class ViewHolderEmpty extends RecyclerView.ViewHolder {
        ImageView imgIndicator;
        
        public ViewHolderEmpty(View itemView) {
            super(itemView);
            imgIndicator = itemView.findViewById(R.id.img_indicator);
        }
        
        void bindView(final int pos) {
            Glide.with(context)
                    .load("")
                    .thumbnail(0.1f)
                    .into(imgIndicator);
            
            itemView.getLayoutParams().width = getScreenWidth() / 3;
            
        }
    }
    
}



