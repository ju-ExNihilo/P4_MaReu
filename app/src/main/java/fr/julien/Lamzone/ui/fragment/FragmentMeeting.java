package fr.julien.Lamzone.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.julien.Lamzone.R;
import fr.julien.Lamzone.di.DI;
import fr.julien.Lamzone.model.Meeting;
import fr.julien.Lamzone.service.MeetingApiService;
import fr.julien.Lamzone.ui.activity.ListMeetingActivity;
import fr.julien.Lamzone.ui.recyclerViewAdapter.MyMeetingRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMeeting extends Fragment implements MyMeetingRecyclerViewAdapter.OnMeetingItemClickListener {

    private MeetingApiService meetingApiService;
    private List<Meeting> meetings;
<<<<<<< HEAD
    private String search;
    private String contentOfSearch;
    private String emptyText;
    private OnButtonClickedListener mCallback;

    @BindView(R.id.list_meeting) RecyclerView recyclerView;
    @BindView(R.id.empty_text) TextView emptyTextLayout;
    @BindView(R.id.empty_list) LinearLayout emptyList;
    @BindView(R.id.layout_for_search) ConstraintLayout layoutForSearch;

    public interface OnButtonClickedListener {
        public void onButtonClicked(int position);
    }
=======
    private String search = "default";
    private String S_search = "";
    private LinearLayout emptyList;
    private TextView empty_text;
    private String emptyText;
>>>>>>> a72fb5ab5cef0cced29251ab92440d56e2d62b77

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
        DI.resetApiService();
        meetingApiService = DI.getMeetingApiService();
<<<<<<< HEAD
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.createCallbackToParentActivity();
    }

    private void createCallbackToParentActivity(){
        try {
            mCallback = (OnButtonClickedListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(e.toString());
        }
=======
>>>>>>> a72fb5ab5cef0cced29251ab92440d56e2d62b77
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meeting_list, container, false);
        Context context = view.getContext();
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        emptyList = (LinearLayout) getActivity().findViewById(R.id.empty_list);
        empty_text = (TextView) getActivity().findViewById(R.id.empty_text);
        return view;
    }

    public void initList(String search, String contentOfSearch){
        this.search = search;
<<<<<<< HEAD
        this.contentOfSearch = contentOfSearch;

        switch (search){
            case ListMeetingActivity.DEFAULT_SEARCH :
                layoutForSearch.setVisibility(View.GONE);
                meetings = meetingApiService.getMeeting();
                emptyText = getString(R.string.please_add_a_new_meeting);
                break;
            case ListMeetingActivity.ROOM_SEARCH :
                layoutForSearch.setVisibility(View.VISIBLE);
                meetings = meetingApiService.searchByRoom(contentOfSearch);
                emptyText = getString(R.string.no_meeting_room);
                break;
            case ListMeetingActivity.TIME_SEARCH :
                layoutForSearch.setVisibility(View.VISIBLE);
                meetings = meetingApiService.searchByTime(contentOfSearch);
                emptyText = getString(R.string.no_meeting_time);
                break;
            case ListMeetingActivity.DATE_SEARCH :
                layoutForSearch.setVisibility(View.VISIBLE);
                meetings = meetingApiService.searchByDate(contentOfSearch);
                emptyText = getString(R.string.no_meeting_date);
                break;
            default:
                break;
        }

        if (meetings.isEmpty()){
            emptyTextLayout.setText(emptyText);
=======
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
>>>>>>> a72fb5ab5cef0cced29251ab92440d56e2d62b77
            recyclerView.setVisibility(View.INVISIBLE);
            emptyList.setVisibility(View.VISIBLE);
        }else{
            emptyList.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new MyMeetingRecyclerViewAdapter(meetings,this));
        }
    }

    public void defaultSearch() {
        contentOfSearch = "";
        initList(ListMeetingActivity.DEFAULT_SEARCH, contentOfSearch);
    }

    @OnClick(R.id.item_stop_search_button)
    void stopSearch() {defaultSearch();}

    @Override
    public void onResume() {
        super.onResume();
        initList(ListMeetingActivity.DEFAULT_SEARCH, contentOfSearch);
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void onClickDeleteButton(int position) {
        meetingApiService.deleteMeeting(meetings.get(position));
        initList(search, contentOfSearch);
    }

    @Override
    public void onClickMeetingItem(int position) {
        mCallback.onButtonClicked(position);
    }
}
