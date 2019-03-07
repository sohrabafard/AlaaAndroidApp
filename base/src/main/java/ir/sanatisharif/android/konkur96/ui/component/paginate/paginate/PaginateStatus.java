package ir.sanatisharif.android.konkur96.ui.component.paginate.paginate;

enum  PaginateStatus {

    NO_MORE_ITEMS, LOADING, ERROR;

    public static PaginateStatus getStatus(boolean loadedAllItems, boolean adapterError) {
        if (loadedAllItems) return NO_MORE_ITEMS;
        else if (adapterError) {
            return ERROR;
        } else {
            return LOADING;
        }
    }
}
