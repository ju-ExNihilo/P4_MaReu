package fr.julien.Lamzone.ui.recyclerViewAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.julien.Lamzone.R;
import fr.julien.Lamzone.di.DI;
import fr.julien.Lamzone.model.Meeting;
import fr.julien.Lamzone.service.MeetingApiService;

public class RoomPopUpRecyclerViewAdapter extends RecyclerView.Adapter<RoomPopUpRecyclerViewAdapter.ViewHolder>{

    public interface OnRoomItemClickListener {
        void onClickRoomButton(int position);
    }

    private final OnRoomItemClickListener callback;
    private WeakReference<OnRoomItemClickListener> callbackWeakRef;
    private MeetingApiService meetingApiService;
    private Button room1,room2,room3,room4,room5,room6,room7,room8,room9,room10;
    private List<Button> buttons = Arrays.asList(room1,room2,room3,room4,room5,room6,room7,room8,room9,room10);
    private List<Integer> buttons_name =  Arrays.asList(R.string.room1,R.string.room2,R.string.room3,R.string.room4,R.string.room5,
            R.string.room6,R.string.room7,R.string.room8,R.string.room9,R.string.room10);
    private int hourStart;
    private int minuteStart;
    private final int DURATION_MEETING = 45;

    public RoomPopUpRecyclerViewAdapter(OnRoomItemClickListener callback) {
        meetingApiService = DI.getMeetingApiService();
        this.callback = callback;
    }

    public RoomPopUpRecyclerViewAdapter(OnRoomItemClickListener callback, int hourStart, int minuteStart) {
        meetingApiService = DI.getMeetingApiService();
        this.callback = callback;
        this.hourStart = hourStart;
        this.minuteStart = minuteStart;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.button_room_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.callbackWeakRef = new WeakReference<OnRoomItemClickListener>(callback);
        holder.button_room.setText(buttons_name.get(position));
        freeRoom(meetingApiService.getMeeting(),hourStart,minuteStart,holder.button_room);
        holder.button_room.setOnClickListener(v -> {
            OnRoomItemClickListener callBack = callbackWeakRef.get();
            if (callBack != null) callBack.onClickRoomButton(position);
        });
    }

    @SuppressLint("ResourceAsColor")
    private void freeRoom(List<Meeting> meetings, int hourStart, int minuteStart, Button button) {
        for (Meeting oldMeeting : meetings) {
            if (hourStart == oldMeeting.getHourStart() | hourStart == oldMeeting.getHourEnd()) {
                if (minuteStart < oldMeeting.getMinuteEnd()){
                    if (oldMeeting.getPlace().contentEquals(button.getText())){
                        button.setBackgroundResource(R.drawable.red_button);
                        button.setEnabled(false);
                    }
                }
            }else if (hourStart + DURATION_MEETING == oldMeeting.getHourStart()) {
                if (minuteStart + DURATION_MEETING > oldMeeting.getMinuteStart()){
                    if (oldMeeting.getPlace().contentEquals(button.getText())){
                        button.setBackgroundResource(R.drawable.red_button);
                        button.setEnabled(false);
                    }
                }
            }
        }
    }

    @Override
    public int getItemCount() { return buttons.size();}

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.button_room) Button button_room;

        public ViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }
}
