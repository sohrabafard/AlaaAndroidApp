package ir.sanatisharif.android.konkur96.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;


import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.fragment.VideoPlayFrg;
import ir.sanatisharif.android.konkur96.model.Video;

public class VideoPlayActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Video video = (Video) getIntent().getExtras().get("video");
        if (video != null) {
            VideoPlayFrg videoPlayFrg = VideoPlayFrg.newInstance(video);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, videoPlayFrg, "videoPlayFrg")
                    .commit();
        }
    }
}
