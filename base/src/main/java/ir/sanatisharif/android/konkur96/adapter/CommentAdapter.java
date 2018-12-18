package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balysv.materialripple.MaterialRippleLayout;

import java.util.ArrayList;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.model.Comment;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.ui.view.CircleTransform;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.VideoHolder> {

    private ArrayList<Comment> itemsList;
    private Context mContext;


    public CommentAdapter(Context context, ArrayList<Comment> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int type) {
        return new VideoHolder(LayoutInflater.from(mContext).inflate(R.layout.comment_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(final VideoHolder holder, final int position) {

        Comment item = itemsList.get(position);
        holder.txtUserName.setText(item.getName());
        holder.txtContent.setText(item.getContent());


        GlideApp.with(AppConfig.context)
                .load("http://yakhmakgroup.ir/jokLike/v1/images/imageLogo/1765554797035373646.jpg")
                .transform(new CircleTransform(AppConfig.context))
                .override(72,72)
                .into(holder.imgUser);
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class VideoHolder extends RecyclerView.ViewHolder {

        private TextView txtUserName, txtContent;
        private ImageView imgUser;

        private VideoHolder(View view) {
            super(view);

            txtUserName = (TextView) view.findViewById(R.id.txtUserName);
            txtContent = (TextView) view.findViewById(R.id.txtContent);
            imgUser = (ImageView) view.findViewById(R.id.imgUser);

            txtUserName.setTypeface(AppConfig.fontIRSensNumber);
            txtContent.setTypeface(AppConfig.fontIRSensNumber);
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