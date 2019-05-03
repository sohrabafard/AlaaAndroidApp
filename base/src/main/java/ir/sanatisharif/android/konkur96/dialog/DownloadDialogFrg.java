package ir.sanatisharif.android.konkur96.dialog;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.listener.DownloadComplete;
import ir.sanatisharif.android.konkur96.model.DownloadUrl;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.utils.DownloadFile;
import ir.sanatisharif.android.konkur96.utils.Utils;

/**
 * Created by Mohamad on 7/7/2017.
 */

public class DownloadDialogFrg extends BaseDialogFragment<DownloadDialogFrg> {

    //------init UI
    private static final String TAG = "LOG";
    private static ArrayList<Video> videos = new ArrayList<>();
    private static String title;

    private TextView txtDownload;
    private TextView txtCancel;
    private RadioGroup radioGroup;
    private RadioButton radioExcellentQuality;
    private RadioButton radioHighQuality;
    private RadioButton radioMediumQuality;

    //------

    private static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,};
    private static final int PERMISSION_ALL = 1;
    private View dialog;
    private SharedPreferences sharedPreferences;
    private DownloadComplete downloadComplete;

    public static DownloadDialogFrg newInstance(List<Video> v, String t) {
        DownloadDialogFrg frag = new DownloadDialogFrg();
        videos.addAll(v);
        title = t;
        return frag;
    }

    public void setData(List<Video> v, String t) {
        videos.addAll(v);
        title = t;
    }

    public void setComplete(DownloadComplete downloadComplete) {
        this.downloadComplete = downloadComplete;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog_Alert);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videos.clear();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialog = inflater.inflate(R.layout.download_bottom_sheet, container, false);

        return dialog;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtDownload = dialog.findViewById(R.id.txtDownload);
        txtCancel = dialog.findViewById(R.id.txtCancel);

        radioGroup = dialog.findViewById(R.id.radioGroup);
        radioExcellentQuality = dialog.findViewById(R.id.radioExcellentQuality);
        radioHighQuality = dialog.findViewById(R.id.radioHighQuality);
        radioMediumQuality = dialog.findViewById(R.id.radioMediumQuality);

        ripple(txtDownload, 4);
        ripple(txtCancel, 4);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        radioExcellentQuality.setText(toString(videos.get(0).getCaption(), videos.get(0).getRes()));
        radioHighQuality.setText(toString(videos.get(1).getCaption(), videos.get(1).getRes()));
        radioMediumQuality.setText(toString(videos.get(2).getCaption(), videos.get(2).getRes()));

        String pref = sharedPreferences.getString(getString(R.string.player_quality), "240");

        if (pref.equals("HD_720")) {
            radioExcellentQuality.setChecked(true);
        } else if (pref.equals("hq")) {
            radioHighQuality.setChecked(true);
        } else if (pref.equals("240p")) {
            radioMediumQuality.setChecked(true);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                txtDownload.setEnabled(true);
            }
        });

        txtDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkLocationPermission()) {
                    if (radioGroup.getCheckedRadioButtonId() == R.id.radioExcellentQuality) {

                        createDir(videos.get(0).getLink(), title);

                    } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioHighQuality) {

                        createDir(videos.get(1).getLink(), title);

                    } else if (radioGroup.getCheckedRadioButtonId() == R.id.radioMediumQuality) {

                        createDir(videos.get(2).getLink(), title);
                    }

                    dismiss();
                }
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dismiss();
            }
        });
    }
    // ----- get Permission

    private boolean checkLocationPermission() {

        boolean has = hasPermissions(getContext(), PERMISSIONS);

        if (!has) {
            ActivityCompat.requestPermissions(AppConfig.currentActivity, PERMISSIONS, PERMISSION_ALL);
        } else {
            return true;
        }
        return false;
    }

    //---------------------------------------------------------------------------------------
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    private void createDir(String url, String title) {

        String mediaPath = FileManager.getPathFromAllaUrl(url);
        File file = new File(FileManager.getRootPath() + mediaPath);
        String fileName = FileManager.getFileNameFromUrl(url);
        if (!file.exists()) {
            if (file.mkdirs())
                startDL(url, title, mediaPath, fileName, file);
        } else
            startDL(url, title, mediaPath, fileName, file);
    }

    private void startDL(String url, String title, String mediaPath, String fileName, final File f) {

        if (sharedPreferences.getBoolean(getString(R.string.setting_external_download), true)) {
            DownloadFile.getInstance().init(getContext(), new DownloadComplete() {
                @Override
                public void complete() {

                    if (downloadComplete != null) {
                        downloadComplete.complete();
                    }
                    Utils.addVideoToGallery(f, AppConfig.currentActivity);
                }
            });

            DownloadFile.getInstance().start(url, AppConstants.ROOT + "/" + mediaPath, fileName, title, "");
        } else {
            Utils.loadUrl(url, getContext());
        }
    }

    public String toString(String caption, String title) {

        return String.format("%s - %s", caption, title);
    }
}
