package ir.sanatisharif.android.konkur96.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.fragment.SettingsFragment;

public class SettingActivity extends ActivityBase {

    private Toolbar toolbar;
    private TextView txtToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        initUI();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, SettingsFragment.newInstance())
                .commit();
    }

    private void initUI() {

        showToolBar(getString(R.string.settings));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showToolBar(String title) {

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        txtToolbarTitle = toolbar.findViewById(R.id.txtToolbarTitle);
        txtToolbarTitle.setText(title);
    }
}
