package fr.julien.Lamzone;

import fr.julien.Lamzone.di.DI;
import fr.julien.Lamzone.model.Meeting;
import fr.julien.Lamzone.service.DummyMeetingGenerator;
import fr.julien.Lamzone.service.MeetingApiService;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit test on Meeting service
**/
@RunWith(JUnit4.class)
public class MeetingServiceTest {

    private MeetingApiService service;
    private final String SEARCH_BY_ROOM = "Room 4";
    private final String SEARCH_BY_TIME = "10h00";
    private final String SEARCH_BY_DATE = "06/04/2020";
    private final int SEARCH_RESULT = 1;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    /**
     * We ensure we can get Meeting list
     */
    @Test
    public void getMeetingsWithSuccess() {
        List<Meeting> meetings = service.getMeetings();
        List<Meeting> expectedMeetings = DummyMeetingGenerator.DUMMY_MEETING;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedMeetings.toArray()));
    }

    /**
     * We ensure we can delete a Meeting
     */
    @Test
    public void deleteMeetingWithSuccess() {
        Meeting meetingToDelete = service.getMeetings().get(0);
        service.deleteMeeting(meetingToDelete);
        assertFalse(service.getMeetings().contains(meetingToDelete));
    }

    /**
     * We ensure we can create a Meeting
     */
    @Test
    public void createMeetingWithSuccess() {
        Meeting meeting = new Meeting(10,30,"06/04/2020", "Room 7", "Sujet 9", Arrays.asList("toto@gmail.fr", "tata@gmail.fr"));
        service.createMeeting(meeting);
        assertTrue(service.getMeetings().contains(meeting));
    }

    /**
     * We ensure we can search a Meeting by Room
     */
    @Test
    public void searchMeetingByRoomWithSuccess() {
        assertEquals(service.searchByRoom(SEARCH_BY_ROOM).size() , SEARCH_RESULT);
    }

    /**
     * We ensure we can search a Meeting by Time
     */
    @Test
    public void searchMeetingByTimeWithSuccess() {
        assertEquals(service.searchByTime(SEARCH_BY_TIME).size() , SEARCH_RESULT);
    }

    /**
     * We ensure we can search a Meeting by Date
     */
    @Test
    public void searchMeetingByDateWithSuccess() {
        assertEquals(service.searchByDate(SEARCH_BY_DATE).size() , SEARCH_RESULT);
    }

}