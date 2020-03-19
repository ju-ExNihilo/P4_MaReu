package fr.julien.Lamzone.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.julien.Lamzone.model.Meeting;

public abstract class DummyMeetingGenerator {

    public static List<Meeting> DUMMY_MEETING = Arrays.asList(
            new Meeting(10,0, "Room 4", "Sujet 1", Arrays.asList("toto@gmail.fr", "tata@gmail.fr")),
            new Meeting(14,0,"Room 5", "Sujet 2", Arrays.asList("riri@gmail.fr", "fifi@gmail.fr","loulou@gmail.fr")),
            new Meeting(8,30, "Room 2", "Sujet 3", Arrays.asList("toto@gmail.fr", "tata@gmail.fr", "fifi@gmail.fr"))
    );

    static List<Meeting> generateMeeting(){
        return new ArrayList<>(DUMMY_MEETING);
    }
}