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
 * Immutable representation of a scheduled meeting. This lightweight version only tracks
 * the details required for upcoming MM1 commands.
 */
public class Meeting {

    public static final String MESSAGE_END_BEFORE_START = "Meeting end time must differ from start time";

    private final MeetingTitle title;
    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final List<Name> participants;

    /**
     * Constructs a {@code Meeting}. Participants are stored as an immutable ordered list.
     */
    public Meeting(MeetingTitle title, LocalDate date, LocalTime startTime, LocalTime endTime,
                   List<Name> participants) {
        requireNonNull(title);
        requireNonNull(date);
        requireNonNull(startTime);
        requireNonNull(endTime);
        requireNonNull(participants);

        checkArgument(!startTime.equals(endTime), MESSAGE_END_BEFORE_START);

        this.title = title;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = Collections.unmodifiableList(new ArrayList<>(participants));
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
                && participants.equals(otherMeeting.participants);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, startTime, endTime, participants);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("title", title)
                .add("date", date)
                .add("start", startTime)
                .add("end", endTime)
                .add("participants", participants)
                .toString();
    }
}
