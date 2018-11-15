package ir.sanatisharif.android.konkur96.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spannable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.utils.URLImageGetter;

/**
 * Created by Mohamad on 11/14/2018.
 */

public class ContentFrg extends BaseFragment {

    private String TAG = "ContentFrg";

    @BindView(R.id.txtDesc)
    TextView txtDesc;

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
            "<img src=\"https://sanatisharif.ir/c/7785\">" +
            "<img src=\"https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/170917011741.jpg?w=253&h=142\">" +
            "<img src=\"https://cdn.sanatisharif.ir/upload/contentset/departmentlesson/hesaban_1806091555.jpg?w=253&h=142\">";

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
        ButterKnife.bind(this, view);

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

}
