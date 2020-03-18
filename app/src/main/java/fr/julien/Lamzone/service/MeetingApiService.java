package fr.julien.Lamzone.service;

import java.util.List;

import fr.julien.Lamzone.model.Meeting;

/**
 * Meeting API client
 */
public interface MeetingApiService {

    /**
     * Get all my Neighbours
     * @return {@link List}
     */
    List<Meeting> getMeeting();

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
     * Create a Meeting
     * @param room
     */
    List<Meeting> searchByRoom(String room);

    /**
     * Create a Meeting
     * @param time
     */
    List<Meeting> searchByTime(String time);
}

