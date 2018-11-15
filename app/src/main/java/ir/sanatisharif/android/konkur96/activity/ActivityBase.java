package ir.sanatisharif.android.konkur96.activity;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;

import android.view.animation.Animation;
import android.widget.TextView;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityBase extends AppCompatActivity {


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        AppConfig.currentActivity = this;

    }

    public static void toastShow(final String message, int MDToastType) {

        int toastDurationInMilliSeconds = 700;

        final MDToast mToastToShow = MDToast.makeText(AppConfig.context, message, MDToastType);

        AppConfig.HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {

                mToastToShow.cancel();
            }
        }, toastDurationInMilliSeconds);

        mToastToShow.show();

    }

    public static int containerHeight(Activity ac) {
        DisplayMetrics dm = new DisplayMetrics();
        ac.getWindowManager().getDefaultDisplay().getMetrics(dm);

        AppConfig.width = dm.widthPixels;
        AppConfig.height = dm.heightPixels;


        return (int) (dm.heightPixels / 3);
    }

   /* @Override
    public void setContentView(int layoutResID) {

        LinearLayout fullView = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        // FrameLayout activityContainer = (FrameLayout) fullView.findViewById(R.id.activity_content);
        getLayoutInflater().inflate(layoutResID, fullView, true);
        super.setContentView(fullView);


    }*/

   /* public void showToolBar(String title) {

        toolbar = (Toolbar) findViewById(R.id.appBar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        toolbarTitle = (TextView) toolbar.findViewById(R.id.txtToolbarTitle);
        toolbarTitle.setText(title);
    }



    public void getDimKeyboard(final View root) {
        int h = 0;
        root.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int heightDiff = root.getRootView().getHeight() - root.getHeight();
                // IF height diff is more then 150, consider keyboard as visible.
                if (heightDiff > 150)
                    AppConfig.editer.putInt("kbHeight", heightDiff).commit();
                //  Log.i("LOG", "screen  " + root.getRootView().getHeight() + "  " + (root.getRootView().getHeight() - root.getHeight()));
            }
        });
    }



    public static String appSignature(Context context) {

        String currentSignature = "";
        try {
            Signature[] sigs = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures;


            for (Signature sig : sigs) {

                MessageDigest md = MessageDigest.getInstance("SHA");

                md.update(sig.toByteArray());

                currentSignature = Base64.encodeToString(md.digest(), Base64.DEFAULT);

            }

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return currentSignature;


    }

    public static void applayMenuFont(android.view.Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            MenuItem mi = menu.getItem(i);
            applyFontToMenuItem(mi);
        }
    }

    private static void applyFontToMenuItem(MenuItem mi) {
        ;
        SpannableString mNewTitle = new SpannableString(mi.getTitle());
        mNewTitle.setSpan(new CustomTypefaceSpan("", AppConfig.fontIRSensNumber), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mi.setTitle(mNewTitle);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    protected Boolean validEmail(String email) {

        String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher match = pattern.matcher(email);

        return match.matches();
    }

    protected void displaySizeScreen() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        AppConfig.HeightLayout = displaymetrics.heightPixels;
        AppConfig.WidthLayout = displaymetrics.widthPixels;
    }

    public void overrideFonts(Context context, final View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;
                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                TextView v1 = (TextView) v;
                v1.setTypeface(AppConfig.fontIRSensLight);
            }
        } catch (Exception e) {
        }
    }



    public int getVersonCode() {
        PackageManager manager = AppConfig.context.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(
                    AppConfig.context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info.versionCode;
    }

    public void getSize() {

        Point size = new Point();
        WindowManager w = getWindowManager();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            w.getDefaultDisplay().getSize(size);
            AppConfig.width = size.x - 10;
            AppConfig.height = size.y - 10;
        } else {
            Display d = w.getDefaultDisplay();
            AppConfig.width = d.getWidth() - 10;
            AppConfig.height = d.getHeight() - 10;
        }

    }*/
}
