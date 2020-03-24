package fr.julien.Lamzone.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fr.julien.Lamzone.R;
import fr.julien.Lamzone.di.DI;
import fr.julien.Lamzone.model.Meeting;
import fr.julien.Lamzone.service.MeetingApiService;
import fr.julien.Lamzone.ui.recyclerViewAdapter.RoomPopUpRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMeetingFragment extends Fragment implements RoomPopUpRecyclerViewAdapter.OnRoomItemClickListener {

    @BindView(R.id.subjectLyt) TextInputLayout subjectLyt;
    @BindView(R.id.timeLyt) TextInputLayout timeLyt;
    @BindView(R.id.time) TextInputEditText time;
    @BindView(R.id.participants) TextInputEditText participants;
    @BindView(R.id.roomLyt) TextInputLayout roomLyt;
    @BindView(R.id.room) TextInputEditText room;
    @BindView(R.id.participantsLyt) TextInputLayout participantsLyt;
    @BindView(R.id.create) Button addButton;

    private MeetingApiService meetingApiService;
    private List<String> participantList ;
    private String participantString;
    private TimePickerDialog picker;
    private int hourStart;
    private int minuteStart;
    private Dialog roomDialog;
    private ImageView roomCloseDialog;
    private TextView titlePopUp;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        meetingApiService = DI.getMeetingApiService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_add_meeting, container, false);
        ButterKnife.bind(this, result);
        this.roomDialog = new Dialog(this.getActivity());
        this.forTimePicker(this.time);
        this.forAddParticipants(this.participants);
        this.forRoomPicker(this.room);
        return (result);
    }

    private void forTimePicker(TextInputEditText time){
        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            picker = new TimePickerDialog(this.getActivity(),R.style.myTimePickerStyle,
                    (tp, sHour, sMinute) -> {
                        time.setText(new StringBuilder().append(Meeting.pad(sHour))
                                .append("h").append(Meeting.pad(sMinute)));
                        hourStart = sHour;
                        minuteStart = sMinute;
                    }, hour, minutes, true);
            picker.show();
        });
    }

    private void forRoomPicker(TextInputEditText room){
        room.setInputType(InputType.TYPE_NULL);
        room.setOnClickListener(v -> {
            if (timeLyt.getEditText().getText().toString().isEmpty()){
                warningDialog(getString(R.string.time_first));
            }else{
                roomDialog.setContentView(R.layout.search_by_room_popup);
                roomCloseDialog = (ImageView) roomDialog.findViewById(R.id.roomCloseDialog);
                titlePopUp = (TextView) roomDialog.findViewById(R.id.title_popup);
                recyclerView = (RecyclerView) roomDialog.findViewById(R.id.list_room);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
                recyclerView.setAdapter(new RoomPopUpRecyclerViewAdapter(this, hourStart, minuteStart));
                titlePopUp.setText(R.string.free_room);
                roomCloseDialog.setOnClickListener(view -> roomDialog.dismiss());
                roomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                roomDialog.show();
            }
        });
    }

    private void warningDialog(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setTitle(R.string.warning);
        alertDialogBuilder.setMessage(message)
                          .setCancelable(true)
                          .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
                              dialogInterface.cancel();
                          });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void forAddParticipants(TextInputEditText participants){
        participants.setInputType(InputType.TYPE_NULL);
        participants.setOnClickListener(v -> {
            final EditText txtUrl = new EditText(this.getActivity());
            txtUrl.setHint(R.string.edit_text_mail);
            new AlertDialog.Builder(this.getActivity())
                    .setTitle(R.string.participants)
                    .setMessage(R.string.require_mail)
                    .setIcon(R.drawable.reunion)
                    .setView(txtUrl)
                    .setPositiveButton(R.string.ok, (dialog, whichButton) -> {
                        if (isEmailValid(txtUrl.getText().toString())){
                            String ifMail = participants.getText().toString();
                            if (ifMail.isEmpty())
                                participants.setText(txtUrl.getText().toString());
                            else participants.setText(ifMail + "," +txtUrl.getText().toString());
                        }else warningDialog(getString(R.string.require_valid_mail));


                    })
                    .setNegativeButton(R.string.back, (dialog, which) -> dialog.cancel())
                    .show();
        });
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean enableSave(){
        boolean enable = false;
        if (!timeLyt.getEditText().getText().toString().isEmpty() &&
            !subjectLyt.getEditText().getText().toString().isEmpty() &&
            !roomLyt.getEditText().getText().toString().isEmpty() &&
            !participantsLyt.getEditText().getText().toString().isEmpty())
            enable = true;

        return enable;
    }

    @OnClick(R.id.create)
    void addMeeting() {
        if (enableSave()){
            participantString = participantsLyt.getEditText().getText().toString();
            participantList = Arrays.asList(participantString.split("\\s*,\\s*"));
            Meeting meeting = new Meeting(
                    hourStart,
                    minuteStart,
                    roomLyt.getEditText().getText().toString(),
                    subjectLyt.getEditText().getText().toString(),
                    participantList
            );
            meetingApiService.createMeeting(meeting);
            getActivity().finish();
        }else warningDialog(getString(R.string.require_all_files));

    }

    @Override
    public void onClickRoomButton(int position) {
        int button_number = position+1;
        room.setText(getString(R.string.room) +" "+ button_number);
        this.roomDialog.dismiss();
    }
}
