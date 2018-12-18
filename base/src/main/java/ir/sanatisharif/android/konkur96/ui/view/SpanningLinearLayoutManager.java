package ir.sanatisharif.android.konkur96.ui.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mohamad on 10/11/2018.
 */

public class SpanningLinearLayoutManager extends LinearLayoutManager {

    public SpanningLinearLayoutManager(Context context) {
        super(context);
    }

    public SpanningLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public SpanningLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return spanLayoutSize(super.generateDefaultLayoutParams());
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(Context c, AttributeSet attrs) {
        return spanLayoutSize(super.generateLayoutParams(c, attrs));
    }

    @Override
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams lp) {
        return spanLayoutSize(super.generateLayoutParams(lp));
    }

    @Override
    public boolean checkLayoutParams(RecyclerView.LayoutParams lp) {
        return super.checkLayoutParams(lp);
    }

  /*  @Override
    public void onMeasure(RecyclerView.Recycler recycler, RecyclerView.State state, int widthSpec, int heightSpec) {

        Log.v("Chart onMeasure w", View.MeasureSpec.toString(widthSpec));
        Log.v("Chart onMeasure h", View.MeasureSpec.toString(heightSpec));

        int desiredWidth = 64 + getPaddingLeft() + getPaddingRight();
        int desiredHeight = 64+ getPaddingTop() + getPaddingBottom();

      //  setMeasuredDimension(measureDimension(desiredWidth, widthSpec),     );

        super.onMeasure(recycler, state, measureDimension(desiredWidth, widthSpec), measureDimension(desiredHeight, heightSpec));
    }*/

    private int measureDimension(int desiredSize, int measureSpec) {

        int result;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = desiredSize;
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }

        if (result < desiredSize){
            Log.e("ChartView", "The view is too small, the content might get cut");
        }
        return result;
    }

    private RecyclerView.LayoutParams spanLayoutSize(RecyclerView.LayoutParams layoutParams){
        if(getOrientation() == HORIZONTAL){
            layoutParams.width = (int) Math.round(getHorizontalSpace() / 3);
        }
        else if(getOrientation() == VERTICAL){
            layoutParams.height = (int) Math.round(getVerticalSpace() /  3);
        }
        return layoutParams;
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }
    @Override
    public boolean canScrollHorizontally() {
        return true;
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingRight() - getPaddingLeft();
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }
}
