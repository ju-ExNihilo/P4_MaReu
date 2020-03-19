package fr.julien.Lamzone.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

    @BindView(R.id.detail_subject)
    TextView subject;
    @BindView(R.id.detail_room)
    TextView room;
    @BindView(R.id.detail_time)
    TextView time;

    public static final String KEY_MEETING = "KEY_MEETING";
    private Meeting meeting;
    private RecyclerView recyclerView;

    public DetailsMeetingFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_details_meeting, container, false);
        Context context = result.getContext();
        recyclerView = (RecyclerView) result.findViewById(R.id.list_participant);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ButterKnife.bind(this, result);
        Intent intent = this.getActivity().getIntent();
        if (intent != null){
            if (intent.hasExtra(KEY_MEETING)){
                meeting = intent.getParcelableExtra(KEY_MEETING);
                subject.setText(meeting.getSubject());
                room.setText(meeting.getPlace());
                time.setText(meeting.getTimeStart() + "   to   " + meeting.getTimeEnd());
            }
        }
        return (result);
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    public void initList(){
        recyclerView.setAdapter(new ParticipantsRecyclerViewAdapter(meeting.getParticipants()));
    }
}
