package ir.sanatisharif.android.konkur96.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Objects;

import ir.sanatisharif.android.konkur96.R;

/**
 * Created by Mohamad on 7/7/2017.
 */

public class MyAlertDialogFrg extends BaseDialogFragment<MyAlertDialogFrg> {
    
    //------init UI
    private static final String   TAG = "LOG";
    private              View     dialog;
    private              TextView txtDownload, txtCancel, txtTitle, txtMessage;
    private MyAlertDialogListener listener;
    
    private String  title;
    private String  message;
    private boolean html = false;
    
    public static MyAlertDialogFrg newInstance() {
        MyAlertDialogFrg frag = new MyAlertDialogFrg();
        return frag;
    }
    
    public TextView getTxtCancel() {
        return txtCancel;
    }
    
    public TextView getTxtDownload() {
        return txtDownload;
    }
    
    //<editor-fold desc="setter">
    
    public boolean isHtml() {
        return html;
    }
    
    public void setHtml(boolean html) {
        this.html = html;
    }
    
    public MyAlertDialogFrg setListener(MyAlertDialogListener listener) {
        this.listener = listener;
        return this;
    }
    
    public String getTitle() {
        return title;
    }
    
    public MyAlertDialogFrg setTitle(String title) {
        this.title = title;
        return this;
    }
    
    public String getMessage() {
        return message;
    }
    
    public MyAlertDialogFrg setMessage(String message) {
        this.message = message;
        return this;
    }
    //</editor-fold>
    
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_Alert);
        } else {
            setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_DeviceDefault);
        }
    }
    
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        Objects.requireNonNull(getDialog().getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        
        dialog = inflater.inflate(R.layout.dialog_alert, container, false);
        
        return dialog;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        txtDownload = dialog.findViewById(R.id.txtDownload);
        txtCancel = dialog.findViewById(R.id.txtCancel);
        txtTitle = dialog.findViewById(R.id.txt_title);
        
        txtMessage = dialog.findViewById(R.id.txtMessage);
        
        ripple(txtDownload, 4);
        ripple(txtCancel, 4);
        
        if (message != null) {
            if (html) {
                txtMessage.setText(Html.fromHtml(message.trim()));
                txtMessage.setMovementMethod(LinkMovementMethod.getInstance());
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
