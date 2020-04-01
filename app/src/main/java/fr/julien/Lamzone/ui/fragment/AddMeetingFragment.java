package fr.julien.Lamzone.ui.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.google.android.material.textfield.TextInputEditText;
import fr.julien.Lamzone.R;
import fr.julien.Lamzone.di.DI;
import fr.julien.Lamzone.model.Meeting;
import fr.julien.Lamzone.service.MeetingApiService;
import fr.julien.Lamzone.ui.recyclerViewAdapter.RoomPopUpRecyclerViewAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddMeetingFragment extends Fragment implements RoomPopUpRecyclerViewAdapter.OnRoomItemClickListener {

    @BindView(R.id.subjectLyt) TextInputEditText subjectLyt;
    @BindView(R.id.timeLyt) TextInputEditText timeLyt;
    @BindView(R.id.participantsLyt) TextInputEditText participantsLyt;
    @BindView(R.id.roomLyt) TextInputEditText roomLyt;
    @BindView(R.id.dateLyt) TextInputEditText dateLyt;

    private MeetingApiService meetingApiService;
    private TimePickerDialog pickerTime;
    private DatePickerDialog pickerDate;
    private int hourStart;
    private int minuteStart;
    private String dateString;
    private Dialog roomDialog;
    private ImageView roomCloseDialog;
    private TextView titlePopUp;
    private RecyclerView recyclerView;

    /**
     * Create and return a new instance
     * @return @{@link FragmentMeeting}
     */
    public static AddMeetingFragment newInstance() {
        AddMeetingFragment fragment = new AddMeetingFragment();
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
        View result = inflater.inflate(R.layout.fragment_add_meeting, container, false);
        ButterKnife.bind(this, result);
        this.roomDialog = new Dialog(this.getActivity());
        this.forTimePicker(this.timeLyt);
        this.forAddParticipants(this.participantsLyt);
        this.forRoomPicker(this.roomLyt);
        this.forDatePicker(this.dateLyt);
        this.saveAction(result);
        return (result);
    }

    private void saveAction(View result){
        if ( result.findViewById(R.id.create) != null){
            Button addButton = result.findViewById(R.id.create);
            addButton.setOnClickListener(v -> addMeeting());
        }
    }

    private void forTimePicker(TextInputEditText time){
        time.setInputType(InputType.TYPE_NULL);
        time.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minutes = calendar.get(Calendar.MINUTE);
            pickerTime = new TimePickerDialog(this.getActivity(),R.style.myTimePickerStyle,
                    (tp, sHour, sMinute) -> {
                        time.setText(getString(R.string.add_h_for_time,Meeting.pad(sHour),Meeting.pad(sMinute)));
                        hourStart = sHour;
                        minuteStart = sMinute;
                    }, hour, minutes, true);
            pickerTime.show();
        });
    }

    private void forDatePicker(TextInputEditText date){
        date.setInputType(InputType.TYPE_NULL);
        date.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);
            pickerDate = new DatePickerDialog(this.getActivity(),R.style.myTimePickerStyle,
                    (view, year1, monthOfYear, dayOfMonth) -> {
                        dateString = getString(R.string.date_format, Meeting.pad(dayOfMonth), Meeting.pad((monthOfYear + 1)), Meeting.pad(year1));
                        date.setText(dateString);
                    }, year, month, day);
            pickerDate.show();
        });
    }

    private void forRoomPicker(TextInputEditText room){
        room.setInputType(InputType.TYPE_NULL);
        room.setOnClickListener(v -> {
            if (timeLyt.getText().toString().isEmpty()){
                warningDialog(getString(R.string.time_first));
            }else if (dateLyt.getText().toString().isEmpty()){
                warningDialog(getString(R.string.date_first));
            }else{
                roomDialog.setContentView(R.layout.search_by_room_popup);
                roomCloseDialog = (ImageView) roomDialog.findViewById(R.id.roomCloseDialog);
                titlePopUp = (TextView) roomDialog.findViewById(R.id.title_popup);
                recyclerView = (RecyclerView) roomDialog.findViewById(R.id.list_room);
                recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
                recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.HORIZONTAL));
                recyclerView.setAdapter(new RoomPopUpRecyclerViewAdapter(this, hourStart, minuteStart, dateString));
                titlePopUp.setText(R.string.free_room);
                roomCloseDialog.setOnClickListener(view -> roomDialog.dismiss());
                roomDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                roomDialog.show();
            }
        });
    }

    private void warningDialog(String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext(),R.style.myDialogStyle);
        alertDialogBuilder.setTitle(R.string.warning)
                          .setIcon(R.mipmap.ic_launcher)
                          .setMessage(message)
                          .setCancelable(true)
                          .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {dialogInterface.cancel();});
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @SuppressLint("ResourceAsColor")
    private void forAddParticipants(TextInputEditText participants){
        participants.setInputType(InputType.TYPE_NULL);
        participants.setOnClickListener(v -> {
            final TextInputEditText txtUrl = new TextInputEditText(this.getActivity());
            txtUrl.setHint(R.string.require_mail);
            txtUrl.setHintTextColor(R.color.colorBlue);
            txtUrl.setTextColor(R.color.colorBlueDark);
            new AlertDialog.Builder(this.getActivity(),R.style.myDialogStyle)
                    .setTitle(R.string.for_add_participant)
                    .setIcon(R.mipmap.ic_launcher)
                    .setView(txtUrl)
                    .setPositiveButton(R.string.ok, (dialog, whichButton) -> {
                        if (isEmailValid(txtUrl.getText().toString())){
                            String ifMail = participants.getText().toString();
                            if (ifMail.isEmpty())
                                participants.setText(txtUrl.getText().toString());
                            else participants.setText(getString(R.string.participant_format,ifMail,txtUrl.getText().toString()));
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
        if (!timeLyt.getText().toString().isEmpty() &&
            !subjectLyt.getText().toString().isEmpty() &&
            !roomLyt.getText().toString().isEmpty() &&
            !participantsLyt.getText().toString().isEmpty())
            enable = true;

        return enable;
    }

    public void addMeeting() {
        if (enableSave()){
            String participantString = participantsLyt.getText().toString();
            List<String> participantList = Arrays.asList(participantString.split("\\s*,\\s*"));
            Meeting meeting = new Meeting(
                    hourStart,
                    minuteStart,
                    dateLyt.getText().toString(),
                    roomLyt.getText().toString(),
                    subjectLyt.getText().toString(),
                    participantList
            );
            meetingApiService.createMeeting(meeting);
            getActivity().finish();
        }else warningDialog(getString(R.string.require_all_files));

    }

    @Override
    public void onClickRoomButton(int position) {
        int button_number = position+1;
        String forSearchRoom = getString(R.string.room_format, button_number);
        roomLyt.setText(forSearchRoom);
        this.roomDialog.dismiss();
    }

    public void onButtonSaveClicked() {addMeeting();}
}
