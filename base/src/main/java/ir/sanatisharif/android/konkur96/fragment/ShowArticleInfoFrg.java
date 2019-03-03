package ir.sanatisharif.android.konkur96.fragment;

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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.uncopt.android.widget.text.justify.JustifiedTextView;

import org.greenrobot.eventbus.EventBus;

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
    private JustifiedTextView txtDesc, txtContext;
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
    }

    private void setData() {

        if (course != null) {
            txtTitle.setText(course.getName());
            txtAuthor.setText(course.getAuthor().getFullName());

            AppConfig.HANDLER.post(new Runnable() {
                @Override
                public void run() {

                    txtDesc.setText(Html.fromHtml(course.getDescription()));
                    txtContext.setText(Html.fromHtml(course.getContext()));
                    tagGroup.setTags(course.getTags().getTags());
                }
            });
        }
    }

    private void initUI(View view) {

        setToolbar(toolbar, "نمایش محتوا");
        txtAuthor = view.findViewById(R.id.txtAuthor);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtDesc = view.findViewById(R.id.txtDesc);
        txtContext = view.findViewById(R.id.txtContext);
        tagGroup = view.findViewById(R.id.tag_group);
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

