package ir.sanatisharif.android.konkur96.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.ArrayList;
import java.util.Stack;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.ApiRestClient;
import ir.sanatisharif.android.konkur96.api.ApiVolley;
import ir.sanatisharif.android.konkur96.api.CustomResponseList;
import ir.sanatisharif.android.konkur96.api.GsonRequest;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.fragment.AllaMainFrg;
import ir.sanatisharif.android.konkur96.fragment.CongressMainFrg;
import ir.sanatisharif.android.konkur96.fragment.ContentFrg;
import ir.sanatisharif.android.konkur96.fragment.DashboardMainFrg;
import ir.sanatisharif.android.konkur96.fragment.DashboardMainFrg1;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.fragment.ForumMainFrg;
import ir.sanatisharif.android.konkur96.fragment.VideoDownloadedFrg;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackMessage;
import ir.sanatisharif.android.konkur96.service.NetworkChangedReceiver;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.PreferenceManager;

//https://blog.iamsuleiman.com/bottom-navigation-bar-android-tutorial/
public class MainActivity extends ActivityBase implements AHBottomNavigation.OnTabSelectedListener, ICheckNetwork {

    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = "MainActivity";
    private NetworkChangedReceiver networkChangedReceiver;

    private AHBottomNavigation bottomNavigation;
    private static Stack<Fragment> fragments;
    private static FragmentManager fm;

    //--- primitive define type-----5853881mhfd  fallah.dellche@gmail.com
    ///AlaaAndroidApp
    private long back_pressed;

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
        addFrg(ContentFrg.newInstance(), "alla");

        //-------- handle deep link
        handleIntent(getIntent());

        // retrieve firebase token
        retrieveToken();

        ApiRestClient.postMessage("http://www.yakhmakgroup.ir/jokLike/v1/", null, new IServerCallbackMessage() {
            @Override
            public void onStart() {

            }

            @Override
            public void onFinish() {

            }

            @Override
            public void onSuccess(String str) {

                Log.i(TAG, "onSuccess: " + str);
            }

            @Override
            public void onFailure(String message) {

                Log.i(TAG, "onFailure: " + message);
            }
        });

        // customResponseObject();
    }



    private void customResponseObject() {

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

    }

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

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_HIDE);

        bottomNavigation.setOnTabSelectedListener(this);
    }

    private void handleIntent(Intent intent) {

        String action = intent.getAction();  // android.intent.action.VIEW
        String data = intent.getDataString();// https://sanatisharif.ir/c/8087
        if (Intent.ACTION_VIEW.equals(action) && data != null) {
            String recipeId = data.substring(data.lastIndexOf("/") + 1);
            Uri appLinkData = intent.getData();
            String prefix = data.substring(24);

            String path = prefix.substring(0, prefix.lastIndexOf("/"));
            if(data.equals("https://sanatisharif.ir"))
            {

            }
            else if (path.equals("c")) {
                // toastShow("c");
                addFrg(DetailsVideoFrg.newInstance(), "alla");
            }
            //https://sanatisharif.ir/c?search=عربی

            //https://sanatisharif.ir/product/search

            //https://sanatisharif.ir/login

        }

    }

    private void retrieveToken() {

        //Log.i(TAG, "retrieveToken: " + PreferenceManager.getInatanse().getFirebaseToken());
        if (PreferenceManager.getInatanse().getFirebaseToken().length() == 0) {
            FirebaseInstanceId.getInstance().getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                        @Override
                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                            if (!task.isSuccessful()) {
                                Log.i(TAG, "getInstanceId failed", task.getException());
                                return;
                            }

                            String token = task.getResult().getToken();
                            PreferenceManager.getInatanse().setFirebaseToken(token);
                        }
                    });
        }
    }

    private void itemSelect(int tab_id) {

        switch (tab_id) {
            case 0:
                addFrg(AllaMainFrg.newInstance(), "alla");
                break;

            case 1:
                addFrg(CongressMainFrg.newInstance(), "CongressMainFrg");
                break;

            case 2:
                addFrg(ForumMainFrg.newInstance(), "ForumMainFrg");
                break;

            case 3:
                addFrg(DashboardMainFrg1.newInstance(), "DashboardMainFrg");
                break;
        }

    }

    public static void addFrg(Fragment frg, String tag) {

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.fl_container, frg, tag);
        // Log.i("LOG", "addFrg: " + frg);
        if (fragments.size() == 0) {
            fragments.push(frg);

        } else {
            fragments.lastElement().onPause();
            transaction.hide(fragments.lastElement());
            fragments.push(frg);
        }
        transaction.commit();
    }

    private void close() {

        if (fragments.size() > 1) {

            FragmentTransaction transaction = fm.beginTransaction();
            fragments.lastElement().onPause();
            transaction.remove(fragments.pop());
            fragments.lastElement().onResume();
            transaction.show(fragments.lastElement());
            transaction.commit();

            //  Log.i("LOG", "addFrg close " + fragments.lastElement().getTag());
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
            // super.onBackPressed();
            //finish();
        }
    }

    private void manageStack() {

        FragmentTransaction transaction = fm.beginTransaction();
        for (int i = 0; i < fragments.size(); i++) {

            transaction.remove(fragments.pop());
            transaction.commit();
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
}
