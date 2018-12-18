package ir.sanatisharif.android.konkur96.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import ir.sanatisharif.android.konkur96.R;

import ir.sanatisharif.android.konkur96.model.Events;
import ir.sanatisharif.android.konkur96.utils.URLImageGetter;

/**
 * Created by Mohamad on 11/14/2018.
 */

public class ContentFrg extends BaseFragment {

    private String TAG = "ContentFrg";
    private TextView txtDesc;
    private Toolbar toolbar;

    String content = "<h1>Heading 1</h1>\n" +
            "        <h2>Heading 2</h2>\n" +
            "        <p>This is some html. Look, here\\'s an <u>underline</u>.</p>\n" +
            "        <p>Look, this is <em>emphasized.</em> And here\\'s some <b>bold</b>.</p>\n" +
            "        <p>Here are UL list items:\n" +
            "        <ul>\n" +
            "        <li>One</li>\n" +
            "        <li>Two</li>\n" +
            "        <li>Three</li>\n" +
            "        </ul>\n" +
            "        <p>Here are OL list items:\n" +
            "        <ol>\n" +
            "        <li>One</li>\n" +
            "        <li>Two</li>\n" +
            "        <li>Three</li>\n" +
            "        </ol>";

    String source = " <b> فیلم جلسه 27 - تحلیل کنکور سراسری 95 </b> " +
            " <br/>" +
            "6 عکس درج شده: <br/>" +
            " <br/>" +
            " بعد از بحث جمع بندیمون راجع به دروس پیش دانشگاهی راجع به ژنتیک و گیاهی با اتق فکر آلاء به این نتیجه رسیدیم که کنکور سراسری سال های قبل رو با هم تحلیل و بررسی کنیم فقط قبلش حتما خودتون کنکور سراسری رو بذارین جلو دستتون حل کنید و تحیلیل کنید و بعدش این فیلم رو ببنید" +
            " <br/>" +
            "<a href=\"https://sanatisharif.ir/c?tags[0]=%D8%B6%D8%A8%D8%B7_%D8%A7%D8%B3%D8%AA%D9%88%D8%AF%DB%8C%D9%88%DB%8C%DB%8C\">نکته و تست</a> <br/>" +
            "<img src=\"https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/riyazi_tajrobi_1809261626.jpg?w=253&h=142\"><br/>and<br/>" +
            "<img src=\"https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/shimi_1809301705.jpg?w=253&h=142\">" +
            "<img src=\"https://sanatisharif.ir/c/7785\">";

    public static ContentFrg newInstance() {

        Bundle args = new Bundle();

        ContentFrg fragment = new ContentFrg();
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

        txtDesc = view.findViewById(R.id.txtDesc);
        setHasOptionsMenu(true);
        setToolbar(toolbar,"نمایش محتوا");

        URLImageGetter imageGetter = new URLImageGetter(getContext(), txtDesc);
        imageGetter.getDrawable("");

        Spannable html;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            html = (Spannable) Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY, imageGetter, null);
        } else {
            html = (Spannable) Html.fromHtml(source, imageGetter, null);
        }
        txtDesc.setText(html);
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

