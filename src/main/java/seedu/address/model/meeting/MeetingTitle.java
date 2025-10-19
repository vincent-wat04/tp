package seedu.address.model.meeting;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the title of a {@link Meeting}.
 * Guarantees: immutable; title is valid as declared in {@link #isValidTitle(String)}.
 */
public class MeetingTitle {

    public static final String MESSAGE_CONSTRAINTS =
            "Meeting titles should be 1 to 100 characters long after trimming.";
    private static final int MAX_LENGTH = 100;

    private final String value;

    /**
     * Constructs a {@code MeetingTitle}.
     *
     * @param title A valid meeting title.
     */
    public MeetingTitle(String title) {
        requireNonNull(title);
        String trimmed = title.trim();
        checkArgument(isValidTitle(trimmed), MESSAGE_CONSTRAINTS);
        this.value = trimmed;
    }

    /**
     * Returns true if a given string is a valid meeting title.
     */
    public static boolean isValidTitle(String test) {
        if (test == null) {
            return false;
        }
        String trimmed = test.trim();
        return !trimmed.isEmpty() && trimmed.length() <= MAX_LENGTH;
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
        if (!(other instanceof MeetingTitle)) {
            return false;
        }
        MeetingTitle otherTitle = (MeetingTitle) other;
        return value.equals(otherTitle.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
