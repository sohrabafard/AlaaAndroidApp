package ir.sanatisharif.android.konkur96.activity;

import android.os.Bundle;
import android.view.WindowManager;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.fragment.VideoPlayFrg;

public class VideoPlayActivity extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_play);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String path = getIntent().getExtras().getString("path");
        if (path != null) {
            VideoPlayFrg videoPlayFrg = VideoPlayFrg.newInstance(path);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, videoPlayFrg, "videoPlayFrg")
                    .commit();
        }
    }
}
