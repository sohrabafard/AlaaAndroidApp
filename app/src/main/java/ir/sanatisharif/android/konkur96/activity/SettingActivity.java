package ir.sanatisharif.android.konkur96.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.sanatisharif.android.konkur96.R;
import ir.sanatisharif.android.konkur96.fragment.SettingsFragment;

public class SettingActivity extends ActivityBase {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.txtToolbarTitle)
    TextView txtToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        showToolBar(getString(R.string.settings));
        getSupportFragmentManager().beginTransaction()
                .add(R.id.content, SettingsFragment.newInstance())
                .commit();
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

        setSupportActionBar(toolbar);
        setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        txtToolbarTitle.setText(title);
    }
}
