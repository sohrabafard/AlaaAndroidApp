package ir.sanatisharif.android.konkur96.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import ir.sanatisharif.android.konkur96.R;

/**
 * Created by Mohamad on 7/7/2017.
 */

public class MyAlertDialogFrg extends BaseDialogFragment<MyAlertDialogFrg> {

    //------init UI
    private static final String TAG = "LOG";
    private View dialog;
    private TextView txtDownload, txtCancel, txtTitle, txtMessage;
    private MyAlertDialogListener listener;

    private String title;
    private String message;
    private boolean html = false;

    public static MyAlertDialogFrg newInstance() {
        MyAlertDialogFrg frag = new MyAlertDialogFrg();
        return frag;
    }

    //<editor-fold desc="setter">

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public void setListener(MyAlertDialogListener listener) {
        this.listener = listener;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    //</editor-fold>

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_Alert);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog = inflater.inflate(R.layout.dialog_alert, container, false);

        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtDownload = dialog.findViewById(R.id.txtDownload);
        txtCancel = dialog.findViewById(R.id.txtCancel);
        txtTitle = dialog.findViewById(R.id.txtTitle);

        txtMessage = dialog.findViewById(R.id.txtMessage);

        ripple(txtDownload, 4);
        ripple(txtCancel, 4);

        if (message != null) {
            if (html) {
                txtMessage.setText(Html.fromHtml(message.trim()));
                txtMessage.setMovementMethod (LinkMovementMethod.getInstance());
            } else
                txtMessage.setText(message.trim());
        }
        if (title != null) {
            txtTitle.setText(title.trim());
        }

        txtDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener != null)
                    listener.setOnPositive();
                dismiss();

            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null)
                    listener.setOnNegative();
                dismiss();
            }
        });
    }

    public interface MyAlertDialogListener {
        void setOnPositive();

        void setOnNegative();
    }

}
