package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.model.PlayList;
import ir.sanatisharif.android.konkur96.model.filter.VideoCourse;

public class PlayListAdapter extends RecyclerView.Adapter<PlayListAdapter.VideoHolder> {

    private List<VideoCourse> itemsList;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private int width, height, pos = -1;
    private RequestOptions requestOptions;

    public PlayListAdapter(Context context, List<VideoCourse> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
        pos = -1;
        setSize();
    }

    private void setSize() {
        width = AppConfig.width / 2;
        height = (int) (width * 0.56f);
        width -= 40;

        requestOptions = new RequestOptions()
                .override(width, height)
                .transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.round_image)))
                .dontTransform()
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .fitCenter();
    }

    public void setItemSelect(int pos) {
        this.pos = pos;
        notifyDataSetChanged();
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
        holder.txtSession.setText("   جلسه " + item.getOrder());

        if (pos > -1 && position == pos) {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                holder.linearBottom.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.shape_play_list));
            } else {
                holder.linearBottom.setBackground(ContextCompat.getDrawable(mContext, R.drawable.shape_play_list));
            }
        } else {
            holder.root.setBackgroundColor(mContext.getResources().getColor(R.color.White));
        }

        holder.imgPlayList.getLayoutParams().width = width;
        holder.imgPlayList.getLayoutParams().height = height;

        Glide.with(mContext)
                .load(item.getThumbnail())
                .apply(requestOptions)
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
        holder.root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position, item, view, holder);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class VideoHolder extends RecyclerView.ViewHolder {

        private LinearLayout root,linearBottom;
        private ImageView imgPlayList;
        private TextView txtTitle, txtSession;

        private VideoHolder(View view) {
            super(view);

            root = view.findViewById(R.id.root);
            linearBottom = view.findViewById(R.id.linearBottom);
            imgPlayList = view.findViewById(R.id.imgPlayList);
            txtTitle = view.findViewById(R.id.txtTitle);
            txtSession = view.findViewById(R.id.txtSession);

            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
            txtSession.setTypeface(AppConfig.fontIRSensNumber);

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