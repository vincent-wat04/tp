package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.NextMeeting;

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
    void formatNextMeetingLabel_emptyMeeting_returnsFormattedString() {
        NextMeeting meeting = new NextMeeting("Project sync on Monday");
        String result = PersonCard.formatNextMeetingLabel(meeting);
        assertEquals("Next meeting: Project sync on Monday", result);
    }
}
