package ir.sanatisharif.android.konkur96.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import org.greenrobot.eventbus.EventBus;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.model.Events;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class AbouteMeFrg extends BaseFragment {

    private Toolbar mToolbar;
    private ImageView imgTelegram, imgInstagram;

    public static AbouteMeFrg newInstance() {

        Bundle args = new Bundle();

        AbouteMeFrg fragment = new AbouteMeFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_me, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:

                Events.CloseFragment closeFragment = new Events.CloseFragment();
                closeFragment.setTagFragments("");
                EventBus.getDefault().post(closeFragment);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initView(View view) {

        setToolbar(mToolbar, getString(R.string.txt_aboute_alla));

        imgInstagram = view.findViewById(R.id.imgInstagram);
        imgTelegram = view.findViewById(R.id.imgTelegram);

        imgTelegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String appName = "org.telegram.messenger";
                final boolean isAppInstalled = isAppAvailable(getContext(), appName);

                if (isAppInstalled) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://telegram.me/alaa_sanatisharif"));
                    intent.setPackage("org.telegram.messenger");
                    startActivity(intent);
                }
            }
        });

        imgInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String appName = "com.instagram.android";
                final boolean isAppInstalled = isAppAvailable(getContext(), appName);

                if (isAppInstalled) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/alaa_sanatisharif"));
                    intent.setPackage("com.instagram.android");
                    startActivity(intent);
                }
            }
        });

    }

    public boolean isAppAvailable(Context context, String appName) {
        PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}


