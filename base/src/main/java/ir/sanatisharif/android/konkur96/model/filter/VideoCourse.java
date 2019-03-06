package ir.sanatisharif.android.konkur96.model.filter;

import android.support.v7.util.DiffUtil;

import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.model.DataCourse;

/**
 * Created by Mohamad on 2/23/2019.
 */

public class VideoCourse extends DataCourse implements FilterBaseModel {
    @Override
    public int getViewType() {
        return AppConstants.FILTER_VIDEO;
    }

//
//    // Allows the adapter to calculate the difference between the old list and new list. This also simplifies animations.
//    public static final DiffUtil.ItemCallback<VideoCourse> DIFF_CALLBACK = new DiffUtil.ItemCallback<VideoCourse>() {
//
//        // Check if items represent the same thing.
//        @Override
//        public boolean areItemsTheSame(VideoCourse oldItem, VideoCourse newItem) {
//            return oldItem.getId() == newItem.getId();
//        }
//
//        // Checks if the item contents have changed.
//        @Override
//        public boolean areContentsTheSame(VideoCourse oldItem, VideoCourse newItem) {
//            return true; // Assume Repository details don't change
//        }
//    };
}
