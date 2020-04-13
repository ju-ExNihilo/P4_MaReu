package fr.julien.Lamzone.ui.recyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import fr.julien.Lamzone.R;
import fr.julien.Lamzone.model.Meeting;
import fr.julien.Lamzone.ui.activity.ListMeetingActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMeetingRecyclerViewAdapter extends RecyclerView.Adapter<MyMeetingRecyclerViewAdapter.ViewHolder> {

    public interface OnMeetingItemClickListener {
        void onClickDeleteButton(int position);
        void onClickMeetingItem(int position);
    }

    private final OnMeetingItemClickListener callback;
    private WeakReference<OnMeetingItemClickListener> callbackWeakRef;
    private List<Meeting> meetings;
    private Context context;

    public MyMeetingRecyclerViewAdapter(List<Meeting> meetings, OnMeetingItemClickListener callback, Context context) {
        this.meetings = meetings;
        this.callback = callback;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.callbackWeakRef = new WeakReference<OnMeetingItemClickListener>(callback);
        Meeting meeting = meetings.get(position);
        String participants = meeting.getParticipants().toString().replaceAll("\\[|\\]" , "");
        holder.mMeetingSubject.setText(meeting.getSubject());
        holder.mMeetingAvatar.setImageResource(meeting.getColor());
        holder.mMeetingTime.setText(context.getString(R.string.for_list_items,meeting.getTimeStart()));
        holder.mMeetingRoom.setText(context.getString(R.string.for_list_items,meeting.getPlace()));
        holder.mMeetingParticipants.setText(participants);

        /** Listener with callback **/
        holder.mDeleteButton.setOnClickListener(v -> {
            OnMeetingItemClickListener callBack = callbackWeakRef.get();
            if (callBack != null) callBack.onClickDeleteButton(position);
        });

        holder.itemView.setOnClickListener(view -> {
            OnMeetingItemClickListener callBack = callbackWeakRef.get();
            if (callBack != null) callBack.onClickMeetingItem(position);
        });
    }

    @Override
    public int getItemCount() {
        return meetings.size();
    }

     static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar) CircleImageView mMeetingAvatar;
        @BindView(R.id.item_list_subject) TextView mMeetingSubject;
        @BindView(R.id.item_list_time) TextView mMeetingTime;
        @BindView(R.id.item_list_room) TextView mMeetingRoom;
        @BindView(R.id.item_list_participants) TextView mMeetingParticipants;
        @BindView(R.id.item_list_delete_button) ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}