package ir.sanatisharif.android.konkur96.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.ui.GlideApp;

/**
 * Created by Mohamad on 10/25/2018.
 */

public class Utils {


    private Utils() {
        // no instance
    }

    public static String getRootDirPath(Context context) {
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            File file = ContextCompat.getExternalFilesDirs(context.getApplicationContext(),
                    null)[0];
            return file.getAbsolutePath();
        } else {
            return context.getApplicationContext().getFilesDir().getAbsolutePath();
        }
    }

    public static int getVersionCode() {
        int versionCode = 0;
        PackageInfo pInfo = null;
        try {
            pInfo = AppConfig.context.getPackageManager().getPackageInfo(AppConfig.context.getPackageName(), 0);
            versionCode = pInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return versionCode;
    }

    public static String getProgressDisplayLine(long currentBytes, long totalBytes) {
        return getBytesToMBString(currentBytes) + "/" + getBytesToMBString(totalBytes);
    }

    private static String getBytesToMBString(long bytes) {
        return String.format(Locale.ENGLISH, "%.2fMb", bytes / (1024.00 * 1024.00));
    }

    public static Boolean validEmail(String email) {

        String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher match = pattern.matcher(email);

        return match.matches();
    }

    public static Boolean validUserName(String userName) {

        String EMAIL_PATTERN = "[a-zA-Z0-9.\\-_]{3,}";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher match = pattern.matcher(userName);

        return match.matches();
    }

    public static Boolean validPhone(String phone) {

        String expression = "(\\+98|0)?9\\d{9}";

        Pattern pattern = Pattern.compile(expression);
        Matcher match = pattern.matcher(phone);

        return match.matches();
    }


    /**
     * get fileName form Url  https://developer.android.com/images/training/appbar/appbar_basic.png -> appbar_basic.png
     *
     * @param url
     * @return
     */
    public static String getFileNameFromUrl(String url) {

        int index = url.lastIndexOf("/");

        return url.substring(index + 1);
    }

    /**
     * get fileName and trim extension Ex: (test.mp4 -> test)
     *
     * @param fileName
     * @return
     */
    public static String getFileName(String fileName) {

        int index = fileName.lastIndexOf(".");
        return fileName.substring(0, index);
    }


    public static String getFileSize(long size) {
        if (size <= 0)
            return "0";
        final String[] units = new String[]{"بایت", "کیلوبایت", "مگابابت", "گیگابایت", "ترابایت"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

    public static String convertTime(long millis) {

        long minute = TimeUnit.MILLISECONDS.toMinutes(millis);
        long sec = TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

        if (minute == 0)
            return String.format("%d ثانیه", sec);
        return String.format(" %d دقیقه %d ثانیه", minute, sec);
    }

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static int pxToDp(int px, Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int dpToPx(int dp, Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getSize(size);
        } catch (NoSuchMethodError e) {
            // For lower than api 11
            size.x = display.getWidth();
            size.y = display.getHeight();
        }
        return size;
    }

    public static void share(String shareText, Context c) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, shareText);
        intent.setType("text/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        c.startActivity(Intent.createChooser(intent, ""));
    }

    public static void loadUrl(String url, Context c) {
        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            AppConfig.currentActivity.startActivity(browserIntent);
        }
    }

    public static Uri addVideoToGallery(File videoFile, Activity activity) {
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Video.Media.TITLE, videoFile.getName());
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.DATA, videoFile.getAbsolutePath());
        return activity.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
    }

    /**
     * c?set=191&contentOnly=1
     *
     * @param contentUrl
     * @return
     */
    public static List<String> getParamsFromUrl(String contentUrl) {

        if (contentUrl == null)
            return null;
        if (!contentUrl.contains("&") && !contentUrl.contains("?"))
            return null;
        String[] t = contentUrl.substring(contentUrl.lastIndexOf("?") + 1).split("&");
        String[] params = new String[t.length];
        for (int i = 0; i < t.length; i++) {
            params[i] = t[i].substring(t[i].lastIndexOf("=") + 1);
        }
        return Arrays.asList(params);
    }

    public static void loadGlide(ImageView img, String url, int width, int height) {

        GlideApp.with(AppConfig.context)
                .load(url)
                .override(width, height)
                .fitCenter()
                //.transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.round_image)))
                .into(new SimpleTarget<Drawable>(460, 259) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        img.setImageDrawable(resource);
                    }
                });
    }

    public static class ValidNationalCode {

        private boolean valid;
        private String message;

        public boolean isValid() {
            return valid;
        }

        public String getMessage() {
            return message;
        }

        public void check(String code) {

            String expression = "\\d{10}";
            int len = code.length();
            int sum = 0, div = 0, control = 0;

            Pattern pattern = Pattern.compile(expression);
            Matcher match = pattern.matcher(code);

            if (!match.matches()) {
                valid = false;
                message = "فرمت کدملی درست نیست!";
                return;
            }

            for (int i = 0; i < (len - 1); i++) {
                sum += Integer.parseInt(code.substring(i, i + 1)) * (10 - i);
            }
            div = sum % 11;
            control = Integer.parseInt(code.substring(9));

            if ((div < 2 && div == control) || (div >= 2 && div == (11 - control))) {
                valid = true;
                message = "";
                return;
            } else {
                valid = false;
                message = "کدملی معتبر نیست!";
                return;
            }
        }

    }
}
