package ir.sanatisharif.android.konkur96.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.ShopItem;
import ir.sanatisharif.android.konkur96.ui.GlideApp;


public class CustomShopItemView extends LinearLayout {

    //-------- define primary type
    private String title;
    private String author;
    private int viewCount;
    private String imageUrl;
    private int width, height;
    private int position;
    private String price, discount;
    private ShopItem item;

    //-------  define views
    private View view;
    private Context mContext;
    private CardView cardViewRoot;
    private LinearLayout layout_click;
    private TextView txtTitle;
    private TextView txtAuthor;
    private TextView txtPrice;
    private TextView txtDiscount;
    private ImageView imgItem;
    //---------------
    private OnClickItem onClickItem;

    public CustomShopItemView(Context context) {
        super(context);
        init(context);
    }

    public CustomShopItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomShopItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomShopItemView, 0, 0);

        try {

            title = a.getString(R.styleable.CustomShopItemView_title) != null ? a.getString(R.styleable.CustomShopItemView_title) : "not set";
            author = a.getString(R.styleable.CustomShopItemView_author) != null ? a.getString(R.styleable.CustomShopItemView_author) : "not set";
            price = a.getString(R.styleable.CustomShopItemView_price) != null ? a.getString(R.styleable.CustomShopItemView_price) : "0";
            discount = a.getString(R.styleable.CustomShopItemView_discount) != null ? a.getString(R.styleable.CustomShopItemView_discount) : "0";
            viewCount = a.getInteger(R.styleable.CustomShopItemView_viewCount, 0);

        } finally {
            a.recycle();
        }

        init(context);
    }

    public void setClickItem(int pos, ShopItem item) {

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
        txtAuthor.setText(author);
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        txtPrice.setText(price + " تومان ");
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
        txtDiscount.setText(discount + " تومان ");
    }

    public void setVisibilityDiscount(int visibility){
        this.txtDiscount.setVisibility(visibility);
    }

    public String getImage() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
        loadImageWithGlide(imageUrl);
    }


    private void init(Context context) {

        mContext = context;
        view = inflate(context, R.layout.category_shop_item, this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            view.setLayoutDirection(LAYOUT_DIRECTION_RTL);
        }

        cardViewRoot = findViewById(R.id.cardViewRoot);
        layout_click = findViewById(R.id.layout_click);
        txtTitle = findViewById(R.id.txtTitle);
        txtAuthor = findViewById(R.id.txtAuthor);
        imgItem = findViewById(R.id.imgItem);
        txtPrice = findViewById(R.id.txtPrice);
        txtDiscount = findViewById(R.id.txtDiscount);

        txtTitle.setTypeface(AppConfig.fontIRSensNumber);
        txtAuthor.setTypeface(AppConfig.fontIRSensNumber);
        txtPrice.setTypeface(AppConfig.fontIRSensNumber);
        txtDiscount.setTypeface(AppConfig.fontIRSensNumber);
        txtDiscount.setPaintFlags(txtDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        //set value
        txtTitle.setText(title);
        txtAuthor.setText(author);
        txtPrice.setText(price);
        txtDiscount.setText(discount);


        // setSize
        setImageSize();

        cardViewRoot.setOnClickListener(v -> {

            if (onClickItem != null) {
                onClickItem.OnClick(position, item);
            }
        });

    }

    private void loadImageWithGlide(String url) {

        GlideApp.with(AppConfig.context)
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
        txtAuthor.measure(0, 0);

        //w= 460 and h = 259
        height = AppConfig.shopItemHeight - txtTitle.getMeasuredHeight() - txtAuthor.getMeasuredHeight();

        height -= 24;
        width = (int) (height * 1.77f);

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
