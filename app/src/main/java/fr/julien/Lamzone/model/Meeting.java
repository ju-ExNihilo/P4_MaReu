package fr.julien.Lamzone.model;

import java.util.List;

/**
 * Model object representing a Meeting
 */
public class Meeting {

    // Time
    private String time;
    // Place of the meeting
    private String place;
    // Subject of the meeting
    private String subject;
    // The list of participants
    private List<String> participants;

    // Constructor
    public Meeting(String time, String place, String subject, List<String> participants) {
        this.time = time;
        this.place = place;
        this.subject = subject;
        this.participants = participants;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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

    @Override
    public String toString() {
        return "Meeting{" +
                "time=" + time +
                ", place='" + place + '\'' +
                ", subject='" + subject + '\'' +
                ", participants=" + participants +
                '}';
    }
}
