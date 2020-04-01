package fr.julien.Lamzone.di;

import fr.julien.Lamzone.service.DummyMeetingApiService;
import fr.julien.Lamzone.service.MeetingApiService;

public class DI {

    private static MeetingApiService service = new DummyMeetingApiService();

    public static MeetingApiService getMeetingApiService(){
        return service;
    }

    public static void resetApiService(){service =  new DummyMeetingApiService();}

    public static MeetingApiService getNewInstanceApiService(){return new DummyMeetingApiService();}
}
