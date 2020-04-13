package fr.julien.Lamzone.service;

import java.util.List;
import fr.julien.Lamzone.model.Meeting;

/**
 * Meeting API client
 */
public interface MeetingApiService {

    /**
     * Get all  Meetings
     * @return {@link List}
     */
    List<Meeting> getMeetings();

    /**
     * Deletes a Meeting
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    /**
     * Create a Meeting
     * @param meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Search a Meeting by Room
     * @param room
     */
    List<Meeting> searchByRoom(String room);

    /**
     * Search a Meeting by Time
     * @param time
     */
    List<Meeting> searchByTime(String time);

    /**
     * Search a Meeting by Date
     * @param date
     */
    List<Meeting> searchByDate(String date);

}

