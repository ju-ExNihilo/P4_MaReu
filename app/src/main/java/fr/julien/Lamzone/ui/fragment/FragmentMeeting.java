package fr.julien.Lamzone.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import fr.julien.Lamzone.R;
import fr.julien.Lamzone.di.DI;
import fr.julien.Lamzone.model.Meeting;
import fr.julien.Lamzone.service.MeetingApiService;
import fr.julien.Lamzone.ui.activity.DetailsMeetingActivity;
import fr.julien.Lamzone.ui.recyclerViewAdapter.MyMeetingRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMeeting extends Fragment implements MyMeetingRecyclerViewAdapter.OnMeetingItemClickListener {

    private RecyclerView recyclerView;
    private MeetingApiService meetingApiService;
    private List<Meeting> meetings;
    private String search = "default";
    private String S_search = "";
    private LinearLayout emptyList;
    private TextView empty_text;
    private String emptyText;

    /**
     * Create and return a new instance
     * @return @{@link FragmentMeeting}
     */
    public static FragmentMeeting newInstance() {
        FragmentMeeting fragment = new FragmentMeeting();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meetingApiService = DI.getMeetingApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);
        Context context = view.getContext();
        recyclerView = (RecyclerView) view;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        emptyList = (LinearLayout) getActivity().findViewById(R.id.empty_list);
        empty_text = (TextView) getActivity().findViewById(R.id.empty_text);
        return view;
    }

    public void initList(String search, String S_search){
        this.search = search;
        this.S_search = S_search;

        if (search.contentEquals("default")){
            meetings = meetingApiService.getMeeting();
            emptyText = getString(R.string.add_meeting);
        }
        else if (search.contentEquals("room")){
            meetings = meetingApiService.searchByRoom(S_search);
            emptyText = getString(R.string.no_meeting_room);
        }
        else if (search.contentEquals("time")){
            meetings = meetingApiService.searchByTime(S_search);
            emptyText = getString(R.string.no_meeting_time);
        }

        if (meetings.isEmpty()){
            empty_text.setText(emptyText);
            recyclerView.setVisibility(View.INVISIBLE);
            emptyList.setVisibility(View.VISIBLE);
        }else{
            emptyList.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(meetings,this));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        initList(search, S_search);
    }

    @Override
    public void onClickDeleteButton(int position) {
        meetingApiService.deleteMeeting(meetings.get(position));
        initList(search, S_search);
    }

    @Override
    public void onClickMeetingItem(int position) {
        Meeting meetingParcelabe = meetings.get(position);
        Intent intent = new Intent(this.getActivity(), DetailsMeetingActivity.class);
        intent.putExtra(DetailsMeetingFragment.KEY_MEETING, meetingParcelabe);
        startActivity(intent);
    }
}
