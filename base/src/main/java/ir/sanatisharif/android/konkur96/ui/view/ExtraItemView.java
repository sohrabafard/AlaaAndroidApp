package ir.sanatisharif.android.konkur96.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.Item;
import ir.sanatisharif.android.konkur96.ui.GlideApp;

/**
 * Created by Mohamad on 12/21/2018.
 */

public class ExtraItemView extends LinearLayout {

    //-----define primary type
    private String title;
    private String author;
    private int viewCount;
    private int contentCount;
    private String imageUrl;
    private int width, height;
    private int position;
    private Item item;

    //-----define views
    private View view;
    private Context mContext;
    private CardView cardViewRoot;
    private LinearLayout layout_click;
    private TextView txtTitle, txtView, txtAuthor;
    private ImageView imgItem, imgMenu, imgPlay;
    //---------------------
    private OnClickItem onClickItem;

    public ExtraItemView(Context context) {
        super(context);
        init(context);
    }

    public ExtraItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ExtraItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView, 0, 0);

        try {

         /*   title = a.getString(R.styleable.CustomItemView_title) != null ? a.getString(R.styleable.CustomItemView_title) : "not set";
            author = a.getString(R.styleable.CustomItemView_author) != null ? a.getString(R.styleable.CustomItemView_author) : "not set";
            contentCount = a.getInteger(R.styleable.CustomItemView_contentCount, 0);
            viewCount = a.getInteger(R.styleable.CustomItemView_viewCount, 0);*/

        } finally {
            a.recycle();
        }

        init(context);
    }
    public void setClickItem(int pos, Item item) {

        this.position = pos;
        this.item = item;
    }

    public String getTitle() {
        return txtTitle.getText().toString();
    }

    public void setTitle(String title) {
        this.title = title;
        txtTitle.setText(title);
    }

    public String getAuthor() {
        return txtAuthor.getText().toString();
    }

    public void setAuthor(String author) {
        this.author = author;
        txtAuthor.setText(author + " | ");
    }


    public String getImage() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
        loadImageWithGlide(imageUrl);
    }

    private void loadImageWithGlide(String imageUrl) {

        GlideApp.with(AppConfig.context)
                .load(item.getPhoto())
                .override(width, height)
                //.transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.round_image)))
                .into(new SimpleTarget<Drawable>(460, 259) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        imgItem.setImageDrawable(resource);
                    }
                });
    }

    private void init(Context context) {

        mContext = context;
        view = inflate(context, R.layout.extra_content_item, this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            view.setLayoutDirection(LAYOUT_DIRECTION_RTL);
        }

        cardViewRoot = findViewById(R.id.cardViewRoot);
        layout_click = (LinearLayout) findViewById(R.id.layout_click);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtView = (TextView) findViewById(R.id.txtView);
        txtAuthor = (TextView) findViewById(R.id.txtAuthor);
        imgItem = (ImageView) findViewById(R.id.imgItem);
        imgMenu = (ImageView) view.findViewById(R.id.imgMenu);
        imgPlay = (ImageView) view.findViewById(R.id.imgPlay);

        txtTitle.setTypeface(AppConfig.fontIRSensNumber);
        txtAuthor.setTypeface(AppConfig.fontIRSensNumber);

        //set value
        txtTitle.setText(title);
        txtAuthor.setText(author);

        // setSize
        setImageSize();

        cardViewRoot.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (onClickItem != null) {
                    onClickItem.OnClick(position, item);
                }
            }
        });
    }

    private void setImageSize() {

        //w= 460 and h = 259
        int height = AppConfig.itemHeight;
        int width = AppConfig.width;

        width -= 24;

        imgItem.getLayoutParams().width = width;
        imgItem.getLayoutParams().height = height;
    }

    public void setOnClickItem(OnClickItem onClickItem) {

        this.onClickItem = onClickItem;
    }

    public interface OnClickItem {

        void OnClick(int position, Object item);
    }
}
