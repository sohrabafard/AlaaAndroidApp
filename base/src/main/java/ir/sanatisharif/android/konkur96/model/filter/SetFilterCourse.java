package ir.sanatisharif.android.konkur96.model.filter;

import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.model.main_page.Set;

/**
 * Created by Mohamad on 2/23/2019.
 */

public class SetFilterCourse extends Set implements FilterBaseModel {
    @Override
    public int getViewType() {
        return AppConstants.FILTER_SET;
    }
}
