package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.model.PlayList;
import ir.sanatisharif.android.konkur96.model.filter.VideoCourse;
import ir.sanatisharif.android.konkur96.ui.GlideApp;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.VideoHolder> {

    private List<VideoCourse> itemsList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;

    public PlayListAdapter(Context context, List<VideoCourse> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    public void setOnClick(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new VideoHolder(LayoutInflater.from(mContext).inflate(R.layout.play_list_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(final VideoHolder holder, final int position) {

        final VideoCourse item = itemsList.get(position);
        holder.txtTitle.setText(item.getName());

        int width = AppConfig.width / 2;
        width -= 8;
        int height = (int) (width * 0.56f);
        holder.imgPlayList.getLayoutParams().width = width;
        holder.imgPlayList.getLayoutParams().height = height;

        GlideApp.with(AppConfig.context)
                .load(item.getThumbnail())
                .override(width, height)
                // .transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.round_image)))
                .into(new SimpleTarget<Drawable>(460, 259) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        holder.imgPlayList.setImageDrawable(resource);
                    }
                });

        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, item, view, holder);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class VideoHolder extends RecyclerView.ViewHolder {

        private LinearLayout root;
        private ImageView imgPlayList;
        private TextView txtTitle;

        private VideoHolder(View view) {
            super(view);

            root = view.findViewById(R.id.root);
            imgPlayList = view.findViewById(R.id.imgPlayList);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtTitle.setTypeface(AppConfig.fontIRSensNumber);

            ripple(root, 0);
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