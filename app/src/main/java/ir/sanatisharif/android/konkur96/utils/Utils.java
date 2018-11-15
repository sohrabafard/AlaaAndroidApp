package ir.sanatisharif.android.konkur96.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static void loadUrl(String url, Context c) {
        if (URLUtil.isHttpsUrl(url) || URLUtil.isHttpUrl(url)) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            c.startActivity(browserIntent);
        }
    }

    public static Uri addVideoToGallery(File videoFile, Activity activity) {
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Video.Media.TITLE, videoFile.getName());
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.DATA, videoFile.getAbsolutePath());
        return activity.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
    }
}
