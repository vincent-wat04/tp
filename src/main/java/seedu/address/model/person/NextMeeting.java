package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's next meeting time in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNextMeeting(String)}
 */
public class NextMeeting {

    public static final String MESSAGE_CONSTRAINTS =
            "Next meeting can take any values, and it should not be blank";
    public static final String DEFAULT_VALUE = "No meeting scheduled";

    public static final NextMeeting DEFAULT = new NextMeeting(DEFAULT_VALUE);

    /*
     * The first character of the next meeting must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[^\\s].*";

    public final String value;

    /**
     * Constructs an {@code NextMeeting}.
     *
     * @param nextMeeting A valid next meeting description.
     */
    public NextMeeting(String nextMeeting) {
        requireNonNull(nextMeeting);
        checkArgument(isValidNextMeeting(nextMeeting), MESSAGE_CONSTRAINTS);
        value = nextMeeting;
    }

    /**
     * Returns true if a given string is a valid next meeting.
     */
    public static boolean isValidNextMeeting(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NextMeeting)) {
            return false;
        }

        NextMeeting otherNextMeeting = (NextMeeting) other;
        return value.equals(otherNextMeeting.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
