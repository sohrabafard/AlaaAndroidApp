package ir.sanatisharif.android.konkur96.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.Toast;

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
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.api.Models.CardReviewModel;
import ir.sanatisharif.android.konkur96.api.Models.ErrorBase;
import ir.sanatisharif.android.konkur96.api.Models.PaymentRequest;
import ir.sanatisharif.android.konkur96.api.Models.PaymentResponse;
import ir.sanatisharif.android.konkur96.api.Models.PaymentVerificationRequest;
import ir.sanatisharif.android.konkur96.api.Models.PaymentVerificationResponse;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.UpdateInfoDialogFrg;
import ir.sanatisharif.android.konkur96.fragment.AllaMainFrg;
import ir.sanatisharif.android.konkur96.fragment.CongressMainFrg;
import ir.sanatisharif.android.konkur96.fragment.DashboardMainFrg;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.fragment.FilterTagsFrg;
import ir.sanatisharif.android.konkur96.fragment.ForumMainFrg;
import ir.sanatisharif.android.konkur96.fragment.ShopMainFragment;
import ir.sanatisharif.android.konkur96.fragment.VideoDownloadedFrg;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.service.NetworkChangedReceiver;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;
import ir.sanatisharif.android.konkur96.utils.Utils;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;
import static ir.sanatisharif.android.konkur96.app.AppConstants.AUTHTOKEN_TYPE_FULL_ACCESS;

//https://blog.iamsuleiman.com/bottom-navigation-bar-android-tutorial/
public class MainActivity extends ActivityBase implements AHBottomNavigation.OnTabSelectedListener, ICheckNetwork {

    private FirebaseAnalytics mFirebaseAnalytics;
    private static final String TAG = "MainActivity";
    private NetworkChangedReceiver networkChangedReceiver;
    private AccountInfo accountInfo;

    private static AHBottomNavigation bottomNavigation;
    private static Stack<Fragment> fragments;
    private static FragmentManager fm;
    private Repository repository;

    //--- primitive define type-----
    private long back_pressed;

    public static MainActivity newInstance() {

        return new MainActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new RepositoryImpl(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        accountInfo = new AccountInfo(getApplicationContext(), this);
        containerHeight(this);
        fragments = new Stack<>();
        fm = getSupportFragmentManager();

        //---------initialize UI--------
        initUI();

        //-----------add FirstFragment
        addFrg(AllaMainFrg.newInstance(), "alla");

        //-------- handle deep link
        if (getIntent() != null)
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
        AHBottomNavigationItem item1 = new AHBottomNavigationItem("", R.drawable.ic_home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem("", R.drawable.ic_friend);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem("", R.drawable.ic_message);
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

        if (action == null)
            return;
        if (action.equals("ir.sanatisharif.android.SETTING")) {
            startActivity(new Intent(AppConfig.currentActivity, SettingActivity.class));
        } else if (Intent.ACTION_VIEW.equals(action) && data != null) {
            Uri appLinkData = intent.getData();
            Log.i(TAG, "handleIntent1: " + appLinkData);
            Log.i(TAG, "handleIntent: " + appLinkData.getPath());


            if (appLinkData.getPath().startsWith("/c")) {
                if (data.contains("tags")) {
                    Log.i(TAG, "handleIntent1: tags " + data);
                    addFrg(FilterTagsFrg.newInstance(data, null), "FilterTagsFrg");
                } else {
                    Log.i(TAG, "handleIntent1: c " + data);
                    addFrg(DetailsVideoFrg.newInstance(data), "DetailsVideoFrg");
                }
            } else if (appLinkData.getPath().startsWith("/product")) {

            } else if (appLinkData.getPath().startsWith("/login")) {
                if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {
                    addFrg(DashboardMainFrg.newInstance(), "DashboardMainFrg");
                }
            } else if (appLinkData.getPath().startsWith("/zarinpal")) {

                if (data.contains("Status")) {

                    String mStatus = appLinkData.getQueryParameter("Status");
                    String amount = appLinkData.getQueryParameter("a");
                    String authority = appLinkData.getQueryParameter("Authority");

                    handlerZarinPalCallBack(amount, authority);
                }

            } else if (appLinkData.getPath().startsWith("/shop")) {

                addFrg(ShopMainFragment.newInstance(), "ShopMainFragment");

            } else if (appLinkData.getPath().startsWith("/")) {

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
                break;

            case 1:
                manageStack();
                addFrg(CongressMainFrg.newInstance(), "CongressMainFrg");
                break;

            case 2:
                manageStack();
                addFrg(ForumMainFrg.newInstance(), "ForumMainFrg");
                break;

            case 3:
                manageStack();

                if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {
                    // accountInfo.getExistingAccountAuthToken(ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS);
                    addFrg(DashboardMainFrg.newInstance(), "DashboardMainFrg");
                }
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

            if (fragments.lastElement() instanceof AllaMainFrg) {
                bottomNavigation.setCurrentItem(0, false);
            } else if (fragments.lastElement() instanceof CongressMainFrg) {
                bottomNavigation.setCurrentItem(1, false);
            } else if (fragments.lastElement() instanceof ForumMainFrg) {
                bottomNavigation.setCurrentItem(2, false);
            } else if (fragments.lastElement() instanceof DashboardMainFrg) {
                bottomNavigation.setCurrentItem(3, false);
            } else if (fragments.lastElement() instanceof ShopMainFragment) {
                bottomNavigation.setCurrentItem(4, false);
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

    @Subscribe
    public void getMessage(Events.CloseFragment closeFragment) {

        close();
    }


    @Override
    public void onCheckNetwork(boolean flag) {

    }

    private void handlerZarinPalCallBack(String amount, String authority) {

        PaymentVerificationRequest body = new PaymentVerificationRequest("55eb1362-08d4-42ee-8c74-4c5f5bef37d4",
                Integer.parseInt(amount),
                authority);

        repository.paymentVerification(body, data -> {

            if (data instanceof Result.Success) {

                PaymentVerificationResponse payment = (PaymentVerificationResponse) ((Result.Success) data).value;

                if (payment.getStatus() == 100) {

                    Toast.makeText(this, "پرداخت با موفقیت انجام شد. کد پیگیری شما: " + String.valueOf(payment.getRefID()), Toast.LENGTH_LONG).show();
                    notifyTransaction(amount, authority, String.valueOf(payment.getRefID()));
                } else {

                    Toast.makeText(this, "خطا : " + String.valueOf(payment.getStatus()), Toast.LENGTH_LONG).show();
                    notifyTransaction(amount, authority, String.valueOf(payment.getRefID()));
                }

            } else {
                Log.d(TAG, (String) ((Result.Error) data).value);
                Toast.makeText(this, "تایید پرداخت با مشکل مواجه شد.در صورتی که محصول خریداری شده به لیست شما اضافه نشده است با پشتیبانی تماس بگیرید.", Toast.LENGTH_LONG).show();
            }


        });


    }


    private void notifyTransaction(String cost, String authority, String refId){

        if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {

            accountInfo.getExistingAccountAuthToken(ACCOUNT_TYPE, AUTHTOKEN_TYPE_FULL_ACCESS, token ->
                    this.runOnUiThread(() -> {

                        repository.notifyTransaction(token, cost, authority, refId, data -> {

                            if (data instanceof Result.Success) {

                               ErrorBase temp = (ErrorBase) ((Result.Success) data).value;

                            } else {

                                Log.d("Test", (String) ((Result.Error) data).value);
                            }


                        });

                    }));
        }

    }


}

