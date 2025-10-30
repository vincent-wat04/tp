package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.NextMeeting;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class PersonCardTest {

    @Test
    public void shouldShowCompany_emptyOrWhitespace_returnsFalse() {
        assertFalse(PersonCard.shouldShowCompany(""));
        assertFalse(PersonCard.shouldShowCompany("   "));
        assertFalse(PersonCard.shouldShowCompany(null));
    }

    @Test
    public void shouldShowCompany_nonEmpty_returnsTrue() {
        assertTrue(PersonCard.shouldShowCompany("Google"));
        assertTrue(PersonCard.shouldShowCompany("  Apple "));
    }

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
}
