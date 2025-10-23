package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.person.Name;

public class JsonAdaptedMeetingTest {
    private static final String VALID_TITLE = "Team Meeting";
    private static final String VALID_DATE = "2025-12-01";
    private static final String VALID_START_TIME = "10:00";
    private static final String VALID_END_TIME = "11:00";
    private static final List<String> VALID_PARTICIPANTS = List.of("Alice Pauline", "Benson Meier");
    private static final boolean VALID_IS_COMPLETED = false;
    private static final String VALID_NOTES = "Discuss project timeline";

    private static final String INVALID_TITLE = "";
    private static final String INVALID_DATE = "invalid-date";
    private static final String INVALID_START_TIME = "25:00";
    private static final String INVALID_END_TIME = "invalid-time";
    private static final String INVALID_PARTICIPANT = "";
    private static final String INVALID_NOTES = " ";

    @Test
    public void toModelType_validMeetingDetails_returnsMeeting() throws Exception {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_TITLE, VALID_DATE, VALID_START_TIME,
                VALID_END_TIME, VALID_PARTICIPANTS, VALID_IS_COMPLETED, VALID_NOTES);
        Meeting expectedMeeting = new Meeting(
                new MeetingTitle(VALID_TITLE),
                LocalDate.parse(VALID_DATE),
                LocalTime.parse(VALID_START_TIME),
                LocalTime.parse(VALID_END_TIME),
                List.of(new Name("Alice Pauline"), new Name("Benson Meier")),
                VALID_IS_COMPLETED,
                VALID_NOTES
        );
        assertEquals(expectedMeeting, meeting.toModelType());
    }

    @Test
    public void toModelType_validMeetingWithEmptyNotes_returnsMeeting() throws Exception {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_TITLE, VALID_DATE, VALID_START_TIME,
                VALID_END_TIME, VALID_PARTICIPANTS, VALID_IS_COMPLETED, "");
        Meeting expectedMeeting = new Meeting(
                new MeetingTitle(VALID_TITLE),
                LocalDate.parse(VALID_DATE),
                LocalTime.parse(VALID_START_TIME),
                LocalTime.parse(VALID_END_TIME),
                List.of(new Name("Alice Pauline"), new Name("Benson Meier")),
                VALID_IS_COMPLETED,
                ""
        );
        assertEquals(expectedMeeting, meeting.toModelType());
    }

    @Test
    public void toModelType_invalidTitle_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(INVALID_TITLE, VALID_DATE, VALID_START_TIME, VALID_END_TIME,
                        VALID_PARTICIPANTS, VALID_IS_COMPLETED, VALID_NOTES);
        String expectedMessage = MeetingTitle.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullTitle_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(null, VALID_DATE, VALID_START_TIME,
                VALID_END_TIME, VALID_PARTICIPANTS, VALID_IS_COMPLETED, VALID_NOTES);
        String expectedMessage = String.format(JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT,
                MeetingTitle.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, INVALID_DATE, VALID_START_TIME, VALID_END_TIME,
                        VALID_PARTICIPANTS, VALID_IS_COMPLETED, VALID_NOTES);
        assertThrows(IllegalValueException.class, meeting::toModelType);
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_TITLE, null, VALID_START_TIME,
                VALID_END_TIME, VALID_PARTICIPANTS, VALID_IS_COMPLETED, VALID_NOTES);
        String expectedMessage = String.format(JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT,
                LocalDate.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, VALID_DATE, INVALID_START_TIME, VALID_END_TIME,
                        VALID_PARTICIPANTS, VALID_IS_COMPLETED, VALID_NOTES);
        assertThrows(IllegalValueException.class, meeting::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_TITLE, VALID_DATE, null,
                VALID_END_TIME, VALID_PARTICIPANTS, VALID_IS_COMPLETED, VALID_NOTES);
        String expectedMessage = String.format(JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT,
                LocalTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, VALID_DATE, VALID_START_TIME, INVALID_END_TIME,
                        VALID_PARTICIPANTS, VALID_IS_COMPLETED, VALID_NOTES);
        assertThrows(IllegalValueException.class, meeting::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_TITLE, VALID_DATE, VALID_START_TIME,
                null, VALID_PARTICIPANTS, VALID_IS_COMPLETED, VALID_NOTES);
        String expectedMessage = String.format(JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT,
                LocalTime.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidParticipant_throwsIllegalValueException() {
        List<String> invalidParticipants = new ArrayList<>(VALID_PARTICIPANTS);
        invalidParticipants.add(INVALID_PARTICIPANT);
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, VALID_DATE, VALID_START_TIME, VALID_END_TIME,
                        invalidParticipants, VALID_IS_COMPLETED, VALID_NOTES);
        assertThrows(IllegalValueException.class, meeting::toModelType);
    }

    @Test
    public void toModelType_nullParticipants_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_TITLE, VALID_DATE, VALID_START_TIME,
                VALID_END_TIME, null, VALID_IS_COMPLETED, VALID_NOTES);
        String expectedMessage = String.format(JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT,
                "participants");
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_invalidNotes_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting =
                new JsonAdaptedMeeting(VALID_TITLE, VALID_DATE, VALID_START_TIME, VALID_END_TIME,
                        VALID_PARTICIPANTS, VALID_IS_COMPLETED, INVALID_NOTES);
        String expectedMessage = Meeting.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_nullNotes_throwsIllegalValueException() {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_TITLE, VALID_DATE, VALID_START_TIME,
                VALID_END_TIME, VALID_PARTICIPANTS, VALID_IS_COMPLETED, null);
        String expectedMessage = String.format(JsonAdaptedMeeting.MISSING_FIELD_MESSAGE_FORMAT, "notes");
        assertThrows(IllegalValueException.class, expectedMessage, meeting::toModelType);
    }

    @Test
    public void toModelType_completedMeeting_returnsMeeting() throws Exception {
        JsonAdaptedMeeting meeting = new JsonAdaptedMeeting(VALID_TITLE, VALID_DATE, VALID_START_TIME,
                VALID_END_TIME, VALID_PARTICIPANTS, true, VALID_NOTES);
        Meeting expectedMeeting = new Meeting(
                new MeetingTitle(VALID_TITLE),
                LocalDate.parse(VALID_DATE),
                LocalTime.parse(VALID_START_TIME),
                LocalTime.parse(VALID_END_TIME),
                List.of(new Name("Alice Pauline"), new Name("Benson Meier")),
                true,
                VALID_NOTES
        );
        assertEquals(expectedMeeting, meeting.toModelType());
    }

    @Test
    public void constructor_validMeeting_success() throws Exception {
        Meeting meeting = new Meeting(
                new MeetingTitle(VALID_TITLE),
                LocalDate.parse(VALID_DATE),
                LocalTime.parse(VALID_START_TIME),
                LocalTime.parse(VALID_END_TIME),
                List.of(new Name("Alice Pauline"), new Name("Benson Meier")),
                VALID_IS_COMPLETED,
                VALID_NOTES
        );
        JsonAdaptedMeeting jsonMeeting = new JsonAdaptedMeeting(meeting);
        assertEquals(meeting, jsonMeeting.toModelType());
    }
}

