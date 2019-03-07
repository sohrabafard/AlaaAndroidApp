package ir.sanatisharif.android.konkur96.ui.component.paginate.paginate;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import ir.sanatisharif.android.konkur96.ui.component.paginate.callback.OnAdapterChangeListener;
import ir.sanatisharif.android.konkur96.ui.component.paginate.callback.OnLoadMoreListener;
import ir.sanatisharif.android.konkur96.ui.component.paginate.callback.OnRepeatListener;
import ir.sanatisharif.android.konkur96.ui.component.paginate.item.DefaultGridLayoutItem;
import ir.sanatisharif.android.konkur96.ui.component.paginate.item.ErrorItem;
import ir.sanatisharif.android.konkur96.ui.component.paginate.item.LoadingItem;
import ir.sanatisharif.android.konkur96.ui.component.paginate.paginate.grid.WrapperSpanSizeLookup;


public final class myPaginate implements OnAdapterChangeListener, OnRepeatListener {


    private final int loadingTriggerThreshold;
    private final RecyclerView recyclerView;
    private final OnLoadMoreListener loadMoreListener;
    private final LoadingItem loadingItem;
    private final ErrorItem errorItem;

    private WrapperAdapter wrapperAdapter;
    private WrapperAdapterObserver wrapperAdapterObserver;
    private RecyclerView.Adapter userAdapter;
    private WrapperSpanSizeLookup wrapperSpanSizeLookup;

    private boolean isError;
    private boolean isLoading;
    private boolean isLoadedAllItems;

    private final RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            checkScroll();
        }
    };


    myPaginate(RecyclerView recyclerView,
               OnLoadMoreListener loadMoreListener,
               int loadingTriggerThreshold,
               LoadingItem loadingItem,
               ErrorItem errorItem) {
        this.recyclerView = recyclerView;
        this.loadMoreListener = loadMoreListener;
        this.loadingTriggerThreshold = loadingTriggerThreshold;
        this.loadingItem = loadingItem;
        this.errorItem = errorItem;
        setupWrapper();
        setupScrollListener();
    }


    public static myPaginateBuilder with(@NonNull RecyclerView recyclerView) {
        return new myPaginateBuilder(recyclerView);
    }

    private void setupWrapper() {
        this.userAdapter = recyclerView.getAdapter();
        wrapperAdapter = new WrapperAdapter(userAdapter, loadingItem, errorItem);
        wrapperAdapterObserver = new WrapperAdapterObserver(this, wrapperAdapter);
        userAdapter.registerAdapterDataObserver(wrapperAdapterObserver);
        recyclerView.setAdapter(wrapperAdapter);
        wrapperAdapter.setRepeatListener(this);
        checkGridLayoutManager();
    }

    private void checkGridLayoutManager() {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            DefaultGridLayoutItem item = new DefaultGridLayoutItem(recyclerView.getLayoutManager());
            wrapperSpanSizeLookup = new WrapperSpanSizeLookup(((GridLayoutManager) recyclerView.getLayoutManager()).getSpanSizeLookup(),
                    item,
                    wrapperAdapter);
            ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanSizeLookup(wrapperSpanSizeLookup);
        }
    }


    private void setupScrollListener() {
        recyclerView.addOnScrollListener(scrollListener);
    }


    private void checkAdapterState() {
        if (isCanLoadMore()) {
            if (loadMoreListener != null) {
                loadMoreListener.onLoadMore();
            }
        }
    }

    private boolean isCanLoadMore() {
        return !isLoading && !isError && !isLoadedAllItems;
    }

    @Override
    public void onAdapterChange() {

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                final PaginateStatus status = PaginateStatus.getStatus(isLoadedAllItems, isError);
                wrapperAdapter.stateChanged(status);
                checkScroll();
            }
        });
    }


    private void checkScroll() {
        if (ScrollUtils.isOnBottom(recyclerView, loadingTriggerThreshold)) {
            checkAdapterState();
        }
    }


    /**
     * This method will show error on the bottom of your recyclerView.
     *
     * @param isShowError - true if show, false if hide
     */
    public void showError(boolean isShowError) {
        if (isShowError) {
            isError = true;
            wrapperAdapter.stateChanged(PaginateStatus.ERROR);
            ScrollUtils.fullScrollToBottom(recyclerView, wrapperAdapter);
        } else {
            isError = false;
        }
    }


    /**
     * This method will show error on the bottom of your recyclerView.
     *
     * @param show - true if show, false if hide
     */
    public void showLoading(boolean show) {
        if (show) {
            isLoading = true;
            wrapperAdapter.stateChanged(PaginateStatus.LOADING);
        } else {
            isLoading = false;
        }
    }

    /**
     * This method  show error on the bottom of your recyclerView.
     *
     * @param isNoMoreItems - true if items ended, false if no
     */
    public void setNoMoreItems(boolean isNoMoreItems) {
        if (isNoMoreItems) {
            this.isLoadedAllItems = true;
            wrapperAdapter.stateChanged(PaginateStatus.NO_MORE_ITEMS);
        } else {
            this.isLoadedAllItems = false;
        }
    }

    @Override
    public void onClickRepeat() {
        showError(false);
        checkScroll();
    }

    /**
     * This method unsubscribe observer and change listeners reference to null
     * for avoid memory leaks.
     */
    public void unbind() {
        recyclerView.removeOnScrollListener(scrollListener);

        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            wrapperAdapter.unbind();
            userAdapter.unregisterAdapterDataObserver(wrapperAdapterObserver);
            recyclerView.setAdapter(userAdapter);

        } else if (recyclerView.getLayoutManager() instanceof GridLayoutManager && wrapperSpanSizeLookup != null) {
            GridLayoutManager.SpanSizeLookup spanSizeLookup = wrapperSpanSizeLookup.getWrappedSpanSizeLookup();
            ((GridLayoutManager) recyclerView.getLayoutManager()).setSpanSizeLookup(spanSizeLookup);
        }
    }

}

