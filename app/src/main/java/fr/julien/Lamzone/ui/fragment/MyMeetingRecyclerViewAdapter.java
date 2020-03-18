package fr.julien.Lamzone.ui.fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import java.lang.ref.WeakReference;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.julien.Lamzone.R;
import fr.julien.Lamzone.model.Meeting;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    public interface OnItemDeleteListener {void onClickDeleteButton(int position);}

    private final OnItemDeleteListener callback;
    private WeakReference<OnItemDeleteListener> callbackWeakRef;
    private final List<Meeting> meetings;

    public MyMeetingRecyclerViewAdapter(List<Meeting> meetings, OnItemDeleteListener callback) {
        this.meetings = meetings;
        this.callback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.callbackWeakRef = new WeakReference<OnItemDeleteListener>(callback);
        final Meeting meeting = meetings.get(position);
        String participants = meeting.getParticipants().toString().replaceAll("\\[|\\]" , "");
        holder.mMeetingSubject.setText(meeting.getSubject());
        holder.mMeetingTime.setText(" - " +meeting.getTime());
        holder.mMeetingRoom.setText(" - " +meeting.getPlace());
        holder.mMeetingParticipants.setText(participants);
        Glide.with(holder.mMeetingAvatar.getContext())
                .load(R.drawable.ic_launcher_background)
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mMeetingAvatar);
        /** Listener for delete event "ifFav" is used to know which tab one is in **/
        holder.mDeleteButton.setOnClickListener(v -> {
            OnItemDeleteListener callBack = callbackWeakRef.get();
            if (callBack != null) callBack.onClickDeleteButton(position);
        });

        holder.itemView.setOnClickListener(view -> Log.i("DEBUG",meeting.toString()));
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mMeetingAvatar;
        @BindView(R.id.item_list_subject)
        public TextView mMeetingSubject;
        @BindView(R.id.item_list_time)
        public TextView mMeetingTime;
        @BindView(R.id.item_list_room)
        public TextView mMeetingRoom;
        @BindView(R.id.item_list_participants)
        public TextView mMeetingParticipants;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}