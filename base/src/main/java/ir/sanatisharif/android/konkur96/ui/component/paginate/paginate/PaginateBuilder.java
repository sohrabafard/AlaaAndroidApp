package ir.sanatisharif.android.konkur96.ui.component.paginate.paginate;

import androidx.recyclerview.widget.RecyclerView;

import ir.sanatisharif.android.konkur96.ui.component.paginate.callback.OnLoadMore;
import ir.sanatisharif.android.konkur96.ui.component.paginate.callback.OnLoadMoreListener;
import ir.sanatisharif.android.konkur96.ui.component.paginate.item.ErrorItem;
import ir.sanatisharif.android.konkur96.ui.component.paginate.item.LoadingItem;

@Deprecated
public class PaginateBuilder {


    private RecyclerView recyclerView;
    private OnLoadMore paginateCallback;
    private OnLoadMoreListener loadMoreListener;
    private LoadingItem loadingItem;
    private ErrorItem errorItem;
    private int loadingTriggerThreshold = 0;

    /**
     * @deprecated use {@link myPaginateBuilder}
     */
    @Deprecated
    public PaginateBuilder() {
    }


    /**
     * This method setup recyclerView
     *
     * @param recyclerView which you want to paginate
     * @return current object
     */
    @Deprecated
    public PaginateBuilder with(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        return this;
    }


    /**
     * @return current object
     * @deprecated use method {@link #setOnLoadMoreListener(OnLoadMoreListener)}
     */
    @Deprecated
    public PaginateBuilder setCallback(OnLoadMore paginateCallback) {
        this.paginateCallback = paginateCallback;
        return this;
    }

    /**
     * This method setup OnLoadMoreListener object, which will called when you need load data
     *
     * @param loadMoreListener object of {@link OnLoadMoreListener}
     * @return current object
     */
    @Deprecated
    public PaginateBuilder setOnLoadMoreListener(OnLoadMoreListener loadMoreListener) {
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
    @Deprecated
    public PaginateBuilder setLoadingTriggerThreshold(int loadingTriggerThreshold) {
        this.loadingTriggerThreshold = loadingTriggerThreshold;
        return this;
    }

    /**
     * This method set custom loading item.
     *
     * @param loadingItem is implementation of {@link LoadingItem}
     * @return current object
     */
    @Deprecated
    public PaginateBuilder setCustomLoadingItem(LoadingItem loadingItem) {
        this.loadingItem = loadingItem;
        return this;
    }

    /**
     * This method set custom error item.
     *
     * @param errorItem is implementation of {@link ErrorItem}
     * @return current object
     */
    @Deprecated
    public PaginateBuilder setCustomErrorItem(ErrorItem errorItem) {
        this.errorItem = errorItem;
        return this;
    }

    /**
     * This method build all configurations
     *
     * @return object of {@link Paginate}
     */
    @Deprecated
    public Paginate build() {
        if (loadingItem == null) {
            loadingItem = LoadingItem.DEFAULT;
        }
        if (errorItem == null) {
            errorItem = ErrorItem.DEFAULT;
        }

        return new Paginate(recyclerView, paginateCallback, loadMoreListener, loadingTriggerThreshold, loadingItem, errorItem);
    }


}
