package fr.julien.Lamzone.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import fr.julien.Lamzone.R;

/** Model object representing a Meeting **/
public class Meeting implements Parcelable {

    // Time
    private String timeStart;
    private String timeEnd;
    private int hourStart;
    private int minuteStart;
    private int hourEnd;
    private int minuteEnd;
    // Date of the meeting
    private String date;
    // Place of the meeting
    private String place;
    // Subject of the meeting
    private String subject;
    // The list of participants
    private List<String> participants;
    //Constant
    public static final int DURATION_MEETING = 45;
    private static final int MINUTE_IN_ONE_HOUR = 60;

    // Constructor
    public Meeting(int hourStart,int minuteStart, String date, String place, String subject, List<String> participants) {
        this.hourStart = hourStart;
        this.minuteStart = minuteStart;
        this.date = date;
        this.place = place;
        this.subject = subject;
        this.participants = participants;
    }

    /** Constructor for Parcelable Meeting **/
    protected Meeting(Parcel in) {
        this.hourStart = in.readInt();
        this.minuteStart = in.readInt();
        this.date = in.readString();
        this.place = in.readString();
        this.subject = in.readString();
        this.participants = in.readArrayList(null);
    }

    /** For Parcelable Meeting **/
    public static final Creator<Meeting> CREATOR = new Creator<Meeting>() {
        @Override
        public Meeting createFromParcel(Parcel in) {
            return new Meeting(in);
        }

        @Override
        public Meeting[] newArray(int size) {
            return new Meeting[size];
        }
    };
    /** ********************** **/

    public int getHourStart() {
        return hourStart;
    }
    public void setHourStart(int hour) {
        this.hourStart = hour;
    }

    public int getMinuteStart() {
        return minuteStart;
    }
    public void setMinuteStart(int minute) {
        this.minuteStart = minute;
    }

    public int getHourEnd() {
        minuteEnd = getMinuteStart() + DURATION_MEETING ;
        hourEnd = getHourStart();

        if (minuteEnd >= MINUTE_IN_ONE_HOUR) {
            hourEnd ++ ;
            minuteEnd = minuteEnd - MINUTE_IN_ONE_HOUR;
        }
        return hourEnd;
    }

    public int getMinuteEnd() {
        minuteEnd = getMinuteStart() + DURATION_MEETING ;
        hourEnd = getHourStart();

        if (minuteEnd >= MINUTE_IN_ONE_HOUR) {
            minuteEnd = minuteEnd - MINUTE_IN_ONE_HOUR;
        }
        return minuteEnd;
    }

    public int getColor(){
        switch (getPlace()){
            case "Room 1":
            case "Room 6":
                return R.color.room16;
            case "Room 2":
            case "Room 8":
                return R.color.room28;
            case "Room 3":
            case "Room 7":
                return R.color.room37;
            case "Room 5":
            case "Room 10":
                return R.color.room510;
            default:
                return R.color.room49;
        }
    }

    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }
    public void setMinuteEnd(int minuteEnd) {
        this.minuteEnd = minuteEnd;
    }

    public String getTimeStart() {
        timeStart = pad(getHourStart()) + "h" + pad(getMinuteStart());
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        timeEnd = pad(getHourEnd()) + "h" + pad(getMinuteEnd());
        return timeEnd;
    }

    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public String getSubject() {
        return subject;
    }
    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getParticipants() {
        return participants;
    }
    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }


    @Override
    public String toString() {
        return "Meeting{" +
                "time = " + getTimeStart() + " à " + getTimeEnd() +
                ", date='" + date + '\'' +
                ", place='" + place + '\'' +
                ", subject='" + subject + '\'' +
                ", participants=" + participants +
                '}';
    }

    public static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + c;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(hourStart);
        parcel.writeInt(minuteStart);
        parcel.writeString(date);
        parcel.writeString(place);
        parcel.writeString(subject);
        parcel.writeList(participants);
    }
}
