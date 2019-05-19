package ir.sanatisharif.android.konkur96.ui.component.paginate.item;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


public interface BaseLinearLayoutManagerItem {

    RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

    void onBindViewHolder(RecyclerView.ViewHolder holder, int position);
}
