package fr.julien.Lamzone.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.julien.Lamzone.R;
import fr.julien.Lamzone.di.DI;
import fr.julien.Lamzone.model.Meeting;
import fr.julien.Lamzone.service.MeetingApiService;
import fr.julien.Lamzone.ui.recyclerViewAdapter.ParticipantsRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsMeetingFragment extends Fragment {

    @BindView(R.id.detail_subject) TextView subject;
    @BindView(R.id.detail_room) TextView room;
    @BindView(R.id.detail_time) TextView time;
<<<<<<< HEAD
    @BindView(R.id.detail_date) TextView date;
    @BindView(R.id.title_details) TextView titleDetails;
    @BindView(R.id.for_see_details_layout) ConstraintLayout forSeeDetailsLayout;
    @BindView(R.id.list_participants_card) CardView listParticipantsCard;
    @BindView(R.id.detail_card) CardView detailCard;
=======
>>>>>>> a72fb5ab5cef0cced29251ab92440d56e2d62b77

    public static final String KEY_MEETING = "KEY_MEETING";
    private Meeting meeting;
    private RecyclerView recyclerView;
    private MeetingApiService meetingApiService;
    private List<Meeting> meetings;

    /**
     * Create and return a new instance
     * @return @{@link DetailsMeetingFragment}
     */
    public static DetailsMeetingFragment newInstance() {
        DetailsMeetingFragment fragment = new DetailsMeetingFragment();
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
        View result = inflater.inflate(R.layout.fragment_details_meeting, container, false);
        ButterKnife.bind(this, result);
        Context context = result.getContext();
        recyclerView = (RecyclerView) result.findViewById(R.id.list_participant);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        meetings = meetingApiService.getMeeting();
        Intent intent = this.getActivity().getIntent();
        if (intent != null){
            if (intent.hasExtra(KEY_MEETING)){
                updateVisibilityDetails(true);
                meeting = intent.getParcelableExtra(KEY_MEETING);
                subject.setText(meeting.getSubject());
                room.setText(meeting.getPlace());
<<<<<<< HEAD
                date.setText(meeting.getDate());
                time.setText(getString(R.string.to,meeting.getTimeStart(),meeting.getTimeEnd()));
=======
                time.setText(meeting.getTimeStart() +"  "+ getString(R.string.to) +"  "+ meeting.getTimeEnd());
>>>>>>> a72fb5ab5cef0cced29251ab92440d56e2d62b77
            }
        }
        return (result);
    }

    public void updateDetailMeeting(Meeting meeting){
        this.meeting = meeting;
        if (meeting != null){
            updateVisibilityDetails(true);
            subject.setText(meeting.getSubject());
            room.setText(meeting.getPlace());
            date.setText(meeting.getDate());
            time.setText(getString(R.string.to,meeting.getTimeStart(),meeting.getTimeEnd()));
            initList();
        }else{
            updateVisibilityDetails(false);
        }
    }

    private void updateVisibilityDetails(boolean haveSelectedMeeting){
        if(haveSelectedMeeting){
            listParticipantsCard.setVisibility(View.VISIBLE);
            detailCard.setVisibility(View.VISIBLE);
            titleDetails.setText(getString(R.string.details_of_meeting));
        }else{
            listParticipantsCard.setVisibility(View.INVISIBLE);
            detailCard.setVisibility(View.INVISIBLE);
            titleDetails.setText(getString(R.string.for_see_details));
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (meeting != null) initList();
    }

    public void initList(){
        recyclerView.setAdapter(new ParticipantsRecyclerViewAdapter(meeting.getParticipants()));
    }
}
