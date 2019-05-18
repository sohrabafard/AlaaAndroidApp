package ir.sanatisharif.android.konkur96.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import ir.sanatisharif.android.konkur96.R;

/**
 * Created by Mohamad on 12/26/2017.
 */

public class CustomBanner extends LinearLayout {

    private TextView txtTitle;
    private ImageView imgItem;
    private String title;

    public CustomBanner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public CustomBanner(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomBanner(Context context) {
        super(context);

        init(context);
    }

    private void init(Context context) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.custom_banner, this, true);

        txtTitle = v.findViewById(R.id.txtTitle);
        imgItem = v.findViewById(R.id.imgItem);

    }

    public void setImage(String url) {

       /* GlideApp.with(AppConfig.currentActivity)
                .load(url)
                .transforms(new CenterCrop(), new RoundedCorners(12))
                .into(imgItem);*/
    }

    public void setImage(Bitmap b) {

        imgItem.setImageBitmap(b);
    }

    public void setTitle(String title) {
        this.title = title;
        txtTitle.setText(title);
    }
}
