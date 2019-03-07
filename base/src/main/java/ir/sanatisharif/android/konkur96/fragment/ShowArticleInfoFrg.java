package ir.sanatisharif.android.konkur96.fragment;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
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

import java.util.Locale;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.filter.ArticleCourse;
import ir.sanatisharif.android.konkur96.model.filter.PamphletCourse;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Mohamad on 11/14/2018.
 */

public class ShowArticleInfoFrg extends BaseFragment {

    private String TAG = "ShowContentInfoFrg";
    private TextView txtAuthor, txtTitle;
    private JustifiedTextView txtDesc;
    private WebView webView;
    private Toolbar toolbar;
    private TagGroup tagGroup;
    private static ArticleCourse course;

    public static ShowArticleInfoFrg newInstance(ArticleCourse c) {

        Bundle args = new Bundle();
        course = c;
        ShowArticleInfoFrg fragment = new ShowArticleInfoFrg();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View createFragmentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_article, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
        setData();

        Configuration configuration = getResources().getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(new Locale("fa"));
        }
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
    }

    private void setData() {

        if (course != null) {
            txtTitle.setText(course.getName());
            txtAuthor.setText(course.getAuthor().getFullName());

            new Thread(new Runnable() {
                @Override
                public void run() {

                    AppConfig.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {

                            if (course.getContext() != null)
                                webView.loadData(course.getContext(), "text/html", "utf8");
//                            if (course.getDescription() != null)
//                                txtDesc.setText(Html.fromHtml(course.getDescription()));
                            if (course.getTags().getTags() != null)
                                tagGroup.setTags(course.getTags().getTags());
                        }
                    });
                }
            }).start();


        }
    }

    private void initUI(View view) {

        setToolbar(toolbar, "نمایش محتوا");
        txtAuthor = view.findViewById(R.id.txtAuthor);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtDesc = view.findViewById(R.id.txtDesc);
        webView = view.findViewById(R.id.webView);
        tagGroup = view.findViewById(R.id.tag_group);

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

}

