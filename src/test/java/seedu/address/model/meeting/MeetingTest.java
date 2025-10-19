package seedu.address.model.meeting;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Name;

/**
 * Minimal tests for {@link Meeting} to ensure constructor validation works.
 */
public class MeetingTest {

    private static final MeetingTitle TITLE = new MeetingTitle("Sprint sync");
    private static final LocalDate DATE = LocalDate.of(2024, 10, 12);
    private static final LocalTime START = LocalTime.of(14, 0);
    private static final LocalTime END = LocalTime.of(15, 0);
    private static final List<Name> PARTICIPANTS = List.of(new Name("Alex Yeoh"));

    @Test
    public void constructor_sameStartEnd_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                new Meeting(TITLE, DATE, START, START, PARTICIPANTS));
    }

    @Test
    public void equalsAndIdentity() {
        Meeting meeting = new Meeting(TITLE, DATE, START, END, PARTICIPANTS);
        Meeting same = new Meeting(TITLE, DATE, START, END, PARTICIPANTS);
        Meeting differentTime = new Meeting(TITLE, DATE, LocalTime.of(16, 0), LocalTime.of(17, 0), PARTICIPANTS);

        assertEquals(meeting, same);
        assertEquals(meeting.hashCode(), same.hashCode());
        assertTrue(meeting.isSameMeeting(same));
        assertFalse(meeting.isSameMeeting(differentTime));
        assertTrue(meeting.isSameMeeting(meeting));
        assertFalse(meeting.isSameMeeting(null));
    }

    @Test
    public void participants_areDefensiveCopies() {
        Meeting meeting = new Meeting(TITLE, DATE, START, END, PARTICIPANTS);
        assertThrows(UnsupportedOperationException.class, () ->
                meeting.getParticipants().add(new Name("Bernice Yu")));
    }

    @Test
    public void getters_returnExpectedValues() {
        Meeting meeting = new Meeting(TITLE, DATE, START, END, PARTICIPANTS);
        assertEquals(TITLE, meeting.getTitle());
        assertEquals(DATE, meeting.getDate());
        assertEquals(START, meeting.getStartTime());
        assertEquals(END, meeting.getEndTime());
        assertEquals(PARTICIPANTS, meeting.getParticipants());
        assertTrue(meeting.toString().contains("Sprint sync"));
    }
}
