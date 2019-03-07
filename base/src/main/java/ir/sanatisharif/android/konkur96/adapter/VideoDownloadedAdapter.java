package ir.sanatisharif.android.konkur96.adapter;

import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.activity.VideoPlayActivity;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.app.AppConstants;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.listener.OnItemCheckedListener;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.listener.OnItemLongListener;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.ui.GlideRequest;
import ir.sanatisharif.android.konkur96.ui.GlideRequests;


/**
 * Created by Mohamad on 11/7/2016.
 */
public class VideoDownloadedAdapter extends RecyclerView.Adapter<VideoDownloadedAdapter.CustomViewHolder> {

    private List<Video> list;
    private Context mContext;
    private int layout;
    private String TAG = "LOG";
    private OnItemClickListener onItemClickListener;
    private OnItemLongListener onItemLongListener;
    private OnItemCheckedListener onItemCheckedListener;
    private Boolean isVisible = false;
    private final GlideRequest<Drawable> requestBuilder;
    int type = 0, height, width, size_grid = 0;

    public VideoDownloadedAdapter(Context context, List<Video> list, int type, GlideRequests glideRequests) {
        this.list = list;
        this.mContext = context;
        this.type = type;
        requestBuilder = glideRequests.asDrawable().fitCenter();
        setSize();
    }

    private void setSize() {

        height = (int) (AppConfig.width * 0.39f);
        height -= 48;
        width = (int) (height * 1.77f);
        size_grid = (AppConfig.width / 3);
    }

    public void updateList(Video video, int position) {
        list.set(position, video);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_layout, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        final Video item = list.get(position);

        holder.imgFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AppConfig.currentActivity, VideoPlayActivity.class);
                intent.putExtra("path", item.getPath());
                AppConfig.currentActivity.startActivity(intent);
            }
        });

        if (type == AppConstants.VIDEO_SHOW_LINEAR) {

            holder.imgFrame.getLayoutParams().width = width;
            holder.imgFrame.getLayoutParams().height = height;

            if (FileManager.checkFileExist(item.getPath())) {
                requestBuilder
                        .load(Uri.fromFile(new File(item.getPath())))
                        .override(width, height)
                        .thumbnail(0.1f)
                        .dontTransform()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .transforms(new CenterCrop(), new RoundedCorners((int) mContext.getResources().getDimension(R.dimen.round_image)))
                        .into(new SimpleTarget<Drawable>(460, 259) {
                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                holder.imgFrame.setImageDrawable(resource);
                            }
                        });
            }


        } else if (type == AppConstants.VIDEO_SHOW_GRID) {

            //load video frame
            if (FileManager.checkFileExist(item.getPath())) {

                holder.checkBox.setVisibility(View.GONE);
                if (!item.isChecked()) {
                    Log.i(TAG, "onBindViewHolder: GONE " + item.isChecked());

                } else if (item.isChecked()) {
                    Log.i(TAG, "onBindViewHolder:VISIBLE  " + item.isChecked());
                    holder.checkBox.setVisibility(View.VISIBLE);
                }

                if (AppConfig.width != 0) {

                    holder.imgFrame.getLayoutParams().width = size_grid;
                    holder.imgFrame.getLayoutParams().height = size_grid;

                } else {

                    holder.imgFrame.getLayoutParams().width = 150;
                    holder.imgFrame.getLayoutParams().height = 150;
                }

                //get frame local
                requestBuilder
                        .load(Uri.fromFile(new File(item.getPath())))
                        .override(holder.imgFrame.getWidth(), holder.imgFrame.getWidth())
                        .centerCrop()
                        .thumbnail(0.1f)
                        .dontTransform()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                                holder.imgFrame.setImageDrawable(resource);
                            }
                        });
            }

            holder.imgFrame.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    if (onItemLongListener != null) {
                        onItemLongListener.onItemClick(position, item, view, holder);
                    }

                    return true;
                }
            });

            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (onItemCheckedListener != null) {
                        onItemCheckedListener.onItemClick(position, item, view, holder, !item.isChecked());
                    }
                }
            });


            //----- checkBox operation

            if (isVisible) {
                holder.checkBox.setVisibility(View.VISIBLE);

            } else {
                holder.checkBox.setVisibility(View.GONE);
            }

            holder.checkBox.setChecked(item.isChecked());
        }

    }

    @Override
    public int getItemCount() {
        if (list == null)
            return 0;
        return list.size();
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongListener(OnItemLongListener onItemLongListener) {
        this.onItemLongListener = onItemLongListener;
    }

    public void setOnItemCheckedListener(OnItemCheckedListener onItemCheckedListener) {
        this.onItemCheckedListener = onItemCheckedListener;
    }

    public void setVisibleChk(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        protected ImageView imgFrame;
        protected CheckBox checkBox;

        public CustomViewHolder(View view) {
            super(view);

            imgFrame = view.findViewById(R.id.imgFrame);
            checkBox = view.findViewById(R.id.checkBox);

        }


    }

}
