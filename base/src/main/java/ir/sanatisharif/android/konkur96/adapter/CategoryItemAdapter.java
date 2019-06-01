package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.SetModel;
import ir.sanatisharif.android.konkur96.fragment.FilterTagsFrg;
import ir.sanatisharif.android.konkur96.ui.view.CustomItemView;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryHolder> {
    
    private List<SetModel> itemsList;
    private Context        mContext;
    
    public CategoryItemAdapter(Context context, List<SetModel> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }
    
    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {
        
        CustomItemView itemView = new CustomItemView(parent.getContext(), R.layout.category_item);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new CategoryHolder(itemView);
    }
    
    @Override
    public void onBindViewHolder(final CategoryHolder holder, final int position) {
        
        final SetModel item = itemsList.get(position);
        holder.customItemView.setClickItem(position);
        holder.customItemView.setTitle(item.getShortName());
        holder.customItemView.setAuthor(item.getAuthor().getLastName());
        holder.customItemView.setContentCount(item.getContentsCount());
        holder.customItemView.setImage(item.getPhoto());
        
        holder.getCustomCatItem().setOnClickItem(new CustomItemView.OnClickItem() {
            @Override
            public void OnClick(int position) {
                
                String url = itemsList.get(position).getContentUrl();
                addFrg(FilterTagsFrg.newInstance(url, null), "FilterTagsFrg");
            }
        });
    }
    
    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }
    
    public class CategoryHolder extends RecyclerView.ViewHolder {
        
        CustomItemView customItemView;
        
        public CategoryHolder(View itemView) {
            super(itemView);
            customItemView = (CustomItemView) itemView;
        }
        
        public CustomItemView getCustomCatItem() {
            return customItemView;
        }
        
    }
    
}