package ir.sanatisharif.android.konkur96.listener;

import android.support.v7.widget.RecyclerView;

/**
 * Created by Mohamad on 3/5/2019.
 */

public interface ScrollOnRecycler {
    void scrollUp(RecyclerView recyclerView, int itemCount);

    void scrollDown(RecyclerView recyclerView, int itemCount);
}
