package ir.sanatisharif.android.konkur96.ui.component.paginate.callback;


public interface PaginateView {

    void showPaginateLoading(boolean show);

    void showPaginateError(boolean show);

    void setPaginateNoMoreData(boolean show);
}
