package ir.sanatisharif.android.konkur96.fragment;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.uncopt.android.widget.text.justify.JustifiedTextView;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Locale;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.model.filter.ArticleCourse;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Mohamad on 11/14/2018.
 */

public class ShowArticleInfoFrg extends BaseFragment {

    private static ArticleCourse course;
    private String TAG = "ShowContentInfoFrg";
    private TextView txtAuthor, txtTitle;
    private JustifiedTextView txtContext, txtDesc;
    // private WebView webView;
    private Toolbar toolbar;
    private TagGroup tagGroup;

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
            if (course.getName() != null)
                txtTitle.setText(course.getName());
            if (course.getAuthor().getFullName() != null)
                txtAuthor.setText(course.getAuthor().getFullName());

            new Thread(new Runnable() {
                @Override
                public void run() {

                    AppConfig.HANDLER.post(new Runnable() {
                        @Override
                        public void run() {

                            if (course.getContext() != null) {
                                convertStringTo(course.getContext());
                                txtContext.setText(Html.fromHtml(course.getContext()));
                            }
                            if (course.getDescription() != null)
                                txtDesc.setText(Html.fromHtml(course.getDescription()));
                            if (course.getTags().getTags() != null)
                                tagGroup.setTags(course.getTags().getTags());
                        }
                    });
                }
            }).start();


        }
    }

    private void convertStringTo(String input) {

        InputStream inputStream = new ByteArrayInputStream(input.getBytes(Charset.forName("UTF-8")));

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String output = null;
        try {
            output = bufferedReader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (output != null) {
            //  System.out.println(output);
            try {
                output = bufferedReader.readLine();
                Log.i(TAG, "convertStringTo: " + output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initUI(View view) {

        setToolbar(toolbar, "نمایش محتوا");
        txtAuthor = view.findViewById(R.id.txtAuthor);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtDesc = view.findViewById(R.id.txtDesc);
        txtContext = view.findViewById(R.id.txtContext);
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

