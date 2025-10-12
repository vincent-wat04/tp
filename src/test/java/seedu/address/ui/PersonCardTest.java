package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.NextMeeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

class PersonCardTest {

    @Test
    void formatNextMeetingLabel_validMeeting_returnsFormattedString() {
        NextMeeting meeting = new NextMeeting("Demo day on Friday");
        String result = PersonCard.formatNextMeetingLabel(meeting);
        assertEquals("Next meeting: Demo day on Friday", result);
    }

    @Test
    void formatNextMeetingLabel_defaultMeeting_returnsFormattedString() {
        NextMeeting meeting = new NextMeeting(NextMeeting.DEFAULT_VALUE);
        String result = PersonCard.formatNextMeetingLabel(meeting);
        assertEquals("Next meeting: No meeting scheduled", result);
    }

    @Test
    void formatNextMeetingLabel_variousMeetings_returnsFormattedString() {
        // Test various meeting scenarios
        assertEquals("Next meeting: Project sync on Monday",
                PersonCard.formatNextMeetingLabel(new NextMeeting("Project sync on Monday")));
        assertEquals("Next meeting: Client call at 3pm",
                PersonCard.formatNextMeetingLabel(new NextMeeting("Client call at 3pm")));
        assertEquals("Next meeting: Team standup tomorrow",
                PersonCard.formatNextMeetingLabel(new NextMeeting("Team standup tomorrow")));
    }

    @Test
    void getNextMeetingText_validPerson_returnsFormattedString() {
        Person person = new PersonBuilder().withNextMeeting("Demo day on Friday").build();
        String result = PersonCard.getNextMeetingText(person);
        assertEquals("Next meeting: Demo day on Friday", result);
    }

    @Test
    void getNextMeetingText_personWithDefaultMeeting_returnsFormattedString() {
        Person person = new PersonBuilder().withNextMeeting(NextMeeting.DEFAULT_VALUE).build();
        String result = PersonCard.getNextMeetingText(person);
        assertEquals("Next meeting: No meeting scheduled", result);
    }

    @Test
    void getNextMeetingText_variousPersons_returnsCorrectText() {
        Person person1 = new PersonBuilder().withNextMeeting("Client call at 3pm").build();
        assertEquals("Next meeting: Client call at 3pm", PersonCard.getNextMeetingText(person1));

        Person person2 = new PersonBuilder().withNextMeeting("Team standup tomorrow").build();
        assertEquals("Next meeting: Team standup tomorrow", PersonCard.getNextMeetingText(person2));
    }
}
