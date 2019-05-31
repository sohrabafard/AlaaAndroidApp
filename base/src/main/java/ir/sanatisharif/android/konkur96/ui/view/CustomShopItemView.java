package ir.sanatisharif.android.konkur96.ui.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.api.Models.ProductModel;
import ir.sanatisharif.android.konkur96.app.AppConfig;


public class CustomShopItemView extends LinearLayout {

    //-------- define primary type
    private String title;
    private String imageUrl;
    private int    width, height;
    private int    position;
    private String price, discount;
    private ProductModel item;

    //-------  define views
    private View        view;
    private CardView    cardViewRoot;
    private TextView    txtTitle;
    private TextView    txtPrice;
    private TextView    txtDiscount;
    private ImageView   imgItem;
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

            title =
                    a.getString(R.styleable.CustomShopItemView_title) !=
                    null ? a.getString(R.styleable.CustomShopItemView_title) : "not set";
            price =
                    a.getString(R.styleable.CustomShopItemView_price) !=
                    null ? a.getString(R.styleable.CustomShopItemView_price) : "0";
            discount =
                    a.getString(R.styleable.CustomShopItemView_discount) !=
                    null ? a.getString(R.styleable.CustomShopItemView_discount) : "0";

        }
        finally {
            a.recycle();
        }

        init(context);
    }

    public void setClickItem(int pos, ProductModel item) {

        this.position = pos;
        this.item = item;
    }

    public void setBackColor(int color) {

        cardViewRoot.setCardBackgroundColor(color);
    }

    public String getTitle() {
        return txtTitle.getText().toString();
    }

    public void setTitle(String title) {
        this.title = title;
        txtTitle.setText(title);
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

    public void setVisibilityDiscount(int visibility) {
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

        view = inflate(context, R.layout.category_shop_item, this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            view.setLayoutDirection(LAYOUT_DIRECTION_RTL);
        }

        cardViewRoot = findViewById(R.id.cardViewRoot);
        txtTitle = findViewById(R.id.txt_title);
        imgItem = findViewById(R.id.imgItem);
        txtPrice = findViewById(R.id.txtPrice);
        txtDiscount = findViewById(R.id.txtDiscount);

        txtTitle.setTypeface(AppConfig.fontIRSensNumber);
        txtPrice.setTypeface(AppConfig.fontIRSensNumber);
        txtDiscount.setTypeface(AppConfig.fontIRSensNumber);
        txtDiscount.setPaintFlags(txtDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);


        //set value
        txtTitle.setText(title);
        txtPrice.setText(price);
        txtDiscount.setText(discount);

        cardViewRoot.setOnClickListener(v -> {

            if (onClickItem != null) {
                onClickItem.OnClick(position, item);
            }
        });

    }

    private void loadImageWithGlide(String url) {

        RequestOptions requestOptions = new RequestOptions()
                .override(200, 250)
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .placeholder(R.mipmap.ic_launcher)
                .fitCenter();
        Glide.with(AppConfig.context)
                .load(url)
                .apply(requestOptions)
                .into(new SimpleTarget<Drawable>(460, 259) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        imgItem.setImageDrawable(resource);
                    }
                });


    }

    public void setOnClickItem(OnClickItem onClickItem) {

        this.onClickItem = onClickItem;
    }

    public interface OnClickItem {

        void OnClick(int position, Object item);
    }

}
