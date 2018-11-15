package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.model.VideoItemSet;
import ir.sanatisharif.android.konkur96.ui.GlideApp;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class VideoItemAdapter extends RecyclerView.Adapter<VideoItemAdapter.VideoHolder> {

    private ArrayList<VideoItemSet> itemsList;
    private Context mContext;


    public VideoItemAdapter(Context context, ArrayList<VideoItemSet> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {
        return new VideoHolder(LayoutInflater.from(mContext).inflate(R.layout.video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final VideoHolder holder, final int position) {

        VideoItemSet item = itemsList.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtAuthor.setText(item.getAuthor()+" | "+"22 فیلم");

        holder.txtTitle.measure(0, 0);
        holder.txtAuthor.measure(0, 0);

        int width =
                AppConfig.itemHeight -
                holder.txtTitle.getMeasuredHeight() -
                holder.txtAuthor.getMeasuredHeight();
        width -= 24;
        // int height = (int) (width * 0.56f);

        holder.imgItem.getLayoutParams().width = width;
        holder.imgItem.getLayoutParams().height = width;

        GlideApp.with(AppConfig.context)
                .load(item.getUrl())
                .override(width, width)
                .transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.round_image)))
                .into(new SimpleTarget<Drawable>(width, width) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        holder.imgItem.setImageDrawable(resource);
                    }
                });


        holder.layout_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addFrg(DetailsVideoFrg.newInstance(), "DetailsVideoFrg");

            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class VideoHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout_click;
        private TextView txtTitle, txtAuthor;
        private ImageView imgItem;

        private VideoHolder(View view) {
            super(view);

            layout_click = (LinearLayout) view.findViewById(R.id.layout_click);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtAuthor = (TextView) view.findViewById(R.id.txtAuther);
            imgItem = (ImageView) view.findViewById(R.id.imgItem);

            //txtAuthor.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_author, 0);

            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
            txtAuthor.setTypeface(AppConfig.fontIRSensNumber);

            ripple(layout_click, (int) mContext.getResources().getDimension(R.dimen.round_image));
        }

        void ripple(View view, int radius) {
            MaterialRippleLayout.on(view)
                    .rippleOverlay(true)
                    .rippleAlpha(0.1f)
                    .rippleRoundedCorners(radius)
                    .rippleHover(true)
                    .create();
        }
    }


}