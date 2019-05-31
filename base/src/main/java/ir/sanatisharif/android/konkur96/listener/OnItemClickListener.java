package ir.sanatisharif.android.konkur96.listener;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public interface OnItemClickListener {
    void onItemClick(int position, Object item, View view, RecyclerView.ViewHolder vh);
}