package ir.sanatisharif.android.konkur96.adapter;

import android.animation.Animator;
import android.content.Context;
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

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.helper.FileManager;
import ir.sanatisharif.android.konkur96.listener.OnItemCheckedListener;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener;
import ir.sanatisharif.android.konkur96.listener.OnItemLongListener;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.ui.GlideApp;


/**
 * Created by Mohamad on 11/7/2016.
 */
public class VideoDownloadedAdapter extends RecyclerView.Adapter<VideoDownloadedAdapter.CustomViewHolder> {

    private ArrayList<Video> list;
    private Context mContext;
    private int layout;
    private String TAG = "LOG";
    private OnItemClickListener onItemClickListener;
    private OnItemLongListener onItemLongListener;
    private OnItemCheckedListener onItemCheckedListener;
    private Boolean isVisible = false;
    int size = 0;

    public VideoDownloadedAdapter(Context context, ArrayList<Video> list, int layout) {
        this.list = list;
        this.mContext = context;
        this.layout = layout;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {

        final Video item = list.get(position);

        holder.imgFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(position, item, view, holder);
            }
        });

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

        //load video frame
        final String videoLocalPath = FileManager.getVideoPath() + item.getName();

        if (FileManager.checkFileExist(videoLocalPath)) {

            //get frame local
            GlideApp.with(AppConfig.currentActivity)
                    .load(Uri.fromFile(new File(videoLocalPath)))
                    .centerCrop()
                    .thumbnail(0.1f)
                    .override(holder.imgFrame.getWidth(), holder.imgFrame.getWidth())
                    .into(holder.imgFrame);
        }

        //----- checkBox operation

        if (isVisible) {
            holder.checkBox.setVisibility(View.VISIBLE);

        } else {
            holder.checkBox.setVisibility(View.GONE);
        }

        holder.checkBox.setChecked(item.isChecked());
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

        @BindView(R.id.imgFrame)
        ImageView imgFrame;
        @BindView(R.id.checkBox)
        CheckBox checkBox;

        public CustomViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

            if (AppConfig.width != 0) {

                imgFrame.getLayoutParams().width = (AppConfig.width / 3);
                imgFrame.getLayoutParams().height = (AppConfig.width / 3);

            } else {

                imgFrame.getLayoutParams().width = 150;
                imgFrame.getLayoutParams().height = 150;
            }
        }


    }

}
