package ir.sanatisharif.android.konkur96.api;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.adapter.FilterAdapter;
import ir.sanatisharif.android.konkur96.model.Video;
import ir.sanatisharif.android.konkur96.model.filter.VideoCourse;

/**
 * Created by Mohamad on 2/27/2019.
 */

public class SearchListAdapter {
} /*extends PagedListAdapter<VideoCourse,SearchListAdapter.VideoHolder> {


    public SearchListAdapter() {
        super();
    }

    @NonNull
    @Override
    public VideoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VideoHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_video_filter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VideoHolder holder, int position) {

        final VideoCourse item = getItem(position);
        final VideoHolder itemHolder = (VideoHolder) holder;
        itemHolder.txtTitle.setText(item.getName());
        itemHolder.txtAuthor.setText(item.getAuthor().getFullName());
        itemHolder.txtSession.setText(" - جلسه "+ item.getOrder());
    }

    public class VideoHolder extends RecyclerView.ViewHolder{

        protected TextView txtTitle, txtAuthor, txtSession;
        private VideoHolder(View view) {
            super(view);

            txtTitle = view.findViewById(R.id.txtTitle);
            txtAuthor = view.findViewById(R.id.txtAuthor);
            txtSession = view.findViewById(R.id.txtSession);

        }
    }
}*/
