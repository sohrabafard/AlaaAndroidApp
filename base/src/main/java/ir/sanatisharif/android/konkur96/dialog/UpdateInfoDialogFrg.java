package ir.sanatisharif.android.konkur96.dialog;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.PreferenceManager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;

import ir.sanatisharif.android.konkur96.model.DownloadUrl;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;

/**
 * Created by Mohamad on 7/7/2017.
 */

public class UpdateInfoDialogFrg extends BaseDialogFragment<UpdateInfoDialogFrg> {

    //------init UI
    private static final String TAG = "LOG";
    private View dialog;
    private TextView txtAccept;
    private TextView txtContent;

    public static UpdateInfoDialogFrg newInstance(ArrayList<DownloadUrl> Urls) {
        UpdateInfoDialogFrg frag = new UpdateInfoDialogFrg();
        return frag;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        dialog = inflater.inflate(R.layout.dialog_update_info, container, false);
        setCancelable(false);
        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtAccept = dialog.findViewById(R.id.txtAccept);
        txtContent = dialog.findViewById(R.id.txtContent);

        txtContent.setText(Html.fromHtml(getString(R.string.update_info)));

        txtAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MyPreferenceManager.getInatanse().setLastVersionCode(32);
                dismiss();
            }
        });


    }

}
