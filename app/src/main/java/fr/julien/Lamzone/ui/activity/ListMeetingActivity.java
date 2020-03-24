package fr.julien.Lamzone.ui.activity;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
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
import fr.julien.Lamzone.ui.fragment.FragmentMeeting;
import fr.julien.Lamzone.ui.recyclerViewAdapter.RoomPopUpRecyclerViewAdapter;

public class ListMeetingActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        RoomPopUpRecyclerViewAdapter.OnRoomItemClickListener {

    @BindView(R.id.meetingActivity_drawer) DrawerLayout drawerLayout;
    @BindView(R.id.activity_main_nav_view) NavigationView navigationView;
    @BindView(R.id.layout_for_search) ConstraintLayout layout_for_search;

    private Dialog roomDialog;
    private TimePickerDialog picker;
    private ImageView roomCloseDialog;
    private String S_search = "";
    private String search = "default";
    private FragmentMeeting meetingFragment;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        ButterKnife.bind(this);
        this.showMeetingFragment();
        this.configureNavigationView();
        roomDialog = new Dialog(this);
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.END)) {
            this.drawerLayout.closeDrawer(GravityCompat.END);
        }else if (!search.contentEquals("default")){
            defaultSearch();
        }else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();
        switch (id){
            case R.id.Seach_menu_byRoom :
                showMenuRoom();
                break;
            case R.id.Seach_menu_byTime :
                showMenuTime();
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(Gravity.RIGHT);
        return true;
    }

    @OnClick(R.id.item_stop_search_button)
    void stopSearch() {defaultSearch();}

    @OnClick(R.id.add_meeting)
    void addMeeting() { AddMeetingActivity.navigate(this);}

    private void defaultSearch() {
        search = "default";
        S_search = "";
        meetingFragment.initList(search, S_search);
        layout_for_search.setVisibility(View.INVISIBLE);
    }

    public void showMenuRoom() {
        roomDialog.setContentView(R.layout.search_by_room_popup);
        roomCloseDialog = (ImageView) roomDialog.findViewById(R.id.roomCloseDialog);
        recyclerView = (RecyclerView) roomDialog.findViewById(R.id.list_room);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));
        recyclerView.setAdapter(new RoomPopUpRecyclerViewAdapter(this));
        roomCloseDialog.setOnClickListener(view -> roomDialog.dismiss());
        roomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        roomDialog.show();
    }

    private void showMenuTime( ){
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        picker = new TimePickerDialog(ListMeetingActivity.this,R.style.myTimePickerStyle,
                (tp, sHour, sMinute) -> {
                S_search = Meeting.pad(sHour) + "h" + Meeting.pad(sMinute);
                search = "time";
                if (!search.contentEquals("default"))layout_for_search.setVisibility(View.VISIBLE);
                meetingFragment.initList(search, S_search);
                this.roomDialog.dismiss();

                }, hour, minutes, true);
        picker.show();
    }

    private void showMeetingFragment(){
        this.meetingFragment = FragmentMeeting.newInstance();
        this.startTransactionFragment(this.meetingFragment);
    }

    private void startTransactionFragment(Fragment fragment) {
        if (!fragment.isVisible()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.activity_main_frame_layout, fragment).commit();
        }
    }

    private void configureNavigationView(){
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClickRoomButton(int position) {
        search = "room";
        int button_number = position+1;
        S_search = "Room " + button_number;
        if (!search.contentEquals("default"))layout_for_search.setVisibility(View.VISIBLE);
        meetingFragment.initList(search, S_search);
        this.roomDialog.dismiss();
    }
}
