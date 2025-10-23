package seedu.address.storage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingTitle;
import seedu.address.model.person.Name;

/**
 * Jackson-friendly version of {@link Meeting}.
 */
class JsonAdaptedMeeting {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Meeting's %s field is missing!";

    private final String title;
    private final String date;
    private final String startTime;
    private final String endTime;
    private final List<String> participants;
    private final boolean isCompleted;
    private final String notes;

    /**
     * Constructs a {@code JsonAdaptedMeeting} with the given meeting details.
     */
    @JsonCreator
    public JsonAdaptedMeeting(@JsonProperty("title") String title, @JsonProperty("date") String date,
            @JsonProperty("startTime") String startTime, @JsonProperty("endTime") String endTime,
            @JsonProperty("participants") List<String> participants, @JsonProperty("isCompleted") boolean isCompleted,
            @JsonProperty("notes") String notes) {
        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = participants;
        this.isCompleted = isCompleted;
        this.notes = notes;
    }

    /**
     * Converts a given {@code Meeting} into this class for Jackson use.
     */
    public JsonAdaptedMeeting(Meeting source) {
        title = source.getTitle().toString();
        date = source.getDate().toString();
        startTime = source.getStartTime().toString();
        endTime = source.getEndTime().toString();
        participants = source.getParticipants().stream()
                .map(Name::toString)
                .collect(Collectors.toList());
        isCompleted = source.isCompleted();
        notes = source.getNotes();
    }

    /**
     * Converts this Jackson-friendly adapted meeting object into the model's {@code Meeting} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted meeting.
     */
    public Meeting toModelType() throws IllegalValueException {
        if (title == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    MeetingTitle.class.getSimpleName()));
        }
        if (!MeetingTitle.isValidTitle(title)) {
            throw new IllegalValueException(MeetingTitle.MESSAGE_CONSTRAINTS);
        }
        final MeetingTitle modelTitle = new MeetingTitle(title);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalDate.class.getSimpleName()));
        }
        final LocalDate modelDate;
        try {
            modelDate = LocalDate.parse(date);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid date format: " + date);
        }

        if (startTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalTime.class.getSimpleName()));
        }
        final LocalTime modelStartTime;
        try {
            modelStartTime = LocalTime.parse(startTime);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid start time format: " + startTime);
        }

        if (endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    LocalTime.class.getSimpleName()));
        }
        final LocalTime modelEndTime;
        try {
            modelEndTime = LocalTime.parse(endTime);
        } catch (Exception e) {
            throw new IllegalValueException("Invalid end time format: " + endTime);
        }

        if (participants == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    "participants"));
        }
        final List<Name> modelParticipants = new ArrayList<>();
        for (String participantName : participants) {
            if (!Name.isValidName(participantName)) {
                throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
            }
            modelParticipants.add(new Name(participantName));
        }

        if (notes == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "notes"));
        }
        if (!Meeting.isValidNotes(notes)) {
            throw new IllegalValueException(Meeting.MESSAGE_CONSTRAINTS);
        }
        final String modelNotes = notes;

        return new Meeting(modelTitle, modelDate, modelStartTime, modelEndTime,
                modelParticipants, isCompleted, modelNotes);
    }
}
