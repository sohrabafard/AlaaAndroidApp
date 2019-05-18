package ir.sanatisharif.android.konkur96.fragment;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.LinearLayoutManager;
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

import com.google.android.gms.common.wrappers.InstantApps;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.account.AccountInfo;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.adapter.MainItemAdapter;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.dialog.MyAlertDialogFrg;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.MainItem;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.model.user.User;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;

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
                                Events.CloseFragment closeFragment = new Events.CloseFragment();
                                closeFragment.setTagFragments("");
                                EventBus.getDefault().post(closeFragment);
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
            alert.show(getFragmentManager(), "alert");

        }


        return super.onOptionsItemSelected(item);
    }

    private void initUi(View view) {

        setToolbar(mToolbar, "داشبورد");

        getFragmentManager().beginTransaction()
                .add(R.id.fl_container_profile, MyProduct.newInstance(), "your fragment here")
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

        ((TextView) itemBasket.findViewById(R.id.txtTitle)).setText("سبدخرید");
        ((TextView) itemAboutMe.findViewById(R.id.txtTitle)).setText("درباره ما");
        ((TextView) itemVideo.findViewById(R.id.txtTitle)).setText("ویدیو");
        ((TextView) itemNewOrder.findViewById(R.id.txtTitle)).setText("لیست سفارش ها");

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






