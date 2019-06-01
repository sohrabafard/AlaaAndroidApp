package ir.sanatisharif.android.konkur96.dialog;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.model.DownloadUrl;
import ir.sanatisharif.android.konkur96.model.main_page.lastVersion.LastVersion;

/**
 * Created by Mohamad on 7/7/2017.
 */

public class ForceUpdateDialogFrg extends BaseDialogFragment<ForceUpdateDialogFrg> {
    
    //------init UI
    private static final String TAG = "LOG";
    private              View   dialog;
    private              Button txtDirectUrl, txtPlayStoreUrl, txtBazaarUrl;
    private LastVersion lastVersion;
    private String      url;
    
    public static ForceUpdateDialogFrg newInstance(ArrayList<DownloadUrl> Urls) {
        ForceUpdateDialogFrg frag = new ForceUpdateDialogFrg();
        return frag;
    }
    
    public void setLastVersion(LastVersion lastVersion) {
        this.lastVersion = lastVersion;
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
        
        dialog = inflater.inflate(R.layout.dialog_force_update, container, false);
        setCancelable(false);
        return dialog;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        Intent intent = new Intent(Intent.ACTION_VIEW);
        initUI();
        
        //check and force dialog
        if (lastVersion.getAndroid().getType() != null) {
            if (lastVersion.getAndroid().getType().getCode() == 1) {
                setCancelable(true);
            } else {
                setCancelable(true);
            }
        }
        //check and invisible
        if (lastVersion.getAndroid().getUrl().getPlayStore().trim().length() == 0) {
            txtPlayStoreUrl.setVisibility(View.GONE);
            
        }
        if (lastVersion.getAndroid().getUrl().getBazaar().trim().length() == 0) {
            txtBazaarUrl.setVisibility(View.GONE);
        }
        if (lastVersion.getAndroid().getUrl().getDirect().trim().length() == 0) {
            txtDirectUrl.setVisibility(View.GONE);
        }
        
        txtPlayStoreUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent
                        intent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse(lastVersion.getAndroid().getUrl().getPlayStore().trim()));
                startActivity(intent);
            }
        });
        txtBazaarUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent
                        intent =
                        new Intent(Intent.ACTION_VIEW, Uri.parse(lastVersion.getAndroid().getUrl().getBazaar().trim()));
                startActivity(intent);
            }
        });
        txtDirectUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(lastVersion.getAndroid().getUrl().getDirect().trim()));
                startActivity(intent);
            }
        });
    }
    
    private void initUI() {
        txtDirectUrl = dialog.findViewById(R.id.txtDirectUrl);
        txtPlayStoreUrl = dialog.findViewById(R.id.txtPlayStoreUrl);
        txtBazaarUrl = dialog.findViewById(R.id.txtBazaarUrl);
        
        ripple(txtDirectUrl, 2);
        ripple(txtPlayStoreUrl, 2);
        ripple(txtBazaarUrl, 2);
    }
}
