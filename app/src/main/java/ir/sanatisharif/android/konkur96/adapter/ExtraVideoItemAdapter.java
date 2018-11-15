package ir.sanatisharif.android.konkur96.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.Request;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.transition.Transition;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.activity.ActivityBase;
import ir.sanatisharif.android.konkur96.app.AppConfig;
import ir.sanatisharif.android.konkur96.fragment.DetailsVideoFrg;
import ir.sanatisharif.android.konkur96.listener.OnItemClickListener1111;
import ir.sanatisharif.android.konkur96.model.VideoItemSet;
import ir.sanatisharif.android.konkur96.ui.GlideApp;
import ir.sanatisharif.android.konkur96.ui.view.CircleTransform;

import static ir.sanatisharif.android.konkur96.activity.MainActivity.addFrg;

public class ExtraVideoItemAdapter extends RecyclerView.Adapter<ExtraVideoItemAdapter.VideoHolder> {

    private ArrayList<VideoItemSet> itemsList;
    private Context mContext;
    private OnItemClickListener1111 mClickListener;

    public ExtraVideoItemAdapter(Context context, ArrayList<VideoItemSet> itemsList) {
        this.itemsList = itemsList;
        this.mContext = context;
    }

    @Override
    public VideoHolder onCreateViewHolder(ViewGroup parent, int typeviewsingle) {
        return new VideoHolder(LayoutInflater.from(mContext).inflate(R.layout.extra_video_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final VideoHolder holder, final int position) {

        final VideoItemSet item = itemsList.get(position);
        holder.txtTitle.setText(item.getTitle());
        holder.txtAuthor.setText(item.getAuthor());
        holder.txtView.setText(item.getView()+" ویو");

        GlideApp.with(AppConfig.context)
                .load(item.getUrl())
                .transform(new CircleTransform(AppConfig.context))
                .into(holder.imgAuthor);

        holder.layout_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addFrg(DetailsVideoFrg.newInstance(), "DetailsVideoFrg");

            }
        });
        holder.imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                PopupMenu popup = new PopupMenu(AppConfig.currentActivity, view);
                popup.getMenuInflater().inflate(R.menu.menu_post, popup.getMenu());
                popup.show();


                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item1) {
                        switch (item1.getItemId()) {

                            case R.id.action_watch:
                                addFrg(DetailsVideoFrg.newInstance(), "DetailsVideoFrg");

                                return true;

                            case R.id.action_share:

                                return true;


                        }
                        return false;
                    }
                });
            }
        });

        GlideApp.with(AppConfig.context)
                .load(item.getUrl()).
                into(new FutureTarget<Drawable>() {
                    @Override
                    public void onLoadStarted(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {

                        holder.imgItem.setImageDrawable(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void getSize(SizeReadyCallback cb) {
                        cb.onSizeReady(100, 100);
                    }

                    @Override
                    public void removeCallback(SizeReadyCallback cb) {

                    }

                    @Override
                    public void setRequest(@Nullable Request request) {

                    }

                    @Nullable
                    @Override
                    public Request getRequest() {
                        return null;
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onStop() {

                    }

                    @Override
                    public void onDestroy() {

                    }

                    @Override
                    public boolean cancel(boolean b) {
                        return false;
                    }

                    @Override
                    public boolean isCancelled() {
                        return false;
                    }

                    @Override
                    public boolean isDone() {
                        return false;
                    }

                    @Override
                    public Drawable get() throws InterruptedException, ExecutionException {
                        return null;
                    }

                    @Override
                    public Drawable get(long l, @NonNull TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
                        return null;
                    }
                });




      /*  itemRowHolder.txtMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mClickListener != null) {
                    mClickListener.onItemClick(position, v, itemRowHolder);
                }
            }
        });*/


      /*  GlideApp.with(AppConfig.context)
                .load(item.getUrl())
                .transforms(new CenterCrop(), new RoundedCorners(12))
                .into(holder.imgItem);*/
           /* holder.layout_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), position + "", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(v.getContext(), Second_MainActivity.class);
                    mContext.startActivity(intent);
                }
            });*/

    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }


    public class VideoHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout_click;
        private TextView txtTitle, txtAuthor, txtView;
        private ImageView imgItem, imgAuthor, imgMenu;

        private VideoHolder(View view) {
            super(view);

            layout_click = (LinearLayout) view.findViewById(R.id.layout_click);
            txtTitle = (TextView) view.findViewById(R.id.txtTitle);
            txtView = (TextView) view.findViewById(R.id.txtView);
            txtAuthor = (TextView) view.findViewById(R.id.txtAuthor);
            imgItem = (ImageView) view.findViewById(R.id.imgItem);
            imgAuthor = (ImageView) view.findViewById(R.id.imgAuthor);
            imgMenu = (ImageView) view.findViewById(R.id.imgMenu);

            txtTitle.setTypeface(AppConfig.fontIRSensNumber);
            txtAuthor.setTypeface(AppConfig.fontIRSensNumber);
            txtView.setTypeface(AppConfig.fontIRSensNumber);

        }
    }

    public void setOnItemClickListener(OnItemClickListener1111 itemClickListener) {
        this.mClickListener = itemClickListener;
    }

}