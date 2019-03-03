package ir.sanatisharif.android.konkur96.model.filter;

import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.model.DataCourse;

/**
 * Created by Mohamad on 2/23/2019.
 */

public class ArticleCourse extends DataCourse implements FilterBaseModel  {
    @Override
    public int getViewType() {
        return AppConstants.FILTER_ARTICLE;
    }
}
