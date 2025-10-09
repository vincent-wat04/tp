package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's nickname in the address book.
 * Guarantees: immutable; is always valid
 */
public class Nickname {

    public final String value;

    /**
     * Constructs a {@code Nickname}.
     *
     * @param nickname A nickname.
     */
    public Nickname(String nickname) {
        requireNonNull(nickname);
        value = nickname;
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
        if (!(other instanceof Nickname)) {
            return false;
        }

        Nickname otherNickname = (Nickname) other;
        return value.equals(otherNickname.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

