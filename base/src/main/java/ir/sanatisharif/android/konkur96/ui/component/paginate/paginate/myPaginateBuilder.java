package ir.sanatisharif.android.konkur96.ui.component.paginate.paginate;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ir.sanatisharif.android.konkur96.ui.component.paginate.callback.OnLoadMoreListener;
import ir.sanatisharif.android.konkur96.ui.component.paginate.item.ErrorItem;
import ir.sanatisharif.android.konkur96.ui.component.paginate.item.LoadingItem;

public final class myPaginateBuilder {


    private RecyclerView       recyclerView;
    private OnLoadMoreListener loadMoreListener;
    private LoadingItem        loadingItem;
    private ErrorItem          errorItem;
    private int                loadingTriggerThreshold = 0;


    myPaginateBuilder(@NonNull RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }


    /**
     * This method setup OnLoadMoreListener object, which will called when you need load data
     *
     * @param loadMoreListener object of {@link OnLoadMoreListener}
     * @return current object
     */
    public myPaginateBuilder setOnLoadMoreListener(@NonNull OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
        return this;
    }


    /**
     * Set the offset from the end of the list at which the load more event needs to be triggered. Default offset
     * if 5.
     *
     * @param loadingTriggerThreshold number of items from the end of the list.
     * @return current object
     */
    public myPaginateBuilder setLoadingTriggerThreshold(@IntRange(from = 0) int loadingTriggerThreshold) {
        this.loadingTriggerThreshold = loadingTriggerThreshold;
        return this;
    }

    /**
     * This method set custom loading item.
     *
     * @param loadingItem is implementation of {@link LoadingItem}
     * @return current object
     */
    public myPaginateBuilder setCustomLoadingItem(@NonNull LoadingItem loadingItem) {
        this.loadingItem = loadingItem;
        return this;
    }

    /**
     * This method set custom error item.
     *
     * @param errorItem is implementation of {@link ErrorItem}
     * @return current object
     */
    public myPaginateBuilder setCustomErrorItem(@NonNull ErrorItem errorItem) {
        this.errorItem = errorItem;
        return this;
    }

    /**
     * This method build all configurations
     *
     * @return object of {@link myPaginate}
     */
    public myPaginate build() {
        if (loadingItem == null) {
            loadingItem = LoadingItem.DEFAULT;
        }
        if (errorItem == null) {
            errorItem = ErrorItem.DEFAULT;
        }

        return new myPaginate(recyclerView, loadMoreListener, loadingTriggerThreshold, loadingItem, errorItem);
    }


}
