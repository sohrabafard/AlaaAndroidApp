package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.Models.ContentModel;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.ui.view.CustomItemView;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class ContentItemAdapter extends RecyclerView.Adapter<ContentItemAdapter.ContentHolder> {
    
    private List<ContentModel> itemsList;
    private Context            mContext;
    
    
    public ContentItemAdapter(Context context, List<ContentModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }
    
    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {
        
        CustomItemView itemView = new CustomItemView(parent.getContext(), R.layout.content_item);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new ContentHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(final ContentHolder holder, final int position) {
        
        ContentModel item = itemsList.get(position);
        holder.customItemView.setTitle(item.getName());
        holder.customItemView.setAuthor(item.getAuthor().getFullName());
        holder.customItemView.setImage(item.getThumbnail());
        
        holder.getCustomCatItem().setOnClickItem(new CustomItemView.OnClickItem() {
            @Override
            public void OnClick(int position) {
                if (item.getContenttypeId() == 8)
                    // addFrg(DetailsVideoFrg.newInstance(item.getApiUrl().getV1()), "DetailsVideoFrg");
                    addFrg(DetailsVideoFrg.newInstance(item), "DetailsVideoFrg");
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }
    
    
    public class ContentHolder extends RecyclerView.ViewHolder {
        
        CustomItemView customItemView;
        
        public ContentHolder(View itemView) {
            super(itemView);
            customItemView = (CustomItemView) itemView;
        }
        
        public CustomItemView getCustomCatItem() {
            return customItemView;
        }
    }
    
}