package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.person.Name;

/**
 * Immutable representation of a scheduled meeting. Tracks meeting details,
 * completion status, and optional notes for meeting management.
 */
public class Meeting {

    public static final String MESSAGE_END_BEFORE_START = "Meeting end time must differ from start time";
    public static final String MESSAGE_CONSTRAINTS = "Meeting notes can take any values, and it can be blank";

    /*
     * Notes can be any string, including empty string.
     * The first character of the notes must not be a whitespace if non-empty,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "^$|[^\\s].*";

    private final MeetingTitle title;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final List<Name> participants;
    private final boolean isCompleted;
    private final String notes;

    /**
     * Constructs a {@code Meeting} with completion status and notes.
     * Participants are stored as an immutable ordered list.
     */
    public Meeting(MeetingTitle title, LocalDate date, LocalTime startTime, LocalTime endTime,
                   List<Name> participants, boolean isCompleted, String notes) {
        requireNonNull(title);
        requireNonNull(date);
        requireNonNull(startTime);
        requireNonNull(endTime);
        requireNonNull(participants);
        requireNonNull(notes);

        checkArgument(!startTime.equals(endTime), MESSAGE_END_BEFORE_START);
        checkArgument(isValidNotes(notes), MESSAGE_CONSTRAINTS);

        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = Collections.unmodifiableList(new ArrayList<>(participants));
        this.isCompleted = isCompleted;
        this.notes = notes;
    }

    /**
     * Constructs a {@code Meeting} with default completion status (false) and empty notes.
     * Participants are stored as an immutable ordered list.
     */
    public Meeting(MeetingTitle title, LocalDate date, LocalTime startTime, LocalTime endTime,
                   List<Name> participants) {
        this(title, date, startTime, endTime, participants, false, "");
    }

    public MeetingTitle getTitle() {
        return title;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public List<Name> getParticipants() {
        return participants;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public String getNotes() {
        return notes;
    }

    /**
     * Returns true if a given string is valid notes.
     */
    public static boolean isValidNotes(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Identity comparison used by future MM1 commands (title + date + start time).
     */
    public boolean isSameMeeting(Meeting other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        return title.equals(other.title)
                && date.equals(other.date)
                && startTime.equals(other.startTime);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Meeting)) {
            return false;
        }
        Meeting otherMeeting = (Meeting) other;
        return title.equals(otherMeeting.title)
                && date.equals(otherMeeting.date)
                && startTime.equals(otherMeeting.startTime)
                && endTime.equals(otherMeeting.endTime)
                && participants.equals(otherMeeting.participants)
                && isCompleted == otherMeeting.isCompleted
                && notes.equals(otherMeeting.notes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, startTime, endTime, participants, isCompleted, notes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("title", title)
                .add("date", date)
                .add("start", startTime)
                .add("end", endTime)
                .add("participants", participants)
                .add("isCompleted", isCompleted)
                .add("notes", notes)
                .toString();
    }
}
