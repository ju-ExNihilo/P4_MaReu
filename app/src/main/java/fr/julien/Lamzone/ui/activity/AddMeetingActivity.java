package fr.julien.Lamzone.ui.activity;

<<<<<<< HEAD
import android.view.MenuItem;
import androidx.annotation.NonNull;
=======
>>>>>>> a72fb5ab5cef0cced29251ab92440d56e2d62b77
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import fr.julien.Lamzone.R;
<<<<<<< HEAD
import fr.julien.Lamzone.ui.fragment.AddMeetingFragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class AddMeetingActivity extends AppCompatActivity {

    private Button addButton;
    private AddMeetingFragment addMeetingFragment;

=======
import android.content.Intent;
import android.os.Bundle;

public class AddMeetingActivity extends AppCompatActivity {

>>>>>>> a72fb5ab5cef0cced29251ab92440d56e2d62b77
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);
        this.configureToolbar();
<<<<<<< HEAD
        this.showAddMeetingFragment();
        this.getSaveButton();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home :
                finish();
                return true;
            default: return super.onOptionsItemSelected(item);
        }
=======
>>>>>>> a72fb5ab5cef0cced29251ab92440d56e2d62b77
    }

    private void configureToolbar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ((ActionBar) ab).setDisplayHomeAsUpEnabled(true);
    }

<<<<<<< HEAD
    private void getSaveButton(){
        if ( findViewById(R.id.create_land) != null){
            addButton = (Button) findViewById(R.id.create_land);
            addButton.setOnClickListener(v -> addMeetingFragment.onButtonSaveClicked());
        }
    }

    private void showAddMeetingFragment(){
        addMeetingFragment = (AddMeetingFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_add_meeting);
        if (addMeetingFragment == null) {
            this.addMeetingFragment = AddMeetingFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_add_meeting, this.addMeetingFragment).commit();
        }
    }

=======
>>>>>>> a72fb5ab5cef0cced29251ab92440d56e2d62b77
    /**
     * Used to navigate to this activity
     * @param activity
     */
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddMeetingActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
