package fr.julien.Lamzone.service;

import java.util.ArrayList;
import java.util.List;
import fr.julien.Lamzone.model.Meeting;

public class DummyMeetingApiService implements MeetingApiService {

    private List<Meeting> meetings = DummyMeetingGenerator.generateMeeting();
    private List<Meeting> meetingsForSearch = new ArrayList<>();

    @Override
    public List<Meeting> getMeetings() {
        return meetings;
    }

    @Override
    public void deleteMeeting(Meeting meeting) { meetings.remove(meeting); }

    @Override
    public void createMeeting(Meeting meeting) {
        meetings.add(meeting);
    }

    @Override
    public List<Meeting> searchByRoom(String room) {
        meetingsForSearch.clear();
        for (Meeting meeting: meetings) {
            if (meeting.getPlace().equalsIgnoreCase(room))
                meetingsForSearch.add(meeting);
        }
        return meetingsForSearch;
    }

    @Override
    public  List<Meeting> searchByTime(String time) {
        meetingsForSearch.clear();
        for (Meeting meeting: meetings) {
            if (meeting.getTimeStart().contentEquals(time))
                meetingsForSearch.add(meeting);
        }
        return meetingsForSearch;
    }

    @Override
    public  List<Meeting> searchByDate(String date) {
        meetingsForSearch.clear();
        for (Meeting meeting: meetings) {
            if (meeting.getDate().contentEquals(date))
                meetingsForSearch.add(meeting);
        }
        return meetingsForSearch;
    }
}
