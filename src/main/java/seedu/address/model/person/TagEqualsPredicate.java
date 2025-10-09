package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.model.tag.Tag;

/**
 * Tests that a {@code Person} has a tag exactly matching the given name.
 * <p>
 * Matching is case-sensitive and must match the full tag name.
 * </p>
 */
public class TagEqualsPredicate implements Predicate<Person> {

    private final Tag target;

    /**
     * Constructs a predicate that matches persons containing the given tag name.
     *
     * @param tagName a valid tag name (must satisfy
     *                {@link Tag#isValidTagName(String)})
     * @throws NullPointerException     if {@code tagName} is null
     * @throws IllegalArgumentException if {@code tagName} violates
     *                                  {@link Tag#MESSAGE_CONSTRAINTS}
     */
    public TagEqualsPredicate(String tagName) {
        requireNonNull(tagName);
        if (!Tag.isValidTagName(tagName)) {
            throw new IllegalArgumentException(Tag.MESSAGE_CONSTRAINTS);
        }
        this.target = new Tag(tagName);
    }

    @Override
    public boolean test(Person person) {
        return person.getTags().contains(target);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof TagEqualsPredicate
                        && target.equals(((TagEqualsPredicate) other).target));
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }
}
