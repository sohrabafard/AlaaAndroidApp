package ir.sanatisharif.android.konkur96.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.model.DownloadUrl;
import ir.sanatisharif.android.konkur96.utils.MyPreferenceManager;

/**
 * Created by Mohamad on 7/7/2017.
 */

public class UpdateInfoDialogFrg extends BaseDialogFragment<UpdateInfoDialogFrg> {

    //------init UI
    private static final String TAG = "UpdateInfoDialogFrg";
    private View dialog;

    public static UpdateInfoDialogFrg newInstance(ArrayList<DownloadUrl> Urls) {
        return new UpdateInfoDialogFrg();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_Alert);
        }
        else {
            setStyle(DialogFragment.STYLE_NORMAL, 0);
        }
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

        TextView txtAccept = dialog.findViewById(R.id.txtAccept);
        TextView txtContent = dialog.findViewById(R.id.txtContent);

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
