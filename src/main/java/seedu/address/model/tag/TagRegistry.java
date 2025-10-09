package seedu.address.model.tag;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Holds the allow-list of tag names permitted in the app.
 * Names are stored in lower-case; external callers should normalize inputs.
 */
public class TagRegistry {
    private final Set<String> allowed = new HashSet<>();

    public TagRegistry(Set<String> seed) {
        if (seed != null) {
            for (String s : seed) {
                if (s != null) {
                    allowed.add(s.trim().toLowerCase());
                }
            }
        }
    }

    /** Returns true if the tag name is allowed. */
    public boolean isAllowed(String name) {
        Objects.requireNonNull(name);
        return allowed.contains(name.trim().toLowerCase());
    }

    /** Adds a new allowed tag; returns true if newly added. */
    public boolean add(String name) {
        Objects.requireNonNull(name);
        String t = name.trim().toLowerCase();
        // lightweight hygiene: keep alnum rule
        if (!t.matches("\\p{Alnum}+")) return false;
        return allowed.add(t);
    }

    /** Removes an allowed tag; returns true if it was present. */
    public boolean remove(String name) {
        Objects.requireNonNull(name);
        return allowed.remove(name.trim().toLowerCase());
    }

    /** Unmodifiable view of allowed tags. */
    public Set<String> view() {
        return Collections.unmodifiableSet(allowed);
    }
}
