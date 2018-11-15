package ir.sanatisharif.android.konkur96.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.ui.view.CircleTransform;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

/**
 * Created by Mohamad on 10/13/2018.
 */

public class DashboardMainFrg1 extends Fragment {

    @BindView(R.id.txtRegister)
    TextView txtRegister;
    @BindView(R.id.imgUser)
    ImageView imgUser;

    public static DashboardMainFrg1 newInstance() {

        Bundle args = new Bundle();

        DashboardMainFrg1 fragment = new DashboardMainFrg1();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this,view);

        GlideApp.with(AppConfig.context)
                .load("http://yakhmakgroup.ir/jokLike/v1/images/imageLogo/1765554797035373646.jpg")
                .transform(new CircleTransform(AppConfig.context))
                .into(imgUser);


        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addFrg(RegisterFrg.newInstance(), "RegisterFrg");
            }
        });
    }
}


