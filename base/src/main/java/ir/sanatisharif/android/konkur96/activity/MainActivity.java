package ir.sanatisharif.android.konkur96.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.widget.Toast;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.google.android.gms.common.wrappers.InstantApps;
import com.google.firebase.iid.FirebaseInstanceId;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Stack;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.account.AuthenticatorActivity;
import ir.sanatisharif.android.konkur96.api.Models.ErrorBase;
import ir.sanatisharif.android.konkur96.api.Models.PaymentVerificationRequest;
import ir.sanatisharif.android.konkur96.api.Models.PaymentVerificationResponse;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.ForceUpdateDialogFrg;
import ir.sanatisharif.android.konkur96.dialog.UpdateInfoDialogFrg;
import ir.sanatisharif.android.konkur96.fragment.AbouteMeFrg;
import ir.sanatisharif.android.konkur96.fragment.AllaMainFrg;
import ir.sanatisharif.android.konkur96.fragment.DashboardMainFrg;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.fragment.FilterTagsFrg;
import ir.sanatisharif.android.konkur96.fragment.ForumMainFrg;
import ir.sanatisharif.android.konkur96.fragment.ProductDetailFragment;
import ir.sanatisharif.android.konkur96.fragment.ShopMainFragment;
import ir.sanatisharif.android.konkur96.fragment.VideoDownloadedFrg;
import ir.sanatisharif.android.konkur96.handler.MainRepository;
import ir.sanatisharif.android.konkur96.handler.Repository;
import ir.sanatisharif.android.konkur96.handler.RepositoryImpl;
import ir.sanatisharif.android.konkur96.handler.Result;
import ir.sanatisharif.android.konkur96.interfaces.LogUserActionsOnPublicContentInterface;
import ir.sanatisharif.android.konkur96.listener.ICheckNetwork;
import ir.sanatisharif.android.konkur96.listener.api.IServerCallbackObject;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.main_page.lastVersion.LastVersion;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.AuthToken;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;
import ir.sanatisharif.android.konkur96.utils.Utils;

import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;

//https://blog.iamsuleiman.com/bottom-navigation-bar-android-tutorial/
public class MainActivity extends ActivityBase implements AHBottomNavigation.OnTabSelectedListener, ICheckNetwork, LogUserActionsOnPublicContentInterface {

    private static final String TAG = "MainActivity";
    private static Stack<Fragment> fragments;
    private static FragmentManager fm;
    private AccountInfo accountInfo;
    private AHBottomNavigation bottomNavigation;
    private Repository repository;
    private MainRepository mainRepository;

    //--- primitive define type-----
    private long back_pressed;

    public static void addFrg(Fragment frg, String tag) {

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(R.id.fl_container, frg, tag);

        if (fragments.size() == 0) {
            fragments.push(frg);

        } else {
            fragments.lastElement().onPause();
            transaction.hide(fragments.lastElement());
            fragments.push(frg);
        }
        transaction.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        repository = new RepositoryImpl(this);
        mainRepository = new MainRepository(this);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        }

        //   getLastVersion();
        accountInfo = new AccountInfo(getApplicationContext(), this);
        containerHeight(this);
        fragments = new Stack<>();
        fm = getSupportFragmentManager();

        //---------initialize UI--------
        initUI();

        getLastVersion();
        //-----------add FirstFragment

        addFrg(AllaMainFrg.newInstance(), "MainFrg");
        handleIntent(getIntent());

        if (MyPreferenceManager.getInatanse().getLastVersionCode() < Utils.getVersionCode()) {

            UpdateInfoDialogFrg updateInfoDialogFrg = new UpdateInfoDialogFrg();
            updateInfoDialogFrg.show(getSupportFragmentManager(), "");
        }

        if (MyPreferenceManager.getInatanse().getFirebaseToken().length() == 0) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    retrieveToken();
                }
            }).start();
        }

        //retrieveToken();
        if (!InstantApps.isInstantApp(getApplicationContext()))
            if (!MyPreferenceManager.getInatanse().isSendTokenToServer())
                sendRegistrationToServer();
    }
    private void retrieveToken() {
        Log.i(TAG, "onCreate: 2 ");
        // FirebaseApp.initializeApp(this);
        FirebaseInstanceId.getInstance().getInstanceId().
                addOnSuccessListener(MainActivity.this, instanceIdResult -> {
                    Log.i(TAG, "onCreate: " + instanceIdResult.getToken());
                    MyPreferenceManager.getInatanse().setFirebaseToken(instanceIdResult.getToken());
                });
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

        // forceCrash(bottomNavigation);
        ArrayList<AHBottomNavigationItem> bottomNavigationItems = new ArrayList<>();
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.home), R.drawable.ic_home);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.forum), R.drawable.ic_message);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.product), R.drawable.ic_shopping_cart);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.myProfile), R.drawable.ic_user);

        bottomNavigationItems.add(item1);
        bottomNavigationItems.add(item2);
        bottomNavigationItems.add(item3);
        bottomNavigationItems.add(item4);

        bottomNavigation.setAccentColor(getResources().getColor(R.color.colorPrimary));
        bottomNavigation.addItems(bottomNavigationItems);
        bottomNavigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);

        bottomNavigation.setOnTabSelectedListener(this);
    }

    private boolean handleIntent(Intent intent) {

        if(intent == null ){
            return false;
        }
        String action = intent.getAction();  // android.intent.action.VIEW
        String data = intent.getDataString();// https://sanatisharif.ir/c/8087

        if (action == null)
            return false;

        if (action.equals("ir.sanatisharif.android.SETTING")) {
            startActivity(new Intent(AppConfig.currentActivity, SettingActivity.class));
            return true;
        }
        if (!Intent.ACTION_VIEW.equals(action) || data == null) {
            return false;
        }
        Uri appLinkData = intent.getData();
        if(appLinkData == null){
            return false;
        }
        String path = appLinkData.getPath();

        if (path.startsWith("/c/") && path.length() > 3) {
            addFrg(DetailsVideoFrg.newInstance(data), "DetailsVideoFrg");
            return true;
        }
        if (path.equals("/c") || (path.startsWith("/c") && data.contains("tags"))) {
            addFrg(FilterTagsFrg.newInstance(data, null), "FilterTagsFrg");
            return true;
        }
        if (path.startsWith("/product")) {
            if (path.length() <= 9) {
                addFrg(ShopMainFragment.newInstance(), "ShopMainFragment");
                return true;
            }

            final Activity activity = this;
            lunchProductFragmentByUrl(path, activity);
            return true;
        }
        if (path.startsWith("/login") && accountInfo.ExistAccount(ACCOUNT_TYPE)) {
            addFrg(DashboardMainFrg.newInstance(), "DashboardMainFrg");
            return true;
        }
        if (path.startsWith("/zarinpal") && data.contains("Status")) {
            String mStatus = appLinkData.getQueryParameter("Status");
            String amount = appLinkData.getQueryParameter("a");
            String authority = appLinkData.getQueryParameter("Authority");

            handlerZarinPalCallBack(amount, authority);
            return true;

        }
        if (path.startsWith("/shop")) {
            addFrg(ShopMainFragment.newInstance(), "ShopMainFragment");
            return true;
        }
        if (path.equals("/contactUs")) {
            addFrg(AbouteMeFrg.newInstance(), "AbouteMeFrg");
            return true;
        }
        if (path.equals("/asset")){
            addFrg(DashboardMainFrg.newInstance(),"DashboardMainFrg");
            return true;
        }
        if (path.equals("/")) {
            addFrg(AllaMainFrg.newInstance(), "MainFrg");
            return true;
        }
        return false;

    }

    private void lunchProductFragmentByUrl(@NonNull String path,@NonNull final Activity activity) {
        ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(this.getString(R.string.loading));
        mProgressDialog.show();

        AuthToken.getInstant().get(this, this, new AuthToken.Callback() {
            @Override
            public void run(@NonNull String token) {
                repository.getProductByUrl(path, token, data1 -> {
                    final Handler handler = new Handler();
                    handler.postDelayed(mProgressDialog::dismiss, 3000);
                    if (data1 instanceof Result.Success) {
                        try {
                            ProductModel productModel = ((ProductModel) ((Result.Success) data1).value);
                            activity.runOnUiThread(() -> addFrg(ProductDetailFragment.newInstance(productModel), "ProductDetailFragment"));
                        } catch (Exception ex) {
                            Log.e(TAG, "lunchProductFragmentByUrl: parse-intent-if:" + path + "\n\r" + ex.getMessage());
                        }

                    } else {
                        Log.e(TAG, "lunchProductFragmentByUrl: parse-intent-else:" + path + "\n\r" + ((Result.Error) data1).value);
                    }
                });
            }

            @Override
            public void nill() {
                startActivity(new Intent(activity, AuthenticatorActivity.class));
            }
        });
    }

    private void sendRegistrationToServer() {
        // TODO: Implement this method to send token to your app server.

        final String firebaseToken = MyPreferenceManager.getInatanse().getFirebaseToken();
        final User user = accountInfo.getInfo(ACCOUNT_TYPE);

        AuthToken.getInstant().get(this, this, new AuthToken.Callback() {
            @Override
            public void run(@NonNull String token) {
                mainRepository.sendRegistrationToServer(user.getId(), firebaseToken, token, new IServerCallbackObject() {
                    @Override
                    public void onSuccess(Object obj) {
                        MyPreferenceManager.getInatanse().setSendTokenToServer(true);
                    }

                    @Override
                    public void onFailure(String message) {
                        MyPreferenceManager.getInatanse().setSendTokenToServer(false);
                    }
                });
            }

            @Override
            public void nill() {

            }
        });
    }

    private void itemSelect(int tab_id) {

        switch (tab_id) {
            case 0:
                manageStack();
                addFrg(AllaMainFrg.newInstance(), "MainFrg");
                break;

            case 1:
                manageStack();
                addFrg(ForumMainFrg.newInstance(), "ForumMainFrg");
                break;

            case 2:
                manageStack();
                addFrg(ShopMainFragment.newInstance(), "ShopMainFragment");
                break;

            case 3:
                manageStack();
                if (accountInfo.ExistAccount(ACCOUNT_TYPE)) {
                    addFrg(DashboardMainFrg.newInstance(), "DashboardMainFrg");
                }
                break;
        }
    }

    public void close() {

        if (fragments.size() > 1) {

            FragmentTransaction transaction = fm.beginTransaction();
            // fragments.lastElement().onPause();
            transaction.remove(fragments.pop());
            // fragments.lastElement().onResume();
            transaction.show(fragments.lastElement());
            transaction.commit();

            if (fragments.lastElement() instanceof AllaMainFrg) {
                bottomNavigation.setCurrentItem(0, false);
            } else if (fragments.lastElement() instanceof ForumMainFrg) {
                bottomNavigation.setCurrentItem(1, false);
            } else if (fragments.lastElement() instanceof ShopMainFragment) {
                bottomNavigation.setCurrentItem(2, false);
            } else if (fragments.lastElement() instanceof DashboardMainFrg) {
                bottomNavigation.setCurrentItem(3, false);
            }

        } else {

            twiceClick();
        }
    }

    private void manageStack() {


        boolean showHomeFrg = true;

        for (int i = 1; i < fragments.size(); i++) {
            try {
                FragmentTransaction transaction = fm.beginTransaction();
                Fragment f = fragments.pop();
                transaction.remove(f).commit();
                showHomeFrg = false;
            } catch (Exception e) {
                Log.e(TAG, "manageStack: error");
                Log.e(TAG, e.getMessage());
            }

        }
        try {
            FragmentTransaction transaction = fm.beginTransaction();
            if (!showHomeFrg) {
                transaction.show(fragments.lastElement()).commit();
            }
        } catch (Exception e) {
            Log.e(TAG, "manageStack-show: error");
            Log.e(TAG, e.getMessage());
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

    private void getLastVersion() {
        mainRepository.getLastVersion(new IServerCallbackObject() {
            @Override
            public void onSuccess(Object obj) {
                if (obj != null) {
                    LastVersion lastVersion = (LastVersion) obj;
                    if (lastVersion.getAndroid().getLastVersion() > Utils.getVersionCode()) {
                        ForceUpdateDialogFrg forceUpdateDialogFrg = new ForceUpdateDialogFrg();
                        forceUpdateDialogFrg.setLastVersion(lastVersion);
                        forceUpdateDialogFrg.show(getSupportFragmentManager(), "forceUpdateDialogFrg");
                    }
                }
            }

            @Override
            public void onFailure(String message) {
                Log.i(TAG, "onSuccess: " + message);
            }
        });
    }

    private void handlerZarinPalCallBack(String amount, String authority) {

        PaymentVerificationRequest body = new PaymentVerificationRequest("55eb1362-08d4-42ee-8c74-4c5f5bef37d4",
                Integer.parseInt(amount),
                authority);

        repository.paymentVerification(body, data -> {

            if (data instanceof Result.Success) {

                PaymentVerificationResponse payment = (PaymentVerificationResponse) ((Result.Success) data).value;

                if (payment.getStatus() == 100) {

                    Toast.makeText(this, "پرداخت با موفقیت انجام شد. کد پیگیری شما: " + payment.getRefID(), Toast.LENGTH_LONG).show();
                    notifyTransaction(amount, authority, String.valueOf(payment.getRefID()));
                } else {

                    Toast.makeText(this, "خطا : " + payment.getStatus(), Toast.LENGTH_LONG).show();
                    notifyTransaction(amount, authority, String.valueOf(payment.getRefID()));
                }

            } else {
                Log.d(TAG, (String) ((Result.Error) data).value);
                Toast.makeText(this, "تایید پرداخت با مشکل مواجه شد.در صورتی که محصول خریداری شده به لیست شما اضافه نشده است با پشتیبانی تماس بگیرید.", Toast.LENGTH_LONG).show();
            }

        });
    }


    private void notifyTransaction(String cost, String authority, String refId) {

        final Activity activity = this;
        AuthToken.getInstant().get(this, this, new AuthToken.Callback() {
            @Override
            public void run(@NonNull String token) {
                repository.notifyTransaction(token, cost, authority, refId, data -> {
                    if (data instanceof Result.Success) {
                        ErrorBase temp = (ErrorBase) ((Result.Success) data).value;
                    } else {
                        Log.d("Test", (String) ((Result.Error) data).value);
                    }
                });
            }

            @Override
            public void nill() {
                startActivity(new Intent(activity, AuthenticatorActivity.class));
            }
        });
    }
}

