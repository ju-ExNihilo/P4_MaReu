package fr.julien.Lamzone.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.julien.Lamzone.R;
import fr.julien.Lamzone.model.Meeting;
import fr.julien.Lamzone.ui.recyclerViewAdapter.ParticipantsRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsMeetingFragment extends Fragment {

    @BindView(R.id.detail_subject) TextView subject;
    @BindView(R.id.detail_room) TextView room;
    @BindView(R.id.detail_time) TextView time;
    @BindView(R.id.detail_date) TextView date;
    @BindView(R.id.title_details) TextView titleDetails;
    @BindView(R.id.for_see_details_layout) ConstraintLayout forSeeDetailsLayout;
    @BindView(R.id.list_participants_card) CardView listParticipantsCard;
    @BindView(R.id.detail_card) CardView detailCard;
    @BindView(R.id.list_participant) RecyclerView listParticipantsRecyclerView;

    public static final String KEY_MEETING = "KEY_MEETING";
    private Meeting meeting;

    /**
     * Create and return a new instance
     * @return @{@link DetailsMeetingFragment}
     */
    public static DetailsMeetingFragment newInstance() {
        return new DetailsMeetingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_details_meeting, container, false);
        ButterKnife.bind(this, result);
        Context context = result.getContext();
        listParticipantsRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        Intent intent = this.getActivity().getIntent();
        if (intent != null){
            if (intent.hasExtra(KEY_MEETING)){
                updateVisibilityDetails(true);
                meeting = intent.getParcelableExtra(KEY_MEETING);
                subject.setText(meeting.getSubject());
                room.setText(meeting.getPlace());
                date.setText(meeting.getDate());
                time.setText(getString(R.string.to,meeting.getTimeStart(),meeting.getTimeEnd()));
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

    private void initList(){
        listParticipantsRecyclerView.setAdapter(new ParticipantsRecyclerViewAdapter(meeting.getParticipants()));
    }
}
