package ir.sanatisharif.android.konkur96.listener;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public interface OnItemClickListener {
    void onItemClick(int position, Object item, View view, RecyclerView.ViewHolder vh);
}