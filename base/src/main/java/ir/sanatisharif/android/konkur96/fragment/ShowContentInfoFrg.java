package ir.sanatisharif.android.konkur96.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.uncopt.android.widget.text.justify.JustifiedTextView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;

import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.dialog.DownloadDialogFrg;
import ir.sanatisharif.android.konkur96.dialog.MyAlertDialogFrg;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.listener.DownloadComplete;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.filter.PamphletCourse;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.DownloadFile;
import ir.sanatisharif.android.konkur96.utils.OpenFile;
import ir.sanatisharif.android.konkur96.utils.URLImageGetter;
import ir.sanatisharif.android.konkur96.utils.Utils;
import me.gujun.android.taggroup.TagGroup;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

/**
 * Created by Mohamad on 11/14/2018.
 */

public class ShowContentInfoFrg extends BaseFragment implements
        View.OnClickListener, TagGroup.OnTagClickListener {

    private String TAG = "ShowContentInfoFrg";
    private TextView txtAuthor, txtTitle;
    //  private JustifiedTextView txtDesc;
    private WebView webView;
    private Button btnDownload, btnOpenPDF;
    private Toolbar toolbar;
    private TagGroup tagGroup;
    private static PamphletCourse course;
    private static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,};
    private static final int PERMISSION_ALL = 1;

    public static ShowContentInfoFrg newInstance(PamphletCourse c) {

        Bundle args = new Bundle();
        course = c;
        ShowContentInfoFrg fragment = new ShowContentInfoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        setData();

//        URLImageGetter imageGetter = new URLImageGetter(getContext(), txtDesc);
//        imageGetter.getDrawable("");
//
//        Spannable html;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            html = (Spannable) Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
//        } else {
//            html = (Spannable) Html.fromHtml(source, imageGetter, null);
//        }
    }

    private void setData() {

        if (course != null) {
            txtTitle.setText(course.getName());
            txtAuthor.setText(course.getAuthor().getFullName());
            tagGroup.setTags(course.getTags().getTags());

            new Thread(new Runnable() {
                @Override
                public void run() {

                    AppConfig.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {
                            if (course.getDescription() != null)
                                webView.loadData(course.getDescription(), "text/html", "UTF-8");
                            if (course.getTags().getTags() != null)
                                tagGroup.setTags(course.getTags().getTags());
                        }
                    });
                }
            }).start();

            String fileName = course.getFile().getPamphlet().get(0).getFileName();
            if (fileName != null) {
                if (FileManager.checkFileExist(FileManager.getPDFPath() + fileName)) {
                    btnDownload.setVisibility(View.GONE);
                    btnOpenPDF.setVisibility(View.VISIBLE);
                } else {
                    btnDownload.setVisibility(View.VISIBLE);
                    btnOpenPDF.setVisibility(View.GONE);
                }
            }

        }
    }

    private void initUI(View view) {

        setToolbar(toolbar, "نمایش محتوا");
        txtAuthor = view.findViewById(R.id.txtAuthor);
        txtTitle = view.findViewById(R.id.txtTitle);
        webView = view.findViewById(R.id.webView);
        btnDownload = view.findViewById(R.id.btnDownload);
        btnOpenPDF = view.findViewById(R.id.btnOpenPDF);
        tagGroup = view.findViewById(R.id.tag_group);
        btnDownload.setOnClickListener(this);
        btnOpenPDF.setOnClickListener(this);
        tagGroup.setOnTagClickListener(this);

        for (View v : tagGroup.getTouchables()) {
            if (v instanceof TextView) {
                ((TextView) v).setTypeface(AppConfig.fontIRSensNumber);
            }
        }
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

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnDownload) {
            MyAlertDialogFrg alert = new MyAlertDialogFrg();
            alert.setTitle("دانلود ");
            alert.setMessage("آیا مایل به دانلود این فایل هستید؟");
            alert.setListener(new MyAlertDialogFrg.MyAlertDialogListener() {
                @Override
                public void setOnPositive() {

                    if (checkLocationPermission()) {
                        if (course.getFile().getPamphlet().get(0).getLink() != null) {
                            String url = course.getFile().getPamphlet().get(0).getLink();
                            String fileName = course.getFile().getPamphlet().get(0).getFileName();
                            String name = course.getName();
                            download(url, fileName, name);
                        } else
                            ActivityBase.toastShow("لینک دانلود معتبر نیست!", MDToast.TYPE_ERROR);
                    }
                }

                @Override
                public void setOnNegative() {

                }
            });
            alert.show(getFragmentManager(), "alert");

        } else if (view.getId() == R.id.btnOpenPDF) {

            String fileName = course.getFile().getPamphlet().get(0).getFileName();
            if (fileName != null) {
                startActivity(OpenFile.getPdfFileIntent(FileManager.getPDFPath() + "/" + fileName));
            }
        }

    }

    private void download(String url, String fileName, String name) {

        DownloadFile.getInstance().init(getContext(), new DownloadComplete() {
            @Override
            public void complete() {

                ActivityBase.toastShow(getResources().getString(R.string.completeDownload), MDToast.TYPE_SUCCESS);
                btnDownload.setVisibility(View.GONE);
                btnOpenPDF.setVisibility(View.GONE);
            }
        });

        DownloadFile.getInstance().start(url,
                AppConstants.ROOT + "/" + AppConstants.PDF, fileName, name, "");
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

}
