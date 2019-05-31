package ir.sanatisharif.android.konkur96.model.filter;

import ir.sanatisharif.android.konkur96.api.Models.ContentModel;
import ir.sanatisharif.android.konkur96.app.AppConstants;

/**
 * Created by Mohamad on 2/23/2019.
 */

public class PamphletCourse extends ContentModel implements FilterBaseModel {

    @Override
    public int getViewType() {
        return AppConstants.FILTER_PAMPHLET;
    }
}
