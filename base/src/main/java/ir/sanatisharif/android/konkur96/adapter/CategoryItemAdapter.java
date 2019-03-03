package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.fragment.ExtraItemFrg;
import ir.sanatisharif.android.konkur96.fragment.FilterTagsFrg;
import ir.sanatisharif.android.konkur96.model.main_page.Set;
import ir.sanatisharif.android.konkur96.ui.view.CustomItemView;
import ir.sanatisharif.android.konkur96.utils.Utils;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class CategoryItemAdapter extends RecyclerView.Adapter<CategoryItemAdapter.CategoryHolder> {

    private List<Set> itemsList;
    private Context mContext;


    public CategoryItemAdapter(Context context, List<Set> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {

        CustomItemView itemView = new CustomItemView(parent.getContext());
        itemView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        return new CategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final CategoryHolder holder, final int position) {

        final Set item = itemsList.get(position);
        holder.customItemView.setClickItem(position, item);
        holder.customItemView.setTitle(item.getShortName());
        holder.customItemView.setAuthor(item.getAuthor().getLastName());
        holder.customItemView.setContentCount(item.getContentsCount());
        holder.customItemView.setImage(item.getPhoto());

        holder.getCustomCatItem().setOnClickItem(new CustomItemView.OnClickItem() {
            @Override
            public void OnClick(int position, Object item) {

                String url = itemsList.get(position).getContentUrl();
                addFrg(FilterTagsFrg.newInstance(url, null), "DetailsVideoFrg");
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