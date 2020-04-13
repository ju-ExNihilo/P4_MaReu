package fr.julien.Lamzone.ui.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.navigation.NavigationView;
import java.util.Calendar;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.julien.Lamzone.R;
import fr.julien.Lamzone.model.Meeting;
import fr.julien.Lamzone.ui.fragment.DetailsMeetingFragment;
import fr.julien.Lamzone.ui.fragment.FragmentMeeting;
import fr.julien.Lamzone.ui.recyclerViewAdapter.RoomPopUpRecyclerViewAdapter;

public class ListMeetingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        RoomPopUpRecyclerViewAdapter.OnRoomItemClickListener, FragmentMeeting.OnMeetingClickedListener {

    @BindView(R.id.meetingActivity_drawer) DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_nav_view) NavigationView navigationView;

    private Dialog dialog;
    private TimePickerDialog pickerTime;
    private DatePickerDialog pickerDate;
    private ImageView roomCloseDialog;
    private String contentOfSearch = "";
    public static final String DEFAULT_SEARCH = "DEFAULT_SEARCH";
    public static final String ROOM_SEARCH = "ROOM_SEARCH";
    public static final String TIME_SEARCH = "TIME_SEARCH";
    public static final String DATE_SEARCH = "DATE_SEARCH";
    private FragmentMeeting meetingFragment;
    private RecyclerView recyclerView;
    private DetailsMeetingFragment detailsMeetingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        ButterKnife.bind(this);
        this.showMeetingFragment();
        this.configureNavigationView();
        this.showDetailMeetingFragment();
        dialog = new Dialog(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_activity_main_search :
                if (!drawerLayout.isDrawerOpen(Gravity.RIGHT))
                    drawerLayout.openDrawer(Gravity.RIGHT);
                else drawerLayout.closeDrawer(Gravity.RIGHT);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            this.drawerLayout.closeDrawer(GravityCompat.END);
        }else if(!contentOfSearch.isEmpty()){
            contentOfSearch = "";
            meetingFragment.initList(DEFAULT_SEARCH, contentOfSearch);
        }else {super.onBackPressed();}
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.Search_menu_byRoom :
                showMenuRoom();
                break;
            case R.id.Search_menu_byTime :
                showMenuTime();
                break;
            case R.id.Search_menu_byDate :
                showMenuDate();
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(Gravity.RIGHT);
        return true;
    }

    @OnClick(R.id.add_meeting)
    void addMeeting() { AddMeetingActivity.navigate(this);}

    public void showMenuRoom() {
        dialog.setContentView(R.layout.search_by_room_popup);
        roomCloseDialog = (ImageView) dialog.findViewById(R.id.roomCloseDialog);
        recyclerView = (RecyclerView) dialog.findViewById(R.id.list_room);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(new RoomPopUpRecyclerViewAdapter(this));
        roomCloseDialog.setOnClickListener(view -> dialog.dismiss());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private void showMenuDate(){
        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        pickerDate = new DatePickerDialog(ListMeetingActivity.this,R.style.myTimePickerStyle,
                (view, year1, monthOfYear, dayOfMonth) ->{
                    contentOfSearch = getString(R.string.date_format,Meeting.pad(dayOfMonth),Meeting.pad((monthOfYear + 1)), Meeting.pad(year1));
                    meetingFragment.initList(DATE_SEARCH, contentOfSearch);
                    this.dialog.dismiss();
                }, year, month, day);
        pickerDate.show();

    }

    private void showMenuTime( ){
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        pickerTime = new TimePickerDialog(ListMeetingActivity.this,R.style.myTimePickerStyle,
                (tp, sHour, sMinute) -> {
                    contentOfSearch = getString(R.string.add_h_for_time,Meeting.pad(sHour),Meeting.pad(sMinute));
                    meetingFragment.initList(TIME_SEARCH, contentOfSearch);
                    this.dialog.dismiss();
                }, hour, minutes, true);
        pickerTime.show();
    }

    private void showMeetingFragment(){
        meetingFragment = (FragmentMeeting) getSupportFragmentManager().findFragmentById(R.id.activity_main_frame_layout);
        if (meetingFragment == null) {
            this.meetingFragment = FragmentMeeting.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, this.meetingFragment).commit();
        }
    }

    private void showDetailMeetingFragment(){
        detailsMeetingFragment = (DetailsMeetingFragment) getSupportFragmentManager().findFragmentById(R.id.frame_layout_detail);
        if (detailsMeetingFragment == null && findViewById(R.id.frame_layout_detail) != null) {
            this.detailsMeetingFragment = DetailsMeetingFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout_detail, this.detailsMeetingFragment).commit();
        }
    }

    private void configureNavigationView(){
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClickRoomButton(int position) {
        int button_number = position+1;
        contentOfSearch = getString(R.string.room_format, button_number);
        meetingFragment.initList(ROOM_SEARCH, contentOfSearch);
        this.dialog.dismiss();
    }

    @Override
    public void onMeetingClicked(int position) {
        Meeting meetingParcelable = meetingFragment.getMeetings().get(position);
        if (this.findViewById(R.id.frame_layout_detail) == null){
            Intent intent = new Intent(this, DetailsMeetingActivity.class);
            intent.putExtra(DetailsMeetingFragment.KEY_MEETING, meetingParcelable);
            startActivity(intent);
        }else{detailsMeetingFragment.updateDetailMeeting(meetingParcelable);}
    }
}
