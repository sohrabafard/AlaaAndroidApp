package ir.sanatisharif.android.konkur96.utils;


import com.bumptech.glide.request.RequestOptions;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;

/**
 * Created by Mohamad on 11/14/2018.
 */

public class URLImageGetter implements ImageGetter {

    final TextView mTextView;
    ArrayList<Target> targets;
    Context mContext;

    public URLImageGetter(Context ctx, TextView tv) {
        this.mTextView = tv;
        this.mContext = ctx;
        this.targets = new ArrayList<Target>();
    }

    @Override
    public Drawable getDrawable(String url) {

        RequestOptions myOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.mipmap.ic_launcher)
                .override(100, 100);
/*        Glide.with(AppConfig.context)
                .asBitmap()
                .apply(myOptions)
                .load(url)
                .into(target);*/

        final UrlDrawable urlDrawable = new UrlDrawable();
//        GlideRequest load = GlideApp.with(mContext).asBitmap().load(url).apply(myOptions);
//        final Target target = new BitmapTarget(urlDrawable);
//        targets.add(target);
//        load.into(target);
        return urlDrawable;
    }

    private class BitmapTarget extends SimpleTarget<Bitmap> {

        private final UrlDrawable urlDrawable;
        Drawable drawable;

        public BitmapTarget(UrlDrawable urlDrawable) {
            this.urlDrawable = urlDrawable;
        }

        @Override
        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
            drawable = new BitmapDrawable(mContext.getResources(), resource);

            mTextView.post(new Runnable() {
                @Override
                public void run() {
                    int w = mTextView.getWidth();
                    int hh = drawable.getIntrinsicHeight();
                    int ww = drawable.getIntrinsicWidth();
                    int newHeight = hh * (w) / ww;
                    Rect rect = new Rect(0, 0, w, newHeight);
                    drawable.setBounds(rect);
                    urlDrawable.setBounds(rect);
                    urlDrawable.setDrawable(drawable);
                    mTextView.setText(mTextView.getText());
                    mTextView.invalidate();
                }
            });
        }
    }

    class UrlDrawable extends BitmapDrawable {
        private Drawable drawable;

        @SuppressWarnings("deprecation")
        public UrlDrawable() {
        }

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null)
                drawable.draw(canvas);
        }

        public Drawable getDrawable() {
            return drawable;
        }

        public void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }
    }
}
