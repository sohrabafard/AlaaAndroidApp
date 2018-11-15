package ir.sanatisharif.android.konkur96.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.util.Util;

import java.security.MessageDigest;

/**
 * Created by Mohamad on 11/4/2018.
 */

public abstract class BitmapTransformation1 implements Transformation<Bitmap> {

    @NonNull
    @Override
    public final Resource<Bitmap> transform(@NonNull Context context, @NonNull Resource<Bitmap> resource,
                                            int outWidth, int outHeight) {
        if (!Util.isValidDimensions(outWidth, outHeight)) {
            throw new IllegalArgumentException(
                    "Cannot apply transformation on width: " + outWidth + " or height: " + outHeight
                            + " less than or equal to zero and not Target.SIZE_ORIGINAL");
        }
        BitmapPool bitmapPool = Glide.get(context).getBitmapPool();
        Bitmap toTransform = resource.get();
        int targetWidth = outWidth == Target.SIZE_ORIGINAL ? toTransform.getWidth() : outWidth;
        int targetHeight = outHeight == Target.SIZE_ORIGINAL ? toTransform.getHeight() : outHeight;
        Bitmap transformed = transform(context.getApplicationContext(), bitmapPool, toTransform, targetWidth, targetHeight);

        final Resource<Bitmap> result;
        if (toTransform.equals(transformed)) {
            result = resource;
        } else {
            result = BitmapResource.obtain(transformed, bitmapPool);
        }
        return result;
    }

    protected abstract Bitmap transform(@NonNull Context context, @NonNull BitmapPool pool,
                                        @NonNull Bitmap toTransform, int outWidth, int outHeight);

    @Override public abstract void updateDiskCacheKey(@NonNull MessageDigest messageDigest);

    @Override public abstract boolean equals(Object o);

    @Override public abstract int hashCode();
}
