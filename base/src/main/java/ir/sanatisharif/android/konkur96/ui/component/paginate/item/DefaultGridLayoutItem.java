package ir.sanatisharif.android.konkur96.ui.component.paginate.item;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;



public final class DefaultGridLayoutItem implements BaseGridLayoutManagerItem {
    private final int loadingListItemSpan;

    public DefaultGridLayoutItem(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            // By default full span will be used for loading list item
            loadingListItemSpan = ((GridLayoutManager) layoutManager).getSpanCount();
        } else {
            loadingListItemSpan = 1;
        }
    }

    @Override
    public int getSpanSize() {
        return loadingListItemSpan;
    }
}
