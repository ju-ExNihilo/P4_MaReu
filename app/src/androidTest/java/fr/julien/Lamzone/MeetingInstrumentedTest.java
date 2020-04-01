package fr.julien.Lamzone;

import android.widget.DatePicker;
import android.widget.TimePicker;
import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import com.google.android.material.textfield.TextInputEditText;
import fr.julien.Lamzone.model.Meeting;
import fr.julien.Lamzone.service.DummyMeetingGenerator;
import fr.julien.Lamzone.ui.activity.ListMeetingActivity;
import fr.julien.Lamzone.utils.DeleteViewAction;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsNull.notNullValue;
import static fr.julien.Lamzone.utils.RecyclerViewItemCountAssertion.withItemCount;

/**
 * Test class for list of meetings
 */
@RunWith(AndroidJUnit4.class)
public class MeetingInstrumentedTest {

    // This is fixed
    private ListMeetingActivity mActivity;
    private static int ITEMS_COUNT = 3;
    private static int ITEMS_COUNT_FOR_SEARCH = 1;
    private Meeting meeting = DummyMeetingGenerator.DUMMY_MEETING.get(0);
    private String subject = meeting.getSubject();

    @Rule
    public ActivityTestRule<ListMeetingActivity> mActivityRule =
            new ActivityTestRule(ListMeetingActivity.class);

    @Before
    public void setUp() {
        mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }

    /**
     * We ensure that our recyclerView is displaying at least on item
     */
    @Test
    public void myMeetingsList_shouldNotBeEmpty() {
        onView(ViewMatchers.withId(R.id.list_meeting))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myMeetingsList_deleteAction_shouldRemoveItem() {
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT));
        onView(ViewMatchers.withId(R.id.list_meeting))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT-1));
    }

    /**
     * When we create an item, the item is shown
     */
    @Test
    public void myMeetingsList_create_newMeeting() {
        onView(ViewMatchers.withId(R.id.add_meeting)).perform(click());
        onView(ViewMatchers.withId(R.id.add_activity));

        onView(ViewMatchers.withId(R.id.subjectLyt)).perform(typeText("Sujet 4"));

        onView(ViewMatchers.withId(R.id.dateLyt)).perform(scrollTo(),doubleClick());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020,04,06));
        onView(withId(android.R.id.button1)).perform(click());

        onView(ViewMatchers.withId(R.id.timeLyt)).perform(scrollTo(),doubleClick());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(17,00));
        onView(withId(android.R.id.button1)).perform(click());

        onView(ViewMatchers.withId(R.id.roomLyt)).perform(scrollTo(),doubleClick());
        onView(ViewMatchers.withId(R.id.list_room)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));

        onView(ViewMatchers.withId(R.id.participantsLyt)).perform(scrollTo(),doubleClick());
        onView(withClassName(Matchers.equalTo(TextInputEditText.class.getName())))
                .perform(typeText("test@lamzone.fr"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(ViewMatchers.withId(R.id.participantsLyt)).perform(click());
        onView(withClassName(Matchers.equalTo(TextInputEditText.class.getName())))
                .perform(typeText("test2@lamzone.fr"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(ViewMatchers.withId(R.id.create)).perform(click());
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT+1));
    }

    /**
     * When we click an item, detailsActivity is displaying with good item
     */
    @Test
    public void myMeetingsList_goToDetails_withGoodItem() {
        onView(ViewMatchers.withId(R.id.list_meeting)).perform(RecyclerViewActions.actionOnItemAtPosition(0,click()));
        onView(ViewMatchers.withId(R.id.details_activity));
        onView(ViewMatchers.withId(R.id.detail_subject)).check(matches(withText(containsString(subject))));
    }

    /**
     * When we search by time, recyclerView is displaying with good number item
     */
    @Test
    public void myMeetingsList_search_byTime() {
        onView(ViewMatchers.withId(R.id.menu_activity_main_search)).perform(click());
        onView(withText("Time")).perform(click());
        onView(withClassName(Matchers.equalTo(TimePicker.class.getName())))
                .perform(PickerActions.setTime(10,00));
        onView(withId(android.R.id.button1)).perform(click());
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT_FOR_SEARCH));
    }

    /**
     * When we search by date, recyclerView is displaying with good number item
     */
    @Test
    public void myMeetingsList_search_byDate() {
        onView(ViewMatchers.withId(R.id.menu_activity_main_search)).perform(click());
        onView(withText("Date")).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2020,04,06));
        onView(withId(android.R.id.button1)).perform(click());
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT_FOR_SEARCH));
    }

    /**
     * When we search by room, recyclerView is displaying with good number item
     */
    @Test
    public void myMeetingsList_search_byRoom() {
        onView(ViewMatchers.withId(R.id.menu_activity_main_search)).perform(click());
        onView(withText("Room")).perform(click());
        onView(ViewMatchers.withId(R.id.list_room)).perform(RecyclerViewActions.actionOnItemAtPosition(3,click()));
        onView(ViewMatchers.withId(R.id.list_meeting)).check(withItemCount(ITEMS_COUNT_FOR_SEARCH));
    }

}

