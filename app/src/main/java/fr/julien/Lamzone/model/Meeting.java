package fr.julien.Lamzone.model;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

import fr.julien.Lamzone.R;

/**
 * Model object representing a Meeting
 */
public class Meeting implements Parcelable {

    // Time
    private String timeStart;
    private String timeEnd;
    private int hourStart;
    private int minuteStart;
    private int hourEnd;
    private int minuteEnd;
    // Place of the meeting
    private String place;
    // Subject of the meeting
    private String subject;
    // The list of participants
    private List<String> participants;

    // Constructor
    public Meeting(int hourStart,int minuteStart, String place, String subject, List<String> participants) {
        this.hourStart = hourStart;
        this.minuteStart = minuteStart;
        this.place = place;
        this.subject = subject;
        this.participants = participants;
    }

    /** Constructor for Parcebal Neighbour */
    protected Meeting(Parcel in) {
        this.hourStart = in.readInt();
        this.minuteStart = in.readInt();
        this.place = in.readString();
        this.subject = in.readString();
        this.participants = in.readArrayList(null);
    }

    /** For Parcebal Neighbour */
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
    /** ********************** */

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
        minuteEnd = getMinuteStart() + 45 ;
        hourEnd = getHourStart();

        if (minuteEnd >= 60) {
            hourEnd ++ ;
            minuteEnd = minuteEnd -60 ;
        }
        return hourEnd;
    }

    public void setHourEnd(int hourEnd) {
        this.hourEnd = hourEnd;
    }

    public int getMinuteEnd() {
        minuteEnd = getMinuteStart() + 45 ;
        hourEnd = getHourStart();

        if (minuteEnd >= 60) {
            minuteEnd = minuteEnd -60 ;
        }
        return minuteEnd;
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

        minuteEnd = getMinuteStart() + 45 ;
        hourEnd = getHourStart();

        if (minuteEnd >= 60) {
            hourEnd ++ ;
            minuteEnd = minuteEnd -60 ;
        }

        timeEnd = pad(hourEnd) + "h" + pad(minuteEnd);
        return timeEnd;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
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

    @Override
    public String toString() {
        return "Meeting{" +
                "time = " + getTimeStart() + " à " + getTimeEnd() +
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
        parcel.writeString(place);
        parcel.writeString(subject);
        parcel.writeList(participants);

    }
}
