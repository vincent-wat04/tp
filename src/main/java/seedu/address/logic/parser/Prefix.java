package seedu.address.logic.parser;

/**
 * Represents a prefix that marks the beginning of an argument in a command input string.
 * <p>
 * For example, in the input {@code "add James t/friend"}, the prefix {@code "t/"} indicates that
 * the argument following it ({@code "friend"}) is associated with the {@code t/} tag.
 * </p>
 */
public class Prefix {

    /** The string representation of the prefix (e.g., "t/"). */
    private final String prefix;

    /**
     * Constructs a {@code Prefix} with the specified prefix string.
     *
     * @param prefix The prefix string (e.g., "t/").
     */
    public Prefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Returns the string value of this prefix.
     *
     * @return The prefix string.
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * Returns the string representation of this prefix.
     * Equivalent to calling {@link #getPrefix()}.
     *
     * @return The prefix string.
     */
    @Override
    public String toString() {
        return getPrefix();
    }

    /**
     * Returns the hash code value for this prefix.
     * If the prefix string is {@code null}, returns {@code 0}.
     *
     * @return The hash code of the prefix string.
     */
    @Override
    public int hashCode() {
        return prefix == null ? 0 : prefix.hashCode();
    }

    /**
     * Compares this prefix with another object for equality.
     * Two {@code Prefix} objects are considered equal if their prefix strings are equal.
     *
     * @param other The object to compare with.
     * @return {@code true} if both objects represent the same prefix, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Prefix)) {
            return false;
        }

        Prefix otherPrefix = (Prefix) other;
        return prefix.equals(otherPrefix.prefix);
    }
}
