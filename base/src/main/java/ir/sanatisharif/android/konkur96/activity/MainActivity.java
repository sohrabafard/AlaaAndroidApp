package ir.sanatisharif.android.konkur96.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Stack;

import ir.sanatisharif.android.konkur96.R;

import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.UpdateInfoDialogFrg;
import ir.sanatisharif.android.konkur96.fragment.AllaMainFrg;
import ir.sanatisharif.android.konkur96.fragment.CongressMainFrg;
import ir.sanatisharif.android.konkur96.fragment.DashboardMainFrg;
import ir.sanatisharif.android.konkur96.fragment.DashboardMainFrg1;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.fragment.ForumMainFrg;
import ir.sanatisharif.android.konkur96.fragment.RegisterFrg;
import ir.sanatisharif.android.konkur96.fragment.ShopMainFragment;
import ir.sanatisharif.android.konkur96.fragment.VideoDownloadedFrg;
import ir.sanatisharif.android.konkur96.fragment.WebLandingFragment;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackMessage;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.service.NetworkChangedReceiver;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;
import ir.sanatisharif.android.konkur96.utils.Utils;

//https://blog.iamsuleiman.com/bottom-navigation-bar-android-tutorial/
public class MainActivity extends ActivityBase implements AHBottomNavigation.OnTabSelectedListener, ICheckNetwork {

    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = "MainActivity";
    private NetworkChangedReceiver networkChangedReceiver;

    private static AHBottomNavigation bottomNavigation;
    private static Stack<Fragment> fragments;
    private static FragmentManager fm;

    //--- primitive define type-----
    private long back_pressed;

    public static MainActivity newInstance() {

        return new MainActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        containerHeight(this);
        fragments = new Stack<>();
        fm = getSupportFragmentManager();

        //---------initialize UI--------
        initUI();

        //-----------add FirstFragment
        addFrg(AllaMainFrg.newInstance(), "alla");

        //-------- handle deep link
        handleIntent(getIntent());

        // retrieve firebase token
        // retrieveToken();


        if (MyPreferenceManager.getInatanse().getLastVersionCode() < Utils.getVersionCode()) {

            UpdateInfoDialogFrg updateInfoDialogFrg = new UpdateInfoDialogFrg();
            updateInfoDialogFrg.show(getSupportFragmentManager(), "");

        }

        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

  /*  private void customResponseObject() {

//http://www.yakhmakgroup.ir/jokLike/v1/service.php?action=getMessage
        GsonRequest gsonRequest = new GsonRequest("http://www.yakhmakgroup.ir/jokLike/v1/",
                CustomResponseList.class, null, new Response.Listener() {

            @Override

            public void onResponse(Object response) {

                // Handle response

            }

        }, new Response.ErrorListener() {

            @Override

            public void onErrorResponse(VolleyError error) {

                // Handle error
                NetworkResponse networkResponse = error.networkResponse;
                if (networkResponse != null) {
                    // HTTP Status Code: 401 Unauthorized
                }

            }

        });

        // Add gson request to volley request queue.

        ApiVolley.getInstance().addToRequestQueue(gsonRequest, "");

    }*/

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        itemSelect(position);
        return true;
    }

    @Override
    public void onBackPressed() {

        boolean flag;
        if (fragments.lastElement() instanceof VideoDownloadedFrg) {
            flag = ((VideoDownloadedFrg) fragments.lastElement()).onBack();
            if (!flag)
                close();

        } else
            close();

    }

    private void initUI() {

        bottomNavigation = findViewById(R.id.bottom_navigation);

        ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("", R.drawable.tab_home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("", R.drawable.ic_friend);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("", R.drawable.ic_forum);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem("", R.drawable.ic_user);
        AHBottomNavigationItem item5 = new AHBottomNavigationItem("", R.drawable.ic_shopping_cart);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);
        bottomNavigationItems.add(item5);

        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        bottomNavigation.setOnTabSelectedListener(this);
    }

    private void handleIntent(Intent intent) {

        String action = intent.getAction();  // android.intent.action.VIEW
        String data = intent.getDataString();// https://sanatisharif.ir/c/8087

        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            // Ex: String recipeId = data.substring(data.lastIndexOf("/") + 1);
            Uri appLinkData = intent.getData();
            //  Log.i(TAG, "handleIntent1: " + appLinkData);
            // Log.i(TAG, "handleIntent: " + appLinkData.getPath());

            switch (appLinkData.getPath()) {
                case "/":
                    Log.i(TAG, "handleIntent4: sanatisharif");
                    break;
                case "/c":
                    Log.i(TAG, "handleIntent4: c");
                    addFrg(DetailsVideoFrg.newInstance(), "RegisterFrg");
                    break;
                case "/product":
                    Log.i(TAG, "handleIntent4: product");
                    break;
                case "/login":
                    Log.i(TAG, "handleIntent4: login");
                    addFrg(RegisterFrg.newInstance(), "RegisterFrg");
                    break;
            }
        }

    }

    private void retrieveToken() {

        //Log.i(TAG, "retrieveToken: " + MyPreferenceManager.getInatanse().getFirebaseToken());
        if (MyPreferenceManager.getInatanse().getFirebaseToken().length() == 0) {
            FirebaseApp.initializeApp(this);
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.i(TAG, "getInstanceId failed", task.getException());
                                return;
                            }

                            String token = task.getResult().getToken();
                            MyPreferenceManager.getInatanse().setFirebaseToken(token);
                        }
                    });
        }
    }

    private void itemSelect(int tab_id) {

        switch (tab_id) {
            case 0:
                manageStack();
                //  addFrg(AllaMainFrg.newInstance(), "alla");
                break;

            case 1:
                manageStack();
                addFrg(CongressMainFrg.newInstance(), "CongressMainFrg");
                break;

            case 2:
                //todo: test web landing here
                manageStack();
                //addFrg(ForumMainFrg.newInstance(), "ForumMainFrg");
                addFrg(WebLandingFragment.newInstance("https://sanatisharif.ir"), "ForumMainFrg");
                break;

            case 3:
                manageStack();
                addFrg(DashboardMainFrg1.newInstance(), "DashboardMainFrg");
                break;
            case 4:
                manageStack();
                addFrg(ShopMainFragment.newInstance(), "ShopMainFragment");
                break;
        }

    }

    public static void addFrg(Fragment frg, String tag) {

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.fl_container, frg, tag);
        // transaction.setCustomAnimations(R.anim.left_enter, R.anim.right_out);

        if (fragments.size() == 0) {
            fragments.push(frg);

        } else {
            fragments.lastElement().onPause();
            transaction.hide(fragments.lastElement());
            fragments.push(frg);
        }
        transaction.commit();
    }

    public void close() {

        if (fragments.size() > 1) {

            FragmentTransaction transaction = fm.beginTransaction();
            fragments.lastElement().onPause();
            transaction.remove(fragments.pop());
            fragments.lastElement().onResume();
            transaction.show(fragments.lastElement());
            transaction.commit();

           // Log.i("LOG", "addFrg close " + fragments.lastElement().getTag());
            if (fragments.lastElement() instanceof AllaMainFrg) {
                bottomNavigation.setCurrentItem(0, false);
            } else if (fragments.lastElement() instanceof CongressMainFrg) {
                bottomNavigation.setCurrentItem(1, false);
            } else if (fragments.lastElement() instanceof ForumMainFrg) {
                bottomNavigation.setCurrentItem(2, false);
            } else if (fragments.lastElement() instanceof DashboardMainFrg) {
                bottomNavigation.setCurrentItem(3, false);
            }


        } else {

            twiceClick();
        }
    }

    private void manageStack() {

        FragmentTransaction transaction = fm.beginTransaction();

        boolean showHomeFrg = true;

        for (int i = 1; i < fragments.size(); i++) {
            Fragment f = fragments.pop();
            transaction.remove(f).commit();
            showHomeFrg = false;
        }
        if (!showHomeFrg) {
            transaction.show(fragments.lastElement());
        }
    }

    private void twiceClick() {

        if (back_pressed + 1000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            toastShow(getResources().getString(R.string.twiceClick), MDToast.TYPE_INFO);
        }
        back_pressed = System.currentTimeMillis();
    }

    @Override
    public void onCheckNetwork(Boolean flag) {
    }

    @Subscribe
    public void getMessage(Events.CloseFragment closeFragment) {

        close();
    }
}
