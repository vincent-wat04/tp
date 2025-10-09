package seedu.address.model.tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a registry that defines the list of allowed tag names in the application.
 * <p>
 * The {@code TagRegistry} acts as a centralized whitelist that commands such as
 * {@code AddCommand}, {@code EditCommand}, and {@code TagAddCommand} can query or update
 * to ensure consistent tag usage across the address book.
 * <p>
 * Tag names are stored in lowercase for case-insensitive comparison.
 * This class is mutable and not thread-safe.
 */
public class TagRegistry {
    private final Set<String> allowed = new HashSet<>();

    /**
     * Constructs a {@code TagRegistry} with an initial set of allowed tag names.
     *
     * @param seed a set of tag names to initialize the registry with; may be {@code null}
     */
    public TagRegistry(Set<String> seed) {
        if (seed != null) {
            for (String s : seed) {
                if (s != null) { // ✅ now uses braces
                    allowed.add(s.trim().toLowerCase());
                }
            }
        }
    }

    /**
     * Returns true if the given tag name is currently allowed.
     *
     * @param name tag name to check
     * @return true if the tag is allowed, false otherwise
     */
    public boolean isAllowed(String name) {
        Objects.requireNonNull(name);
        return allowed.contains(name.trim().toLowerCase());
    }

    /**
     * Adds a new allowed tag name to the registry.
     * Returns true if the tag was newly added, false if it already exists or is invalid.
     *
     * @param name tag name to add
     * @return true if successfully added
     */
    public boolean add(String name) {
        Objects.requireNonNull(name);
        String t = name.trim().toLowerCase();
        if (!t.matches("\\p{Alnum}+")) {
            return false;
        }
        return allowed.add(t);
    }

    /**
     * Removes an allowed tag name from the registry.
     *
     * @param name tag name to remove
     * @return true if the tag was present and removed
     */
    public boolean remove(String name) {
        Objects.requireNonNull(name);
        return allowed.remove(name.trim().toLowerCase());
    }

    /**
     * Returns an unmodifiable view of the allowed tag names.
     *
     * @return an unmodifiable {@code Set} of tag names
     */
    public Set<String> view() {
        return Collections.unmodifiableSet(allowed);
    }
}
