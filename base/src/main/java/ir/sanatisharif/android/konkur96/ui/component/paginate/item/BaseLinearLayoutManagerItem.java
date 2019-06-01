package ir.sanatisharif.android.konkur96.ui.component.paginate.item;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;


public interface BaseLinearLayoutManagerItem {
    
    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);
    
    void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
}
