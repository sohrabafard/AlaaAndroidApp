package ir.sanatisharif.android.konkur96.dialog;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.DownloadUrl;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;

/**
 * Created by Mohamad on 7/7/2017.
 */

public class NotInternetDialogFrg extends BaseDialogFragment<NotInternetDialogFrg> {

    //------init UI
    private static final String TAG = "LOG";
    private View dialog;
    private Button btnOk;
    private ImageView imgCLose;
    private NoInternetCallback callback;

    public static NotInternetDialogFrg newInstance(ArrayList<DownloadUrl> Urls) {
        NotInternetDialogFrg frag = new NotInternetDialogFrg();
        return frag;
    }

    public NotInternetDialogFrg setNoInternetCallback(NoInternetCallback callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppConfig.showNoInternetDialog = true;
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_Alert);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog = inflater.inflate(R.layout.dialog_no_internet, container, false);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnOk = dialog.findViewById(R.id.btnOK);
        imgCLose = dialog.findViewById(R.id.imgCLose);

        ripple(btnOk, 8);
        ripple(imgCLose, 8);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (callback != null) {
                    callback.onClickOk();
                    dismiss();
                }
            }
        });
        imgCLose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AppConfig.showNoInternetDialog = false;
                dismiss();
            }
        });

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        AppConfig.showNoInternetDialog = false;
    }

    public interface NoInternetCallback {
        void onClickOk();
    }

}
