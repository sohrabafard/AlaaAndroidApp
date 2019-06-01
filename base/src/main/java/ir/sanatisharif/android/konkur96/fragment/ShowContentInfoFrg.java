package ir.sanatisharif.android.konkur96.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.Models.ContentModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.dialog.MyAlertDialogFrg;
import ir.sanatisharif.android.konkur96.handler.EncryptedDownloadInterface;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.DownloadFile;
import ir.sanatisharif.android.konkur96.utils.OpenFile;
import ir.sanatisharif.android.konkur96.utils.Utils;
import me.gujun.android.taggroup.TagGroup;

import static ir.sanatisharif.android.konkur96.activity.ActivityBase.toastShow;
import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

/**
 * Created by Mohamad on 11/14/2018.
 */

public class ShowContentInfoFrg extends BaseFragment implements
        View.OnClickListener, TagGroup.OnTagClickListener {
    
    private static final String[]
                                      PERMISSIONS    =
            {Manifest.permission.WRITE_EXTERNAL_STORAGE,};
    private static final int          PERMISSION_ALL = 1;
    private static       ContentModel course;
    FragmentManager fragmentManager;
    private TextView txtAuthor, txtTitle;
    //  private JustifiedTextView txtDesc;
    private WebView webView;
    private Button  btnDownload, btnOpenPDF;
    private Toolbar  toolbar;
    private TagGroup tagGroup;
    private String   TAG = "Alaa\\ShowContentInfoFrg";
    
    public static ShowContentInfoFrg newInstance(ContentModel c) {
        
        Bundle args = new Bundle();
        course = c;
        ShowContentInfoFrg fragment = new ShowContentInfoFrg();
        fragment.setArguments(args);
        return fragment;
    }
    
    //---------------------------------------------------------------------------------------
    public static boolean hasPermissions(Context context, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null &&
            permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) !=
                    PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        fragmentManager = getFragmentManager();
        initUI(view);
        setData();
        
    }
    
    private void setData() {
        
        if (course != null) {
            if (course.getName() != null)
                txtTitle.setText(course.getName());
            if (course.getAuthor().getFullName() != null)
                txtAuthor.setText(course.getAuthor().getFullName());
            
            //  Log.i(TAG, "setData: "+course.getDescription());
            //    tagGroup.setTags(course.getTags().getTags());
            
            new Thread(new Runnable() {
                @Override
                public void run() {
                    
                    AppConfig.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            if (course.getDescription() != null)
                                try {
                                    webView.loadData(course.getDescription(), "text/html", "UTF-8");
                                }
                                catch (Exception e) {
                                    toastShow("خطا در پردازش توضیحات", MDToast.TYPE_ERROR);
                                }
                            if (course.getTags() != null && course.getTags().getTags() != null)
                                tagGroup.setTags(course.getTags().getTags());
                        }
                    });
                }
            }).start();
            
            if (course.getFile().getPamphlet().get(0).getLink() != null) {
                String
                        fileName =
                        Utils.getFileNameFromUrl(course.getFile().getPamphlet().get(0).getLink());
                if (fileName != null) {
                    if (FileManager.checkFileExist(FileManager.getPDFPath() + fileName)) {
                        btnDownload.setVisibility(View.GONE);
                        btnOpenPDF.setVisibility(View.VISIBLE);
                    } else {
                        btnDownload.setVisibility(View.VISIBLE);
                        btnOpenPDF.setVisibility(View.GONE);
                    }
                }
            } else {
                ActivityBase.toastShow("لینک دانلود یافت مشد", MDToast.TYPE_ERROR);
            }
        }
    }
    
    private void initUI(View view) {
        
        setToolbar(toolbar, "نمایش محتوا");
        txtAuthor = view.findViewById(R.id.txtAuthor);
        txtTitle = view.findViewById(R.id.txt_title);
        webView = view.findViewById(R.id.webView);
        
        btnDownload = view.findViewById(R.id.btnDownload);
        btnOpenPDF = view.findViewById(R.id.btnOpenPDF);
        tagGroup = view.findViewById(R.id.tag_group);
        
        for (View v : tagGroup.getTouchables()) {
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(AppConfig.fontIRSensLight);
            }
        }
        
        btnDownload.setOnClickListener(this);
        btnOpenPDF.setOnClickListener(this);
        tagGroup.setOnTagClickListener(this);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        
        int id = item.getItemId();
        
        if (id == android.R.id.home) {
            Events.CloseFragment closeFragment = new Events.CloseFragment();
            closeFragment.setTagFragments("");
            EventBus.getDefault().post(closeFragment);
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onClick(View view) {
        
        if (view != null) {
            if (view.getId() == R.id.btnDownload) {
                MyAlertDialogFrg alert = new MyAlertDialogFrg();
                alert.setTitle("دانلود ")
                        .setMessage("آیا مایل به دانلود این فایل هستید؟")
                        .setListener(new MyAlertDialogFrg.MyAlertDialogListener() {
                            @Override
                            public void setOnPositive() {
                                
                                startFileDownload();
                                alert.dismiss();
                            }
                            
                            @Override
                            public void setOnNegative() {
                                alert.dismiss();
                            }
                        });
                
                if (fragmentManager != null) {
                    alert.show(fragmentManager, "alert");
                }
                
            } else if (view.getId() == R.id.btnOpenPDF) {
                String
                        fileName =
                        FileManager.getFileNameFromUrl(course.getFile().getPamphlet().get(0).getLink());
                if (fileName != null) {
                    Intent
                            pdfFileIntent =
                            OpenFile.getPdfFileIntent(getActivity(),
                                    FileManager.getPDFPath() + "/" + fileName);
                    Log.i(TAG, "." + pdfFileIntent.toString());
                    
                    
                    try {
                        startActivity(pdfFileIntent);
                    }
                    catch (Exception ex) {
                        Log.e(TAG, ex.getMessage());
                        ActivityBase.toastShow(getString(R.string.open_with_file_manager), MDToast.TYPE_ERROR);
                    }
                }
            }
        }
        
    }
    
    private void startFileDownload() {
        if (getActivity() != null && checkLocationPermission()) {
            if (course != null && course.getFile().getPamphlet().get(0).getLink() != null) {
                String url = course.getFile().getPamphlet().get(0).getLink();
                String
                        fileName =
                        Utils.getFileNameFromUrl(course.getFile().getPamphlet().get(0).getLink());
                String name = course.getName();
                downloadPreProcess(url, fileName, name);
            } else
                ActivityBase.toastShow("لینک دانلود معتبر نیست!", MDToast.TYPE_ERROR);
        }
    }
    
    private void downloadPreProcess(String url, String fileName, String name) {
        
        Log.i(TAG, "downloadPreProcess: " + url);
        
        
        if (url.contains("cdn.") || url.contains("paid.")) {
            startDownload(url, fileName, name);
        } else {
            Utils.followRedirectedLink(getContext(), getActivity(), url, new EncryptedDownloadInterface.Callback() {
                @Override
                public void fetch(String newUrl) {
                    Log.i(TAG, newUrl);
                    startDownload(newUrl, fileName, name);
                }
                
                @Override
                public void error(String message) {
                    Log.e(TAG,
                            "link: " + url + "\n\n" + "followRedirectedLink-error:\n\r" + message);
                }
            });
        }
        
    }
    
    private void startDownload(String url, String fileName, String name) {
        String mediaPath = FileManager.getPathFromAllaUrl(url);
        File   file      = new File(FileManager.getRootPath() + mediaPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        DownloadFile.getInstance().init(() -> {
            
            ActivityBase.toastShow(getResources().getString(R.string.completeDownload), MDToast.TYPE_SUCCESS);
            btnDownload.setVisibility(View.GONE);
            btnOpenPDF.setVisibility(View.VISIBLE);
        });
        DownloadFile.getInstance().start(url,
                AppConstants.ROOT + "/" +
                mediaPath, fileName, name, getResources().getString(R.string.alaa));
    }
    
    @Override
    public void onTagClick(String tag) {
        
        ArrayList<String> tags = new ArrayList<>();
        tags.add(tag);
        addFrg(FilterTagsFrg.newInstance(null, tags), "FilterTagsFrg");
    }
    
    private boolean checkLocationPermission() {
        
        boolean has = hasPermissions(getContext(), PERMISSIONS);
        if (!has) {
            ActivityCompat.requestPermissions(AppConfig.currentActivity, PERMISSIONS, PERMISSION_ALL);
            return false;
        }
        return true;
    }
    
}

