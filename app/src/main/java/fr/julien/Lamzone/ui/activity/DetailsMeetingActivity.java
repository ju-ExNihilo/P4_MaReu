package fr.julien.Lamzone.ui.activity;

import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import androidx.appcompat.widget.Toolbar;
=======
>>>>>>> a72fb5ab5cef0cced29251ab92440d56e2d62b77
import fr.julien.Lamzone.R;
import android.os.Bundle;
import fr.julien.Lamzone.ui.fragment.DetailsMeetingFragment;

public class DetailsMeetingActivity extends AppCompatActivity {

    private DetailsMeetingFragment detailsMeetingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_meeting);
<<<<<<< HEAD
        this.showDetailMeetingFragment();
        this.configureToolbar();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
               finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    private void showDetailMeetingFragment(){
        detailsMeetingFragment = (DetailsMeetingFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail_activity);
        if (detailsMeetingFragment == null && findViewById(R.id.frame_layout_detail_activity) != null) {
            this.detailsMeetingFragment = DetailsMeetingFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout_detail_activity, this.detailsMeetingFragment).commit();
        }
=======
        this.configureToolbar();
>>>>>>> a72fb5ab5cef0cced29251ab92440d56e2d62b77
    }

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_details);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ((ActionBar) ab).setDisplayHomeAsUpEnabled(true);
    }
}
