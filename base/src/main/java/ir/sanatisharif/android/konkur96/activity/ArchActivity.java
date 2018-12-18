package ir.sanatisharif.android.konkur96.activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ir.sanatisharif.android.konkur96.R;

public class ArchActivity extends AppCompatActivity {

    private TextView txtCount;
    private TextView txtLiveData;
    private Button btnChangeLiveData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arch);

        txtCount = findViewById(R.id.txtCount);
        txtLiveData = findViewById(R.id.txtLiveData);
        btnChangeLiveData = findViewById(R.id.btnChangeLiveData);

//-------------------------------------------------

        MyViewModel myViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

        txtCount.setText("count " + myViewModel.getRotationCount());

        //live

        final MyViewModel_1 viewMode1 = ViewModelProviders.of(this).get(MyViewModel_1.class);

        Observer<String> observer = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                txtLiveData.setText(s);
            }
        };

        viewMode1.getCurrentName().observe(this, observer);

        btnChangeLiveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String anotherName = "John Doe";
                viewMode1.getCurrentName().setValue(anotherName);
            }
        });
    }
}
