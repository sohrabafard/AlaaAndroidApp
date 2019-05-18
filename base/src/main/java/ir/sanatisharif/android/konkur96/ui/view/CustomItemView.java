package ir.sanatisharif.android.konkur96.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;

import ir.sanatisharif.android.konkur96.model.Item;
import ir.sanatisharif.android.konkur96.model.main_page.Set;
import ir.sanatisharif.android.konkur96.ui.GlideApp;


/**
 * Created by Mohamad on 11/25/2018.
 */

public class CustomItemView extends LinearLayout {

    //-------- define primary type
    private String title;
    private String price;
    private String author;
    private int contentCount;
    private String imageUrl;
    private int width, height;
    private int position;
    private Set item;
    private int layout;

    //-------  define views
    private View view;
    private Context mContext;
    private CardView cardViewRoot;
    private LinearLayout layout_click;
    private TextView txtTitle;
    private TextView txtAuthor;
    private TextView txtPrice;
    private TextView txtContentCount;
    private ImageView imgItem;
    //---------------
    private OnClickItem onClickItem;

    public CustomItemView(Context context, int layout) {
        super(context);
        setLayout(layout);
        init(context);
    }

    public CustomItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public CustomItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomItemView, 0, 0);

        try {
            title = a.getString(R.styleable.CustomItemView_title) != null ? a.getString(R.styleable.CustomItemView_title) : "not set";
            author = a.getString(R.styleable.CustomItemView_author) != null ? a.getString(R.styleable.CustomItemView_author) : "not set";
            contentCount = a.getInteger(R.styleable.CustomItemView_contentCount, 0);
            //viewCount = a.getInteger(R.styleable.CustomItemView_viewCount, 0);

        } finally {
            a.recycle();
        }

        init(context);
    }

    public void setClickItem(int pos) {
        this.position = pos;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        txtPrice.setText(price + " تومان ");
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
        if (layout == R.layout.category_item)
            txtAuthor.setText(author + " | ");
        else
            txtAuthor.setText(author + "");
    }

    public String getContentCount() {
        return txtContentCount.getText().toString();
    }

    public void setContentCount(int contentCount) {
        this.contentCount = contentCount;
        txtContentCount.setText(contentCount + "");
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
        loadImageWithGlide(imageUrl);
    }

    private void setLayout(int layout) {
        this.layout = layout;
    }

    private void init(Context context) {

        mContext = context;
        view = inflate(context, layout, this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            view.setLayoutDirection(LAYOUT_DIRECTION_RTL);
        }

        cardViewRoot = findViewById(R.id.cardViewRoot);
        layout_click = findViewById(R.id.layout_click);
        txtTitle = findViewById(R.id.txtTitle);
        imgItem = findViewById(R.id.imgItem);

        if (layout == R.layout.category_item) {
            txtAuthor = findViewById(R.id.txtAuthor);
            txtContentCount = findViewById(R.id.txtContentCount);
            txtAuthor.setTypeface(AppConfig.fontIRSensNumber);
            txtContentCount.setTypeface(AppConfig.fontIRSensNumber);

            txtAuthor.setText(author);
            txtContentCount.setText(contentCount + "");

        } else if (layout == R.layout.content_item) {
            txtAuthor = findViewById(R.id.txtAuthor);
            txtAuthor.setTypeface(AppConfig.fontIRSensNumber);
            txtAuthor.setText(author);

        } else if (layout == R.layout.product_main_item) {
            txtPrice = findViewById(R.id.txtPrice);
            txtPrice.setTypeface(AppConfig.fontIRSensNumber);
            // Log.i("LOG", "onBindViewHolder: "+price);
        }

        txtTitle.setTypeface(AppConfig.fontIRSensNumber);
        txtTitle.setText(title);
        setImageSize();

        cardViewRoot.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {

                if (onClickItem != null) {
                    onClickItem.OnClick(position);
                }
            }
        });

    }

    private void loadImageWithGlide(String url) {
        GlideApp
                .with(mContext)
                .load(url)
                .override(width, height)
                .transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.round_image)))
                .into(new SimpleTarget<Drawable>(460, 259) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        imgItem.setImageDrawable(resource);
                    }
                });
    }

    private void setImageSize() {

        txtTitle.measure(0, 0);
        // txtAuthor.measure(0, 0);

        //w= 460 and h = 259
        height = AppConfig.itemHeight - txtTitle.getMeasuredHeight() - txtTitle.getMeasuredHeight();

        height -= 24;
//        if (layout == R.layout.product_main_item)
//            width -= 24;
//        else
        width = (int) (height * 1.77f);

        imgItem.getLayoutParams().width = width;
        imgItem.getLayoutParams().height = height;
    }

    public void setOnClickItem(OnClickItem onClickItem) {

        this.onClickItem = onClickItem;
    }

//    public void setGlide(GlideRequests glideRequests) {
//
//        requestBuilder = glideRequests.asDrawable().fitCenter();
//    }

    public interface OnClickItem {

        void OnClick(int position);
    }

}
