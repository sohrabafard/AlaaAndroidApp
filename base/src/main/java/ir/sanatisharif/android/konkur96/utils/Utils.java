package ir.sanatisharif.android.konkur96.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.handler.EncryptedDownloadInterface;
import ir.sanatisharif.android.konkur96.handler.EncryptedDownloadRepository;

/**
 * Created by Mohamad on 10/25/2018.
 */

public class Utils {


    private static final String TAG = "Alaa\\Utils";

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
        int         versionCode = 0;
        PackageInfo pInfo       = null;
        try {
            pInfo =
                    AppConfig.context.getPackageManager().getPackageInfo(AppConfig.context.getPackageName(), 0);
            versionCode = pInfo.versionCode;
        }
        catch (PackageManager.NameNotFoundException e) {
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

        String
                EMAIL_PATTERN =
                "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher match   = pattern.matcher(email);

        return match.matches();
    }

    public static Boolean validUserName(String userName) {

        String EMAIL_PATTERN = "[a-zA-Z0-9.\\-_]{3,}";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher match   = pattern.matcher(userName);

        return match.matches();
    }

    public static Boolean validPhone(String phone) {

        String expression = "(\\+98|0)?9\\d{9}";

        Pattern pattern = Pattern.compile(expression);
        Matcher match   = pattern.matcher(phone);

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
        final String[]
                units       =
                new String[]{
                        "بایت",
                        "کیلوبایت",
                        "مگابابت",
                        "گیگابایت",
                        "ترابایت"
                };
        int     digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " +
               units[digitGroups];
    }

    public static String convertTime(long millis) {

        long minute = TimeUnit.MILLISECONDS.toMinutes(millis);
        long
             sec    =
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis));

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
        int
                       dp             =
                Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int dpToPx(int dp, Activity activity) {
        DisplayMetrics displayMetrics = activity.getResources().getDisplayMetrics();
        int
                       px             =
                Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static Point getScreenSize(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point   size    = new Point();
        try {
            display.getSize(size);
        }
        catch (NoSuchMethodError e) {
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

    public static Uri addVideoToGallery(File videoFile, Context context) {
        ContentValues values = new ContentValues(3);
        values.put(MediaStore.Video.Media.TITLE, videoFile.getName());
        values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
        values.put(MediaStore.Video.Media.DATA, videoFile.getAbsolutePath());
        return context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
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
        String[] t      = contentUrl.substring(contentUrl.lastIndexOf("?") + 1).split("&");
        String[] params = new String[t.length];
        for (int i = 0; i < t.length; i++) {
            params[i] = t[i].substring(t[i].lastIndexOf("=") + 1);
        }
        return Arrays.asList(params);
    }

    public static void loadGlide(ImageView img, String url, int width, int height) {

        RequestOptions options = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        Glide.with(AppConfig.context)
                .load(url)
                .apply(options)
                .thumbnail(0.1f)
                .into(img);
    }

    public static boolean isConnected() {
        try {
            android.net.ConnectivityManager
                    e =
                    (android.net.ConnectivityManager) AppConfig.context.getSystemService(
                            Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = e.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        catch (Exception e) {
            Log.w("LOG", e.toString());
        }

        return false;
    }

    public static boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public static void followRedirectedLink(Context context, Activity activity, String url, EncryptedDownloadInterface.Callback callback) {
        EncryptedDownloadRepository repository = new EncryptedDownloadRepository(activity);
        AuthToken.getInstant().get(context, activity, new AuthToken.Callback() {
            @Override
            public void run(@NonNull String token) {
                Log.i(TAG, "followRedirectedLink, has_token");
                repository.getDirectLink(url, token, callback);
            }

            @Override
            public void nill() {
                Log.i(TAG, "followRedirectedLink, without_token");
                repository.getDirectLink(url, null, callback);
            }
        });
    }

    public static class ValidNationalCode {

        private boolean valid;
        private String  message;

        public boolean isValid() {
            return valid;
        }

        public String getMessage() {
            return message;
        }

        public void check(String code) {

            String expression = "\\d{10}";
            int    len        = code.length();
            int    sum        = 0, div = 0, control = 0;

            Pattern pattern = Pattern.compile(expression);
            Matcher match   = pattern.matcher(code);

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
