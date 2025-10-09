package seedu.address.model.tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {

    private static final Set<String> VALID_TAGS = new HashSet<>(Set.of(
            "friend", "family", "colleague", "classmate", "client"
    ));

    public static final String MESSAGE_CONSTRAINTS =
            "Tag name must be one of the predefined valid tags: " + String.join(", ", getValidTags());

    public final String tagName;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        String trimmedTag = tagName.trim().toLowerCase();
        checkArgument(isValidTagName(trimmedTag), MESSAGE_CONSTRAINTS);
        this.tagName = trimmedTag;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return VALID_TAGS.contains(test.trim().toLowerCase());
    }

    /**
     * Returns an unmodifiable view of valid tags.
     */
    public static Set<String> getValidTags() {
        return Collections.unmodifiableSet(VALID_TAGS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Tag)) {
            return false;
        }

        Tag otherTag = (Tag) other;
        return tagName.equals(otherTag.tagName);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

}
