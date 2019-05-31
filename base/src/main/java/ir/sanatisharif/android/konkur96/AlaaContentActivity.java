package ir.sanatisharif.android.konkur96;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ir.sanatisharif.android.konkur96.ui.alaacontent.AlaaContentFragment;

public class AlaaContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alaa_content_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AlaaContentFragment.newInstance())
                    .commitNow();
        }
    }
}
