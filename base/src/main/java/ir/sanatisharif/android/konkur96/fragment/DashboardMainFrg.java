package ir.sanatisharif.android.konkur96.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.account.AuthenticatorActivity;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.activity.SettingActivity;
import ir.sanatisharif.android.konkur96.adapter.MainItemAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.dialog.MyAlertDialogFrg;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.Utils;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;
import static ir.sanatisharif.android.konkur96.app.AppConstants.ACCOUNT_TYPE;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class DashboardMainFrg extends BaseFragment {

    private User user;
    private AccountInfo accountInfo;
    private ImageView imgUser;
    private Toolbar mToolbar;
    private RecyclerView myRecyclerView;
    private TextView txtNationalCode, txtMobile, txtFullName, txtField;
    private LinearLayout itemVideo, itemAboutMe, itemNewOrder, itemBasket;
    private FloatingActionButton fabItemVideo, fabItemAboutMe, fabItemNewOrder, fabItemBasket;

    private MainItemAdapter adapter;
    private List<MainItem> items = new ArrayList<>();

    public static DashboardMainFrg newInstance() {

        Bundle args = new Bundle();

        DashboardMainFrg fragment = new DashboardMainFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_profile_fab_menu, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        accountInfo = new AccountInfo(getContext(), getActivity());
        user = accountInfo.getInfo(ACCOUNT_TYPE);
        initUi(view);
        setData();
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_dashbard, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {
            Events.CloseFragment closeFragment = new Events.CloseFragment();
            closeFragment.setTagFragments("");
            EventBus.getDefault().post(closeFragment);

        } else if (id == R.id.actionSetting) {
            startActivity(new Intent(AppConfig.currentActivity, SettingActivity.class));
        } else if (id == R.id.actionSettingSupportBuy) {

            MyAlertDialogFrg alert = new MyAlertDialogFrg();
            alert.setTitle(getString(R.string.settingsSupportBuy));
            alert.setMessage(getString(R.string.supportBuy));
            alert.setHtml(true);
            alert.show(getHostFragmentManager(), "alert");
        } else if (id == R.id.actionSettingTelegram) {
            String alaaTelegramUrl = "https://telegram.me/joinchat/AAAAADwv5Wn78qn7-PT8fQ";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(alaaTelegramUrl));
            intent.setPackage("org.telegram.messenger");

            try {
                if (intent.resolveActivity(AppConfig.context.getPackageManager()) != null) {
                    startActivity(intent);
                }else {
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse(alaaTelegramUrl));
                }
                startActivity(intent);
            }catch (Exception ex){
                Toast.makeText(AppConfig.context,"@alaa_sanatisharif",Toast.LENGTH_LONG).show();
            }
        } else if (id == R.id.actionSettingLogout) {

            MyAlertDialogFrg alert = new MyAlertDialogFrg();
            alert.setTitle("خروج از حساب کاربری");
            alert.setMessage("آیا مایلید از حساب کاربری آلاء خارج شوید؟");
            alert.setListener(new MyAlertDialogFrg.MyAlertDialogListener() {
                @Override
                public void setOnPositive() {
                    accountInfo.removeAccount(ACCOUNT_TYPE, new AccountInfo.RemoveAccount() {
                        @Override
                        public void onRemove(boolean done) {
                            if (done) {
                                ActivityBase.toastShow("با موفقیت خارج شدید", MDToast.TYPE_SUCCESS);
                                // Events.CloseFragment closeFragment = new Events.CloseFragment();
                                // closeFragment.setTagFragments("");
                                // EventBus.getDefault().post(closeFragment);

                                startActivity(new Intent(getActivity(), AuthenticatorActivity.class));
                                AppConfig.HANDLER.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {

                                    }
                                }, 100);
                                AppConfig.currentActivity.finish();

                            } else {
                                ActivityBase.toastShow("خطا در حذف اکانت", MDToast.TYPE_ERROR);
                            }
                        }
                    });

                    // Log.i("LOG", "setOnPositive: accountInfo");
                }

                @Override
                public void setOnNegative() {

                }
            });
            alert.show(getHostFragmentManager(), "alert");

        }


        return super.onOptionsItemSelected(item);
    }

    public FragmentManager getHostFragmentManager() {
        FragmentManager fm = getFragmentManager();
        if (fm == null && isAdded()) {
            fm = ((AppCompatActivity)getActivity()).getSupportFragmentManager();
        }
        return fm;
    }

    private void initUi(View view) {

        setToolbar(mToolbar, "پروفایل من");

        getFragmentManager().beginTransaction()
                .add(R.id.fl_container_profile, MyProduct.newInstance(), "MyProduct")
                .commit();

        //init

        final CollapsingToolbarLayout collapsing_toolbar = view.findViewById(R.id.collapsing_toolbar);
        ((AppBarLayout) view.findViewById(R.id.app_bar_layout)).
                addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                    @Override
                    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                        int min_height = ViewCompat.getMinimumHeight(collapsing_toolbar) * 2;
                        float scale = (float) (min_height + verticalOffset) / min_height;
//                        image.setScaleX(scale >= 0 ? scale : 0);
//                        image.setScaleY(scale >= 0 ? scale : 0);
                        Log.i("LOG", "onOffsetChanged: " + scale + " " + verticalOffset);
                    }
                });

        imgUser = view.findViewById(R.id.imgUser);
        txtFullName = view.findViewById(R.id.txtFullName);
        txtField = view.findViewById(R.id.txtField);
        txtMobile = view.findViewById(R.id.txtMobile);
        txtNationalCode = view.findViewById(R.id.txtNationalCode);

        itemBasket = view.findViewById(R.id.itemBasket);
        itemAboutMe = view.findViewById(R.id.itemAboutMe);
        itemVideo = view.findViewById(R.id.itemVideo);
        itemNewOrder = view.findViewById(R.id.itemNewOrder);

        fabItemBasket = itemBasket.findViewById(R.id.fabTitle);
        fabItemAboutMe = itemAboutMe.findViewById(R.id.fabTitle);
        fabItemVideo = itemVideo.findViewById(R.id.fabTitle);
        fabItemNewOrder = itemNewOrder.findViewById(R.id.fabTitle);

        ((TextView) itemBasket.findViewById(R.id.txt_title)).setText("سبدخرید");
        ((TextView) itemAboutMe.findViewById(R.id.txt_title)).setText("درباره آلاء");
        ((TextView) itemVideo.findViewById(R.id.txt_title)).setText("فیلم\u200Cهای دانلود شده");
        ((TextView) itemNewOrder.findViewById(R.id.txt_title)).setText("سفارش\u200Cهای من");

        fabItemBasket.setImageResource(R.drawable.ic_buy);//
        fabItemAboutMe.setImageResource(R.drawable.ic_call);
        fabItemVideo.setImageResource(R.drawable.ic_video_24dp);//
        fabItemNewOrder.setImageResource(R.drawable.ic_gift);

        fabItemBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFrg(CardFragment.newInstance(), "CardFragment");
            }
        });
        fabItemAboutMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFrg(AbouteMeFrg.newInstance(), "AbouteMeFrg");
            }
        });

        fabItemVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFrg(VideoDownloadedFrg.newInstance(), "VideoDownloadedFrg");
            }
        });
        fabItemNewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityBase.toastShow(getString(R.string.newOrder), MDToast.TYPE_INFO);
            }
        });
    }


    //<editor-fold desc="set data">
    private void setData() {
        if (user != null) {

            if (user.getLastName() != null && user.getFirstName() != null)
                txtFullName.setText(user.getFirstName() + " " + user.getLastName());
            if (user.getInfo() != null && user.getInfo().getMajor() != null)
                if (user.getInfo().getMajor().getName() != null)
                    txtField.setText(user.getInfo().getMajor().getName());
            if (user.getMobile() != null)
                txtMobile.setText(user.getMobile());
            if (user.getNationalCode() != null)
                txtNationalCode.setText(user.getNationalCode());
        }
    }
    //</editor-fold>
}






