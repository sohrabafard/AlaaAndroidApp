package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.model.OnBoardItem;

/**
 * Created by Mohamad on 10/26/2018.
 */

public class OnBoard_Adapter extends PagerAdapter {

    ArrayList<OnBoardItem> onBoardItems = new ArrayList<>();
    private Context mContext;


    public OnBoard_Adapter(Context mContext, ArrayList<OnBoardItem> items) {
        this.mContext = mContext;
        this.onBoardItems = items;
    }

    @Override
    public int getCount() {
        return onBoardItems.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.onboard_item, container, false);

        OnBoardItem item = onBoardItems.get(position);

        ImageView imageView = itemView.findViewById(R.id.iv_onboard);
        imageView.setImageResource(item.getImageID());

        TextView tv_title = itemView.findViewById(R.id.tv_header);
        tv_title.setText(item.getTitle());

        TextView tv_content = itemView.findViewById(R.id.tv_desc);
        tv_content.setText(item.getDescription());

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }

}
