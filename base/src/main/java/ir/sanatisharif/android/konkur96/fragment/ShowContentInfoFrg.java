package ir.sanatisharif.android.konkur96.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
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
import ir.sanatisharif.android.konkur96.model.filter.PamphletCourse;
import ir.sanatisharif.android.konkur96.ui.view.MDToast;
import ir.sanatisharif.android.konkur96.utils.URLImageGetter;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by Mohamad on 11/14/2018.
 */

public class ShowContentInfoFrg extends BaseFragment implements View.OnClickListener {

    private String TAG = "ShowContentInfoFrg";
    private TextView txtAuthor, txtTitle;
    private JustifiedTextView txtDesc;
    private Button btnDownload;
    private Toolbar toolbar;
    private TagGroup tagGroup;
    private static PamphletCourse course;

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

            AppConfig.HANDLER.post(new Runnable() {
                @Override
                public void run() {

                    txtDesc.setText(Html.fromHtml(course.getDescription()));
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
        btnDownload = view.findViewById(R.id.btnDownload);
        tagGroup = view.findViewById(R.id.tag_group);
        btnDownload.setOnClickListener(this);
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

        ActivityBase.toastShow("not imp", MDToast.TYPE_INFO);
    }
}

