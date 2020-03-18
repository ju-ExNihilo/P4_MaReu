package fr.julien.Lamzone.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMeeting extends Fragment implements MyMeetingRecyclerViewAdapter.OnItemDeleteListener {

    private RecyclerView recyclerView;
    private MeetingApiService meetingApiService;
    private List<Meeting> meetings;
    private String search = "default";
    private String S_search = "";

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
        return view;
    }

    public void initList(String search, String S_search){
        this.search = search;
        this.S_search = S_search;

        if (search.contentEquals("default")){
            meetings = meetingApiService.getMeeting();
        }else if (search.contentEquals("room")){
            meetings = meetingApiService.searchByRoom(S_search);
        }else if (search.contentEquals("time")){
            meetings = meetingApiService.searchByTime(S_search);
        }
        recyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(meetings,this));
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
}
